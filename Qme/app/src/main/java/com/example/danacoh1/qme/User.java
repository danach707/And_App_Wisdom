package com.example.danacoh1.qme;

import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;

/**
 * Created by danac on 23/09/2017.
 */

public class User {

    private String username;
    private String password;
    private String fname;
    private String lname;
    private String age;
    private String email;
    private Uri photoUrl;
    private String uid;
    private LinkedList<String> askedQuestionsUID;

    public User(String username, String password, String fname, String lname, String age, String email, Uri photoUrl) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.email = email;
        this.photoUrl = photoUrl;
        askedQuestionsUID = new LinkedList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(Uri photoUrl) {
        this.photoUrl = photoUrl;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public LinkedList<String> getAskedQuestionsUID() {
        return askedQuestionsUID;
    }

    public void setAskedQuestionsUID(LinkedList<String> askedQuestionsUID) {
        this.askedQuestionsUID = askedQuestionsUID;
    }

}
