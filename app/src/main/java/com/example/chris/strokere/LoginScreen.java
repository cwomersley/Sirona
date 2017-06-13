package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_login);


        EditText emailLogIn = (EditText) findViewById(R.id.emailLogIn);
        emailLogIn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginPassword.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Listner for not registered button to go to register screen
        Button notRegisteredBtn = (Button) findViewById(R.id.notRegistered);
        notRegisteredBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        notRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, Register.class));
            }

        });


        //Listner for not activty_login button TEST for exercise
        Button logIn = (Button) findViewById(R.id.registerBtnR);
        logIn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, exerciseView.class));
            }
        });

    }
}
