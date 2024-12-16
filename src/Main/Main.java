package Main;

import java.sql.SQLException;
import javax.swing.SwingUtilities;
import Utilidades.TableDefinitions;

public class Main {
	public static void main(String[] args) {

		// Creo las tablas en la BD
		try {

			for (int i = 0; i < TableDefinitions.ALL_TABLES_NAMES.length; i++) {
				TableManager.createTable(TableDefinitions.ALL_TABLES_NAMES[i], TableDefinitions.ALL_TABLES_SQL[i]);
			}

		} catch (SQLException e) {
			System.err.println("Error al crear las tablas: " + e.getMessage());
			e.printStackTrace();

			// Si no se pudieron crear las tablas, no inicio la UI
			return;
		}

		// Inicio la UI (en un hilo seguro para Swing)
		SwingUtilities.invokeLater(() -> {
			new PanelManager();
		});
	}
}