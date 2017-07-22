package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.text.TextWatcher;
import android.widget.EditText;
import android.text.Editable;

public class ExerciseList extends AppCompatActivity {


    ListView listView;
    ArrayAdapter adapter;
    EditText inputSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        List<String> list = new ArrayList<>();
        list.add("exampleExercise1");
        list.add("exampleExercise2");
        list.add("exampleExercise3");
        list.add("exampleExercise4");
        list.add("exampleExercise5");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        listView = (ListView) findViewById(R.id.list);


       inputSearch = (EditText) findViewById(R.id.exerciseSearch);

        listView.setAdapter(adapter);


         inputSearch.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence charSeq, int arg1, int arg2, int arg3) {
                ExerciseList.this.adapter.getFilter().filter(charSeq);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
            });


        }
    }
