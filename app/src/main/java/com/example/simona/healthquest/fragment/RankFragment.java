package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.adapter.RecyclerUsersAdapter;
import com.example.simona.healthquest.model.User;
import com.example.simona.healthquest.network.RetrofitManager;
import com.example.simona.healthquest.util.PointsComparator;
import com.example.simona.healthquest.util.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by user on 06.8.2017.
 */

public class RankFragment extends BaseFragment{

    private View rootView;
    private RelativeLayout usersRanking;
    private List<User> users;
    private RecyclerView rvUsersRanking;
    private RecyclerUsersAdapter usersAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_rank_list, container, false);
        rvUsersRanking = (RecyclerView) rootView.findViewById(R.id.rvUsersRanking);
        users = new ArrayList<>();
        usersAdapter = new RecyclerUsersAdapter(users, context);
        RecyclerView.LayoutManager layoutManager =  new LinearLayoutManager(context);
        rvUsersRanking.setLayoutManager(layoutManager);
        rvUsersRanking.setAdapter(usersAdapter);

        prepareUsersData();

        return rootView;
    }

    private void prepareUsersData()
    {
        RetrofitManager.getInstance().getRetrofitService().getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    users = response.body();
                    Collections.sort(users, new PointsComparator());
                    usersAdapter.updateUsers(users);
                }else {
                    Toast.makeText(context,  R.string.game_error, Toast.LENGTH_LONG).show();
                    UI.clearBackstack(supportFragmentManager);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(context,  R.string.game_error, Toast.LENGTH_LONG).show();
                UI.clearBackstack(supportFragmentManager);
            }
        });
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static RankFragment newInstance() {
        return new RankFragment();
    }

}
