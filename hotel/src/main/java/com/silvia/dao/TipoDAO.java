package com.silvia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvia.model.Tipo;

public interface TipoDAO extends JpaRepository<Tipo, Integer> {
		
}