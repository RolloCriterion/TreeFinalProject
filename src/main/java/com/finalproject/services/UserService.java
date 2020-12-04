package com.finalproject.services;

import com.finalproject.entities.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired SecurityService securityService;

    public String criptPassword(UserEntity user){
        return user.setPassword(securityService.computeHash(user.getPassword()));
    }
}
