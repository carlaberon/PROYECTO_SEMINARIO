package ar.edu.unrn.seminario.gui;

import javax.swing.*;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.NotificacionDTO;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;

import ar.edu.unrn.seminario.exception.DataBaseFoundException;

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
    ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 
    
    public Inicio(IApi api) {

         
        
        this.api = api;
        this.usuarioActual = api.getUsuarioActual();

        setTitle(labels.getString("ventana.inicio"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setBounds(50, 50, 1200, 650);

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
        //
        mainPanel.setBackground(new Color(45, 44, 50)); //color del centro

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {labels.getString("menu.proyectos"), labels.getString("menu.actividad")};
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
                } catch (NotNullException | DataEmptyException e1) {
                	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (DataBaseConnectionException e2) {
                	JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				}
            });
            return proyectoButton;
        }).forEach(proyectosListPanel::add);

		} catch (NotNullException | DataEmptyException e1) {
        	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DataBaseConnectionException e2) {
        	JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		}
        JPanel proyectosButtonsPanel = new JPanel();
        proyectosButtonsPanel.setBackground(new Color(65, 62, 77));
 

        proyectosButtonsPanel.setLayout(new GridLayout(3,6,6,6));
       
  
        JButton btnNuevoProyecto = createButton(labels.getString("menu.agregarProyecto"), new Color(138, 102, 204));
        btnNuevoProyecto.addActionListener(e -> {
            abrirCrearProyecto();
            dispose();
        });

        JButton btnVerProyectos = new JButton(labels.getString("menu.verProyectos"));
        
        btnVerProyectos.addActionListener(e -> {
        	try {
				abrirListaProyectos();
			} catch (DataBaseConnectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	dispose();
        });

   
        formatButton(btnVerProyectos);

        proyectosButtonsPanel.add(btnVerProyectos);
        proyectosButtonsPanel.add(btnNuevoProyecto);

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
				JPanel panelPrueba1 = createPanel("Invitacion a proyecto", notificacionDTO.getDescripcion(),panelNotificaciones, 
						notificacionDTO.getUsername(),notificacionDTO.getIdProyecto(),notificacionDTO.getCodigoRol());
				panelPrueba1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60)); // Permitir que ocupe todo el ancho disponible
				panelPrueba1.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear al inicio del eje X
				panelNotificaciones.add(panelPrueba1);
				panelNotificaciones.add(Box.createVerticalStrut(10)); // Espacio entre notificaciones
			}
        } catch (NotNullException | DataEmptyException e1) {
        	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (DataBaseConnectionException e2) {
			JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
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
    
    private void abrirListaProyectos() throws DataBaseConnectionException {
        ListaProyectos listaProyectos = new ListaProyectos(api);
        listaProyectos.setVisible(true); 
    }

    private void abrirVentanaResumen() {
        VentanaResumen ventanaResumen = new VentanaResumen(api); 
        ventanaResumen.setVisible(true); 
    }
    
    private JPanel createPanel(String title, String subtitle, JPanel parentPanel, String username, int idProyecto, int codigoRol) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(53, 52, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        // Panel para el contenido de texto
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(53, 52, 60));

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPanel.add(label);

        if (subtitle != null) {
            JLabel subLabel = new JLabel(subtitle);
            subLabel.setForeground(Color.GRAY);
            subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            textPanel.add(subLabel);
        }

        panel.add(textPanel, BorderLayout.CENTER);

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(new Color(53, 52, 60));

        JButton acceptButton = new JButton("Aceptar");
        JButton rejectButton = new JButton("Rechazar");
        
        formatActionButton(acceptButton);
        formatActionButton(rejectButton);

        acceptButton.addActionListener(e -> {
        	try {
        	api.eliminarNotificacion(idProyecto, username);
        	api.invitarMiembro(username, idProyecto, codigoRol);
        	parentPanel.remove(panel); // Eliminar este panel del contenedor padre
            parentPanel.revalidate(); // Actualizar el contenedor para reflejar el cambio
            parentPanel.repaint();    // Volver a pintar el contenedor
				actualizarProyectos();
        	} catch (NotNullException | DataEmptyException e1) {
            	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (DataBaseConnectionException e2) {
				JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
        });
        
        // Acción para eliminar el panel al presionar "Rechazar"
        rejectButton.addActionListener(e -> {
        	try {
				api.eliminarNotificacion(idProyecto, username);
				parentPanel.remove(panel); // Eliminar este panel del contenedor padre
				parentPanel.revalidate(); // Actualizar el contenedor para reflejar el cambio
				parentPanel.repaint();    // Volver a pintar el contenedor
        	} catch (DataBaseConnectionException e1) {
				JOptionPane.showMessageDialog(null,labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
        });

        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);

        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }
    
    // Método para aplicar estilo a los botones
    private void formatActionButton(JButton button) {
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(83, 82, 90));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Ajustar padding interno
    }
    
  public void actualizarProyectos() throws NotNullException, DataEmptyException {
	  proyectosListPanel.removeAll(); // Limpiar el panel actual
  
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
				  } catch (NotNullException | DataEmptyException e1) {
	                	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
	              } catch (DataBaseConnectionException e2) {
	            	  JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				}
			  });
			  return proyectoButton;
		  }).forEach(proyectosListPanel::add);

	  } catch (NotNullException | DataEmptyException e1) {
      	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
      } catch (DataBaseConnectionException e2) {
    	  JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
	}
 
	  proyectosListPanel.revalidate(); // Actualizar el panel
	  proyectosListPanel.repaint();    // Repintar el panel
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
    
	public static void main(String[] args)  {
		
		IApi api = new PersistenceApi();
		UsuarioDTO usuario;
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
		try {
			usuario = api.obtenerUsuario("gabi");
			api.setUsuarioActual(usuario.getUsername());
			Inicio inicio = new Inicio(api);
			inicio.setVisible(true);
		} catch (DataBaseFoundException e1) {
			JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseConnectionException e1) {
			JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	
}
