package Historial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import Utilidades.GenericH2Impl;
import Empleado.EmpleadoH2Impl;
import Exceptions.DAOException;
import Tarea.TareaH2Impl;
import Empleado.Empleado;
import Tarea.Tarea;

public class HistorialH2Impl extends GenericH2Impl<Historial> implements HistorialDAO {

	private final EmpleadoH2Impl empleadoDAO;
	private final TareaH2Impl tareaDAO;

	public HistorialH2Impl() {
		super("Historial", "ID_Historial");
		this.empleadoDAO = new EmpleadoH2Impl();
		this.tareaDAO = new TareaH2Impl();
	}

	@Override
	protected String buildInsertSQL(Historial historial) {
		return String.format(
				"INSERT INTO Historial (ID_Empleado, ID_Tarea, Estado, Horas_Trabajadas, Fecha) "
						+ "VALUES (%d, %d, '%s', %d, '%tY-%tm-%td')",
				historial.getEmpleado().getIdEmpleado(), historial.getTarea().getIdTarea(), historial.getEstado(),
				historial.getHorasReales(), historial.getFechaAjuste(), historial.getFechaAjuste(),
				historial.getFechaAjuste());
	}

	@Override
	protected String buildUpdateSQL(Historial historial) {
		return String.format(
				"UPDATE Historial SET ID_Empleado = %d, ID_Tarea = %d, Estado = '%s', "
						+ "Horas_Trabajadas = %d, Fecha = '%tY-%tm-%td' WHERE ID_Historial = %d",
				historial.getEmpleado().getIdEmpleado(), historial.getTarea().getIdTarea(), historial.getEstado(),
				historial.getHorasReales(), historial.getFechaAjuste(), historial.getFechaAjuste(),
				historial.getFechaAjuste(), historial.getIdHistorial());
	}

	@Override
	protected Historial mapResultSetToEntity(ResultSet rs) throws SQLException, DAOException {
		Historial historial = new Historial();
		historial.setIdHistorial(rs.getInt("ID_Historial"));

		// Obtener Empleado relacionado
		int idEmpleado = rs.getInt("ID_Empleado");
		Empleado empleado = empleadoDAO.read(idEmpleado);
		historial.setEmpleado(empleado);

		// Obtener Tarea relacionada
		int idTarea = rs.getInt("ID_Tarea");
		Tarea tarea = tareaDAO.read(idTarea);
		historial.setTarea(tarea);

		historial.setEstado(rs.getString("Estado"));
		historial.setFechaAjuste(new Date(rs.getDate("Fecha").getTime()));
		historial.setHorasReales(rs.getInt("Horas_Trabajadas"));

		return historial;
	}
}