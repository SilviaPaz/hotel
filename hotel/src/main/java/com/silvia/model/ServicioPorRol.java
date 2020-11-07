package com.silvia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServicioPorRol {

	@Id 
	private int id_servicio_rol;
	
	@Column
	private String id_servicio;
	
	@Column
	private String id_rol;

	public int getId_servicio_rol() {
		return id_servicio_rol;
	}
 
	public void setId_servicio_rol(int id_servicio_rol) {
		this.id_servicio_rol = id_servicio_rol;
	}

	public String getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(String id_servicio) {
		this.id_servicio = id_servicio;
	}

	public String getId_rol() {
		return id_rol;
	}

	public void setId_rol(String id_rol) {
		this.id_rol = id_rol;
	}
	
}
