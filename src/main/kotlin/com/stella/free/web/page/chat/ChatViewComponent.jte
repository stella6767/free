<div id="chat-name-page" class="bg-yellow-400 h-screen overflow-hidden flex items-center justify-center">
    <div class="bg-white lg:w-5/12 md:6/12 w-10/12 shadow-3xl">
        <div class="bg-gray-800 absolute left-1/2 transform -translate-x-1/2 -translate-y-1/2 rounded-full p-4 md:p-8">
        </div>
        <form id="chat-username-form"
              class="p-12 md:p-24">
            <div class="flex items-center text-lg mb-6 md:mb-8">
                <svg class="absolute ml-3" width="24" viewBox="0 0 24 24">
                    <path d="M20.822 18.096c-3.439-.794-6.64-1.49-5.09-4.418 4.72-8.912 1.251-13.678-3.732-13.678-5.082 0-8.464 4.949-3.732 13.678 1.597 2.945-1.725 3.641-5.09 4.418-3.073.71-3.188 2.236-3.178 4.904l.004 1h23.99l.004-.969c.012-2.688-.092-4.222-3.176-4.935z"/>
                </svg>
                <input required type="text" id="username"
                       class="bg-gray-200 pl-12 py-2 md:py-4 focus:outline-none w-full" placeholder="Username"/>
            </div>
            <button class="bg-gradient-to-b from-gray-700 to-gray-900 font-medium p-2 md:p-4 text-white uppercase w-full">
                오픈 채팅방 접속
            </button>
        </form>
    </div>
</div>


<div id="chat-page" class="flex-1 p:2 sm:p-6 justify-between flex flex-col container mx-auto hidden">

    <div id="message-area" class="overflow-y-auto " style="height: 1000px;">
    </div>

    <div class="border-t-2 border-gray-200 px-4 pt-4 mb-2 sm:mb-0">
        <div class="relative flex">
            <input id="message-input"
                   type="text" placeholder="Write your message!"
                   class="w-full focus:outline-none focus:placeholder-gray-400 text-gray-600 placeholder-gray-600 pl-12 bg-gray-200 rounded-md py-3">
            <div class="absolute right-0 items-center inset-y-0 hidden sm:flex">
                <button id="message-form"
                        type="button"
                        class="inline-flex items-center justify-center rounded-lg px-4 py-3 transition duration-500 ease-in-out text-white bg-blue-500 hover:bg-blue-400 focus:outline-none">
                    <span class="font-bold">Send</span>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" fill="currentColor"
                         class="h-6 w-6 ml-2 transform rotate-90">
                        <path d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"></path>
                    </svg>
                </button>
            </div>
        </div>
    </div>
</div>





<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.4.0/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>

<script>

    'use strict';

    const chatNamePage = document.querySelector('#chat-name-page');
    const chatPage = document.querySelector('#chat-page');
    const usernameForm = document.querySelector('#chat-username-form');
    const messageForm = document.querySelector('#message-form');
    const messageInput = document.querySelector('#message-input');
    const messageArea = document.querySelector('#message-area');

    let stompClient = null;
    let username = null;

    const connect = (event) => {
        username = document.querySelector('#username').value.trim();
        if (username) {
            chatNamePage.classList.add('hidden');
            chatPage.classList.remove('hidden');
            const socket = new SockJS('/ws');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, onConnected, onError);
        }
        event.preventDefault();
    }


    const onConnected = () => {
        // Subscribe to the Public Topic
        stompClient.subscribe('/topic/public', onMessageReceived);
        console.log("add User")

        let chatDto = {
            sender: username,
            type: 'JOIN',
            message: username + " JOIN",
        };

        stompClient.send("/app/chat.addUser",
            {},
            JSON.stringify(chatDto)
        )
    }


    const onError = (error) => {
        console.error(error)
    }


    const sendMessage = (event) => {

        let messageContent = messageInput.value.trim();

        if (messageContent && stompClient) {
            const chatMessage = {
                sender: username,
                message: messageContent,
                type: 'TALK'
            };

            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            messageInput.value = '';
        }

        event.preventDefault();
    }


    const onMessageReceived = (payload) => {
        console.log("arrived", payload)
        let response = JSON.parse(payload.body);
        console.log("response", response);

        const chatDom =
            new DOMParser().parseFromString(response.html, 'text/html').body.childNodes[0];

        console.log("chatDom", chatDom)

        //messageArea.insertAdjacentHTML("beforeend", response.html)

        if (username == response.sender){
            console.log("username", username)
            chatDom.querySelector("#chat-box-comment").classList.add('items-end', 'justify-end');
            chatDom.querySelector("#chat-message-box").classList.add('bg-blue-600', 'text-white');
            chatDom.querySelector("#chat-message-box").classList.remove('bg-gray-300', 'text-gray-600');
        }

        console.log("chatDom", chatDom)
        messageArea.append(chatDom)

    }


    usernameForm.addEventListener('submit', connect, true)
    messageForm.addEventListener('click', sendMessage, true)

</script>