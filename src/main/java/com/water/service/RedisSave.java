package com.water.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.WaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;


@Service
public class RedisSave implements Serializable {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Object getFromCache(String cacheKey) {
        System.out.println(cacheKey);
        return redisTemplate.opsForValue().get(cacheKey);
    }

}
