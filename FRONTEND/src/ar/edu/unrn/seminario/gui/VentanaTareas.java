package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseEliminationException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class VentanaTareas extends JFrame {

    private JPanel contentPane;
    private JTable table;
	DefaultTableModel modelo;
	private IApi api;
	JButton botonModificar;
	JButton botonEliminar;
	private UsuarioDTO usuarioActual;
    private ProyectoDTO unproyecto; 
    private RolDTO rolActual;
    public VentanaTareas(IApi api) {
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
//		 descomentar para que tome el idioma ingles (english)

		//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	
    	
    	this.api = api; 
    	this.usuarioActual = api.getUsuarioActual();
    	this.unproyecto = api.getProyectoActual();
    	try {
			this.rolActual = api.getRol(usuarioActual.getUsername(), unproyecto.getId());
		} catch (DataBaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	setTitle(labels.getString("menu.tareas"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(unproyecto.getNombre());
        menuProyecto.setForeground(Color.WHITE);
        menuProyecto.setFont(new Font("Segoe UI", Font.BOLD, 18));
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
        
        // Panel lateral (Menú)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {labels.getString("menu.tareas"), labels.getString("menu.volver")};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(83, 82, 90));
            menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setHorizontalAlignment(SwingConstants.LEFT);
            menuButton.setMargin(new Insets(10, 10, 10, 10));
            menuPanel.add(menuButton);
         // Agregar ActionListener solo al botón de "Volver o Back"
            if (item.equals("Volver") || item.equals("Return")) {
                menuButton.addActionListener(e -> {
                	new VentanaResumen(api).setVisible(true);
                    dispose();
                });
            }
        }
    
        contentPane.add(menuPanel, BorderLayout.WEST);
        
        //Panel central 
        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new GridLayout(1, 1, 10, 10));
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(centerPanel1, BorderLayout.CENTER);
    
        JPanel descPanel = createPanel("","");
        descPanel.setLayout(new BorderLayout());
        
        JScrollPane scrollPane = new JScrollPane();
        descPanel.add(scrollPane, BorderLayout.CENTER);

		// Personalización de la tabla
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };

        // Personalización del encabezado de la tabla
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(83, 82, 90)); // Color de fondo del encabezado
        table.getTableHeader().setForeground(Color.WHITE); // Color del texto del encabezado
        table.getTableHeader().setPreferredSize(new Dimension(100, 40)); // Altura del encabezado

        // Personalización de la tabla
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40); // Altura de las filas
        table.setBackground(new Color(45, 44, 50)); // Fondo de la tabla
        table.setForeground(Color.WHITE); // Color de texto de las celdas
        table.setSelectionBackground(new Color(138, 102, 204)); // Fondo de la selección
        table.setSelectionForeground(Color.WHITE); // Texto de la selección

        // Mostrar las líneas de cuadrícula
        table.setGridColor(new Color(83, 82, 90)); // Color de la cuadrícula
        table.setShowGrid(true);

        // Modelo de la tabla
		String[] titulos = { labels.getString("menu.Id"),labels.getString("menu.nombreTabla"),labels.getString("menu.estado"),labels.getString("menu.descripcion"), labels.getString("menu.usuarioAsignado"),labels.getString("mensaje.prioridad"), labels.getString("menu.fechaInicio"), labels.getString("menu.fechaFin") };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		
			
		List<TareaDTO> tareas;
		try {
			tareas = api.obtenerTareasPorProyecto(unproyecto.getId());
			tareas.sort((t1, t2) -> {
            int prioridadComparacion = Integer.compare(api.obtenerPrioridad(t1.getPriority()), 
                                                       api.obtenerPrioridad(t2.getPriority()));
            if (prioridadComparacion != 0) {
                return prioridadComparacion;
            }
            return t1.getName().compareTo(t2.getName());
        });
		
		
		modelo.setRowCount(0); // Limpiar el modelo antes de agregar nuevas filas
		
		for (TareaDTO t : tareas) {
		    modelo.addRow(new Object[] {
		    	t.getId(),
		        t.getName(),
		        labels.getString("estado.proyecto"), // Modifica el estado a una cadena legible
		        t.getDescription(),
		        t.getUser(),
		        labels.getString(api.traducirPrioridad(t.getPriority())), 
		        t.getInicio(),
		        t.getFin()
		    });
		}
		
		} catch (InvalidDateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(); //Tratar mejor la excepcion
		} catch (NotNullException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DataEmptyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DataBaseConnectionException e1) {
			JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		table.setModel(modelo);
		scrollPane.setViewportView(table);
		scrollPane.getViewport().setBackground(new Color(45, 44, 50)); // Fondo del scrollPane
      	
		// Ocultar la columna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Habilitar botones
				habilitarBotones(true);

			}
		});
		
		// Configuración del botón "Tarea +" en la esquina superior derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton btnTarea = createButton(labels.getString("menu.agregarTarea"), new Color(138, 102, 204));
        btnTarea.addActionListener(e -> {
				
				if(!(rolActual.getNombre().equals("Observador"))) {
					CrearTarea crearTarea;
					crearTarea = new CrearTarea(api);
					crearTarea.setVisible(true);
					dispose();
				
				}else {
					JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);
				}
			}
		);
        buttonPanel.add(btnTarea);
        descPanel.add(buttonPanel, BorderLayout.NORTH); // Coloca el botón en el norte (arriba)
        centerPanel1.add(descPanel);
        
       //Configuración de los botones "Modificar" y "Eliminar" tarea
      JPanel botones = new JPanel(new FlowLayout());
      botones.setOpaque(false);
      
      botonModificar = createButton(labels.getString("boton.modificar"), new Color(138, 102, 204));
      botonEliminar = createButton(labels.getString("boton.eliminar"), new Color(138, 102, 204));
      botones.add(botonModificar);
      botones.add(botonEliminar);
      descPanel.add(botones, BorderLayout.SOUTH);
      
      habilitarBotones(false);
      
      botonModificar.addActionListener(e -> {
    	        int filaSeleccionada = table.getSelectedRow();
	    	        int idTarea = (int) table.getValueAt(filaSeleccionada, 0);
	    	        if(!(rolActual.getNombre().equals("Observador"))) {
								try {
									api.setTareaActual(idTarea);
									modificarTarea();
								} catch (NotNullException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace(); //Tratar mejor la excepcion
								} catch (DataEmptyException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (InvalidDateException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} catch (DataBaseFoundException e1) {
						        	JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
								} catch (DataBaseConnectionException e1) {
						        	JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
								}
								dispose();
    	    	}else {
    	    		JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);
    	    	}
	    	    habilitarBotones(false);
	    	    table.clearSelection();
    	    }
    	);
      
      botonEliminar.addActionListener(e -> {
	  		if(!(rolActual.getNombre().equals("Observador"))) {
	  		int filaSeleccionada = table.getSelectedRow(); 	        
	  			int idTarea = (int) table.getValueAt(filaSeleccionada, 0);
				int confirmacion = JOptionPane.showConfirmDialog(null, labels.getString("mensaje.preguntaDeEliminar"),labels.getString("titulo.optionPanePreguntaEliminacion"), JOptionPane.YES_NO_OPTION);				
				if (confirmacion == JOptionPane.YES_OPTION) {		
					
						try {
							api.eliminarTarea(idTarea);
							habilitarBotones(false);
							((DefaultTableModel) table.getModel()).removeRow(filaSeleccionada);
							JOptionPane.showMessageDialog(null, labels.getString("mensaje.eliminacionExitosa"), labels.getString("titulo.optionPaneEliminacion"), JOptionPane.INFORMATION_MESSAGE);		
						}  catch (DataBaseEliminationException e1) {
							JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
						} catch (DataBaseConnectionException e1) {
							JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
						}
	                    
				}
	  		}else {
	  			JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);
	    	}
    	    habilitarBotones(false);
    	    table.clearSelection();
		}  	  
     );
      
      setLocationRelativeTo(null);
      
      addWindowListener(new WindowAdapter() { 
      	public void windowClosing(WindowEvent e) {
      		new VentanaResumen(api).setVisible(true);
      	}
	 });
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
    
	private void habilitarBotones(boolean b) {
		botonModificar.setEnabled(b);
		botonEliminar.setEnabled(b);

	}
	
	void modificarTarea() {
	 ModificarTarea modificatarea = new ModificarTarea(api);
	 modificatarea.setVisible(true);
    }
}


	
