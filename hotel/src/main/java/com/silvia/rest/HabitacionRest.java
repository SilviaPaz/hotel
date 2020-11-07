package com.silvia.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.silvia.pojo.Error;

import com.silvia.dao.HabitacionDAO;
import com.silvia.dao.RolDAO;
import com.silvia.dao.ServicioDAO;
import com.silvia.dao.ServicioPorRolDAO;
import com.silvia.dao.TipoDAO;
import com.silvia.dao.EstadoDAO;
import com.silvia.model.Estado;
import com.silvia.model.Habitacion;
import com.silvia.model.Rol;
import com.silvia.model.Rol_servicio;
import com.silvia.model.Servicio;
import com.silvia.model.ServicioPorRol;

import antlr.collections.List;

@RestController
@RequestMapping("habitacion")
public class HabitacionRest {
	
	@Autowired
	private HabitacionDAO habitacionDAO;
	
	@Autowired
	private EstadoDAO estadoDAO;
	
	@Autowired
	private TipoDAO tipoDAO;
	
	@Autowired
	private RolDAO rolDAO;
	
	@Autowired
	private ServicioDAO servicioDAO;
	
	@Autowired
	private ServicioPorRolDAO servicioPorRolDAO;
	
	private Integer cest_libre=1;
	private Integer cest_ocupada=2;
	private Integer cest_mantenimiento=3;
	private Integer cest_limpieza=4;
	
	private String cserv_transicion="SERV_TRANSACCION";
	private String cserv_reservacion="SERV_RESERVACION";
	private String cserv_listadoTodas="SERV_LISTAR_HABITACIONES_TODAS";
	private String cserv_listadolibres="SERV_LISTAR_HABITACIONES_LIBRES";
	
