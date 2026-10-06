# ReplyReceiver 한 줄 주석

파일: `src/main/java/com/example/ch10_notification/ReplyReceiver.kt`

```kotlin
package com.example.ch10_notification
// 현재 코틀린 파일이 속한 패키지 이름

import android.app.NotificationChannel
// Android 8 이상에서 알림 채널을 만들기 위한 클래스

import android.app.NotificationManager
// 알림을 실제로 표시하고 관리하는 시스템 서비스 클래스

import android.content.BroadcastReceiver
// 브로드캐스트를 수신하는 리시버의 부모 클래스

import android.content.Context
// 앱 환경 정보와 시스템 서비스에 접근할 때 사용하는 객체

import android.content.Intent
// 브로드캐스트로 전달된 데이터를 담는 객체

import android.graphics.BitmapFactory
// drawable 이미지를 Bitmap 형태로 변환할 때 사용

import android.os.Build
// 현재 안드로이드 버전을 확인할 때 사용

import android.widget.Toast
// 짧은 안내 메시지를 화면에 띄울 때 사용

import androidx.core.app.NotificationCompat
// 하위 버전까지 호환되는 알림 생성 클래스

import androidx.core.app.RemoteInput
// 알림의 답장 입력값을 가져올 때 사용하는 클래스

class ReplyReceiver : BroadcastReceiver() {
// 알림의 답장 이벤트를 처리하는 BroadcastReceiver 클래스 시작

    override fun onReceive(context: Context, intent: Intent) {
    // 브로드캐스트를 수신했을 때 자동으로 호출되는 메서드

        // ReplyReceiver는 알림의 답장 액션이 보낸 Broadcast를 백그라운드에서 받는다.
        val replyText = RemoteInput.getResultsFromIntent(intent)
        // 전달된 Intent에서 RemoteInput 결과를 꺼낸다.

            ?.getCharSequence(KEY_TEXT_REPLY)
            // KEY_TEXT_REPLY 키에 저장된 답장 문자열을 가져온다.

            ?.toString()
            // CharSequence 타입을 String으로 변환한다.

            ?.trim()
            // 앞뒤 공백을 제거한다.

        if (replyText.isNullOrEmpty()) {
        // 답장 내용이 없거나 비어 있으면

            return
            // 더 이상 처리하지 않고 함수 종료
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 시스템의 NotificationManager 객체를 가져온다.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android 8 이상인지 확인

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            // 채널 ID, 채널 이름, 중요도를 지정하여 알림 채널 생성

            manager.createNotificationChannel(channel)
            // 알림 갱신에도 채널이 필요하므로 채널을 다시 등록한다.
        }

        val sender = intent.getStringExtra(EXTRA_SENDER) ?: "홍길동"
        // MainActivity가 넣어 준 보낸 사람 이름을 가져오고, 없으면 기본값을 사용한다.

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Android 8 이상이면

            NotificationCompat.Builder(context, CHANNEL_ID)
            // 채널 ID를 사용해서 알림 Builder 생성

        } else {
        // Android 7 이하이면

            NotificationCompat.Builder(context)
            // 채널 없이 알림 Builder 생성
        }

        // 입력받은 답장 내용을 사용해 알림을 다시 구성한다.
        builder.run {
        // builder에 알림의 여러 속성을 한 번에 설정

            setSmallIcon(R.drawable.small)
            // 상태바에 표시될 작은 아이콘 설정

            setWhen(System.currentTimeMillis())
            // 알림이 갱신된 시각 설정

            setContentTitle(sender)
            // 알림 제목을 보낸 사람 이름으로 설정

            setContentText(replyText)
            // 알림 내용을 사용자가 입력한 답장으로 설정

            setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.big))
            // 알림 확장 시 보일 큰 아이콘 설정

            setAutoCancel(true)
            // 사용자가 알림을 누르면 자동으로 사라지게 설정

            priority = NotificationCompat.PRIORITY_DEFAULT
            // 알림 우선순위를 기본값으로 설정

            setCategory(NotificationCompat.CATEGORY_MESSAGE)
            // 이 알림이 메시지 성격의 알림임을 지정
        }

        manager.notify(NOTIFICATION_ID, builder.build())
        // 사용자가 입력한 답장을 반영한 알림으로 화면을 갱신한다.

        Toast.makeText(context, "\"$replyText\" 답장이 처리되었습니다.", Toast.LENGTH_SHORT).show()
        // 답장 처리가 끝났음을 토스트 메시지로 안내한다.
    }
}
```

## 역할 정리

- `ReplyReceiver`는 화면을 다시 열지 않고 알림의 답장 이벤트를 처리한다.
- `PendingIntent.getBroadcast()`로 전달된 Intent를 수신한다.
- `RemoteInput`으로 입력된 문자열을 꺼내서 답장 내용을 처리한다.
- 처리 결과를 반영한 새 알림을 만들어 다시 표시한다.

## 흐름 정리

- `알림의 답장 버튼 클릭`
- `PendingIntent 실행`
- `Broadcast 전달`
- `ReplyReceiver.onReceive() 호출`
- `RemoteInput 입력값 추출`
- `알림 갱신`
- `Toast 출력`
