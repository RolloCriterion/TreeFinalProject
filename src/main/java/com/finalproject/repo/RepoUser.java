package com.finalproject.repo;

import com.finalproject.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface RepoUser extends CrudRepository<UserEntity, String> {

    public UserEntity findUserByUsername(String username);

}
