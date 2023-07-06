package com.example.mpline;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LineChart lineChart;
    MyMarkerView marker;
    private final String BASE_URL = "https://taewoooh88.cafe24.com/";
    Retrofit retrofit;
    List<Entry> entries, bentries, rentries, mentries;
    LineDataSet BDataSet, RDataSet, MDataSet;
    private long backpressedTime = 0;
    int linechartday;
    XAxis xAxis;
    YAxis yLAxis;
    float maxx, minx;
    float maxy, miny;
    int xterm;
    LineData lineData;
    int b_btn, j_btn, m_btn;
    TextView Linecharttext1, Linecharttext2, Linecharttext3, Linecharttext100;
    CardView Linecharttype1, Linecharttype2, Linecharttype3, Linecharttype100;

    TextView chartymd, chartpirce, chartgunsu, rchartymd, rchartpirce, rchartgunsu, mchartymd, mchartpirce, mchartgunsu;

    RelativeLayout linechartlayout10;
    CardView m1, j1, g1;
    TextView m2, j2, g2;
    Vibrator vibrator;
    RelativeLayout linechartlayout2, linechartlayout3, linechartlayout4;
    int infor = 0;

    int bentries_c = 0;
    int rentries_c = 0;
    int mentries_c = 0;

    int brlistitem_c = 0;

    int blistitem_c = 0;
    int bmlistitem_c = 0;
    int rlistitem_c = 0;
    int rmlistitem_c = 0;
    int mlistitem_c = 0;
    int brmlistitem_c = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infor = 0;
        Findview();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        marker.setChartView(lineChart);
