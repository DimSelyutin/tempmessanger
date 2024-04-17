package com.petproject.tempmessanger.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petproject.tempmessanger.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByCreatedAtBefore(LocalDateTime oneHourAgo);
    
}