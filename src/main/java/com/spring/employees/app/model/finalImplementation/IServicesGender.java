package com.spring.employees.app.model.finalImplementation;

import java.util.List;

import com.spring.employees.app.model.entitys.EntityGender;

public interface IServicesGender {

	public EntityGender saveUpdate (EntityGender entityGender)  throws Exception;
	public EntityGender findById(Integer id)throws Exception;
	public List<EntityGender> findAll () throws Exception;
	public void deleteById(Integer id) throws Exception;;
}
