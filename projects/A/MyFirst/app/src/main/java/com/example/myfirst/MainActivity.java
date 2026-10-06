package com.example.myfirst;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
// CompatActivity에게 상속을 받은 MainActivity클래스
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //OnCreate메서드는 자바에 Main메서드처럼 activity코드의 시작지점 activity 생성시 최초로 호출되는 메서드
//오버라이드 되어 있기 때문에 상속 관계인 부모 AppCompatActivity클래스에 있는 OnCreate메서드가 호출되는 것이 아닌
//자식 클래스인 MainActivity에 있는 OnCreate메서드가 우선적으로 호출된다.
        super.onCreate(savedInstanceState);
        setTitle("첫번째 앱!");
        setContentView(R.layout.activity_main);
//        중요 view를 가져다가 content를 세팅하겠다. 자바 R = resource를 의미 activity_main를 layout 펼친다
//        즉 리소스 내에 여러 레이아웃 파일 중 activity_main파일을 뷰로 설정하겠다는 의미 액티비티가 UI를 관리한다.
    }
}