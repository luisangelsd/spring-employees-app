package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.app.model.entitys.EntityEmployee;
import com.spring.employees.app.model.servicesJpaRepository.IServiceEmployeeJpaRepository;


@Service(value = "serviceEmployee")
public class ServicesEmployee implements IServicesEmployee {
	
	//-- Variables globales
	private EntityEmployee entityEmployee=null;
	private List<EntityEmployee> listEntityEmployee=null;
	
	
	//-- Servicios
	@Autowired
	private IServiceEmployeeJpaRepository serviceEmployee;
	
	
	

	@Override
	public EntityEmployee saveUpdate(EntityEmployee entityEmployee) throws Exception {
		this.entityEmployee=this.serviceEmployee.save(entityEmployee);
		return this.entityEmployee;
	}

	@Override
	public EntityEmployee findById(Integer id) throws Exception {
		this.entityEmployee=this.serviceEmployee.findById(id).orElse(null);
		return this.entityEmployee;
	}

	@Override
	public List<EntityEmployee> listAll() throws Exception {
		this.listEntityEmployee=this.serviceEmployee.findAll();
		return this.listEntityEmployee;
	}

	
	@Override
	public void deleteById(Integer id) throws Exception {
		this.serviceEmployee.deleteById(id);
		
	}

	
	@Override
	public EntityEmployee findByNameAndLastName(String name, String lastName) throws Exception {
		this.entityEmployee=this.serviceEmployee.findByNameAndLastName(name, lastName);
		return this.entityEmployee;
	}

	
	@Override
	public List<EntityEmployee> findEmployeesByJobId(Integer idJob) throws Exception {
		this.listEntityEmployee=this.serviceEmployee.findEmployeesByJobId(idJob);
		return this.listEntityEmployee;
	}
	

}
