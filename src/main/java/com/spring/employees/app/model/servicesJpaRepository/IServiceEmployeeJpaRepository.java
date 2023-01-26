package com.spring.employees.app.model.servicesJpaRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.employees.app.model.entitys.EntityEmployee;

public interface IServiceEmployeeJpaRepository extends JpaRepository<EntityEmployee, Integer>{
	
	
	/*-- Find by Name and LastName: Realiza una consulta personalizada a un procedure almacenado a la bd*/
	@Query(value = "SELECT * FROM EMPLOYEES WHERE UPPER(NAME)= UPPER (:name) OR UPPER(LAST_NAME)= UPPER(:lastName)", nativeQuery = true)
	public EntityEmployee findByNameAndLastName(@Param("name") String name, @Param("lastName") String lastName); 
	             
	
	/*Find by job_id: Trae todos los empleados que coincidan con el job_id*/
	@Query(value = "SELECT * FROM EMPLOYEES WHERE job_id=:jobId", nativeQuery = true)
	public List<EntityEmployee> findEmployeesByJobId(@Param("jobId") Integer jobId);
	

}
