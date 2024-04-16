package com.petproject.tempmessanger.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.petproject.tempmessanger.entity.Room;
import com.petproject.tempmessanger.entity.ChatMessage;
import com.petproject.tempmessanger.repository.RoomRepository;
import com.petproject.tempmessanger.repository.ChatMessageRepository;

@Service
public class ChatService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;
    
    public void createRoomAndSaveMessages(Room room, List<ChatMessage> messages) {
        roomRepository.save(room);
        chatMessageRepository.saveAll(messages);
    }
}