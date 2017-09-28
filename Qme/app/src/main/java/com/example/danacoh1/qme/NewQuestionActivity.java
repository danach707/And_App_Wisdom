package com.example.danacoh1.qme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

public class NewQuestionActivity extends AppCompatActivity {

    private DatabaseReference database;
    private Button submit;
    private EditText question;
    private Spinner types;
    private ArrayAdapter<String> questionTypesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        question = (EditText)findViewById(R.id.Question);
        submit = (Button) findViewById(R.id.submit);
        types = (Spinner) findViewById(R.id.questionTypes);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quest = ""+question.getText();
                String qtype = ""+types.getSelectedItem();
                if(isValid()){
                    Question q = new Question(quest,qtype);
                    System.out.println("  ----------------------  "+ quest + " IM NOT NULL!!");
                    DatabaseUtils.writeToDatabase(q,UserProfileActivity.currUserLogged Constants.TYPE_QUESTION);
                    Toast.makeText(getApplicationContext(), "Question submitted!" , Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "missing fields" , Toast.LENGTH_LONG).show();
                }

            }
        });




    }
    private boolean isValid(){
        if(question.getText().length() > 0)
            return true;
        return false;
    }
}
