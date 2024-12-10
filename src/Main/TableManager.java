package Main;

import java.sql.SQLException;
import Utilidades.DBUtils;

public class TableManager {

	public static void createTable(String tableName, String tableDefinition) throws SQLException {
		String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableDefinition + ");";
		DBUtils.ejecutarSQL(sql);
	}
}