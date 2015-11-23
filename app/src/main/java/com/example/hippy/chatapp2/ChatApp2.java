package com.example.hippy.chatapp2;

import android.app.Application;

import com.firebase.client.Firebase;


public class ChatApp2 extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
