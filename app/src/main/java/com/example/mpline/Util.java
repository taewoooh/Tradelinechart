package com.example.mpline;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.data.Entry;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * Created by taewoo on 2020-02-18.
 */

public class Util {
    Context context;
    ProgressBar progress;

    public Util(Context context) {
        this.context = context;
        progress = new ProgressBar(context);
    } //기능 모음집  return 메서드만

    public Util() {
    } //기

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Getday() { //현재 날짜구하기


        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(currentTime);


        return date_text;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Getda() { //현재 날짜구하기


        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(currentTime);


        return date_text;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int Gettime() { //현재 날짜구하기
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

        Date now = new Date();
        String nowTime1 = sdf1.format(now);

        long l = Long.parseLong(nowTime1);
        int i3 = Long.valueOf(l).intValue();


        return i3;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Long Gettime2() { //현재 날짜구하기
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");

        Date now = new Date();
        String nowTime1 = sdf1.format(now);

        long l = Long.parseLong(nowTime1);


        return l;

    }

    public String Dday(String st, String en) { //////////

        Log.e("Dday", "" + st + " / " + en);


        String[] day1 = st.split("\\.");

        String ymd1;
        String ye1 = day1[0];
        String mo1 = null;
        String da1 = null;


        if (day1[1].length() == 1) {


            mo1 = "0" + day1[1];


        } else {
            mo1 = day1[1];

        }

        if (day1[2].length() == 1) {


            da1 = "0" + day1[2];


        } else {

            da1 = day1[2];
        }


        ymd1 = ye1 + mo1 + da1;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        en = "20" + en;
        String[] day2 = en.split("\\.");

        String ymd2;
        String ye2 = day2[0];
        String mo2 = null;
        String da2 = null;


        if (day2[1].length() == 1) {


            mo2 = "0" + day2[1];


        } else {
            mo2 = day2[1];

        }

        if (day2[2].length() == 1) {


            da2 = "0" + day2[2];


        } else {

            da2 = day2[2];
        }


        ymd2 = ye2 + mo2 + da2;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        String s = null;
        long y = 0;
        long li = 0;
        long m = 0;
        long d = 0;


        long diffDate = 0;
        long diffMonth = 0;
        long diffYear = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");

        String start = ymd1;
        String end = ymd2;

        try {
            Date toDate = format.parse(ymd1);
            Date fromDate = format.parse(ymd2);

            long baseDay = 24 * 60 * 60 * 1000;    // 일
            long baseMonth = baseDay * 30;        // 월
            long baseYear = baseMonth * 12;        // 년

            // from 일자와 to 일자의 시간 차이를 계산한다.
            long calDate = fromDate.getTime() - toDate.getTime();

            // from 일자와 to 일자의 시간 차 값을 하루기준으로 나눠 준다.
            diffDate = calDate / baseDay;
            diffMonth = calDate / baseMonth;
            diffYear = calDate / baseYear;

            System.out.println("diffDate : " + diffDate + "일");
            System.out.println("diffMonth : " + diffMonth + "월");
            System.out.println("diffYear : " + diffYear + "년");

        } catch (Exception e) {
            // TODO: handle exception
        }


        String year = String.valueOf(diffYear);


        if (diffDate >= 365) {

            y = diffDate / 365;
            li = diffDate % 365;

            if (li >= 30) {

                m = li / 30;
                d = li % 30;

                // s = y + "년 " + m + "개월 " + d + "일";
                s = "약 " + y + "년 " + m + "개월만에";

            } else {


                d = li;

                s = "약 " + y + "년만에";
            }


        } else if (diffDate >= 30) {

            m = diffDate / 30;
            d = diffDate % 30;

            //s = m + "개월 " + d + "일";
            s = "약 " + m + "개월만에";

        } else {


            d = diffDate;
            s = d + "일만에";
        }

        Log.e("Dday2", "-----------------------------------------------------------" +
                "\nstart -> " + start + " / end - > " + end + "\n"
                + diffYear + "년 / " + diffMonth + "개월 /" + diffDate + " 일  \n" +
                "" + y + "년 / " + m + "개월 / " + d + "일\n"
                + "total - > " + s);
        return s;
    }
    public int Selectcheck() {

        int switchcheck = new TWPreference(context).getInt("서울특별시", 0)
                + new TWPreference(context).getInt("부산광역시", 0)
                + new TWPreference(context).getInt("대구광역시", 0)
                + new TWPreference(context).getInt("인천광역시", 0)
                + new TWPreference(context).getInt("광주광역시", 0)
                + new TWPreference(context).getInt("대전광역시", 0)
                + new TWPreference(context).getInt("울산광역시", 0)
                + new TWPreference(context).getInt("세종특별자치시", 0)
                + new TWPreference(context).getInt("경기도", 0)
                + new TWPreference(context).getInt("강원도", 0)
                + new TWPreference(context).getInt("충청북도", 0)
                + new TWPreference(context).getInt("충청남도", 0)
                + new TWPreference(context).getInt("전라북도", 0)
                + new TWPreference(context).getInt("전라남도", 0)
                + new TWPreference(context).getInt("경상북도", 0)
                + new TWPreference(context).getInt("경상남도", 0)
                + new TWPreference(context).getInt("제주특별자치도", 0);


        return switchcheck;

    }
    public int Dday2(String st) { //21.2.3 --- > 210203 날짜를 숫자로 변환해서 정렬한다
        Log.e("Dday2", "" + st);


        String[] day1 = st.split("\\.");

        String ymd1;
        String ye1 = day1[0];
        String mo1 = null;
        String da1 = null;


        if (day1[1].length() == 1) {


            mo1 = "0" + day1[1];


        } else {
            mo1 = day1[1];

        }

        if (day1[2].length() == 1) {


            da1 = "0" + day1[2];


        } else {

            da1 = day1[2];
        }


        ymd1 = ye1 + mo1 + da1;

        return Integer.parseInt(ymd1);

    }

    public float MaxY(List<Entry> value) {


        List<Entry> entries = value;


        float v = entries.get(0).getY();

        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).getY() > v) {
                v = entries.get(i).getY();
            }
        }


        return v;
    }

