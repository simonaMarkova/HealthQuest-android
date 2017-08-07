package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.adapter.RecyclerUsersAdapter;

import java.util.ArrayList;

/**
 * Created by user on 07.8.2017.
 */

public class AboutFragment extends BaseFragment{

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_about, container, false);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static AboutFragment newInstance()
    {
        return new AboutFragment();
    }

}
