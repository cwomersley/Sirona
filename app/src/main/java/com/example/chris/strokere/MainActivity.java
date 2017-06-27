package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText emailLogin = (EditText) findViewById(R.id.emailLoginM);
        emailLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText passwordLogin = (EditText) findViewById(R.id.passwordLoginM);
        passwordLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Listner for Log in button to go to log inscreen
        Button logInBtn = (Button) findViewById(R.id.loginBtnM);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, Home.class));
            }
        });


        logInBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Listner for Log in button to go to Register screen
        Button registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }

}
