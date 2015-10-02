package com.smartcity.kyivdeafservice.app.fragments;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.utils.Logger;

public class LoginFragment extends Fragment implements View.OnClickListener {
//    private OnFragmentInteractionListener mListener;

    private static final int USERNAME_LIMIT = 200;

    private App app;

    private ProgressDialog dialog;

    private EditText mEtOovooLoginId;
    private Button mBLogin;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        initViews(view);
        setupViews();

        return view;
    }

    private void initViews(View view) {
        mEtOovooLoginId = (EditText) view.findViewById(R.id.et_oovoo_login_id);
        mBLogin = (Button) view.findViewById(R.id.b_login);
    }

    private void setupViews() {
        mBLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.b_login) {
            onLoginClick();
        }
    }

    public void onLoginClick() {
        String username = mEtOovooLoginId.getText().toString();
        if (username.isEmpty()) {
//            Toast.makeText(LoginActivity.this, R.string.enter_username_toast, Toast.LENGTH_LONG).show();

            Logger.e("onLogin username is empty");
            return;
        }

        if (!username.matches("^([a-zA-Z0-9-_%. ])+$") || username.length() > USERNAME_LIMIT) {
            showErrorMessageBox(getString(R.string.join_session), getString(R.string.wrong_username_id));

            return;
        }

//        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mEtOovooLoginId.getWindowToken(), 0);

        ((App) getActivity().getApplication()).login(mEtOovooLoginId.getText().toString());
    }

    public void showErrorMessageBox(String title, String msg) {
        try {
            AlertDialog.Builder popupBuilder = new AlertDialog.Builder(getActivity());
            TextView myMsg = new TextView(getActivity());
            myMsg.setText(msg);
            myMsg.setGravity(Gravity.CENTER);
            popupBuilder.setTitle(title);
            popupBuilder.setPositiveButton(android.R.string.ok, null);
            popupBuilder.setView(myMsg);

            popupBuilder.show();
        } catch (Exception e) {
        }
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        public void onFragmentInteraction(Uri uri);
//    }

}
