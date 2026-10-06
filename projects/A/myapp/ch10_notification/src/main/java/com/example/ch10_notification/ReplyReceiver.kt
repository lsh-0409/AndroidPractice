package com.example.ch10_notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class ReplyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // ReplyReceiver는 알림의 답장 액션이 보낸 Broadcast를 백그라운드에서 받는다.

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
            // 답장 내용이 없으면 추가 작업 없이 종료한다.
            return
        }

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // 시스템의 NotificationManager 객체를 가져온다.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android 8 이상에서는 알림 갱신에도 채널이 필요하다.
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
            NotificationCompat.Builder(context, CHANNEL_ID)
            // Android 8 이상은 채널 ID를 사용해서 알림 Builder를 만든다.
        } else {
            NotificationCompat.Builder(context)
            // Android 7 이하는 채널 없이 Builder를 만든다.
        }

        // 입력받은 답장 내용을 사용해 알림을 다시 구성한다.
        builder.run { // builder에 알림의 여러 속성을 한 번에 설정
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
/*
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
* */