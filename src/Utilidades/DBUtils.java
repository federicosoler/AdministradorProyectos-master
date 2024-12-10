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

			// Si es un SELECT, usar executeQuery y devolver ResultSet
			if (sql.trim().toUpperCase().startsWith("SELECT")) {
				ResultSet rs = stmt.executeQuery(sql);
				conexion.commit();
				return rs;
			}
			// Si no es SELECT, usar execute, cerrar conexión y no devolver nada
			else {
				stmt.execute(sql);
				conexion.commit();
				conexion.close();
				return null;
			}
		} catch (SQLException e) {
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
