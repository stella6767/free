

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

#### 참고 래퍼런스를 보며 만들었는데, 내가 참조한 래퍼런스에는 browsermob-proxy 를 활용해 har 파일을 만들고, 거기서 request url을 가져오는 식으로 구현되어있다. 근데 나도 똑같이 활용해서 만들어진 har 파일의 entry가 텅텅 비어있는 것 아닌가. 암만 해도 원인을 못 찾겠어서 결국 selenium의 devTool api를 활용해 브라우저의 네트워크를 트래킹하기로 했다.

#### 한글 및 특수문자가 포함된 url은 다운로드시 403 권한 거부 발생함. 다른 사이트에서도 간혹 403 거부가 발생하는 거는, 프록시를 통해 request header를 조작하면 대부분 해결가능한데 (referer, user-agent) 이거는 진짜 모르겠드라, 같은 사이트의 영어 url은 다운 잘 받아지는 거 보면, 인코딩 이슈같긴 한데..

#### Springboot selnium version issue    

이게 진짜 골때린다. chrome driver devtool에서 devTools.createSession() 을 하면,



```angular2html

org.openqa.selenium.SessionNotCreatedException: Could not start a new session. Response code 500. Message: session not created: This version of ChromeDriver only supports Chrome version 114
2024-05-05T21:33:53.304715513Z Current browser version is 124.0.6367.118 with binary path /usr/bin/google-chrome

```

```angular2html


Selenium DevToolsActivePort file doesn't exist

options.addArguments("--remote-debugging-port=9222")

```


![background](/img/cover.jpg)




## 참고

[Java TS Video Downloader](https://medium.com/geekculture/java-ts-video-downloader-a0fcf23ab84a)

https://stackoverflow.com/questions/40939380/how-to-get-file-name-from-content-disposition



