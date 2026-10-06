# myapp 모듈 안내

`myapp`은 Kotlin으로 작성한 여러 실습 모듈을 포함하는 **하나의 Gradle 프로젝트**입니다. Android Studio에서는 이 `myapp` 폴더를 열고 모듈을 선택합니다.

| 모듈 | 실습 주제 | 주요 코드 |
| --- | --- | --- |
| [`app`](app/) | `ConstraintLayout`에 인사 문구를 표시하는 기본 앱 화면 | [`MainActivity.kt`](app/src/main/java/com/example/myapp/MainActivity.kt) |
| [`ch6_view`](ch6_view/) | 안내문·계정 텍스트·비밀번호 입력칸·확인 버튼을 세로로 배치한 입력 화면 | [`activity_main.xml`](ch6_view/src/main/res/layout/activity_main.xml) |
| [`ch7_layout`](ch7_layout/) | `LinearLayout`·`GridLayout`·`RelativeLayout`을 조합한 전화 키패드 화면 | [`activity_main.xml`](ch7_layout/src/main/res/layout/activity_main.xml) |
| [`ch8`](ch8/) | 프로필 이미지·제목·메시지·날짜를 제약 조건으로 배치한 메신저 알림 화면 모형 | [`activity_main.xml`](ch8/src/main/res/layout/activity_main.xml) |
| [`ch8_event`](ch8_event/) | Jetpack Compose의 `Scaffold`·`Text`와 미리보기로 만든 인사 화면 | [`MainActivity.kt`](ch8_event/src/main/java/com/example/ch8_event/MainActivity.kt) |
| [`ch8_event2`](ch8_event2/) | 크로노미터 시작·정지·초기화 버튼과 뒤로 가기 두 번으로 종료하는 처리 | [`MainActivity.kt`](ch8_event2/src/main/java/com/example/ch8_event2/MainActivity.kt) |
| [`ch9_resource`](ch9_resource/) | 문자열 리소스를 참조해 안내문·버튼을 배치한 `RelativeLayout` 화면 | [`activity_main.xml`](ch9_resource/src/main/res/layout/activity_main.xml) |
| [`ch10_notification`](ch10_notification/) | 알림 권한·채널을 설정하고 `RemoteInput` 답장을 브로드캐스트로 받아 알림 갱신 | [`MainActivity.kt`](ch10_notification/src/main/java/com/example/ch10_notification/MainActivity.kt), [`ReplyReceiver.kt`](ch10_notification/src/main/java/com/example/ch10_notification/ReplyReceiver.kt) |
| [`ch11_jetpack`](ch11_jetpack/) | 툴바 검색 메뉴·드로어 토글·`ViewPager2`로 세 개의 Fragment 표시 | [`MainActivity.kt`](ch11_jetpack/src/main/java/com/example/ch11_jetpack/MainActivity.kt) |
| [`week4_homework`](week4_homework/) | 중첩 `LinearLayout`과 `layout_weight`로 여러 색 영역의 크기·위치를 배치 | [`activity_main.xml`](week4_homework/src/main/res/layout/activity_main.xml) |
| [`week5`](week5/) | 체크박스로 선택 UI를 열고 라디오 버튼에 따라 강아지·고양이·토끼 사진 표시 | [`MainActivity.kt`](week5/src/main/java/com/example/week5/MainActivity.kt) |

모듈 목록은 [`settings.gradle.kts`](settings.gradle.kts)에 선언된 순서를 기준으로 확인할 수 있습니다. 각 모듈은 개별 저장소로 분리하지 않아도 GitHub에서 폴더별로 볼 수 있습니다.

`app`과 `ch8_event`는 기본 화면 예제이며, `ch8`은 화면 배치 모형입니다. 이름에 `event`가 들어간 `ch8_event`에는 별도의 클릭 이벤트 로직이 없습니다.
