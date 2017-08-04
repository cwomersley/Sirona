package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import static android.graphics.Color.parseColor;

public class Graph extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setupNavbar();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 0),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);
        series.setColor(parseColor("#4BAA71"));
        series.setThickness(6);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setTitle("Weekly Progress");

        GridLabelRenderer gridRenderer = graph.getGridLabelRenderer();

                gridRenderer.setHorizontalLabelsColor(parseColor("#4BAA71"));
                gridRenderer.setVerticalLabelsColor(parseColor("#4BAA71"));
                gridRenderer.setHorizontalAxisTitleColor(parseColor("#4BAA71"));
                gridRenderer.setVerticalAxisTitleColor(parseColor("#4BAA71"));
                gridRenderer.setGridColor(parseColor("#4BAA71"));
    }

    @Override
    public int getLayout() {
        return R.layout.activity_graph;
    }
}
