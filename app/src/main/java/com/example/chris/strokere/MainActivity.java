package com.example.chris.strokere;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText emailL, passwordL;
    private ProgressDialog progressDialog;
    private Button logInBtn,registerBtn;
    public static final String TAG = "Login";



    @Override
    protected void onCreate(Bundle savedInstanceState) {



            progressDialog = new ProgressDialog(this);
            mAuth = FirebaseAuth.getInstance();
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //method that checks whether a user is already signed in and advanced to home activity if so
            onStart();

            //Button for development purposes that skips the login page
            Button bypassBtn = (Button) findViewById(R.id.bypassBtn);
            bypassBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
            bypassBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, Home.class));
                }
            });

            emailL = (EditText) findViewById(R.id.emailL);
            passwordL = (EditText) findViewById(R.id.passwordL);
            logInBtn = (Button) findViewById(R.id.loginBtnM);
            registerBtn = (Button) findViewById(R.id.registerBtnM);

            emailL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
            passwordL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
            logInBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
            registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    //checking if user is signed in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null)
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser!=null) {
            startActivity(new Intent(MainActivity.this, Home.class));
        }
    }



    //method to open Register activity
    public void onRegister(View v) {

        startActivity(new Intent(MainActivity.this, Register.class));
    }

    //method to login and advance to Home activity
    public void onLogin(View v) {
        if (!validateForm()) {
            return;
        }
        Toast.makeText(MainActivity.this, "Signing in",Toast.LENGTH_SHORT).show();
        String email = emailL.getText().toString();
        String password = passwordL.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            startActivity(new Intent(MainActivity.this, Home.class));
                            finish();
                        }

                        else {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Sign in failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    //https://github.com/firebase/quickstart-android/blob/master/auth/app/src/main/java/com/google/firebase/quickstart/auth/EmailPasswordActivity.java
    //method to validate login credentials
    private boolean validateForm() {
        boolean valid = true;

        String email = emailL.getText().toString();
        String password = passwordL.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailL.setError("Required.");
            valid = false;
        }
        else if (!emailValidator(email)) {
            emailL.setError("Please enter a valid email address");
            valid =false;
        }
        else {
            emailL.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            passwordL.setError("Required.");
            valid = false;
        } else {
            passwordL.setError(null);
        }
        return valid;
    }

    public boolean emailValidator(String email)

    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //sends a password reset email to user
    public void forgotPassword(View view){
        startActivity(new Intent(MainActivity.this, ForgottenPassword.class));
        /**
        String email = emailL.getText().toString();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email address to proceed", Toast.LENGTH_LONG).show();
            return;
        }
        else if (!emailValidator(email)) {
            emailL.setError("Please enter a valid email address");
            return;
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(MainActivity.this, "Reset password email sent",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
         **/

    }

}






