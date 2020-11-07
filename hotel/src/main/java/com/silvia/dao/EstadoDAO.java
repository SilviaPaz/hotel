package com.silvia.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.silvia.model.Estado;

public interface EstadoDAO extends JpaRepository<Estado, Integer> {
	
}
