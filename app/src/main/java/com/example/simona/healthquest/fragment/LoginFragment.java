package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.util.UI;
import com.example.simona.healthquest.util.Validate;
import com.facebook.login.widget.LoginButton;

/**
 * Created by Simona on 7/17/2017.
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener{
    private View rootView;
    private EditText etLoginEmail;
    private EditText etLoginPassword;
    private Button loginSignInBtn;
    private Button btnVisiblePassword;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container,false);
        loginSignInBtn = (Button) rootView.findViewById(R.id.loginSignInBtn);
        loginSignInBtn.setOnClickListener(this);
        etLoginEmail = (EditText) rootView.findViewById(R.id.etLoginEmail);
        etLoginPassword = (EditText) rootView.findViewById(R.id.etLoginPassword);
        btnVisiblePassword = (Button) rootView.findViewById(R.id.btnVisiblePassword);

        btnVisiblePassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch ( event.getAction() ) {
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

        return rootView;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginSignInBtn:
                login();
                break;
        }
    }


    private void login() {
        if(Validate.getInstance().validateEmail(etLoginEmail.getText().toString())&&Validate.getInstance().validatePassword(etLoginPassword.getText().toString())){
            UI.replaceFragment(supportFragmentManager,R.id.container_layout,MainFragment.newInstance(),false,0,0);
        }else{
            Toast.makeText(context,"Invalid email or password",Toast.LENGTH_LONG).show();
        }

    }
}
