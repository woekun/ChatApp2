package com.example.hippy.chatapp2.ui.activities;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;

import com.example.hippy.chatapp2.core.Commands;
import com.example.hippy.chatapp2.core.appService;
import com.firebase.client.Firebase;

public class BaseActivity extends AppCompatActivity implements ServiceConnection {

    protected Firebase firebase;
    protected ProgressDialog progressDialog;
    private appService.ServiceInterface serviceInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplicationContext().bindService(new Intent(this, appService.class), this, BIND_AUTO_CREATE);
        firebase = Commands.initFireBase();
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (appService.class.getName().equals(componentName.getClassName())) {
            serviceInterface = (appService.ServiceInterface) iBinder;
            onServiceConnected();
        }
    }


    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (appService.class.getName().equals(componentName.getClassName())) {
            serviceInterface = null;
            onServiceDisconnected();
        }
    }

    protected void onServiceConnected() {
        // for subclasses
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected appService.ServiceInterface getSinchServiceInterface() {
        return serviceInterface;
    }
}
