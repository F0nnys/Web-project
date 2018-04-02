package com.roy.service;

import org.springframework.stereotype.Service;

/**
 * Created by roy on 2018/3//10.
 */
@Service
public class WendaService {
    public String getMessage(int userId) {
        return "Hello Message:" + String.valueOf(userId);
    }
}
