package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setupNavbar();


        Button btnThisApp = (Button) findViewById(R.id.btnThisAppS);
        btnThisApp.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button btnWorkPref = (Button) findViewById(R.id.btnWorkPrefS);
        btnWorkPref.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button btnWorkRem = (Button) findViewById(R.id.btnWorkRemS);
        btnWorkRem.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        Button btnAccount = (Button) findViewById(R.id.btnAccountS);
        btnAccount.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

    }

    //Tells the toolbar which acitvity it is being used in
    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }


    //Methods to open the activities each button leads to
    public void openThisApp(View view) {
        startActivity(new Intent(Settings.this, Account.class));
    }

    public void openWorkPref(View view) {
        startActivity(new Intent(Settings.this, Account.class));
    }

    public void openWorkRem(View view) {
        startActivity(new Intent(Settings.this, SettingsWorkoutReminder.class));
    }

    public void openAccount(View view) {

        startActivity(new Intent(Settings.this, Account.class));
    }


}

