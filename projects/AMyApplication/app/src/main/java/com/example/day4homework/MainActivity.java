package com.example.day4homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//  위젯변수 생성
    private Button btnCheck;
    private RadioGroup rgroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      위젯변수 결합
        btnCheck = findViewById(R.id.btnCheck);
        rgroup = findViewById(R.id.rgroup);
    }
//  라디오 버튼이 눌린 후 확인 버튼이 눌려야 이벤트 발생
    public void OnClick(View view) {
        int id = view.getId();
        if (id == R.id.btnCheck) {
//          체크된 라디오버튼을 라디오그룹이 읽어서 변수에 저장
            int res = rgroup.getCheckedRadioButtonId();
//          체크된 과목명을 subject에 저장
            String subject = "";
//          변수에 저장된 값이 해당 라디오버튼과 일치하면 과목명 저장
            if (res == R.id.rdo1) {
                subject = "자바";
            } else if (res == R.id.rdo2) {
                subject = "C++";
            } else if (res == R.id.rdo3) {
                subject = "C#";
            }
//          최종적으로 버튼을 누르면 해당 과목명 이벤트출력
            Toast.makeText(getApplicationContext(), "과목 체크: " + subject, Toast.LENGTH_SHORT).show();
        }
    }
}