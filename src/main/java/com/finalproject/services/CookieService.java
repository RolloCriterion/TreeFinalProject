package com.finalproject.services;

import com.finalproject.entities.CookieEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

@Service
public class CookieService {
    public CookieEntity cookieFromViewToEntity(Cookie cookie){
        CookieEntity cookieEntity = new CookieEntity();
        cookieEntity.setUsername(cookie.getValue());
        return cookieEntity;
    }
}
