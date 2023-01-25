package com.spring.employees.app.model.servicesJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employees.app.model.entitys.EntityJob;

public interface IServiceJobJpaRepository extends JpaRepository<EntityJob, Integer> {

}
