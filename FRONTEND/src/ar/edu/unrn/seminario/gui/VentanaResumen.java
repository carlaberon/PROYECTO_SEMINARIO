package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskQueryException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

public class VentanaResumen extends JFrame {

    private JPanel contentPane;
    private IApi api;
    private UsuarioDTO usuarioActual; 
    private ProyectoDTO unproyecto; 
    
    public VentanaResumen(IApi api) throws NotNullException, DataEmptyException {

    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 
//		 descomentar para que tome el idioma ingles (english)

		//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	
    	this.api = api;
    	this.usuarioActual = api.getUsuarioActual();
    	this.unproyecto = api.getProyectoActual();
        
        setTitle(labels.getString("menu.resumen"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 900, 600);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(unproyecto.getNombre());
        menuProyecto.setForeground(Color.WHITE);
        menuProyecto.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JMenuItem item1 = new JMenuItem("Opción 1");
        JMenuItem item2 = new JMenuItem("Opción 2");
        JMenuItem item3 = new JMenuItem("Opción 3");
        menuProyecto.add(item1);
        menuProyecto.add(item2);
        menuProyecto.add(item3);

        menuBar.add(menuProyecto);

        JLabel appName = new JLabel(labels.getString("menu.proyecto"));
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.add(appName);
        menuBar.add(centerPanel);

        JMenu accountMenu = new JMenu(usuarioActual.getUsername());
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem logoutItem = new JMenuItem(labels.getString("menu.cerrarSesion"));
        JMenuItem confItem = new JMenuItem(labels.getString("menu.configurarCuenta"));
        
        accountMenu.add(confItem);
        accountMenu.add(logoutItem);
        
        
        logoutItem.addActionListener(e -> System.exit(0));

        menuBar.add(accountMenu);

       
        this.setJMenuBar(menuBar);

     // Panel lateral (Menú)-----------------------------------------------------------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10)); // Espacio entre botones
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));
        
 

        String[] menuItems = {labels.getString("menu.resumen"),labels.getString("menu.progreso"),labels.getString("menu.plan1"),labels.getString("menu.calendario"), labels.getString("menu.miembros"), labels.getString("menu.configuracion"),labels.getString("menu.volver")};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item + " →");
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(83, 82, 90));
            menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setHorizontalAlignment(SwingConstants.LEFT);
            menuButton.setMargin(new Insets(10, 10, 10, 10));
            menuPanel.add(menuButton);

            // Agregar ActionListener solo al botón de "Configuración"
            if (item.equals("Configuración") || item.equals("Settings")) {
                menuButton.addActionListener(e -> {
                    abrirPanelConfiguracion();
                    dispose();
                });
            }
         // Agregar ActionListener solo al botón de "Volver o Back"
            if (item.equals("Volver") || item.equals("Return")) {
                menuButton.addActionListener(e -> {
                	try {
						new Inicio(api).setVisible(true);
					} catch (NotNullException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DataEmptyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    dispose();
                });
            }
           
        }
        contentPane.add(menuPanel, BorderLayout.WEST);

        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new GridLayout(3, 2, 10, 10));
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20)); 

        JPanel descPanel = createPanel(labels.getString("menu.descripcionProyecto"),unproyecto.getDescripcion());
        centerPanel1.add(descPanel);

        JPanel estadoPanel = createPanel(labels.getString("menu.estadoProyecto"),unproyecto.isEstado());
        centerPanel1.add(estadoPanel);

        JPanel planPanel = createPanel(labels.getString("menu.detallesPlan"), null);
        JButton btnPlan = createButton(labels.getString("menu.plan"), new Color(138, 102, 204));
        JButton btnVerPlan = createButton(labels.getString("menu.verPlan"), new Color(83, 82, 90));
        planPanel.add(btnPlan);
        planPanel.add(btnVerPlan);
        centerPanel1.add(planPanel);

        JPanel miembrosPanel = createPanel(labels.getString("menu.miembrosProyecto"), null);
        JButton btnMiembro = createButton(labels.getString("menu.agregarMiembro"), new Color(138, 102, 204));
        JButton btnVerMiembros = createButton(labels.getString("menu.verMiembros"), new Color(83, 82, 90));
        miembrosPanel.add(btnMiembro);
        miembrosPanel.add(btnVerMiembros);
        centerPanel1.add(miembrosPanel);
        btnMiembro.addActionListener(e -> {
                InvitarMiembro invitarMiembro = new InvitarMiembro();  
                invitarMiembro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                invitarMiembro.setVisible(true);  
            }
        );

        miembrosPanel.add(btnMiembro);
        miembrosPanel.add(btnVerMiembros);
        centerPanel1.add(miembrosPanel);

        JPanel tareasPanel = createPanel(labels.getString("menu.tareas"), null);
        JButton btnTarea = createButton(labels.getString("menu.agregarTarea"), new Color(138, 102, 204));
        JButton btnVerTareas = createButton(labels.getString("menu.verDetalles"), new Color(83, 82, 90));
        tareasPanel.add(btnTarea);
        tareasPanel.add(btnVerTareas);
        centerPanel1.add(tareasPanel);
        btnVerTareas.addActionListener(e -> {
				VentanaTareas ventanaTareas = null;
				try {
					ventanaTareas = new VentanaTareas(api);
				} catch (RuntimeException | NotNullException | DataEmptyException | InvalidDateException
						| TaskQueryException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ventanaTareas.setVisible(true);
				dispose();
        }
    );
        contentPane.add(centerPanel1, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent e) {
        		try {
					new Inicio(api).setVisible(true);
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
		});
    }
    
    private void abrirPanelConfiguracion() {
        VentanaConfigurarProyecto ventanaConfig = new VentanaConfigurarProyecto(api);
        ventanaConfig.setVisible(true);
    }

    private JPanel createPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(53, 52, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

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

    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }
}


