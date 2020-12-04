package com.finalproject.repo;

import com.finalproject.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EventRepo extends CrudRepository<EventEntity, UUID> {


}
