package com.spring.employees.app.model.dtos;

import java.io.Serializable;

public class DtoRequestJobId implements Serializable {
	
	private Integer jobId;

	//-- Getters and setters
	public Integer getJobId() {return jobId;	}
	public void setJobId(Integer jobId) {	this.jobId = jobId;	}
	
	
	//-- Constructores
	public DtoRequestJobId(Integer jobId) {
		this.jobId = jobId;
	}
	
	public DtoRequestJobId() {
		
	}
	
	
	
	

}
