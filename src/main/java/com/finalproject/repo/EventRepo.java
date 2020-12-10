package com.finalproject.repo;

import com.finalproject.entities.EventEntity;
import com.finalproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface EventRepo extends CrudRepository<EventEntity, UUID> {
    List<EventEntity> findAllByDateIsAfter(Timestamp timestamp);
    EventEntity findEventEntityByEventid(UUID eventid);
    EventEntity findEventEntityByOwner(UserEntity userEntity);
}
