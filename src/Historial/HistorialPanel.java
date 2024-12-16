package Historial;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Exceptions.ServiceException;
import Empleado.Empleado;
import Empleado.EmpleadoService;
import Tarea.Tarea;
import Tarea.TareaService;

public class HistorialPanel extends GenericPanel {
	private HistorialService historialService;
	private TareaService tareaService;
	private EmpleadoService empleadoService;

	private JComboBox<String> tareaComboBox;
	private JComboBox<String> empleadoComboBox;
	private JComboBox<String> estadoComboBox;

	private String[] nombreColumnas = { "ID", "Tarea", "Empleado", "Estado", "Fecha Ajuste", "Horas Reales" };

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	private JTextField horasRealesField;

	public HistorialPanel(PanelManager panelManager) {
		super(panelManager);
		historialService = new HistorialService();
		tareaService = new TareaService();
		empleadoService = new EmpleadoService();

		// Configurar tabla (NORTH)
		configurarTabla(nombreColumnas);

		// Configurar ComboBoxes (CENTER)
		configurarComboBoxes();

		// Configurar botones (SOUTH)
		configurarBotones();
		readAll();

		// Cargar datos iniciales
		llenarTareaComboBox();
		llenarEmpleadoComboBox();
		estadoComboBox.setSelectedItem(null);

		readAll();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	private void configurarComboBoxes() {
		try {
			// Crear ComboBoxes y Campo para Horas Reales
			tareaComboBox = new JComboBox<>();
			empleadoComboBox = new JComboBox<>();
			estadoComboBox = new JComboBox<>(new String[] { "EN CURSO", "FINALIZADA" });
			horasRealesField = new JTextField(10);

			// Configurar layout
			inputPanel.add(new JLabel("Tarea:"));
			inputPanel.add(tareaComboBox);
			inputPanel.add(new JLabel("Empleado Responsable:"));
			inputPanel.add(empleadoComboBox);
			inputPanel.add(new JLabel("Estado:"));
			inputPanel.add(estadoComboBox);
			inputPanel.add(new JLabel("Horas Reales (solo para FINALIZADA):"));
			inputPanel.add(horasRealesField);

			inputPanel.revalidate();
			inputPanel.repaint();
		} catch (Exception e) {
			mostrarError("Error al configurar los campos de entrada", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	private void llenarTareaComboBox() {
		try {
			tareaComboBox.removeAllItems();
			List<Tarea> tareas = tareaService.readAll();
			for (Tarea tarea : tareas) {
				tareaComboBox.addItem(tarea.getIdTarea() + " - " + tarea.getTitulo());
			}
			tareaComboBox.setSelectedItem(null); // Sin selección inicial
		} catch (ServiceException e) {
			mostrarError("Error al cargar tareas", e);
		}
	}

	private void llenarEmpleadoComboBox() {
		try {
			empleadoComboBox.removeAllItems();
			List<Empleado> empleados = empleadoService.readAll();
			for (Empleado empleado : empleados) {
				empleadoComboBox.addItem(
						empleado.getIdEmpleado() + " - " + empleado.getNombre() + " " + empleado.getApellido());
			}
			empleadoComboBox.setSelectedItem(null); // Sin selección inicial
		} catch (ServiceException e) {
			mostrarError("Error al cargar empleados", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Historial> historialList = historialService.readAll();
			for (Historial historial : historialList) {
				tableModel.addRow(new Object[] { historial.getIdHistorial(), historial.getTarea().getTitulo(),
						historial.getEmpleado().getNombre() + " " + historial.getEmpleado().getApellido(),
						historial.getEstado(), dateFormat.format(historial.getFechaAjuste()),
						historial.getHorasReales() });
			}

			// Limpiar campos y comboboxes
			tareaComboBox.setSelectedItem(null);
			empleadoComboBox.setSelectedItem(null);
			estadoComboBox.setSelectedItem(null);
			horasRealesField.setText("");
		} catch (ServiceException e) {
			mostrarError("Error al leer historial", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void create() {
		try {
			// Validar selección de tarea y empleado
			if (tareaComboBox.getSelectedItem() == null || empleadoComboBox.getSelectedItem() == null) {
				mostrarError("Debe seleccionar una tarea y un empleado responsable", new Exception());
				return;
			}

			// Validar estado
			String estadoSeleccionado = (String) estadoComboBox.getSelectedItem();
			if ("FINALIZADA".equals(estadoSeleccionado) && horasRealesField.getText().isEmpty()) {
				mostrarError("Debe ingresar las horas reales para cambiar el estado a FINALIZADA", new Exception());
				return;
			}

			// Crear nueva entrada en el historial
			Historial historial = new Historial();

			// Obtener tarea seleccionada
			String tareaSeleccionada = (String) tareaComboBox.getSelectedItem();
			int idTarea = Integer.parseInt(tareaSeleccionada.split(" - ")[0]);
			Tarea tarea = new Tarea();
			tarea.setIdTarea(idTarea);
			historial.setTarea(tarea);

			// Obtener empleado seleccionado
			String empleadoSeleccionado = (String) empleadoComboBox.getSelectedItem();
			int idEmpleado = Integer.parseInt(empleadoSeleccionado.split(" - ")[0]);
			Empleado empleado = new Empleado();
			empleado.setIdEmpleado(idEmpleado);
			historial.setEmpleado(empleado);

			// Establecer estado, fecha de ajuste y horas reales
			historial.setEstado(estadoSeleccionado);
			historial.setFechaAjuste(new Date());
			historial.setHorasReales(
					"FINALIZADA".equals(estadoSeleccionado) ? (int) Double.parseDouble(horasRealesField.getText()) : 0);

			// Guardar en la base de datos
			historialService.create(historial);

			// Actualizar tabla
			readAll();
		} catch (ServiceException | NumberFormatException e) {
			mostrarError("Error al crear el historial", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idHistorial = (int) tableModel.getValueAt(selectedRow, 0);
				historialService.delete(idHistorial);
				readAll();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar historial", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	@Override
	protected void update() {
		// No hace falta
	}

	@Override
	protected JTextField[] getCampos() {
		return new JTextField[0];
	}
}