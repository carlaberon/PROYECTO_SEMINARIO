package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.modelo.Usuario;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
public class VentanaResumen extends JFrame {

    private JPanel contentPane;
    private Usuario usuarioActual;
    
    private ProyectoDTO unproyecto;
    private IApi api;
    public VentanaResumen(IApi api, ProyectoDTO proyecto, Usuario usuarioActual) {

    	this.unproyecto = proyecto; 
    	this.api = api;
    	this.usuarioActual = usuarioActual;
        
        setTitle("");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 900, 600);

        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(unproyecto.getNombre());
        menuProyecto.setForeground(Color.WHITE);
        menuProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JMenuItem item1 = new JMenuItem("Opción 1");
        JMenuItem item2 = new JMenuItem("Opción 2");
        JMenuItem item3 = new JMenuItem("Opción 3");
        menuProyecto.add(item1);
        menuProyecto.add(item2);
        menuProyecto.add(item3);

        menuBar.add(menuProyecto);

        JLabel appName = new JLabel("LabProject");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.add(appName);
        menuBar.add(centerPanel);

        JMenu accountMenu = new JMenu(usuarioActual.getNombre());
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem logoutItem = new JMenuItem("Cerrar sesión");
        JMenuItem confItem = new JMenuItem("Configurar cuenta");
        
        accountMenu.add(confItem);
        accountMenu.add(logoutItem);
        
        
        logoutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });

        menuBar.add(accountMenu);

       
        this.setJMenuBar(menuBar);

     // Panel lateral (Menú)-----------------------------------------------------------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10)); // Espacio entre botones
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {"Resumen", "Progreso", "Plan", "Calendario", "Tareas", "Miembros", "Configuración"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item + " →");
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(83, 82, 90));
            menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setHorizontalAlignment(SwingConstants.LEFT); // Alineación izquierda
            menuButton.setMargin(new Insets(10, 10, 10, 10)); // Margen interno
            menuPanel.add(menuButton);

            // Agregar ActionListener solo al botón de "Configuración"
            if (item.equals("Configuración")) {
                menuButton.addActionListener(e -> {
                    // Por ejemplo, podrías abrir un nuevo panel de configuración:
                    abrirPanelConfiguracion();
                });
            }
        }
        
        
        
        // Agregar el panel lateral al contentPane
        contentPane.add(menuPanel, BorderLayout.WEST);

        

        // Panel principal (Centro)
        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new GridLayout(3, 2, 10, 10));
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen alrededor del contenido

        // Descripción del proyecto
        JPanel descPanel = createPanel("Descripción del proyecto", proyecto.getDescripcion());
        centerPanel1.add(descPanel);

        // Estado del proyecto
        JPanel estadoPanel = createPanel("Estado del proyecto", "No definido");
        centerPanel1.add(estadoPanel);

        // Detalles del plan
        JPanel planPanel = createPanel("Detalles del plan", null);
        JButton btnPlan = createButton("Plan +", new Color(138, 102, 204));
        JButton btnVerPlan = createButton("Ver plan", new Color(83, 82, 90));
        planPanel.add(btnPlan);
        planPanel.add(btnVerPlan);
        centerPanel1.add(planPanel);

        // Miembros del proyecto
        JPanel miembrosPanel = createPanel("Miembros del proyecto", null);
        JButton btnMiembro = createButton("Miembro +", new Color(138, 102, 204));
        JButton btnVerMiembros = createButton("Ver miembros", new Color(83, 82, 90));
        miembrosPanel.add(btnMiembro);
        miembrosPanel.add(btnVerMiembros);
        centerPanel1.add(miembrosPanel);
        btnMiembro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InvitarMiembro invitarMiembro = new InvitarMiembro();  // Crear una nueva instancia de la clase InvitarMiembro
                invitarMiembro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                invitarMiembro.setVisible(true);  // Mostrar la ventana de InvitarMiembro
            }
        });

        btnVerMiembros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListaMiembros listaMiembros = new ListaMiembros(api); //MODIFICADO X MI
                listaMiembros.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listaMiembros.setVisible(true);  
            }
        });

        miembrosPanel.add(btnMiembro);
        miembrosPanel.add(btnVerMiembros);
        centerPanel1.add(miembrosPanel);
        // Tareas
        JPanel tareasPanel = createPanel("Tareas", null);
        JButton btnTarea = createButton("Tarea +", new Color(138, 102, 204));
        JButton btnVerTareas = createButton("Ver detalles", new Color(83, 82, 90));
        tareasPanel.add(btnTarea);
        tareasPanel.add(btnVerTareas);
        centerPanel1.add(tareasPanel);
        btnVerTareas.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            String nombreProyecto = unproyecto.getNombre(); // Este método obtiene el nombre del proyecto seleccionado
            VentanaTareas ventanaTareas = new VentanaTareas(api, nombreProyecto, usuarioActual);
            ventanaTareas.setVisible(true);
        }
    });
        // Agregar el panel principal al contentPane
        contentPane.add(centerPanel1, BorderLayout.CENTER);
    }
    
    // Método para abrir el panel de configuración
    private void abrirPanelConfiguracion() {
        // Lógica para mostrar el panel de configuración
        // Puedes implementar esto como desees
        VentanaConfigurarProyecto ventanaConfig = new VentanaConfigurarProyecto(api,unproyecto);
        ventanaConfig.setVisible(true);
    }

    // Método auxiliar para crear paneles con título y diseño consistente
    private JPanel createPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(53, 52, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label);

        if (subtitle != null) {
            JLabel subLabel = new JLabel(subtitle);
            subLabel.setForeground(Color.GRAY);
            subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panel.add(subLabel);
        }

        return panel;
    }

    // Método para crear botones con estilo
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }


}
