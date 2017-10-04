package com.example.danacoh1.qme;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * Created by danacoh1 on 5/26/2017.
 */

public class Constants {

    final static public int LEGAL_AGE = 13;
    final static public int PASSWORD_MIN_LEN = 6;



    //----Database Related----//

    final static public String TYPE_USER = "Users";
    final static public String TYPE_QUESTION = "Questions";
    final static public String TYPE_COMMENT = "Comment";
    final static public String TYPE_STRING_LINKED = "LinkedList<String>";
    final static public Type STRING_LIST_TYPE = new TypeToken<LinkedList<String>>(){}.getType();

    final static public String CHILD_USERNAME = "username";

    final static public int MAX_LENGTH_COMMENT = 100;





    final static public int GET_FROM_GALLERY = 1;



}
