package com.spring.employees.app.model.servicesJpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.employees.app.model.entitys.EntityGender;

public interface IServiceGenderJpaRepository extends JpaRepository<EntityGender,Integer> {

}
