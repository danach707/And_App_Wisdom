package com.example.danacoh1.qme;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
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

public class ListActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = getClass().getSimpleName();
    private ListView q_list_view;
    private ArrayList<Question> q_arraylist;
    private DatabaseReference database;
    private boolean filter;
    private User currUserLoggedIn;


    //=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance().getReference(Constants.TYPE_QUESTION);
        q_list_view = (ListView) findViewById(R.id.question_list);
        q_arraylist = new ArrayList<>();
        q_list_view.setLongClickable(true);

        Intent intent = getIntent();
        filter = intent.getBooleanExtra("filter", false);
        currUserLoggedIn = (User)DatabaseUtils.readFromDatabase(intent.getStringExtra("userId"), Constants.TYPE_USER);


        initList();
        q_list_view.setAdapter(new QuestionListAdapter(this, q_arraylist));

        q_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"inside short press");
                Intent intent = new Intent(getApplicationContext(), QuestionActivity.class);
                Gson gson = new Gson();
                Question q = (Question) parent.getItemAtPosition(position);
                String data = gson.toJson(q);
                intent.putExtra("Question Data", data);
                startActivity(intent);
            }
        });

        q_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                Log.d(TAG,"inside long press");
                DatabaseUtils.removeFromDatabase(q_arraylist.get(pos).getId(),Constants.TYPE_QUESTION);
                return true;
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

    }
    //=============================================================================================

    //reading all the questions from the database and adding hem to the list.
    private void initList() {

        database.addValueEventListener(new ValueEventListener() {
            //TODO - handle download time from server
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                q_arraylist.clear();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Question q = noteSnapshot.getValue(Question.class);

                    if(!filter) {
                        q_arraylist.add(q);
                    }
                    else{
                        if(q.getQuestionOwner().equals(DatabaseUtils.user.getEmail()))
                            q_arraylist.add(q);
                    }
                }

                ((QuestionListAdapter) q_list_view.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            if(DatabaseUtils.isUserConnected()) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        }

        else if (id == R.id.nav_questionslist) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_signout) {
            DatabaseUtils.signoutCurrentFirebaseUser();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //=============================================================================================

}
