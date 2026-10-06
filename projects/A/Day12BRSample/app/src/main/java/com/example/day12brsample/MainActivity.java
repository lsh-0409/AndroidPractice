package com.example.day12brsample;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView tvBattery;

//  인텐트필터
    private IntentFilter filter;

//  BluetoothAdapter은 블루투스 상태를 확인하기 위한 클래스  
    private BluetoothAdapter blue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
//      BluetoothAdapter 초기화 즉 기기의 블루투스 어댑터를 가져옴 
        blue = BluetoothAdapter.getDefaultAdapter();

        tvBattery = findViewById(R.id.tvBattery);

//      필요한 필터를 설정하자!
        filter = new IntentFilter();

//      필터에 배터리상태변화 감지추가
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);

//      잠금해제는 USER_PRESENT 잠금해제는 수신할 수 없다.
//        filter.addAction(Intent.ACTION_USER_PRESENT);

//      과제 배터리 잔량이 낮은 경우 수신해서 그 상태를 표시하시오
        filter.addAction(Intent.ACTION_BATTERY_LOW);

//      과제 비행기 모드 전환을 감지하도록 수정해보세요!
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);

//      블루투스 상태 변경을 감지 / 주의 항상 인텐트클래스에만 존재하지 않음
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
    }

//  수신기객체생성, onReceive추상메서드이고 수신기가 수신한 광고메시지가 도착하는 곳!
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//           내가 필요한 것만 들어오지 않음

            String action = intent.getAction();

//            if (action.equals(Intent.ACTION_BATTERY_CHANGED)){
//              배터리정보는 %이기 때문에 int임
//              현재 배터리 정보를 받아옴 / BatteryManager클래스가 존재
//                int state = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//                tvBattery.setText("배터리 상태: " + state + "%");}

//            배터리 잔량이 낮은 경우 수신해서 그 상태를 표시하시오
             if (action.equals(Intent.ACTION_BATTERY_LOW)) {
//              배터리 잔량이 15%이하면 화면에 표시하도록 한 코드
                int state = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 15);
                    tvBattery.setText("배터리 상태: " + state + "%");

//              비행기 모드 전환을 감지하도록 수정해보세요!
            } else if (action.equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
                 Boolean state = intent.getBooleanExtra("state", true);
                 if (state){ //비행기 모드 전환을 토스트로 화면에 출력
                     Toast.makeText(getApplicationContext(), "비행기 모드 켜짐", Toast.LENGTH_SHORT).show();                     
                 } else Toast.makeText(getApplicationContext(), "비행기 모드 꺼짐", Toast.LENGTH_SHORT).show();
                 
//             블루투스 상태 변경 감지    
             } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                 
//               int인 이유는 BluetoothAdapter클래스에서 다양한 상태를 정수 값으로 정의하고 있기 때문
                 int bluetoothstate = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,

//               또한 값이 BluetoothAdapter.ERROR인 이유는 블루투스 어댑터의 상태를 확인할 때 초기화에
//               문제가 있어 상태를 정확히 확인할 수 없는 경우에 에러 상태를 나타내기 위해 사용한 것
                         BluetoothAdapter.ERROR);
                 
//               스위치문으로 정수값을 비교   
                 switch (bluetoothstate){
                     case BluetoothAdapter.STATE_OFF: // 블루투스 비활성화
                         Toast.makeText(getApplicationContext(), "블루투스 꺼짐", Toast.LENGTH_SHORT).show();
                         break;
                     case BluetoothAdapter.STATE_ON: // 블루투스 활성화
                         Toast.makeText(getApplicationContext(), "블루투스 켜짐", Toast.LENGTH_SHORT).show();
                         break;
                 }
             }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
//      수신기등록
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
//      수신기해제
        unregisterReceiver(receiver);
    }
}