package com.spring.employees.app.model.dtos;

public class DtoResponseHoursSuccessMessage {
	
	private Integer totalWorkedHours;
	private Boolean success;
	private String message;

	
	//-- Getters and setters
	public Integer getTotalWorkedHours() {return totalWorkedHours;	}
	public void setTotalWorkedHours(Integer totalWorkedHours) {	this.totalWorkedHours = totalWorkedHours;}
	public Boolean getSuccess() {	return success;	}
	public void setSuccess(Boolean success) {	this.success = success;	}
	public String getMessage() {	return message;	}
	public void setMessage(String message) {	this.message = message;	}
	
	//-- Constructores
	public DtoResponseHoursSuccessMessage() {
		
	}
	
	public DtoResponseHoursSuccessMessage(Integer totalWorkedHours, Boolean success, String message) {
		this.totalWorkedHours = totalWorkedHours;
		this.success = success;
		this.message = message;
	}
	
	

	


	
	
}
