package com.example.day3eventprocess;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//    멤버로 선언
    private Button btn1; // 위젯 변수 선언
//    아이디와 위젯변수를 동일하게 하는 것을 권장
    // 질문: 변수 선언 시 꼭 해야하는 작업은? => 초기화필요
    // 멤버 변수는 자동 초기화 된다.
    // btn1 역시 자동 초기화 된다. 이때 값은??? => null
    private Button btn5;
    private EditText edtNum;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNum = findViewById(R.id.edtNum);
//      리스너가 필요없음 이유는 EditText를 눌러서 이벤트를 처리하는 것이 아닌 버튼을 눌러서 처리하기 때문에
//      Null포인트 exception발생시 결합이 안된 것으로 결합을 해주어야 함.

//      위젯변수와 결합 / 여기서 Id는 R.java에서 찾아옴
        btn1 = findViewById(R.id.btn1);
        tvResult = findViewById(R.id.tvResult);
        btn5 = findViewById(R.id.btn5);

//      리스너 설정 시험문제 리스너의 구성에 대해 설명하시오.
//      괄호안에는 클래스가 아닌 인터페이스임 그래서 객체생성이 불가 이유 추상메서드 메서드 선언만 있고 구현이 없기 때문
//      객체방식은 XML에서 리스너설정 / 자바에서 핸들러 역할을 함

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvResult.setText("읽어온 값: "); // 하드 코딩: 고정 안하는 방법
                // 변환 숫자를 읽기 때문에 get사용 설정은 set
                String strNum = edtNum.getText().toString();
//Toast.makeText(getApplicationContext(), "입력 숫자는 " + strNum + "입니다.", Toast.LENGTH_LONG).show();
                tvResult.setText("읽어온 값: " + strNum);
                edtNum.setText("");
            }
        });
//      객체생성를 하기 위해 추상메서드를 오버라이드를 함 / onclick메서드는 핸들러이다. 이벤트 동작코드
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "버튼5가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    최신버전에서는 switch-case문을 쓰지 못한다. 그이유는 리소스는 정수를 이용하는데 switch문은 상수를 이용하기 때문에
    public void onClick(View view) {
        if (view.getId() == R.id.btn3)
            Toast.makeText(getApplicationContext(), "버튼3이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        else if (view.getId() == R.id.btn4) {
            Toast.makeText(getApplicationContext(), "버튼4이 클릭되었습니다.", Toast.LENGTH_SHORT).show();
        }
    }
}