package com.ztp.converter.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SectionMapService {

    @Autowired
    private RedisTemplate<String, String> template;

    public void setRedisData() throws InterruptedException {
        template.opsForValue().set("erkek", "1");
        template.opsForValue().set("kadin", "2");
        template.opsForValue().set("cocuk", "3");
        template.opsForValue().set("supermarket", "4");
    }

    public String getValue(String key) {
        return template.opsForValue().get(key);
    }

}
