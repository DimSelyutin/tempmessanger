package com.petproject.tempmessanger.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.petproject.tempmessanger.entity.Room;
import com.petproject.tempmessanger.repository.RoomRepository;
import com.petproject.tempmessanger.repository.UserRoomMappingRepository;

@Service
public class RoomService {

    // @Autowired
    // private RoomRepository roomRepository;

    // @Autowired
    // private UserRoomMappingRepository userRoomMappingRepository;

    // @Scheduled(cron = "0 0 * * *") // Запуск каждый час
    // public void deleteExpiredRooms() {
    //     LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

    //     List<Room> expiredRooms = roomRepository.findByCreatedAtBefore(oneHourAgo);
    //     for (Room room : expiredRooms) {
    //         // Удалить связанные записи перед удалением комнаты
    //         userRoomMappingRepository.deleteById(room.getId());
    //         roomRepository.delete(room);
    //     }
    // }
}
