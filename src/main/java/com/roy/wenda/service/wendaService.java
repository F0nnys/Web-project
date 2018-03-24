package com.roy.wenda.service;


import org.springframework.stereotype.Service;

@Service
public class wendaService {
    public String getMessage(int userId){
        return " UserId:" + String.valueOf(userId);
    }
}
