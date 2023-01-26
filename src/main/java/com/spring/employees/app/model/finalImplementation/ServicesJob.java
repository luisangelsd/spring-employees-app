package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.employees.app.model.entitys.EntityJob;
import com.spring.employees.app.model.servicesJpaRepository.IServiceJobJpaRepository;

@Service(value = "servicesJob")
public class ServicesJob implements IServicesJob {

	//-- Variables globales
	private EntityJob entityJob;
	private List<EntityJob> listEntityJob;
	
	//-- Inyección de servicios
	@Autowired
	private IServiceJobJpaRepository serviceJob;
	
	
	
	@Override
	public EntityJob saveUpdate(EntityJob entityJob) throws Exception {
		this.entityJob=this.serviceJob.save(entityJob);
		return this.entityJob;
	}

	
	@Override
	public EntityJob findById(Integer id) throws Exception {
		this.entityJob=this.serviceJob.findById(id).orElse(null);
		return this.entityJob;
	}

	
	@Override
	public List<EntityJob> findAll() throws Exception {
		this.listEntityJob=this.serviceJob.findAll();
		return this.listEntityJob;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		this.serviceJob.deleteById(id);
		
	}


	@Override
	public Double findSalaryById(Integer id) throws Exception {
		Double salario=this.serviceJob.findSalaryById(id);
		if (salario==null) {
			salario=0.0;
		}
		return salario;
	}

}
