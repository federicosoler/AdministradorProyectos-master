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

    // Crear un nuevo panel por reflexión y devolverlo (Ejemplo: "Empleado")
    public JPanel crearPanel(String nombrePanel) {  
        if (!mapaPaneles.containsKey(nombrePanel)) {
            try {

                // Armo un String con la clase y paquete específicos "Empleado.EmpleadoPanel"
                String className = nombrePanel + "." + nombrePanel + "Panel";

                // REFLEXIÓN -> Configurar Class<?> como tipo EmpleadoPanel
                Class<?> panelClass = Class.forName(className); 

                // UPCASTING -> Trato EmpleadoPanel (clase hija) como JPanel (clase padre) para guardarlo en el mapa y correr mostrarPanel()              
                JPanel panel = (JPanel) panelClass.getConstructor(PanelManager.class).newInstance(this); 

                // Guardar el nuevo JPanel en el mapa
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