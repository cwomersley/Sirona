package com.example.chris.strokere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginScreenx extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText emailL;
    private EditText passwordL;
    public static final String TAG = "Register";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginx);


        emailL = (EditText) findViewById(R.id.emailL);
        passwordL = (EditText) findViewById(R.id.passwordL);


        //passwordLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //passwordL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Listener for not registered button to go to register screen
        Button notRegisteredBtn = (Button) findViewById(R.id.notRegistered);
        notRegisteredBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        notRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreenx.this, Register.class));
            }

        });


        //Lisetner for not activity_loginx button TEST for exercise
        Button loginBtnL = (Button) findViewById(R.id.registerBtnM);
        loginBtnL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        loginBtnL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreenx.this, Home.class));
            }
        });

    }



}
