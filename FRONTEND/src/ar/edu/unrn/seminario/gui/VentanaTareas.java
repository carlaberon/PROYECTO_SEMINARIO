package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Button;
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
import java.util.ArrayList;
import java.util.List;

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
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotFoundException;
import ar.edu.unrn.seminario.exception.TaskQueryException;


public class VentanaTareas extends JFrame {

    private JPanel contentPane;
    private JTable table;
	DefaultTableModel modelo;
	private IApi api;
	JButton botonModificar;
	JButton botonEliminar;
	private UsuarioDTO usuarioActual; //obtener usuario actual por medio de la api
    private ProyectoDTO unproyecto; //obtener proyecto por medio de la api
	

    public VentanaTareas(IApi api) throws RuntimeException, InvalidDateException, NotNullException, DataEmptyException, TaskQueryException{
    	this.api = api; 
    	this.usuarioActual = api.getUsuarioActual();
    	this.unproyecto = api.getProyectoActual(); 
    	
    	setTitle("TAREAS");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
        // Configuración del menú superior
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

        JMenu accountMenu = new JMenu(usuarioActual.getUsername());
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem logoutItem = new JMenuItem("Cerrar sesión");
        JMenuItem confItem = new JMenuItem("Configurar cuenta");
        accountMenu.add(confItem);
        accountMenu.add(logoutItem);
        
        logoutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        });

        menuBar.add(accountMenu);
        this.setJMenuBar(menuBar);
        
        // Panel lateral (Menú)
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {"Tareas", "Volver"};
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
        }
    
        contentPane.add(menuPanel, BorderLayout.WEST);
        
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
		String[] titulos = { "ID","NOMBRE", "PROYECTO", "ESTADO","DESCRIPCION", "ASIGNADO", "PRIORIDAD", "FECHA INICIO", "FECHA FIN" };
		modelo = new DefaultTableModel(new Object[][] {}, titulos);
		try {
			
		List<TareaDTO> tareas = api.obtenerTareasPorProyecto(unproyecto.getId());
			
		modelo.setRowCount(0); // Limpiar el modelo antes de agregar nuevas filas
		
		for (TareaDTO t : tareas) {
		    modelo.addRow(new Object[] {
		    	t.getId(),
		        t.getName(),
		        unproyecto.getNombre(),
		        t.getEstado(), // Modifica el estado a una cadena legible
		        t.getDescription(),
		        t.getUser(),
		        t.getPriority(), 
		        t.getInicio(),
		        t.getFin()
		    });
		}
		}
		catch (NullPointerException exception) {
			JOptionPane.showMessageDialog(null, "No hay tareas", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
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

        JButton btnTarea = createButton("Tarea +", new Color(138, 102, 204));
        btnTarea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CrearTarea crearTarea;
				try {
					crearTarea = new CrearTarea(api);
					crearTarea.setLocationRelativeTo(null);
					crearTarea.setVisible(true);
				} catch (NotNullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (DataEmptyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
	
			}
		});

        buttonPanel.add(btnTarea);
        descPanel.add(buttonPanel, BorderLayout.NORTH); // Coloca el botón en el norte (arriba)
        centerPanel1.add(descPanel);
        
      //Configuración del botón "Actualizar Tabla" en la esquina superior izquierda
        
        JButton btnActualizarTabla = createButton("Actualizar", new Color(138, 102, 204));
        buttonPanel.add(btnActualizarTabla);
        btnActualizarTabla.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent arg0) {
    				try {
						actualizarTabla();
					} catch (NotNullException e) {
			            JOptionPane.showMessageDialog(null, "Error: Hay campos que no pueden ser nulos.", "Error de validación", JOptionPane.ERROR_MESSAGE);
					} catch (InvalidDateException e) {
			            JOptionPane.showMessageDialog(null, "Error: La fecha ingresada es inválida.", "Error de fecha", JOptionPane.ERROR_MESSAGE);
					} catch (DataEmptyException e) {
			            JOptionPane.showMessageDialog(null, "Error: No se encontraron datos en la consulta. Verifica si el proyecto tiene tareas.", "Datos vacíos", JOptionPane.WARNING_MESSAGE);
					} catch (TaskQueryException e) {
			            JOptionPane.showMessageDialog(null, "Error al realizar la consulta de tareas en la base de datos: " + e.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
					}
    			}
    		});
        
       //Configuración de los botones "Modificar" y "Eliminar" tarea
      JPanel botones = new JPanel(new FlowLayout());
      botones.setOpaque(false);
      
      botonModificar = createButton("Modificar", new Color(138, 102, 204));
      botonEliminar = createButton("Eliminar", new Color(138, 102, 204));
      botones.add(botonModificar);
      botones.add(botonEliminar);
      descPanel.add(botones, BorderLayout.SOUTH);
      
      
      habilitarBotones(false);
      
      botonModificar.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        int filaSeleccionada = table.getSelectedRow();
    	        if (filaSeleccionada != -1) {
	    	        int idTarea = (int) table.getValueAt(filaSeleccionada, 0);
	
						try {
							api.setTareaActual(idTarea);
						} catch (DataEmptyException e1) {
							JOptionPane.showMessageDialog(null, "Error: Hay datos vacios. No se puede establecer la tarea actual.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (NotNullException e1) {
							JOptionPane.showMessageDialog(null, "Error: Hay datos nulos. No se puede establecer la tarea actual.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (InvalidDateException e1) {
							JOptionPane.showMessageDialog(null, "Error: Fecha inválida en la tarea seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (TaskQueryException e1) {
				            JOptionPane.showMessageDialog(null, "Error al realizar la consulta de tareas: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	
						}
	
						try {
							habilitarBotones(false);
		    	        	table.clearSelection();
							modificarTarea();
						} catch (NotNullException e1) {
				            JOptionPane.showMessageDialog(null, "Error: Hay datos nulos en la tarea a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (DataEmptyException e1) {
				            JOptionPane.showMessageDialog(null, "Error: Hay datos vacios en la tarea a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
						} catch (InvalidDateException e1) {
				            JOptionPane.showMessageDialog(null, "Error: Fecha inválida en la tarea a modificar.", "Error", JOptionPane.ERROR_MESSAGE);
						}
    	        } else {
    	            JOptionPane.showMessageDialog(botonEliminar, "Por favor, seleccione una tarea para eliminar.", "Selección de tarea", JOptionPane.WARNING_MESSAGE);
    			}

    	    }
    	});
      
      botonEliminar.addActionListener(new ActionListener() {
      
	  	public void actionPerformed(ActionEvent e) {
	  		
	  		int filaSeleccionada = table.getSelectedRow(); 	        
	  		if (filaSeleccionada != -1) {
	  			int idTarea = (int) table.getValueAt(filaSeleccionada, 0);
				
				int confirmacion = JOptionPane.showConfirmDialog(botonEliminar, "¿Desea eliminar la tarea: " ,"Confirmar Eliminacion", JOptionPane.YES_NO_OPTION);				
				if (confirmacion == JOptionPane.YES_OPTION) {															
					try {
						api.eliminarTarea(idTarea);
						habilitarBotones(false);
						((DefaultTableModel) table.getModel()).removeRow(filaSeleccionada);
	                    JOptionPane.showMessageDialog(botonEliminar, "La tarea ha sido eliminada con éxito.", "Eliminación exitosa", JOptionPane.INFORMATION_MESSAGE);
					} catch (TaskNotFoundException e1) {
	                    JOptionPane.showMessageDialog(botonEliminar, "No se encontró la tarea a eliminar o no se pudo eliminar debido a un error de base de datos: " + e1.getMessage(), "Error al eliminar", JOptionPane.ERROR_MESSAGE);
					}										
				}
			} else {
	            JOptionPane.showMessageDialog(botonEliminar, "Por favor, seleccione una tarea para eliminar.", "Selección de tarea", JOptionPane.WARNING_MESSAGE);
			}				
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
    
	private void habilitarBotones(boolean b) {
		botonModificar.setEnabled(b);
		botonEliminar.setEnabled(b);

	}
	
	void actualizarTabla() throws NotNullException, InvalidDateException, DataEmptyException, TaskQueryException{
	
	    // Obtiene el model del table
	    DefaultTableModel modelo = (DefaultTableModel) table.getModel();

	    //***********************************************************************************************************************//
	    // Obtiene la lista de tareas filtradas por proyecto: PENDIENTE
	    //List<TareaDTO> tareas = api.obtenerTareasPorProyecto(this.getTitle()); // this.getTitle() retorna el nombre del proyecto
	    //***********************************************************************************************************************//
	    
	    List<TareaDTO> tareas = api.obtenerTareas();
	    
	    
	    modelo.setRowCount(0);

	    // Agrega las tareas en el modelo
		for (TareaDTO t : tareas) {
		    modelo.addRow(new Object[] {
		        t.getId(),
		        t.getName(),
		        unproyecto.getNombre(),
		        t.getEstado(), // Modifica el estado a una cadena legible
		        t.getDescription(),
		        t.getUser(),
		        t.getPriority(), 
		        t.getInicio(),
		        t.getFin()
		    });
		}
		// Ocultar la columna ID
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

	}
	//api.setTareaActual->id
	//api.getTareaActual<-id
	void modificarTarea() throws NotNullException, DataEmptyException, InvalidDateException {
	 ModificarTarea modificatarea = new ModificarTarea(api);
	 modificatarea.setVisible(true);
    }
	
	/*public static void main(String []args) throws NotNullException, DataEmptyException, RuntimeException, InvalidDateException {
		
		IApi api = new PersistenceApi();
		//prueba
		api.setUsuarioActual("ldifabio");
	
		api.setProyectoActual(1);

		VentanaTareas ventana = new VentanaTareas(api);
		
		ventana.setVisible(true);
		
		
	}*/

}


	
