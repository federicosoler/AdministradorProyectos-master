package Main;

import javax.swing.*;
import Utilidades.*;

public class PanelManager extends JFrame {
	private JPanel currentPanel;

	public PanelManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Administrador de Proyectos");

		// Mostrar el menú por defecto
		mostrarPanel(new MenuPanel(this));

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void mostrarPanel(JPanel panel) {
		// Remover el panel actual si existe
		if (currentPanel != null) {
			remove(currentPanel);
		}

		currentPanel = panel;
		add(currentPanel);
		pack();
		revalidate();
		repaint();
	}
}
