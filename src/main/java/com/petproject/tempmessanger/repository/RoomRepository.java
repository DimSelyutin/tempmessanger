package com.petproject.tempmessanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petproject.tempmessanger.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    
}