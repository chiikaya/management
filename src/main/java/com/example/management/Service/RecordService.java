package com.example.management.Service;

import com.example.management.entity.TaskRecord;

import java.util.List;

public interface RecordService {
    void insert(TaskRecord taskRecord);

    void delete(int id);

    List<TaskRecord> findbyTUid(int TUid);

}
