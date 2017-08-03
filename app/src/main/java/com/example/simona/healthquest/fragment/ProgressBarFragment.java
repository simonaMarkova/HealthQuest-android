package com.example.simona.healthquest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.simona.healthquest.R;

/**
 * Created by Simona on 7/24/2017.
 */

public class ProgressBarFragment extends BaseFragment {
    private View rootView;
    private ProgressBar progressBar;

    public static ProgressBarFragment newInstance(){
        return new ProgressBarFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.progres_dialog,container,false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar) ;
        progressBar.getIndeterminateDrawable().setColorFilter(0xFFFFFFFF,android.graphics.PorterDuff.Mode.MULTIPLY);

        return rootView;
    }
}
