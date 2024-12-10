package Reporte;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Exceptions.DAOException;
import Utilidades.GenericH2Impl;
import Proyecto.ProyectoH2Impl;
import Historial.HistorialH2Impl;
import Proyecto.Proyecto;
import Historial.Historial;

public class ReporteH2Impl extends GenericH2Impl<Reporte> implements ReporteDAO {

	private final ProyectoH2Impl proyectoDAO;
	private final HistorialH2Impl historialDAO;

	public ReporteH2Impl() {
		super("Reporte", "ID_Reporte");
		this.proyectoDAO = new ProyectoH2Impl();
		this.historialDAO = new HistorialH2Impl();
	}

	@Override
	protected String buildInsertSQL(Reporte reporte) {
		return String.format(
				"INSERT INTO Reporte (ID_Proyecto, ID_Historial, Costo_Horas, Costo_Dinero, Fecha_Creacion) "
						+ "VALUES (%d, %d, %f, %f, '%tY-%tm-%td')",
				reporte.getProyecto().getIdProyecto(), reporte.getHistorial().getIdHistorial(), reporte.getCostoHoras(),
				reporte.getCostoDinero(), reporte.getFechaCreacion(), reporte.getFechaCreacion(),
				reporte.getFechaCreacion());
	}

	@Override
	protected String buildUpdateSQL(Reporte reporte) {
		return String.format(
				"UPDATE Reporte SET ID_Proyecto = %d, ID_Historial = %d, Costo_Horas = %f, "
						+ "Costo_Dinero = %f, Fecha_Creacion = '%tY-%tm-%td' WHERE ID_Reporte = %d",
				reporte.getProyecto().getIdProyecto(), reporte.getHistorial().getIdHistorial(), reporte.getCostoHoras(),
				reporte.getCostoDinero(), reporte.getFechaCreacion(), reporte.getFechaCreacion(),
				reporte.getFechaCreacion(), reporte.getIdReporte());
	}

	@Override
	protected Reporte mapResultSetToEntity(ResultSet rs) throws SQLException, DAOException {
		Reporte reporte = new Reporte();
		reporte.setIdReporte(rs.getInt("ID_Reporte"));

		// Obtener Proyecto relacionado
		int idProyecto = rs.getInt("ID_Proyecto");
		Proyecto proyecto = proyectoDAO.read(idProyecto);
		reporte.setProyecto(proyecto);

		// Obtener Historial relacionado
		int idHistorial = rs.getInt("ID_Historial");
		Historial historial = historialDAO.read(idHistorial);
		reporte.setHistorial(historial);

		reporte.setCostoHoras(rs.getDouble("Costo_Horas"));
		reporte.setCostoDinero(rs.getDouble("Costo_Dinero"));
		reporte.setFechaCreacion(new Date(rs.getDate("Fecha_Creacion").getTime()));

		return reporte;
	}
}