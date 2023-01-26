package com.spring.employees.app.model.dtos;

public class DtoResponsePaymenSuccessMessage {

	private  Double payment;
	private Boolean success;
	private String message;
	
	
	//--Getters and setters
	public Double getPayment() {return payment;	}
	public void setPayment(Double payment) {this.payment = payment;	}
	public Boolean getSuccess() {	return success;	}
	public void setSuccess(Boolean success) {	this.success = success;	}
	public String getMessage() {	return message;	}
	public void setMessage(String message) {	this.message = message;	}

	
	//-- Constructores
	
	
	public DtoResponsePaymenSuccessMessage() {
		
	}
	
	public DtoResponsePaymenSuccessMessage(Double payment, Boolean success, String message) {
		super();
		this.payment = payment;
		this.success = success;
		this.message = message;
	}
	
	
	
}
