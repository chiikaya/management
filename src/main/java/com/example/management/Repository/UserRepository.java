package com.example.management.Repository;

import com.example.management.entity.Authority;
import com.example.management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional
public interface UserRepository
        extends JpaRepository<User,Integer>,
        CustomizedRepository<User,Integer> {
    //查询用户本身的信息
    User findById(int id);

    @Query("select u from User u where u.username=:username")
    List<User> findAllByUsername(@Param("username") String username);
}
