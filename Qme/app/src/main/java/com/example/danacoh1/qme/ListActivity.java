package com.example.danacoh1.qme;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ListActivity extends Activity {

    private final String TAG = getClass().getSimpleName();
    private ListView q_list_view;
    private ArrayList<Question> q_arraylist;
    private DatabaseReference database;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    //=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance().getReference();
        q_list_view = (ListView) findViewById(R.id.question_list);
        q_arraylist = new ArrayList<>();



        Question[] qset = Question.getInitQuestions();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        if (sharedPreferences.getBoolean(Constants.FIRST_RUN, true)) {
            addInitialDataToFirebase(qset);
            editor.putBoolean(Constants.FIRST_RUN, false).apply();
        }


        initList();
        q_list_view.setAdapter(new QuestionAdapter(this, q_arraylist));

        q_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                Gson gson = new Gson();
                Question q = (Question) parent.getItemAtPosition(position);
                String data = gson.toJson(q);
                intent.putExtra("Question Data", data);
                startActivity(intent);
            }
        });
    }

    //=============================================================================================
    @Override
    protected void onResume() {
        super.onResume();
        q_arraylist.clear();
        initList();
    }
    //=============================================================================================
    @Override
    protected void onStop() {
        super.onStop();

        editor.putBoolean(Constants.FIRST_RUN, false);
        editor.commit();

    }
    //=============================================================================================

    //reading all the questions from the database and adding hem to the list.
    private void initList() {

        database.addValueEventListener(new ValueEventListener() {
            //TODO - handle download time from server
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Question q = noteSnapshot.getValue(Question.class);
                    Log.d(TAG, q.toString());
                    q_arraylist.add(q);
                }
                ((QuestionAdapter) q_list_view.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

    }

    //=============================================================================================

    private void addInitialDataToFirebase(Question[] qset) {
        for (Question q : qset) {
            String key = database.push().getKey();
            q.setId(key);
            database.child(key).setValue(q);
        }
    }



}
