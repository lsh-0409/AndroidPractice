package com.example.day6simplecalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText edtInput;
    private ArrayList<String> inputList, numList, operList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtInput = findViewById(R.id.edtInput);
        inputList = new ArrayList<>();
        numList = new ArrayList<>();
        operList = new ArrayList<>();
    }

    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.btn0){
            edtInput.setText(edtInput.getText().toString() + "0");
            putInputList("0");
        } else if(id == R.id.btn1){
            edtInput.setText(edtInput.getText().toString() + "1");
            putInputList("1");
        } else if(id == R.id.btn2){
            edtInput.setText(edtInput.getText().toString() + "2");
            putInputList("2");
        } else if(id == R.id.btn3){
            edtInput.setText(edtInput.getText().toString() + "3");
            putInputList("3");
        } else if(id == R.id.btn4){
            edtInput.setText(edtInput.getText().toString() + "4");
            putInputList("4");
        } else if(id == R.id.btn5){
            edtInput.setText(edtInput.getText().toString() + "5");
            putInputList("5");
        } else if(id == R.id.btn6){
            edtInput.setText(edtInput.getText().toString() + "6");
            putInputList("6");
        } else if(id == R.id.btn7){
            edtInput.setText(edtInput.getText().toString() + "7");
            putInputList("7");
        } else if(id == R.id.btn8){
            edtInput.setText(edtInput.getText().toString() + "8");
            putInputList("8");
        } else if(id == R.id.btn9){
            edtInput.setText(edtInput.getText().toString() + "9");
            putInputList("9");
        } else if(id == R.id.btnDot){
            edtInput.setText(edtInput.getText().toString() + ".");
            findViewById(R.id.btnDot).setClickable(false);
            putInputList(".");
        } else if(id == R.id.btnPlus){
            edtInput.setText(edtInput.getText().toString() + "+");
            findViewById(R.id.btnDot).setClickable(true);
            numList.add(getInputList());
            operList.add("+");
        }else if(id == R.id.btnMinus){
            edtInput.setText(edtInput.getText().toString() + "-");
            findViewById(R.id.btnDot).setClickable(true);
            numList.add(getInputList());
            operList.add("-");
        } else if(id == R.id.btnMul){
            edtInput.setText(edtInput.getText().toString() + "*");
            findViewById(R.id.btnDot).setClickable(true);
            numList.add(getInputList());
            operList.add("*");
        } else if(id == R.id.btnDiv){
            edtInput.setText(edtInput.getText().toString() + "/");
            findViewById(R.id.btnDot).setClickable(true);
            numList.add(getInputList());
            operList.add("/");
        } else if(id == R.id.btnCalc){
            edtInput.setText("");
            findViewById(R.id.btnDot).setClickable(true);
            numList.add(getInputList());

            calc();
        }
    }

    public void putInputList(String s){
        inputList.add(s);
    }

    public String getInputList(){
        String str = "";

        while(inputList.isEmpty() == false) {
            str += inputList.remove(0);
        }

        return str;
    }

    public void calc(){
        // 1. 숫자 리스트와 연산자 리스트가 비어있지 않은 상태인지 검사!!!
        if(numList.isEmpty() && operList.isEmpty()){
            return;
        }
        // 2. 2개의 값을 꺼낸다!
        // 3. 연산자를 꺼낸다.
        String oper = operList.remove(0);
        // 4. 각 상태에 맞도록 변환한다.
        // 5. 연산자에 따라 연산한다.
        double result = 0.0;
        switch (oper){
            case "+":
                result = Double.parseDouble(numList.remove(0)) + Double.parseDouble(numList.remove(0));
                if(result - (int)result == 0){
                    edtInput.setText("" + (int)result);
                } else {
                    edtInput.setText("" + result);
                }

                break;
            case "-":
                break;
            case "*":
                break;
            case "/":
                break;
        }
        // 6. 결과를 edtInput에 출력한다.
    }

    public boolean isInt(String s){
        boolean isOk = true;

        try {
            Integer.parseInt(s);
        } catch (Exception e){
            isOk = false;
        }

        return isOk;
    }
}









