package Main;

import java.sql.SQLException;
import javax.swing.SwingUtilities;
import Utilidades.TableDefinitions;

public class Main {
	public static void main(String[] args) {
		try {
			// Crear las tablas usando las definiciones de TableDefinitions
			for (int i = 0; i < TableDefinitions.ALL_TABLES_NAMES.length; i++) {
				TableManager.createTable(TableDefinitions.ALL_TABLES_NAMES[i], TableDefinitions.ALL_TABLES_SQL[i]);
			}
		} catch (SQLException e) {
			System.err.println("Error al crear las tablas: " + e.getMessage());
			e.printStackTrace();
			return; // Si no se pueden crear las tablas, no hace falta iniciar la UI
		}

		// Iniciar la UI
		SwingUtilities.invokeLater(() -> {
			new PanelManager();
		});
	}
}