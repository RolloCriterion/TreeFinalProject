package com.finalproject.repo;

import com.finalproject.entities.CookieEntity;
import com.finalproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface CookieRepo extends CrudRepository<CookieEntity, String> {
    CookieEntity findCookieEntityByUsername(String username);
}