package com.example.day6homework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//  주의사항: 해당 계산기는 이항 계산기이고 소수점은 한번만 사용 가능합니다.
//  추가기능 --> Del버튼에 edt초기화와 .(소수점)이 한번만 클릭되도록 기능을 추가 했습니다.
//  그러므로 계산을 하고 나서 다시 계산을 하기 위해서는
//  무조건 Del버튼을 클릭하면 다시 계산을 할 수 있습니다.
    private EditText edtInput;
//  숫자버튼이 입력되는 순서대로 개별 저장해서 보관하는 리스트 inputStrList
//  두개의 ArrayList가 필요 숫자 저장과 연산자 저장
    private ArrayList<String> inputStrList;

    //  연산자버튼이 클릭되면 연산자들을 저장하는 OperList
    private ArrayList<String> OperList;

    //  숫자들을 저장하는 numList
    private ArrayList<String> numList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      위젯변수 결합과 3개의 ArrayList생성
        edtInput = findViewById(R.id.edtInput);
        inputStrList = new ArrayList<>();
        OperList = new ArrayList<>();
        numList = new ArrayList<>();
    }
    public void onClick(View view) { // 버튼을 클릭하는 이벤트 설정

//      view에서 읽어온 ID값을 저장하는 id변수
        int id = view.getId();

//      if문을 사용해 id값이 버튼의 id값과 같은지 판단
        if(id == R.id.btn0){
            putInputList("0");
//          버튼이 눌리면 글자를 붙여서 추가 해라
            edtInput.setText(edtInput.getText() + "0");
        } else if (id == R.id.btn1) {
//          버튼이 눌리면 putInputList함수에 숫자를 String으로 전달
            putInputList("1");
//          get해야 입력한 숫자를 읽어와서 edt에 출력
            edtInput.setText(edtInput.getText() + "1");
        } else if (id == R.id.btn2) {
            putInputList("2");
            edtInput.setText(edtInput.getText() + "2");
        } else if (id == R.id.btn3) {
            putInputList("3");
            edtInput.setText(edtInput.getText() + "3");
        } else if (id == R.id.btn4) {
            putInputList("4");
            edtInput.setText(edtInput.getText() + "4");
        } else if (id == R.id.btn5) {
            putInputList("5");
            edtInput.setText(edtInput.getText() + "5");
        } else if (id == R.id.btn6) {
            putInputList("6");
            edtInput.setText(edtInput.getText() + "6");
        } else if (id == R.id.btn7) {
            putInputList("7");
            edtInput.setText(edtInput.getText() + "7");
        } else if (id == R.id.btn8) {
            putInputList("8");
            edtInput.setText(edtInput.getText() + "8");
        } else if (id == R.id.btn9) {
            putInputList("9");
            edtInput.setText(edtInput.getText() + "9");
        } else if (id == R.id.btnDot) {
            putInputList(".");
            edtInput.setText(edtInput.getText() + ".");
            findViewById(R.id.btnDot).setClickable(false);
//          .(소수점)이 한번 눌리게 하기 위해서
        } else if (id == R.id.btnDel) {
            edtInput.setText(""); // edt 초기화
            findViewById(R.id.btnDot).setClickable(true);
//          Del버튼을 클릭하면 .(소수점)이 한번 더 눌리게 하기 위해
        } else if (id == R.id.btnDiv) {
//          연산자가 클릭 되면 연산자 이전에 있는 숫자를 inputStrList에서 꺼내와
//          numList에 추가함 그리고 inputStrList는 숫자가 꺼낼 때 해당 숫자를 지움
            String returnNumStr = getNumStrFromInputList();
            numList.add(returnNumStr);
//          연산자 버튼이 눌리면 putOper함수에 해당 연산자를 String으로 전달
            putOper("/");
            edtInput.setText(edtInput.getText() + "/");
        } else if (id == R.id.btnMulti) {
            String returnNumStr = getNumStrFromInputList();
            numList.add(returnNumStr);
            putOper("*");
            edtInput.setText(edtInput.getText() + "*");
        } else if (id == R.id.btnMinus) {
            String returnNumStr = getNumStrFromInputList();
            numList.add(returnNumStr);
            putOper("-");
            edtInput.setText(edtInput.getText() + "-");
        } else if (id == R.id.btnPlus) {
            String returnNumStr = getNumStrFromInputList();
            numList.add(returnNumStr);
            putOper("+");
            edtInput.setText(edtInput.getText() + "+");
        } else if (id == R.id.btnCalc) {
            edtInput.setText("");
//          '='버튼이 클릭 된 경우 처리순서
//          1. 숫자 리스트의 0번지 값 읽어오기
//          1-1 현재 입력 리스트 값 읽어오기
//          2. 정수, 실수 판단
//          3. 변환
//          4. 연산자 리스트에서 값 읽어오기
//          5. 연산
//          6. 출력

//          숫자 리스트의 0번지 값 읽어오기
            String str1 = numList.remove(0);
//          현재 입력 리스트 값 읽어오기
            String str2 = getNumStrFromInputList();
//          연산자 리스트에서 값 읽어오기
            String stroper = OperList.remove(0);
//          정수, 실수 판단 -> 해당 숫자로 변환
//          -> 연산자 리스트에서 가져온 해당 연산자로 연산 -> 화면에 계산결과 출력
            calcAndPrint(str1, str2, stroper);
        }
    }
    //  숫자 버튼이 클릭된 순서대로 inputStrList에 저장
    public void putInputList(String v){
//    String 타입의 받아온 숫자(v)를 inputStrList에 추가
        inputStrList.add(v);
    }

    //  OperList에 받아온 연산자를 추가
    public void putOper(String v){
        OperList.add(v);
    }

