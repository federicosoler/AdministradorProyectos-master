package Utilidades;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Main.DBManager;

public class DBUtils {

	public static ResultSet ejecutarSQL(String sql) throws SQLException {
		Connection conexion = null;
		try {
			conexion = DBManager.connect();
			conexion.setAutoCommit(false);
			Statement stmt = conexion.createStatement();

			// Si es un SELECT, uso executeQuery() que devuelve un ResultSet
			if (sql.startsWith("SELECT")) {
				ResultSet rs = stmt.executeQuery(sql);
				conexion.commit();
				return rs;
			}

			// Si no es SELECT, uso execute() que no devuelve nada y cierro la conexión
			else {
				stmt.execute(sql);
				conexion.commit();
				conexion.close();
				return null;
			}

		} catch (SQLException e) {

			// Cancelo los cambios que hice en la BD y cierro la conexión
			try {
				if (conexion != null) {
					conexion.rollback();
					conexion.close();
				}
			} catch (SQLException rollbackEx) {
				System.err.println("Error durante el rollback: " + rollbackEx.getMessage());
			}
			throw e;
		}
	}

	// Método para cerrar la conexión manualmente para sentencias que no son SELECT
	public static void cerrarRecursos(ResultSet rs) {
		try {
			if (rs != null) {
				Connection conexion = rs.getStatement().getConnection();
				rs.close();
				if (conexion != null) {
					conexion.close();
				}
			}
		} catch (SQLException e) {
			System.err.println("Error al cerrar recursos: " + e.getMessage());
		}
	}
}
