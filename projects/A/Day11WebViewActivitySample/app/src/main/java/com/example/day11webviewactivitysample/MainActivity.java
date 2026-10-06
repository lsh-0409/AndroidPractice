package com.example.day11webviewactivitysample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int PERMISSION = 1;
//    위치정보 얻어오기
    private LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      현재 빌드버전을 읽어와서 버전이 23버전이상이고
//      위치권한인 FINE과 COARSE의 권한이 거부되었는지 확인
//      사용자의 OS 버전이 마시멜로우(23)이상인지 판별

        //허가 -> PERMISSION_GRANTED
        //거부 -> PERMISSION_DENIED
        if (Build.VERSION.SDK_INT >= 23 &&
//              checkSelfPermission()함수로 현재 어플리케이션의 위치권한이 거부되었는지 확인
           ContextCompat.checkSelfPermission(this,
                   Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED  ||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED
//         23버전 이상이고 메니페스트에 작성한 위치정보의 허가권의 내용이
//         허가되지 않은 상태라면.. 이코드가 없으면 앱 작동X
        ){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        }
//      시스템에서 위치정보를 주기 때문에 그 위치정보를 받는 것
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }
//  위치정보를 감지하는 리스너설정
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {

        }
    };

// 요청된 허가가 승인이 됬는지 거절 됬는지 결과를 확인하는 함수
// 매개변수 중 grantResults가 허가여부를 판단
    @Override
    public void onRequestPermissionsResult(int requestCode,
              @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//      요청코드를 여러개 보낼 수 있으니 내가 보낸 요청이 잘 갔는지 확인
        switch (requestCode){
            case PERMISSION:
//              허가가 거절되면 다시 허가창을 띄울것인지 ,아니면 앱 종료할 것인지
//              grantResults에 허락결과가 들어있음
//              두 개(FINE, COARSE)를 요청했으니 grantResults의 길이가 당연히 0보다 크다
//              그리고 grantResults배열 0번지에 값이 PackageManager.PERMISSION_GRANTED 같으면 허락
//              배열에 1번지 마찬가지로 PackageManager.PERMISSION_GRANTED 같으면 허락
//              모두 허락받은 상태이다. 앱을 정상 시작하다!!
                if (grantResults.length > 0
                        && grantResults[0] >= PackageManager.PERMISSION_GRANTED
                        && grantResults[1] >= PackageManager.PERMISSION_GRANTED){
                } else {
//                요청한 모든 허가에 대해 승인되지 않은 경우!!
//                앱을 종료합니다.
                    Toast.makeText(getApplicationContext(), "권한이 없어 앱을 종료합니다.",
                            Toast.LENGTH_SHORT).show();

                    finish();
                }
                break;
        }
    }
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnWebActivity){
            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
            intent.putExtra("URL", "https://map.naver.com/");
//          네이버지도 https://map.naver.com/p?c=15.00,0,0,0,dh
            startActivity(intent);
        }
    }
}