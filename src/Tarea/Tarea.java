package Tarea;

public class Tarea {
	private int idTarea;
	private String titulo;
	private String descripcion;
	private int horasEstimadas;

	// Constructor por defecto
	public Tarea() {
	}

	// Constructor con parámetros
	public Tarea(int idTarea, String titulo, String descripcion, int horasEstimadas) {
		this.idTarea = idTarea;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.horasEstimadas = horasEstimadas;
	}

	// Getters y Setters
	public int getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(int idTarea) {
		this.idTarea = idTarea;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getHorasEstimadas() {
		return horasEstimadas;
	}

	public void setHorasEstimadas(int horasEstimadas) {
		this.horasEstimadas = horasEstimadas;
	}

	// Método toString
	@Override
	public String toString() {
		return "Tarea{" + "idTarea=" + idTarea + ", titulo='" + titulo + '\'' + ", descripcion='" + descripcion + '\''
				+ ", horasEstimadas=" + horasEstimadas + '}';
	}
}
