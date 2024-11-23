package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class ListaProyectos extends JFrame {
	private IApi api;
	private JTable tabla;
	private JButton eliminarProyecto;
	private JButton volver;
	
    public ListaProyectos(IApi api) throws NotNullException, DataEmptyException {
    	
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
//   	 descomentar para que tome el idioma ingles (english)

   	//ResourceBundle labels = ResourceBundle.getBundle("labels");
    	this.api = api;

        // Configuración básica de la ventana
        setTitle("");
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
        String[] proyectosTabla = {"Id", "Nombre", "Descripcion", "Estado", "Prioridad", "Propietario"};
        
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


        // Hacer que la columna de descripción permita texto multilínea
        tabla.getColumnModel().getColumn(2).setCellRenderer(new JTextAreaRenderer());
        
        tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// Habilitar botones
				habilitarBotones(true);

			}
		});

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(109, 114, 195)); // Púrpura para el fondo del panel inferior
        JLabel labelInferior = new JLabel("LabProject - Sistema de Gestión de Proyectos");
        labelInferior.setForeground(Color.WHITE);
        panelInferior.add(labelInferior);

        // Añadir la tabla y el panel inferior al JFrame
        JPanel panelCentro = new JPanel(new BorderLayout());
        JPanel panelEliminar = new JPanel();
        panelEliminar.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        eliminarProyecto = createButton("Eliminar", new Color(138, 102, 204));
        habilitarBotones(false);
        eliminarProyecto.addActionListener(new ActionListener() {
		
			@Override
			public void actionPerformed(ActionEvent e) {
				int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
						"Estas seguro que queres eliminar el proyecto?", "Confirmar cambio de estado.",
						JOptionPane.YES_NO_OPTION);
				if (opcionSeleccionada == JOptionPane.YES_OPTION) {
					int projecId = (int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
					try {
						api.eliminarProyecto(projecId);
					} catch (TaskNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DataEmptyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NotNullException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidDateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TaskQueryException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						actualizarTabla();
					} catch (NotNullException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (DataEmptyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					habilitarBotones(false);
				}
				
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
    }

    // Renderer personalizado para celdas con JTextArea (que permita texto multilínea)
    class JTextAreaRenderer extends JTextArea implements TableCellRenderer {
        public JTextAreaRenderer() {
            setLineWrap(true); // Permite que el texto salte a la siguiente línea
            setWrapStyleWord(true); // Ajusta a palabras completas
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);

            if (isSelected) {
                setBackground(table.getSelectionBackground());
                setForeground(table.getSelectionForeground());
            } else {
                setBackground(table.getBackground());
                setForeground(table.getForeground());
                setForeground(Color.WHITE); // Texto en blanco
                setBackground(new Color(48, 48, 48));
            }
            return this;
        }
    }

    // Editor para manejar la acción del botón "Expandir"
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                    int row = table.getSelectedRow();

                    // Obtener el tamaño preferido de la celda de la columna de descripción
                    JTextArea textArea = (JTextArea) table.getCellRenderer(row, 2)
                            .getTableCellRendererComponent(table, table.getValueAt(row, 2), false, false, row, 2);
                    int preferredHeight = textArea.getPreferredSize().height;

                    // Establecer la altura de la fila en función del tamaño preferido
                    table.setRowHeight(row, preferredHeight > 30 ? preferredHeight : 30);
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "Expandir" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
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
        button.setForeground(Color.WHITE);
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


//    public static void main(String args[]) throws NotNullException, DataEmptyException {
//    	IApi api = new PersistenceApi();
//		api.setUsuarioActual("ldifabio");
//		
//		api.setProyectoActual("proyecto fenix");
//	
//    	ListaProyectos ventana = new ListaProyectos (api);
//    	
//    	ventana.setVisible(true);
//    	
//    }

	
}
