package com.smartcity.kyivdeafservice.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartcity.kyivdeafservice.app.R;

public class JkhRequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private String mViewId;


    public static JkhRequestFragment newInstance(int id) {
        JkhRequestFragment fragment = new JkhRequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    public JkhRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mViewId = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jkh_request, container, false);
        return view;
    }


}
