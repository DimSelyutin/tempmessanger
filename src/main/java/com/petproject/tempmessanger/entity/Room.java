package com.petproject.tempmessanger.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Название комнаты

    // Дополнительные поля, которые могут потребоваться в вашем приложении:

    private String description; // Описание комнаты
    private String createdBy; // Имя пользователя, создавшего комнату
    private LocalDateTime createdAt; // Дата и время создания комнаты
}
