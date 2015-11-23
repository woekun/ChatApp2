package com.example.hippy.chatapp2.ui.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.hippy.chatapp2.R;
import com.example.hippy.chatapp2.core.Commands;
import com.example.hippy.chatapp2.helper.utils.Dialogs;

public class LoginActivity extends BaseActivity {

    private EditText edtId;
    private EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        edtId = (EditText) findViewById(R.id.edtId);
        edtPass = (EditText) findViewById(R.id.edtPass);
        progressDialog = Dialogs.getProgressDialog(this);

        findViewById(R.id.btnSignUp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtId.getText().toString();
                String password = edtPass.getText().toString();
                Commands.createUserCommand(progressDialog, LoginActivity.this, email, password);
            }
        });

        findViewById(R.id.btnSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtId.getText().toString();
                String password = edtPass.getText().toString();
                Commands.logInCommand(progressDialog, LoginActivity.this, email, password);
            }
        });
    }
}
