package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.NotificacionDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class Inicio extends JFrame {
    private IApi api;
    private JPanel proyectosListPanel;
    private UsuarioDTO usuarioActual;

    public Inicio(IApi api) {

        ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
        
        this.api = api;
        this.usuarioActual = api.getUsuarioActual();

        setTitle(labels.getString("ventana.inicio"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JLabel projectName = new JLabel(labels.getString("menu.bienvenida"));
        projectName.setForeground(Color.WHITE);
        projectName.setFont(new Font("Segoe UI", Font.BOLD, 18));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(projectName);
        menuBar.add(Box.createHorizontalGlue());

        JMenu accountMenu = new JMenu(usuarioActual.getUsername());
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JMenuItem logoutItem = new JMenuItem(labels.getString("menu.cerrarSesion"));
        JMenuItem confItem = new JMenuItem(labels.getString("menu.configurarCuenta"));

        accountMenu.add(confItem);
        accountMenu.add(logoutItem);

        menuBar.add(accountMenu);

        logoutItem.addActionListener(e -> System.exit(0));

        setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {labels.getString("menu.proyectos"), labels.getString("menu.actividad"), labels.getString("menu.calendario")};
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
        }
        getContentPane().add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(45, 45, 45));
        JLabel welcomeLabel = new JLabel(labels.getString("menu.bienvenida"));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPanel.add(welcomeLabel);
        

        JPanel rightPanel = new JPanel(new BorderLayout(6,6));
        rightPanel.setBackground(new Color(65, 62, 77));

        JLabel proyectosLabel = new JLabel(labels.getString("menu.proyectos"));
        proyectosLabel.setForeground(Color.WHITE);
        proyectosLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        proyectosListPanel = new JPanel();
        proyectosListPanel.setLayout(new GridLayout(10,1,6,6));
        proyectosListPanel.setBackground(new Color(65, 62, 77));

        List<ProyectoDTO> proyectos;
		try {
			proyectos = api.obtenerProyectos(usuarioActual.getUsername());
        proyectos.sort((p1, p2) -> Integer.compare(api.obtenerPrioridad(p1.getPrioridad()), 
                api.obtenerPrioridad(p2.getPrioridad())));
        proyectos.sort((p1, p2) -> {
            int prioridadComparacion = Integer.compare(api.obtenerPrioridad(p1.getPrioridad()), 
                                                       api.obtenerPrioridad(p2.getPrioridad()));
            if (prioridadComparacion != 0) {
                return prioridadComparacion;
            }
            return p1.getNombre().compareTo(p2.getNombre());
        });
        //STREAM MIS MODIFICACIONES
        proyectos.stream().map(proyecto -> {
            JButton proyectoButton = new JButton(proyecto.getNombre());
            proyectoButton.setForeground(Color.WHITE);
            proyectoButton.setBackground(new Color(65, 62, 77));
            proyectoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));

            proyectoButton.addActionListener(e -> {
                try {
                    api.setProyectoActual(proyecto.getId());
                    abrirVentanaResumen();
                    dispose();
                } catch (NotNullException | DataEmptyException ex) {
                    ex.printStackTrace(); //Tratar mejor la excepcion
                }
            });
            return proyectoButton;
        }).forEach(proyectosListPanel::add);

		} catch (NotNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); //Tratar mejor la excepcion
		} catch (DataEmptyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JPanel proyectosButtonsPanel = new JPanel();
        proyectosButtonsPanel.setBackground(new Color(65, 62, 77));
        proyectosButtonsPanel.setLayout(new BoxLayout(proyectosButtonsPanel, BoxLayout.Y_AXIS));

        JButton btnNuevoProyecto = new JButton(labels.getString("menu.agregarProyecto"));
        btnNuevoProyecto.addActionListener(e -> {
            abrirCrearProyecto();
            dispose();
        });

        JButton btnVerProyectos = new JButton(labels.getString("menu.verProyectos"));
        btnVerProyectos.addActionListener(e -> {
        	abrirListaProyectos();
        	dispose();
        });

        formatButton(btnNuevoProyecto);
        formatButton(btnVerProyectos);

        JPanel panelHorizontal = new JPanel();
        panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.Y_AXIS));
        panelHorizontal.setBackground(new Color(30, 30, 30));
        panelHorizontal.add(btnNuevoProyecto);

        proyectosButtonsPanel.add(panelHorizontal);
        proyectosButtonsPanel.add(btnVerProyectos);
        

        JPanel panelNotificaciones = new JPanel();
        panelNotificaciones.setLayout(new BoxLayout(panelNotificaciones, BoxLayout.Y_AXIS)); // Cambiar a BoxLayout para tamaño dinámico
        panelNotificaciones.setBackground(new Color(48, 48, 48));

        // Agregar el panelNotificaciones a un JScrollPane
        JScrollPane scroll = new JScrollPane(panelNotificaciones);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Scroll vertical solo si es necesario
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Desactivar scroll horizontal
        scroll.getVerticalScrollBar().setUnitIncrement(16); // Ajustar velocidad de scroll
        scroll.setBorder(null);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
       
        try {
			List<NotificacionDTO> notificaciones = api.obtenerNotificaciones(usuarioActual.getUsername());
			for (NotificacionDTO notificacionDTO : notificaciones) {
				JPanel panelPrueba1 = createPanel("Prueba", notificacionDTO.getDescripcion());
				panelPrueba1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Permitir que ocupe todo el ancho disponible
				panelPrueba1.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear al inicio del eje X
				panelNotificaciones.add(panelPrueba1);
				panelNotificaciones.add(Box.createVerticalStrut(10)); // Espacio entre notificaciones
			}
		} catch (NotNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); //Tratar mejor
		} catch (DataEmptyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        

        rightPanel.add(proyectosLabel, BorderLayout.NORTH);
        rightPanel.add(proyectosListPanel, BorderLayout.CENTER);
        rightPanel.add(proyectosButtonsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(scroll,BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        getContentPane().add(mainPanel);
        setLocationRelativeTo(null); // Centrar frame en la pantalla
    }

    private void formatButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void abrirCrearProyecto() {
    	CrearProyecto crearProyecto = new CrearProyecto(api); 
        crearProyecto.setVisible(true);
    }
    
    private void abrirListaProyectos() {
        ListaProyectos listaProyectos = new ListaProyectos(api);
        listaProyectos.setVisible(true); 
    }

    private void abrirVentanaResumen() {
        VentanaResumen ventanaResumen = new VentanaResumen(api); 
        ventanaResumen.setVisible(true); 
    }
    
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
    
	public static void main(String[] args)  {
		
		IApi api = new PersistenceApi();
		UsuarioDTO usuario = api.obtenerUsuario("ldifabio");

		api.setUsuarioActual(usuario.getUsername());
		Inicio inicio = new Inicio(api);
		inicio.setVisible(true);
	}

	
}
