package com.jasmine.jasmine_rest.service;

import com.jasmine.jasmine_rest.Models.JNCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CellService {

    @Autowired
    private RedisTemplate<String, JNCell> redisTemplate;

    @Transactional
    public JNCell save(JNCell cell) {
        HashOperations<String, String, JNCell> ops = this.redisTemplate.opsForHash();
        ops.put("cells", cell.getId(), cell);
        return cell;
    }

    @Transactional
    public JNCell findById(String id) {
        HashOperations<String, String, JNCell> ops = this.redisTemplate.opsForHash();
        return ops.get("cells", id);
    }


}
