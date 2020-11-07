
insert into estado (id_estado,nombre) values (1,'Libre');
insert into estado (id_estado,nombre) values (2,'Ocupada');
insert into estado (id_estado,nombre) values (3,'Mantenimiento');
insert into estado (id_estado,nombre) values (4,'Limpieza');

insert into tipo (id_tipo,nombre,ocupantes) values (1,'Estándar',1);
insert into tipo (id_tipo,nombre,ocupantes) values (2,'Normal',2);
insert into tipo (id_tipo,nombre,ocupantes) values (3,'Suite',3);

insert into habitacion (id,id_estado,TIPO_ID_TIPO) values (1,1,1);

insert into habitacion (id,id_estado,TIPO_ID_TIPO) values (2,2,2);

insert into habitacion (id,id_estado,TIPO_ID_TIPO) values (3,3,3);


insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (1,'GERENTE', 'SERV_TRANSACCION');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (2,'GERENTE', 'SERV_RESERVACION');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (3,'GERENTE', 'SERV_LISTAR_HABITACIONES_TODAS');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (4,'GERENTE', 'SERV_LISTAR_HABITACIONES_LIBRES');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (5,'RECEPCIONISTA', 'SERV_TRANSACCION');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (6,'RECEPCIONISTA', 'SERV_RESERVACION');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (7,'RECEPCIONISTA', 'SERV_LISTAR_HABITACIONES_TODAS');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (8,'CLIENTE', 'SERV_RESERVACION');
insert into servicio_por_rol (id_Servicio_rol, id_rol, id_servicio) values (9,'CLIENTE', 'SERV_LISTAR_HABITACIONES_LIBRES');
