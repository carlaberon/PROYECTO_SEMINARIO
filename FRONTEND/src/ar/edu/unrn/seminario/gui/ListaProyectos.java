package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.TaskNotFoundException;
import ar.edu.unrn.seminario.exception.TaskQueryException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private JButton volver;
	
    public ListaProyectos(IApi api) throws NotNullException, DataEmptyException {
    	
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 
//   	 descomentar para que tome el idioma ingles (english)

   	//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	this.api = api;

        // Configuración básica de la ventana
        setTitle(labels.getString("menu.proyectos"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 400);
        getContentPane().setLayout(new BorderLayout());

        // Configurar colores y fuente
        Color fondoColor = new Color(48, 48, 48); // Color de fondo oscuro
        Color tituloColor = new Color(109, 114, 195); // Púrpura para los títulos
        Color nombreProyectoColor = new Color(109, 114, 195); // Púrpura para nombres de proyecto
        Font fuente = new Font("Segoe UI", Font.PLAIN, 11);

        getContentPane().setBackground(fondoColor);
        
        tabla = new JTable();
        String[] proyectosTabla = {labels.getString("menu.Id"), labels.getString("menu.nombre"), labels.getString("menu.descripcionProyecto"), labels.getString("menu.estadoProyecto"), labels.getString("mensaje.prioridad"), labels.getString("menu.propietario")};
        
        DefaultTableModel modelo = new DefaultTableModel(new Object[][] {}, proyectosTabla);
        tabla.setModel(modelo);
        
        List<ProyectoDTO> proyectos = api.obtenerProyectos(api.getUsuarioActual().getUsername());

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
        				p.isEstado(),
        				p.getPrioridad(), 
        				p.getUsuarioPropietario().getUsername()});
        	}
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
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabla.getTableHeader().setBackground(tituloColor); 
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setBackground(fondoColor);
        tabla.setRowHeight(30);
        
        tabla.addMouseListener(new MouseAdapter() {
        	
			@Override
			public void mouseClicked(MouseEvent arg0) {

					// Habilitar botones
					habilitarBotones(true);

			}
		});

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(109, 114, 195)); // Púrpura para el fondo del panel inferior
        JLabel labelInferior = new JLabel(labels.getString("menu.sistema"));
        labelInferior.setForeground(Color.WHITE);
        panelInferior.add(labelInferior);

        // Añadir la tabla y el panel inferior al JFrame
        JPanel panelCentro = new JPanel(new BorderLayout());
        JPanel panelEliminar = new JPanel();
        panelEliminar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        eliminarProyecto = createButton(labels.getString("boton.eliminar"), new Color(138, 102, 204));
        
        habilitarBotones(false);
        
        eliminarProyecto.addActionListener(e -> {
            int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
                    labels.getString("mensaje.confirmarEliminacion"), labels.getString("mensaje.eliminarProyecto"),
                    JOptionPane.YES_NO_OPTION);
            if (opcionSeleccionada == JOptionPane.YES_OPTION) {
                int projecId = (int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
                try {
                    api.eliminarProyecto(projecId);
                    actualizarTabla();
                } catch (TaskNotFoundException | DataEmptyException | NotNullException | InvalidDateException
                         | TaskQueryException e1) {
                    e1.printStackTrace();
                }
                habilitarBotones(false);
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

    
    public void actualizarTabla() throws NotNullException, DataEmptyException{
    	// Obtiene el model del table
    			DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    			// Obtiene la lista de usuarios a mostrar
    			List<ProyectoDTO> proyectos = api.obtenerProyectos(api.getUsuarioActual().getUsername());
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