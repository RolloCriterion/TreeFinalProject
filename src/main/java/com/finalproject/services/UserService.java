package com.finalproject.services;

import com.finalproject.entities.CookieEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.repo.CookieRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.Gender;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class UserService {

    @Autowired SecurityService securityService;
    @Autowired UserRepo userRepo;
    @Autowired CookieRepo cookieRepo;

    public UserView signin(UserView userView, Cookie cookie){
        if(userRepo.findUserEntityByUsername(userView.getUsername())==null){
            userView.setPassword(securityService.computeHash(userView.getPassword()));
            userRepo.save(convertFromViewToEntity(userView));
            cookieRepo.save(cookieFromViewToEntity(cookie));
            return userView;
        }
        return null;
    }

    public UserView login(String username, String password, Cookie cookie){
        if(userRepo.findUserEntityByUsernameAndPassword(username, securityService.computeHash(password)) != null){
            cookieRepo.save(cookieFromViewToEntity(cookie));
            return convertFromEntityToView(userRepo.findUserEntityByUsername(username));
        }
        return null;
    }

    public CookieEntity cookieFromViewToEntity(Cookie cookie){
        CookieEntity cookieEntity = new CookieEntity();
        cookieEntity.setUsername(cookie.getValue());
        return cookieEntity;
    }

    public UserView convertFromEntityToView(UserEntity userEntity){
        return new UserView(userEntity.getUsername(),
                userEntity.getName(),
                userEntity.getSurname(),
                userEntity.getBirthDate(),
                Gender.valueOf(userEntity.getGender()),
                userEntity.getPassword());
    }

    public UserEntity convertFromViewToEntity(UserView userView){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userView.getUsername());
        userEntity.setName(userView.getName());
        userEntity.setSurname(userView.getSurname());
        userEntity.setBirthDate(userView.getBirthDate());
        userEntity.setGender(userView.getGender().name());
        userEntity.setPassword(userView.getPassword());
        return userEntity;
    }
}
