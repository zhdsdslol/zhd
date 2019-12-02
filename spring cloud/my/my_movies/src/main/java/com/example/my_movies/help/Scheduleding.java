package com.example.my_movies.help;

import com.example.my_movies.dao.dianzanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Scheduleding {
    @Autowired
    private dianzanDao dianzanDao;
    @Autowired
    private  RedisUtils redisUtils;

}
