package com.spring.employees.app.model.entitys;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "genders")
public class EntityGender implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ID_AUTO_INCREMENT_GENDERS")
	@SequenceGenerator(name = "S_ID_AUTO_INCREMENT_GENDERS", initialValue = 1 ,allocationSize = 1)
	private Integer id;
	
	@Column(unique = true)
	@NotNull(message = "El nombre no puede ser null")
	@NotNull(message = "El nombre no puede estar vacío")
	private String name;
	
	
	//-- Getters and setters
	private static final long serialVersionUID = 1L;
	public Integer getId() {return id;	}
	public void setId(Integer id) {		this.id = id;	}
	public String getName() {		return name;	}
	public void setName(String name) {		this.name = name;	}
	
	//-- Constructores
	public EntityGender(Integer id) {
		this.id = id;
	}
	public EntityGender() {
	}
	
	

}
