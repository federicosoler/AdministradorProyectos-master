package Empleado;

import java.sql.ResultSet;
import java.sql.SQLException;
import Utilidades.GenericH2Impl;

public class EmpleadoH2Impl extends GenericH2Impl<Empleado> implements EmpleadoDAO {

	public EmpleadoH2Impl() {
		super("Empleado", "ID_Empleado");
	}

	@Override
	protected String buildInsertSQL(Empleado empleado) {
		return String.format("INSERT INTO Empleado (Nombre, Apellido, Costo_Hora) VALUES ('%s', '%s', %f)",
				empleado.getNombre(), empleado.getApellido(), empleado.getCostoHora());
	}

	@Override
	protected String buildUpdateSQL(Empleado empleado) {
		return String.format(
				"UPDATE Empleado SET Nombre = '%s', Apellido = '%s', Costo_Hora = %f WHERE ID_Empleado = %d",
				empleado.getNombre(), empleado.getApellido(), empleado.getCostoHora(), empleado.getIdEmpleado());
	}

	@Override
	protected Empleado mapResultSetToEntity(ResultSet rs) throws SQLException {
		Empleado empleado = new Empleado();
		empleado.setIdEmpleado(rs.getInt("ID_Empleado"));
		empleado.setNombre(rs.getString("Nombre"));
		empleado.setApellido(rs.getString("Apellido"));
		empleado.setCostoHora(rs.getDouble("Costo_Hora"));
		return empleado;
	}
}