package com.example.danacoh1.qme;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    final private String TAG = getClass().getSimpleName();

    TextView questionView, y_c, n_c;
    Button y_b, n_b;


    DatabaseReference myRef;

    private DatabaseReference mDatabase;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Question questionData;

    private String[] web = {
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    };

//=============================================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getPreferences(MODE_PRIVATE);

        y_b = (Button)findViewById(R.id.yes_button);
        n_b = (Button)findViewById(R.id.no_button);
        questionView = (TextView)findViewById(R.id.Question);
        y_c = (TextView)findViewById(R.id.yes_count);
        n_c = (TextView)findViewById(R.id.no_count);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Question Data");

        Gson gson = new Gson();
        questionData = gson.fromJson(data, Question.class);

        questionView.setText(questionData.getQuestion());
        create_pie(questionData.getYes_counter(),questionData.getNo_counter());

        //TODO add error handling


     //   callAlertDialog();


        y_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setYes_counter(questionData.getYes_counter()+1);
                String tmp = "" + questionData.getYes_counter();
                y_c.setText(tmp);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());
                DatabaseUtils.writeToDatabase_question(questionData);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());

            }
        });

        n_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionData.setNo_counter(questionData.getNo_counter()+1);
                String tmp = "" + questionData.getNo_counter();
                n_c.setText(tmp);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());
                DatabaseUtils.writeToDatabase_question(questionData);
                create_pie(questionData.getYes_counter(),questionData.getNo_counter());

            }
        });


        ListView list;

        Integer[] imageId = {
                R.drawable.dislike,
                R.drawable.background_main,
                R.drawable.ic_add_circle_black_24dp,
                R.drawable.like,
                R.drawable.like,
                R.drawable.qme_logo,
                R.drawable.ic_add_circle_black_24dp

        };
        CustomList adapter = new
                CustomList(QuestionActivity.this, web, imageId);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(QuestionActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();

            }
        });



    }

    //=============================================================================================





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
        pieChart.setDescription("Question Pie Chart");

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


}
