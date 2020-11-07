package com.silvia.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Rol_servicio {
	@Id
	private int id_rol_servicio;
	
	@ManyToOne
	@JoinColumn(name="id_rol")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name="id_servicio")
	private Servicio servicio;
	
	public int getId_rol_servicio() {
		return id_rol_servicio;  
	}
	public void setId_rol_servicio(int id_rol_servicio) {
		this.id_rol_servicio = id_rol_servicio;
	}
	public Rol getRol() { 
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	
	
}
