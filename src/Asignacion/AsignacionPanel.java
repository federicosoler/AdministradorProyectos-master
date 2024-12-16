package Asignacion;

import java.util.List;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Exceptions.ServiceException;
import Empleado.Empleado;
import Empleado.EmpleadoService;
import Proyecto.Proyecto;
import Proyecto.ProyectoService;

public class AsignacionPanel extends GenericPanel {
	private AsignacionService asignacionService;
	private EmpleadoService empleadoService;
	private ProyectoService proyectoService;
	
	private JComboBox<String> empleadoComboBox;
	private JComboBox<String> proyectoComboBox;

	private String[] nombreColumnas = { "ID", "Nombre", "Apellido", "Proyecto" };

	public AsignacionPanel(PanelManager panelManager) {
		super(panelManager);
		asignacionService = new AsignacionService();
		empleadoService = new EmpleadoService();
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
	// Configura los ComboBoxes para empleado y proyecto
	private void configurarComboBoxes() {
		try {
			// Crear etiquetas y ComboBoxes
			JLabel empleadoLabel = new JLabel("Empleado:");
			JLabel proyectoLabel = new JLabel("Proyecto:");

			empleadoComboBox = new JComboBox<>();
			proyectoComboBox = new JComboBox<>();

			inputPanel.add(proyectoLabel);
			inputPanel.add(proyectoComboBox);
			inputPanel.add(empleadoLabel);
			inputPanel.add(empleadoComboBox);

			// Llenar proyectos y configurar listener
			List<Proyecto> proyectos = proyectoService.readAll();
			for (Proyecto proyecto : proyectos) {
				proyectoComboBox.addItem(proyecto.getIdProyecto() + " - " + proyecto.getDescripcion());
			}

			proyectoComboBox.addActionListener(e -> {
				String proyectoSeleccionado = (String) proyectoComboBox.getSelectedItem();
				if (proyectoSeleccionado != null) {
					int idProyecto = Integer.parseInt(proyectoSeleccionado.split(" - ")[0]);
					actualizarEmpleadoComboBox(idProyecto);
				} else {
					empleadoComboBox.removeAllItems(); // Limpiar empleados si no hay proyecto seleccionado
					empleadoComboBox.setSelectedItem(null);
				}
			});

			inputPanel.revalidate();
			inputPanel.repaint();
		} catch (ServiceException e) {
			mostrarError("Error al configurar ComboBoxes", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Actualiza dinámicamente el ComboBox de empleados según el proyecto seleccionado.
	private void actualizarEmpleadoComboBox(int idProyecto) {
		try {
			empleadoComboBox.removeAllItems();
			List<Asignacion> asignaciones = asignacionService.readAll();
			List<Empleado> todosEmpleados = empleadoService.readAll();

			for (Empleado empleado : todosEmpleados) {
				boolean estaAsignado = asignaciones.stream()
						.anyMatch(asignacion -> asignacion.getProyecto().getIdProyecto() == idProyecto
								&& asignacion.getEmpleado().getIdEmpleado() == empleado.getIdEmpleado());

				if (!estaAsignado) {
					empleadoComboBox.addItem(
							empleado.getIdEmpleado() + " - " + empleado.getNombre() + " " + empleado.getApellido());
				}
			}
		} catch (ServiceException e) {
			mostrarError("Error al actualizar empleados", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Asignacion> listaAsignaciones = asignacionService.readAll();
			for (Asignacion asignacion : listaAsignaciones) {
				Object[] row = { asignacion.getIdAsignacion(), asignacion.getEmpleado().getNombre(),
						asignacion.getEmpleado().getApellido(), asignacion.getProyecto().getDescripcion() };
				tableModel.addRow(row);
			}

			// Reiniciar selección de ComboBoxes
			proyectoComboBox.setSelectedItem(null);
			empleadoComboBox.removeAllItems();
			empleadoComboBox.setSelectedItem(null);
		} catch (ServiceException e) {
			mostrarError("Error al leer asignaciones", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void create() {
		try {
			if (empleadoComboBox.getSelectedItem() == null || proyectoComboBox.getSelectedItem() == null) {
				mostrarError("Debe seleccionar un empleado y un proyecto", new Exception());
				return;
			}

			Asignacion asignacion = new Asignacion();

			// Configurar empleado
			String empleadoSeleccionado = (String) empleadoComboBox.getSelectedItem();
			int idEmpleado = Integer.parseInt(empleadoSeleccionado.split(" - ")[0]);
			Empleado empleado = new Empleado();
			empleado.setIdEmpleado(idEmpleado);
			asignacion.setEmpleado(empleado);

			// Configurar proyecto
			String proyectoSeleccionado = (String) proyectoComboBox.getSelectedItem();
			int idProyecto = Integer.parseInt(proyectoSeleccionado.split(" - ")[0]);
			Proyecto proyecto = new Proyecto();
			proyecto.setIdProyecto(idProyecto);
			asignacion.setProyecto(proyecto);

			asignacionService.create(asignacion);
			readAll();
		} catch (ServiceException | NumberFormatException e) {
			mostrarError("Error al crear asignación", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idAsignacion = (int) tableModel.getValueAt(selectedRow, 0);
				asignacionService.delete(idAsignacion);
				readAll();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar asignación", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void update() {
		// No hace falta
	}

	@Override
	protected JTextField[] getCampos() {
		// Devuelve un arreglo vacío porque no hay JTextFields en este panel
		return new JTextField[0];
	}
}
