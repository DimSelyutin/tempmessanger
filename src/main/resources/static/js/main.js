'use strict';

let usernamePage = document.querySelector('#username-page');
let chatPage = document.querySelector('#chat-page');
let usernameForm = document.querySelector('#usernameForm');
let messageForm = document.querySelector('#messageForm');
let messageInput = document.querySelector('#message');
let messageArea = document.querySelector('#messageArea');
let connectingElement = document.querySelector('.connecting');
let connectingElementHeader = document.querySelector('#chat-header-h');

let stompClient = null;
let username = null;
let roomId = null;
let colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

let connect = (event) => {
    username = document.querySelector('#name').value.trim();
    roomId = document.querySelector('#roomId').value.trim()
    if (username) {

        showChatPage();
        let socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

let showChatPage = () => {
    usernamePage.classList.add('hidden');
    chatPage.classList.remove('hidden');
}

let onConnected = () => {
    subscribeToPublicTopic();
    addUserToChat();
    connectingElement.classList.add('hidden');
    connectingElementHeader.textContent = "Spring Boot Room № " + roomId;
}

let onError = (error) => {
    connectingElement.textContent = 'Не удалось присоединиться! Обновите страницу!';
    connectingElement.style.color = 'red';
}

let sendMessage = (event) => {
    let messageContent = messageInput.value.trim();

    if (messageContent && stompClient) {
        sendChatMessage(messageContent);
        messageInput.value = '';
    }
    event.preventDefault();
}

let sendChatMessage = (messageContent) => {
    let chatMessage = { sender: username, content: messageContent, type: 'CHAT', room: {id: roomId}  };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
}

let subscribeToPublicTopic = () => {
    stompClient.subscribe('/topic/public', onMessageReceived);
}

let addUserToChat = () => {
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({ sender: username, type: 'JOIN', room: {id: roomId} }))
}

// Функция для создания элемента сообщения
let createMessageElement = (message) => {
    let messageElement = document.createElement('li');

    if (message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = `${message.sender} присоединился!`;
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = `${message.sender} left!`;
    } else {
        let avatarElement = document.createElement('i');
        let avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(message.sender);


        let usernameElement = document.createElement('span');
        usernameElement.textContent = message.sender;

        let textElement = document.createElement('p');
        textElement.textContent = message.content;
        messageElement.classList.add('chat-message');


        let spanElement = document.createElement('span');
        spanElement.style.float = "right";
        
        spanElement.textContent = message.created_at;
        messageElement.appendChild(avatarElement);
        messageElement.appendChild(usernameElement);
        messageElement.appendChild(spanElement);
        messageElement.appendChild(textElement);
    }

    return messageElement;
}

// Обновленный метод onMessageReceived
let onMessageReceived = (payload) => {
    let messages = JSON.parse(payload.body);

    if (Array.isArray(messages)) {
        messages.forEach(message => {
            let messageElement = createMessageElement(message);
            messageArea.appendChild(messageElement);
            messageArea.scrollTop = messageArea.scrollHeight;
        });
    } else {
        let messageElement = createMessageElement(messages);
        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
    }
}
let getAvatarColor = (messageSender) => {
    let hash = 0;

    for (let i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    return colors[Math.abs(hash % colors.length)];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)