package Reporte;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Exceptions.ServiceException;
import Proyecto.Proyecto;
import Proyecto.ProyectoService;
import Historial.Historial;
import Historial.HistorialService;

public class ReportePanel extends GenericPanel {
	private ReporteService reporteService;
	private ProyectoService proyectoService;
	private HistorialService historialService;

	private JComboBox<String> proyectoComboBox;

	private String[] nombreColumnas = { "ID", "Proyecto", "Costo Horas", "Costo Dinero", "Fecha Creación" };

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	public ReportePanel(PanelManager panelManager) {
		super(panelManager);
		reporteService = new ReporteService();
		proyectoService = new ProyectoService();
		historialService = new HistorialService();

		// Configurar tabla (NORTH)
		configurarTabla(nombreColumnas);

		// Configurar ComboBoxes (CENTER)
		configurarComboBoxes();

		// Configurar botones (SOUTH)
		configurarBotones();
		readAll();

		// Cargar datos iniciales
		llenarProyectoComboBox();

		readAll();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	private void configurarComboBoxes() {
		try {
			proyectoComboBox = new JComboBox<>();

			inputPanel.add(new JLabel("Proyecto:"));
			inputPanel.add(proyectoComboBox);

			inputPanel.revalidate();
			inputPanel.repaint();
		} catch (Exception e) {
			mostrarError("Error al configurar los ComboBoxes", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	private void llenarProyectoComboBox() {
		try {
			proyectoComboBox.removeAllItems();
			List<Proyecto> proyectos = proyectoService.readAll();
			for (Proyecto proyecto : proyectos) {
				proyectoComboBox.addItem(proyecto.getIdProyecto() + " - " + proyecto.getDescripcion());
			}
			proyectoComboBox.setSelectedItem(null);
		} catch (ServiceException e) {
			mostrarError("Error al cargar proyectos", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Reporte> reporteList = reporteService.readAll();
			for (Reporte reporte : reporteList) {
				tableModel.addRow(new Object[] { reporte.getIdReporte(), reporte.getProyecto().getDescripcion(),
						reporte.getCostoHoras(), reporte.getCostoDinero(),
						dateFormat.format(reporte.getFechaCreacion()) });
			}

			// Limpiar campos y comboboxes
			proyectoComboBox.setSelectedItem(null);
		} catch (ServiceException e) {
			mostrarError("Error al leer reportes", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void create() {
		try {
			if (proyectoComboBox.getSelectedItem() == null) {
				mostrarError("Debe seleccionar un proyecto", new Exception());
				return;
			}

			String proyectoSeleccionado = (String) proyectoComboBox.getSelectedItem();
			int idProyecto = Integer.parseInt(proyectoSeleccionado.split(" - ")[0]);

			// Calcular costos
			double costoHoras = 0;
			double costoDinero = 0;

			Historial historialRelacionado = null;

			// Obtener todas las tareas finalizadas del proyecto
			List<Historial> historialList = historialService.readAll();
			for (Historial historial : historialList) {
				if ("FINALIZADA".equals(historial.getEstado())) {
					if (historial.getTarea().getIdTarea() != 0) {
						Proyecto proyectoTarea = proyectoService.read(historial.getTarea().getIdTarea());
						if (proyectoTarea != null && proyectoTarea.getIdProyecto() == idProyecto) {
							costoHoras += historial.getHorasReales();
							costoDinero += historial.getHorasReales() * historial.getEmpleado().getCostoHora();
							historialRelacionado = historial; // Asociar el historial al proyecto
						}
					}
				}
			}

			// Validar si se encontró un historial relacionado
			if (historialRelacionado == null) {
				mostrarError("No hay historial asociado a tareas finalizadas para este proyecto", new Exception());
				return;
			}

			// Crear nuevo reporte
			Reporte reporte = new Reporte();
			Proyecto proyecto = new Proyecto();
			proyecto.setIdProyecto(idProyecto);
			reporte.setProyecto(proyecto);
			reporte.setHistorial(historialRelacionado);
			reporte.setCostoHoras(costoHoras);
			reporte.setCostoDinero(costoDinero);
			reporte.setFechaCreacion(new Date());

			// Guardar en la base de datos
			reporteService.create(reporte);

			// Actualizar tabla
			readAll();
		} catch (ServiceException | NumberFormatException e) {
			mostrarError("Error al crear el reporte", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idReporte = (int) tableModel.getValueAt(selectedRow, 0);
				reporteService.delete(idReporte);
				readAll();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar reporte", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// No hace falta
	@Override
	protected void update() {
		// No hace falta
	}

	// No hace falta
	@Override
	protected JTextField[] getCampos() {
		return new JTextField[0];
	}
}
