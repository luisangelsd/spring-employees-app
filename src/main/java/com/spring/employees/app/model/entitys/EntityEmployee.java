package com.spring.employees.app.model.entitys;

import java.io.Serializable;
import java.time.LocalDate;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
public class EntityEmployee implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_ID_AUTO_INCREMENT_EMPLOYEES")
	@SequenceGenerator(name = "S_ID_AUTO_INCREMENT_EMPLOYEES", initialValue = 1, allocationSize = 1)
	private Integer id;
	
	
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id")
	@NotNull(message = "El genderId no puede ser null")
	private EntityGender genderId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="job_id")
	@NotNull(message = "El jobId no puede ser null")
	private EntityJob jobId;
	
	@NotNull(message = "El name no puede ser null")
	@NotEmpty(message = "El name no puede estar vacío")
	@Column(unique = true)
	private String name;
	
	@NotNull(message = "El lastName no puede ser null")
	@NotEmpty(message = "El lastName no puede estar vacío")
	@Column(unique = true)
	private String lastName;
	
	@NotNull(message = "El birthdate no puede ser null")
	private LocalDate birthdate;
	
	
	
	//-- Gettes and setters
	private static final long serialVersionUID = 1L;
	public Integer getId() {return id;	}
	public void setId(Integer id) {this.id = id;	}
	public EntityGender getGenderId() {return genderId;	}
	public void setGenderId(EntityGender genderId) {this.genderId = genderId;	}
	public EntityJob getJobId() {return jobId;	}
	public void setJobId(EntityJob jobId) {this.jobId = jobId;	}
	public String getName() {return name;	}
	public void setName(String name) {this.name = name;	}
	public String getLastName() {return lastName;	}
	public void setLastName(String lastName) {this.lastName = lastName;	}
	public LocalDate getBirthdate() {return birthdate;	}
	public void setBirthdate(LocalDate birthdate) {this.birthdate = birthdate;	}
	
	
	//-- Methods
	public EntityEmployee(Integer id) {
		this.id = id;
	}
	
	public EntityEmployee() {
	
	}
	

	
	
}
