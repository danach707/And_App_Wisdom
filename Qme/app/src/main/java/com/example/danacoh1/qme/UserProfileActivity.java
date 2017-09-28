package com.example.danacoh1.qme;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.danacoh1.qme.Constants.GET_FROM_GALLERY;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static User currUserLogged;
    private final String TAG = getClass().getSimpleName();
    private FirebaseUser userAuthenticated;
    private TextView txt_shortBio;
    private TextView txt_userName;
    private DatabaseReference ref;
    private ImageButton userProfilePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ref = FirebaseDatabase.getInstance().getReference(Constants.TYPE_USER);
        userAuthenticated = FirebaseAuth.getInstance().getCurrentUser();

        txt_userName = (TextView) findViewById(R.id.user_profile_name);
        txt_shortBio = (TextView) findViewById(R.id.user_profile_short_bio);
        userProfilePhoto = (ImageButton) findViewById(R.id.user_profile_photo);

        /*********   USER  DETAILS   ********/

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot noteSnapshot : dataSnapshot.getChildren()) {
                    User u = noteSnapshot.getValue(User.class);
                    if (u != null && u.getEmail().equals(userAuthenticated.getEmail())) {
                        currUserLogged = new User(u);

                        txt_userName.setText(currUserLogged.getFname() + " " + currUserLogged.getLname());
                        txt_shortBio.setText(currUserLogged.getGender() + ", " + currUserLogged.getAge());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "User Loading Cancelled", Toast.LENGTH_SHORT).show();
            }

        });

        /*********** USER  SET PHOTO *********/
        //System.out.println("@@@@@@@@@@UserProfileActivity.currUserLogged.getId(): "+currUserLogged);
        userProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_FROM_GALLERY);
            }
        });


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), NewQuestionActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //==============================================================================================//

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //==============================================================================================//

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    //==============================================================================================//

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================//

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {

            if (DatabaseUtils.isUserConnected()) {
                Intent intent = new Intent(getApplicationContext(), UserProfileActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }

        } else if (id == R.id.nav_myQuestions) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("filter", true);
            startActivity(intent);
        } else if (id == R.id.nav_questionslist) {
            Intent intent = new Intent(getApplicationContext(), ListActivity.class);
            intent.putExtra("filter", false);
            //intent.putExtra("userId", currUserLogged.getId());
            startActivity(intent);
        } else if (id == R.id.nav_signout) {
            DatabaseUtils.signoutCurrentFirebaseUser();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //==============================================================================================//


    @Override
    public void onSaveInstanceState(Bundle outState) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if userAuthenticated's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The userAuthenticated's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            String uid = user.getUid();
        }
        outState.putString("message", "This is my message to be reloaded");
        super.onSaveInstanceState(outState);
    }

    //==============================================================================================//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            currUserLogged.setPhotoUrl(selectedImage);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
                userProfilePhoto.setBackground(bitmapDrawable);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
