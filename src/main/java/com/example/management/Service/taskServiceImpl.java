package com.example.management.Service;

import com.example.management.Repository.TaskRepository;
import com.example.management.entity.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Service
public class taskServiceImpl implements taskService {
    @Autowired
    private TaskRepository tr;
    @Autowired
    private EntityManager em;

    @Override
    public Task check(int id) {
        return tr.findById(id);
    }

    @Override
    public List<Task> checkAll() {
        return tr.findAll();
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(Task.class, id));
    }

    //只是改一些基本的东西
    @Override
    public void update(Task task, int id) {
        Task task1 = em.find(Task.class, id);
        task1.setDeadline(task.getDeadline());
        task1.setDescription(task.getDescription());
        task1.setTaskName(task.getTaskName());
        em.flush();
    }

    @Override
    public void insert(Task task) {
        task.setOpen(true);
        em.persist(task);
    }
    //更改任务状态
    public void close(int id){
        Task task = em.find(Task.class, id);
        task.setOpen(false);
        em.flush();
    }
}
