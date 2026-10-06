package com.example.day9changeactivitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnCallSecond){
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            startActivity(intent); // 기초 암기필요
        }
    }
}