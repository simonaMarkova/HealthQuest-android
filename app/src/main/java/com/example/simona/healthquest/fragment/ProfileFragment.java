package com.example.simona.healthquest.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.activity.HealthQuestActivity;
import com.example.simona.healthquest.helper.JSON;
import com.example.simona.healthquest.log.LogLevel;
import com.example.simona.healthquest.log.Logger;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.network.RetrofitManager;
import com.example.simona.healthquest.persistance.Persistence;
import com.example.simona.healthquest.util.Constants;
import com.example.simona.healthquest.util.UI;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simona on 8/3/2017.
 */

public class ProfileFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private ImageView userProfileImage;
    private TextView userProfileName;
    private TextView userProfileEmail;
    private TextView userProfileLevel;
    private TextView userProfilePoints;
    private User user;
    static final int REQUEST_IMAGE_CAPTURE = 100;
    static final int REQUEST_IMAGE_GALLERY = 1;
    private Button btnProfileTakePicture;
    private Button btnProfileChooseFromGallery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_profile,container,false);
        userProfileImage = (ImageView) rootView.findViewById(R.id.userProfileImage);
        userProfileName = (TextView) rootView.findViewById(R.id.userProfileName);
        userProfileEmail = (TextView) rootView.findViewById(R.id.userProfileEmail);
        userProfileLevel = (TextView) rootView.findViewById(R.id.userProfileLevel);
        userProfilePoints = (TextView) rootView.findViewById(R.id.userProfilePoints);

        btnProfileTakePicture = (Button) rootView.findViewById(R.id.btnProfileTakePicture);
        btnProfileTakePicture.setOnClickListener(this);

        btnProfileChooseFromGallery = (Button) rootView.findViewById(R.id.btnProfileChooseFromGallery);
        btnProfileChooseFromGallery.setOnClickListener(this);

        user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""),User.class);

        setProfileInfo();
        return rootView;
    }

    public void setProfileInfo(){
        Picasso.with(context).load(Constants.BASE_URL + "/user/photo/" + user.id).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.brain_with_bg).into(userProfileImage);
        RetrofitManager.getInstance().getRetrofitService().getUser(user.id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Persistence.getInstance().getPersistence().setString(Persistence.KEY_USER, JSON.toJson(response.body(),User.class));
                    userProfileName.setText(user.getFirstName() + " " + user.getLastName());
                    userProfileEmail.setText(user.getEmail());
                    userProfileLevel.setText(Integer.toString(user.getLevel().getLevel()));
                    userProfilePoints.setText(Integer.toString(user.getPoints()));
                } else {
                    Toast.makeText(context,  R.string.login_error, Toast.LENGTH_LONG).show();
                    UI.clearBackstack(supportFragmentManager);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, R.string.login_error, Toast.LENGTH_LONG).show();
                UI.clearBackstack(supportFragmentManager);
            }
        });

        RetrofitManager.getInstance().getRetrofitService().getPoints(user.id).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    userProfilePoints.setText(Integer.toString(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {

            }
        });
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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
            new ConvertCameraImage().execute((Bitmap)extras.get("data"));
        }
        if(requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK){
            new ConvertGalleryImage().execute(data.getData());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnProfileChooseFromGallery:
                startGallery();
                break;
            case R.id.btnProfileTakePicture:
                startCamera();;
                break;
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
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
            bitmap = Bitmap.createBitmap(bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                userProfileImage.setImageBitmap(bitmap);
                userProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.onPostExecute(bitmap);
                user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""),User.class);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] imageInByte = baos.toByteArray();
                user.setProfileImage(Base64.encodeToString(imageInByte, Base64.DEFAULT));

                RetrofitManager.getInstance().getRetrofitService().updatePhoto(user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            ((HealthQuestActivity) getActivity()).updateNavDrawerInfo(user, ((HealthQuestActivity) getActivity()).getNavigationView());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Faliure",Toast.LENGTH_SHORT).show();
                    }
                });
            }
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
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
                bitmap = Bitmap.createBitmap(bitmap);
            } catch (IOException e) {
                Logger.log(LogLevel.ERROR, e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                userProfileImage.setImageBitmap(bitmap);
                userProfileImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                super.onPostExecute(bitmap);
                user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""),User.class);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] imageInByte = baos.toByteArray();
                user.setProfileImage(Base64.encodeToString(imageInByte, Base64.DEFAULT));

                RetrofitManager.getInstance().getRetrofitService().updatePhoto(user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            ((HealthQuestActivity) getActivity()).updateNavDrawerInfo(user, ((HealthQuestActivity) getActivity()).getNavigationView());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context,"Faliure",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }


}
