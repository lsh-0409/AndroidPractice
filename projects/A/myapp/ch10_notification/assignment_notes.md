# Ch10 Notification 과제 정리

## 1. 전체 흐름도

```text
[사용자]
   |
   v
[MainActivity 화면 실행]
   |
   v
[알림 발생 버튼 클릭]
   |
   v
[Android 13 이상 권한 확인]
   |
   +-- 권한 없음 --> [POST_NOTIFICATIONS 권한 요청] --> [권한 허용] --> [알림 생성]
   |
   +-- 권한 있음 -----------------------------------------------> [알림 생성]
                                                                  |
                                                                  v
                                                        [NotificationChannel 생성(Android 8 이상)]
                                                                  |
                                                                  v
                                                        [NotificationCompat.Builder 설정]
                                                                  |
                                                                  v
                                                        [답장 Action + RemoteInput 추가]
                                                                  |
                                                                  v
                                                        [알림 표시]
                                                                  |
                                                                  v
                                                        [사용자가 알림에서 답장 입력]
                                                                  |
                                                                  v
                                                        [PendingIntent 실행]
                                                                  |
                                                                  v
                                                        [Broadcast 전달]
                                                                  |
                                                                  v
                                                        [ReplyReceiver.onReceive()]
                                                                  |
                                                                  v
                                                        [입력 문자열 추출]
                                                                  |
                                                                  v
                                                        [알림 내용 갱신 + Toast 출력]
```

## 2. SW 설계구조

### 2-1. 역할 분리

- `MainActivity`
  - 사용자 화면을 표시한다.
  - 버튼 클릭 이벤트를 처리한다.
  - Android 13 이상에서 알림 권한을 확인한다.
  - 알림 채널을 만들고 알림을 생성한다.
  - 답장 액션에 `PendingIntent`와 `RemoteInput`을 연결한다.

- `ReplyReceiver`
  - 알림의 답장 이벤트를 백그라운드에서 받는다.
  - `RemoteInput`으로 전달된 문자열을 꺼낸다.
  - 답장 내용을 반영해 알림을 다시 갱신한다.
  - Toast로 처리 결과를 보여준다.

- `NotificationConstants`
  - 채널 ID, 알림 ID, 답장 키, 부가 데이터 키를 공용 상수로 관리한다.

### 2-2. 이벤트 기반 설계

- 이 앱은 버튼 클릭과 알림 액션을 중심으로 동작하는 이벤트 기반 구조이다.
- 화면의 버튼 클릭 이벤트는 `MainActivity`가 처리한다.
- 알림의 답장 이벤트는 `PendingIntent`를 통해 `Broadcast`로 전달된다.
- `ReplyReceiver`는 이 Broadcast를 받아 입력 내용을 처리한다.

### 2-3. 버전별 분기 설계

- `Android 13 이상`
  - `POST_NOTIFICATIONS` 런타임 권한이 필요하다.
  - 권한이 허용된 뒤에만 알림을 생성한다.

- `Android 8 이상`
  - `NotificationChannel` 생성이 필수이다.
  - 채널을 등록한 뒤 `NotificationCompat.Builder`에 채널 ID를 연결한다.

### 2-4. 구조 요약

```text
사용자 화면 계층
  MainActivity

알림 생성 계층
  NotificationManager
  NotificationChannel
  NotificationCompat.Builder

이벤트 전달 계층
  PendingIntent
  Broadcast

입력 처리 계층
  RemoteInput
  ReplyReceiver
```

### 2-5. 설계 의도

- Activity는 화면과 알림 생성까지만 담당하고, 실제 답장 입력 처리는 Receiver에 위임했다.
- 이 구조는 Activity를 다시 열지 않고도 알림 입력을 처리할 수 있어서 채팅 앱 구조에 적합하다.
- 공용 상수를 분리해 `MainActivity`와 `ReplyReceiver`가 같은 키를 안정적으로 공유하도록 설계했다.
