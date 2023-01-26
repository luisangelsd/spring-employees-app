package com.spring.employees.app.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.spring.employees.app.model.dtos.DtoRequestEmployeeIdStartDateEndDate;
import com.spring.employees.app.model.dtos.DtoRequestJobId;
import com.spring.employees.app.model.dtos.DtoResponseEmployee;
import com.spring.employees.app.model.dtos.DtoResponseHoursSuccessMessage;
import com.spring.employees.app.model.dtos.DtoResponseIdSuccessMessage;
import com.spring.employees.app.model.dtos.DtoResponsePaymenSuccessMessage;
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
	private List<EntityEmployee> listEntityEmployee;
	private EntityEmployeeWorkedHours entityEmployeeWorkedHours=null;
	
	private DtoResponseEmployee dtoResponseEmployee=null;
	private DtoResponseIdSuccessMessage dtoResponseIdSuccessMessage;
	private DtoRequestJobId dtoRequestJobId=null;
	private DtoRequestEmployeeIdStartDateEndDate dtoRequestEmployeeIdStartDateEndDate=null;
	private DtoResponseHoursSuccessMessage  dtoResponseHoursSuccessMessage=null;
	private DtoResponsePaymenSuccessMessage dtoResponsePaymenSuccessMessage=null;
	
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
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(this.entityEmployee.getId(), true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.dtoResponseEmployee=new DtoResponseEmployee(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	//-- Find Employees By JobId
	@GetMapping("employees/job")
	public ResponseEntity<?> hoursWorked(@RequestBody(required = true) DtoRequestJobId dtoRequestJobId) {
		
		try {
			
			//-- Validar request
			if (dtoRequestJobId.getJobId()==null || dtoRequestJobId.getJobId()<0) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false,"El jobId es requerido y debe ser mayor a 0");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- validar que el puesto exista
			this.entityJob=this.servicesJob.findById(dtoRequestJobId.getJobId());
			if (this.entityJob==null) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false,"Lo sentimos, el jobId no existe");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Realizar consulta
			this.listEntityEmployee=this.servicesEmployee.findEmployeesByJobId(dtoRequestJobId.getJobId());			
			if (this.listEntityEmployee.isEmpty()) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false,"No se han encontrado registros");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			return new ResponseEntity< List<EntityEmployee> >(this.listEntityEmployee, HttpStatus.OK);
			
			
			
		} catch (Exception e) {
			this.dtoResponseEmployee=new DtoResponseEmployee(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	//-- Calculate hours worked by an employee in a range of dates
	@GetMapping(path = "worked/hours")
	public ResponseEntity<?> calculateWorkedHours(@RequestBody(required = true) DtoRequestEmployeeIdStartDateEndDate dtoRequest){
		
		try {
			
			//-- Validar el request
			if (dtoRequest==null
				|| dtoRequest.getEmployeeId()==null 
				|| dtoRequest.getEmployeeId()<0  
				|| dtoRequest.getStartDate()==null
				|| dtoRequest.getEndDate()==null) {
				this.dtoResponseHoursSuccessMessage=new DtoResponseHoursSuccessMessage(null, false, "Asegurate de enviar todos los campos y que el employeeId sea mayor a 0");
				return new ResponseEntity<DtoResponseHoursSuccessMessage>(this.dtoResponseHoursSuccessMessage, HttpStatus.BAD_REQUEST);
			}
			
			//-- Validar que la fecha de inicio no sea mayor a la fin
			if (dtoRequest.getStartDate().isAfter(dtoRequest.getEndDate())) {
				this.dtoResponseHoursSuccessMessage=new DtoResponseHoursSuccessMessage(null, false, "La fecha de inicio no puede ser mayor a la fecha de fin");
				return new ResponseEntity<DtoResponseHoursSuccessMessage>(this.dtoResponseHoursSuccessMessage, HttpStatus.BAD_REQUEST);
			}
			
			//-- Validar que el empleado exista
			this.entityEmployee=this.servicesEmployee.findById(dtoRequest.getEmployeeId());
			if (this.entityEmployee==null) {
				this.dtoResponseHoursSuccessMessage=new DtoResponseHoursSuccessMessage(null, false, "El empleado no existe");
				return new ResponseEntity<DtoResponseHoursSuccessMessage>(this.dtoResponseHoursSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Consultar horas trabajadas		
			Integer horasTrabajadas=this.servicesEmployeeWorkedHours.workedHours(dtoRequest.getEmployeeId(),dtoRequest.getStartDate() ,dtoRequest.getEndDate());			
			
			
			
			//-- Regresar respuesta
			this.dtoResponseHoursSuccessMessage=new DtoResponseHoursSuccessMessage(horasTrabajadas, true, null);
			return new ResponseEntity<DtoResponseHoursSuccessMessage>(this.dtoResponseHoursSuccessMessage, HttpStatus.OK);
			

		} catch (Exception e) {
			this.dtoResponseHoursSuccessMessage=new DtoResponseHoursSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseHoursSuccessMessage>(this.dtoResponseHoursSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//--calculates how much an employee is paid in a range of dates
	@GetMapping(path = "worked/payment")
	public ResponseEntity<?> workedPayment(@RequestBody(required = true) DtoRequestEmployeeIdStartDateEndDate dtoRequest){
		
		try {
			
			//-- Validar request
			if (dtoRequest.getEmployeeId()==null 
				|| dtoRequest.getEmployeeId()<0
				|| dtoRequest.getEndDate()==null
				|| dtoRequest.getStartDate()==null) 
				{
					this.dtoResponsePaymenSuccessMessage=new DtoResponsePaymenSuccessMessage(null, false, "Asegurate de añadir todos los campos y que employeeId sea mayor a 0");
					return new ResponseEntity<DtoResponsePaymenSuccessMessage>(this.dtoResponsePaymenSuccessMessage, HttpStatus.BAD_REQUEST);
				}
			
			//-- Validar que la fecha inicio no sea mayor a la fecha fin
				if (dtoRequest.getStartDate().isAfter(dtoRequest.getEndDate())) {
					this.dtoResponsePaymenSuccessMessage=new DtoResponsePaymenSuccessMessage(null, false, "La fecha de inicio no puede ser mayor a la fecha de fin");
					return new ResponseEntity<DtoResponsePaymenSuccessMessage>(this.dtoResponsePaymenSuccessMessage, HttpStatus.BAD_REQUEST);
				}
			
			//-- Validar que el employee exista
			this.entityEmployee=this.servicesEmployee.findById(dtoRequest.getEmployeeId());
			if (this.entityEmployee==null) {
				this.dtoResponsePaymenSuccessMessage=new DtoResponsePaymenSuccessMessage(null, false, "El employee no existe");
				return new ResponseEntity<DtoResponsePaymenSuccessMessage>(this.dtoResponsePaymenSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Obtener las horas trabajadas
			Integer horasTrabajadas=this.servicesEmployeeWorkedHours.workedHours(dtoRequest.getEmployeeId(), dtoRequest.getStartDate(), dtoRequest.getEndDate());
			
			
			//-- Obtener el salario del empleado
			Double salario=this.servicesJob.findSalaryById(this.entityEmployee.getJobId().getId());
			
			
			//-- Regresar respuesta
			this.dtoResponsePaymenSuccessMessage=new DtoResponsePaymenSuccessMessage((horasTrabajadas*salario), true, null);
			return new ResponseEntity<DtoResponsePaymenSuccessMessage>(this.dtoResponsePaymenSuccessMessage, HttpStatus.OK);
			
		} catch (Exception e) {
			this.dtoResponsePaymenSuccessMessage=new DtoResponsePaymenSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponsePaymenSuccessMessage>(this.dtoResponsePaymenSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}  
	}
	
	
	
	
}
