package com.spring.employees.app.model.servicesLogical;

import java.time.LocalDate;
import java.time.Period;

public class ServicesPersonalizedDate {
	
    public Boolean esMayorDe18(LocalDate fechaInicio) throws Exception {

        Period periodo = Period.between(fechaInicio, LocalDate.now());
        LocalDate fechaActual= LocalDate.now();

   
        if (periodo.getYears() > 18) {
            return true;
        }

        if (periodo.getYears() < 18) {
            return false;
        }


        if (periodo.getYears()==18 || fechaInicio.getMonthValue() < fechaActual.getMonthValue()){
            return true;
        }

        if (periodo.getYears()==18 || fechaInicio.getMonthValue() == fechaActual.getMonthValue()){
           if (fechaInicio.getDayOfMonth()<= fechaActual.getDayOfMonth()){
               return true;
           }else{
               return false;
           }
        }

        return null;


    }


}
