package com.example.mpline;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LineChart lineChart;
    MyMarkerView marker;
    TextView chartymd, chartpirce, chartgunsu;
    List<Entry> entries;
    LineDataSet lineDataSet;
    int linechartday;
    XAxis xAxis;
    YAxis yLAxis;
    float max, min;
    int xterm;
    LineData lineData;

    TextView Linecharttext1, Linecharttext2, Linecharttext3, Linecharttext100;
    CardView Linecharttype1, Linecharttype2, Linecharttype3, Linecharttype100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Findview();
        Datasetting();

        marker.setChartView(lineChart);
        lineChart.setMarker(marker);



        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                // Toast.makeText(getApplicationContext(),entries.indexOf(e)+"",Toast.LENGTH_SHORT).show();
                int v = entries.indexOf(e);
                Log.e("테스트", "" + entries.get(v).getX() + " / " + entries.indexOf(e));
                String ymd = String.valueOf(entries.get(v).getX());
                String p = String.valueOf(entries.get(v).getY());

                chartymd.setText(ymd + "월 / ");
                chartpirce.setText("평균 "+p + "만원 / ");
                chartgunsu.setText("4"+" 건");
            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    public void Datasetting() {

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

        max = entries.get(entries.size() - 1).getX();
        min = entries.get(0).getX();

        float minus = max - min;
        int i = (int) minus;


        if (i > 7) {

            xterm = 7;
        } else if (i > 2) {
            xterm = 3;

        }


        Chartsetting();


    }

    public void Findview() {
        linechartday = 100;
        entries = new ArrayList<>();
        marker = new MyMarkerView(this, R.layout.markerviewtext);

        lineChart = (LineChart) findViewById(R.id.linechart);
        chartymd = (TextView) findViewById(R.id.chartymd);
        chartpirce = (TextView) findViewById(R.id.chartpirce);
        chartgunsu = (TextView) findViewById(R.id.chartgunsu);


        Linecharttext1 = (TextView) findViewById(R.id.Linecharttext1);
        Linecharttext2 = (TextView) findViewById(R.id.Linecharttext2);
        Linecharttext3 = (TextView) findViewById(R.id.Linecharttext3);
        Linecharttext100 = (TextView) findViewById(R.id.Linecharttext100);

        Linecharttype1 = (CardView) findViewById(R.id.Linecharttype1);
        Linecharttype2 = (CardView) findViewById(R.id.Linecharttype2);
        Linecharttype3 = (CardView) findViewById(R.id.Linecharttype3);
        Linecharttype100 = (CardView) findViewById(R.id.Linecharttype100);

        Linecharttype1.setOnClickListener(this::onClick);
        Linecharttype2.setOnClickListener(this::onClick);
        Linecharttype3.setOnClickListener(this::onClick);
        Linecharttype100.setOnClickListener(this::onClick);

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Timelimit() {


        if (linechartday == 100) {

            max = entries.get(entries.size() - 1).getX();
            min = entries.get(0).getX();
            lineChart.setData(lineData);



            xAxis.setLabelCount((int) xterm, true);

            xAxis.setAxisMinimum(min);


            xAxis.setAxisMaximum(max);
            lineChart.invalidate();


            Linecharttype100.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));
            Linecharttype2.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (linechartday == 1) {


            linechartday = 1;

            lineChart.setData(lineData);

            this.min = max - 3; ///////// 최근 3년

            xAxis.setLabelCount(4, true);
            xAxis.setAxisMinimum(this.min);
            xAxis.setAxisMaximum(max);

            lineChart.invalidate();


            Linecharttype1.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype100.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));
            Linecharttype2.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (linechartday == 2) {


            lineChart.setData(lineData);


            this.min = max - 5;///////// 최근 5년

            xAxis.setLabelCount(5, true);
            xAxis.setAxisMinimum(this.min);
            xAxis.setAxisMaximum(max);
            lineChart.invalidate();

            Linecharttype2.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));


            Linecharttype100.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (linechartday == 3) {

            lineChart.setData(lineData);


            this.min = max - 10;///////// 최근 10년

            xAxis.setLabelCount(6, true);
            xAxis.setAxisMinimum(this.min);
            xAxis.setAxisMaximum(max);

            lineChart.invalidate();


            Linecharttype3.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype2.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.Off_Textcolor));


            Linecharttype100.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.Off_Textcolor));


        }


    }
    public void Chartsetting() {

         lineDataSet = new LineDataSet(entries, "");
//        lineDataSet.setLineWidth(2);
//        lineDataSet.setCircleRadius(6);
//        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
//        lineDataSet.setCircleColorHole(Color.BLUE);
//        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
//        lineDataSet.setDrawCircleHole(true);
//        lineDataSet.setDrawCircles(true);
//        lineDataSet.setDrawHorizontalHighlightIndicator(true);
//        lineDataSet.setDrawHighlightIndicators(true);
//        lineDataSet.setDrawValues(true);


        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(1);
        lineDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        // lineDataSet.setCircleColorHole(Color.BLUE);
        lineDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawHighlightIndicators(false);
        lineDataSet.setDrawValues(false);

        Legend l = lineChart.getLegend();
        l.setEnabled(false);



         lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);


         xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.enableGridDashedLine(8, 24, 0);
        xAxis.setLabelCount((int) 7, true);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                int v = (int) value;

                String s = String.valueOf(v);

                s = s + "년";

                return s;
            }
        });


         yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);

        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        yLAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                int v = (int) value;

                String s = String.valueOf(v) + "건";
                return s;
            }
        });


        Description description = new Description();
        description.setText("");


//        lineChart.setScaleEnabled(false);
//        lineChart.setDoubleTapToZoomEnabled(true);
//        lineChart.setDrawGridBackground(true);
//        lineChart.setDescription(description);
//        lineChart.animateY(1000, Easing.EasingOption.EaseInCubic);

        lineChart.setScaleEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDescription(description);

        lineChart.invalidate();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Linecharttype100: //백
                linechartday = 100;


                Timelimit();


                break;


            case R.id.Linecharttype1:

                linechartday = 1;
                Timelimit();


                break;

            case R.id.Linecharttype2:
                linechartday = 2;
                Timelimit();


                break;

            case R.id.Linecharttype3:
                linechartday = 3;
                Timelimit();
                break;
        }
    }
}