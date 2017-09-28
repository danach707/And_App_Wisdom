package com.example.danacoh1.qme;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.LauncherApps;
import android.media.projection.MediaProjection;
import android.os.Handler;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by danacoh1 on 6/3/2017.
 */

public class DatabaseUtils{

    private static final String TAG = "DatabaseUtils";
    //static instance of firebase database
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static DatabaseReference ref = database.getReference();
    private static Question quest;
    private static Comments comment;
    private static User users;

    //==============================================================================================//

    public static void writeToDatabase(Object data, User user, String type) {
        String key = "";
        try {
            switch (type) {
                case Constants.TYPE_QUESTION:
                    Question q = (Question) data;
                    q.setQuestionOwner(user.getEmail());
                    user.getAskedQuestionsUID().add(q.getId());
                    key = ref.child(type).push().getKey();
                    q.setId(key);
                    break;
                case Constants.TYPE_USER:
                    User u = (User) data;
                    key = ref.child(type).push().getKey();
                    u.setId(key);
                    break;
                case Constants.TYPE_COMMENT:
                    Comments c = (Comments) data;
                    ref.child(type).push().getKey();
                    c.setId(key);
                    break;
            }
            ref.child(type).child(key).setValue(data);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //==============================================================================================//

    public static void addDataToChildFirebase(Object data, String type) {
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
                    Comments c = (Comments) data;
                    ref.child(type).child(c.getId()).setValue(data);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //==============================================================================================//

    public static void removeFromDatabase(String key, User user, String type) {
        user.getAskedQuestionsUID().remove(key);
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
                    comment = dataSnapshot.getValue(Comments.class);
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

}
