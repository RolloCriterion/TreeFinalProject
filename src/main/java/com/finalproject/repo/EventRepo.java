package com.finalproject.repo;

import com.finalproject.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<EventEntity, String> {


}
