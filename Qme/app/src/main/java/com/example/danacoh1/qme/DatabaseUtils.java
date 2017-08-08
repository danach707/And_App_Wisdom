package com.example.danacoh1.qme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

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
    private static DatabaseReference ref = database.getReference();
    private static Question quest;
    private static Comments comment;

    public static void writeToDatabase_question(Question data) {
        ref.child(data.getId()).setValue(data);
    }

    public static void writeToDatabase_comment(String question_id, String comment) {
        Question q = readQuestionFromDatabase(question_id);
        String key = ref.child(question_id).child(q.getComments()
                .getId())
                .push()
                .getKey();
        ref.child(key).setValue(comment);
    }

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

    public static void addInitialDataToFirebase(Question qset) {
        String key = ref.push().getKey();
        qset.setId(key);
        ref.child(key).setValue(qset);
    }


    public static void addInitialDataToFirebase(Question[] qset) {
        for (Question q : qset) {
            String key = ref.push().getKey();
            q.setId(key);
            ref.child(key).setValue(q);
        }
    }

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
}
