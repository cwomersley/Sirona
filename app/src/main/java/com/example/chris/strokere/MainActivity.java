package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Import typeface

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Listner for Log in button to go to log inscreen
        Button logInBtn = (Button) findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, LoginScreen.class));
            }
        });


        logInBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        //Listner for Log in button to go to Register screen
        Button registerBtn = (Button) findViewById(R.id.registerBtnR);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }

}
