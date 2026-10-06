package com.example.day10dataintentsample;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText edtAge;
    TextView tvReturnValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtAge = findViewById(R.id.edtAge);
        tvReturnValue = findViewById(R.id.tvReturnValue);
    }
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnSendData){
            String str = edtAge.getText().toString();

//        문자로 꺼내온 값을 정수로 변환, 원래라면 예외처리필요 하지만 edtText에 숫자만 들어오게 해서 괜찮음
            int age = Integer.parseInt(str);
            Intent intent = new Intent(getApplicationContext(), RecvDataActivity.class);
            intent.putExtra("AGE", age);
//          제목이 AGE 값은 age
            startActivity(intent);
        } else if (id == R.id.btnSendDataForReturn) {
//        위에 있는 코드를 취약하면
            Intent intent = new Intent(getApplicationContext(), RecvDataActivity.class);
            intent.putExtra("AGE", Integer.parseInt(edtAge.getText().toString()));
////          requestcode은 수신된 값의 대한 결과값 요청 사용자가 설정할 수 있음.
//            startActivityForResult(intent, 0);

            launcher.launch(intent); //launcher객체를 이용해 launch함수로 intent를 실행
        }
    }
//  콜백 객체 방식 launcher의 매개변수로 StartActivityForResult() 발송을 하기 위한 객체와
//  ActivityResultCallback<ActivityResult>() 수신되는 객체
//  onActivityResult은 핸들러함수이고 수신된 인텐트를 result라는 매개변수로 받고
//  정확히는 인텐트의 데이터를 받아오기 위해서는 getIntent()가 아니기 때문에 getData()를 이용
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK){
            Intent intent = result.getData(); //getData가 회시된 인텐트임
            int rAge = intent.getIntExtra("rAge", 0);
            tvReturnValue.setText("" + rAge);
        }
    }
});
//  초기방식
//  data가 out인텐트의 값을 전달받는 매개변수임/함수 단축키 Alt + insert
//  requestCode와 resultCode는 사용자가 설정한 값을 매개변수로 전달받음
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == 1){
//            int rAge = data.getIntExtra("rAge", 0);
//            tvReturnValue.setText("" + rAge);
//        }
//    }
}