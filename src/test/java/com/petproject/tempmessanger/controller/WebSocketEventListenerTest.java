package com.petproject.tempmessanger.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.springframework.amqp.core.Message;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.petproject.tempmessanger.entity.ChatMessage;

import org.springframework.util.ReflectionUtils;

public class WebSocketEventListenerTest {

    @Mock
    private SessionDisconnectEvent disconnectEvent;

    @Mock
    private SimpMessageSendingOperations messagingTemplate;

    @Mock
    private StompHeaderAccessor headerAccessor;

    @Test
    public void testHandleUserDisconnect() {
        WebSocketEventListener webSocketListener = new WebSocketEventListener();

    }

}