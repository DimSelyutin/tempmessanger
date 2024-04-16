package com.petproject.tempmessanger.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.petproject.tempmessanger.entity.ChatMessage;
import com.petproject.tempmessanger.entity.Room;
import com.petproject.tempmessanger.entity.UserRoomMapping;
import com.petproject.tempmessanger.repository.ChatMessageRepository;
import com.petproject.tempmessanger.repository.RoomRepository;
import com.petproject.tempmessanger.repository.UserRoomMappingRepository;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRoomMappingRepository userRoomMappingRepository;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getContent().length() > 255) {
            chatMessage.setContent(chatMessage.getContent().substring(0, 255));
        }

        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public List<ChatMessage> addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = chatMessage.getSender();
        Long roomId = chatMessage.getRoomId();
        List<ChatMessage> chatMessagesList = joinOrCreateRoom(roomId, username);

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", chatMessage.getRoomId());

        return chatMessagesList;
    }

    public List<ChatMessage> joinOrCreateRoom(Long roomId, String username) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            return chatMessageRepository.findByRoomId(room.getId());
        } else {
            Room newRoom = new Room();
            newRoom.setName("Created by " + username);
            newRoom.setId(roomId);
            Room savedRoom = roomRepository.save(newRoom);

            UserRoomMapping mapping = new UserRoomMapping();
            mapping.setUsername(username);
            mapping.setRoomId(savedRoom.getId());
            userRoomMappingRepository.save(mapping);

            return new ArrayList<>(); // Возвращаем пустой список сообщений для новой комнаты
        }
    }
}