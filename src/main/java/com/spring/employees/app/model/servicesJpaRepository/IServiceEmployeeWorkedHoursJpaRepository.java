package com.spring.employees.app.model.servicesJpaRepository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.spring.employees.app.model.entitys.EntityEmployeeWorkedHours;

public interface IServiceEmployeeWorkedHoursJpaRepository extends JpaRepository<EntityEmployeeWorkedHours, Integer> {

	
	/*Find by employee_id , Fecha*/
	@Query(value = "SELECT * FROM employee_worked_hours WHERE EMPLOYEE_ID=:employeeId AND WORKED_DATE=:fecha", nativeQuery = true)
	public EntityEmployeeWorkedHours findByIdEmployeeAndWorkedDate (@Param("employeeId") Integer employeeId, @Param("fecha") LocalDate fecha);
	
	
	/*Worked Hours: Contar las horas trabajadas de un cliente en un rango de fechas*/
	@Query(value = "SELECT SUM(WORKED_HOURS) AS workedHours from employee_worked_hours WHERE employee_id=:employeeId AND  WORKED_DATE BETWEEN :startDate AND :endDate", nativeQuery = true)
	public Integer workedHours (@Param("employeeId") Integer employeeId, @Param("startDate") LocalDate startDate , @Param("endDate") LocalDate endDate);
	

	
}
