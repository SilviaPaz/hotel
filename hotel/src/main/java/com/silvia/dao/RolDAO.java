package com.silvia.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.silvia.model.Rol;

public interface RolDAO extends JpaRepository<Rol, Integer> {
		
}