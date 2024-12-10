package Tarea;

import java.sql.ResultSet;
import java.sql.SQLException;
import Utilidades.GenericH2Impl;

public class TareaH2Impl extends GenericH2Impl<Tarea> implements TareaDAO {

	public TareaH2Impl() {
		super("Tarea", "ID_Tarea");
	}

	@Override
	protected String buildInsertSQL(Tarea tarea) {
		return String.format("INSERT INTO Tarea (Titulo, Descripcion, Horas_Estimadas) VALUES ('%s', '%s', %d)",
				tarea.getTitulo(), tarea.getDescripcion(), tarea.getHorasEstimadas());
	}

	@Override
	protected String buildUpdateSQL(Tarea tarea) {
		return String.format(
				"UPDATE Tarea SET Titulo = '%s', Descripcion = '%s', Horas_Estimadas = %d WHERE ID_Tarea = %d",
				tarea.getTitulo(), tarea.getDescripcion(), tarea.getHorasEstimadas(), tarea.getIdTarea());
	}

	@Override
	protected Tarea mapResultSetToEntity(ResultSet rs) throws SQLException {
		Tarea tarea = new Tarea();
		tarea.setIdTarea(rs.getInt("ID_Tarea"));
		tarea.setTitulo(rs.getString("Titulo"));
		tarea.setDescripcion(rs.getString("Descripcion"));
		tarea.setHorasEstimadas(rs.getInt("Horas_Estimadas"));
		return tarea;
	}
}