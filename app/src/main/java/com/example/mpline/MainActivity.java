package com.example.mpline;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lineChart = (LineChart) findViewById(R.id.chart);

        MyMarkerView marker = new MyMarkerView(this, R.layout.markerviewtext);
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);


        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry((float) 1992.2, (float) 10.5));
        entries.add(new Entry((float) 1994.3, (float) 10));
        entries.add(new Entry((float) 1996.5, (float) 17.5));
        entries.add(new Entry((float) 1998.2, (float) 9));
        entries.add(new Entry((float) 2001.4, (float) 16));
        entries.add(new Entry((float) 2007.6, (float) 18));
        entries.add(new Entry((float) 2010.2, (float) 20));
        entries.add(new Entry((float) 2012.2, (float) 22.5));
        entries.add(new Entry((float) 2014.2, (float) 20));
        entries.add(new Entry((float) 2017.6, (float) 23.5));
        entries.add(new Entry((float) 2019.8, (float) 28));
        entries.add(new Entry((float) 2021.5, (float) 28.9));
        entries.add(new Entry((float) 2021.6, (float) 31));
        entries.add(new Entry((float) 2022.11, (float) 32));
        entries.add(new Entry((float) 2023.8, (float) 34.5));


        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Toast.makeText(getApplicationContext(),entries.indexOf(e)+"",Toast.LENGTH_SHORT).show();
                int v = entries.indexOf(e);
                Log.e("테스트", "" + entries.get(v).toString() + " / " + entries.indexOf(e));
            }

            @Override
            public void onNothingSelected() {

            }
        });


        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);


        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);

        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        Description description = new Description();
        description.setText("");

        lineChart.setScaleEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);
        lineChart.animateY(1000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

    }
}