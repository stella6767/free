

# TS FILE Downloader 
- - -

## 설명

웹 사이트의 url을 입력하면, Selenium을 이용해서, 네트워크 url을 트래킹 해, 마스터 .m3u8 파일을 찾고 
그걸 이용해 TS File List를 다운받은 뒤 FFMPEG을 이용해 하나의 파일로 합쳐서 다운받게 해주는 프로그램

## System Requirements

- JDK 21
- FFMPEG 
- Chrome Driver


## 문제

- 아래 참고 글을 이용했는데, browerMob
- 한글 및 특수문자가 포함된 url은 다운로드시 403 권한 거부 발생함
- selnium version issue

![background](img/background.jpg)




## 참고

[Java TS Video Downloader](https://medium.com/geekculture/java-ts-video-downloader-a0fcf23ab84a)

https://stackoverflow.com/questions/40939380/how-to-get-file-name-from-content-disposition



