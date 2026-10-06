package com.example.day10dataintentsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class RecvDataActivity extends AppCompatActivity {
    TextView tvRecvValue;
    int age;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recv_data);

        tvRecvValue = findViewById(R.id.tvRecvValue);

        Intent intent = getIntent();
        // 메인액티비티에서 전송한 Intent를 수신된 Intent 객체를 얻어오는 함수

        age = intent.getIntExtra("AGE", 0);
        tvRecvValue.setText("" + age); //문자열로 변환되어 textview에 나타남
    }

    public void onClick(View view) {
        int id = view.getId();
        
        if (id == R.id.btnReturn){
//          회신용 intent객체를 생성해야 함.
            Intent out = new Intent(getApplicationContext(), MainActivity.class);
//          돌아갈 액티비티를 지정해야 됨 즉 context,클래스이름이 필요함
            out.putExtra("rAge", (age+1));
//          Activity.RESULT_OK를 사용하는 게 더 좋음  
            setResult(Activity.RESULT_OK, out); // 회신이 이루어짐
            finish(); // 현재 창을 닫기
        }
    }
}