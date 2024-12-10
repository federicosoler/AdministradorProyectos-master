package Afiliacion;

import java.sql.ResultSet;
import java.sql.SQLException;

import Exceptions.DAOException;
import Utilidades.GenericH2Impl;
import Tarea.TareaH2Impl;
import Proyecto.ProyectoH2Impl;
import Tarea.Tarea;
import Proyecto.Proyecto;

public class AfiliacionH2Impl extends GenericH2Impl<Afiliacion> implements AfiliacionDAO {

	private final TareaH2Impl tareaDAO;
	private final ProyectoH2Impl proyectoDAO;

	public AfiliacionH2Impl() {
		super("Afiliacion", "ID_Afiliacion");
		this.tareaDAO = new TareaH2Impl();
		this.proyectoDAO = new ProyectoH2Impl();
	}

	@Override
	protected String buildInsertSQL(Afiliacion afiliacion) {
		return String.format("INSERT INTO Afiliacion (ID_Tarea, ID_Proyecto) VALUES (%d, %d)",
				afiliacion.getTarea().getIdTarea(), afiliacion.getProyecto().getIdProyecto());
	}

	@Override
	protected String buildUpdateSQL(Afiliacion afiliacion) {
		return String.format("UPDATE Afiliacion SET ID_Tarea = %d, ID_Proyecto = %d " + "WHERE ID_Afiliacion = %d",
				afiliacion.getTarea().getIdTarea(), afiliacion.getProyecto().getIdProyecto(),
				afiliacion.getIdAfiliacion());
	}

	@Override
	protected Afiliacion mapResultSetToEntity(ResultSet rs) throws SQLException, DAOException {
		Afiliacion afiliacion = new Afiliacion();
		afiliacion.setIdAfiliacion(rs.getInt("ID_Afiliacion"));

		// Obtener Tarea relacionada
		int idTarea = rs.getInt("ID_Tarea");
		Tarea tarea = tareaDAO.read(idTarea);
		afiliacion.setTarea(tarea);

		// Obtener Proyecto relacionado
		int idProyecto = rs.getInt("ID_Proyecto");
		Proyecto proyecto = proyectoDAO.read(idProyecto);
		afiliacion.setProyecto(proyecto);

		return afiliacion;
	}
}
