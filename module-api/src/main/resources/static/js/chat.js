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
    //let response = JSON.parse(payload.body);
    console.log("response", payload.body);

    const chatDom =
        new DOMParser().parseFromString(payload.body, 'text/html').body.childNodes[0];

    console.log("chatDom", chatDom)
    //messageArea.insertAdjacentHTML("beforeend", response.html)
    let sender = chatDom.querySelector("#chat-sender").value;
    console.log("sender", sender)

    if (username == sender){
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
