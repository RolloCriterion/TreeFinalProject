package com.finalproject.services;

import com.finalproject.exceptions.ImpossibileToCreateEventException;
import com.finalproject.exceptions.ImpossibleToJoinEventException;
import com.finalproject.entities.EventEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.repo.CookieRepo;
import com.finalproject.repo.EventRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.EventView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    public EventView joinEvent(UUID eventid, String username) throws ImpossibleToJoinEventException {
        if(cookieRepo.existsById(username)){
            if(eventRepo.existsById(eventid)){
                UserEntity userEntity = userRepo.findUserEntityByUsername(username);
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);
                userEntity.addEvent(eventEntity);
                eventRepo.save(eventEntity);
                return convertFromEntityToView(eventEntity, username);
            }
        }
        throw new ImpossibleToJoinEventException();
    }

    public EventView unjoinEvent(UUID eventid, String username){
        if(cookieRepo.existsById(username)) {
            if (eventRepo.existsById(eventid)) {
                UserEntity userEntity = userRepo.findUserEntityByUsername(username);
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);
                userEntity.removeEvent(eventEntity);
                //eventRepo.delete(eventEntity);
                return convertFromEntityToView(eventEntity, username);
            }
        }
        return null;
    }

    public EventView createEvent(EventView eventView, String cookieUser) throws ImpossibileToCreateEventException, ImpossibleToJoinEventException {
        if(cookieRepo.existsById(cookieUser)){
            if(!eventRepo.existsById(eventView.getEventid())){
                EventEntity eventEntity = convertFromViewToEntity(eventView, cookieUser);
                eventRepo.save(eventEntity);
                try {
                    return joinEvent(eventView.getEventid(), cookieUser);
                }catch (ImpossibleToJoinEventException e){
                    throw new ImpossibleToJoinEventException();
                }
            }
        }
        throw new ImpossibileToCreateEventException();
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

    public EventEntity convertFromViewToEntity(EventView eventView, String username){
        UserEntity userEntity = userRepo.findUserEntityByUsername(username);
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventid(eventView.getEventid());
        eventEntity.setPlace(eventView.getPlace());
        eventEntity.setName(eventView.getName());
        eventEntity.setDate(eventView.getDate());
        eventEntity.setOwner(userEntity);
        eventEntity.setCapacity(eventView.getCapacity());

        return eventEntity;
    }
}
