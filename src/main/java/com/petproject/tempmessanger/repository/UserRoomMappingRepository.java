package com.petproject.tempmessanger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.petproject.tempmessanger.entity.UserRoomMapping;
@Repository
public interface UserRoomMappingRepository extends JpaRepository<UserRoomMapping, Long> {
    
}