//  InputList에 저장된 0번째 인덱스에 있는 숫자를 문자열 value에 저장 후
//  InputList에 0번째 인덱스는 지우고 문자열 value를 반환
    public String getNumStrFromInputList(){
        String value = "";

        while(inputStrList.isEmpty() == false){

            value += inputStrList.remove(0);

        }
        return value;
    }
    //  현재값이 정수인가 실수인가 판단하는 함수 예외처리 사용 true면 정수로 바꿔라
    public boolean isInt(String v){
        boolean isOk = true;
        try {
            Integer.parseInt(v);
        }   catch (Exception e){
            isOk = false;
        }
        return isOk;
    }
//     이 함수에서 정수, 실수 판단과 해당 숫자로 변환, editText화면에 연산된 결과를 출력
    public void calcAndPrint(String s1, String s2, String op){
        edtInput.setText("");
//     정수끼리 실수끼리 정수와 실수끼리 총 4가지
//     -> 다 실수로 바꾸어서 계산결과를 저장하는 변수를 double타입으로 만들어도 됨,
//     정수면 소수점 제외
        double result = 0;

        switch (op){ // isInt함수에서 isOk가 참이면 정수고 거짓이면 실수
            case "+":
                if(isInt(s1) && isInt(s2)) // if문이 둘다 참이면 정수변환
                    result = (Integer.parseInt(s1) + Integer.parseInt(s2));
                else
                    result = (Double.parseDouble(s1) + Double.parseDouble(s2));
                break;
            case "-":
                if(isInt(s1) && isInt(s2))
                    result = (Integer.parseInt(s1) - Integer.parseInt(s2));
                else
                    result = (Double.parseDouble(s1) - Double.parseDouble(s2));
                break;
            case "*":
                if(isInt(s1) && isInt(s2))
                    result = (Integer.parseInt(s1) * Integer.parseInt(s2));
                else
                    result = (Double.parseDouble(s1) * Double.parseDouble(s2));
                break;
            case "/":
                if(isInt(s1) && isInt(s2))
                    result = (Integer.parseInt(s1) / Integer.parseInt(s2));
                else
                    result = (Double.parseDouble(s1) / Double.parseDouble(s2));
                break;
        }
        // 3.1 - 3 = 0.1 인데 int형으로 바꿔도 3.0으로 계산함 / 정수면 소수점자리를 제거, 실수면 소수점자리 그대로 출력 / int형으로 캐스팅
        if(result - (int)result == 0) edtInput.setText(""+(int)result);
        else edtInput.setText(""+result); // 연산된 결과인 result를 editText에 넣어 화면 출력
    }
}