package com.example.management.Service;

import com.example.management.entity.MonitorMess;

import java.util.List;

public interface monitorService {
    //check for all these messages
    List<MonitorMess> find();
    //check by id
    MonitorMess find1(int id);

    //check by course
    List<MonitorMess> find2(String course);

    void insert(MonitorMess monitorMess);

    void update(MonitorMess monitorMess,int id);

    void delete(int id);
}
