package com.finalproject.services;

import com.finalproject.entities.EventEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.repo.CookieRepo;
import com.finalproject.repo.EventRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.EventView;
import com.finalproject.views.Gender;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired EventRepo eventRepo;
    @Autowired CookieRepo cookieRepo;
    @Autowired UserRepo userRepo;

    public List<EventView> getActiveEvents(String username){
        if(cookieRepo.existsById(username)){
            List<EventEntity> eventEntityList = eventRepo.findAllByDateIsAfter(Timestamp.valueOf(LocalDateTime.now()));
            return eventEntityList.stream().map(e->convertFromEntityToView(e, username)).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public EventView joinEvent(UUID eventid, String username){
        if(cookieRepo.existsById(username)){
            if(eventRepo.existsById(eventid)){
                UserEntity userEntity = userRepo.findUserEntityByUsername(username);
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);
                userEntity.addEvent(eventEntity);
                eventRepo.save(eventEntity);
                return convertFromEntityToView(eventEntity, username);
            }
        }
        return null;
    }

    public EventView convertFromEntityToView(EventEntity eventEntity, String username){

        Boolean owned = eventEntity.getOwner().getUsername().equals(username);

        return new EventView(eventEntity.getEventid(),
                owned,
                eventEntity.getName(),
                eventEntity.getDate(),
                eventEntity.getPlace(),
                eventEntity.getCapacity());
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
