package com.spring.employees.app.model.servicesJpaRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.employees.app.model.entitys.EntityJob;

public interface IServiceJobJpaRepository extends JpaRepository<EntityJob, Integer> {

	
	/*Obtener salario por id*/
	@Query(value = "SELECT SALARY FROM JOBS WHERE ID= :jobId", nativeQuery = true)
	public Double findSalaryById (@Param("jobId") Integer jobId);
	
}
