
# HTMX + kotlinx.html 로 TodoApp 만들기

## 목표

1. 단 한 줄의 자바스크립트 작성 없이 (적어도 개발자가 쓰는 것에 한해서)
2. 별도의 SPA 프레임워크 없이 SPA 로 만들기
3. CRUD 지원 + Pagination


```angular2html

https://github.com/oshai/kotlin-logging

https://github.com/bigskysoftware/htmx

https://github.com/Kotlin/kotlinx.html

https://github.com/line/kotlin-jdsl

https://github.com/saadeghi/daisyui

https://github.com/tailwindlabs/tailwindcss
```


## 소감

순수 코틀린만을 이용하여, 인터렉티브한 싱글페이지 웹앱을 구현해보는 건 어떨까 해서 만들었다. 물론 htmx 자체가
자바스크립트를 이용하여 만든 라이브러리라고 따질 수 있지만, 그렇게 따진다면, 모든 컴퓨터 언어는 0과 1로 이루어진 기계어로 컴파일되니까 나는 기계어를
쓰고 있다고 하는 만큼 의미없는 말이다. 중요한 건 만드는 당사자가 체감을 하냐, 안 하냐다. 애석하게도 단 한 줄의 자바스크립트 작성 없이는 실패했다.

```angular2html
attributes["hx-on--after-request"] = "javascript:document.getElementById('new-todo').value='';"
```

목표만을 위해서라면 좀 더 다른 방안으로 구현할 여지는 있지만 이게 제일 간편한 방법 같아서 이 부분은 자바스크립트를 직접 사용했다.
완전한 SPA로 모든 전환은 단일 페이지에서 html tag 단위로 갈아끼운다. 아주 아주 간단한 TodoApp이지만 대부분의 어플리케이션이
기본적으로 갖춰야 되는 CRUD를 충족한다. 느낀 점이라면, 왠만한 어플리케이션, 특히 백오피스 같은 데이터 발싸개류 앱은 HTMX만으로도 차고
넘치게 다룰 수 있겠다는 거. HTMX에 대해 느낀 장점은 

1. 낮은 러닝커브 

2. 별도의 다른 종속성 X 

3. 높은 호환성 및 이식성

4. data shipping 의 최소화

5. 로직 코드의 집중


[관련 내 블로그글](https://velog.io/@stella6767/%EB%82%98%EB%8A%94-%EC%A0%95%EB%A7%90-React.js-%EA%B0%80-%EC%8B%AB%EB%8B%A4%EA%B3%A0%EC%9A%94)