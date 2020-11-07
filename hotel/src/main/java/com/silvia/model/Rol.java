package com.silvia.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rol {

	@Id 
	private int id_rol;
	@Column
	private String nombre;
	
	@JsonIgnore
	@OneToMany(mappedBy="rol",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Servicio> servicio= new ArrayList<Servicio>();

	public int getId_rol() {
		return id_rol;
	}

	public void setId_rol(int id_rol) {
		this.id_rol = id_rol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	
}
