package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;


public class ListaProyectos extends JFrame {
	private IApi api;
	private JTable tabla;
	private JButton eliminarProyecto;
	private UsuarioDTO usuarioActual; 
	
    public ListaProyectos(IApi api) {
    	
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 
//   	 descomentar para que tome el idioma ingles (english)

   	//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	this.api = api;
    	this.usuarioActual = api.getUsuarioActual();
        setTitle(labels.getString("menu.proyecto"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);
        getContentPane().setLayout(new BorderLayout());

        Color fondoColor = new Color(65, 62, 77); 
        Color tituloColor = new Color(138, 102, 204);
        Font fuente = new Font("Segoe UI", Font.PLAIN, 14);

        getContentPane().setBackground(fondoColor);
        
     // Configuración del menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));
        
        JLabel appName = new JLabel(labels.getString("menu.proyectos"));
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.add(appName);
        menuBar.add(centerPanel);
        this.setJMenuBar(menuBar);
        
        
     // Personalización de la tabla
        tabla = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hace que las celdas no sean editables
            }
        };
        
        String[] proyectosTabla = {labels.getString("menu.Id"), labels.getString("menu.nombreTabla"), labels.getString("menu.descripcionProyecto"), labels.getString("menu.estadoProyecto"), labels.getString("mensaje.prioridad"), labels.getString("menu.propietario")};
        
        DefaultTableModel modelo = new DefaultTableModel(new Object[][] {}, proyectosTabla);
        tabla.setModel(modelo);
        
        List<ProyectoDTO> proyectos;
		try {
			proyectos = api.obtenerProyectos(api.getUsuarioActual().getUsername());

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

        if(!proyectos.isEmpty()) {
        	for (ProyectoDTO p : proyectos) {
        		modelo.addRow(new Object[] {
        				p.getId(),
        				p.getNombre(), 
        				p.getDescripcion(), 
        				labels.getString("estado.proyecto"),
        				labels.getString(api.traducirPrioridad(p.getPrioridad())), 
        				p.getUsuarioPropietario().getUsername()});
        	}
        }
		} catch (NotNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); //Tratar mejor la excepcion
		} catch (DataEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DataBaseConnectionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
     
        // Ocultar la columna ID
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
        
        // Configurar render de la tabla
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.WHITE);
                return c;
            }
        });

        // Establecer fuente y color de encabezados
        tabla.setFont(fuente);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabla.getTableHeader().setBackground(tituloColor); 
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setBackground(fondoColor);
        tabla.setRowHeight(30);
        
        tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					habilitarBotones(true);

			}
		});

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(138, 102, 204)); 
        JLabel labelInferior = new JLabel(labels.getString("menu.sistema"));
        labelInferior.setForeground(Color.WHITE);
        panelInferior.add(labelInferior);

        // Añadir la tabla y el panel inferior al JFrame
        JPanel panelCentro = new JPanel(new BorderLayout());
        JPanel panelEliminar = new JPanel();
        panelEliminar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        eliminarProyecto = createButton(labels.getString("boton.eliminar"), new Color(138, 102, 204));
        
        
        eliminarProyecto.addActionListener(e -> {
	        	habilitarBotones(false);
	        	int projecId = (int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
	        	try {
					if (api.getRol(usuarioActual.getUsername(), projecId).getNombre().equals("Administrador")) {
						int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
						           labels.getString("mensaje.confirmarEliminacion"), labels.getString("mensaje.eliminarProyecto"),
						           JOptionPane.YES_NO_OPTION);
						if (opcionSeleccionada == JOptionPane.YES_OPTION) {
							api.eliminarProyecto(projecId);
							actualizarTabla();
									
							habilitarBotones(false);
						}
					}else {
						JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);	
					}
	        	} catch (DataBaseConnectionException e1) {
					JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				}
        });
        
        panelEliminar.add(eliminarProyecto);
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(fondoColor); // Establecer el fondo del viewport
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelEliminar.setBackground(fondoColor);
        panelCentro.add(panelEliminar,BorderLayout.SOUTH);
        getContentPane().add(panelCentro, BorderLayout.CENTER);
        getContentPane().add(panelInferior, BorderLayout.SOUTH); // Añadir el panel inferior
        
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		new Inicio(api).setVisible(true);
        	}
		});
        
    }
    
    public void actualizarTabla() {
    	// Obtiene el model del table
    			DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    			// Obtiene la lista de usuarios a mostrar
    			List<ProyectoDTO> proyectos = null;
				try {
					try {
						proyectos = api.obtenerProyectos(api.getUsuarioActual().getUsername());
					} catch (DataBaseConnectionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			  proyectos.sort((p1, p2) -> {
    		            int prioridadComparacion = Integer.compare(api.obtenerPrioridad(p1.getPrioridad()), 
    		                                                       api.obtenerPrioridad(p2.getPrioridad()));
    		            if (prioridadComparacion != 0) {
    		                return prioridadComparacion;
    		            }
    		            return p1.getNombre().compareTo(p2.getNombre());
    		        });
    			// Resetea el model
    			modelo.setRowCount(0);
    			if(!proyectos.isEmpty()) {
    				for (ProyectoDTO p : proyectos) {
    					modelo.addRow(new Object[] {
    							p.getId(),
    							p.getNombre(), 
    							p.getDescripcion(), 
    							p.isEstado(),
    							p.getPrioridad(), 
    							p.getUsuarioPropietario().getUsername()});
    				}
    			}

				} catch (NotNullException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(); //Tratar mejor la excepcion
				} catch (DataEmptyException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    }
 // Método para crear botones con estilo
    private JButton createButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setForeground(new Color(229, 212, 237));
        button.setBackground(backgroundColor);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }
    
	private void habilitarBotones(boolean b) {
		eliminarProyecto.setEnabled(b);
	}
}