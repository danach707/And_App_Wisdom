package com.example.danacoh1.qme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceIdReceiver;

public class NewQuestionActivity extends AppCompatActivity {

    private DatabaseReference database;
    Button submit;
    EditText question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        question = (EditText)findViewById(R.id.Question);
        submit = (Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quest = ""+question.getText();
                if(quest.length() != 0){
                    Question q = new Question(quest);
                    FirebaseUtils.writeToDatabase(q);
                }
            }
        });

    }
}
