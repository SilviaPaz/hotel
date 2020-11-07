package com.silvia.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.silvia.model.ServicioPorRol;

public interface ServicioPorRolDAO extends JpaRepository<ServicioPorRol, Integer> {

	@Query("SELECT sr from ServicioPorRol sr WHERE sr.id_servicio = :id_servicio AND sr.id_rol = :id_rol")
	public ServicioPorRol buscarServicioPorRol(@Param("id_servicio") String id_servicio, @Param("id_rol") String id_rol);
	
}
