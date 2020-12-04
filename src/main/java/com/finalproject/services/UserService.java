package com.finalproject.services;

import com.finalproject.entities.UserEntity;
import com.finalproject.repo.EventRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.Gender;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired SecurityService securityService;
    @Autowired UserRepo userRepo;
    @Autowired EventRepo eventRepo;

    public String criptPassword(UserEntity user){
        return user.setPassword(securityService.computeHash(user.getPassword()));
    }

    public void convertFromEntityToView(UserEntity userEntity){
        UserView userView = new UserView(userEntity.getUsername(),
                userEntity.getName(),
                userEntity.getSurname(),
                userEntity.getBirthDate(),
                Gender.valueOf(userEntity.getGender()),
                userEntity.getPassword());
    }

    public void convertFromViewToEntity(UserView userView){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userView.getUsername());
        userEntity.setName(userView.getName());
        userEntity.setSurname(userView.getSurname());
        userEntity.setBirthDate(userView.getBirthDate());
        userEntity.setGender(userView.getGender().name());
        userEntity.setPassword(userView.getPassword());
    }
}
