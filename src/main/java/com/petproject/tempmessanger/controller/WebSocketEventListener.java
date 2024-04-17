package com.petproject.tempmessanger.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.petproject.tempmessanger.entity.ChatMessage;

@Component
public class WebSocketEventListener {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

        logger.info("New connection: ");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        Long roomId = (Long) headerAccessor.getSessionAttributes().get("roomId");

        if (username != null) {
            handleUserDisconnect(username, roomId);
        }
    }

    private void handleUserDisconnect(String username, Long roomId) {
        logger.info("User disconnected: " + username);

        if (roomId != null) {
            ChatMessage chatMessage = createLeaveMessage(username, roomId);
            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        } else {
            logger.warn("Room ID not found for user: " + username);
        }
    }

    private ChatMessage createLeaveMessage(String username, Long roomId) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        chatMessage.setSender(username);
        

        return chatMessage;
    }
}
