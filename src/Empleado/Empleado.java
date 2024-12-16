package Empleado;

public class Empleado {
	private int idEmpleado;
	private String nombre;
	private String apellido;
	private double costoHora;

	// Constructor por defecto
	public Empleado() {
	}

	// Constructor con parámetros
	public Empleado(int idEmpleado, String nombre, String apellido, double costoHora) {
		this.idEmpleado = idEmpleado;
		this.nombre = nombre;
		this.apellido = apellido;
		this.costoHora = costoHora;
	}

	// Getters y Setters
	public int getIdEmpleado() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public double getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(double costoHora) {
		this.costoHora = costoHora;
	}

}
