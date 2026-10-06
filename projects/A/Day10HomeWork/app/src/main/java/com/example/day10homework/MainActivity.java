package com.example.day10homework;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText edtShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtShow = findViewById(R.id.edtShow);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnThrow){
            String str = edtShow.getText().toString();
            int input = Integer.parseInt(str);

            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra("Input", input);
            startActivity(intent);
        } else if (id == R.id.btnReturn) {
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            intent.putExtra("Input", Integer.parseInt(edtShow.getText().toString()));
            launcher.launch(intent);
        }
    }
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if (o.getResultCode() == Activity.RESULT_OK){
                Intent intent = o.getData();
                int age = intent.getIntExtra("Input", 0);
                edtShow.setText("" + age);
            }
        }
    });
}
