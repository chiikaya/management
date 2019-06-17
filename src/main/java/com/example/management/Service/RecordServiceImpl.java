package com.example.management.Service;

import com.example.management.Repository.TaskRecordRepository;
import com.example.management.Repository.TaskUserRepository;
import com.example.management.entity.Task;
import com.example.management.entity.TaskRecord;
import com.example.management.entity.TaskUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class RecordServiceImpl implements RecordService {
    @Autowired
    private EntityManager em;
    @Autowired
    private TaskRecordRepository trr;

    @Override
    public void insert(TaskRecord taskRecord) {
        em.persist(taskRecord);
        TaskUser taskUser=taskRecord.getTaskUser();
        Task task = taskUser.getTask();
        em.merge(taskUser);
        em.merge(task);
        if(task.getDeadline().isBefore(LocalDateTime.now()))
            task.setOpen(false);
        if (!taskUser.isCompleted())
            taskUser.setCompleted(true);
        em.flush();
    }

    @Override
    public void delete(int id) {
        em.remove(em.find(TaskRecord.class, id));
    }

    @Override
    public List<TaskRecord> findbyTUid(int TUid) {
        return trr.findAllByTUid(TUid);
    }
}
