package com.finalproject.repo;

import com.finalproject.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepo extends CrudRepository<EventEntity, UUID> {

    public List<EventEntity> findAllEventEntity();
    public EventEntity findEventEntityByUUID(UUID id);
    public EventEntity findEventEntityByName(String name);
}
