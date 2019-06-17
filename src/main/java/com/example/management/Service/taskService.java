package com.example.management.Service;

import com.example.management.entity.Task;

import java.util.List;

public interface taskService {
    Task check(int id);

    List<Task> checkAll();

    void delete(int id);

    void update(Task task,int id);

    void insert(Task task);

}
