package com.finalproject;

import com.finalproject.exceptions.*;
import com.finalproject.services.EventService;
import com.finalproject.services.UserService;
import com.finalproject.views.EventView;
import com.finalproject.views.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@RestController
public class EndPoints {

    private UserService userService;
    private EventService eventService;

    public EndPoints(@Autowired UserService userService, @Autowired EventService eventService){
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping("/user")
    public ResponseEntity<UserView> signUpUser(@RequestBody UserView userView, HttpServletResponse response){
        Cookie cookie = new Cookie("username", userView.getUsername());
        try {
            UserView userViewNew = userService.signin(userView, cookie);
            response.addCookie(cookie);
            return new ResponseEntity<>(userViewNew, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<UserView> logIn(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletResponse response){
        Cookie cookie = new Cookie("username", username);
        try {
            UserView userView = userService.login(username, password, cookie);
            response.addCookie(cookie);
            return new ResponseEntity<>(userView, HttpStatus.OK);
        } catch (UserNotExistException | UserWrongPasswordException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventView>> getActiveEvents(@CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.getActiveEvents(username), HttpStatus.OK);
        } catch (UserNotLoggedException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/join/{eventid}")
    public ResponseEntity<EventView> joinEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.joinEvent(eventId, username), HttpStatus.CREATED);
        }catch (ImpossibleToJoinEventException e){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/unjoin/{eventid}")
    public ResponseEntity<EventView> unjoinEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.unjoinEvent(eventId, username), HttpStatus.CREATED);
        }catch (ImpossibleToUnjoinEventException e){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/event")
    public ResponseEntity<EventView> createEvent(@RequestBody EventView eventView, @CookieValue("username") String username) {
        try {
            return new ResponseEntity<>(eventService.createEvent(eventView, username), HttpStatus.CREATED);
        } catch (ImpossibileToCreateEventException | ImpossibleToJoinEventException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/event/{eventid}")
    public ResponseEntity<EventView> getEventDetails(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.getEventDetails(eventId, username), HttpStatus.OK);
        }catch (ImpossibleToGetEventDetails e){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/event/{eventid}")
    public ResponseEntity<EventView> cancelEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.cancelEvent(eventId, username), HttpStatus.OK);
        }catch (ImpossibleToCancelEventException e){
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/user/events")
    public ResponseEntity<List<EventView>> getUserEvents(@CookieValue("username") String username){
        try {
            return new ResponseEntity<>(eventService.getUserEvents(username), HttpStatus.OK);
        } catch (UserNotLoggedException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }
}
