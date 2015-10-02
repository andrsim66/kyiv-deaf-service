package com.smartcity.kyivdeafservice.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartcity.kyivdeafservice.app.R;

public class EmergencyFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RelativeLayout rl_fire;
    private RelativeLayout rl_police;
    private RelativeLayout rl_ambulance;
    private RelativeLayout rl_gas;

    private OnEmergencyFragmentListener mListener;

    public static EmergencyFragment newInstance() {
        return new EmergencyFragment();
    }

    public EmergencyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emergency, container, false);

        initViews(rootView);
        setupViews();

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnEmergencyFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnEmergencyFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initViews(View view) {
        rl_fire = (RelativeLayout) view.findViewById(R.id.rl_fire);
        rl_police = (RelativeLayout) view.findViewById(R.id.rl_police);
        rl_ambulance = (RelativeLayout) view.findViewById(R.id.rl_ambulance);
        rl_gas = (RelativeLayout) view.findViewById(R.id.rl_gas);
    }

    private void setupViews() {
        rl_fire.setOnClickListener(this);
        rl_police.setOnClickListener(this);
        rl_ambulance.setOnClickListener(this);
        rl_gas.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            mListener.onEmergencyRequest(v.getId());
        }
    }

    public interface OnEmergencyFragmentListener {
        void onEmergencyRequest(int id);
    }
}
