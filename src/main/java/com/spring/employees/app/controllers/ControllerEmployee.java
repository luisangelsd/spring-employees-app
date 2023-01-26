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
import com.spring.employees.app.model.entitys.EntityEmployee;
import com.spring.employees.app.model.entitys.EntityGender;
import com.spring.employees.app.model.entitys.EntityJob;
import com.spring.employees.app.model.finalImplementation.IServicesEmployee;
import com.spring.employees.app.model.finalImplementation.IServicesGender;
import com.spring.employees.app.model.finalImplementation.IServicesJob;
import com.spring.employees.app.model.servicesLogical.ServicesPersonalizedDate;

import jakarta.validation.Valid;

@CrossOrigin({"*"})
@RestController
@RequestMapping("employee/")
public class ControllerEmployee {

	//-- Variables globales
	private EntityEmployee entityEmployee=null;
	private List<EntityEmployee> listEntityEmployee=null;
	private DtoResponseEmployee dtoResponseEmployee=null;
	
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
	
	private ServicesPersonalizedDate servicesDate=new ServicesPersonalizedDate();
	
	
	
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
			
			//-- validar que no exista
			this.entityEmployee=this.servicesEmployee.findByNameAndLastName(entityEmployee.getLastName(), entityEmployee.getLastName());
			if (this.entityEmployee!=null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El employee ya esta registrado");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar job
			this.entityJob=this.servicesJob.findById(entityEmployee.getJobId().getId());
			if (this.entityJob==null) {
				this.dtoResponseEmployee=new DtoResponseEmployee(null,false, "El jobId no existe");
				return new ResponseEntity<DtoResponseEmployee>(this.dtoResponseEmployee, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- validar gender
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
	
	
	
	
}
