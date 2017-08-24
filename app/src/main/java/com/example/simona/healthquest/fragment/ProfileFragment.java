package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.helper.JSON;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.network.RetrofitManager;
import com.example.simona.healthquest.persistance.Persistence;
import com.example.simona.healthquest.util.Constants;
import com.example.simona.healthquest.util.UI;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Simona on 8/3/2017.
 */

public class ProfileFragment extends BaseFragment {
    private View rootView;
    private ImageView userProfileImage;
    private TextView userProfileName;
    private TextView userProfileEmail;
    private TextView userProfileLevel;
    private TextView userProfilePoints;
    private User user;

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

        user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""),User.class);
        RetrofitManager.getInstance().getRetrofitService().getUser(user.id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Persistence.getInstance().getPersistence().setString(Persistence.KEY_USER, JSON.toJson(response.body(),User.class));
                    userProfileName.setText(user.getFirstName() + " " + user.getLastName());
                    userProfileEmail.setText(user.getEmail());
                    userProfileLevel.setText(Integer.toString(user.getLevel().getLevel()));
                    userProfilePoints.setText(Integer.toString(user.getPoints()));
                    Picasso.with(context).load(Constants.BASE_URL + "/user/photo/" + user.id).placeholder(R.drawable.brain_only).into(userProfileImage);


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

        return rootView;
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }
}
