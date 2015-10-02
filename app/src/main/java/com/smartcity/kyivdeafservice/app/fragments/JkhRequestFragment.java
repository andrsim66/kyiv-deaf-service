package com.smartcity.kyivdeafservice.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.R;

public class JkhRequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private int mViewId;
    private TextView mTvMaster;

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
            mViewId = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jkh_request, container, false);

        initViews(view);
        setupViews();

        return view;
    }

    private void initViews(View view) {
        mTvMaster = (TextView) view.findViewById(R.id.tv_jkh_master);
    }

    private void setupViews(){
        switch (mViewId){
            case R.id.rl_electric_master:
                mTvMaster.setText("Виклик електрика");
                break;
            case R.id.rl_plumber:
                mTvMaster.setText("Виклик сантехніка");
                break;
            case R.id.rl_carpenter:
                mTvMaster.setText("Виклик столяра");
                break;
            case R.id.rl_other:
                mTvMaster.setText("Виклик працівника ЖКГ");
                break;
        }
    }

}
