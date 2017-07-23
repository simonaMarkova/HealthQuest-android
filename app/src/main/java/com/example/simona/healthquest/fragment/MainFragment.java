package com.example.simona.healthquest.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.util.UI;

/**
 * Created by Simona on 7/17/2017.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener{
    private View rootView;
    private Button btnMainPlay;
    private Button btnExit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        btnMainPlay = (Button) rootView.findViewById(R.id.btnMainPlay);
        btnMainPlay.setOnClickListener(this);
        btnExit = (Button) rootView.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        supportActionBar.setTitle("Health Quest");
        return rootView;
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnMainPlay:
                openNewGame();
                break;
            case R.id.btnExit:
                exit();
                break;

        }
    }

    private void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Exit");

        alertDialogBuilder
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    //exit from app
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    private void openNewGame() {
        UI.addFragment(supportFragmentManager,R.id.container_layout,GameFragment.newInstance(),true,0,0);
    }

}