    public float MinY(List<Entry> value) {


        List<Entry> entries = value;


        float v = entries.get(0).getY();

        for (int i = 1; i < entries.size(); i++) {
            if (entries.get(i).getY() < v) {
                v = entries.get(i).getY();
            }
        }


        return v;
    }


    public int Doublechange(String name, double v) { //더블을 int로 형변환
        String s = String.valueOf(v);
        s = s.replaceAll("-", "");
        String s1 = s.substring(s.lastIndexOf(".") + 1);
        if (s1.length() == 1) {


            s = s + "0";

        }

        BigDecimal bigDecimal1 = new BigDecimal(String.valueOf(Double.parseDouble(s)));
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(100));
        BigDecimal s2 = bigDecimal1.multiply(bigDecimal2);


        Log.e("decline1", " : " + name + " / " + s + " / " + s2.intValue() + " / " + s1.length());


        return s2.intValue();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public int Calldate(String dd) throws ParseException {

        Log.e("Calldate", "" + dd);

        String[] day = dd.split("\\.");

        String ymd;
        String y = day[0];
        String m = null;
        String d = null;


        if (day[1].length() == 1) {


            m = "0" + day[1];


        } else {
            m = day[1];

        }

        if (day[2].length() == 1) {


            d = "0" + day[2];


        } else {

            d = day[2];
        }


        ymd = y + m + d;


        Date format1 = new SimpleDateFormat("yyyyMMdd").parse(Getday());
        Date format2 = new SimpleDateFormat("yyyyMMdd").parse(ymd);

        long diffSec = (format1.getTime() - format2.getTime()) / 1000; //초 차이
        long diffMin = (format1.getTime() - format2.getTime()) / 60000; //분 차이
        long diffHor = (format1.getTime() - format2.getTime()) / 3600000; //시 차이
        long diffDays = diffSec / (24 * 60 * 60); //일자수 차이

        System.out.println(diffSec + "초 차이");
        System.out.println(diffMin + "분 차이");
        System.out.println(diffHor + "시 차이");
        System.out.println(diffDays + "일 차이");


        return (int) diffDays;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Calldate2(String st) throws ParseException {

        String ymd2;

        String[] day1 = st.split("\\.");

        String ymd1;
        String ye1 = day1[0];
        String mo1 = null;
        String da1 = null;


        if (day1[1].length() == 1) {


            mo1 = "0" + day1[1];


        } else {
            mo1 = day1[1];

        }

        if (day1[2].length() == 1) {


            da1 = "0" + day1[2];


        } else {

            da1 = day1[2];
        }


        ymd1 = ye1 + mo1 + da1;

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        Log.e("getday", "" + Getday());


        ymd2 = Getday();

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        String s = null;
        long y = 0;
        long li = 0;
        long m = 0;
        long d = 0;


        long diffDate = 0;
        long diffMonth = 0;
        long diffYear = 0;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyyMMdd");

        String start = ymd1;
        String end = ymd2;

        try {
            Date toDate = format.parse(ymd1);
            Date fromDate = format.parse(ymd2);

            long baseDay = 24 * 60 * 60 * 1000;    // 일
            long baseMonth = baseDay * 30;        // 월
            long baseYear = baseMonth * 12;        // 년

            // from 일자와 to 일자의 시간 차이를 계산한다.
            long calDate = fromDate.getTime() - toDate.getTime();

            // from 일자와 to 일자의 시간 차 값을 하루기준으로 나눠 준다.
            diffDate = calDate / baseDay;
            diffMonth = calDate / baseMonth;
            diffYear = calDate / baseYear;

            System.out.println("diffDate : " + diffDate + "일");
            System.out.println("diffMonth : " + diffMonth + "월");
            System.out.println("diffYear : " + diffYear + "년");

        } catch (Exception e) {
            // TODO: handle exception
        }


        String year = String.valueOf(diffYear);


        if (diffDate >= 365) {

            y = diffDate / 365;
            li = diffDate % 365;

            if (li >= 30) {

                m = li / 30;
                d = li % 30;

                // s = y + "년 " + m + "개월 " + d + "일";
                s = y + "년 " + m + "개월전";

            } else {


                d = li;

                s = y + "년전";
            }


        } else if (diffDate >= 30) {

            m = diffDate / 30;
            d = diffDate % 30;

            //s = m + "개월 " + d + "일";
            s = m + "개월전";

        } else {


            d = diffDate;
            s = d + "일전";
        }

        Log.e("Dday2", "-----------------------------------------------------------" +
                "\nstart -> " + start + " / end - > " + end + "\n"
                + diffYear + "년 / " + diffMonth + "개월 /" + diffDate + " 일  \n" +
                "" + y + "년 / " + m + "개월 / " + d + "일\n"
                + "total - > " + s);
        return s;

    }

    public void ShowSnackbar(RelativeLayout v, String message){

        Snackbar snackbar = Snackbar.make(v,message, Snackbar.LENGTH_LONG);

        View snackbarView = snackbar.getView();
        TextView snackbarText = (TextView) snackbarView.findViewById(com.google.android.material.R.id.snackbar_text);
        //snackbarText.setTextColor(Color.BLUE);
        snackbarText.setTextSize(13);

        snackbarView.setBackgroundColor(Color.parseColor("#BF000000"));

        snackbar.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Getday2() { //현재 날짜구하기


        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyyMM", Locale.getDefault()).format(currentTime);


        return date_text;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String Getday3() { //현재 날짜구하기


        Date currentTime = Calendar.getInstance().getTime();
        String date_text = new SimpleDateFormat("yyyy", Locale.getDefault()).format(currentTime);


        return date_text;

    }

    public String AreaChange(String area) { //평형 바꾸기
        double value;
        value = Double.valueOf(area) / 2.45;
        area = String.valueOf(value);

        try {
            Double aDouble = Double.parseDouble(area);
            return String.format("%.0f", aDouble);  // 원하는 자릿수 1자리 = %.1f  2자리 %.2f
        } catch (Exception e) {
            return "0";
        }


    }

    public String Priceedit(String price) {  // 금액 한글로 변경하기

        Log.e("taewoooh88", "" + price);


        if (price == null || price.isEmpty()) {
            return null;
        } else {


            double db = Double.valueOf(price.replaceAll("\\,", ""));//쉼표 제거하기


            DecimalFormat d = new DecimalFormat("#,####");

            String[] unit = new String[]{"", "억 "};
            String[] str = d.format(db).split(",");
            String result = "";
            int cnt = 0;
            for (int i = str.length; i > 0; i--) {
                if (Integer.parseInt(str[i - 1]) != 0) {
                    result = String.valueOf(Integer.parseInt(str[i - 1])) + unit[cnt] + result;
                }
                cnt++;
            }


            return result;
        }
    }

    public String Priceedit2(String price) { //14.2역 // 금액 한글로 변경하기
        String[] s;
        String s1;
        String s2;
        Log.e("taewoooh88", "" + price);


        if (price == null || price.isEmpty()) {
            return null;
        } else {


            double db = Double.valueOf(price.replaceAll("\\,", ""));//쉼표 제거하기


            DecimalFormat d = new DecimalFormat("#,####");

            String[] unit = new String[]{"", "억"};
            String[] str = d.format(db).split(",");
            String result = "";
            int cnt = 0;
            for (int i = str.length; i > 0; i--) {
                if (Integer.parseInt(str[i - 1]) != 0) {
                    result = String.valueOf(Integer.parseInt(str[i - 1])) + unit[cnt] + result;
                }
                cnt++;
            }


            if (String.valueOf(result.charAt(result.length() - 1)).equals("억")) {


            } else if (Integer.parseInt(price) < 10000) {

            } else {
                result = result.replace("억", ".");

                int i = result.indexOf(".");
                String substr1 = result.substring(i + 1);
                Log.e("하하하1", "" + result + " / " + i + " / " + substr1);

                s1 = result.substring(0, i + 1);

                if (Integer.parseInt(substr1) < 1000) {

                    Log.e("하하하2", "" + result + " / " + i + " / " + substr1 + " / " + s1);
                    result = s1.replace(".", "억");
                } else {

                    char firstCharacter = substr1.charAt(0);
                    s2 = String.valueOf(firstCharacter);

                    result = s1 + s2 + "억";

                }

            }


            return result;
        }
    }

    public String Gappricechange(String price) {
        String answer = null;
        int minus = 0;

        Log.e("solution13-1", "" + price.length());


        if (price.contains("-")) { //마이너스가 있으면 뗏다가 다시붙혀주겠따


            minus = 1;
            price = price.replace("-", "");


        }
        Log.e("solution13-1-1", "" + price);

        if ("10000000" == price) {
            answer = "1000억";


        } else if (price.equals("0")) {

            answer = "0";

        } else if (price.length() > 4) {
            String s;
            String s1;
            s = price.substring(price.length() - 4, price.length());

            Log.e("solution13-2", "" + s);
            s1 = price.replace(s, "");
            Log.e("solution13-3", "" + s1 + " / " + Integer.parseInt(s));


            if (Integer.parseInt(s) == 0) {
                answer = s1 + "억";

            } else { // 0005 5만원 단위일경우
                String[] strings = s.split("");


                for (int k = 0; k < strings.length; k++) {

                    if (!strings[k].equals("0")) {


                        answer = s1 + "억 " + s.substring(k);
                        break;

                    }


                }


            }
        } else if (price.length() <= 4) {
            Log.e("solution13-4-1", "" + answer);
            answer = price;

        }

        if (minus == 1) {

            answer = "-" + answer;
        }

        Log.e("solution13-5", "" + answer);
        return answer;
    }


    public String Ymd(String year, String moonth, String day) { // 년도 점 붙히기

        String ymd;

        year = year.substring(2);

        ymd = year + "." + moonth + "." + day;


        return ymd;
    }


    public List Jungbok(ArrayList arlList) { // 중복제거 및 순차 정렬 함수

        HashSet set = new HashSet();

        List newList = new ArrayList();

        for (Iterator iter = arlList.iterator(); iter.hasNext(); ) {

            Object element = iter.next();

            if (set.add(element)) newList.add(element);

        }

        arlList.clear();

        arlList.addAll(newList);


        return arlList;

    }

    public String Daygagong(String hightyear, String hightmonth, String hightday) {


        String daygagong;

        daygagong = hightyear + "." + hightmonth + "." + hightday;


        return daygagong;
    }


    public int Singogaday(String year, String month, String day, int chaik) {
        String y;
        String m;
        String d;
        int i;


        if (month.length() == 1) {


            m = "0" + month;


        } else {

            m = month;

        }


        if (day.length() == 1) {


            d = "0" + day;


        } else {

            d = day;

        }


        String s = year + m + d;

        i = Integer.parseInt(s);

        //i = i + chaik;

        if (chaik == 0) {

            i = 0;
        }

        Log.e("Singogaday", year + " / " + m + " / " + d + " / " + s);


        return i;
    }


    public String Yearchange(String month) {

        if (month.contains("91")) {
            month = month.replace("91", "10");

        } else if (month.contains("92")) {
            month = month.replace("92", "11");
        } else if (month.contains("93")) {
            month = month.replace("93", "12");

        }

        return month;

    }

    public String Pricechange(float price) {

        float v;
        int i;
        String s;


        v = price * 10000;

        i = (int) v;
        DecimalFormat decimalFormat = new DecimalFormat("###,###");
        s = String.valueOf(decimalFormat.format(i));
        s = Priceedit(s);


        return s;

    }


}