package com.spring.employees.app.model.finalImplementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.spring.employees.app.model.entitys.EntityEmployeeWorkedHours;
import com.spring.employees.app.model.servicesJpaRepository.IServiceEmployeeWorkedHoursJpaRepository;

@Service(value = "servicesEmployeeWorkedHours")
public class ServicesEmployeeWorkedHours implements IServicesEmployeeWorkedHours{

	//-- Variables globales
	private EntityEmployeeWorkedHours entityEmployeeWorkedHours=null;
	private List<EntityEmployeeWorkedHours> listEntityEmployeeWorkedHours=null;

	
	//--Servicios
	@Autowired
	private IServiceEmployeeWorkedHoursJpaRepository serviceEmployeeWorkedHours;
	
	
	@Override
	public EntityEmployeeWorkedHours saveUpdate(EntityEmployeeWorkedHours entityEmployeeWorkedHours) throws Exception {
		this.entityEmployeeWorkedHours=this.serviceEmployeeWorkedHours.save(entityEmployeeWorkedHours);
		return this.entityEmployeeWorkedHours;
	}

	@Override
	public EntityEmployeeWorkedHours findById(Integer id) throws Exception {
		this.entityEmployeeWorkedHours=this.serviceEmployeeWorkedHours.findById(id).orElse(null);
		return this.entityEmployeeWorkedHours;
	}

	@Override
	public List<EntityEmployeeWorkedHours> findAll() throws Exception {
		this.listEntityEmployeeWorkedHours=this.serviceEmployeeWorkedHours.findAll();
		return this.listEntityEmployeeWorkedHours;
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		this.serviceEmployeeWorkedHours.deleteById(id);
		
	}

	@Override
	public EntityEmployeeWorkedHours findByIdEmployeeAndWorkedDate(Integer employeeId, LocalDate fecha)
			throws Exception {
		this.entityEmployeeWorkedHours=this.serviceEmployeeWorkedHours.findByIdEmployeeAndWorkedDate(employeeId, fecha);
		return this.entityEmployeeWorkedHours;
	}

	@Override
	public Integer workedHours(Integer EmployeeId, LocalDate starDate, LocalDate endDate) throws Exception {
		
		Integer horasTrabjadas=this.serviceEmployeeWorkedHours.workedHours(EmployeeId, starDate, endDate);
		if (horasTrabjadas==null) {
			horasTrabjadas=0;
		}
		return horasTrabjadas;
	}

}
