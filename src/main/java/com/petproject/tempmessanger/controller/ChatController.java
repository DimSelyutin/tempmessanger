package com.petproject.tempmessanger.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.petproject.tempmessanger.dto.ChatMessageDTO;
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
    public ChatMessageDTO sendMessage(@Payload ChatMessage chatMessage) {
        if (chatMessage.getContent().length() > 255) {
            chatMessage.setContent(chatMessage.getContent().substring(0, 255));
        }
        chatMessageRepository.save(chatMessage);
        return convertToDto(chatMessage);
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public List<ChatMessageDTO> addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        String username = chatMessage.getSender();
        Long roomId = getRoomId(chatMessage);
        List<ChatMessageDTO> chatMessagesList = joinOrCreateRoom(roomId, username);

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("roomId", roomId);

        return chatMessagesList;
    }

    private Long getRoomId(ChatMessage chatMessage) {
        Room room = chatMessage.getRoom();
        return room != null ? room.getId() : null;
    }

    private List<ChatMessageDTO> joinOrCreateRoom(Long roomId, String username) {
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        String owner = "Created by ";

        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            return chatMessageRepository.findByRoomId(room.getId())
                    .stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            Room newRoom = new Room();
            
            //здесь один id
            newRoom.setId(roomId);
            newRoom.setName(owner + username);
            //а здесь уже другой совсем??
            Room savedRoom = roomRepository.save(newRoom);
            
            UserRoomMapping mapping = new UserRoomMapping();
            mapping.setUsername(username);
            mapping.setRoomId(savedRoom.getId());
            userRoomMappingRepository.save(mapping);

            return new ArrayList<>();
        }
    }

    private ChatMessageDTO convertToDto(ChatMessage chatMessage) {
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

        chatMessageDTO.setId(chatMessage.getId());

        if (chatMessage.getRoom() != null) {
            chatMessageDTO.setRoomId(chatMessage.getRoom().getId());
        }

        chatMessageDTO.setContent(chatMessage.getContent());
        chatMessageDTO.setSender(chatMessage.getSender());

        if (chatMessage.getCreated_at() != null) {
            chatMessageDTO
                    .setCreated_at(chatMessage.getCreated_at().format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yy")));
        }

        return chatMessageDTO;
    }
}