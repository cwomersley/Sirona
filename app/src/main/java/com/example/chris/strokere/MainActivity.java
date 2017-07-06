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

public class MainActivity extends AppCompatActivity  {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText emailL;
    private EditText passwordL;
    private ProgressDialog progressDialog;
    public static final String TAG = "Login";
    boolean loggedIn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loggedIn=false;
        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        emailL = (EditText) findViewById(R.id.emailL);
        passwordL = (EditText) findViewById(R.id.passwordL);


        //passwordLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //emailLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Listner for Log in button
       // Button logInBtn = (Button) findViewById(R.id.loginBtnM);
        //logInBtn.setOnClickListener(new View.OnClickListener() {
         //   public void onClick(View v) {
         //       if (loggedIn) {
         //      startActivity(new Intent(MainActivity.this, Home.class));
         //       }

         //   }
        //});


        //logInBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Listener for register button to go to Register screen
        Button registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                    startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        //registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }

    private void loginUser()
    {

        Toast.makeText(MainActivity.this, "Signing in",Toast.LENGTH_SHORT).show();
        String email = emailL.getText().toString();
        String password = passwordL.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        loggedIn=true;

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, "Sign in failed",
                                    Toast.LENGTH_SHORT).show();
                            loggedIn=false;
                        }


                    }
                });
        //Intent intent = new Intent(MainActivity.this, Home.class);
        //startActivity(intent);
    }


    public void onLogin(View v) {
        String email = emailL.getText().toString();
        String password = passwordL.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter an email address and password",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            loginUser();
            if (loggedIn) {
                startActivity(new Intent(MainActivity.this, Home.class));
            }
        }
    }



    /**
    public void onLogin (View view) {
        EditText emailLoginM = (EditText) findViewById(R.id.emailL);
        EditText passwordLoginM = (EditText) findViewById(R.id.passwordL);
        String username = emailLoginM.getText().toString();
        String password = passwordLoginM.getText().toString();
        String type = "login";

        BackgroundWorker backgroundWorker= new BackgroundWorker(this);
        backgroundWorker.execute(type, username, password);
    }

     **/

}


