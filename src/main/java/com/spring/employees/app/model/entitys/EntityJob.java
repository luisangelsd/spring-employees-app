package com.spring.employees.app.model.entitys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "jobs")
public class EntityJob implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ID_AUTO_INCREMENT_JOBS")
    @SequenceGenerator(name = "S_ID_AUTO_INCREMENT_JOBS", initialValue = 1, allocationSize=1   )
	private Integer id;
	
	@NotNull(message = "El nombre no puede ser null")
	@NotEmpty(message = "El nombre no puede estar vacio")
	@Column(unique = true)
	private String name;
	
	@NotNull(message = "El salario no puede ser null")
	private Double salary;
	
	
	//--Getters and setters
	private static final long serialVersionUID = 1L;


	public Integer getId() {return id;	}
	public void setId(Integer id) {this.id = id;	}
	public String getName() {return name;	}
	public void setName(String name) {	this.name = name;	}
	public Double getSalary() {	return salary;	}
	public void setSalary(Double salary) {	this.salary = salary;}
	
	//-- Methods
	public EntityJob(Integer id) {
		this.id = id;
	}
	
	public EntityJob() {
		
	}
	
	
	

	
}
