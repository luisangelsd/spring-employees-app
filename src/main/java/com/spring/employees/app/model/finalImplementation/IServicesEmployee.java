package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import com.spring.employees.app.model.entitys.EntityEmployee;


public interface IServicesEmployee {
	
	/*Metodos CRUD*/
	public EntityEmployee saveUpdate(EntityEmployee entityEmployee) throws Exception;
	public EntityEmployee findById (Integer id) throws Exception;
	public List<EntityEmployee> listAll() throws Exception;
	public void deleteById(Integer id) throws Exception;
	
	
	/*Metodos personalizados*/
	public EntityEmployee findByNameAndLastName(String name, String lastName) throws Exception;
	public List<EntityEmployee> findEmployeesByJobId(Integer idJob)throws Exception;
	
}
