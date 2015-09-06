package com.smartcity.kyivdeafservice.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.adapters.InterpretersAdapter;
import com.smartcity.kyivdeafservice.app.objects.Interpreter;

import java.util.ArrayList;

public class InterpreterFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mLvInterpreters;
    private InterpretersAdapter mAdapter;
    private ArrayList<Interpreter> mInterpreters;

    private App app;

    private OnFragmentInteractionListener mListener;

    public static InterpreterFragment newInstance() {
        return new InterpreterFragment();
    }

    public InterpreterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_interpreter, container, false);

        app = (App) getActivity().getApplication();

        initViews(view);
        addInterpreters();
        setupViews();

        return view;
    }

    private void initViews(View view) {
        mLvInterpreters = (ListView) view.findViewById(R.id.lv_interpreters);
    }

    private void setupViews() {
        mAdapter = new InterpretersAdapter(getActivity(), R.layout.item_interpreter, mInterpreters);
        mLvInterpreters.setAdapter(mAdapter);
        mLvInterpreters.setOnItemClickListener(this);
    }

    private void addInterpreters() {
        if (app.getInterpreters() == null || app.getInterpreters().size() == 0) {
            mInterpreters = new ArrayList<>();

            Interpreter interpreter = new Interpreter();
            interpreter.setName("Любов Юрчук");
            interpreter.setPricePerHour("$5/год");
            interpreter.setPhotoUrl("http://i.imgur.com/vQnBNok.png");
            interpreter.setBigPhotoUrl("http://i.imgur.com/0BzRSdh.png");
            interpreter.setWorkSchedule("Вт., Чт. 14.00 - 18.00");
            interpreter.setIsActive(true);
            mInterpreters.add(interpreter);

            interpreter = new Interpreter();
            interpreter.setName("Меган Фокс");
            interpreter.setPricePerHour("$7/год");
            interpreter.setPhotoUrl("http://i.imgur.com/5gJYCiI.png");
            interpreter.setBigPhotoUrl("http://i.imgur.com/5gJYCiI.png");
            interpreter.setWorkSchedule("Вт.-Чт. 9.00 - 18.00");
            interpreter.setIsActive(true);
            mInterpreters.add(interpreter);

            interpreter = new Interpreter();
            interpreter.setName("Максим Яковенко");
            interpreter.setPricePerHour("$6/год");
            interpreter.setPhotoUrl("http://i.imgur.com/UGMV6eh.png");
            interpreter.setBigPhotoUrl("http://i.imgur.com/pyZtF3K.png");
            interpreter.setWorkSchedule("Пн., Пт. 8.00 - 18.00");
            interpreter.setIsActive(false);
            mInterpreters.add(interpreter);

            interpreter = new Interpreter();
            interpreter.setName("Тамара Клименко");
            interpreter.setPricePerHour("$5/год");
            interpreter.setPhotoUrl("http://i.imgur.com/WVlCfQK.png");
            interpreter.setBigPhotoUrl("http://i.imgur.com/OcPwHVg.png");
            interpreter.setWorkSchedule("Ср., Пт. 10.00 - 14.00");
            interpreter.setIsActive(false);
            mInterpreters.add(interpreter);

            interpreter = new Interpreter();
            interpreter.setName("Вікторія Пастушенко");
            interpreter.setPricePerHour("$7/год");
            interpreter.setPhotoUrl("http://i.imgur.com/8VpCIrA.png");
            interpreter.setBigPhotoUrl("http://i.imgur.com/h6QxOpA.png");
            interpreter.setWorkSchedule("Сб. 10.00 - 18.00");
            interpreter.setIsActive(true);
            mInterpreters.add(interpreter);

            app.setInterpreters(mInterpreters);
        } else {
            mInterpreters = app.getInterpreters();
        }
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (mListener != null) {
            mListener.onInterpreterSelect(position);
        }
    }

    public interface OnFragmentInteractionListener {
        void onInterpreterSelect(int position);
    }

}
