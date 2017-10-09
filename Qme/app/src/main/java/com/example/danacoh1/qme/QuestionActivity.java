package com.example.danacoh1.qme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;

public class QuestionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    final private String TAG = getClass().getSimpleName();

    TextView questionView, y_c, n_c;
    Button y_b, n_b, add_comment_action;
    EditText add_comment_text;

    private SharedPreferences.Editor editor;
    private Question questionData;

    private ArrayList<Comment> comments;
    private ListView c_list_view;


//=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        y_b = (Button)findViewById(R.id.yes_button);
        n_b = (Button)findViewById(R.id.no_button);
        questionView = (TextView)findViewById(R.id.Question);
        y_c = (TextView)findViewById(R.id.yes_count);
        n_c = (TextView)findViewById(R.id.no_count);
        add_comment_action = (Button)findViewById(R.id.add_comment_action);
        add_comment_text = (EditText)findViewById(R.id.add_comment_text);
        c_list_view = (ListView) findViewById(R.id.comments_list);
        c_list_view.setLongClickable(true);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Question Data");

        comments = new ArrayList<>();

        Gson gson = new Gson();
        questionData = gson.fromJson(data, Question.class);

        questionView.setText(questionData.getQuestion());
        create_pie(questionData.getYes_counter(),questionData.getNo_counter());

        y_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setYes_counter(questionData.getYes_counter()+1);
                String tmp = "" + questionData.getYes_counter();
                y_c.setText(tmp);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());
                DatabaseUtils.addDataToChildFirebase(questionData, null, Constants.TYPE_QUESTION);

            }
        });

        n_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setNo_counter(questionData.getNo_counter()+1);
                String tmp = "" + questionData.getNo_counter();
                n_c.setText(tmp);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());
                DatabaseUtils.addDataToChildFirebase(questionData,null, Constants.TYPE_QUESTION);
            }
        });


        retrieveComments(questionData.getId());


        add_comment_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(add_comment_text.getText().toString())){
                    add_comment_text.setError(getString(R.string.EmptyComment));
                    add_comment_text.requestFocus();
                }
                else{
                    Comment com = new Comment(add_comment_text.getText().toString());
                    comments.add(com);
                    DatabaseUtils.addDataToChildFirebase(questionData,com,Constants.TYPE_COMMENT);
                }
            }
        });


        c_list_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int pos, long id) {
                Log.d(TAG,"inside long press");
                return true;
            }
        });

        c_list_view.setAdapter(new CustomList(this, comments));
        ((CustomList) c_list_view.getAdapter()).notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        comments.clear();
    }

    //=============================================================================================

    private void retrieveComments(String id) {


        //TODO Throw it to general database class  - no logic of data base inside the QME
        DatabaseUtils.ref.child(Constants.TYPE_QUESTION).child(id).child(Constants.TYPE_COMMENT).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.clear();
                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    Comment q = noteSnapshot.getValue(Comment.class);
                    comments.add(q);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    //=============================================================================================

    private void create_pie(long yes, long no){
        PieChart pieChart = (PieChart) findViewById(R.id.piechart);

        pieChart.setUsePercentValues(true);

        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        yvalues.add(new Entry(yes, 0));
        yvalues.add(new Entry(no, 1));

        PieDataSet dataSet = new PieDataSet(yvalues, "Question Results");

        //Set colors to pie
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        //Give description
//        pieChart.setDescription("Question Pie Chart");

        //Disable Hole in the Pie Chart
        //pieChart.setDrawHoleEnabled(false);

        //pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(32f);
        pieChart.setHoleRadius(32f);

        ArrayList<String> xVals = new ArrayList<>();

        xVals.add("YES");
        xVals.add("NO");

        PieData data = new PieData(xVals, dataSet);

        //We can define text size and also color for the Pie Chart
        data.setValueTextSize(20f);
        dataSet.setColors(PieColor.Qme_COLORS);


        data.setValueFormatter(new PercentFormatter());
        pieChart.setData(data);
        pieChart.invalidate();

        /*
        data.setValueTextColor(Color.DKGRAY);
        OR
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        OR
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        OR
        dataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        OR
        dataSet.setColors(ColorTemplate.PASTEL_COLORS);
         */
    }


    //=============================================================================================


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


}
