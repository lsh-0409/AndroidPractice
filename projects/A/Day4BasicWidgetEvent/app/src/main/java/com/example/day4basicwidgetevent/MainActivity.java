package com.example.day4basicwidgetevent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private Button btnHide;
    private LinearLayout linear1;
    private CheckBox chk1;
    private Switch sw1;
    private ToggleButton tog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHide = findViewById(R.id.btnHide);
        linear1 = findViewById(R.id.linear1);
        chk1 = findViewById(R.id.chk1);
        sw1 = findViewById(R.id.sw1);
        tog1 = findViewById(R.id.tog1);

//      체크박스에 체크를 하면 매개변수 b에 상태값이 넘어옴
        chk1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    Toast.makeText(getApplicationContext(), "체크됨!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "해제됨!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            }
        });

        tog1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//               켜졌을 때 또는 꺼졌을 때 코드
            }
        });
    }
//  자바에서 속성을 설정, 제어 가능
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnHide) {
            btnHide.setVisibility(View.GONE);
//          Invisible은 스테틱이다. View의 상수이기 때문에
            linear1.setBackgroundColor(Color.CYAN);
        } else if (id == R.id.btnShow) {
            btnHide.setVisibility(View.VISIBLE);
        } else if (id == R.id.btnCheck) {
            if (chk1.isChecked() == true) {
//              리스너 설정
                Toast.makeText(getApplicationContext(), "안드로이드 체크됨", Toast.LENGTH_SHORT).show();
            }
        }
    }
}