package com.example.day11webviewactivitysample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

public class WebActivity extends AppCompatActivity {
    private EditText edtUrl;
    private WebView web;
//  혼돈될 수 있기 때문에 별도로 멤버지정
    private String recvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        edtUrl = findViewById(R.id.edtUrl);
        web = findViewById(R.id.web);

        Intent intent = getIntent();
        recvUrl = intent.getStringExtra("URL");
//      WebViewClient클래스로 객체생성 11주코드
//        web.setWebViewClient(new myWebViewClient());

            initWebView();
        }

//   12주 웹뷰코드수정, 코드간결화
//   WebActivity에 위치권한이 부여되어 있지 WebActivity안에 있는 웹뷰에는 위치권한이 부여되어 있지않음
//   그래서 웹뷰브라우저에서 위치권한을 주어서 현재위치를 특정할 수 있게 하자

    public void initWebView(){
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
//      현재상태 읽어오기
        WebSettings settings = web.getSettings();
//      자바스크립트설정
        settings.setJavaScriptEnabled(true);

//      웹뷰브라우저 내부에서 위치 정보 사용을 설정하자!!
//      정확히는 웹뷰에서 GeolocationAPI를 사용하려고 시도
//      -> 여기서는 네이버 지도에 설정된 권한상태가 없어서 -> WebActivity에 권한이 없다고 알림
        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,

//      GeolocationPermissions.Callback은 지리적 위치 권한 상태를 설정하기 위해
//      Activity에서 사용하는 콜백인터페이스
          GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);

//   onGeolocationPermissionsShowPrompt()함수에 매개변수인 origin을 사용
//   콜백인터페이스내에 추상메서드인 invoke()메서드가 존재
//   -> 웹뷰위젯에 대한 위치권한상태를 설정하는 메서드, origin은 String으로 위치값을 가지고 있음
                callback.invoke(origin, true, false);
            }
        });
        web.loadUrl(recvUrl.toString());
    }
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btnGo){
            String url = edtUrl.getText().toString();
//          url값을 검사해 https없으면 넣어주고 있으면 넣어준다. startsWith String함수사용
            if (!url.startsWith("https://") || !url.startsWith("http://")){
                web.loadUrl(url.toString());
            } else {
                url = "https://" + url;
//              https라는 프로토콜까지 넣어줘야 됨
                web.loadUrl(url);
            }
        }
    }
//  12주 코드수정
//  따로 클래스를 만들지 않고 WebViewClient(){}함수 괄호사이에 바로 객체생성과
//  중괄호에 shouldOverrideUrlLoading()함수 오버라이드 코드가 간결해 졌다.
//  WebViewClient객체를 생성하기 위해 shouldOverrideUrlLoading()함수 오버라이드필요
//    class myWebViewClient extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            return super.shouldOverrideUrlLoading(view, url);
//        }
//    }
}

