package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.dto.RolDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Inicio extends JFrame {

    private JFrame frame;
    private IApi api;
    private JPanel proyectosListPanel;
    private UsuarioDTO usuarioActual;
    
    public Inicio(IApi api) throws NotNullException, DataEmptyException {
    	this.api = api;
    	this.usuarioActual = api.getUsuarioActual();
    	
        frame = new JFrame("LabProject");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JLabel projectName = new JLabel("LabProject");
        projectName.setForeground(Color.WHITE);
        projectName.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(projectName);
        menuBar.add(Box.createHorizontalGlue());

        JMenu accountMenu = new JMenu(usuarioActual.getUsername()); 
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem logoutItem = new JMenuItem("Cerrar sesión");
        JMenuItem confItem = new JMenuItem("Configurar Cuenta");
        JMenuItem verTodosProyectosMenuItem = new JMenuItem("Ver todos los proyectos");

        accountMenu.add(confItem);
        accountMenu.add(logoutItem);
        accountMenu.add(verTodosProyectosMenuItem);
        menuBar.add(accountMenu);

        logoutItem.addActionListener(e -> System.exit(0));

        verTodosProyectosMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					abrirListaProyectos();
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // Abrir la ventana de proyectos desde el menú
            }
        });

        frame.setJMenuBar(menuBar);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.DARK_GRAY);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {"Proyectos", "Actividad", "Calendario"};
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
        frame.getContentPane().add(menuPanel, BorderLayout.WEST);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(45, 45, 45));
        JLabel welcomeLabel = new JLabel("¡Bienvenido a LabProject!");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPanel.add(welcomeLabel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(30, 30, 30));

        JLabel proyectosLabel = new JLabel("Proyectos");
        proyectosLabel.setForeground(Color.GRAY);
        proyectosLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        proyectosListPanel = new JPanel();
        proyectosListPanel.setLayout(new BoxLayout(proyectosListPanel, BoxLayout.Y_AXIS));
        proyectosListPanel.setBackground(new Color(30, 30, 30));

        //BACK -> DTO -> FRONT
        List<ProyectoDTO> proyectos = api.obtenerProyectos(usuarioActual.getUsername());
        
        if(!proyectos.isEmpty()) {
        	for (ProyectoDTO proyecto : proyectos) {
        		JButton proyectoButton = new JButton(proyecto.getNombre());
        		proyectoButton.setForeground(Color.GRAY);
        		proyectoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        		
        		proyectoButton.addActionListener( new ActionListener () {

        			@Override
        			public void actionPerformed(ActionEvent e) {

							try {
								api.setProyectoActual(proyecto.getNombre());
							} catch (NotNullException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (DataEmptyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								abrirVentanaResumen();
							} catch (NotNullException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							} catch (DataEmptyException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
        			}
        					
            });
            
            proyectosListPanel.add(proyectoButton);
           
            
        }

        JPanel proyectosButtonsPanel = new JPanel();
        proyectosButtonsPanel.setBackground(new Color(30, 30, 30));
        proyectosButtonsPanel.setLayout(new BoxLayout(proyectosButtonsPanel, BoxLayout.Y_AXIS));
        
        JButton btnNuevoProyecto = new JButton("Proyecto +");
        btnNuevoProyecto.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		abrirCrearProyecto();
        	}
        });
        
        JButton btnVerProyectos = new JButton("Ver todos los proyectos");
        btnVerProyectos.addActionListener(e -> {
        	try {
        		abrirListaProyectos();
        	} catch (NotNullException e1) {
        		// TODO Auto-generated catch block
        		e1.printStackTrace();
        	} catch (DataEmptyException e1) {
        		// TODO Auto-generated catch block
        		e1.printStackTrace();
        	}
        }); // Acción para el botón
        
        JButton actualizarProyectos = new JButton("Actualizar");
        actualizarProyectos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					actualizarProyectos();
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
        formatButton(actualizarProyectos);
        formatButton(btnNuevoProyecto);
        formatButton(btnVerProyectos);
        
        JPanel panelHorizontal = new JPanel();
        panelHorizontal.setLayout(new BoxLayout(panelHorizontal, BoxLayout.Y_AXIS)); // Configuración horizontal
        panelHorizontal.setBackground(new Color(30, 30, 30));
        panelHorizontal.add(actualizarProyectos);
        panelHorizontal.add(btnNuevoProyecto);
       
        proyectosButtonsPanel.add(panelHorizontal);
        proyectosButtonsPanel.add(btnVerProyectos);
        

        rightPanel.add(proyectosLabel, BorderLayout.NORTH);
        rightPanel.add(proyectosListPanel, BorderLayout.CENTER);
        rightPanel.add(proyectosButtonsPanel, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        frame.getContentPane().add(mainPanel);

        frame.setVisible(true);
    }}

    private void formatButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void abrirCrearProyecto() {
    	CrearProyecto crearProyecto = new CrearProyecto(api); // Crear una instancia de ListaProyectos
        crearProyecto.setVisible(true); // Hacer visible la ventana de proyectos
    }
    
    private void abrirListaProyectos() throws NotNullException, DataEmptyException {
        ListaProyectos listaProyectos = new ListaProyectos(api); // Crear una instancia de ListaProyectos
        listaProyectos.setVisible(true); // Hacer visible la ventana de proyectos
    }

    private void abrirVentanaResumen() throws NotNullException, DataEmptyException {
        VentanaResumen ventanaResumen = new VentanaResumen(api); // Crear una instancia de VentanaResumen
        ventanaResumen.setVisible(true); // Hacer visible la ventana de resumen
    }

    public void actualizarProyectos() throws NotNullException, DataEmptyException {
        proyectosListPanel.removeAll(); // Limpiar el panel actual
        
        List<ProyectoDTO> proyectos = api.obtenerProyectos(usuarioActual.getUsername()); // Obtener los proyectos actualizados
        

        for (ProyectoDTO proyecto : proyectos) {
            JButton proyectoButton = new JButton(proyecto.getNombre());
            proyectoButton.setForeground(Color.GRAY);
            proyectoButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            
            proyectoButton.addActionListener( new ActionListener () {

				@Override
				public void actionPerformed(ActionEvent e) {


						try {
							api.setProyectoActual(proyecto.getId());
						} catch (NotNullException e1) {
							//msj front-end
							e1.printStackTrace();
						} catch (DataEmptyException e1) {
							//msj front-end
							e1.printStackTrace();
						}
		

					try {
						abrirVentanaResumen();

					} catch (NotNullException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DataEmptyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
            	
            });
            
            proyectosListPanel.add(proyectoButton);
        }
        
        proyectosListPanel.revalidate(); // Actualizar el panel
        proyectosListPanel.repaint();    // Repintar el panel
    }


	public void proyectoEliminado() throws NotNullException, DataEmptyException {
		actualizarProyectos(); //Cuando se elimina un proyecto activa el metodo actualizarProyectos para actualizar Inicio
	}

    
	public static void main(String[] args) throws NotNullException, DataEmptyException, InvalidDateException{
		
		IApi api = new PersistenceApi();
		UsuarioDTO usuario = api.obtenerUsuario("ldifabio");

		api.setUsuarioActual(usuario.getUsername());
		new Inicio(api);
	}

	
}

