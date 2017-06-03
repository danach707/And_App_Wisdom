package com.example.danacoh1.qme;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

/**
 * Created by danacoh1 on 6/3/2017.
 */

public class FirebaseUtils {



    public static void writeToDatabase(Question data){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        ref.child(data.getId()).setValue(data);

    }
}
