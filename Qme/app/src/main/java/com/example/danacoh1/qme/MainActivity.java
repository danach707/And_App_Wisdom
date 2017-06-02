package com.example.danacoh1.qme;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    TextView question, y_c, n_c;
    Button y_b, n_b, next_q;


    DatabaseReference myRef;

    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Question questionData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        y_b = (Button)findViewById(R.id.yes_button);
        n_b = (Button)findViewById(R.id.no_button);
        question = (TextView)findViewById(R.id.Question);
        y_c = (TextView)findViewById(R.id.yes_count);
        n_c = (TextView)findViewById(R.id.no_count);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Question Data");

        Gson gson = new Gson();
        questionData = gson.fromJson(data, Question.class);

        question.setText(questionData.getQuestion());

        //TODO add error handling


     //   callAlertDialog();

        Question[] qset = Question.getInitQuestions();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            addInitialDataToFirebase(qset);;
            editor.putBoolean(Constants.FIRST_RUN, false).apply();
        }



        y_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setYes_counter(questionData.getYes_counter()+1);
                String tmp = "" + questionData.getYes_counter();
                y_c.setText(tmp);
            }
        });

        n_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setNo_counter(questionData.getNo_counter()+1);
                String tmp = "" + questionData.getNo_counter();
                n_c.setText(tmp);
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

    private void addInitialDataToFirebase(Question [] qset) {
        for (Question q: qset){
            String key = mDatabase.push().getKey();
            q.setId(key);
            mDatabase.child(key).setValue(q);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        editor.putBoolean(Constants.FIRST_RUN, false);
        editor.commit();

    }
}
