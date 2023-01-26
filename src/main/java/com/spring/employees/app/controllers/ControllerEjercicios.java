package com.spring.employees.app.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.employees.app.model.dtos.DtoResponseEmployee;
import com.spring.employees.app.model.dtos.DtoResponseIdSuccessMessage;
import com.spring.employees.app.model.entitys.EntityEmployee;
import com.spring.employees.app.model.entitys.EntityEmployeeWorkedHours;
import com.spring.employees.app.model.entitys.EntityGender;
import com.spring.employees.app.model.entitys.EntityJob;
import com.spring.employees.app.model.finalImplementation.IServicesEmployee;
import com.spring.employees.app.model.finalImplementation.IServicesEmployeeWorkedHours;
import com.spring.employees.app.model.finalImplementation.IServicesGender;
import com.spring.employees.app.model.finalImplementation.IServicesJob;
import com.spring.employees.app.model.servicesLogical.ServicesPersonalizedDate;

import jakarta.validation.Valid;

@CrossOrigin({"*"})
@RestController
@RequestMapping("/")
public class ControllerEjercicios {

	//-- Variables globales
	private EntityEmployee entityEmployee=null;
	private List<EntityEmployee> listEntityEmployee=null;
	private EntityEmployeeWorkedHours entityEmployeeWorkedHours=null;
	
	private DtoResponseEmployee dtoResponseEmployee=null;
	private DtoResponseIdSuccessMessage dtoResponseIdSuccesMessage;
	
	private EntityJob entityJob=null;
	private EntityGender entityGender=null;
	
	
	
	//-- Servicios
	@Autowired
	@Qualifier(value = "serviceEmployee")
	private IServicesEmployee servicesEmployee;
	
	@Autowired
	@Qualifier(value = "servicesJob")
	private IServicesJob servicesJob;
	
	@Autowired
	@Qualifier(value = "servicesGender")
	private IServicesGender servicesGender;
	
	@Autowired
	@Qualifier(value = "servicesEmployeeWorkedHours")
	private IServicesEmployeeWorkedHours servicesEmployeeWorkedHours;
	
	
	private ServicesPersonalizedDate servicesDate=new ServicesPersonalizedDate();
	
	
	
	//-- Save employee
	@PostMapping(path = "save")
	public ResponseEntity<?> save(@Valid @RequestBody(required = true) EntityEmployee entityEmployee, BindingResult result ){
		
		try {
			
			//-- validar el request
			if (result.hasErrors()) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "Asegurate de enviar todos los datos requeridos");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//---- Validar que la fecha no sea mayor a la actual
			if (entityEmployee.getBirthdate().isAfter(LocalDate.now())) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "La fecha Birthdate no puede ser mayor a la fecha actual");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar si es mayor de edad
			if (!this.servicesDate.esMayorDe18(entityEmployee.getBirthdate())) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El employee no puede ser menor de 18 años");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar que el employee no exista
			this.entityEmployee=this.servicesEmployee.findByNameAndLastName(entityEmployee.getLastName(), entityEmployee.getLastName());
			if (this.entityEmployee!=null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El employee ya esta registrado");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar que exista el job
			this.entityJob=this.servicesJob.findById(entityEmployee.getJobId().getId());
			if (this.entityJob==null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El jobId no existe");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- validar que exista el  gender
			this.entityGender=this.servicesGender.findById(entityEmployee.getGenderId().getId());
			if (this.entityGender==null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El genderId no existe");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Guardar
			entityEmployee.setId(null);
			this.entityEmployee=this.servicesEmployee.saveUpdate(entityEmployee);
			
			
			//-- Enviar respuesta
			this.dtoResponseEmployee=new DtoResponseEmployee(this.entityEmployee.getId(), true, null);
			return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.dtoResponseEmployee=new DtoResponseEmployee(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//-- Add worked hours
	@PostMapping("add/hours")
	public ResponseEntity<?> addHoursWorked(@Valid @RequestBody(required = true) EntityEmployeeWorkedHours entityEmployeWorkedHours, BindingResult result ){
		
		try {
			
			//-- Validar request
			if (result.hasErrors()) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null, false, "Verifica que todos los campos esten completos y no agregar valores negativos");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			
			//--Validad que sean menos de 20 horas trabajadas
			if (entityEmployeWorkedHours.getWorkedHours() > 20) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null, false, "No puedes registrar más de 20 horas");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Validar que no se puedan registrar horas en fechas futuras
			if (entityEmployeWorkedHours.getWorkedDate().isAfter(LocalDate.now())) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null, false, "No puedes registrar horas para fechas futuras");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Validar que el empleado exista
			this.entityEmployee=this.servicesEmployee.findById(entityEmployeWorkedHours.getEmployeeId().getId());
			if (this.entityEmployee==null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null, false, "El employee no existe");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- No se pueden tener más de 2 registros 
			this.entityEmployeeWorkedHours=this.servicesEmployeeWorkedHours.findByIdEmployeeAndWorkedDate(entityEmployeWorkedHours.getEmployeeId().getId(), entityEmployeWorkedHours.getWorkedDate());
			if (this.entityEmployeeWorkedHours!=null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null, false, "Ya registraste horas para esta fecha");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Guardar
			this.entityEmployeeWorkedHours=this.servicesEmployeeWorkedHours.saveUpdate(entityEmployeWorkedHours);
			
			//-- Devolver respuesta
			this.dtoResponseIdSuccesMessage=new DtoResponseIdSuccessMessage(this.entityEmployee.getId(), true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccesMessage, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.dtoResponseEmployee=new DtoResponseEmployee(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
}
