package com.smartcity.kyivdeafservice.app.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.smartcity.kyivdeafservice.app.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JkhFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JkhFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RelativeLayout mRlElectricMaster;
    private RelativeLayout mRlCarpenter;
    private RelativeLayout mRlPlumber;
    private RelativeLayout mRlOther;

//    public static JkhFragment newInstance(String param1, String param2) {
//        JkhFragment fragment = new JkhFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    private OnFragmentInteractionListener mListener;

    public static JkhFragment newInstance() {
        return new JkhFragment();
    }

    public JkhFragment() {
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
        View view = inflater.inflate(R.layout.jkh_fragment, container, false);

        initViews(view);
        setupViews();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void initViews(View view) {
        mRlElectricMaster = (RelativeLayout) view.findViewById(R.id.rl_electric_master);
        mRlCarpenter = (RelativeLayout) view.findViewById(R.id.rl_carpenter);
        mRlPlumber = (RelativeLayout) view.findViewById(R.id.rl_plumber);
        mRlOther = (RelativeLayout) view.findViewById(R.id.rl_other);
    }

    private void setupViews() {
        mRlElectricMaster.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onJkhRequest(view.getId());
        }
    }

    public interface OnFragmentInteractionListener {

        void onJkhRequest(int id);
    }
}
