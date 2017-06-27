package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText firstNameR = (EditText) findViewById(R.id.firstNameR);
        firstNameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText passR = (EditText) findViewById(R.id.passR);
        passR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText passConfirmR = (EditText) findViewById(R.id.passConfirmR);
        passConfirmR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText emailR = (EditText) findViewById(R.id.emailR);
        emailR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText surnameR = (EditText) findViewById(R.id.surnameR);
        surnameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        //Listener for register button (needs implementing)
        Button registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
    }
}
