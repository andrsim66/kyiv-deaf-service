package com.smartcity.kyivdeafservice.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.kyivdeafservice.app.R;

public class EmergencyFragment extends Fragment {

    public static EmergencyFragment newInstance() {
        return new EmergencyFragment();
    }

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emergency_fragment, container, false);
        return view;
    }
}
