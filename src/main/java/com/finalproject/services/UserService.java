package com.finalproject.services;

import com.finalproject.entities.CookieEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.exceptions.UserAlreadyExistException;
import com.finalproject.exceptions.UserNotExistException;
import com.finalproject.exceptions.UserWrongPasswordException;
import com.finalproject.repo.CookieRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.Gender;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class UserService {

    private SecurityService securityService;
    private CookieService cookieService;
    private UserRepo userRepo;
    private CookieRepo cookieRepo;

    public UserService(@Autowired SecurityService securityService, @Autowired CookieService cookieService, @Autowired UserRepo userRepo, @Autowired CookieRepo cookieRepo){
        this.securityService = securityService;
        this.cookieService = cookieService;
        this.userRepo = userRepo;
        this.cookieRepo = cookieRepo;
    }

    public UserView signin(UserView userView, Cookie cookie) throws UserAlreadyExistException {
        if(userRepo.findUserEntityByUsername(userView.getUsername())==null){
            userView.setPassword(securityService.computeHash(userView.getPassword()));
            userRepo.save(convertFromViewToEntity(userView));
            cookieRepo.save(cookieService.cookieFromViewToEntity(cookie));
            return userView;
        }
        throw new UserAlreadyExistException();
    }

    public UserView login(String username, String password, Cookie cookie) throws UserNotExistException, UserWrongPasswordException{
        if(userRepo.findUserEntityByUsername(username) != null){
            if(userRepo.findUserEntityByUsernameAndPassword(username, securityService.computeHash(password)) != null){
                cookieRepo.save(cookieService.cookieFromViewToEntity(cookie));
                return convertFromEntityToView(userRepo.findUserEntityByUsername(username));
            }
            throw new UserWrongPasswordException();
        }
        throw new UserNotExistException();
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
