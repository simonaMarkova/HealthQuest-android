package com.example.simona.healthquest.fragment;

        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.simona.healthquest.R;
        import com.example.simona.healthquest.util.UI;
        import com.example.simona.healthquest.model.Level;
        import com.example.simona.healthquest.model.User;
        import com.example.simona.healthquest.network.RetrofitManager;

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

        RetrofitManager.getInstance().getRetrofitService().getLevel((long) 1).enqueue(new Callback<Level>() {
            @Override
            public void onResponse(Call<Level> call, Response<Level> response) {
                if(response.isSuccessful()){
                    level = response.body();
                    user.setLevel(level);
                }
            }

            @Override
            public void onFailure(Call<Level> call, Throwable t) {

            }
        });

        RetrofitManager.getInstance().getRetrofitService().registerUser(user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    supportFragmentManager.popBackStack();
                    UI.replaceFragment(supportFragmentManager, R.id.container_layout, MainFragment.newInstance(), false, 0, 0);

                } else {
                    Toast.makeText(context, "No success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
            }
        });
    }

}