package com.silvia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.silvia.model.Habitacion;

public interface HabitacionDAO extends JpaRepository<Habitacion, Integer> {
	
	//@Query("select t from Habitacion t inner join Estado e on t.estado.id=e.id where e.id=?1")
	//public List<Habitacion> obtHabitacionPorEstado(@Param("estado") int estado);
	
}
