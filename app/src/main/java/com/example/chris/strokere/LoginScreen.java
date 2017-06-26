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
        setContentView(R.layout.activity_login);


        EditText emailLogin = (EditText) findViewById(R.id.emailLogin);
        emailLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText passwordLogin = (EditText) findViewById(R.id.passwordLogin);
        passwordLogin.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Listner for not registered button to go to register screen
        Button notRegisteredBtn = (Button) findViewById(R.id.notRegistered);
        notRegisteredBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        notRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, Register.class));
            }

        });


        //Lisetner for not activity_login button TEST for exercise
        Button loginBtnL = (Button) findViewById(R.id.loginBtnL);
        loginBtnL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        loginBtnL.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, ExerciseView.class));
            }
        });

    }

    public void onLogin (View view) {

        //EditText emailLogin = (EditText) findViewById(R.id.emailLogin);
        //EditText passwordLogin = (EditText) findViewById(R.id.passwordLogin);

        //String username = emailLogin.getText().toString();
        //String password = passwordLogin.getText().toString();
        //String type = "login";

        //BackgroundWorker backgroundWorker= new BackgroundWorker(this);
        //backgroundWorker.execute(type, username, password);

        //Intent I=new Intent(LoginScreen.this,ExerciseView.class);
        //startActivity(I);

        //Button loginBtnL = (Button) findViewById(R.id.loginBtnL);
        //loginBtnL.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //loginBtnL.setOnClickListener(new View.OnClickListener() {
         //   public void onClick(View v) {
         //       startActivity(new Intent(LoginScreen.this, ExerciseView.class));
         //   }
        //});


    }




}
