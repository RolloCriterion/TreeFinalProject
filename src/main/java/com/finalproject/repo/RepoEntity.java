package com.finalproject.repo;

import com.finalproject.entities.EventEntity;
import org.springframework.data.repository.CrudRepository;

public interface RepoEntity extends CrudRepository<EventEntity, String> {


}
