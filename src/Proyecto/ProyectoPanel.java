package Proyecto;

import java.util.List;
import javax.swing.*;
import Main.PanelManager;
import Utilidades.GenericPanel;
import Utilidades.TableDefinitions;
import Exceptions.ServiceException;

public class ProyectoPanel extends GenericPanel {
	private ProyectoService proyectoService;
	private String[] nombreColumnas = TableDefinitions.PROYECTO_COLUMNAS;
	private JTextField[] campos;

	public ProyectoPanel(PanelManager panelManager) {
		super(panelManager);
		proyectoService = new ProyectoService();

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

	// cRud - Read All
	@Override
	protected void readAll() {
		tableModel.setRowCount(0);
		try {
			List<Proyecto> listaProyectos = proyectoService.readAll();
			for (Proyecto proyecto : listaProyectos) {
				Object[] row = { proyecto.getIdProyecto(), proyecto.getDescripcion() };
				tableModel.addRow(row);
			}
		} catch (ServiceException e) {
			mostrarError("Error al leer proyectos", e);
		}
	}

	// Crud - Create
	@Override
	protected void create() {
		try {
			Proyecto proyecto = new Proyecto();
			proyecto.setDescripcion(campos[0].getText());

			proyectoService.create(proyecto);
			refresh();
		} catch (ServiceException e) {
			mostrarError("Error al crear proyecto", e);
		}
	}

	// crUd - Update
	@Override
	protected void update() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				Proyecto proyecto = new Proyecto();
				proyecto.setIdProyecto(Integer.parseInt(campos[0].getText()));
				proyecto.setDescripcion(campos[1].getText());

				proyectoService.update(proyecto);
				refresh();
			}
		} catch (ServiceException e) {
			mostrarError("Error al actualizar proyecto", e);
		}
	}

	// cruD - Delete
	@Override
	protected void delete() {
		try {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
				int idProyecto = (int) tableModel.getValueAt(selectedRow, 0);

				proyectoService.delete(idProyecto);
				refresh();
			}
		} catch (ServiceException e) {
			mostrarError("Error al eliminar proyecto", e);
		}
	}
}
