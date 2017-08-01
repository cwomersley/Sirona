package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import android.widget.EditText;
import android.text.Editable;
import android.widget.VideoView;
import java.util.HashMap;
public class ExerciseList extends AppCompatActivity {


    ListView listView;
    ArrayAdapter adapter;
    EditText inputSearch;
    List<String> list;
    HashMap<String, Field> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);


        Field[] f = R.raw.class.getFields();
        list = new ArrayList<>();
        for(Field fields : f)
        try{
            String name = fields.getName();
            list.add(name);
        }
        catch (IllegalArgumentException e) {
    }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView = (ListView) findViewById(R.id.list);
        listView.setClickable(true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int p, long l) {
                String name = adapter.getItemAtPosition(p).toString();
                String path = "android.resource://" + getPackageName() + "//raw/"  + name;
                VideoView v = (VideoView) findViewById(R.id.videoView2);
                v.setVideoPath(path);
                v.start();
            }
        } );
        //search bar code below
        inputSearch = (EditText) findViewById(R.id.exerciseSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSeq, int a, int b, int c) {
                ExerciseList.this.adapter.getFilter().filter(charSeq);
            }

            @Override
            public void beforeTextChanged(CharSequence d, int e, int f, int g) {

            }

            @Override
            public void afterTextChanged(Editable h) {

            }
        });


    }
}