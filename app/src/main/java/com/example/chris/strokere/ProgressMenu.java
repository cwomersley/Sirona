package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProgressMenu extends BaseActivity {

    Tip tip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_menu);
        setupNavbar();

        Button btnCalendar = (Button) findViewById(R.id.StepUpsBtn);
        btnCalendar.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ProgressMenu.this, ProgressCalendar.class));
            }
        });

        Button btnGraph = (Button) findViewById(R.id.Settings);
        btnGraph.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        btnGraph.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ProgressMenu.this, ProgressGraph.class));
            }
        });

        TextView tipsText = (TextView) findViewById(R.id.tipsText);
        TipManager tipManager = new TipManager();
        //Uses the TipManager to get a random tip
        this.tip = tipManager.getATip();
        //Gets the text of the tip
        String tipText = this.tip.getTipText();
        //Gets the activity that the tip should go to
        String tipIntent = this.tip.getTipIntent();
        tipsText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToTip();
            }
        });
        tipsText.setText(tipText);
        tipsText.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


    }

    private void goToTip() {

        Intent intent = new Intent();
        intent.setClassName(ProgressMenu.this,this.tip.getTipIntent());
        startActivity(intent);

    }

    @Override
    public int getLayout() {
        return R.layout.activity_progress_menu;
    }

}
