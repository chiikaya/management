package com.example.management.Service;

import com.example.management.Repository.UserMonitorRepository;
import com.example.management.entity.UserMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Slf4j
//如果要更改分配的话，采用删除记录然后重建的方法
public class UMServiceImpl implements UMService {
    @Autowired
    private EntityManager em;
    @Autowired
    private UserMonitorRepository  umr;


    @Override
    public void delete(int id) {
        em.remove(em.find(UserMonitor.class, id));
    }

    //只有address一条属性可以改
    @Override
    public void update(UserMonitor userMonitor, int id) {
        UserMonitor userMonitor1=em.find(UserMonitor.class, id);
        userMonitor1.setAddress(userMonitor.getAddress());
        em.flush();
    }

    @Override
    public List<UserMonitor> check1(int userid) {
        return umr.find(userid);
    }

    @Override
    public List<UserMonitor> check2(int mid) {
        return umr.find2(mid);
    }

    @Override
    public List<UserMonitor> check3() {
        return umr.findAll();
    }

    @Override
    public void insert(UserMonitor userMonitor) {
        em.persist(userMonitor);
    }
}
