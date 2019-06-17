package com.example.management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CustomizedRepository<T,ID> extends JpaRepository<T,ID> {
    void refresh(T t);
}
