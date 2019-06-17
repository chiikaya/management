package com.example.management.Repository;

import com.example.management.entity.UserMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserMonitorRepository
        extends JpaRepository<UserMonitor,Integer>,
        CustomizedRepository<UserMonitor,Integer>{

    @Query("select um from UserMonitor um where um.user.id=:userid")
    List<UserMonitor> find(@Param("userid")int userid);

    @Query("select um from UserMonitor um ")
    List<UserMonitor> findAll();

    @Query("select um from UserMonitor um where um.monitorMess.id=:mid")
    List<UserMonitor> find2(@Param("mid")int mid);
}
