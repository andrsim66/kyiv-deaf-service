package com.smartcity.kyivdeafservice.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.objects.Interpreter;
import com.smartcity.kyivdeafservice.app.utils.UtilsDevice;

public class InterpreterRequestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";

    private int mPosition;

    private ImageView mIvPhoto;
    private TextView mTvName;
    private TextView mTvPrice;
    private TextView mTvSchedule;

    private App app;
    private Interpreter mInterpreter;

    public static InterpreterRequestFragment newInstance(int position) {
        InterpreterRequestFragment fragment = new InterpreterRequestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, position);
        fragment.setArguments(args);
        return fragment;
    }

    public InterpreterRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interpreter_request, container, false);

        app = (App) getActivity().getApplication();
        mInterpreter = app.getInterpreters().get(mPosition);

        initViews(view);
        setupViews();

        return view;
    }

    private void initViews(View view) {
        mIvPhoto = (ImageView) view.findViewById(R.id.iv_interpreter_photo_big);
        mTvName = (TextView) view.findViewById(R.id.tv_interpreter_name_big);
        mTvPrice = (TextView) view.findViewById(R.id.tv_interpreter_price_big);
        mTvSchedule = (TextView) view.findViewById(R.id.tv_interpreter_schedule_big);
    }

    private void setupViews() {
        UtilsDevice.loadImage(mInterpreter.getBigPhotoUrl(), mIvPhoto, mIvPhoto.getWidth());
        mTvName.setText(mInterpreter.getName());
        mTvPrice.setText(mInterpreter.getPricePerHour());
        mTvSchedule.setText(mInterpreter.getWorkSchedule());
    }
}
