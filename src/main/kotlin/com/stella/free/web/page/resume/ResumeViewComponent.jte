@param String author

<div class="font-sans antialiased text-gray-100 leading-normal tracking-wider bg-cover  h-auto"
     style="background-image:url('/img/resumeBackground.png');">


    <div class="max-w-4xl flex items-center h-auto lg:h-screen flex-wrap mx-auto my-32 lg:my-0">

        <!--Main Col-->
        <div id="profile"
             class="w-full lg:w-5/5 rounded-lg lg:rounded-l-lg lg:rounded-r-none shadow-2xl bg-gray-900 opacity-75 mx-6 lg:mx-0">


            <div class="p-4 md:p-12 text-center lg:text-left">
                <!-- Image for mobile view-->
                <div class="block lg:hidden rounded-full shadow-xl mx-auto -mt-16 h-48 w-48 bg-cover bg-center"></div>

                <h1 class="text-3xl font-bold pt-8 lg:pt-0">${author}</h1>
                <div class="mx-auto lg:mx-0 w-4/5 pt-3 border-b-2 border-green-500 opacity-25"></div>
                <p class="pt-4 text-base font-bold flex items-center justify-center lg:justify-start">
                    <svg class="h-4 fill-current text-green-700 pr-4" xmlns="http://www.w3.org/2000/svg"
                         viewBox="0 0 20 20">
                        <path
                                d="M9 12H1v6a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-6h-8v2H9v-2zm0-1H0V5c0-1.1.9-2 2-2h4V2a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v1h4a2 2 0 0 1 2 2v6h-9V9H9v2zm3-8V2H8v1h4z"/>
                    </svg>
                    I'm a Software Developer
                </p>
                <p class="pt-2 text-gray-600 text-xs lg:text-sm flex items-center justify-center lg:justify-start">
                    <svg class="h-4 fill-current text-green-700 pr-4" xmlns="http://www.w3.org/2000/svg"
                         viewBox="0 0 20 20">
                        <path
                                d="M10 20a10 10 0 1 1 0-20 10 10 0 0 1 0 20zm7.75-8a8.01 8.01 0 0 0 0-4h-3.82a28.81 28.81 0 0 1 0 4h3.82zm-.82 2h-3.22a14.44 14.44 0 0 1-.95 3.51A8.03 8.03 0 0 0 16.93 14zm-8.85-2h3.84a24.61 24.61 0 0 0 0-4H8.08a24.61 24.61 0 0 0 0 4zm.25 2c.41 2.4 1.13 4 1.67 4s1.26-1.6 1.67-4H8.33zm-6.08-2h3.82a28.81 28.81 0 0 1 0-4H2.25a8.01 8.01 0 0 0 0 4zm.82 2a8.03 8.03 0 0 0 4.17 3.51c-.42-.96-.74-2.16-.95-3.51H3.07zm13.86-8a8.03 8.03 0 0 0-4.17-3.51c.42.96.74 2.16.95 3.51h3.22zm-8.6 0h3.34c-.41-2.4-1.13-4-1.67-4S8.74 3.6 8.33 6zM3.07 6h3.22c.2-1.35.53-2.55.95-3.51A8.03 8.03 0 0 0 3.07 6z"/>
                    </svg>
                    My Location - Seoul
                </p>


                <p class="pt-8 text-sm">
                    주로 벡엔드 개발파트를 맡고 있는 개발자입니다. 다양한 기술스택에 관심이 있으며, 가장 좋아하는 언어는 코틀린입니다.
                </p>


                <h2 class="text-xl font-bold mt-6">Skills</h2>

                <h3 class="text-lg indent-2	font-bold mb-1 mt-2">Main</h3>
                <p class="text-sm indent-2">
                    GIT, AWS, MySQL, SpringBoot, JAVA/Kotlin, Gradle/Maven, JPA/MyBatis, Linux
                </p>


                <h3 class="text-lg indent-2	font-bold mb-1 mt-2">Sub</h3>
                <p class="text-sm indent-2">
                    JavaScript, React, Node.js, HTML/CSS, Tailwind Css, Jenkins, Docker, Android, Shell Script ...
                </p>

                <h2 class="text-xl font-bold mt-4 mb-1"> Currently Interested in Technologies</h2>
                <p class="text-sm indent-2">
                    HTMX, Go, Jetpack Compose
                </p>


                <h2 class="text-xl font-bold mt-6 mb-4">Experience</h2>
                <div class="mb-6">
                    <div class="flex justify-between">
                        <span class="text-gray-600 font-bold">Server Developer</span>
                        <p>
                            <span class="text-gray-600 mr-2">at SP Company</span>
                            <span class="text-gray-600">2022 - ..</span>
                        </p>
                    </div>
                    <ul class="mt-2">
                        <li class="text-sm">기밀</li>
                    </ul>
                </div>

                <div class="mb-6">
                    <div class="flex justify-between">
                        <span class="text-gray-600 font-bold">Server Developer</span>
                        <p>
                            <span class="text-gray-600 mr-2">at Lunalabs Company</span>
                            <span class="text-gray-600">2021 - 2022</span>
                        </p>
                    </div>
                    <ul class="mt-2">
                        <li class="text-sm mb-1">Sap(ERP) 연동해서, 키 대여 전자사물함 관련 API 개발 (SpringBoot + JAVA 8) </li>
                        <li class="text-sm mb-1">
                            KCC 유지보수 - node.js 기반 (express.js + ejs template + react.js) 으로 만들어진 웹 사이트 유지보수,
                            WespJS SDK
                        </li>
                        <li class="text-sm mb-1">
                            전기자전거(IOT) 중앙 컨트롤러 GW 제작 - 기기센서가 전달하는 코드(헥스)를 받아 변환 후
                            API 서버에 전달. (NIO Socket + Springboot) (기여도 100%)
                        </li>
                        <li class="text-sm mb-1">
                            환자 모니터링 웹 기반 솔루션(POC) 개발 -
                            환자 기기(QT)와 연동할 GW (hl7 프로토콜) (Springboot without WebMVC + NIO Socket)
                            + 웹 API 서버 제작(Springboot + Oracle DB + Mysql DB + SSE + NIO Socket + FTP) (기여도 100%)
                            + 웹 페이지 프론트 작업 (next.js + Redux + chartjs-plugin-streaming) 실시간 데이터 스트리밍 차트 구현 (기여도 70%)
                        </li>
                        <li class="text-sm mb-1">
                            크레스콤 비동기 기능 추가 - 기존 API 호출 비동기 처리, ui에 프로그레스바로 표시 대체 (Springboot + JSP + Jquery) (기여도 100%)
                        </li>
                        <li class="text-sm mb-1">
                            주차관리 서버 - 입출차 관리 API 서버(springboot + mySql + myBatis) (기여도 100%)
                            웹페이지 제작(React.js + redux + axios) (기여도 50%)
                        </li>
                    </ul>
                </div>

                <div class="pt-12 pb-8">
                    <button class="bg-green-700 hover:bg-green-900 text-white font-bold py-2 px-4 rounded-full">
                        Get In Touch 노션, velog, naver blog, github, youtube, email
                    </button>
                </div>

                <div class="mt-6 pb-16 lg:pb-0 w-4/5 lg:w-full mx-auto flex flex-wrap items-center justify-between">
                    <a class="link" href="#" data-tippy-content="@facebook_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img" viewBox="0 0 24 24"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>Facebook</title>
                            <path
                                    d="M22.676 0H1.324C.593 0 0 .593 0 1.324v21.352C0 23.408.593 24 1.324 24h11.494v-9.294H9.689v-3.621h3.129V8.41c0-3.099 1.894-4.785 4.659-4.785 1.325 0 2.464.097 2.796.141v3.24h-1.921c-1.5 0-1.792.721-1.792 1.771v2.311h3.584l-.465 3.63H16.56V24h6.115c.733 0 1.325-.592 1.325-1.324V1.324C24 .593 23.408 0 22.676 0"/>
                        </svg>
                    </a>
                    <a class="link" href="#" data-tippy-content="@twitter_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img" viewBox="0 0 24 24"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>Twitter</title>
                            <path
                                    d="M23.954 4.569c-.885.389-1.83.654-2.825.775 1.014-.611 1.794-1.574 2.163-2.723-.951.555-2.005.959-3.127 1.184-.896-.959-2.173-1.559-3.591-1.559-2.717 0-4.92 2.203-4.92 4.917 0 .39.045.765.127 1.124C7.691 8.094 4.066 6.13 1.64 3.161c-.427.722-.666 1.561-.666 2.475 0 1.71.87 3.213 2.188 4.096-.807-.026-1.566-.248-2.228-.616v.061c0 2.385 1.693 4.374 3.946 4.827-.413.111-.849.171-1.296.171-.314 0-.615-.03-.916-.086.631 1.953 2.445 3.377 4.604 3.417-1.68 1.319-3.809 2.105-6.102 2.105-.39 0-.779-.023-1.17-.067 2.189 1.394 4.768 2.209 7.557 2.209 9.054 0 13.999-7.496 13.999-13.986 0-.209 0-.42-.015-.63.961-.689 1.8-1.56 2.46-2.548l-.047-.02z"/>
                        </svg>
                    </a>
                    <a class="link" href="#" data-tippy-content="@github_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img" viewBox="0 0 24 24"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>GitHub</title>
                            <path
                                    d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"/>
                        </svg>
                    </a>
                    <a class="link" href="#" data-tippy-content="@unsplash_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img" viewBox="0 0 24 24"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>Unsplash</title>
                            <path d="M7.5 6.75V0h9v6.75h-9zm9 3.75H24V24H0V10.5h7.5v6.75h9V10.5z"/>
                        </svg>
                    </a>
                    <a class="link" href="#" data-tippy-content="@dribble_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img" viewBox="0 0 24 24"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>Dribbble</title>
                            <path
                                    d="M12 24C5.385 24 0 18.615 0 12S5.385 0 12 0s12 5.385 12 12-5.385 12-12 12zm10.12-10.358c-.35-.11-3.17-.953-6.384-.438 1.34 3.684 1.887 6.684 1.992 7.308 2.3-1.555 3.936-4.02 4.395-6.87zm-6.115 7.808c-.153-.9-.75-4.032-2.19-7.77l-.066.02c-5.79 2.015-7.86 6.025-8.04 6.4 1.73 1.358 3.92 2.166 6.29 2.166 1.42 0 2.77-.29 4-.814zm-11.62-2.58c.232-.4 3.045-5.055 8.332-6.765.135-.045.27-.084.405-.12-.26-.585-.54-1.167-.832-1.74C7.17 11.775 2.206 11.71 1.756 11.7l-.004.312c0 2.633.998 5.037 2.634 6.855zm-2.42-8.955c.46.008 4.683.026 9.477-1.248-1.698-3.018-3.53-5.558-3.8-5.928-2.868 1.35-5.01 3.99-5.676 7.17zM9.6 2.052c.282.38 2.145 2.914 3.822 6 3.645-1.365 5.19-3.44 5.373-3.702-1.81-1.61-4.19-2.586-6.795-2.586-.825 0-1.63.1-2.4.285zm10.335 3.483c-.218.29-1.935 2.493-5.724 4.04.24.49.47.985.68 1.486.08.18.15.36.22.53 3.41-.43 6.8.26 7.14.33-.02-2.42-.88-4.64-2.31-6.38z"/>
                        </svg>
                    </a>

                    <a class="link" href="#" data-tippy-content="@youtube_handle">
                        <svg class="h-6 fill-current text-gray-600 hover:text-green-700" role="img"
                             xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                            <title>YouTube</title>
                            <path
                                    d="M23.495 6.205a3.007 3.007 0 0 0-2.088-2.088c-1.87-.501-9.396-.501-9.396-.501s-7.507-.01-9.396.501A3.007 3.007 0 0 0 .527 6.205a31.247 31.247 0 0 0-.522 5.805 31.247 31.247 0 0 0 .522 5.783 3.007 3.007 0 0 0 2.088 2.088c1.868.502 9.396.502 9.396.502s7.506 0 9.396-.502a3.007 3.007 0 0 0 2.088-2.088 31.247 31.247 0 0 0 .5-5.783 31.247 31.247 0 0 0-.5-5.805zM9.609 15.601V8.408l6.264 3.602z"/>
                        </svg>
                    </a>
                </div>

                <!-- Use https://simpleicons.org/ to find the svg for your preferred product -->

            </div>

        </div>


    </div>

</div>
<script>


</script>

</div>