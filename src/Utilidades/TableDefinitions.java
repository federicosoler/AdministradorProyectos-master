package Utilidades;

public class TableDefinitions {

	// Nombres de las tablas
	public static final String EMPLEADO = "Empleado";
	public static final String PROYECTO = "Proyecto";
	public static final String TAREA = "Tarea";
	public static final String HISTORIAL = "Historial";
	public static final String AFILIACION = "Afiliacion";
	public static final String ASIGNACION = "Asignacion";
	public static final String REPORTE = "Reporte";

	// Array con los nombres de las tablas
	public static final String[] ALL_TABLES_NAMES = { EMPLEADO, PROYECTO, TAREA, HISTORIAL, AFILIACION, ASIGNACION,
			REPORTE };

	// ------------------------------------------------------------------------------------------------------------------------------
	// Columnas de las tablas
	public static final String[] EMPLEADO_COLUMNAS = { "ID_Empleado", "Nombre", "Apellido", "Costo_Hora" };
	public static final String[] PROYECTO_COLUMNAS = { "ID_Proyecto", "Descripcion" };
	public static final String[] TAREA_COLUMNAS = { "ID_Tarea", "Titulo", "Descripcion", "Horas_Estimadas" };
	public static final String[] HISTORIAL_COLUMNAS = { "ID_Historial", "ID_Empleado", "ID_Tarea", "Estado",
			"Horas_Trabajadas", "Fecha" };
	public static final String[] AFILIACION_COLUMNAS = { "ID_Afiliacion", "ID_Tarea", "ID_Proyecto" };
	public static final String[] ASIGNACION_COLUMNAS = { "ID_Asignacion", "ID_Empleado", "ID_Proyecto" };
	public static final String[] REPORTE_COLUMNAS = { "ID_Reporte", "ID_Proyecto", "ID_Historial", "Costo_Horas",
			"Costo_Dinero", "Fecha_Creacion" };

	// Array con los nombres de las columnas
	public static final String[][] ALL_TABLES_COLUMNS = { EMPLEADO_COLUMNAS, PROYECTO_COLUMNAS, TAREA_COLUMNAS,
			HISTORIAL_COLUMNAS, AFILIACION_COLUMNAS, ASIGNACION_COLUMNAS, REPORTE_COLUMNAS };

	// ------------------------------------------------------------------------------------------------------------------------------
	// Definiciones SQL de las tablas
	public static final String EMPLEADO_SQL = "ID_Empleado INT AUTO_INCREMENT PRIMARY KEY, " + "Nombre VARCHAR(255), "
			+ "Apellido VARCHAR(255), " + "Costo_Hora DECIMAL(10, 2)";

	public static final String PROYECTO_SQL = "ID_Proyecto INT AUTO_INCREMENT PRIMARY KEY, " + "Descripcion TEXT";

	public static final String ASIGNACION_SQL = "ID_Asignacion INT AUTO_INCREMENT PRIMARY KEY, " + "ID_Empleado INT, "
			+ "ID_Proyecto INT, " + "FOREIGN KEY (ID_Empleado) REFERENCES Empleado(ID_Empleado), "
			+ "FOREIGN KEY (ID_Proyecto) REFERENCES Proyecto(ID_Proyecto)";

	public static final String TAREA_SQL = "ID_Tarea INT AUTO_INCREMENT PRIMARY KEY, " + "Titulo VARCHAR(255), "
			+ "Descripcion TEXT, " + "Horas_Estimadas INT";

	public static final String AFILIACION_SQL = "ID_Afiliacion INT AUTO_INCREMENT PRIMARY KEY, " + "ID_Tarea INT, "
			+ "ID_Proyecto INT, " + "FOREIGN KEY (ID_Tarea) REFERENCES Tarea(ID_Tarea), "
			+ "FOREIGN KEY (ID_Proyecto) REFERENCES Proyecto(ID_Proyecto)";

	public static final String REPORTE_SQL = "ID_Reporte INT AUTO_INCREMENT PRIMARY KEY, " + "ID_Proyecto INT, "
			+ "ID_Historial INT, " + "Costo_Horas DECIMAL(10, 2), " + "Costo_Dinero DECIMAL(10, 2), "
			+ "Fecha_Creacion DATE, " + "FOREIGN KEY (ID_Proyecto) REFERENCES Proyecto(ID_Proyecto), "
			+ "FOREIGN KEY (ID_Historial) REFERENCES Historial(ID_Historial)";

	public static final String HISTORIAL_SQL = "ID_Historial INT AUTO_INCREMENT PRIMARY KEY, " + "ID_Empleado INT, "
			+ "ID_Tarea INT, " + "Estado VARCHAR(50), " + "Horas_Trabajadas INT, " + "Fecha DATE, "
			+ "FOREIGN KEY (ID_Empleado) REFERENCES Empleado(ID_Empleado), "
			+ "FOREIGN KEY (ID_Tarea) REFERENCES Tarea(ID_Tarea)";

	// Array con las definiciones SQL
	public static final String[] ALL_TABLES_SQL = { EMPLEADO_SQL, PROYECTO_SQL, TAREA_SQL, HISTORIAL_SQL,
			AFILIACION_SQL, ASIGNACION_SQL, REPORTE_SQL };
	
}
