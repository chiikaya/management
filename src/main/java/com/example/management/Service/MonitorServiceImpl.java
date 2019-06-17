package com.example.management.Service;

import com.example.management.Repository.MonitorRepository;
import com.example.management.Repository.UserMonitorRepository;
import com.example.management.entity.MonitorMess;
import com.example.management.entity.State;
import com.example.management.entity.UserMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.management.entity.address;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Transactional
@Slf4j
@Service
public class MonitorServiceImpl implements monitorService {
    @Autowired
    private EntityManager em;
    @Autowired
    private MonitorRepository mr;



    //查询所有监考记录，不对应人
    @Override
    public List<MonitorMess> find() {
        return mr.findAll();
    }

    //查询某一个监考记录
    @Override
    public MonitorMess find1(int id) {
        return em.find(MonitorMess.class, id);
    }

    //查询某一课的所有监考记录
    @Override
    public List<MonitorMess> find2(String course) {
        return mr.find(course);
    }

    //添加一个监考记录
    @Override
    public void insert(MonitorMess monitorMess) {
        monitorMess.setState(State.underdistributed);
        em.persist(monitorMess);
    }

    //修改一个监考记录，基本的东西，封住list不让动
    @Override
    public void update(MonitorMess monitorMess, int id) {
        MonitorMess mm=em.find(MonitorMess.class,id);
        mm.setAdate(monitorMess.getAdate());
        mm.setBdate(monitorMess.getBdate());
        mm.setDate(monitorMess.getDate());
        mm.setCourse(monitorMess.getCourse());
        em.flush();
    }

    public void updateState(int id){
        Date date = Calendar.getInstance().getTime();
        MonitorMess monitorMess=em.find(MonitorMess.class,id);
        List<address> addresses1 = monitorMess.getAddresses();

        List<address> addresses2 = new ArrayList<>();
        for(UserMonitor um:monitorMess.getUserMonitors())
            addresses2.add(um.getAddress());
        if (addresses2.contains(addresses1)){
            if (monitorMess.getDate().after(date))
                monitorMess.setState(State.completed);
            else
                monitorMess.setState(State.distributed);
        }
        else
            monitorMess.setState(State.underdistributed);
        em.flush();
    }

    //前端只暴露给权限为manager
    @Override
    public void delete(int id) {
        em.remove(em.find(MonitorMess.class, id));
    }
}
