package com.example.management.Service;


import com.example.management.entity.User;

public interface UserService {

    void update(User newUser,int userId);

    void delete(int id);

    void insert(User newUser);

    User check(int id);
}
