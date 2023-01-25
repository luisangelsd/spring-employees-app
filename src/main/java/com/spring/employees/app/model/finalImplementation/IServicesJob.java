package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import com.spring.employees.app.model.entitys.EntityJob;


public interface IServicesJob {
	
	public EntityJob saveUpdate (EntityJob entityJob)  throws Exception;
	public EntityJob findById(Integer id)throws Exception;
	public List<EntityJob> findAll () throws Exception;
	public void deleteById(Integer id) throws Exception;

}
