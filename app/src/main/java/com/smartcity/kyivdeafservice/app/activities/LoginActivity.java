package com.smartcity.kyivdeafservice.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.utils.Logger;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, App.OperationChangeListener {

    private static final int USERNAME_LIMIT = 200;

    private App app;

    private ProgressDialog dialog;

    private EditText mEtOovooLoginId;
    private Button mBLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(LoginActivity.this);

        app = ((App) getApplication());
        app.addOperationChangeListener(this);

        initViews();
        setupViews();
    }

    private void initViews() {
        mEtOovooLoginId = (EditText) findViewById(R.id.et_oovoo_login_id);
        mBLogin = (Button) findViewById(R.id.b_login);
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

        ((App) getApplication()).login(mEtOovooLoginId.getText().toString());
    }

    public void showErrorMessageBox(String title, String msg) {
        try {
            AlertDialog.Builder popupBuilder = new AlertDialog.Builder(LoginActivity.this);
            TextView myMsg = new TextView(LoginActivity.this);
            myMsg.setText(msg);
            myMsg.setGravity(Gravity.CENTER);
            popupBuilder.setTitle(title);
            popupBuilder.setPositiveButton(android.R.string.ok, null);
            popupBuilder.setView(myMsg);

            popupBuilder.show();
        } catch (Exception e) {
        }
    }

    @Override
    public void onOperationChange(App.Operation state) {
        try {
            switch (state) {
                case Processing:
                    dialog.setMessage(getResources().getString(R.string.loading));
                    dialog.show();
                    break;
                case LoggedIn:
                    dialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    break;
            }
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
        }
    }
}
