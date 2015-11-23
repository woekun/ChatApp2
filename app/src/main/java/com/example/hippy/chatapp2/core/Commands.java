package com.example.hippy.chatapp2.core;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.hippy.chatapp2.model.Chat;
import com.example.hippy.chatapp2.model.User;
import com.example.hippy.chatapp2.ui.activities.LoginActivity;
import com.example.hippy.chatapp2.ui.activities.MainActivity;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Commands {

    public static Firebase initFireBase() {
        return new Firebase("https://sweltering-fire-6444.firebaseio.com/");
    }

    public static void createUserCommand(final ProgressDialog progressDialog, final Activity activity,
                                         final String email, final String password) {
        initFireBase().createUser(email, password, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                saveObjectToDatabase("users", new User(email, "user1"));
                logInCommand(progressDialog, activity, email, password);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(activity, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERRORs", firebaseError.getMessage());
            }
        });
    }

    public static void logInCommand(final ProgressDialog progressDialog, final Activity activity,
                                    String email, String password) {
        initFireBase().authWithPassword(email, password, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                progressDialog.dismiss();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(activity, firebaseError.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ERROR", firebaseError.getMessage());
            }
        });
    }

    private static void saveObjectToDatabase(String child, Object object) {
        initFireBase().child(child).push().setValue(object);
    }

    public static void logOutCommand(Activity activity) {
        initFireBase().unauth();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public static void changePasswordCommand(final Activity activity, String email,
                                             String oldPassword, String newPassword) {
        initFireBase().changePassword(email, oldPassword, newPassword, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "U password has changed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(activity, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void resetPasswordCommand(final Activity activity, String email) {
        initFireBase().resetPassword(email, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "OK, check ur email", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(activity, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                Log.d("ERROR", firebaseError.getMessage());
            }
        });
    }

    public static void addFriendsCommand(String child, String friendId) {
        initFireBase().child(child);
    }

    public static void deleteFriendCommand() {

    }

    public static void createGroupCommand() {

    }

    public static void deleteGroupCommand() {

    }

    public static void sendMessageCommand(String recipientUserId, String textBody) {
        saveObjectToDatabase("history/messages", new Chat(textBody, "user", recipientUserId));
    }

    public static List<User> loadAllUser(final Activity activity) {
        final List<User> collector = new ArrayList<>();

        initFireBase().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() == 0)
                    Toast.makeText(activity, "U have no friend!", Toast.LENGTH_SHORT).show();
                else
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);
                        collector.add(user);
                        Log.d("TEST", snapshot.getKey() + " " + user.getDisplayName() + " " + user.getEmail());
                    }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                if (!collector.isEmpty())
                    collector.isEmpty();
            }
        });
        return collector;
    }

    public static void loadHistoryConversationCommand() {
        initFireBase().child("history/messages")
                .limitToLast(50).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void loadContactsCommand() {

    }

    public static void loadUserInfomationsCommand() {
        initFireBase().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

}
