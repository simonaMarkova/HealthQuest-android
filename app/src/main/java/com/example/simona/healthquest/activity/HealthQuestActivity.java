package com.example.simona.healthquest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.fragment.BaseFragment;
import com.example.simona.healthquest.fragment.LoginFragment;
import com.example.simona.healthquest.util.UI;

public class HealthQuestActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_quest);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("");
        }
        UI.replaceFragment(getSupportFragmentManager(),R.id.container_layout,LoginFragment.newInstance(),false,0,0);
    }

    @Override
    public void onFragmentInteraction(BaseFragment source, Bundle message, Class destination) {

    }
}
