package com.example.danacoh1.qme;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;


/**
 * Created by Qme team on 6/3/2017.
 */

public class DatabaseUtils{

    private static final String TAG = "DatabaseUtils";
    //static instance of firebase database
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static DatabaseReference ref = database.getReference();
    private static Question quest;
    private static Comment comment;
    private static User users;
    private static boolean valExistInDatabase;


    //==============================================================================================//

    public static void writeToDatabase(Object data, User user, String type) {
        String key = "";
        try {
            switch (type) {
                case Constants.TYPE_QUESTION:
                    Question q = (Question) data;
                    q.setQuestionOwner(user.getEmail());
                    key = ref.child(type).push().getKey();
                    q.setId(key);
                    break;
                case Constants.TYPE_USER:
                    User u = (User) data;
                    key = ref.child(type).push().getKey();
                    u.setId(key);
                    break;
            }
            ref.child(type).child(key).setValue(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==============================================================================================//

    public static void addDataToChildFirebase(Object data,Object extraData, String type) {
        String key = "";
        try {
            switch (type) {
                case Constants.TYPE_QUESTION:
                    Question q = (Question) data;
                    ref.child(type).child(q.getId()).setValue(data);
                    break;
                case Constants.TYPE_USER:
                    User u = (User) data;
                    ref.child(type).child(u.getId()).setValue(data);
                    break;
                case Constants.TYPE_COMMENT:
                    Question c = (Question) data;
                    Comment com = (Comment)extraData;
                    key = ref.child(Constants.TYPE_QUESTION).child(c.getId()).child(Constants.TYPE_COMMENT).push().getKey();
                    com.setId(key);
                    ref.child(Constants.TYPE_QUESTION).child(c.getId()).child(Constants.TYPE_COMMENT).child(key).setValue(com);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==============================================================================================//

    public static void removeFromDatabase(String key, User user, String type) {
        ref.child(type).child(key).removeValue();
    }

    //==============================================================================================//

//    public static void writeToDatabase_comment(String question_id, String comment) {
//        Question q = readQuestionFromDatabase(question_id);
//        String key = ref.child(question_id).child(q.getComments()
//                .getId())
//                .push()
//                .getKey();
//        ref.child(key).setValue(comment);
//    }

    //==============================================================================================//

    public static Object readFromDatabase(final String id, final String type) {
        ref = ref.child(type).child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals(Constants.TYPE_QUESTION))
                    quest = dataSnapshot.getValue(Question.class);
                if (type.equals(Constants.TYPE_COMMENT))
                    comment = dataSnapshot.getValue(Comment.class);
                if (type.equals(Constants.TYPE_USER))
                    users = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        if (type.equals(Constants.TYPE_QUESTION))
            return quest;
        else if (type.equals(Constants.TYPE_COMMENT))
            return comment;
        else
            return users;
    }

    //==============================================================================================//

    public static boolean isUserConnected() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null)
            return true;
        return false;

    }

    //==============================================================================================//

    public static void readAllCommentQuestion(String id, String type, ListView list, CustomList adapter){



    }

    //==============================================================================================//

    public static void signoutCurrentFirebaseUser() {
        FirebaseAuth.getInstance().signOut();
    }

    //==============================================================================================//

    public static void buildMessage(Context context, String field) {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(field);
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    //==============================================================================================//

    public static boolean isExistInDatabase(final String data, final String field, String type){
        ref = ref.child(type);

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    String val = noteSnapshot.child(field).getValue(String.class);

                    if(val != null){
                        Log.d(TAG, "----Val: " + val + "----");
                        if(val.equals(data)) {
                            valExistInDatabase = true;
                            break;
                        }
                    }else{
                        Log.d(TAG, "----Child is not exist on database! check parameters sent----");
                    }

                }
                valExistInDatabase = false;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

        return valExistInDatabase;
    }

    public static String transformToString(Object obj){
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    public static Object transformToObject(String data, String type){
        Gson gson = new Gson();
        if(type.equals(Constants.TYPE_COMMENT))
            return gson.fromJson(data,Comment.class);
        if(type.equals(Constants.TYPE_USER))
            return gson.fromJson(data,User.class);
        if(type.equals(Constants.TYPE_QUESTION))
            return gson.fromJson(data,Question.class);
        if(type.equals(Constants.TYPE_STRING_LINKED))
            return gson.fromJson(data,Constants.STRING_LIST_TYPE);
        return null;
    }




}
