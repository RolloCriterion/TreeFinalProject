package com.finalproject.services;

import com.finalproject.exceptions.*;
import com.finalproject.entities.EventEntity;
import com.finalproject.entities.UserEntity;
import com.finalproject.repo.CookieRepo;
import com.finalproject.repo.EventRepo;
import com.finalproject.repo.UserRepo;
import com.finalproject.views.EventView;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EventService {

    private EventRepo eventRepo;
    private UserRepo userRepo;
    private CookieRepo cookieRepo;

    public EventService(@Autowired EventRepo eventRepo, @Autowired UserRepo userRepo, @Autowired CookieRepo cookieRepo){
        this.eventRepo = eventRepo;
        this.userRepo = userRepo;
        this.cookieRepo = cookieRepo;
    }

    public List<EventView> getActiveEvents(String username) throws UserNotLoggedException {
        if (cookieRepo.existsById(username)) {
            List<EventEntity> eventEntityList = eventRepo.findAllByDateIsAfter(Timestamp.valueOf(LocalDateTime.now()));
            UserEntity userEntity = userRepo.findUserEntityByUsername(username);
            return eventEntityList.stream()
                    .filter(e -> !e.getUserEntityList().contains(userEntity))
                    .filter(e->e.getUserEntityList().size() < e.getCapacity())
                    .map(e -> convertFromEntityToView(e, username))
                    .collect(Collectors.toList());
        }
        throw new UserNotLoggedException();
    }

    public List<EventView> getUserEvents(String username) throws UserNotLoggedException {
        if (cookieRepo.existsById(username)) {
            List<EventEntity> eventEntityList = eventRepo.findAllByDateIsAfter(Timestamp.valueOf(LocalDateTime.now()));
            UserEntity userEntity = userRepo.findUserEntityByUsername(username);
            return eventEntityList.stream().filter(e -> e.getUserEntityList().contains(userEntity)).map(e -> convertFromEntityToView(e, username)).collect(Collectors.toList());
        }
        throw new UserNotLoggedException();
    }

    public EventView joinEvent(UUID eventid, String username) throws ImpossibleToJoinEventException {
        if (cookieRepo.existsById(username)) {
            if (eventRepo.existsById(eventid)) {
                UserEntity userEntity = userRepo.findUserEntityByUsername(username);
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);
                userEntity.addEvent(eventEntity);
                eventRepo.save(eventEntity);
                return convertFromEntityToView(eventEntity, username);
            }
        }
        throw new ImpossibleToJoinEventException();
    }

    public EventView unjoinEvent(UUID eventid, String username) throws ImpossibleToUnjoinEventException {
        if (cookieRepo.existsById(username)) {
            if (eventRepo.existsById(eventid)) {
                UserEntity userEntity = userRepo.findUserEntityByUsername(username);
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);
                userEntity.removeEvent(eventEntity);
                eventRepo.save(eventEntity);
                return convertFromEntityToView(eventEntity, username);
            }
        }
        throw new ImpossibleToUnjoinEventException();
    }

    public EventView getEventDetails(UUID eventid, String username) throws ImpossibleToGetEventDetails {
        if (cookieRepo.existsById(username)) {
            if (eventRepo.existsById(eventid)) {
                return convertFromEntityToView(eventRepo.findEventEntityByEventid(eventid), username);
            }
        }
        throw new ImpossibleToGetEventDetails();
    }

    public EventView createEvent(EventView eventView, String cookieUser) throws ImpossibileToCreateEventException, ImpossibleToJoinEventException {
        if (cookieRepo.existsById(cookieUser)) {
            if (!eventRepo.existsById(eventView.getEventid())) {
                EventEntity eventEntity = convertFromViewToEntity(eventView, cookieUser);
                eventRepo.save(eventEntity);
                return joinEvent(eventView.getEventid(), cookieUser);
            }
        }
        throw new ImpossibileToCreateEventException();
    }

    public EventView cancelEvent(UUID eventid, String cookieUser) throws ImpossibleToCancelEventException {
        if (cookieRepo.existsById(cookieUser)) {
            if (eventRepo.existsById(eventid)) {
                EventEntity eventEntity = eventRepo.findEventEntityByEventid(eventid);

                for (UserEntity userEntity : eventEntity.getUserEntityList()) {
                    userEntity.getEventEntityList().remove(eventEntity);
                }
                eventEntity.getUserEntityList().clear();

                EventView eventView = convertFromEntityToView(eventEntity, cookieUser);
                eventRepo.delete(eventEntity);
                return eventView;
            }
        }
        throw new ImpossibleToCancelEventException();
    }

    public EventView convertFromEntityToView(EventEntity eventEntity, String username){

        UserEntity userEntity = userRepo.findUserEntityByUsername(username);
        Boolean owned = eventEntity.getOwner().getUsername().equals(username);
        Boolean joined = eventEntity.getUserEntityList().contains(userEntity);

        return new EventView(eventEntity.getEventid(),
                owned,
                joined,
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
