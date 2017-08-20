package com.example.chris.strokere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import android.widget.EditText;
import android.text.Editable;
import android.widget.ExpandableListView.OnChildClickListener;

public class ExerciseList extends BaseActivity {

    ListView listView;
    ArrayAdapter listViewAdapter;
    ExpandableListAdapter expListAdapter;
    EditText inputSearch;
    List<String> list;
    List<String> allExercises;
    HashMap<String, List<String>> map;
    ExpandableListView expListView;
    public static String path;
    public static String name;
    public static String niceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

/*
    THINGS TO DO
    4. make the file names start with the right letter, then add underscore (so change char to remove first 2)
    5. Refactor
    6. possibly initdata() its own class, possibly make a video class
     */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setupNavbar();

//below is making the listview
        expListView = (ExpandableListView) findViewById(R.id.list);
        initData();
        expListAdapter = new ExpandableListAdapter(this, list, map);
        expListView.setClickable(true);
        expListView.setAdapter(expListAdapter);

        listViewAdapter = new ArrayAdapter<>(this, R.layout.list_items, R.id.textView, allExercises);
        listView = (ListView) findViewById(R.id.listview);
        listView.setClickable(true);
        listView.setAdapter(listViewAdapter);

        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                getApplicationContext();
                niceName = map.get(list.get(groupPosition)).get(childPosition);
                //cant use contain gonna have to use.equals(), change this at end
                name = niceName.replace(" ", "_");
                name = name.toLowerCase();
                path = "android.resource://" + getPackageName() + "//raw/" + name;
                startActivity(new Intent(ExerciseList.this, exerciseListVplayer.class));
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int p, long l) {
                niceName = adapter.getItemAtPosition(p).toString();
                name = niceName.replace(" ", "_").toLowerCase();
                path = "android.resource://" + getPackageName() + "//raw/" + name;
                startActivity(new Intent(ExerciseList.this, exerciseListVplayer.class));
            }

        } );


        //below makes the search functionality
        inputSearch = (EditText) findViewById(R.id.exerciseSearch);
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence charSeq, int a, int b, int c) {
                if (inputSearch.getText().toString().trim().length() > 0) {
                    expListView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);

                } else {
                    expListView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);

                }
                listViewAdapter.getFilter().filter(charSeq);

            }

            @Override
            public void beforeTextChanged(CharSequence d, int e, int f, int g) {

            }

            @Override
            public void afterTextChanged(Editable h) {

            }
        });

    }

    private void initData() {
        //below instantiates the headers, and then makes a map of headers to arraylists
        list = new ArrayList<>();
        allExercises = new ArrayList<>();
        map = new HashMap<>();
        list.add("All Exercises");
        list.add("Arms");
        list.add("Back");
        list.add("Chest");
        list.add("Core");
        list.add("Legs");
        list.add("Shoulders");
        map.put("All Exercises", new ArrayList<String>());
        map.put("Arms", new ArrayList<String>());
        map.put("Back", new ArrayList<String>());
        map.put("Chest", new ArrayList<String>());
        map.put("Core", new ArrayList<String>());
        map.put("Legs", new ArrayList<String>());
        map.put("Shoulders", new ArrayList<String>());

        //below then uses a for loop to go through each exercise video and maps them accordingly.
        Field[] f = R.raw.class.getFields();
        for (Field fields : f)
            try {
                String name = fields.getName();
                char[] c = name.toCharArray();
                char[] d = Arrays.copyOfRange(c, 1, name.length());
                d[0] = Character.toUpperCase(d[0]);
                name = new String(c);
                name = name.replace("_", " ");
                if (!name.toLowerCase().equals("$change") && !name.toLowerCase().equals("serialVersionuid")) {
                    map.get("All Exercises").add(name);
                    allExercises.add(name);
                    switch (c[0]) {
                        case 'a':
                            map.get("Arms").add(name);
                            break;
                        case 'b':
                            map.get("Back").add(name);
                            break;
                        case 'c':
                            map.get("Chest").add(name);
                            break;
                        case 'd':
                            map.get("Core").add(name);
                            break;
                        case 'e':
                            map.get("Legs").add(name);
                            break;
                        case 'f':
                            map.get("Shoulders").add(name);
                            break;
                    }
                }

            } catch (IllegalArgumentException e) {
            }

    }

    @Override
    public int getLayout() {
        return R.layout.activity_exercise_list;
    }

    public String getPath() {

        return path;
    }
}