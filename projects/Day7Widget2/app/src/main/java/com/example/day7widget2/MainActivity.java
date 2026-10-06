package com.example.day7widget2;

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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private Chronometer chrono1;
    private TextView tvTime;
    private CalendarView calendar;
    private int year;
    private int month;
    private int date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        chrono1 = findViewById(R.id.chrono1);
        tvTime = findViewById(R.id.tvTime);
        calendar = findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                year = i;
                month = i1 + 1;
                date = i2;
            }
        });

        getToday();

    }

    private void getToday(){
        // 문제!!!! 오늘 날짜를 얻어오는 코드를 작성하시오.
        // 멤벼 변수에 해당 값을 설정하도록 작성하시오.
        // LocalDate 클래스 사용방법, SimpleDateFormat 클래스 사용 방법
        // Date 클래스를 이용하는 방법
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            LocalDate today = LocalDate.now();
//            year = today.getYear();
//        }
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("MM").format(new Date());
        String day = new SimpleDateFormat("dd").format(new Date());
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.date = Integer.parseInt(day);
    }

    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btnStart){
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);

            startButton();
        } else if (id == R.id.btnStop) {
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);

            stopButton();
        }
    }

    private void startButton(){
        // 크로노미터 설정
        chrono1.setBase(SystemClock.elapsedRealtime());
        // 시작
        chrono1.start();
        chrono1.setTextColor(Color.RED);
    }

    private void stopButton(){
        // 중지
        chrono1.stop();
        chrono1.setTextColor(Color.BLACK);
        // 총 소요 시간 정보를 얻어오자!!!
        String str = chrono1.getText().toString();
        String strTime = str.replace("측정 시간: ", "");
        String[] timeInfo = strTime.split(":");
        tvTime.setText("소요 시간: " + timeInfo[0] + "분 " + timeInfo[1] + "초\n" + 
                "선택 날짜: " + year + "년 " + month + "월 " + date + "일");
    }

}









