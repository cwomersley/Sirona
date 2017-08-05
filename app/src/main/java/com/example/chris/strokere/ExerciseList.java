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

public class ExerciseList extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    EditText inputSearch;
    List<String> list;
    List<String> Headings;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

//below is populating an arraylist with the video names from raw
        Field[] f = R.raw.class.getFields();
        list = new ArrayList<>();
        for(Field fields : f)
            try{
                //below attempts to format the text so _ are spaces (which works now) and to capitalise the first letter after
                //the space (which does not work right now).
                String name = fields.getName();
                char[] c = name.toCharArray();
                if(name.contains("_")){
                    while(i<name.length()) {
                        if(c[i] == ' ') {
                            c[i+1] = Character.toUpperCase(c[i+1]);
                        }
                       i++;
                    }
                }
                //below if gets rid of the two unwanted field names that get added automatically
                if(!name.equals("$change") && !name.equals("serialVersionUID")) {
                    c[0] = Character.toUpperCase(c[0]); //make first letter always uppercase
                    name = String.valueOf(c);
                    name = name.replace("_", " ");
                    list.add(name);
                }
            }
            catch (IllegalArgumentException e) {
            }
//below is making the listview
        adapter = new ArrayAdapter<>(this, R.layout.list_items, R.id.textView, list);
        listView = (ListView) findViewById(R.id.list);
        listView.setClickable(true);
        listView.setAdapter(adapter);

// below is making the items clickable so they play the video when clicked
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int p, long l) {
                String name = adapter.getItemAtPosition(p).toString();
                name = name.replace(" ", "_");
                name = name.toLowerCase();
                String path = "android.resource://" + getPackageName() + "//raw/"  + name;
                VideoView v = (VideoView) findViewById(R.id.videoView2);
                v.setVideoPath(path);
                v.start();
            }
        } );

        //below makes the search functionality
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