package Utilidades;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import Main.PanelManager;

public abstract class GenericPanel extends JPanel {
	protected PanelManager panelManager;
	protected JPanel listPanel;
	protected JPanel inputPanel;
	protected JPanel buttonPanel;
	protected JTable table;
	protected DefaultTableModel tableModel;

	public GenericPanel(PanelManager panelManager) {
		this.panelManager = panelManager;
		setLayout(new BorderLayout());
		configurarBordes(this);

		// Panel para la lista (NORTH)
		listPanel = new JPanel();
		configurarBordes(listPanel);

		// Panel para los campos de entrada (CENTER)
		inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
		configurarBordes(inputPanel);

		// Panel para los botones (SOUTH)
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		configurarBordes(buttonPanel);

		add(listPanel, BorderLayout.NORTH);
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void configurarBordes(JPanel panel) {
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Método para configurar la lista (NORTH)
	protected void configurarTabla(String[] nombreColumnas) {
		tableModel = new DefaultTableModel(nombreColumnas, 0);
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setPreferredSize(new Dimension(800, 200));
		listPanel.add(scrollPane);

		// Agregar listener para selección de fila
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow != -1) {
					loadSelectedRowToFields(selectedRow);
				}
			}
		});
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Método para configurar los campos de entrada (CENTER)
	protected JTextField[] configurarCamposEntrada(String[] nombreColumnas) {
		JTextField[] campos = new JTextField[nombreColumnas.length - 1]; // Excluyo la columna del ID
		for (int i = 1; i < nombreColumnas.length; i++) {
			campos[i - 1] = new JTextField(20);
			inputPanel.add(new JLabel(nombreColumnas[i] + ":"));
			inputPanel.add(campos[i - 1]);
		}
		return campos;
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Método para configurar los botones (SOUTH)
	protected void configurarBotones() {

		// Botón GUARDAR (Crud o crUd)
		JButton guardarButton = new JButton("Guardar");
		guardarButton.addActionListener(e -> {
			// Si hay una fila seleccionada, actualizar
			if (table.getSelectedRow() != -1) {
				update();
			} else {
				// Si no hay fila seleccionada, crear nuevo
				create();
			}
		});
		buttonPanel.add(guardarButton);

		// Botón ELIMINAR (cruD)
		JButton eliminarButton = new JButton("Eliminar");
		eliminarButton.addActionListener(e -> {			
			if (table.getSelectedRow() != -1) {
				int confirm = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este registro?",
						"Confirmar eliminación", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					delete();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Por favor, seleccione un registro para eliminar", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
		buttonPanel.add(eliminarButton);

		// Botón VOLVER AL MENÚ
		JButton volverButton = new JButton("Volver al Menú");
		volverButton.addActionListener(e -> panelManager.mostrarPanel(new MenuPanel(panelManager)));
		buttonPanel.add(volverButton);
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Método para cargar datos de la fila seleccionada a los campos
	protected void loadSelectedRowToFields(int selectedRow) {
		try {
			JTextField[] campos = getCampos();
			if (campos == null) {
				System.err.println("Error: campos no inicializados");
				return;
			}

			for (int i = 0; i < campos.length; i++) {
				if (campos[i] != null && i + 1 < tableModel.getColumnCount()) {
					Object value = tableModel.getValueAt(selectedRow, i + 1); // Excluyo la columna del ID
					campos[i].setText(value != null ? String.valueOf(value) : "");
				}
			}
		} catch (Exception e) {
			mostrarError("Error al cargar datos en los campos", e);
		}
	}

	// Método para mostrar en pantalla un diálogo de error
	protected void mostrarError(String mensaje, Exception e) {
		String mensajeError = e.getMessage() != null ? e.getMessage() : "Error desconocido";
		JOptionPane.showMessageDialog(this, mensaje + ": " + mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Método para limpiar campos pero no los labels (CENTER) y deselecciona y refresca la tabla (NORTH)
	protected void refresh() {
		for (Component component : inputPanel.getComponents()) {
			if (component instanceof JTextField) {
				((JTextField) component).setText("");
			}
		}
		table.clearSelection();
		readAll();
	}

	// ------------------------------------------------------------------------------------------------------------------------------
	// Métodos abstractos que obligatoriamente deben implementar las subclases
	// (CRUD)
	protected abstract void readAll();

	protected abstract void create();

	protected abstract void update();

	protected abstract void delete();

	protected abstract JTextField[] getCampos();
}