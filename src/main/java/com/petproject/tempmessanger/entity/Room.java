package com.petproject.tempmessanger.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Room {
    @Id
    private Long id;

    private String name; // Название комнаты

    // Дополнительные поля, которые могут потребоваться в вашем приложении:

    private String description; // Описание комнаты
    private String createdBy; // Имя пользователя, создавшего комнату
    private LocalDateTime createdAt; // Дата и время создания комнаты

    
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatMessage> chatMessages;
}
