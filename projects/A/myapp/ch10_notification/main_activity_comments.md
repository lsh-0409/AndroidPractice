# MainActivity 한 줄 주석

파일: `src/main/java/com/example/ch10_notification/MainActivity.kt`

```kotlin
package com.example.ch10_notification
// 현재 코틀린 파일이 속한 패키지 이름

import android.Manifest
// 안드로이드 권한 상수를 사용하기 위한 import

import android.app.NotificationChannel
// Android 8 이상에서 알림 채널을 만들기 위한 클래스

import android.app.NotificationManager
// 알림을 실제로 표시하고 관리하는 시스템 서비스 클래스

import android.app.PendingIntent
// 나중에 시스템이 대신 실행할 Intent 정보를 담는 객체

import android.content.Intent
// Activity나 Receiver로 이벤트를 전달할 때 사용하는 객체

import android.content.pm.PackageManager
// 권한 허용 여부를 비교할 때 사용하는 상수 클래스

import android.graphics.BitmapFactory
// drawable 이미지를 Bitmap 형태로 변환할 때 사용

import android.media.AudioAttributes
// 알림 소리의 용도를 설정할 때 사용

import android.media.RingtoneManager
// 기본 알림 소리 URI를 가져올 때 사용

import android.net.Uri
// 알림 소리 경로를 저장할 때 사용하는 타입

import android.os.Build
// 현재 안드로이드 버전을 확인할 때 사용

import android.os.Bundle
// Activity 생성 시 전달되는 데이터 묶음 객체

import android.widget.Toast
// 짧은 안내 메시지를 화면에 띄울 때 사용

import androidx.activity.result.contract.ActivityResultContracts
// 권한 요청 결과를 처리하는 최신 방식 API

import androidx.appcompat.app.AppCompatActivity
// 앱의 기본 Activity 클래스

import androidx.core.app.NotificationCompat
// 하위 버전까지 호환되는 알림 생성 클래스

import androidx.core.app.RemoteInput
// 알림에서 바로 텍스트 입력을 받기 위한 클래스

import androidx.core.content.ContextCompat
// 권한 확인 등 호환성 처리를 도와주는 클래스

import com.example.ch10_notification.databinding.ActivityMainBinding
// ViewBinding으로 XML 뷰를 코드와 연결하기 위한 클래스

class MainActivity : AppCompatActivity() {
// 앱의 메인 화면 역할을 하는 Activity 클래스 시작

    private lateinit var binding: ActivityMainBinding
    // activity_main.xml의 뷰들을 코드에서 사용하기 위한 바인딩 변수

    // Android 13 이상에서는 알림 권한을 먼저 허용받아야 한다.
    // 이 분기는 권한 처리용이며, 아래의 Android 8 채널 분기와는 별개이다.
    private val notificationPermissionLauncher =
    // 권한 요청 결과를 처리하는 런처 객체 생성

        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        // POST_NOTIFICATIONS 권한 요청 후 결과를 granted로 받음

            if (granted) {
            // 사용자가 권한을 허용한 경우

                showNotification()
                // 권한이 있으면 바로 알림 생성 함수 실행

            } else {
            // 사용자가 권한을 거부한 경우

                Toast.makeText(this, "알림 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                // 권한이 필요하다는 메시지를 짧게 출력
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
    // Activity가 처음 생성될 때 자동으로 호출되는 메서드

        super.onCreate(savedInstanceState)
        // 부모 클래스의 onCreate를 먼저 실행

        binding = ActivityMainBinding.inflate(layoutInflater)
        // activity_main.xml 레이아웃을 바인딩 객체로 연결

        setContentView(binding.root)
        // 바인딩된 화면을 실제 Activity 화면으로 설정

        // 버튼을 누르면 권한 확인 후 알림을 생성한다.
        binding.notificationButton.setOnClickListener {
        // notificationButton 버튼 클릭 이벤트 처리 시작

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            // 현재 버전이 Android 13 이상인지 확인

                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
            // Android 13 이상이면서 알림 권한이 아직 허용되지 않은 경우

                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                // 알림 권한 요청 창을 띄움

            } else {
            // Android 12 이하이거나 이미 권한이 허용된 경우

                showNotification()
                // 바로 알림 생성 함수 실행
            }
        }
    }

    private fun showNotification() {
    // 실제 알림을 생성하고 표시하는 함수

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // 시스템의 NotificationManager 객체를 가져옴

        val builder: NotificationCompat.Builder
        // 알림 내용을 설정할 Builder 변수 선언

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android 8 이상인지 확인

            // Android 8 이상은 채널을 먼저 등록해야 알림을 화면에 표시할 수 있다.
            // 이 분기는 채널 생성용이며, 위의 Android 13 권한 분기와는 별개이다.
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
            // 채널 ID, 채널 이름, 중요도를 지정하여 알림 채널 생성

                description = "카카오톡 스타일 답장 알림 채널"
                // 채널 설명 설정

                setShowBadge(true)
                // 앱 아이콘에 배지 표시 허용

                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                // 기본 알림 소리 URI를 가져옴

                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                // 이 소리가 알림용 소리임을 시스템에 알려줌

                setSound(uri, audioAttributes)
                // 채널에 알림 소리 설정

                enableVibration(true)
                // 채널 진동 사용 설정
            }

            manager.createNotificationChannel(channel)
            // 만든 채널을 시스템에 등록

            builder = NotificationCompat.Builder(this, CHANNEL_ID)
            // 등록한 채널 ID를 사용해서 알림 Builder 생성

        } else {
        // Android 7 이하인 경우

            builder = NotificationCompat.Builder(this)
            // 채널 없이 Builder 생성
        }

        // 알림 기본 정보 설정
        builder.run {
        // builder에 알림의 여러 속성을 한 번에 설정

            setSmallIcon(R.drawable.small)
            // 상태바에 표시될 작은 아이콘 설정

            setWhen(System.currentTimeMillis())
            // 알림이 생성된 시각 설정

            setContentTitle("홍길동")
            // 알림 제목 설정

            setContentText("안녕하세요")
            // 알림 내용 설정

            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big))
            // 알림 확장 시 보일 큰 아이콘 설정

            setAutoCancel(true)
            // 사용자가 알림을 누르면 자동으로 사라지게 설정

            priority = NotificationCompat.PRIORITY_DEFAULT
            // 알림 우선순위를 기본값으로 설정

            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            // 이 알림이 메시지 성격의 알림임을 지정
        }

        // 알림에서 바로 답장을 입력할 수 있도록 RemoteInput을 추가한다.
        val remoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
        // 답장 입력창에 사용할 RemoteInput 객체 생성

            setLabel("답장")
            // 입력창 힌트 문구를 "답장"으로 설정

            build()
            // RemoteInput 객체 생성 완료
        }

        // 알림의 답장 액션은 Broadcast로 전달되어 ReplyReceiver가 처리한다.
        val replyIntent = Intent(this, ReplyReceiver::class.java).apply {
        // 답장 버튼을 누르면 ReplyReceiver로 전달할 Intent 생성

            putExtra(EXTRA_SENDER, "홍길동")
            // 보낸 사람 정보를 Intent에 저장

            putExtra(EXTRA_MESSAGE, "안녕하세요")
            // 원래 메시지 내용을 Intent에 저장
        }

        val replyPendingIntent = PendingIntent.getBroadcast(
            this,
            30,
            replyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
        // BroadcastReceiver를 실행할 PendingIntent 생성
        // FLAG_UPDATE_CURRENT: 기존 PendingIntent가 있으면 내용 갱신
        // FLAG_MUTABLE: RemoteInput 결과를 담기 위해 수정 가능 상태로 생성

        builder.addAction(
            NotificationCompat.Action.Builder(
                R.drawable.send,
                "답장",
                replyPendingIntent
            ).addRemoteInput(remoteInput).build()
        )
        // 알림에 '답장' 액션 버튼을 추가하고, RemoteInput과 PendingIntent를 연결

        manager.notify(NOTIFICATION_ID, builder.build())
        // 최종적으로 알림을 화면에 표시
    }
}
```

## 버전 분기 정리

- `Android 13 이상(API 33+)`
  - `POST_NOTIFICATIONS` 런타임 권한 확인이 필요하다.
- `Android 8 이상(API 26+)`
  - `NotificationChannel` 생성이 필요하다.
- 즉, `13 이상`과 `8 이상`은 서로 다른 기능 기준의 분기이며 각각 코드에 따로 들어가야 한다.
