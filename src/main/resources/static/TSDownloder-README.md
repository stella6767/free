

# TS FILE Downloader 
- - -

## 설명

웹 사이트의 url을 입력하면, Selenium을 이용해서, 네트워크 url을 트래킹 해, 마스터 .m3u8 파일을 찾고 
그걸 이용해 TS File List를 다운받은 뒤 FFMPEG을 이용해 하나의 파일로 합쳐서 다운받게 해주는 프로그램

## 채택 기술

- JDK 21
- FFMPEG 
- [Chrome Driver](https://chromedriver.chromium.org/downloads)
- Selenium


## 문제

### 1. lightbody/browsermob-proxy har 파일 empty

참고 래퍼런스를 보며 만들었는데, 내가 참조한 래퍼런스에는 [browsermob-proxy](https://github.com/lightbody/browsermob-proxy) 를 활용해 HAR 파일을 만들고, 
거기서 request url을 가져오는 식으로 구현되어있다. 근데 나도 똑같이 사용해서 만들어진 har 파일의 entry가 텅텅 비어있는 것 아닌가. 암만 해도 원인을 못 찾겠어서 결국 selenium의 devTool api를 활용해 브라우저의 네트워크 요청을 트래킹하기로 했다.

### 2. 한글 포함 url

한글 및 특수문자가 포함된 url은 다운로드시 403 권한 거부 발생함. 다른 사이트에서도 간혹 403 거부가 발생하는 거는, 프록시를 통해 request header를 조작하면 대부분 해결가능한데 (referer, user-agent) 이거는 진짜 모르겠드라, 같은 사이트의 영어 url은 다운 잘 받아지는 거 보면, 인코딩 이슈같긴 한데..

### 3. Springboot selenium version issue

```angular2html
java.lang.NoSuchMethodError:'java.util.function.Function org.openqa.selenium.devtools.ConverterFunctions.map(java.lang.String, java.util.function.Function)'
```

이게 진짜 골때린다. chrome driver devtool에서 devTools.createSession() 을 하면, 위와 같은 Method를 찾을 수 없다는 런타임 익셉션이 
터지는데, 막상 찾아보면 잘 있단 말이지.. 깃허브 이슈를 이잡듯 뒤진 결과 springboot 가 selenium 예전 version을
참조하고 있는 갓 같다는 정보를 얻게 된다.

[관련 스택오버플로우 링크](https://stackoverflow.com/questions/70881256/selenium-4-1-and-spring-boot-web-driver-version-issue)

[spring-boot dependencies 정보](https://github.com/spring-projects/spring-boot/blob/v3.2.0/spring-boot-project/spring-boot-dependencies/build.gradle#L1449)

그래서 build.gradle.kts 에 현재 selenium version을 오버라이딩 했는데 저 에러가 거짓말 같이 사라졌다. 시간낭비 오지게 했다. 

```angular2html
ext["selenium.version"] = "4.20.0"
```

### 4. Selenium DevToolsActivePort file doesn't exist

이번에는 도커로 말아서 프로세스로 띄우니 발생하는 이슈였다. chromdriver에 관련 옵션들을 추가하니 이 에러는 안 뜬다.

```angular2html
options.addArguments("--remote-debugging-port=9222")
options.addArguments("--no-sandbox")
options.addArguments("--headless") //브라우저 안띄움
options.addArguments("--single-process")
options.addArguments("--disable-dev-shm-usage")
```


### 5. session not created: This version of ChromeDriver only supports Chrome version 114

```angular2html
org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Response code 500. Message: session not created: This version of ChromeDriver only supports Chrome version 114
2024-05-05T21:33:53.304715513Z Current browser version is 124.0.6367.118 with binary path /usr/bin/google-chrome
```

도커 파일에서 크롬 브라우저를 설치하고, 크롬 드라이버를 설치하는 스크립트를 작성하는데 버전을 잘 보고 다시 짜뒀다.
여기서 문제는, 내가 arm64v8 플랫폼 용. jdk 이미지를 가지고 도커파일을 만들었는데, arm 용 크롬브라우저가 지원되지
않더라.. 진짜 순조롭게 되는 게 하나 없다. 그래서  --platform=linux/amd64 를 명시하고 크롬 드라이버와 크롬 브라우저를 깔았다.

```angular2html
FROM --platform=linux/amd64 openjdk:21-jdk-slim

# Google Chrome의 최신 .deb 파일 다운로드
RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb

RUN apt-get -y install ./google-chrome-stable_current_amd64.deb

RUN wget -O /tmp/chromedriver.zip https://storage.googleapis.com/chrome-for-testing-public/124.0.6367.91/linux64/chromedriver-linux64.zip
RUN unzip /tmp/chromedriver.zip -d /usr/bin
RUN mv /usr/bin/chromedriver-linux64/chromedriver /usr/bin/

```

컨테이너에 접속해서 확인해보았다.

```angular2html
docker exec -it 8737307c38f4 /bin/bash

root@8737307c38f4:/# chromedriver --version
ChromeDriver 124.0.6367.91 (51df0e5e17a8b0a4f281c1665dbd1b8a0c6b46af-refs/branch-heads/6367@{#984})

root@8737307c38f4:/# google-chrome --version
Google Chrome 124.0.6367.118
```


### 6. ec2 환경에서 DockerFile build - exec /bin/sh: exec format error  

로컬에서 잘 되는 거 확인하고.. ec2 환경에서 똑같은 도커파일로 빌드할려고 하니 안 되드라, 문제는 내 ec2 환경의
Docker가 linux/amd64 플랫폼을 지원해주지 않는 거..

```angular2html
sudo apt-get install -y qemu qemu-user-static
docker buildx ls
```

[관련 스택오버플로우 링크](https://stackoverflow.com/questions/73253352/docker-exec-bin-sh-exec-format-error-on-arm64)

![docker](/img/docker-buildx-ls.png)



### 7. 근데 문제는 그렇게까지 해도 도커 환경에서 안 돌아가드라

```angular2html
 Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Response code 500. Message: session not created: Chrome failed to start: exited normally.
2024-05-06T15:21:19.211466763Z   (chrome not reachable)
2024-05-06T15:21:19.211473388Z   (The process started from chrome location /usr/bin/google-chrome is no longer running, so ChromeDriver is assuming that Chrome has crashed.)
```

아 스트레스.. 일단 포기


## 참고

[Java TS Video Downloader](https://medium.com/geekculture/java-ts-video-downloader-a0fcf23ab84a)

