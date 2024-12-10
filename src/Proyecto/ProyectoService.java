package Proyecto;

import Utilidades.GenericService;

public class ProyectoService extends GenericService<Proyecto> {

	public ProyectoService() {
		super(new ProyectoH2Impl());
	}
}