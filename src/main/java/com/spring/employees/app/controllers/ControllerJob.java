package com.spring.employees.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.employees.app.model.dtos.DtoResponseJob;
import com.spring.employees.app.model.entitys.EntityJob;
import com.spring.employees.app.model.finalImplementation.IServicesJob;

import jakarta.validation.Valid;

@CrossOrigin({"*"})
@RestController
@RequestMapping(path = "job/")
public class ControllerJob {

	
	//-- Variables globales
	EntityJob entityJob=null;
	List<EntityJob> listEntityJob=null;
	DtoResponseJob dtoResponseJob=null;
	
	
	//-- Servicios
	@Autowired
	@Qualifier(value = "servicesJob")
	private IServicesJob servicesJob;
	
	
	
	//--Guardar
	@PostMapping(path = "save")
	public ResponseEntity<?> save(@Valid @RequestBody(required = true) EntityJob entityJob, BindingResult result){
		
		try {
			
			//-- Validar info
			if (result.hasErrors()) {
				this.dtoResponseJob=new DtoResponseJob(null, false, "Asegurate de enviar todos los campos");
				return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Preparar datos
			entityJob.setId(null);
			
			//-- Ejecutar consulta
			this.entityJob=this.servicesJob.saveUpdate(entityJob);
			this.dtoResponseJob=new DtoResponseJob(this.entityJob.getId(), true, null);
			
			//-- Regresar respuesta
			return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.OK);
			
		} catch (Exception e) {
			this.dtoResponseJob=new DtoResponseJob(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}
	
	
	//-- Editar
	@PutMapping(path = "update")
	public ResponseEntity<?> update(@Valid @RequestBody(required = true) EntityJob entityJob, BindingResult result){
		
	
		try {
			
			//-- Validar info
			if (result.hasErrors()) {
				this.dtoResponseJob=new DtoResponseJob(null, false, "Asegurate de enviar todos los campos");
				return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar si existe job a editar
			this.entityJob=this.servicesJob.findById(entityJob.getId());
			if (this.entityJob==null) {
				this.dtoResponseJob=new DtoResponseJob(null, false, "El Job no existe");
				return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Editar
			this.entityJob=this.servicesJob.saveUpdate(entityJob);
			
			//-- Regresar respuesta
			this.dtoResponseJob=new DtoResponseJob(this.entityJob.getId(), true, null);
			return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.dtoResponseJob=new DtoResponseJob(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseJob>(this.dtoResponseJob, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
	}
	
	
	
	
	
	
}
