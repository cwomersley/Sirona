package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Listner for not registered button to go to register screen
        Button notRegisteredBtn = (Button) findViewById(R.id.notRegistered);
        notRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, Register.class));
            }
        });


        //Listner for not login button TEST for exercise
        Button logIn = (Button) findViewById(R.id.logInButton);
        logIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(LoginScreen.this, exerciseView.class));
            }
        });

    }
}
