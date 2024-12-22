package Empleado;

import Utilidades.GenericService;

public class EmpleadoService extends GenericService<Empleado> {

	// Constructor que instancia la clase EmpleadoH2Impl que implementa la interfaz EmpleadoDAO
	public EmpleadoService() {
		super(new EmpleadoH2Impl());
	}
}