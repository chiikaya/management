package com.example.management.Repository;

import com.example.management.entity.TaskRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TaskRecordRepository
        extends JpaRepository<TaskRecord,Integer>,
        CustomizedRepository<TaskRecord,Integer> {
    @Query("select tr from TaskRecord tr where tr.taskUser.id=:id")
    List<TaskRecord> findAllByTUid(@Param("id") int id);
}
