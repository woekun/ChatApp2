package com.example.hippy.chatapp2.model;

public class User {

    private String displayName;
    private String email;
//    private String avatar;

    @SuppressWarnings("unused")
    public User() {
    }

    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

//    public User(String email, String displayName, Bitmap avatar) {
//        this.email = email;
//        this.displayName = displayName;
//        this.avatar = ConvertImage.bitmapToString(avatar);
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

//    public String getAvatar() {
//        return avatar;
//    }
//
//    public void setAvatar(String avatar) {
//        this.avatar = avatar;
//    }
}
