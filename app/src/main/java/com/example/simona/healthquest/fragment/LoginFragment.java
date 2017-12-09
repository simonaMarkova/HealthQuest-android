package com.example.simona.healthquest.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.helper.JSON;
import com.example.simona.healthquest.model.FacebookLogin;
import com.example.simona.healthquest.model.LoginInfo;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.network.RetrofitManager;
import com.example.simona.healthquest.persistance.Persistence;
import com.example.simona.healthquest.util.UI;
import com.example.simona.healthquest.util.Validate;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simona on 7/17/2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private View rootView;
    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private Button loginSignInBtn;
    private Button btnFacebook;
    private Button btnVisiblePassword;
    private LoginButton loginButton;
    private Button btnSighUp;
    private CallbackManager callbackManager = null;
    private String fbToken;
    ImageView image;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginSignInBtn = (Button) rootView.findViewById(R.id.loginSignInBtn);
        loginSignInBtn.setOnClickListener(this);
        btnFacebook = (Button) rootView.findViewById(R.id.btnFacebook);
        btnSighUp = (Button) rootView.findViewById(R.id.btnSighUp);
        btnSighUp.setOnClickListener(this);

        etLoginEmail = (EditText) rootView.findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) rootView.findViewById(R.id.etLoginPassword);
        btnVisiblePassword = (Button) rootView.findViewById(R.id.btnVisiblePassword);

        toolbar.setVisibility(View.GONE);

        btnVisiblePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        btnVisiblePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_black, 0);
                        break;
                    case MotionEvent.ACTION_UP:
                        etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                        btnVisiblePassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_visibility_off_black, 0);
                        etLoginPassword.setTypeface(etLoginEmail.getTypeface());
                        break;
                }
                return true;
            }
        });

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) rootView.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

        image = (ImageView) rootView.findViewById(R.id.facebookImage);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UI.addFragment(supportFragmentManager, R.id.container_layout, ProgressBarFragment.newInstance(), true, 0, 0);
                loginButton.performClick();
            }
        });

        return rootView;
    }

    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(final LoginResult loginResult) {

            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    fbToken = loginResult.getAccessToken().getToken();
                    String email = new String();
                    final User user = new User();
                    user.setFirstName(Profile.getCurrentProfile().getFirstName());
                    user.setLastName(Profile.getCurrentProfile().getLastName());
                    String fbProfilePictureUrl = Profile.getCurrentProfile().getProfilePictureUri(150,150).toString();
                    try {
                        email = object.getString("email");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    user.setEmail(email);

                    Picasso.with(context).load(fbProfilePictureUrl).into(image, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            final byte[] imageInByte = baos.toByteArray();
                            user.setProfileImage(Base64.encodeToString(imageInByte, Base64.DEFAULT));

                            FacebookLogin facebookLogin = new FacebookLogin();
                            facebookLogin.setUser(user);
                            facebookLogin.setAccessToken(fbToken);

                            RetrofitManager.getInstance().getRetrofitService().facebookLogin(facebookLogin).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    if(response.isSuccessful()){
                                        Persistence.getInstance().getPersistence().setString(Persistence.KEY_USER, JSON.toJson(response.body(),User.class));
                                        UI.popUpBackstack(supportFragmentManager);
                                        UI.replaceFragment(supportFragmentManager, R.id.container_layout, MainFragment.newInstance(), false, 0, 0);
                                    }else {
                                        Toast.makeText(context,  "Response error", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(context,  "Failure", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        @Override
                        public void onError() {

                        }
                    });

                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id, first_name, last_name, email");
            request.setParameters(parameters);
            request.executeAsync();
        }

        @Override
        public void onCancel() {
            Toast.makeText(context, "Cancel facebook", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(FacebookException error) {
            Toast.makeText(context, "Error facebook"+error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginSignInBtn:
                login();
                break;
            case R.id.btnSighUp:
                UI.addFragment(supportFragmentManager, R.id.container_layout, SignUpFragment.newInstance(), true, 0,0);
                break;
        }
    }

    private void login() {
        UI.addFragment(supportFragmentManager, R.id.container_layout, ProgressBarFragment.newInstance(), true, 0, 0);
        if (Validate.getInstance().validatePassword(etLoginPassword.getText().toString())) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setEmail(etLoginEmail.getText().toString());
            loginInfo.setPassword(etLoginPassword.getText().toString());
            RetrofitManager.getInstance().getRetrofitService().login(loginInfo).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Persistence.getInstance().getPersistence().setString(Persistence.KEY_USER, JSON.toJson(response.body(),User.class));
                        UI.popUpBackstack(supportFragmentManager);
                        UI.replaceFragment(supportFragmentManager, R.id.container_layout, MainFragment.newInstance(), false, 0, 0);
                    } else {
                        Toast.makeText(context, R.string.login_invalid, Toast.LENGTH_LONG).show();
                        UI.clearBackstack(supportFragmentManager);
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(context,  R.string.login_error, Toast.LENGTH_LONG).show();
                }
            });

        } else {
            Toast.makeText(context, R.string.login_invalid, Toast.LENGTH_LONG).show();
        }

    }
}
