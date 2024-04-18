package com.petproject.tempmessanger.controller;


import org.mockito.Mockito;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import static org.mockito.ArgumentMatchers.eq;

import java.util.HashMap;
import java.util.Map;

public class WebSocketEventListenerTest {

    public static void main(String[] args) {
        testHandleUserDisconnect();
    }

    public static void testHandleUserDisconnect() {
        // WebSocketEventListener webSocketEventListener = new WebSocketEventListener();
        
        // // Создание мок объекта SessionDisconnectEvent
        // SessionDisconnectEvent event = new SessionDisconnectEvent(new Object(), null, "sessionId");

        // // Моделирование атрибутов сессии
        // Map<String, Object> sessionAttributes = new HashMap<>();
        // sessionAttributes.put("username", "testUser");
        // sessionAttributes.put("roomId", 123L);
        
        // // Создание mock объекта StompHeaderAccessor
        // StompHeaderAccessor headerAccessor = StompHeaderAccessor.create(StompCommand.DISCONNECT);
        // headerAccessor.setSessionAttributes(sessionAttributes);

        // event.setMessage(headerAccessor.getMessage());

        // // Вызов тестируемого метода
        // webSocketEventListener.handleWebSocketDisconnectListener(event);
    }
}