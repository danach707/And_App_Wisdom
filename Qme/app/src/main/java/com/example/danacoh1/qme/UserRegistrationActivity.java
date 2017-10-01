package com.example.danacoh1.qme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class UserRegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private EditText firstname, surname, age, email, password, password2, shortStory, username;
    private Spinner gender;
    private Button signup, cancel;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private View mProgressView;
    private UserSignUpTask mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        firstname = (EditText) findViewById(R.id.reg_privatename);
        surname = (EditText) findViewById(R.id.reg_surname);
        age = (EditText) findViewById(R.id.reg_age);
        email = (EditText) findViewById(R.id.reg_email);
        username = (EditText) findViewById(R.id.reg_username);
        password = (EditText) findViewById(R.id.reg_password);
        password2 = (EditText) findViewById(R.id.reg_password2);
        shortStory = (EditText) findViewById(R.id.reg_short_story);
        gender = (Spinner) findViewById(R.id.reg_gender);
        signup = (Button) findViewById(R.id.reg_signup);
        cancel = (Button) findViewById(R.id.reg_cancel);
        mProgressView = findViewById(R.id.login_progress);

        DatabaseUtils.signoutCurrentFirebaseUser();

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "inside onAuthStateListener!!!");
                    sendVerificationEmail();
                } else {
                    // User is signed out
                    //Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgress(true);
                if (valiDetails()) {
                    User user = new User(username.getText().toString(),
                            password.getText().toString(),
                            firstname.getText().toString(),
                            surname.getText().toString(),
                            age.getText().toString(),
                            email.getText().toString(),
                            gender.getSelectedItem().toString(),
                            shortStory.getText().toString(),
                            null);
                    DatabaseUtils.writeToDatabase(user, null, Constants.TYPE_USER);

                    mAuthTask = new UserSignUpTask(email.getText().toString(), password.getText().toString());
                    mAuthTask.execute((Void) null);

//                    finish();
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
                } else {
                    showProgress(false);
                }

            }
        });



        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private boolean valiDetails() {
        email.setError(null);
        password.setError(null);
        username.setError(null);
        age.setError(null);
        String email = this.email.getText().toString();
        String ugender = this.gender.getSelectedItem().toString();
        String musername = this.username.getText().toString();
        String pass = password.getText().toString();
        String pass2 = password2.getText().toString();
        String fname = firstname.getText().toString();
        String lastname = surname.getText().toString();
        String userage = age.getText().toString();
        boolean cancel = false;
        View focusView = null;

//        if(!TextUtils.isEmpty(musername) &&)

        if (!TextUtils.isEmpty(pass) && !isPasswordValid(pass)) {
            password.setError(getString(R.string.error_invalid_password));
            focusView = password;
            cancel = true;
        }if (!TextUtils.isEmpty(pass2) || !pass2.equals(pass)) {
            password2.setError(getString(R.string.error_invalid_password2));
            focusView = password2;
            cancel = true;
        }
        if (!TextUtils.isEmpty(userage) && Integer.parseInt(userage) < Constants.LEGAL_AGE) {
            age.setError(getString(R.string.error_invalid_age));
            focusView = age;
            cancel = true;
        }
        if (TextUtils.isEmpty(email)) {
            this.email.setError(getString(R.string.error_field_required));
            focusView = this.email;
            cancel = true;
        } else if (!isEmailValid(email)) {
            this.email.setError(getString(R.string.error_invalid_email));
            focusView = this.email;
            cancel = true;
        } else if (TextUtils.isEmpty(fname)) {
            firstname.setError(getString(R.string.error_invalid_email));
            focusView = firstname;
            cancel = true;
        } else if (TextUtils.isEmpty(lastname)) {
            surname.setError(getString(R.string.error_invalid_email));
            focusView = surname;
            cancel = true;
        } else if (TextUtils.isEmpty(userage)) {
            age.setError(getString(R.string.error_invalid_email));
            focusView = age;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            return true;
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".com");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    public void writeUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the userAuthenticated. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in userAuthenticated can be handled in the listener.
                        if (task.isSuccessful()) {
                            Log.d(TAG, "inside writeUser   SUCCESS!!!");
                        } else {
                            Log.d(TAG, "inside writeUser   FAIL!!!");
                        }
                    }
                });

    }

    private void sendVerificationEmail() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null)
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // email sent
                                Toast.makeText(UserRegistrationActivity.this, "Verification Email sent to: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                // after email is sent just logout the user and finish this activity
                                FirebaseAuth.getInstance().signOut();
                                startActivity(new Intent(UserRegistrationActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                // email not sent, so display message and restart the activity or do whatever you wish to do
                                Toast.makeText(UserRegistrationActivity.this, "Failed sending Email to: " + user.getEmail(), Toast.LENGTH_SHORT).show();
                                //restart this activity
//                                overridePendingTransition(0, 0);
//                                finish();
//                                overridePendingTransition(0, 0);
//                                startActivity(getIntent());

                            }
                        }
                    });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    public class UserSignUpTask extends AsyncTask<Void, Void, Void> {

        private final String mEmail;
        private final String mPassword;
        private Boolean approvalThread = false;

        UserSignUpTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Void doInBackground(Void... params) {
            writeUser(mEmail, mPassword);
            return params[0];
        }

        @Override
        protected void onPostExecute(Void v) {
            mAuthTask = null;
            showProgress(false);
        }
    }

}
