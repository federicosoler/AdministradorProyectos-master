package Utilidades;

import java.awt.GridLayout;
import javax.swing.*;
import Main.PanelManager;

public class MenuPanel extends JPanel {
    private PanelManager panelManager;

    public MenuPanel(PanelManager panelManager) {
        this.panelManager = panelManager;
        configurarLayout();
        configurarBotones();
    }

    private void configurarLayout() {
        setLayout(new GridLayout(TableDefinitions.ALL_TABLES_NAMES.length, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void configurarBotones() {
        for (String nombreTabla : TableDefinitions.ALL_TABLES_NAMES) {
            JButton boton = new JButton(nombreTabla);
            boton.addActionListener(e -> {
                JPanel panel = panelManager.crearPanel(nombreTabla);
                panelManager.mostrarPanel(panel);
            });
            add(boton);
        }
    }
}