package com.example.management.Service;


import com.example.management.Repository.UserRepository;
import com.example.management.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository ur;

    @Override
    public void update(User newUser,int userId) {
        User user = new User();
        user.setId(userId);
        user=em.merge(user);
        user.setPassword(newUser.getPassword());
        user.setPhone(newUser.getPhone());
        user.setUsername(newUser.getUsername());
        user.setBasicInformation(newUser.getBasicInformation());
        em.refresh(user);
    }

    //删除和插入用不到
    @Override
    public void delete(int id) {}

    @Override
    public void insert(User newUser) {}

    //用作用户登录页面那块，根据传来的用户id直接查
    @Override
    public User check(int id) {
        return ur.findById(id);
    }

    public List<User> check2(String name){return ur.findAllByUsername(name);}

}
