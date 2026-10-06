package com.example.day14servivesample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private MediaPlayer mediaPlayer;

    public MyService() {
    }
    
//  서비스 결합하는 코드
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

//  서비스 초기화
    @Override
    public void onCreate() {
        super.onCreate();

        // Logcat에 로그 정보 띄우기
        Log.i("SERVICE_TAG", "서비스의 onCreate() 호출");
    }

//  서비스 시작
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("SERVICE_TAG", "서비스의 onStartCommand() 호출");

//      시스템이 가지고 있는 객체이기 떄문에 new필요없음 / 미디어파일 위치
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

//      계속 반복
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        return super.onStartCommand(intent, flags, startId);
    }

//  서비스 종료
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("SERVICE_TAG", "서비스의 onDestroy() 호출");
//      중지
        mediaPlayer.stop();
    }
}