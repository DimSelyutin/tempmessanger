package com.petproject.tempmessanger.dto;

import com.petproject.tempmessanger.util.MessageType;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private Long id;
    private String content;
    private String sender;
    private long roomId;
    private MessageType type;
    private String created_at;
    
}