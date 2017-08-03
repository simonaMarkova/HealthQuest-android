package com.example.simona.healthquest.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.helper.JSON;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.persistance.Persistence;
import com.example.simona.healthquest.util.UI;

/**
 * Created by Simona on 7/17/2017.
 */

public class MainFragment extends BaseFragment implements View.OnClickListener{
    private View rootView;
    private Button btnMainPlay;
    private Button btnMainMiniGame;
    private Button btnExit;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        toolbar.setVisibility(View.VISIBLE);

        btnMainPlay = (Button) rootView.findViewById(R.id.btnMainPlay);
        btnMainPlay.setOnClickListener(this);
        btnExit = (Button) rootView.findViewById(R.id.btnExit);
        btnExit.setOnClickListener(this);
        btnMainMiniGame = (Button) rootView.findViewById(R.id.btnMainMiniGame);
        btnMainMiniGame.setOnClickListener(this);
        supportActionBar.setTitle(R.string.main_app_title);

        DrawerLayout drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        NavigationView navigationView;
        navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

        user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""),User.class);
        if(user!=null){
            activity.updateNavDrawerInfo(user, activity.getNavigationView());
        }


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
            case R.id.btnMainMiniGame:
                openNewMiniGame();
                break;
            case R.id.btnExit:
                exit();
                break;

        }
    }

    private void openNewMiniGame() {
        Bundle bundle = new Bundle();
        bundle.putString("type","mini-game");
        UI.addFragment(supportFragmentManager,R.id.container_layout,GameFragment.newInstance(bundle),true,0,0);
    }

    private void exit() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle(R.string.main_exit_title);

        alertDialogBuilder
                .setMessage(R.string.main_exit_desc)
                .setNegativeButton(R.string.main_exit_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.main_exit_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        getActivity().onBackPressed();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    private void openNewGame() {
        Bundle bundle = new Bundle();
        bundle.putString("type","game");
        UI.addFragment(supportFragmentManager,R.id.container_layout,GameFragment.newInstance(bundle),true,0,0);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


}