//        lineChart.setMarker(marker);
        Tongsin("서울특별시");


        b_btn = 1;
        j_btn = 1;
        m_btn = 1;
        marker.setChartView(lineChart);
        lineChart.setMarker(marker);


        lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {

                btntext();
                if (b_btn == 0 && j_btn == 0 && m_btn == 0 ) {
                    vibrator.cancel();

                    lineChart.highlightValue(null);


                } else {

                    vibrator.vibrate(10);

                }


                linechartlayout10.setVisibility(View.GONE);

                    int k = entries.indexOf(e);


                    if (k > -1) {
                        String by = String.valueOf(entries.get(k).getX());
                        String bp = String.valueOf(entries.get(k).getY());
                        String bg = String.valueOf(entries.get(k).getData());

                        String bp2;
                        if (b_btn == 0 && j_btn == 0 && m_btn == 1) {


                            String my = String.valueOf(mentries.get(k).getX());
                            String mp = String.valueOf(mentries.get(k).getY());
                            String mg = String.valueOf(mentries.get(k).getData());

                            String mp2;

                            my = new Util().Yearchange(my);

                            mp2 = String.valueOf(mentries.get(k).getY());
                            int b = mp2.indexOf(".");
                            mp2 = mp2.substring(0, b);


                            btntext();




                            mchartymd.setText("매매 : " + my + "월 / ");
                            mchartpirce.setText("거래건수 : " + mp2 + " 건 / ");
                            mchartgunsu.setText(mg + " %");

                        } else {


                            by = new Util().Yearchange(by); // 년월일 90 --- > 10월 변경

                            bp2 = String.valueOf(bentries.get(k).getY());
                            int b = bp2.indexOf(".");
                            bp2 = bp2.substring(0, b);



                            btntext();


                            chartymd.setText("매매 : " + by + "월 / ");
                            chartpirce.setText("거래건수 : " + bp2 + " 건 / ");
                            chartgunsu.setText(bg + " %");


                        }


                        for (int i = 0; i < rentries.size(); i++) {


                            String ry = String.valueOf(rentries.get(i).getX());
                            String rp = String.valueOf(rentries.get(i).getY());
                            String rg = String.valueOf(rentries.get(i).getData());



                            String rp2;

                            String my = String.valueOf(mentries.get(i).getX());
                            String mp = String.valueOf(mentries.get(i).getY());
                            String mg = String.valueOf(mentries.get(i).getData());

                            String mp2;

                            ry = new Util().Yearchange(ry); // 90 --- > 10월 변경
                           // rp2 = new Util().Pricechange(rentries.get(i).getY());

                            if (by.equals(ry)) {


                                if (m_btn == 1 && j_btn == 1) {


                                    int r = rp.indexOf(".");
                                    rp = rp.substring(0, r);


                                    rchartymd.setText("전세 : " + ry + "월 / ");
                                    rchartpirce.setText("거래건수 : " + rp + " 건 / ");
                                    rchartgunsu.setText(rg + "%");







                                    my = new Util().Yearchange(my);

                                    mp2 = String.valueOf(mentries.get(i).getY());
                                    int b = mp2.indexOf(".");
                                    mp2 = mp2.substring(0, b);


                                    btntext();




                                    mchartymd.setText("월세 : " + my + "월 / ");
                                    mchartpirce.setText("거래건수 : " + mp2 + " 건 / ");
                                    mchartgunsu.setText(mg + " %");





                                } else if (b_btn == 0 && j_btn == 1) {

                                    btntext();
                                    rchartymd.setText("전세 : " + ry + "월 / ");
                                    rchartpirce.setText("평균 " + rp + " / ");
                                    rchartgunsu.setText(rg + "%");


                                }else if (b_btn == 1 && j_btn == 1){
                                    btntext();
                                    rchartymd.setText("전세 : " + ry + "월 / ");
                                    rchartpirce.setText("평균 " + rp + " / ");
                                    rchartgunsu.setText(rg + "%");


                                    by = new Util().Yearchange(by); // 년월일 90 --- > 10월 변경

                                    bp2 = String.valueOf(bentries.get(k).getY());
                                    int b = bp2.indexOf(".");
                                    bp2 = bp2.substring(0, b);



                                    btntext();


                                    chartymd.setText("매매 : " + by + "월 / ");
                                    chartpirce.setText("거래건수 : " + bp2 + " 건 / ");
                                    chartgunsu.setText(bg + " %");

                                }else if(b_btn == 1 && m_btn == 1){

                                    my = new Util().Yearchange(my);

                                    mp2 = String.valueOf(mentries.get(i).getY());
                                    int b = mp2.indexOf(".");
                                    mp2 = mp2.substring(0, b);


                                    btntext();




                                    mchartymd.setText("월세 : " + my + "월 / ");
                                    mchartpirce.setText("거래건수 : " + mp2 + " 건 / ");
                                    mchartgunsu.setText(mg + " %");

                                    by = new Util().Yearchange(by); // 년월일 90 --- > 10월 변경

                                    bp2 = String.valueOf(bentries.get(k).getY());
                                    int b3 = bp2.indexOf(".");
                                    bp2 = bp2.substring(0, b3);



                                    btntext();


                                    chartymd.setText("매매 : " + by + "월 / ");
                                    chartpirce.setText("거래건수 : " + bp2 + " 건 / ");
                                    chartgunsu.setText(bg + " %");


                                }
                            }

                        }




                    }


                    //   scrollView.setNestedScrollingEnabled(false);



            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

    public void btncheck1() {

        if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("100")) {
            j1.performClick();
            g1.performClick();


        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("010")) {
            m1.performClick();
            g1.performClick();
        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("001")) {
            m1.performClick();
            j1.performClick();

        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("000")) {

            m1.performClick();
            j1.performClick();
            g1.performClick();

        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("110")) {
            g1.performClick();

        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("101")) {
            j1.performClick();

        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("011")) {
            m1.performClick();

        } else if (new TWPreference(MainActivity.this).getString("매매전세월세버튼2", "111").equals("111")) {


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        String s = String.valueOf(b_btn) + String.valueOf(j_btn) + String.valueOf(m_btn);
        Log.e("버튼상태 확인", " - > " + b_btn + " / " + j_btn + " / " + m_btn + " / " + s);
        new TWPreference(MainActivity.this).putString("매매전세월세버튼2", s);

        new TWPreference(this).putInt("기간", linechartday);

    }

    public void gigancheck() {  //비지블

        xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //시발
        xAxis.enableGridDashedLine(8, 24, 0);
        Log.e("기간체크", "" + new TWPreference(this).getInt("기간", 100));

        if (new TWPreference(this).getInt("기간", 100) == 100) {


            lineChart.setData(lineData);


            xAxis.setLabelCount((int) xterm, true);

            xAxis.setAxisMinimum(minx);


            xAxis.setAxisMaximum(maxx);
            lineChart.invalidate();


            Linecharttype100.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));
            Linecharttype2.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (new TWPreference(this).getInt("기간", 100) == 1) {


            linechartday = 1;

            lineChart.setData(lineData);

            this.minx = maxx - 3; ///////// 최근 3년

            xAxis.setLabelCount(4, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);

            lineChart.invalidate();


            Linecharttype1.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype100.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));
            Linecharttype2.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (new TWPreference(this).getInt("기간", 100) == 2) {


            lineChart.setData(lineData);


            this.minx = maxx - 5;///////// 최근 5년

            xAxis.setLabelCount(5, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);
            lineChart.invalidate();

            Linecharttype2.setCardBackgroundColor(getColor(R.color.On_Btcolor));
            Linecharttext2.setTextColor(getColor(R.color.On_Textwcolor));

            Linecharttype1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext1.setTextColor(getColor(R.color.Off_Textcolor));

            Linecharttype3.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext3.setTextColor(getColor(R.color.Off_Textcolor));


            Linecharttype100.setCardBackgroundColor(getColor(R.color.off_Btcolor));
            Linecharttext100.setTextColor(getColor(R.color.Off_Textcolor));


        } else if (new TWPreference(this).getInt("기간", 100) == 3) {

            lineChart.setData(lineData);


            this.minx = maxx - 10;///////// 최근 10년

            xAxis.setLabelCount(6, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);

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

    public void btntext() {  //비지블

        Log.e("BTNCHECK", "" + b_btn + " / " + j_btn + " / " + m_btn);

        if (b_btn == 1 && j_btn == 0 && m_btn == 0) {
            entries = bentries;
            linechartlayout2.setVisibility(View.VISIBLE);
            linechartlayout3.setVisibility(View.GONE);
            linechartlayout4.setVisibility(View.GONE);


        } else if (b_btn == 0 && j_btn == 1 && m_btn == 0) {
            entries = rentries;
            linechartlayout2.setVisibility(View.GONE);
            linechartlayout3.setVisibility(View.VISIBLE);
            linechartlayout4.setVisibility(View.GONE);

        } else if (b_btn == 0 && j_btn == 0 && m_btn == 1) {
            entries = mentries;
            linechartlayout2.setVisibility(View.GONE);
            linechartlayout3.setVisibility(View.GONE);
            linechartlayout4.setVisibility(View.VISIBLE);
        } else if (b_btn == 0 && j_btn == 0 && m_btn == 0) {

            linechartlayout2.setVisibility(View.GONE);
            linechartlayout3.setVisibility(View.GONE);
            linechartlayout4.setVisibility(View.GONE);
        } else if (b_btn == 1 && j_btn == 1 && m_btn == 0) {   //
            entries = bentries;
            linechartlayout2.setVisibility(View.VISIBLE);
            linechartlayout3.setVisibility(View.VISIBLE);
            linechartlayout4.setVisibility(View.GONE);
        } else if (b_btn == 1 && j_btn == 0 && m_btn == 1) { // 2
            entries = bentries;
            linechartlayout2.setVisibility(View.VISIBLE);
            linechartlayout3.setVisibility(View.GONE);
            linechartlayout4.setVisibility(View.VISIBLE);
        } else if (b_btn == 0 && j_btn == 1 && m_btn == 1) { /// 3
            entries = rentries;
            linechartlayout2.setVisibility(View.GONE);
            linechartlayout3.setVisibility(View.VISIBLE);
            linechartlayout4.setVisibility(View.VISIBLE);
        } else if (b_btn == 1 && j_btn == 1 && m_btn == 1) { /// 3
            entries = bentries;
            linechartlayout2.setVisibility(View.VISIBLE);
            linechartlayout3.setVisibility(View.VISIBLE);
            linechartlayout4.setVisibility(View.VISIBLE);

        }

    }

    public void init() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        // GSON 컨버터를 사용하는 REST 어댑터 생성
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


    }

    public void Tongsin(String jiyeok) {   //통신

        Btongsin(jiyeok);
        Rtongsin(jiyeok);
        Mtongsin(jiyeok);
    }

    public void Btongsin(String today) { // 서버 데이터를 가지고 온다 파라미터는 불러올 테이블 이름

        // progressDialog.show();
        init();
        Atradegithub gitHub = retrofit.create(Atradegithub.class);
        Call<List<Daydatalistitem>> call = gitHub.contributors(today);
        call.enqueue(new Callback<List<Daydatalistitem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            // 성공시
            public void onResponse(Call<List<Daydatalistitem>> call, Response<List<Daydatalistitem>> result) {
                List<Daydatalistitem> contributors = result.body();
                // 받아온 리스트를 순회하면서

                Log.e("Test888", result.body().toString());

                for (Daydatalistitem contributor : contributors) {


                    String si = contributor.si;
                    int year = contributor.year;
                    int month = contributor.month;
                    int yearmonth = contributor.yearmonth;
                    int trade = contributor.trade;
                    String per = contributor.per;
                    String ym = contributor.ym;

                    int hightrade = contributor.hightrade;
                    int highyear = contributor.highyear;
                    int highmonth = contributor.highmonth;

                    int rowtarde = contributor.rowtrade;
                    int rowyear = contributor.rowyear;
                    int rowmonth = contributor.rowmonth;
                    String updatetime = contributor.updatetime;


                    Log.e("dhxodn88", "" + si + " / " + year + " / " + month + " / " + yearmonth + " ---> "
                            + trade + "(" + per + "%)" + " /  (최고거래건수 : " + highyear + "년 " + highmonth + "월 / " + hightrade + ")"
                            + " (최저거래건수 : " + rowyear + "년 " + rowmonth + "월 / " + rowtarde + " / 업데이트 시간 : " + updatetime + " / " + ym + ")");


                    float y = Float.parseFloat(ym);
                    float p = trade;
                    Object o = per;


                    Log.e("sizecheck", "" + y + " / " + p + " / " + o);
                    bentries.add(new Entry(y, p, o));


                }

                bentries_c = 1;
                Collections.sort(bentries, new EntryXComparator());
                entries = bentries;

                if (rentries_c == 1 && bentries_c == 1 && mentries_c == 1) {

                    Datasetting1();


                }


            }

            @Override
            public void onFailure(Call<List<Daydatalistitem>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "정보받아오기 실패 " + t, Toast.LENGTH_LONG)
//                        .show();

                Log.e("onFailure", "- > " + t);

            }
        });


    }

    public void Rtongsin(String jiyeok) { // 서버 데이터를 가지고 온다 파라미터는 불러올 테이블 이름

        // progressDialog.show();
        init();
        Atradegithub1 gitHub = retrofit.create(Atradegithub1.class);
        Call<List<Daydatalistitem>> call = gitHub.contributors(jiyeok);
        call.enqueue(new Callback<List<Daydatalistitem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            // 성공시
            public void onResponse(Call<List<Daydatalistitem>> call, Response<List<Daydatalistitem>> result) {
                List<Daydatalistitem> contributors = result.body();
                // 받아온 리스트를 순회하면서

                Log.e("Test888", result.body().toString());

                for (Daydatalistitem contributor : contributors) {


                    String si = contributor.si;
                    int year = contributor.year;
                    int month = contributor.month;
                    int yearmonth = contributor.yearmonth;
                    int trade = contributor.trade;
                    String per = contributor.per;
                    String ym = contributor.ym;

                    int hightrade = contributor.hightrade;
                    int highyear = contributor.highyear;
                    int highmonth = contributor.highmonth;

                    int rowtarde = contributor.rowtrade;
                    int rowyear = contributor.rowyear;
                    int rowmonth = contributor.rowmonth;
                    String updatetime = contributor.updatetime;


                    Log.e("rent", "" + si + " / " + year + " / " + month + " / " + yearmonth + " ---> "
                            + trade + "(" + per + "%)" + " /  (최고거래건수 : " + highyear + "년 " + highmonth + "월 / " + hightrade + ")"
                            + " (최저거래건수 : " + rowyear + "년 " + rowmonth + "월 / " + rowtarde + " / 업데이트 시간 : " + updatetime + " / " + ym + ")");


                    float y = Float.parseFloat(ym);
                    float p = trade;
                    Object o = per;


                    Log.e("sizecheck", "" + y + " / " + p + " / " + o);
                    rentries.add(new Entry(y, p, o));


                }

                rentries_c = 1;
                Collections.sort(rentries, new EntryXComparator());
                if (rentries_c == 1 && bentries_c == 1 && mentries_c == 1) {

                    Datasetting1();


                }


            }

            @Override
            public void onFailure(Call<List<Daydatalistitem>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "정보받아오기 실패 " + t, Toast.LENGTH_LONG)
//                        .show();

                Log.e("onFailure", "- > " + t);

            }
        });


    }


    public void Mtongsin(String jiyeok) { // 서버 데이터를 가지고 온다 파라미터는 불러올 테이블 이름

        // progressDialog.show();
        init();
        Atradegithub2 gitHub = retrofit.create(Atradegithub2.class);
        Call<List<Daydatalistitem>> call = gitHub.contributors(jiyeok);
        call.enqueue(new Callback<List<Daydatalistitem>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            // 성공시
            public void onResponse(Call<List<Daydatalistitem>> call, Response<List<Daydatalistitem>> result) {
                List<Daydatalistitem> contributors = result.body();
                // 받아온 리스트를 순회하면서

                Log.e("Test888", result.body().toString());

                for (Daydatalistitem contributor : contributors) {


                    String si = contributor.si;
                    int year = contributor.year;
                    int month = contributor.month;
                    int yearmonth = contributor.yearmonth;
                    int trade = contributor.trade;
                    String per = contributor.per;
                    String ym = contributor.ym;

                    int hightrade = contributor.hightrade;
                    int highyear = contributor.highyear;
                    int highmonth = contributor.highmonth;

                    int rowtarde = contributor.rowtrade;
                    int rowyear = contributor.rowyear;
                    int rowmonth = contributor.rowmonth;
                    String updatetime = contributor.updatetime;


                    Log.e("month", "" + si + " / " + year + " / " + month + " / " + yearmonth + " ---> "
                            + trade + "(" + per + "%)" + " /  (최고거래건수 : " + highyear + "년 " + highmonth + "월 / " + hightrade + ")"
                            + " (최저거래건수 : " + rowyear + "년 " + rowmonth + "월 / " + rowtarde + " / 업데이트 시간 : " + updatetime + " / " + ym + ")");


                    float y = Float.parseFloat(ym);
                    float p = trade;
                    Object o = per;


                    Log.e("sizecheck", "" + y + " / " + p + " / " + o);
                    mentries.add(new Entry(y, p, o));


                }

                mentries_c = 1;
                Collections.sort(mentries, new EntryXComparator());
                if (rentries_c == 1 && bentries_c == 1 && mentries_c == 1) {

                    Datasetting1();


                }


            }

            @Override
            public void onFailure(Call<List<Daydatalistitem>> call, Throwable t) {
//                Toast.makeText(MainActivity.this, "정보받아오기 실패 " + t, Toast.LENGTH_LONG)
//                        .show();

                Log.e("onFailure", "- > " + t);

            }
        });

    }

    public void btnGone() {


        if (bentries.size() > 0) {


            m1.setVisibility(View.VISIBLE);
        }


        if (rentries.size() > 0) {


            j1.setVisibility(View.VISIBLE);
        }


        if (mentries.size() > 0) {

            g1.setVisibility(View.VISIBLE);
        }


    }

    public void Datasetting1() {

        btnGone();

        Buyset();
        Rentset();
        Monthset();


//        max = bentries.get(bentries.size() - 1).getX();
//        min = bentries.get(0).getX();

        MaxX();
        //MaxY();


        Log.e("맥스민", "-->" + maxx + " / " + minx);
        float minus = maxx - minx;
        int i = (int) minus;


        if (i > 7) {

            xterm = 7;
        } else if (i > 2) {
            xterm = 3;

        }

        btncheck1();
        Datasetting2();


    }


    public void MaxX() {  //비지블


        if (rentries.get(rentries.size() - 1).getX() > bentries.get(bentries.size() - 1).getX()) {

            maxx = bentries.get(bentries.size() - 1).getX();
        } else if (rentries.get(rentries.size() - 1).getX() < bentries.get(bentries.size() - 1).getX()) {
            maxx = bentries.get(bentries.size() - 1).getX();
        } else if (rentries.get(rentries.size() - 1).getX() == bentries.get(bentries.size() - 1).getX()) {

            maxx = bentries.get(bentries.size() - 1).getX();
        }


        if (maxx > mentries.get(mentries.size() - 1).getX()) {


        } else {


            maxx = mentries.get(mentries.size() - 1).getX();
        }


        if (rentries.get(0).getX() > bentries.get(0).getX()) {


            minx = bentries.get(0).getX();
        } else if (rentries.get(0).getX() < bentries.get(0).getX()) {

            minx = rentries.get(0).getX();

        } else {

            minx = rentries.get(0).getX();
        }


        if (minx < mentries.get(0).getX()) {


        } else {


            minx = mentries.get(0).getX();
        }


    }


    public void MaxY() {  //비지블


        if (rentries.get(rentries.size() - 1).getY() > bentries.get(bentries.size() - 1).getY()) {

            maxy = bentries.get(bentries.size() - 1).getY();
        } else if (rentries.get(rentries.size() - 1).getY() < bentries.get(bentries.size() - 1).getY()) {
            maxy = bentries.get(bentries.size() - 1).getY();
        } else if (rentries.get(rentries.size() - 1).getY() == bentries.get(bentries.size() - 1).getY()) {

            maxy = bentries.get(bentries.size() - 1).getY();
        }


        if (maxy > mentries.get(mentries.size() - 1).getY()) {


        } else {


            maxy = mentries.get(mentries.size() - 1).getY();
        }


        if (rentries.get(0).getY() > bentries.get(0).getY()) {


            maxy = bentries.get(0).getY();
        } else if (rentries.get(0).getY() < bentries.get(0).getY()) {

            maxy = rentries.get(0).getY();

        } else {

            maxy = rentries.get(0).getY();
        }


        if (maxy < mentries.get(0).getY()) {


        } else {


            maxy = mentries.get(0).getY();
        }


    }


    public void Findview() {
        linechartday = 100;
        entries = new ArrayList<>();
        bentries = new ArrayList<>();
        rentries = new ArrayList<>();
        mentries = new ArrayList<>();
        marker = new MyMarkerView(this, R.layout.markerviewtext);
        lineData = new LineData();
        linechartlayout10 = (RelativeLayout) findViewById(R.id.linechartlayout10);
        linechartlayout2 = (RelativeLayout) findViewById(R.id.linechartlayout2);
        linechartlayout3 = (RelativeLayout) findViewById(R.id.linechartlayout3);
        linechartlayout4 = (RelativeLayout) findViewById(R.id.linechartlayout4);

        lineChart = (LineChart) findViewById(R.id.linechart);

        m1 = (CardView) findViewById(R.id.m1);
        j1 = (CardView) findViewById(R.id.j1);
        g1 = (CardView) findViewById(R.id.w1);

        m2 = (TextView) findViewById(R.id.m2);
        j2 = (TextView) findViewById(R.id.j2);
        g2 = (TextView) findViewById(R.id.w2);

        Linecharttext1 = (TextView) findViewById(R.id.Linecharttext1);
        Linecharttext2 = (TextView) findViewById(R.id.Linecharttext2);
        Linecharttext3 = (TextView) findViewById(R.id.Linecharttext3);
        Linecharttext100 = (TextView) findViewById(R.id.Linecharttext100);

        Linecharttype1 = (CardView) findViewById(R.id.Linecharttype1);
        Linecharttype2 = (CardView) findViewById(R.id.Linecharttype2);
        Linecharttype3 = (CardView) findViewById(R.id.Linecharttype3);
        Linecharttype100 = (CardView) findViewById(R.id.Linecharttype100);


        chartymd = (TextView) findViewById(R.id.chartymd);
        chartpirce = (TextView) findViewById(R.id.chartpirce);
        chartgunsu = (TextView) findViewById(R.id.chartgusnu);


        rchartymd = (TextView) findViewById(R.id.rchartymd);
        rchartpirce = (TextView) findViewById(R.id.rchartpirce);
        rchartgunsu = (TextView) findViewById(R.id.rchartgusnu);

        mchartymd = (TextView) findViewById(R.id.mchartymd);
        mchartpirce = (TextView) findViewById(R.id.mchartpirce);
        mchartgunsu = (TextView) findViewById(R.id.mchartgusnu);

        Linecharttype1.setOnClickListener(this::onClick);
        Linecharttype2.setOnClickListener(this::onClick);
        Linecharttype3.setOnClickListener(this::onClick);
        Linecharttype100.setOnClickListener(this::onClick);

        m1.setOnClickListener(this::onClick);
        j1.setOnClickListener(this::onClick);
        g1.setOnClickListener(this::onClick);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Timelimit() {


        if (linechartday == 100) {


            lineChart.setData(lineData);

            MaxX();

            xAxis.setLabelCount((int) xterm, true);

            xAxis.setAxisMinimum(minx);


            xAxis.setAxisMaximum(maxx);
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

            this.minx = maxx - 3; ///////// 최근 3년

            xAxis.setLabelCount(4, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);

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


            this.minx = maxx - 5;///////// 최근 5년

            xAxis.setLabelCount(5, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);
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


            this.minx = maxx - 10;///////// 최근 10년

            xAxis.setLabelCount(6, true);
            xAxis.setAxisMinimum(this.minx);
            xAxis.setAxisMaximum(maxx);

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

    public void Buyset() {

        BDataSet = new LineDataSet(bentries, "DataSet 1");

        BDataSet.setLineWidth(2);
        BDataSet.setCircleRadius(1);
        BDataSet.setCircleColor(Color.parseColor("#FFA1B4DC"));
        // lineDataSet.setCircleColorHole(Color.BLUE);
        BDataSet.setColor(Color.parseColor("#FFA1B4DC"));
        BDataSet.setDrawCircleHole(true);
        BDataSet.setDrawCircles(true);
        BDataSet.setDrawHorizontalHighlightIndicator(false);
        BDataSet.setDrawHighlightIndicators(false);
        BDataSet.setDrawValues(false);


        lineData.addDataSet(BDataSet);
    }

    @Override
    public void onBackPressed() { //앱 강제 종료
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backpressedTime + 2000) {
            finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Rentset() {

        RDataSet = new LineDataSet(rentries, "전세");
        RDataSet.setLineWidth(2);
        RDataSet.setCircleRadius(1);
        RDataSet.setCircleColor(getColor(R.color.j_btn));
        //lineDataSet2.setCircleColorHole(Color.parseColor("#FF4500"));
        RDataSet.setColor(getColor(R.color.j_btn));

        // lineDataSet2.setDrawCircleHole(true);
        RDataSet.setDrawCircles(true);
        RDataSet.setDrawHorizontalHighlightIndicator(false);
        RDataSet.setDrawHighlightIndicators(false);
        RDataSet.setDrawValues(false);

        lineData.addDataSet(RDataSet);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Monthset() {


        MDataSet = new LineDataSet(mentries, "월세");
        MDataSet.setLineWidth(2);
        MDataSet.setCircleRadius(1);
        MDataSet.setCircleColor(getColor(R.color.w_btn));
        //lineDataSet2.setCircleColorHole(Color.parseColor("#FF4500"));
        MDataSet.setColor(getColor(R.color.w_btn));

        // lineDataSet2.setDrawCircleHole(true);
        MDataSet.setDrawCircles(true);
        MDataSet.setDrawHorizontalHighlightIndicator(false);
        MDataSet.setDrawHighlightIndicators(false);
        MDataSet.setDrawValues(false);

        lineData.addDataSet(MDataSet);


    }

    public void Datasetting2() {

        Log.e("기간체크", "" + new TWPreference(this).getInt("기간", 100));
        Legend l = lineChart.getLegend();
        l.setEnabled(false);


        gigancheck();


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

        yLAxis.setAxisMinimum(0);


        Log.e("거래건수체킹", "-->" + bentries.get(bentries.size() - 1).getY() + " / " + rentries.get(rentries.size() - 1).getY() + " / " + mentries.get(mentries.size() - 1).getY());
        //  yLAxis.setAxisMaximum(max2 + 4);

        yLAxis.setLabelCount(6, true);

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

            case R.id.m1:

                if (b_btn == 1) { // offf

                    BDataSet.setVisible(false);
                    lineChart.invalidate();

                    linechartlayout2.setVisibility(View.GONE);


                    m1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
                    m2.setTextColor(getColor(R.color.Off_Tcolor));
                    b_btn = 0;

                } else {  // on

                    // Timelimit();
                    BDataSet.setVisible(true);
                    lineChart.invalidate();
                    if (infor == 1) {
                        linechartlayout2.setVisibility(View.VISIBLE);
                    }

                    m1.setCardBackgroundColor(getColor(R.color.m_btn));
                    m2.setTextColor(getColor(R.color.On_Textwcolor));

                    b_btn = 1;
                }

                break;

            case R.id.j1:


                if (j_btn == 1) { //off


                    RDataSet.setVisible(false);
                    lineChart.invalidate();
                    linechartlayout3.setVisibility(View.GONE);
                    j1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
                    j2.setTextColor(getColor(R.color.Off_Tcolor));


                    j_btn = 0;


                } else {  // on

                    // Timelimit();
                    RDataSet.setVisible(true);
                    lineChart.invalidate();
                    if (infor == 1) {
                        linechartlayout3.setVisibility(View.VISIBLE);
                    }
                    j1.setCardBackgroundColor(getColor(R.color.j_btn));
                    j2.setTextColor(getColor(R.color.On_Textwcolor));

                    j_btn = 1;

                }


                /////////////////////////////////////////////////////////////////////////
                break;

            case R.id.w1:


                if (m_btn == 1) {     //   offf


                    MDataSet.setVisible(false);
                    lineChart.invalidate();

                    linechartlayout4.setVisibility(View.GONE);

                    g1.setCardBackgroundColor(getColor(R.color.off_Btcolor));
                    g2.setTextColor(getColor(R.color.Off_Tcolor));

                    m_btn = 0;


                } else {
                    // Timelimit();
                    MDataSet.setVisible(true);
                    lineChart.invalidate();

                    if (infor == 1) {
                        linechartlayout4.setVisibility(View.VISIBLE);
                    }
                    g1.setCardBackgroundColor(getColor(R.color.w_btn));
                    g2.setTextColor(getColor(R.color.On_Textwcolor));


                    m_btn = 1;


                }


                break;

        }
    }
}