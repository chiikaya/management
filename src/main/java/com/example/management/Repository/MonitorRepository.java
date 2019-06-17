package com.example.management.Repository;

import com.example.management.entity.MonitorMess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Repository
@Transactional
public interface MonitorRepository
        extends JpaRepository<MonitorMess,Integer>,
        CustomizedRepository<MonitorMess,Integer> {
    @Query("select m from MonitorMess m where m.course=:course")
    List<MonitorMess> find(@Param("course")String course);

    @Query("select m from MonitorMess m")
    List<MonitorMess> findAll();

    @Query("select m from MonitorMess m where m.date=:date")
    List<MonitorMess> findByDate(@Param("date")Date date);

}
