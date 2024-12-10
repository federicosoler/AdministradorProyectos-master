package Asignacion;

import Empleado.Empleado;
import Proyecto.Proyecto;

public class Asignacion {
	private int idAsignacion;
	private Empleado empleado;
	private Proyecto proyecto;

	// Constructor por defecto
	public Asignacion() {
	}

	// Constructor con parámetros
	public Asignacion(int idAsignacion, Empleado empleado, Proyecto proyecto) {
		this.idAsignacion = idAsignacion;
		this.empleado = empleado;
		this.proyecto = proyecto;
	}

	// Getters y Setters
	public int getIdAsignacion() {
		return idAsignacion;
	}

	public void setIdAsignacion(int idAsignacion) {
		this.idAsignacion = idAsignacion;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	// Método toString
	@Override
	public String toString() {
		return "Asignacion{" + "idAsignacion=" + idAsignacion + ", empleado=" + empleado + ", proyecto=" + proyecto
				+ '}';
	}
}
