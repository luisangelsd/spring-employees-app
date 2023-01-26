package com.spring.employees.app.model.entitys;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employee_worked_hours")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class EntityEmployeeWorkedHours implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ID_AUTO_INCREMEN_EMPLOYEES_WORKED_HOURS")
	@SequenceGenerator(name = "S_ID_AUTO_INCREMEN_EMPLOYEES_WORKED_HOURS", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	@NotNull(message = "El employeeId no puede ser null")
	private EntityEmployee employeeId;
	
	@NotNull(message = "Las horas trabajadas no pueden ser null")
	@Column(name = "worked_hours")
	private Integer workedHours;
	
	@NotNull(message = "La fecha no puede ser null")
	@Column(name = "worked_date")
	private LocalDate workedDate;
	
	
	//-- Getters and setters
	private static final long serialVersionUID = 1L;

	public Integer getId() {return id;	}
	public void setId(Integer id) {this.id = id;	}
	public EntityEmployee getEmployeeId() {		return employeeId;	}
	public void setEmployeeId(EntityEmployee employeeId) {	this.employeeId = employeeId;}
	public Integer getWorkedHours() {	return workedHours;	}
	public void setWorkedHours(Integer workedHours) {	this.workedHours = workedHours;	}
	public LocalDate getWorkedDate() {	return workedDate;}
	public void setWorkedDate(LocalDate workedDate) {	this.workedDate = workedDate;}


	//-- Methods
	public EntityEmployeeWorkedHours(Integer id) {
		this.id = id;
	}
	
	public EntityEmployeeWorkedHours() {
	
	}
	
	
	
	

}
