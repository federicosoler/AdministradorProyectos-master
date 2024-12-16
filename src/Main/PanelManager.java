package Main;

import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import Utilidades.*;

public class PanelManager extends JFrame {
    private JPanel currentPanel;
    private MenuPanel menuPanel;
    private Map<String, JPanel> mapaPaneles = new HashMap<>();

    public PanelManager() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Administrador de Proyectos");

        menuPanel = new MenuPanel(this);
        mostrarPanel(menuPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JPanel crearPanel(String nombrePanel) {
        if (!mapaPaneles.containsKey(nombrePanel)) {
            try {
                String className = nombrePanel + "." + nombrePanel + "Panel";
                Class<?> panelClass = Class.forName(className);
                JPanel panel = (JPanel) panelClass.getConstructor(PanelManager.class).newInstance(this);
                mapaPaneles.put(nombrePanel, panel);
            } catch (Exception ex) {
                System.out.println("No se pudo crear el panel: " + nombrePanel);
            }
        }
        return mapaPaneles.get(nombrePanel);
    }

    public void mostrarPanel(JPanel panel) {
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