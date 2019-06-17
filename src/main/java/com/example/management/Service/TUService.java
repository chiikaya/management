package com.example.management.Service;

import com.example.management.entity.TaskUser;

import java.util.List;

public interface TUService {
    List<TaskUser> findByTid(int tid);

    List<TaskUser> findByUid(int uid);

    void insert(int tid,int uid);

    void delete(int id);
}
