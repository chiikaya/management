package com.example.management.Service;

import com.example.management.Repository.TaskUserRepository;
import com.example.management.entity.Task;
import com.example.management.entity.TaskUser;
import com.example.management.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
public class TUServiceImpl implements TUService {

    @Autowired
    private EntityManager em;
    @Autowired
    private TaskUserRepository tur;
    @Override
    public List<TaskUser> findByTid(int tid) {
        return tur.findByTid(tid);
    }

    @Override
    public List<TaskUser> findByUid(int uid) {
        return tur.findByUid(uid);
    }

    @Override
    public void insert(int tid, int uid) {
        TaskUser taskUser = new TaskUser();
        taskUser.setTask(em.find(Task.class, tid));
        taskUser.setCompleted(false);
        taskUser.setUser(em.find(User.class, uid));
        em.persist(taskUser);
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(TaskUser.class, id));
    }

    public void changeState(int id){
        TaskUser taskUser=em.find(TaskUser.class, id);
        taskUser.setCompleted(!taskUser.isCompleted());
    }
}
