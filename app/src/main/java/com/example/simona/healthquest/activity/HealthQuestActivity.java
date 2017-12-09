package com.example.simona.healthquest.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.fragment.BaseFragment;
import com.example.simona.healthquest.fragment.GameFragment;
import com.example.simona.healthquest.fragment.LoginFragment;
import com.example.simona.healthquest.fragment.MainFragment;
import com.example.simona.healthquest.fragment.ProfileFragment;
import com.example.simona.healthquest.helper.JSON;
import com.example.simona.healthquest.log.LogCatLogger;
import com.example.simona.healthquest.log.Logger;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.persistance.Persistence;
import com.example.simona.healthquest.persistance.SharedPreferencesPersistence;
import com.example.simona.healthquest.util.Constants;
import com.example.simona.healthquest.util.UI;
import com.facebook.login.LoginManager;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class HealthQuestActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_quest);

        Persistence.getInstance().init(new SharedPreferencesPersistence(getApplicationContext()));
        Logger.getInstance().init(new LogCatLogger());

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, "").equals("")) {
            UI.replaceFragment(getSupportFragmentManager(), R.id.container_layout, LoginFragment.newInstance(), false, 0, 0);
        } else {
            navigationView.getMenu().getItem(0).setChecked(true);
            UI.replaceFragment(getSupportFragmentManager(), R.id.container_layout, MainFragment.newInstance(), false, 0, 0);
        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onFragmentInteraction(BaseFragment source, Bundle message, Class destination) {

    }

    public NavigationView getNavigationView(){
        return navigationView;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            UI.clearBackstack(getSupportFragmentManager());
        }else if(id == R.id.nav_profile){
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container_layout);
            if(!(fragment instanceof ProfileFragment))  {
                UI.clearBackstack(getSupportFragmentManager());
                UI.addFragment(getSupportFragmentManager(), R.id.container_layout, ProfileFragment.newInstance(), true, 0, 0);
            }
        }else if(id == R.id.nav_logout) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateNavDrawerInfo(User user, NavigationView navigationView) {
        final View header = navigationView.getHeaderView(0);
        TextView tvNavHeaderNameSurname = (TextView) header.findViewById(R.id.tvNavHeaderNameSurname);
        tvNavHeaderNameSurname.setText(user.getFirstName() + " " + user.getLastName());
        ImageView navProfileImage = (ImageView) header.findViewById(R.id.navProfileImage);
        Picasso.with(this).load(Constants.BASE_URL + "/user/photo/" + user.id).memoryPolicy(MemoryPolicy.NO_CACHE).placeholder(R.drawable.brain).into(navProfileImage);
    }

    private void logout() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(R.string.activity__alert_logout_title);

        alertDialogBuilder
                .setMessage(R.string.activity__alert_logout_body)
                .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container_layout);
                        if (baseFragment instanceof MainFragment) {
                            navigationView.getMenu().getItem(0).setChecked(true);
                        }
                        dialog.cancel();
                    }
                })
                .setPositiveButton(R.string.activity__alert_logout_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        User user = (User) JSON.fromJson(Persistence.getInstance().getPersistence().getString(Persistence.KEY_USER, ""), User.class);
                        if(user.getFacebookAccount() != null && user.getFacebookAccount())
                            LoginManager.getInstance().logOut();
                        Persistence.getInstance().getPersistence().removeValueForKey(Persistence.KEY_USER);
                        UI.clearBackstack(getSupportFragmentManager());
                        UI.replaceFragment(getSupportFragmentManager(), R.id.container_layout, LoginFragment.newInstance(), false, 0, 0);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_EXTERNAL_STORAGE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                } else {
                    Toast.makeText(this, "Оваа апликација нема да работи соодветно без оваа дозвола!", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {

        BaseFragment baseFragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container_layout);
        if(baseFragment instanceof GameFragment){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Прекин на играта");
            alertDialogBuilder
                    .setMessage("Дали сте сигурни?")
                    .setNegativeButton("Назад", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            UI.clearBackstack(getSupportFragmentManager());
                            UI.replaceFragment(getSupportFragmentManager(), R.id.container_layout, MainFragment.newInstance(), false, 0, 0);
                        }
                    });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else{
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }

            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentById(R.id.container_layout);
            if (fragment instanceof MainFragment) {
                navigationView.getMenu().getItem(0).setChecked(true);
            }
        }


    }
}
