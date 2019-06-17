package com.example.management.Repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;

public class CustomizedRepositoryImp<T,ID>
        extends SimpleJpaRepository<T,ID> implements CustomizedRepository<T,ID> {
    private EntityManager em;
    public CustomizedRepositoryImp(JpaEntityInformation<T,?> entityInformation,
                                   EntityManager entityManager){
        super(entityInformation,entityManager);
        this.em=entityManager;
    }
    @Override
    public void refresh(T t){
        em.refresh(t);
    }
}
