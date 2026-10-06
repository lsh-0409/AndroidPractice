package com.example.day14servivesample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      명시적 인텐트 사용해 서비스클래스 명시  
        intent = new Intent(getApplicationContext(), MyService.class);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnStart){
            
//          서비스시작  
            startService(intent);
        } else if (id == R.id.btnStop) {
            
//          서비스종료  
            stopService(intent);
        }
    }
}