package Historial;

import java.util.Date;
import Empleado.Empleado;
import Tarea.Tarea;

public class Historial {
	private int idHistorial;
	private Tarea tarea;
	private Empleado empleado;
	private String estado;
	private Date fechaAjuste;
	private int horasReales;

	// Constructor por defecto
	public Historial() {
	}

	// Constructor con parámetros
	public Historial(int idHistorial, Tarea tarea, Empleado empleado, String estado, Date fechaAjuste,
			int horasReales) {
		this.idHistorial = idHistorial;
		this.tarea = tarea;
		this.empleado = empleado;
		this.estado = estado;
		this.fechaAjuste = fechaAjuste;
		this.horasReales = horasReales;
	}

	// Getters y Setters
	public int getIdHistorial() {
		return idHistorial;
	}

	public void setIdHistorial(int idHistorial) {
		this.idHistorial = idHistorial;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaAjuste() {
		return fechaAjuste;
	}

	public void setFechaAjuste(Date fechaAjuste) {
		this.fechaAjuste = fechaAjuste;
	}

	public int getHorasReales() {
		return horasReales;
	}

	public void setHorasReales(int horasReales) {
		this.horasReales = horasReales;
	}

}
