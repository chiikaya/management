package com.example.management.Repository;

import com.example.management.entity.TaskUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface TaskUserRepository
        extends JpaRepository<TaskUser,Integer>,
        CustomizedRepository<TaskUser,Integer> {

    @Query("select tu from TaskUser tu where tu.user.id=:uid")
    List<TaskUser> findByUid(@Param("uid")int uid);

    @Query("select tu from TaskUser tu where tu.task.id=:tid")
    List<TaskUser> findByTid(@Param("tid")int tid);



}
