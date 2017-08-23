package com.example.chris.strokere;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;

/**
 * A standard ListView does not allow making parents/children (headers of muscle groups/each muscle groups exercises),
 * this class makes an ExpandableListView to enable this.
 * @author Adam
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listHashMap;

    /**
     * @param listDataHeader the muscle group headers
     * @param listHashMap the exercises of the muscle groups
     */
    public ExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listHashMap = listHashMap;
    }

    /**
     * @return the size of the parent groups
     */
    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    /**
     * @return the size of the children in a group
     */
    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listDataHeader.get(i)).size();
    }

    /**
     * @return the specified parent group
     */
    @Override
    public Object getGroup(int i) {
        return listDataHeader.get(i);
    }

    /**
     * @return the specified child
     */
    @Override
    public Object getChild(int i, int j) {
        return listHashMap.get(listDataHeader.get(i)).get(j);
    }

    /**
     * @return the location of the parent group
     */
    @Override
    public long getGroupId(int i) {
        return i;
    }

    /**
     * @param i the parent group of the child
     * @param j the child
     * @return the specified child's location
     */
    @Override
    public long getChildId(int i, int j) {
        return j;
    }

    /**
     * @return false to prevent recreating ExpandableListView when something changes
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * @param i the group/parent's header positioning
     * @param b checks if the group is expanded or not
     * @param view the old view
     * @param viewGroup parent collection of views, to make the new view
     * @return the new view
     */
    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        //headerName is instantiated from header's position
        String headerName = (String) getGroup(i);
        //if the view has nothing in it, instantiate it
        if(view == null){
            //constructs an inflater object, instantiated using the Android inflater service
            LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //inflates view with the headers XML
            view = inflater.inflate(R.layout.list_items_headers, null);
        }
        //constructs TextView for listHeaders to go in
        TextView listHeader = (TextView) view.findViewById(R.id.textView2);
        //set the text as bold
        listHeader.setTypeface(null, Typeface.BOLD);
        //set the header's text
        listHeader.setText(headerName);
        //set the header's font
        listHeader.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
        return view;
    }

    /**
     *
     * @param i group/parent position
     * @param j child position
     * @param b checks if there are more children after current one
     * @param view the old view
     * @param viewGroup collection of views, to make the new view
     * @return the new view
     */
    @Override
    public View getChildView(int i, int j, boolean b, View view, ViewGroup viewGroup) {
        //the name of the exercise
        final String childText = (String) getChild(i, j);
        //if the view of the child is empty
        if (view == null) {
            //same as previous
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //view instantiated with the exercises XML
            view = inflater.inflate(R.layout.list_items, null);
        }
        //TextView for the for the exercises to go in
        TextView txtListChild = (TextView)view.findViewById(R.id.textView);
        //set the exercises text
        txtListChild.setText(childText);
        //set the exercises font
        txtListChild.setTypeface(Typeface.createFromAsset(context.getAssets(), "Lato-Regular.ttf"));
        return view;
    }
    @Override
    /**
     * @return true so exercises are clickable
     */
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}