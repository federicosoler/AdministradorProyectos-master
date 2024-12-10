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

	// Componentes comunes para las tablas
	protected JTable table;
	protected DefaultTableModel tableModel;

	public GenericPanel(PanelManager panelManager) {
		this.panelManager = panelManager;
		setLayout(new BorderLayout());

		// Panel para la lista (NORTH)
		listPanel = new JPanel();
		listPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

		// Panel para los campos de entrada (CENTER)
		inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
		inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Panel para los botones (SOUTH)
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		add(listPanel, BorderLayout.NORTH);
		add(inputPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	// Método común para configurar la lista (NORTH)
	protected void setupTable(String[] columnNames) {
		tableModel = new DefaultTableModel(columnNames, 0);
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

	// Método común para cargar datos de la fila seleccionada a los campos
	protected void loadSelectedRowToFields(int selectedRow) {
		try {
			JTextField[] campos = getCampos();
			if (campos == null) {
				System.err.println("Error: campos no inicializados");
				return;
			}

			for (int i = 0; i < campos.length; i++) {
				if (campos[i] != null && i + 1 < tableModel.getColumnCount()) {
					Object value = tableModel.getValueAt(selectedRow, i + 1); // i + 1 para saltar la columna ID
					campos[i].setText(value != null ? String.valueOf(value) : "");
				}
			}
		} catch (Exception e) {
			System.err.println("Error al cargar datos en los campos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Método común para limpiar los campos
	protected void clearFields() {
		for (Component component : inputPanel.getComponents()) {
			if (component instanceof JTextField) {
				((JTextField) component).setText("");
			}
		}
		table.clearSelection();
	}

	// Método común para configurar los campos de entrada (CENTER)
	protected JTextField[] setupInputFields(String[] columnNames) {
		JTextField[] campos = new JTextField[columnNames.length - 1];
		for (int i = 1; i < columnNames.length; i++) {
			campos[i - 1] = new JTextField(20);
			inputPanel.add(new JLabel(columnNames[i] + ":"));
			inputPanel.add(campos[i - 1]);
		}
		return campos;
	}

	// Método común para configurar los botones (SOUTH)
	protected void setupButtons() {
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

		JButton eliminarButton = new JButton("Eliminar");
		eliminarButton.addActionListener(e -> {
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1) {
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

		JButton volverButton = new JButton("Volver al Menú");
		volverButton.addActionListener(e -> panelManager.mostrarPanel(new MenuPanel(panelManager)));
		buttonPanel.add(volverButton);
	}

	// Método para inicializar el panel con sus componentes
	protected void initializePanel(String[] columnNames) {
		setupTable(columnNames);
		setupButtons();
	}

	// Método para manejar errores de forma genérica
	protected void mostrarError(String mensaje, Exception e) {
		String mensajeError = e.getMessage() != null ? e.getMessage() : "Error desconocido";
		JOptionPane.showMessageDialog(this, mensaje + ": " + mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
	}

	// Método para validar si es número
	protected boolean esNumeroValido(String valor, String campo) {
		try {
			Double.parseDouble(valor);
			return true;
		} catch (NumberFormatException e) {
			mostrarError("El campo " + campo + " debe ser un número válido", e);
			return false;
		}
	}

	// Método para validar si es número entero
	protected boolean esEnteroValido(String valor, String campo) {
		try {
			Integer.parseInt(valor);
			return true;
		} catch (NumberFormatException e) {
			mostrarError("El campo " + campo + " debe ser un número entero", e);
			return false;
		}
	}

	// Método para refrescar el panel
	protected void refresh() {
		clearFields();
		readAll();
	}

	// Métodos abstractos que deben implementar las subclases
	protected abstract void readAll();

	protected abstract void create();

	protected abstract void update();

	protected abstract void delete();

	protected abstract JTextField[] getCampos();
}