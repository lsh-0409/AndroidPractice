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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private Chronometer chrono1;
    private TextView tvTime;
    private CalendarView calendar;
    private int year, month, date;
//  객체 생성시 해당 자료형으로 자동 초기화 int이니 0으로 초기화
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        chrono1 = findViewById(R.id.chrono1);
        tvTime = findViewById(R.id.tvTime);
        calendar = findViewById(R.id.calendar);
//      날짜를 바뀔 때마다 듣는 리스너설정
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
//          i = 년, i1 = 월, i2 = 일
      public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
           year  = i;
           month = i1 + 1;
           date = i2;
            }
        });
        getToday();
    }
//  오늘 년, 월, 일, 시, 분, 초를 가져오는 함수
    private void  getToday(){
//      문제 오늘 날짜를 얻어오는 코드를 작성
//      멤버 변수에 해당 값을 설정하도록 작성하시오.
//      LocalDate 클래스 사용방법, SimpleDateFormat 클래스 사용 방법
//      Date 클래스를 이용하는 방법
//      LocalDate은 자바 8버전과 안드로이드 26버전 이상부터 사용가능 이외에 버전에서 작동X
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            LocalDate today = LocalDate.now();
//            year = today.getYear();
//            month = today.getMonth();
//            date = today.getDayOfMonth();
//        }
//      SimpleDateFormat 클래스를 권장
//      문자열
//      다른 언어도 마찬가지로 SimpleDateFormat에서 월을 가져올 때 0부터 시작하기 때문에 +1을 수정해야함.
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String month = new SimpleDateFormat("MM").format(new Date());
        String date = new SimpleDateFormat("dd").format(new Date());
        this.year = Integer.parseInt(year);
        this.month = Integer.parseInt(month);
        this.date = Integer.parseInt(date);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnStart){
            btnStart.setVisibility(View.GONE);
            btnStop.setVisibility(View.VISIBLE);
            startButton();
        } else if (id == R.id.btnStop) {
            btnStart.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.GONE);
            stopButton();
        }
    }
//  추가코드 버튼을 보내면 크로노미터에
    private void startButton(){
//       크로노미터 설정
        chrono1.setBase(SystemClock.elapsedRealtime());
//       시작
        chrono1.start();
//      시간이 흐름을 보기위해
        chrono1.setTextColor(Color.RED);
//      총 걸린 시간을 얻어오자!
    }
    private void stopButton(){
//      멈춤
        chrono1.stop();
        chrono1.setTextColor(Color.BLACK);
//      총 소요 시간 정보를 얻어보자!
        String str = chrono1.getText().toString();
//      불필요한 글씨 제거 필요한 글씨만 얻어오기
        String strTime = str.replace("측정 시간: ", "");
        String[] timeinfo = strTime.split(":");
        tvTime.setText("소요 시간: " + timeinfo[0] + "분 " + timeinfo[1] + "초\n" +
                "선택 날짜: " + year + "년 " + month + "월 " + date +"일");
    }
}