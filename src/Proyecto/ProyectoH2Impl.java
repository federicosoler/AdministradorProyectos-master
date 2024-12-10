package Proyecto;

import java.sql.ResultSet;
import java.sql.SQLException;
import Utilidades.GenericH2Impl;

public class ProyectoH2Impl extends GenericH2Impl<Proyecto> implements ProyectoDAO {

	public ProyectoH2Impl() {
		super("Proyecto", "ID_Proyecto");
	}

	@Override
	protected String buildInsertSQL(Proyecto proyecto) {
		return String.format("INSERT INTO Proyecto (Descripcion) VALUES ('%s')", proyecto.getDescripcion());
	}

	@Override
	protected String buildUpdateSQL(Proyecto proyecto) {
		return String.format("UPDATE Proyecto SET Descripcion = '%s' WHERE ID_Proyecto = %d", proyecto.getDescripcion(),
				proyecto.getIdProyecto());
	}

	@Override
	protected Proyecto mapResultSetToEntity(ResultSet rs) throws SQLException {
		Proyecto proyecto = new Proyecto();
		proyecto.setIdProyecto(rs.getInt("ID_Proyecto"));
		proyecto.setDescripcion(rs.getString("Descripcion"));
		return proyecto;
	}
}