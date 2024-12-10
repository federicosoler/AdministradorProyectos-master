package Proyecto;

public class Proyecto {
	private int idProyecto;
	private String descripcion;

	// Constructor por defecto
	public Proyecto() {
	}

	// Constructor con parámetros
	public Proyecto(int idProyecto, String descripcion) {
		this.idProyecto = idProyecto;
		this.descripcion = descripcion;
	}

	// Getters y Setters
	public int getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(int idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	// Método toString
	@Override
	public String toString() {
		return "Proyecto{" + "idProyecto=" + idProyecto + ", descripcion='" + descripcion + '\'' + '}';
	}
}
