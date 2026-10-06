package com.example.day10homework;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    TextView tvShow;
    int input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tvShow = findViewById(R.id.tvShow);

        Intent intent = getIntent();
        input = intent.getIntExtra("Input", 0);
        tvShow.setText("" + input);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnRec){
            Intent outinent = new Intent();
            outinent.putExtra("Input", input+1);
            setResult(Activity.RESULT_OK, outinent);
            finish();
        }
    }
}