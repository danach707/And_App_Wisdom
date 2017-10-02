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
    private String shortStory;
    private String email;
    private Uri photoUrl;
    private String gender;
    private String askedQuestionsUID;
    private String id;

    public User() {

    }

    public User(String username, String password, String fname, String lname, String age, String email, String gender, String shortStory, Uri photoUrl) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.age = age;
        this.email = email;
        this.photoUrl = photoUrl;
        this.gender = gender;
        this.shortStory = shortStory;
        this.askedQuestionsUID="";
    }

    public User(User other){
        this.username = other.username;
        this.password = other.password;
        this.fname = other.fname;
        this.lname = other.lname;
        this.age = other.age;
        this.email = other.email;
        this.photoUrl = other.photoUrl;
        this.gender = other.gender;
        this.shortStory = other.shortStory;
        this.askedQuestionsUID="";
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAskedQuestionsUID() {return askedQuestionsUID;}

    public void setAskedQuestionsUID(String askedQuestionsUID) {this.askedQuestionsUID = askedQuestionsUID;}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getShortStory() {return shortStory;}

    public void setShortStory(String shortStory) {this.shortStory = shortStory;}


}
