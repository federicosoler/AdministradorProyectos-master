package Asignacion;

import Utilidades.GenericService;

public class AsignacionService extends GenericService<Asignacion> {

	public AsignacionService() {
		super(new AsignacionH2Impl());
	}
}