	@PostMapping("/insertar")
	@ResponseBody public ResponseEntity<Object> insertar(@RequestBody Map<Object, Object> datos) {
		Error error = new Error();
		int idHab = Integer.parseInt(datos.get("idHabitacion").toString());
		try {
			Habitacion habitacion1 = habitacionDAO.findById(idHab).get();
			error.setCodigo(2);
			error.setMensaje("Id de habitacion ya existe");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
		catch (Exception e) {
			int idEst = Integer.parseInt(datos.get("idEstado").toString());
			int idTip = Integer.parseInt(datos.get("idTipo").toString());
			Habitacion habitacion =new Habitacion();
			habitacion.setId(idHab);
			habitacion.setEstado(estadoDAO.findById(idEst).get());
			habitacion.setTipo(tipoDAO.findById(idTip).get());
			habitacionDAO.save(habitacion);
			error.setCodigo(0);
			error.setMensaje("Guardado con éxito");
			return new ResponseEntity<>(error,HttpStatus.OK);
			
		}
	}
	
	@GetMapping("/listar")
	@ResponseBody public ResponseEntity<Object> listar(@RequestParam String id_rol) {
		Error error = new Error();
		if (validarServicioxRol(cserv_listadoTodas, id_rol)) {
			return new ResponseEntity<>(habitacionDAO.findAll(),HttpStatus.OK);
		} 
		else {
			error.setCodigo(1);
			error.setMensaje("Rol incorrecto para listar habitaciones");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
	}
	
	@PostMapping("/actualizar")
	@ResponseBody public ResponseEntity<Object> actualizar(@RequestBody Map<Object, Object> datos) {
		Error error = new Error();
		int idHab = Integer.parseInt(datos.get("idHabitacion").toString());
		try {
			Habitacion habitacion1 = habitacionDAO.findById(idHab).get();
			int idEst = Integer.parseInt(datos.get("idEstado").toString());
			int idTip = Integer.parseInt(datos.get("idTipo").toString());
			Habitacion habitacion =new Habitacion();
			habitacion.setId(idHab);
			habitacion.setEstado(estadoDAO.findById(idEst).get());
			habitacion.setTipo(tipoDAO.findById(idTip).get());
			habitacionDAO.save(habitacion);
			error.setCodigo(0);
			error.setMensaje("Modificado con éxito");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
		catch (Exception e) {
			error.setCodigo(2);
			error.setMensaje("Error en actualizar");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
	}
	
	/*
	@GetMapping("/listarPorId")
	@ResponseBody public ResponseEntity<Object> listar
	(@RequestParam Integer id) {
		return new ResponseEntity<>(habitacionDAO.findById(id),
				HttpStatus.OK);
	}*/
	
	@GetMapping("/listarPorId2")
	@ResponseBody public ResponseEntity<Object> listar2
	(@RequestBody Map<Object,Object> id) {
		java.util.List<Integer> ids = (java.util.List<Integer>) id.get("listado");
		return new ResponseEntity<>(habitacionDAO.findAllById(ids),
				HttpStatus.OK);
	}
	
	//transacciones -> gerente y recepcionista
	@PostMapping("/transacciones")
	@ResponseBody public ResponseEntity<Object> transacciones
	(@RequestBody Map<Object,Object> datos1) {
		Error error = new Error();
		
		try {
			int idHab = (int)datos1.get("idHab");
			int idEst = (int)datos1.get("idEst");
			String id_rol = (String)datos1.get("idRol");
			if (validarServicioxRol(cserv_transicion, id_rol)) {
				Estado estado = estadoDAO.findById(idEst).get();
				Habitacion habitacion = habitacionDAO.findById(idHab).get();
				int estAnt = habitacion.getEstado().getId_estado();
				int estNuevo = estado.getId_estado();
				if ((estAnt==cest_libre && (estNuevo==cest_ocupada || estNuevo==cest_mantenimiento))
				|| (estAnt==cest_ocupada && (estNuevo==cest_mantenimiento || estNuevo==cest_limpieza))
				|| (estAnt==cest_limpieza && (estNuevo==cest_libre || estNuevo==cest_mantenimiento))
				|| (estAnt==cest_mantenimiento && (estNuevo==cest_libre || estNuevo==cest_limpieza)))
				{
					habitacion.setEstado(estado);
					habitacionDAO.save(habitacion);
					error.setCodigo(0);
					error.setMensaje("Guardado con éxito");
					return new ResponseEntity<>(error,HttpStatus.OK);
				}
				else {
					error.setCodigo(2);
					error.setMensaje("Error: transacción incorrecta");
					return new ResponseEntity<>(error,HttpStatus.OK);
				}
			}
			else {
				error.setCodigo(1);
				error.setMensaje("Rol incorrecto para hacer transiciones");
				return new ResponseEntity<>(error,HttpStatus.OK);
			}
		}
		catch(Exception e) { 
			error.setCodigo(1);
			error.setMensaje("Error al guardar");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
	}
	
	//Obtener habitaciones por estado
	@GetMapping("/listarPorEstado")
	@ResponseBody public ResponseEntity<Object> listarPorEstado
	(@RequestParam Integer id) {
		Estado e = estadoDAO.findById(id).get(); 
		return new ResponseEntity<>(e.getHabitaciones(),
				HttpStatus.OK);
	} 
	
	//Consultar habitaciones libres
	@GetMapping("/listarHabitacionesLibres")
	@ResponseBody public ResponseEntity<Object> listarHabitacionesLibres(@RequestParam String id_rol) {
		Error error = new Error();
		Map<Object,Object> respuesta = new HashMap<Object, Object>();
		try {
			if (validarServicioxRol(cserv_listadolibres, id_rol)) {
				Estado e = estadoDAO.findById(cest_libre).get(); 
				if (e.getHabitaciones()!= null) {
					error.setCodigo(0);
					error.setMensaje("Éxito");
					respuesta.put("error",error);
					respuesta.put("lista",e.getHabitaciones());
					return new ResponseEntity<>(respuesta,HttpStatus.OK);
				}
				else {
					error.setCodigo(2);
					error.setMensaje("No hay cupos");
					respuesta.put("error",error);
					return new ResponseEntity<>(respuesta,HttpStatus.OK);
				}
			}
			else {
				error.setCodigo(0);
				error.setMensaje("Servicio no autorizado para su Rol");
				respuesta.put("error",error);
				return new ResponseEntity<>(respuesta,HttpStatus.OK);
			}
		}
		catch(Exception e) { 
			error.setCodigo(1);
			error.setMensaje("Error al obtener habitaciones");
			respuesta.put("error",error);
			return new ResponseEntity<>(respuesta,HttpStatus.OK);
		}
	} 
	
	//Reservar una habitacion
	@PostMapping("/reservar")
	@ResponseBody public ResponseEntity<Object> reservar(@RequestBody Map<Object,Object> datos) {
		Error error = new Error();
		try {
			int id = (int)datos.get("id");
			int cantidad = (int)datos.get("cantidad");
			Habitacion habitacion = habitacionDAO.findById(id).get();
			Estado estado;
			if (habitacion!=null) {
				if(habitacion.getEstado().getId_estado()==cest_libre)
				{
					if(habitacion.getTipo().getOcupantes()>=cantidad) {
						estado= estadoDAO.findById(cest_ocupada).get();
						habitacion.setEstado(estado);
						habitacionDAO.save(habitacion);
						error.setCodigo(0);
						error.setMensaje("Habitación reservada");
						return new ResponseEntity<>(error,HttpStatus.OK);
					}
					else {
						error.setCodigo(3);
						error.setMensaje("Numero de personas excede el límite por tipo de habitación");
						return new ResponseEntity<>(error,HttpStatus.OK);
					}
				}
				else {
					error.setCodigo(3);
					error.setMensaje("Habitacion no está libre");
					return new ResponseEntity<>(error,HttpStatus.OK);
				}
			}
			else {
				error.setCodigo(2);
				error.setMensaje("Habitacion no existe");
				return new ResponseEntity<>(error,HttpStatus.OK);
			}
		}
		catch(Exception e) { 
			error.setCodigo(1);
			error.setMensaje("Error al guardar");
			return new ResponseEntity<>(error,HttpStatus.OK);
		}
	}
	
	/*
	public boolean validarRol(int id_rol,int id_servicio){
		try {
			Rol rol = rolDAO.findById(id_rol).get();
			List <Servicio> servicio = rol.getServicio().;
			if (servici) 
			return true;
			}
			
			else return false;
		}
		catch (Exception e) {
			return false;
		}
	}
	*/
	
	// Validar Acceso a servicio por Rol
	public boolean validarServicioxRol(String id_servicio, String id_rol){
		try {
			ServicioPorRol servicioPorRol = servicioPorRolDAO.buscarServicioPorRol(id_servicio,id_rol);
			if (servicioPorRol!=null) 
				return true;
			return false;
		}
		catch (Exception e) {
			return false;
		}
	}
	
	/*
	//Obtener habitaciones por estado
		@GetMapping("/listarPorEstado")
		@ResponseBody public ResponseEntity<Object> listarPorEstado
		(@RequestParam Integer id) {
			Estado e = estadoDAO.findById(id).get(); 
			return new ResponseEntity<>(e.getHabitaciones(),
					HttpStatus.OK);
		} 
		*/
}
