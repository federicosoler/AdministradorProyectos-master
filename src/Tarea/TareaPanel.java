package Tarea;

import java.util.List;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Utilidades.TableDefinitions;
import Utilidades.Validador;
import Exceptions.ServiceException;

public class TareaPanel extends GenericPanel {
	private TareaService tareaService;
	private String[] nombreColumnas = TableDefinitions.TAREA_COLUMNAS;
	private JTextField[] campos;

	public TareaPanel(PanelManager panelManager) {
		super(panelManager);
		tareaService = new TareaService();

		// Configurar tabla (NORTH)
		configurarTabla(nombreColumnas);

		// Configurar campos (CENTER)
		campos = configurarCamposEntrada(nombreColumnas);

		// Configurar botones (SOUTH)
		configurarBotones();
		readAll();
	}

	// Getter para campos
	@Override
	protected JTextField[] getCampos() {
		return campos;
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// cRud
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Tarea> listaTareas = tareaService.readAll();
			for (Tarea tarea : listaTareas) {
				Object[] row = { tarea.getIdTarea(), tarea.getTitulo(), tarea.getDescripcion(),
						tarea.getHorasEstimadas() };
				tableModel.addRow(row);
			}
		} catch (ServiceException e) {
			mostrarError("Error al leer tareas", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Crud
	@Override
	protected void create() {
		if (!Validador.esEnteroValido(campos[2].getText(), "horas estimadas")) {
			return;
		}

		try {
			Tarea tarea = new Tarea();
			tarea.setTitulo(campos[0].getText());
			tarea.setDescripcion(campos[1].getText());
			tarea.setHorasEstimadas(Integer.parseInt(campos[2].getText()));

			tareaService.create(tarea);
			refresh();

		} catch (ServiceException e) {
			mostrarError("Error al crear tarea", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// crUd
	@Override
	protected void update() {
		if (!Validador.esEnteroValido(campos[2].getText(), "horas estimadas")) {
			return;
		}

		try {
			int selectedRow = table.getSelectedRow();
			int idTarea = (int) tableModel.getValueAt(selectedRow, 0);

			Tarea tarea = new Tarea();
			tarea.setIdTarea(idTarea);
			tarea.setTitulo(campos[0].getText());
			tarea.setDescripcion(campos[1].getText());
			tarea.setHorasEstimadas(Integer.parseInt(campos[2].getText()));

			tareaService.update(tarea);
			refresh();

		} catch (ServiceException e) {
			mostrarError("Error al actualizar tarea", e);
		}
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// cruD
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idTarea = (int) tableModel.getValueAt(selectedRow, 0);
				tareaService.delete(idTarea);
				refresh();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar tarea", e);
		}
	}
}
