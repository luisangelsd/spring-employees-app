package com.spring.employees.app.controllers;


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
import com.spring.employees.app.model.entitys.EntityGender;
import com.spring.employees.app.model.finalImplementation.IServicesGender;

import jakarta.validation.Valid;

@CrossOrigin({"*"})
@RestController
@RequestMapping("gender/")
public class ControllerGender {
	
	//-- variables globales
	private EntityGender entityGender=null;
	//private List<EntityGender> listEntityGender=null;
	private DtoResponseIdSuccessMessage dtoResponseIdSuccessMessage=null;
	
	//-- Servicios
	@Autowired
	@Qualifier(value = "servicesGender")
	private IServicesGender servicesGender;
	
	
	//-- Guardar
	@PostMapping("save")
	public ResponseEntity<?> save(@Valid @RequestBody(required = true) EntityGender entityGender, BindingResult result) {
		
		try {
			
			//-- Validar Request
			if (result.hasErrors()) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false,"Asegurate de enviar todos los parametros");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			//-- Prepara consulta
			entityGender.setId(null);
			
			//-- Realizar consulta
			this.entityGender=this.servicesGender.saveUpdate(entityGender);
			
			//-- Regresar respuesta
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(this.entityGender.getId(), true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.OK);

		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	//-- Update
	@PutMapping("update")
	public ResponseEntity<?> update(@Valid @RequestBody(required = true) EntityGender entityGender, BindingResult result) {
		
		try {
			
			//-- Validar Request
			if (result.hasErrors() || entityGender.getId()==null || entityGender.getId()<0 ) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false,"Asegurate de enviar todos los parametros");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Verificar que exista el gender
			this.entityGender=this.servicesGender.findById(entityGender.getId());
			if (this.entityGender==null) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "No existe en Gender");
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
			//-- Realizar consulta
			this.entityGender=this.servicesGender.saveUpdate(entityGender);
			
			
			//-- Regresar respuesta
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(this.entityGender.getId(), true, null);
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.OK);

		} catch (Exception e) {
			this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
			return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

	
	
	//-- Find by id
		@GetMapping(path = "find/{id}")
		public ResponseEntity<?> findById(@PathVariable(value = "id") Integer id){
			
			try {
				
				
				//-- validar id
				if (id<0) {
					this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El id tiene que ser mayor a 0");
					return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
				//-- Realizar consulta
				this.entityGender=this.servicesGender.findById(id);
				if (this.entityGender==null) {
					this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "No existe el gender");
					return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
				//-- Enviar respuesta
				return new ResponseEntity<EntityGender>(this.entityGender,HttpStatus.OK);
				
			} catch (Exception e) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}
		
		
		//-- Delete by id
		@DeleteMapping(path = "delete/{id}")
		public ResponseEntity<?> deleteById(@PathVariable(value = "id") Integer id){
			
			try {
				
				
				//-- validar id
				if (id<0) {
					this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "El id tiene que ser mayor a 0");
					return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
				//-- Realizar consulta
				this.entityGender=this.servicesGender.findById(id);
				if (this.entityGender==null) {
					this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, "No existe el gender");
					return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
				}
				
				
				//-- Enviar respuesta
				this.servicesGender.deleteById(id);
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(id, true,null);
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.OK);
				
				
			} catch (Exception e) {
				this.dtoResponseIdSuccessMessage=new DtoResponseIdSuccessMessage(null, false, e.getMessage());
				return new ResponseEntity<DtoResponseIdSuccessMessage>(this.dtoResponseIdSuccessMessage,HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			
		}
			
	
	
	

}
