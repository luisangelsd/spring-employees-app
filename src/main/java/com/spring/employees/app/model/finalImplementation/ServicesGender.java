package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.app.model.entitys.EntityGender;
import com.spring.employees.app.model.servicesJpaRepository.IServiceGenderJpaRepository;


@Service(value = "servicesGender")
public class ServicesGender implements IServicesGender {

	
	//-- variables globales
	private EntityGender entityGender=null;
	private List<EntityGender> listEntityGender=null;
	
	//-- Servicios
	@Autowired
	private IServiceGenderJpaRepository serviceGender;
	
	
	
	@Override
	public EntityGender saveUpdate(EntityGender entityGender) throws Exception {
		this.entityGender=this.serviceGender.save(entityGender);
		return this.entityGender;
	}
	
	
	
	@Override
	public EntityGender findById(Integer id) throws Exception {
		this.entityGender=this.serviceGender.findById(id).orElse(null);
		return this.entityGender;
	}
	
	
	@Override
	public List<EntityGender> findAll() throws Exception {
		this.listEntityGender=this.serviceGender.findAll();
		return this.listEntityGender;
	}
	
	
	@Override
	public void deleteById(Integer id) throws Exception {
		this.serviceGender.deleteById(id);
		
	}
	
	

	
	

}
