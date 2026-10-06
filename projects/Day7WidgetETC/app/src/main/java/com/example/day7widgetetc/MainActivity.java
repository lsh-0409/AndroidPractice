package com.example.day7widgetetc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private Chronometer chrono1;
    private TextView tvTime, tvSelect;
    private CalendarView calendar;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        chrono1 = findViewById(R.id.chrono1);
        tvTime = findViewById(R.id.tvTime);
        calendar = findViewById(R.id.calendar);
        tvSelect = findViewById(R.id.tvSelect);

        // 년월일 값을 오늘 날짜 값으로 초기화 해야한다.
        // LocalDate 클래스, SimpleDateFormat 클래스, Calendar 클래스 등을 이용하여 오늘 날짜를 얻어올 수 있다.
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            LocalDate today = LocalDate.now();
//            Toast.makeText(getApplicationContext(), today + "", Toast.LENGTH_SHORT).show();
//            this.year = today.getYear();
//            this.month = today.getMonthValue();
//            this.day = today.getDayOfMonth();
//        }

//        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
//        Toast.makeText(getApplicationContext(), date.toString(), Toast.LENGTH_SHORT).show();

        String date = new SimpleDateFormat("yyyy").format(new Date());
        year = Integer.parseInt(date);
        date = new SimpleDateFormat("MM").format(new Date());
        month = Integer.parseInt(date);
        date = new SimpleDateFormat("dd").format(new Date());
        day = Integer.parseInt(date);

        calendar.setOnDateChangeListener(listener);
    }

    private CalendarView.OnDateChangeListener listener = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
//            tvSelect.setText("선택 날짜: " + i + "년 " + (i1 + 1) + "월 " + i2 + "일");
            year = i;
            month = i1 + 1;
            day = i2;
        }
    };

    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnStart){
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);

            chronoStart();
        } else if(id == R.id.btnStop){
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);

            chronoStop();
        }
    }

    private void chronoStart(){
        chrono1.setBase(SystemClock.elapsedRealtime());
        chrono1.start();
        chrono1.setTextColor(Color.RED);
    }

    private void chronoStop(){
        chrono1.stop();
        chrono1.setTextColor(Color.BLUE);
        String strTime = chrono1.getText().toString();
        strTime = strTime.replace("시간 측정: ", "");
        String[] arrTime = strTime.split(":");
//        tvTime.setText("소요 시간: " + strTime.toString());
//        tvTime.setText("소요 시간: " + arrTime[0] + "분 " + arrTime[1] + "초");
        // 실습 문제 : 분에 값이 00분이면 초만 표시해보자!!!
        int len = arrTime.length;

        if(arrTime[0].equals("00")){
            tvTime.setText("소요 시간: " + arrTime[len - 1] + "초");
        } else {
            tvTime.setText("소요 시간: " + arrTime[len - 2] + "분 " + arrTime[len - 1] + "초");
        }

        tvSelect.setText("선택 날짜: " + year + "년 " + month + "월 " + day + "일");
    }


}










