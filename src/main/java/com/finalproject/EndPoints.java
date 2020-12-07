package com.finalproject;

import com.finalproject.services.EventService;
import com.finalproject.services.UserService;
import com.finalproject.views.EventView;
import com.finalproject.views.UserView;
import org.apache.catalina.User;
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

    @Autowired UserService userService;
    @Autowired EventService eventService;

    @PostMapping("/user")
    public ResponseEntity<UserView> signUpUser(@RequestBody UserView userView, HttpServletResponse response){
        Cookie cookie = new Cookie("username", userView.getUsername());
        response.addCookie(cookie);
        return new ResponseEntity<>(userService.signin(userView, cookie), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserView> logIn(@RequestParam(name="username") String username, @RequestParam(name="password") String password, HttpServletResponse response){
        Cookie cookie = new Cookie("username", username);
        UserView userView = userService.login(username, password, cookie);
        if(userView != null){
            response.addCookie(cookie);
            return new ResponseEntity<>(userView, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/events")
    public ResponseEntity<List<EventView>> getActiveEvents(@CookieValue("username") String username){
        return new ResponseEntity<>(eventService.getActiveEvents(username), HttpStatus.OK);
    }

    @PostMapping("/join/{eventid}")
    public ResponseEntity<EventView> joinEvent(@PathVariable("eventid") UUID eventId, @CookieValue("username") String usernname){
        return new ResponseEntity<>(eventService.joinEvent(eventId, usernname), HttpStatus.CREATED);
    }

    @PostMapping("/unjoin/{eventid}")
    public String unjoinEvent(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Annulla la registrazione dell'utente ad un evento";
    }

    @PostMapping("/event")
    public String createEvent(@RequestBody EventView eventView, @CookieValue("username") String usernname){
        return "Crea un evento sulla piattaforma";
    }

    @GetMapping("/event/{eventid}")
    public String getEventDetails(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Restituisce le informazioni dettagliate di un evento.";
    }

    @DeleteMapping("/event/{eventid}")
    public String cancelEvent(@PathVariable("eventid") String eventId, @CookieValue("username") String usernname){
        return "Permette solo al creatore di un evento di annullarlo.";
    }

    @GetMapping("/user/events")
    public List<EventService> getUserEvents(@RequestBody EventView eventView, @CookieValue("username") String usernname){
        //Restituisce una lista con gli eventi creati dall'utente e quelli a cui ha partecipato. Solo gli eventi futuri, non quelli passati
        return null;
    }
}
