package com.finalproject.repo;

import com.finalproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, String> {

    public UserEntity findUserEntityByUsername(String username);
    public UserEntity findUserEntityByUsernameAndPassword(String username, String password);
}
