package com.example.management.Service;


import com.example.management.entity.UserMonitor;

import java.util.List;

public interface UMService {
    void delete(int id);

    void update(UserMonitor userMonitor,int id);

    List<UserMonitor> check1(int userid);

    List<UserMonitor> check2(int mid);

    List<UserMonitor> check3();

    void insert(UserMonitor userMonitor);
}
