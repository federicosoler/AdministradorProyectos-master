package Afiliacion;

import Proyecto.Proyecto;
import Tarea.Tarea;

public class Afiliacion {
	private int idAfiliacion;
	private Tarea tarea;
	private Proyecto proyecto;

	// Constructor por defecto
	public Afiliacion() {
	}

	// Constructor con parámetros
	public Afiliacion(int idAfiliacion, Tarea tarea, Proyecto proyecto) {
		this.idAfiliacion = idAfiliacion;
		this.tarea = tarea;
		this.proyecto = proyecto;
	}

	// Getters y Setters
	public int getIdAfiliacion() {
		return idAfiliacion;
	}

	public void setIdAfiliacion(int idAfiliacion) {
		this.idAfiliacion = idAfiliacion;
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

}
