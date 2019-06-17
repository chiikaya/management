package com.example.management.Service;

import com.example.management.Repository.UserRepository;
import com.example.management.entity.Authority;
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
public class managerServiceImpl implements UserService {
    @Autowired
    EntityManager em;
    @Autowired
    UserRepository ur;
    //改自己的
    //改别人的，基本信息，只对权限是USER的用户操作
    @Override
    public void update(User newUser,int userId) {
        User user = new User();
        user.setId(userId);
        user=em.merge(user);
        user.setPassword(newUser.getPassword());
        user.setPhone(newUser.getPhone());
        user.setUsername(newUser.getUsername());
        user.setBasicInformation(newUser.getBasicInformation());
        em.flush();
    }


    //前端只显示权限为USER的用户为操作对象
    @Override
    public void delete(int id) {
        User u =em.find(User.class, id);
        Authority authority1 = u.getUserAuthority();
        if (authority1==Authority.User)
            em.remove(u);
    }

    //默认给的权限是User
    @Override
    public void insert(User newUser) {
        newUser.setUserAuthority(Authority.User);
        em.persist(newUser);
    }

    @Override
    public User check(int id) {
        return ur.findById(id);
    }

    //根据名字查
    public List<User> ManagerCheck(String name){
        return ur.findAllByUsername(name);
    }

    //查所有的user，权限为USER的

    //权限管理,升权限为manager
    public boolean rightManage(int id){
        User u=em.find(User.class,id);
        u.setUserAuthority(Authority.Manager);
        em.refresh(u);
        return true;
    }
}
