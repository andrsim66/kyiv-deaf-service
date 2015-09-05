package com.smartcity.kyivdeafservice.app.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.TextView;

import com.smartcity.kyivdeafservice.app.App;
import com.smartcity.kyivdeafservice.app.ApplicationSettings;
import com.smartcity.kyivdeafservice.app.R;
import com.smartcity.kyivdeafservice.app.utils.Logger;

public class DispatchActivity extends AppCompatActivity implements App.OperationChangeListener {

    private ProgressDialog dialog;
    private App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new ProgressDialog(DispatchActivity.this);

        app = ((App) getApplication());
        app.addOperationChangeListener(this);
        app.getSettings().load();
        app.onMainActivityCreated();
    }

    @Override
    public void onOperationChange(App.Operation state) {
        try {
            switch (state) {
                case Authorized:
                    dialog.dismiss();
                    String username = app.getSettings().get(ApplicationSettings.Username);
                    if (username != null) {
                        app.login(username);
                    } else {
                        Intent intent = new Intent(DispatchActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                case Error:
                    dialog.dismiss();
                    final AlertDialog.Builder popupBuilder = new AlertDialog.Builder(DispatchActivity.this);
                    TextView myMsg = new TextView(DispatchActivity.this);
                    myMsg.setText(state.getDescription());
                    myMsg.setGravity(Gravity.CENTER);
                    popupBuilder.setTitle("Error");
                    popupBuilder.setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            });
                    popupBuilder.setView(myMsg);

                    popupBuilder.show();
                case Processing:
                    dialog.setMessage(getResources().getString(R.string.loading));
                    dialog.show();
                    break;
                case LoggedIn:
                    dialog.dismiss();
                    Intent intent = new Intent(DispatchActivity.this, MainActivity.class);
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
