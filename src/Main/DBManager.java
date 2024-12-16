package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_URL = "jdbc:h2:file:./db/DB";
	private static final String DB_USERNAME = "sa";
	private static final String DB_PASSWORD = "";

	public static Connection connect() {
		Connection c = null;
		try {
			Class.forName(DB_DRIVER);
			c = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			c.setAutoCommit(false);
		} catch (ClassNotFoundException e) {

			// Problema de configuración del controlador JDBC
			System.err.println("Driver not found: " + e.getMessage());

		} catch (SQLException e) {

			// Problema de BD
			System.err.println("SQL error: " + e.getMessage());
		}
		// Devuelvo un objeto Connection que representa una conexión activa a la BD
		return c;
	}
}
