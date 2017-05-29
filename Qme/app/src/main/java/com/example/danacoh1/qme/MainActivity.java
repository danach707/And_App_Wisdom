package com.example.danacoh1.qme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    TextView question, y_c, n_c;
    Button y_b, n_b, next_q;


    DatabaseReference myRef;

    private DatabaseReference mDatabase;
    private LinkedList<DatabaseReference> runQ;
    DatabaseReference q;
    private SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences.Editor editor;

        y_b = (Button)findViewById(R.id.yes_button);
        n_b = (Button)findViewById(R.id.no_button);
        question = (TextView)findViewById(R.id.Question);
        y_c = (TextView)findViewById(R.id.yes_count);
        n_c = (TextView)findViewById(R.id.no_count);

        callAlertDialog();

        Question[] qset = getQuestions();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            addInitialDataToFirebase(qset);;
            editor.putBoolean(Constants.FIRST_RUN, false).commit();
        }



        y_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final LinkedList<Question> ques = new LinkedList<Question>();
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                            Question q = noteSnapshot.getValue(Question.class);
                            if(q.getQuestion().charAt(0) == '5')
                                ques.add(q);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(TAG, databaseError.getMessage());
                    }
                });
                if(!ques.isEmpty()){
                    question.setText(ques.get(0).getQuestion());
                }
            }
        });


    }

    private void callAlertDialog() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Write your message here.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static Question[] getQuestions() {

        String [] questions_set;
        questions_set = new String[10];
        Question[] qset;
        qset = new Question[10];
        questions_set[0] = "Do you like camels?";
        questions_set[1] = "Do you like smartphone?";
        questions_set[2] = "Do you think pasta is tasty?";
        questions_set[3] = "Do you like the gui of the app?";
        questions_set[4] = "Do you love shrimps?";
        questions_set[5] = "Do you want to be Dana Cohen?";
        questions_set[6] = "Are you going to Britney spears concert?";
        questions_set[7] = "Can you lift a Woman with one hand?";
        questions_set[8] = "Are you going to the beach today?";
        questions_set[9] = "Is Lior Sapir samrt (NO)?";

        for(int i = 0; i < 10; i++){
            qset[i] = new Question(questions_set[i]);
        }
        return qset;
    }

    private void addInitialDataToFirebase(Question [] qset) {
        for (Question q: qset){
            String key = mDatabase.push().getKey();
            q.setId(key);
            mDatabase.child(key).setValue(q);
        }
    }

}
