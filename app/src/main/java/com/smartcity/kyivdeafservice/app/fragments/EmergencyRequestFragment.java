package com.smartcity.kyivdeafservice.app.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.R;

/**
 * Created by voronsky on 30.09.15.
 */
public class EmergencyRequestFragment extends Fragment {

    private final static String ARG_PARAM1 = "param1";

    private int mViewId;
    private TextView tv_emergency_name;

    public static EmergencyRequestFragment newInstance(int id) {
        EmergencyRequestFragment fragment = new EmergencyRequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, id);
        fragment.setArguments(args);
        return fragment;
    }

    public EmergencyRequestFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mViewId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.emergency_form, container, false);

        initView(rootView);
        setViews();

        return rootView;
    }

    private void initView(View view) {
        tv_emergency_name = (TextView) view.findViewById(R.id.tv_emergency_name);
    }

    private void setViews() {
        switch (mViewId) {
            case R.id.rl_fire:
                tv_emergency_name.setText("Пожежна служба");
                break;
            case R.id.rl_police:
                tv_emergency_name.setText("Поліція");
                break;
            case R.id.rl_ambulance:
                tv_emergency_name.setText("Швидка допомога");
                break;
            case R.id.rl_gas:
                tv_emergency_name.setText("Газова служба");
                break;
        }
    }

}
