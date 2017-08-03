package com.example.simona.healthquest.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.simona.healthquest.R;
import com.example.simona.healthquest.activity.HealthQuestActivity;

/**
 * Created by Simona on 7/17/2017.
 */

public class BaseFragment extends Fragment {
    protected OnFragmentInteractionListener mListener;
    protected Context context;
    protected FragmentManager supportFragmentManager;
    protected ActionBar supportActionBar;
    protected Toolbar toolbar;
    protected HealthQuestActivity activity;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onAttach(Context context) {
        this.context = context;
        supportFragmentManager = getActivity().getSupportFragmentManager();
        supportActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity = (HealthQuestActivity) getActivity();

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }


    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(BaseFragment source, Bundle message, Class destination);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
