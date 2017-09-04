package com.example.simona.healthquest.fragment;

        import android.app.Activity;
        import android.content.ContentResolver;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.Matrix;
        import android.media.Image;
        import android.net.Uri;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.provider.MediaStore;
        import android.support.annotation.Nullable;
        import android.util.Base64;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.example.simona.healthquest.R;
        import com.example.simona.healthquest.helper.JSON;
        import com.example.simona.healthquest.persistance.Persistence;
        import com.example.simona.healthquest.util.UI;
        import com.example.simona.healthquest.model.Level;
        import com.example.simona.healthquest.model.User;
        import com.example.simona.healthquest.network.RetrofitManager;
        import com.example.simona.healthquest.log.*;

        import java.io.ByteArrayOutputStream;
        import java.io.IOException;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

/**
 * Created by Simona on 7/18/2017.
 */

public class SignUpFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private Button btnRegister;
    private EditText etSignUpName;
    private EditText etSignUpSurName;
    private EditText etSignUpEmail;
    private EditText etSignUpUsername;
    private EditText etSignUpPassword;
    private Level level;
    private ImageView ivRegisterProfileImage;
    private Button btnChooseFromGallery;
    private Button btnTakePicture;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_IMAGE_GALLERY = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        etSignUpName = (EditText) rootView.findViewById(R.id.etSignUpName);
        etSignUpSurName = (EditText) rootView.findViewById(R.id.etSignUpSurName);
        etSignUpEmail = (EditText) rootView.findViewById(R.id.etSignUpEmail);
        etSignUpUsername = (EditText) rootView.findViewById(R.id.etSignUpUsername);
        etSignUpPassword = (EditText) rootView.findViewById(R.id.etSignUpPassword);
        ivRegisterProfileImage = (ImageView) rootView.findViewById(R.id.ivRegisterProfileImage);
        btnChooseFromGallery = (Button) rootView.findViewById(R.id.btnChooseFromGallery);
        btnTakePicture = (Button) rootView.findViewById(R.id.btnTakePicture);
        btnTakePicture.setOnClickListener(this);
        btnChooseFromGallery.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                registerUser();
                break;
            case R.id.btnChooseFromGallery:
                startGallery();
                break;
            case R.id.btnTakePicture:
                startCamera();;
                break;
        }
    }

    private void registerUser() {
        final User user = new User();
        user.setFirstName(etSignUpName.getText().toString());
        user.setLastName(etSignUpSurName.getText().toString());
        user.setEmail(etSignUpEmail.getText().toString());
        user.setUsername(etSignUpUsername.getText().toString());
        user.setPassword(etSignUpPassword.getText().toString());
        user.setPoints(0);
        ivRegisterProfileImage.setDrawingCacheEnabled(true);
        Bitmap bitmap = ivRegisterProfileImage.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        final byte[] imageInByte = baos.toByteArray();
        user.setProfileImage(Base64.encodeToString(imageInByte, Base64.DEFAULT));

        RetrofitManager.getInstance().getRetrofitService().registerUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Persistence.getInstance().getPersistence().setString(Persistence.KEY_USER, JSON.toJson(response.body(),User.class));
                    supportFragmentManager.popBackStack();
                    UI.replaceFragment(supportFragmentManager, R.id.container_layout, MainFragment.newInstance(), false, 0, 0);

                } else {
                    if (response.code() == 409)
                    {
                        Toast.makeText(context, "Корисничкото име е зафатено!", Toast.LENGTH_LONG).show();
                    }
                    else
                    Toast.makeText(context, "Неуспешна регистрација!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
    private void startCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void startGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(galleryIntent, "Select"), REQUEST_IMAGE_GALLERY);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            new ConvertCameraImage().execute((Bitmap)extras.get("data"));
        }
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){
            new ConvertGalleryImage().execute(data.getData());
        }
    }

    private class ConvertCameraImage extends AsyncTask<Bitmap,Void,Bitmap>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Bitmap... data) {
            Bitmap bitmap = data[0];
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                bitmap = Bitmap.createBitmap(bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                int w = ivRegisterProfileImage.getWidth();
                int h = ivRegisterProfileImage.getHeight();
                ivRegisterProfileImage.setImageBitmap(RotateBitmap(bitmap,0));
                ivRegisterProfileImage.getLayoutParams().width = w;
                ivRegisterProfileImage.getLayoutParams().height = h;
                ivRegisterProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.onPostExecute(bitmap);
            }
        }
        public Bitmap RotateBitmap(Bitmap source, float angle) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                    source.getHeight(), matrix, true);
        }

    }

    private class ConvertGalleryImage extends AsyncTask<Uri, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Uri... data) {
            Uri imageUri = data[0];
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                bitmap = Bitmap.createBitmap(bitmap);
            } catch (IOException e) {
                Logger.log(LogLevel.ERROR, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                int w = ivRegisterProfileImage.getWidth();
                int h = ivRegisterProfileImage.getHeight();
                ivRegisterProfileImage.setImageBitmap(RotateBitmap(bitmap,0));
                ivRegisterProfileImage.getLayoutParams().width = w;
                ivRegisterProfileImage.getLayoutParams().height = h;
                ivRegisterProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.onPostExecute(bitmap);
            }
        }
        
        public Bitmap RotateBitmap(Bitmap source, float angle) {
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                    source.getHeight(), matrix, true);
        }
    }
}




