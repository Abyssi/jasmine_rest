package com.jasmine.jasmine_rest.Models;

import com.jasmine.jasmine_rest.utils.JSONSerializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@RedisHash("jn_cell")
public class JNCell implements JSONSerializable {
    @Id
    private String id;
    private JNBoxContainer container;
    private List<JNBaseSemaphore> semaphores;

    public JNCell(String id, JNBoxContainer container) {
        this(id, container, new ArrayList<>());
    }

    public JNCell(String id, JNBoxContainer container, List<JNBaseSemaphore> semaphores) {
        this.id = id;
        this.container = container;
        this.semaphores = semaphores;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public JNBoxContainer getContainer() {
        return container;
    }

    public void setContainer(JNBoxContainer container) {
        this.container = container;
    }

    public List<JNBaseSemaphore> getSemaphores() {
        return semaphores;
    }

    public void setSemaphores(List<JNBaseSemaphore> semaphores) {
        this.semaphores = semaphores;
    }
}
