package com.example.day13implicitintentsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnDial){
//          parse로 uri를 구분해서 uri생성 String타입으로
            Uri uri = Uri.parse("tel: 01012345678");

//          작업 지정(액션), 데이터 지정(uri)
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);

//          원래 실행 되면 안됨, 메니페스트에 전화권한을 설정해야 함.
            startActivity(intent);

//        홈페이지 열기
        } else if (id == R.id.btnWeb) {

            Uri uri = Uri.parse("https://www.gtec.ac.kr");

//          ACTION_VIEW로 홈페이지 열기
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            startActivity(intent);

//        지도 열기 위치정보를 알 경우
        } else if (id == R.id.btnMap) {

//          서버에 호스트 방식과 겟 방식(? 사용) - 로그인에 사용안됨 주소값이 다 보임, 위도 경도 설정
//            구글 지도 지정
//            Uri uri = Uri.parse("https://maps.google.co.kr/maps?q=" + 37.559133 +
//                "," + 126.927824 + "&z" + 15);
//
//            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//
//            startActivity(intent);

//        다른 방법 내 현재 위치를 수신해 지도에 보여주자
//        위도, 경도 값을 설정한다.
            double latitude = 37.5665;
            double longitude = 126.9780;
//          ?q= 정보값 위도, 경도를 합친
            Uri uri =Uri.parse("geo:" + latitude + "," + longitude + "?q=" +
                    latitude + "," + longitude);

//          지도 보여줌
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

//          구글페키지 사용하겠다. 구글지도를 사용하겠다고 명시
            intent.setPackage("com.google.android.apps.maps");

//          액비티비에서 resolveActivity()호출 지도를 실행할 액티비티가 존재 하냐?
//          즉 null이 아니면 내가 지금 발송하는 인텐트가 확인되었다는 것
            if (intent.resolveActivity(getPackageManager()) != null)
                startActivity(intent);

//        구글 검색 특정한 주소가 있는 것이 아닌 데이터를 보내는 것이기 때문에 URI사용 안함
        } else if (id == R.id.btnSearch) {

//          Uri uri = Uri.parse(); 다른 경우 검색하고자 하는 데이터라 uri가 필요없음

            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);

//          데이터를 지정 즉 쿼리 값을 사용(쿼리값은 String) -> SearchManager.QUERY이용해
//          경기과학기술대학교라는 데이터로 검색
            intent.putExtra(SearchManager.QUERY, "경기과학기술대학교");

            startActivity(intent);

//        문자 발송 / 문자도 uri필요 없음 데이터를 보내기 때문에
//        하지만 문자를 보내는 수신자 전화번호 필요 추가적인 uri필요
        } else if (id == R.id.btnSms) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);

//          sms_body은 시스템에 정의되어 있음
            intent.putExtra("sms_body", "문자 내용: 안녕하세요!!!");

//          전화번호 지정 / smsto도 시스템에 정의, Uri.encode로 전화번호 인코딩
            intent.setData(Uri.parse("smsto:" + Uri.encode("010-1234-5678")));

            startActivity(intent);

//        카메라 열기
        } else if (id == R.id.btnCam) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);

        }
    }
}
// 마지막 컴포넌트 Contentprovide