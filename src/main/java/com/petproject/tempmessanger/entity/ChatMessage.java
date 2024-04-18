package com.petproject.tempmessanger.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.petproject.tempmessanger.util.MessageType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "chat_message_table")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private MessageType type;
    private String content;
    private String sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Room room;


    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private LocalDateTime created_at;
   

}
