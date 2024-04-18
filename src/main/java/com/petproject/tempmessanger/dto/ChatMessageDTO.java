package com.petproject.tempmessanger.dto;

import com.petproject.tempmessanger.util.MessageType;

import lombok.Data;


public record ChatMessageDTO(
        Long id,
        String content,
        String sender,
        long roomId,
        MessageType type,
        String created_at) {

    
}

