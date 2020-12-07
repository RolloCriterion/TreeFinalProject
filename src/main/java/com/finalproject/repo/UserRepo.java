package com.finalproject.repo;

import com.finalproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, String> {

    UserEntity findUserEntityByUsername(String username);
    UserEntity findUserEntityByUsernameAndPassword(String username, String password);
}
