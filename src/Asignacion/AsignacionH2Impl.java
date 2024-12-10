package Asignacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import Utilidades.GenericH2Impl;
import Empleado.EmpleadoH2Impl;
import Exceptions.DAOException;
import Proyecto.ProyectoH2Impl;
import Empleado.Empleado;
import Proyecto.Proyecto;

public class AsignacionH2Impl extends GenericH2Impl<Asignacion> implements AsignacionDAO {

	private final EmpleadoH2Impl empleadoDAO;
	private final ProyectoH2Impl proyectoDAO;

	public AsignacionH2Impl() {
		super("Asignacion", "ID_Asignacion");
		this.empleadoDAO = new EmpleadoH2Impl();
		this.proyectoDAO = new ProyectoH2Impl();
	}

	@Override
	protected String buildInsertSQL(Asignacion asignacion) {
		return String.format("INSERT INTO Asignacion (ID_Empleado, ID_Proyecto) VALUES (%d, %d)",
				asignacion.getEmpleado().getIdEmpleado(), asignacion.getProyecto().getIdProyecto());
	}

	@Override
	protected String buildUpdateSQL(Asignacion asignacion) {
		return String.format("UPDATE Asignacion SET ID_Empleado = %d, ID_Proyecto = %d WHERE ID_Asignacion = %d",
				asignacion.getEmpleado().getIdEmpleado(), asignacion.getProyecto().getIdProyecto(),
				asignacion.getIdAsignacion());
	}

	@Override
	protected Asignacion mapResultSetToEntity(ResultSet rs) throws SQLException, DAOException {
		Asignacion asignacion = new Asignacion();
		asignacion.setIdAsignacion(rs.getInt("ID_Asignacion"));

		// Obtener Empleado relacionado
		int idEmpleado = rs.getInt("ID_Empleado");
		Empleado empleado = empleadoDAO.read(idEmpleado);
		asignacion.setEmpleado(empleado);

		// Obtener Proyecto relacionado
		int idProyecto = rs.getInt("ID_Proyecto");
		Proyecto proyecto = proyectoDAO.read(idProyecto);
		asignacion.setProyecto(proyecto);

		return asignacion;
	}
}
