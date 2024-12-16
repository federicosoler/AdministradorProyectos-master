package Afiliacion;

import java.util.List;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Exceptions.ServiceException;
import Tarea.Tarea;
import Tarea.TareaService;
import Proyecto.Proyecto;
import Proyecto.ProyectoService;

public class AfiliacionPanel extends GenericPanel {
	private AfiliacionService afiliacionService;
	private TareaService tareaService;
	private ProyectoService proyectoService;

	private JComboBox<String> tareaComboBox;
	private JComboBox<String> proyectoComboBox;

	private String[] nombreColumnas = { "ID", "Tarea", "Proyecto" };

	public AfiliacionPanel(PanelManager panelManager) {
		super(panelManager);
		afiliacionService = new AfiliacionService();
		tareaService = new TareaService();
		proyectoService = new ProyectoService();

		// Configurar tabla (NORTH)
		configurarTabla(nombreColumnas);

		// Inicializar campos (CENTER)
		configurarComboBoxes();

		// Configurar botones (SOUTH)
		configurarBotones();
		readAll();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Configura los ComboBoxes de tarea y proyecto
	private void configurarComboBoxes() {
		try {
			// Crear etiquetas y ComboBoxes
			JLabel tareaLabel = new JLabel("Tarea:");
			JLabel proyectoLabel = new JLabel("Proyecto:");

			tareaComboBox = new JComboBox<>();
			proyectoComboBox = new JComboBox<>();

			inputPanel.add(proyectoLabel);
			inputPanel.add(proyectoComboBox);
			inputPanel.add(tareaLabel);
			inputPanel.add(tareaComboBox);

			// Llenar proyectos y configurar listener
			List<Proyecto> proyectos = proyectoService.readAll();
			for (Proyecto proyecto : proyectos) {
				proyectoComboBox.addItem(proyecto.getIdProyecto() + " - " + proyecto.getDescripcion());
			}

			proyectoComboBox.addActionListener(e -> {
				String proyectoSeleccionado = (String) proyectoComboBox.getSelectedItem();
				if (proyectoSeleccionado != null) {
					int idProyecto = Integer.parseInt(proyectoSeleccionado.split(" - ")[0]);
					actualizarTareaComboBox(idProyecto);
				} else {
					tareaComboBox.removeAllItems();
					tareaComboBox.setSelectedItem(null);
				}
			});

			inputPanel.revalidate();
			inputPanel.repaint();
		} catch (ServiceException e) {
			mostrarError("Error al configurar ComboBoxes", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Actualiza las tareas según el proyecto seleccionado
	private void actualizarTareaComboBox(int idProyecto) {
		try {
			tareaComboBox.removeAllItems();
			List<Afiliacion> afiliaciones = afiliacionService.readAll();
			List<Tarea> todasTareas = tareaService.readAll();

			for (Tarea tarea : todasTareas) {
				boolean estaAsignada = afiliaciones.stream()
						.anyMatch(afiliacion -> afiliacion.getProyecto().getIdProyecto() == idProyecto
								&& afiliacion.getTarea().getIdTarea() == tarea.getIdTarea());

				if (!estaAsignada) {
					tareaComboBox.addItem(tarea.getIdTarea() + " - " + tarea.getTitulo());
				}
			}
		} catch (ServiceException e) {
			mostrarError("Error al actualizar tareas", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Afiliacion> listaAfiliaciones = afiliacionService.readAll();
			for (Afiliacion afiliacion : listaAfiliaciones) {
				Object[] row = { afiliacion.getIdAfiliacion(), afiliacion.getTarea().getTitulo(),
						afiliacion.getProyecto().getDescripcion() };
				tableModel.addRow(row);
			}

			proyectoComboBox.setSelectedItem(null);
			tareaComboBox.removeAllItems();
			tareaComboBox.setSelectedItem(null);
		} catch (ServiceException e) {
			mostrarError("Error al leer afiliaciones", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void create() {
		try {
			if (tareaComboBox.getSelectedItem() == null || proyectoComboBox.getSelectedItem() == null) {
				mostrarError("Debe seleccionar una tarea y un proyecto", new Exception());
				return;
			}

			Afiliacion afiliacion = new Afiliacion();

			String tareaSeleccionada = (String) tareaComboBox.getSelectedItem();
			int idTarea = Integer.parseInt(tareaSeleccionada.split(" - ")[0]);
			Tarea tarea = new Tarea();
			tarea.setIdTarea(idTarea);
			afiliacion.setTarea(tarea);

			String proyectoSeleccionado = (String) proyectoComboBox.getSelectedItem();
			int idProyecto = Integer.parseInt(proyectoSeleccionado.split(" - ")[0]);
			Proyecto proyecto = new Proyecto();
			proyecto.setIdProyecto(idProyecto);
			afiliacion.setProyecto(proyecto);

			afiliacionService.create(afiliacion);
			readAll();
		} catch (ServiceException | NumberFormatException e) {
			mostrarError("Error al crear afiliación", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idAfiliacion = (int) tableModel.getValueAt(selectedRow, 0);
				afiliacionService.delete(idAfiliacion);
				readAll();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar afiliación", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void update() {
		// No hace falta
	}

	@Override
	protected JTextField[] getCampos() {
		// No se utilizan JTextFields en este panel
		return new JTextField[0];
	}
}
