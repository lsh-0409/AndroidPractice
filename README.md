# AndroidPractice

Android 수업에서 만든 실습 프로젝트를 모아 둔 저장소입니다. 아래 표의 프로젝트 폴더는 각각 독립적인 Android Studio/Gradle 프로젝트이며, 해당 폴더의 `settings.gradle.kts`를 기준으로 열 수 있습니다. `myapp` 안의 11개 모듈은 하나의 프로젝트에 속합니다.

## 폴더 구조

- [`projects/`](projects/): 원래 `AndroidStudioProjects` 바로 아래에 있던 실습 프로젝트 7개입니다.
- [`projects/A/`](projects/A/): 원래 `AndroidStudioProjects/A`에 있던 실습·과제 프로젝트 15개입니다.
- [`projects/A/myapp/`](projects/A/myapp/): Kotlin 실습을 모은 다중 모듈 프로젝트입니다. [모듈별 기능과 코드](projects/A/myapp/README.md)를 따로 정리했습니다.

각 프로젝트의 `src/main/java`에는 Java 또는 Kotlin 코드, `src/main/res`에는 화면 XML·이미지 등의 리소스가 있습니다. 프로젝트 루트의 Gradle 설정과 `gradle/wrapper`는 나중에 다시 열고 빌드할 때 사용합니다.

## 프로젝트 목록

### 기본 실습

| 프로젝트 폴더 | 내용 |
| --- | --- |
| [MyApplication](projects/MyApplication/) | Jetpack Compose의 `Surface`와 `Text`로 인사 문구를 표시하는 기본 화면 |
| [AMyApplication](projects/AMyApplication/) | Java·C++·C# 라디오 버튼 중 선택한 과목을 확인 버튼과 Toast로 표시 |
| [Day3EventProcess](projects/Day3EventProcess/) | 숫자 입력칸, 클릭 버튼, 결과 텍스트를 배치한 이벤트 처리 화면 초안. Java 클릭 처리는 아직 없음 |
| [Day5LayoutSample](projects/Day5LayoutSample/) | `ConstraintLayout` 가운데에 텍스트를 배치한 기본 화면 |
| [Day6SimpleCalc](projects/Day6SimpleCalc/) | 숫자·소수점·사칙연산 버튼으로 값을 입력하고 계산하는 앱 |
| [Day7Widget2](projects/Day7Widget2/) | `Chronometer` 시작·정지, 소요 시간과 `CalendarView` 선택 날짜 표시 |
| [Day7WidgetETC](projects/Day7WidgetETC/) | 크로노미터의 경과 시간과 달력에서 선택한 날짜를 텍스트로 출력 |

### 추가 실습 및 과제

| 프로젝트 폴더 | 내용 |
| --- | --- |
| [MyFirst](projects/A/MyFirst/) | 세 개의 버튼을 세로 `LinearLayout`에 배치한 첫 화면 |
| [Day3EventProcess](projects/A/Day3EventProcess/) | 입력값을 읽어 화면에 표시하고, 여러 버튼 클릭을 Toast로 처리 |
| [Day4BasicWidgetEvent](projects/A/Day4BasicWidgetEvent/) | 체크박스·스위치·토글 버튼의 상태 변경과 뷰 숨김·표시 처리 |
| [Day5LayoutSample](projects/A/Day5LayoutSample/) | `LinearLayout`, `GridLayout`, `RelativeLayout` 등 여러 화면 배치 XML 예제 |
| [Day6SimpleCalc](projects/A/Day6SimpleCalc/) | 버튼식 사칙연산 계산기와 소수점 입력·화면 초기화 처리 |
| [Day6HomeWork](projects/A/Day6HomeWork/) | 사칙연산 계산기 과제. 계산 결과를 정수 또는 실수 형태로 표시 |
| [Day7Widget2](projects/A/Day7Widget2/) | 크로노미터로 소요 시간을 재고 달력에서 선택한 날짜를 표시 |
| [Day9ChangeActivitySample](projects/A/Day9ChangeActivitySample/) | 명시적 `Intent`로 두 Activity를 이동하며 생명주기 호출을 Toast로 확인 |
| [Day10DataIntentSample](projects/A/Day10DataIntentSample/) | `Intent`로 나이를 전달하고 `ActivityResultLauncher`로 1을 더한 값을 반환받음 |
| [Day10HomeWork](projects/A/Day10HomeWork/) | 입력한 숫자를 두 번째 Activity로 보내고 1을 더한 결과를 돌려받는 과제 |
| [Day11WebViewActivitySample](projects/A/Day11WebViewActivitySample/) | `WebView`에서 전달받은 웹 주소를 열고 주소 입력·페이지 이동을 처리 |
| [Day12BRSample](projects/A/Day12BRSample/) | `BroadcastReceiver`로 배터리 부족·비행기 모드·블루투스 상태 변화를 감지 |
| [Day13ImplicitIntentSample](projects/A/Day13ImplicitIntentSample/) | 암시적 `Intent`로 전화·웹·지도·검색·문자·카메라 앱 호출 |
| [Day14ServiveSample](projects/A/Day14ServiveSample/) | `Service`와 `MediaPlayer`로 음악 재생을 시작하거나 중지 |
| [myapp](projects/A/myapp/README.md) | Kotlin 다중 모듈 프로젝트. 화면 배치, 이벤트, 리소스, 알림, Jetpack 등을 모듈별로 실습 |

같은 이름의 프로젝트도 원본에서 서로 다른 폴더에 있던 별개의 사본이므로 두 위치를 유지했습니다. `Day14ServiveSample`은 원본 폴더명을 그대로 사용했습니다.

## 별도 Kotlin 연습 파일

- [`kotlin-practice/Homework1.kt`](kotlin-practice/Homework1.kt): `when`의 점수 범위 조건으로 학점을 출력하는 콘솔 연습입니다. Android Studio 프로젝트와 별개의 파일입니다.

수업 자료 폴더의 `ch9_resource`, `ch11_jetpack`에 있는 Kotlin 소스는 위 `myapp` 모듈의 소스와 동일합니다. 수업 공유 ZIP 안의 예제 코드는 이 저장소에 추가하지 않았습니다.

## 사용 방법

1. 저장소를 내려받습니다.
2. Android Studio에서 원하는 프로젝트 폴더 하나를 엽니다. 예: `projects/A/myapp`.
3. Android SDK 위치가 필요하면 Android Studio가 `local.properties`를 새로 만들도록 합니다.

`myapp`은 전체가 하나의 Gradle 프로젝트입니다. [모듈별 안내](projects/A/myapp/README.md)에서 각 실습 모듈로 이동할 수 있습니다.
