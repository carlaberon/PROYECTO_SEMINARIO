package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
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
    private RolDTO rolActual;
    public VentanaResumen(IApi api) {

    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
//		 descomentar para que tome el idioma ingles (english)

		//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	
    	this.api = api;
    	this.usuarioActual = api.getUsuarioActual();
    	this.rolActual = api.getRol(usuarioActual.getUsername(), api.getProyectoActual().getId());
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

        JLabel appName = new JLabel(labels.getString("menu.proyecto1"));
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
            menuButton.setHorizontalAlignment(SwingConstants.LEFT); // Alineación izquierda
            menuButton.setMargin(new Insets(10, 10, 10, 10)); // Margen interno
            menuPanel.add(menuButton);

            // Agregar ActionListener solo al botón de "Configuración"
            if (item.equals("Configuración") || item.equals("Settings")) {
                menuButton.addActionListener(e -> {
                    // Por ejemplo, podrías abrir un nuevo panel de configuración:
                    if(rolActual.getNombre().equals("Administrador")) {
                    	abrirPanelConfiguracion();
                    	dispose();
                    } else {
        	            JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);

					}
                    
                });
            }
         // Agregar ActionListener solo al botón de "Volver o Back"
            if (item.equals("Volver") || item.equals("Return")) {
                menuButton.addActionListener(e -> {
                	new Inicio(api).setVisible(true);
                    dispose();
                });
            }
           
        }

        // Agregar el panel lateral al contentPane
        contentPane.add(menuPanel, BorderLayout.WEST);

        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new GridLayout(2, 2, 20,20));
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen alrededor del contenido

        JPanel descPanel = createPanel(labels.getString("menu.descripcionProyecto"),unproyecto.getDescripcion());
        centerPanel1.add(descPanel);

        JPanel estadoPanel = createPanel(labels.getString("menu.estadoProyecto"),unproyecto.isEstado());
        centerPanel1.add(estadoPanel);

        JPanel miembrosPanel = createPanel(labels.getString("menu.miembrosProyecto"), null);
        miembrosPanel.setLayout(new GridLayout(3,6,6,6));
        JButton btnMiembro = createButton(labels.getString("menu.agregarMiembro"), new Color(138, 102, 204));
        JButton btnVerMiembros = createButton(labels.getString("menu.verMiembros"), new Color(83, 82, 90));
        miembrosPanel.add(btnMiembro);
        miembrosPanel.add(btnVerMiembros);
        centerPanel1.add(miembrosPanel);
        btnMiembro.addActionListener(e -> {
            	
                if(rolActual.getNombre().equals("Administrador")) {
    					InvitarMiembro invitarMiembro = new InvitarMiembro(api);
    					invitarMiembro.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	                invitarMiembro.setVisible(true);
                } else {
    	            JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);
                }
            }
        );
        /**/
        btnVerMiembros.addActionListener(e -> {
  			ListaMiembros ventanaMiembros = new ListaMiembros(api);
  			ventanaMiembros.setVisible(true);
  			dispose();
          });
          

       
        miembrosPanel.add(btnVerMiembros);
        miembrosPanel.add(btnMiembro);
        centerPanel1.add(miembrosPanel);
        JPanel tareasPanel = createPanel(labels.getString("menu.tareas"), null);
        tareasPanel.setLayout(new GridLayout(3,6,6,6));
        JButton btnTarea = createButton(labels.getString("menu.agregarTarea"), new Color(138, 102, 204));
        JButton btnVerTareas = createButton(labels.getString("menu.verDetalles"), new Color(83, 82, 90));
        
        tareasPanel.add(btnVerTareas);
        tareasPanel.add(btnTarea);
        centerPanel1.add(tareasPanel);
        btnVerTareas.addActionListener(e -> {
			VentanaTareas ventanaTareas = new VentanaTareas(api);
			ventanaTareas.setVisible(true);
			dispose();
        }
    );
        // Agregar el panel principal al contentPane
        contentPane.add(centerPanel1, BorderLayout.CENTER);
        
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent e) {
        		new Inicio(api).setVisible(true);
        	}
		});
    }

    private void abrirPanelConfiguracion() {
        VentanaConfigurarProyecto ventanaConfig = new VentanaConfigurarProyecto(api);
        ventanaConfig.setVisible(true);
    }

    private JPanel createPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,6,6,6));
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


