package com.spring.employees.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.employees.app.model.dtos.DtoResponseIdSuccessMessage;
import com.spring.employees.app.model.entitys.EntityJob;
import com.spring.employees.app.model.finalImplementation.IServicesJob;

import jakarta.validation.Valid;


@CrossOrigin({"*"})
@RestController
@RequestMapping(path = "job/")
public class ControllerJob {

	
	//-- Variables globales
	private EntityJob entityJob=null;
	//private List<EntityJob> listEntityJob=null;
	private DtoResponseIdSuccessMessage dtoResponseIdSuccessMessage=null;
	
	
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
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "Asegurate de enviar todos los campos");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Preparar datos
			entityJob.setId(null);
			
			//-- Ejecutar consulta
			this.entityJob=this.servicesJob.saveUpdate(entityJob);
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(this.entityJob.getId(), true, null);
			
			//-- Regresar respuesta
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.OK);
			
		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}
	
	
	//-- Editar
	@PutMapping(path = "update")
	public ResponseEntity<?> update(@Valid @RequestBody(required = true) EntityJob entityJob, BindingResult result){
		
	
		try {
			
			//-- Validar info
			if (result.hasErrors()) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "Asegurate de enviar todos los campos");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- validar si existe job a editar
			this.entityJob=this.servicesJob.findById(entityJob.getId());
			if (this.entityJob==null) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El Job no existe");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Editar
			this.entityJob=this.servicesJob.saveUpdate(entityJob);
			
			//-- Regresar respuesta
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(this.entityJob.getId(), true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.OK);
			
			
		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	
	}
	
	
	//-- Eliminar por id
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id){
		
		try {
			
			//-- validar el id
			if (id<0) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El id no puede ser nulo o ser menor a 0");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Validar que exista el job
			this.entityJob=this.servicesJob.findById(id);
			if (this.entityJob==null) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El Job no existe");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Eliminar
			this.servicesJob.deleteById(id);
			
			//--Regresar respuesta
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(id, true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.OK);
			
	
		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	//-- Buscar por id
	@GetMapping(path = "find/{id}")
	public ResponseEntity<?> findById(@PathVariable(name = "id") Integer id){
		
		try {
			
			//-- validar el id
			if (id<0) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El id no puede ser nulo o ser menor a 0");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Realizar consulta y validar
			this.entityJob=this.servicesJob.findById(id);
			
			if (this.entityJob==null) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El id no existe");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//--Regresar respuesta
			return new ResponseEntity<EntityJob>(this.entityJob, HttpStatus.INTERNAL_SERVER_ERROR);


		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		
		
	}
	
	
}
