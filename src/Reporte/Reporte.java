package Reporte;

import java.util.Date;
import Proyecto.Proyecto;
import Historial.Historial;

public class Reporte {
	private int idReporte;
	private Proyecto proyecto;
	private Historial historial;
	private double costoHoras;
	private double costoDinero;
	private Date fechaCreacion;

	// Constructor por defecto
	public Reporte() {
	}

	// Constructor con parámetros
	public Reporte(int idReporte, Proyecto proyecto, Historial historial, double costoHoras, double costoDinero,
			Date fechaCreacion) {
		this.idReporte = idReporte;
		this.proyecto = proyecto;
		this.historial = historial;
		this.costoHoras = costoHoras;
		this.costoDinero = costoDinero;
		this.fechaCreacion = fechaCreacion;
	}

	// Getters y Setters
	public int getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Historial getHistorial() {
		return historial;
	}

	public void setHistorial(Historial historial) {
		this.historial = historial;
	}

	public double getCostoHoras() {
		return costoHoras;
	}

	public void setCostoHoras(double costoHoras) {
		this.costoHoras = costoHoras;
	}

	public double getCostoDinero() {
		return costoDinero;
	}

	public void setCostoDinero(double costoDinero) {
		this.costoDinero = costoDinero;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
