package com.silvia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvia.model.Rol;
import com.silvia.model.Servicio;

public interface ServicioDAO extends JpaRepository<Servicio, Integer> {
		
}