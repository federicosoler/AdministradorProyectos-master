package Empleado;

import java.util.List;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Utilidades.TableDefinitions;
import Exceptions.ServiceException;

public class EmpleadoPanel extends GenericPanel {
	private EmpleadoService empleadoService;
	private String[] nombreColumnas = TableDefinitions.EMPLEADO_COLUMNAS;
	private JTextField[] campos;

	public EmpleadoPanel(PanelManager panelManager) {
		super(panelManager);
		empleadoService = new EmpleadoService();

		// Inicializar campos (CENTER)
		campos = setupInputFields(nombreColumnas);

		// Configurar tabla (NORTH)
		initializePanel(nombreColumnas);
		readAll();
	}

	// Getter para campos
	@Override
	protected JTextField[] getCampos() {
		return campos;
	}

	// cRud
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Empleado> listaEmpleados = empleadoService.readAll();
			for (Empleado empleado : listaEmpleados) {
				Object[] row = { empleado.getIdEmpleado(), empleado.getNombre(), empleado.getApellido(),
						empleado.getCostoHora() };
				tableModel.addRow(row);
			}
		} catch (ServiceException e) {
			mostrarError("Error al leer empleados", e);
		}
	}

	// Crud
	@Override
	protected void create() {
		if (!esNumeroValido(campos[2].getText(), "costo por hora")) {
			return;
		}

		try {
			Empleado empleado = new Empleado();
			empleado.setNombre(campos[0].getText());
			empleado.setApellido(campos[1].getText());
			empleado.setCostoHora(Double.parseDouble(campos[2].getText()));

			empleadoService.create(empleado);
			refresh();

		} catch (ServiceException e) {
			mostrarError("Error al crear empleado", e);
		}
	}

	// crUd
	@Override
	protected void update() {
		if (!esNumeroValido(campos[2].getText(), "costo por hora")) {
			return;
		}

		try {
			int selectedRow = table.getSelectedRow();
			int idEmpleado = (int) tableModel.getValueAt(selectedRow, 0);
			Empleado empleado = new Empleado();
			empleado.setIdEmpleado(idEmpleado);
			empleado.setNombre(campos[0].getText());
			empleado.setApellido(campos[1].getText());
			empleado.setCostoHora(Double.parseDouble(campos[2].getText()));

			empleadoService.update(empleado);
			refresh();

		} catch (ServiceException e) {
			mostrarError("Error al actualizar empleado", e);
		}
	}

	// cruD
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idEmpleado = (int) tableModel.getValueAt(selectedRow, 0);
				empleadoService.delete(idEmpleado);
				refresh();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar empleado", e);
		}
	}
}