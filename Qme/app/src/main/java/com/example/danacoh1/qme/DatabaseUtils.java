package com.example.danacoh1.qme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

/**
 * Created by danacoh1 on 6/3/2017.
 */

public class DatabaseUtils {

    //static instance of firebase database
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static DatabaseReference ref = database.getReference("server/saving-data/qme-a00a6");
    private static Question quest;
    private static Comments comment;

    //==============================================================================================//

    public static void writeToDatabase_question(Question data) {
        data.setQuestionOwner(user.getEmail());
        String key = ref.child("Questions").push().getKey();
        data.setId(key);
        ref.child(key).setValue(data);

    }

    //==============================================================================================//

    public static void addDataToQuestionFirebase_question(Question data) {
        ref.child("Questions").child(data.getId()).setValue(data);
    }

    //==============================================================================================//

    public static void writeToDatabase_comment(String question_id, String comment) {
        Question q = readQuestionFromDatabase(question_id);
        String key = ref.child("Questions").child(question_id).child(q.getComments()
                .getId())
                .push()
                .getKey();
        ref.child(key).setValue(comment);
    }

    //==============================================================================================//

    public static Question readQuestionFromDatabase(final String id) {
        ref = ref.child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quest = dataSnapshot.getValue(Question.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return quest;
    }

    //==============================================================================================//

    public static Comments readCommentFromDatabase(final String Qid, final String Cid) {
        ref = ref.child(Qid).child(Cid);
        // Attach a listener to read the data at our posts reference
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comment = dataSnapshot.getValue(Comments.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return comment;
    }

    //==============================================================================================//

    public static void writeToDatabase_user(User data) {
        String key = ref.child("Users").push().getKey();
        data.setUid(key);
        ref.child(key).setValue(data);

    }

    //==============================================================================================//

    public static void addDataToUserFirebase_question(User data) {
        ref.child("Users").child(data.getUid()).setValue(data);
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
