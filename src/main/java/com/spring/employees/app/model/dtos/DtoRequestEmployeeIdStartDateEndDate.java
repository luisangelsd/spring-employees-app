package com.spring.employees.app.model.dtos;

import java.time.LocalDate;

public class DtoRequestEmployeeIdStartDateEndDate {
	
	private Integer employeeId;
	private LocalDate startDate;
	private LocalDate endDate;
	
	
	//-- Getters and Setters

	
	public Integer getEmployeeId() {return employeeId;	}
	public void setEmployeeId(Integer employeeId) {	this.employeeId = employeeId;	}
	public LocalDate getStartDate() {	return startDate;}
	public void setStartDate(LocalDate startDate) {	this.startDate = startDate;}
	public LocalDate getEndDate() {return endDate;}public void setEndDate(LocalDate endDate) {	this.endDate = endDate;	}
	
	
	//-- Constructores
	public DtoRequestEmployeeIdStartDateEndDate() {
	}
	
	
	
	

}
