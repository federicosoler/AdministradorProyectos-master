package Empleado;

import Utilidades.GenericService;

public class EmpleadoService extends GenericService<Empleado> {

	public EmpleadoService() {
		super(new EmpleadoH2Impl());
	}
}