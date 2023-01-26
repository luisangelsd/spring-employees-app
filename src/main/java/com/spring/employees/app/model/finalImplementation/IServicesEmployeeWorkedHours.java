package com.spring.employees.app.model.finalImplementation;

import java.time.LocalDate;
import java.util.List;

import com.spring.employees.app.model.entitys.EntityEmployeeWorkedHours;


public interface IServicesEmployeeWorkedHours {
	
	public EntityEmployeeWorkedHours saveUpdate (EntityEmployeeWorkedHours entityEmployeeWorkedHours)  throws Exception;
	public EntityEmployeeWorkedHours findById(Integer id)throws Exception;
	public List<EntityEmployeeWorkedHours> findAll () throws Exception;
	public void deleteById(Integer id) throws Exception;
	
	public EntityEmployeeWorkedHours findByIdEmployeeAndWorkedDate(Integer employeeId, LocalDate fecha) throws Exception;
	public Integer workedHours(Integer EmployeeId, LocalDate starDate, LocalDate endDate)throws Exception;

}
