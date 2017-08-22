package com.example.chris.strokere;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.EditText;
import android.text.Editable;
import android.widget.ExpandableListView.OnChildClickListener;

public class ExerciseList extends BaseActivity {

    ListView listView;
    ArrayAdapter listViewAdapter;
    ExpandableListAdapter expListAdapter;
    EditText inputSearch;
    ExpandableListView expListView;
    public static String path;
    public static String name;
    public static String niceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        setupNavbar();

        setDataExList.setData();


        /* Constructs an Expandable ListView (default screen, headers for muscle groups, exercises
        as their children. Adapter is instantiated by the HashMap 'map' in setDataExList. */

        expListView = (ExpandableListView) findViewById(R.id.list);
        expListAdapter = new ExpandableListAdapter(this, setDataExList.list, setDataExList.map);
        expListView.setClickable(true);
        expListView.setAdapter(expListAdapter);

        /* Constructs a ListView (screen made when inputting in the searchbar) and
         its adapter. The adapter is instantiated by an ArrayList.*/

        listViewAdapter = new ArrayAdapter<>(this, R.layout.list_items, R.id.textView, setDataExList.allExercises);
        listView = (ListView) findViewById(R.id.listview);
        listView.setClickable(true);
        listView.setAdapter(listViewAdapter);

        /** This method constructs a listener for when the children (exercises) are clicked.
         * @param ExpandableListView the parent view
         * @param v the view the children are in
         * @param groupPosition the final 3 parameters are for locating which child is chosen
         */
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                getApplicationContext();
                //niceName is the text the user sees
                niceName = setDataExList.map.get(setDataExList.list.get(groupPosition)).get(childPosition);
                //name is the actual filename
                name = niceName.replace(" ", "_").toLowerCase();
                //path is the full video path
                path = "android.resource://" + getPackageName() + "//raw/" + VideoNameChange.changeName(name);
                //opens the video playing class
                startActivity(new Intent(ExerciseList.this, ExerciseListVplayer.class));
                return false;
            }
        });
    /** This method constructs a listener for when the children (exercises) are clicked. This page
     * is made when user inputs into the searchbar. Same functionality as onChildClick.
    * @param adapter the parent view
    * @param view the view within the parent clicked
    * @param p the final 2 parameters are for locating which child is chosen
    */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int p, long l) {
                niceName = adapter.getItemAtPosition(p).toString();
                name = niceName.replace(" ", "_").toLowerCase();
                path = "android.resource://" + getPackageName() + "//raw/" + VideoNameChange.changeName(name);
                startActivity(new Intent(ExerciseList.this, ExerciseListVplayer.class));
            }
        } );
        // Constructs a EditText searchbar
        inputSearch = (EditText) findViewById(R.id.exerciseSearch);
        // Constructs a listener for when user inputs
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            /** Method hides ExpandableListView (parent/children) if user has typed in searchbar
             * and replaces with just ListView (exercises).  Else, shows ExpandableListView.
             *
             * @param charSeq what the user has inputted
             * @param a final 3 params refer to the positioning of text inputted.
             */
            public void onTextChanged(CharSequence charSeq, int a, int b, int c) {
                if (inputSearch.getText().toString().trim().length() > 0) {
                    expListView.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    expListView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                }
                //filters the ListView according to input
                listViewAdapter.getFilter().filter(charSeq);
            }
            //requires the two below methods for the listener same params as above.
            @Override
            public void beforeTextChanged(CharSequence d, int e, int f, int g) {

            }

            @Override
            public void afterTextChanged(Editable h) {

            }
        });

    }

    @Override
    public int getLayout() {
        return R.layout.activity_exercise_list;
    }
}

