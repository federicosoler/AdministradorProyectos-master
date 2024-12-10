package Utilidades;

import java.awt.GridLayout;
import javax.swing.*;
import Main.PanelManager;

public class MenuPanel extends JPanel {
	private PanelManager panelManager;

	public MenuPanel(PanelManager panelManager) {
		this.panelManager = panelManager;

		setLayout(new GridLayout(TableDefinitions.ALL_TABLES_NAMES.length, 1, 10, 10));
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		for (String tableName : TableDefinitions.ALL_TABLES_NAMES) {
			JButton button = new JButton(tableName);
			button.addActionListener(e -> crearPanel(tableName));
			add(button);
		}
	}

	private void crearPanel(String tableName) {
		try {
			String className = tableName + "." + tableName + "Panel";
			Class<?> panelClass = Class.forName(className);
			JPanel panel = (JPanel) panelClass.getConstructor(PanelManager.class).newInstance(panelManager);
			panelManager.mostrarPanel(panel);
		} catch (Exception ex) {
			System.out.println("No se pudo crear el panel: " + tableName);
		}
	}
}
