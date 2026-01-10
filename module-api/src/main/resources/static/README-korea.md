

# Easy Fullstack Web with SpringBoot (with Zero cost)

- - -

## Backend

### Kotlin

제일 좋아하는 언어이다. 강타입이면서 null로부터 안전하게 코딩이 가능하고, 각종 편의성 기능들을 듬뿍 제공한다.
거기다 풍부한 자바의 에코시스템을 고대로 갖다 쓸 수 있다는 장점까지

### Spring Boot

서버 사이드에서 여러가지 프레임워크를 써봤지만, 전반적인 종합 능력치에서 SpringBoot 를 따라올 수 있는 프레임워크는 없다고 생각한다.

### JPA

하이버네이트를 구현체로 쓰는 자바 공식 표준 ORM 인터페이스. 다른 언어 + 생태계 다 합쳐서 가장 성숙하고 안정된 ORM API 라고 생각한다.

### Kotlin JDSL

이제 3.0 으로 업그레이드 되면서, JPQL 기반으로 새로 만들어졌다. JPQL로 할 수 있는 모든 짓을 Kotlin JDSL로 TypeSafe하게 작성할 수 있다는 것이고,
QueryDsl 같은 류의 Query Builder 라이브러리를 쓸 때의 번거로운 빌드 스텝을 셋업할 필요가 이제는 없어졌다.


## FrontEnd

### JTE

컴파일 타임 검증 가능하며 HTML 친화적인 템플릿 엔진. 거기다 핫 리로드 지원과 성능까지!

### HTMX

서버에서 html 조각을 던져주면, html tag 단위로 갈아끼울 수 있게 도와주는 JS 라이브러리이다.
1인 웹 개발시 프론트엔드 벡엔드를 나누고, HTTP API 위로 JSON 프로토콜로 통신하는 구조를 짜는 것은 지나치게 비효율적이다.
서버에서 JTE로 빌드된 html 조각을 직접 던져주자. 전체 리로드를 요구하지 않고 마치 SPA 프레임워크로 만든 웹앱같은 부드러운 화면 전환이 가능해진다.
더 이상 클라이언트 상태와 서버 상태를 일치시키려고 골머리를 안 써도 된다. 가장 맘에 드는 점은 사용하는데 별도의 빌드 스텝과 종속성을 요구하지 않는다는 것.
접근방식은 심플하고 빠르며 우아하다.

1. not java -> json -> javsScript -> HTML
2. Just JAVA -> HTML

### tailwindcss + daisy ui

부트스트랩 대신 tailwindcss를 선택한다.
디자인은 잼병이기에, 잘 만든 UI Component Library를 찾게 된다.   
tailwindcss를 사용하는 컴포넌트 라이브러리를 찾아본다. 원하는 요구사항은

1. 돈이 안 들고
2. 별도의 다른 종속성 필요없이 간편한 설치가능
3. HTML + tailwindcss 로 스니펫을 제공해 복붙해서 편하게 갈아끼울 수 있는
4. 깃허브에서 인지도 있는

위 조건을 다 만족하는 게, daisy ui

### toast UI

마크다운과 Html을 둘 다 지원해주는 웹 에디터. 앞으로 에디터 쓸 일 있으면 이 친구로 고정


## Infra

### AWS EC2 ==> GCP VM

프리티어 끝나서 돈 나간다. GCP VM으로 이전

### Github action

깃허브 액션에 쉘 스크립트 연동해서 VM에 배포하도록 간편하게 설정했다. 젠킨스 및 다른 CI/CD 툴은 호스팅해주는 서버가
따로 필요한데 깃허브에서 원격으로 무료로 제공해주는 서버가 있다는 게 참 매력적이다.

### Docker

주위 환경과는 독립적인 배포

### Let's encrypt + NGINX

ACM을 고민했으나, ALB를 필수로 동반한다는 점에서 무료인 HTTPS 인증서 + Nginx 로 셋팅

### SupaBase postgresql ==> GCP Mysql

RDB를 무료로 제공해주는 서비스가 생각보다 별로 없다. Supabase 는 써보니 한동안 안 쓰면 중간에 꺼지는 게 너무 크리티컬해 이전

### Github Oauth2

소셜로그인으로 Github 를 사용한다. 나머지 소셜로그인에 비해 압도적인 간편함
