package com.example.management.Repository;

import com.example.management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskRepository
        extends JpaRepository<Task,Integer>,
        CustomizedRepository<Task,Integer> {
    @Query("SELECT t FROM Task t where t.taskName=:taskname")
    List<Task> find(@Param("taskname")String taskname);

    Task findById(int id);

    @Query("select t from Task t")
    List<Task> findAll();


}
