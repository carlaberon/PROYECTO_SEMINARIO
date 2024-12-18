package ar.edu.unrn.seminario.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
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
	private JPanel contentPane;
	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 

 public ListaProyectos(IApi api)  {
    	this.api = api;
    	this.usuarioActual = api.getUsuarioActual();
        setTitle(labels.getString("menu.proyecto"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);
        getContentPane().setLayout(new BorderLayout());
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        Color fondoColor =  new Color(45, 44, 50);

        getContentPane().setBackground(fondoColor);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(labels.getString("menu.proyectos"));
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

        // Panel lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = { labels.getString("menu.proyectos"), labels.getString("menu.volver") };
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
                	new Inicio(api).setVisible(true);
                    dispose();
                });
            }
        }

        contentPane.add(menuPanel, BorderLayout.WEST);

        // Panel central
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
        tabla = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
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
            return p1.getNombre().compareToIgnoreCase(p2.getNombre());
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
		} catch (NotNullException | DataEmptyException e) {
        	JOptionPane.showMessageDialog(null, labels.getString("mensaje.camposVaciosONulos") + labels.getString(e.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		} catch (DataBaseConnectionException e1) {
			JOptionPane.showMessageDialog(null,labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
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

        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        tabla.getTableHeader().setOpaque(false);
        tabla.getTableHeader().setBackground(new Color(83, 82, 90)); 
        tabla.getTableHeader().setForeground(Color.WHITE); 
        tabla.getTableHeader().setPreferredSize(new Dimension(100, 40));
        
        // Personalización de la tabla
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabla.setRowHeight(40);
        tabla.setBackground(new Color(45, 44, 50)); 
        tabla.setForeground(Color.WHITE); 
        tabla.setSelectionBackground(new Color(138, 102, 204));
        tabla.setSelectionForeground(Color.WHITE); 
        
        // Mostrar las líneas de cuadrícula
        tabla.setGridColor(new Color(83, 82, 90)); 
        tabla.setShowGrid(true);
        
        tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
					habilitarBotones(true);

			}
		});

		tabla.setModel(modelo);
		scrollPane.setViewportView(tabla);
		scrollPane.getViewport().setBackground(new Color(45, 44, 50)); 
		
		centerPanel1.add(descPanel);

  
        eliminarProyecto = createButton(labels.getString("boton.eliminar"), new Color(138, 102, 204));
        
        
        eliminarProyecto.addActionListener(e -> {
	        	habilitarBotones(false);
	        	int filaSeleccionada = tabla.getSelectedRow(); 
	        	int projecId = (int) tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
	        	try {
					if (api.getRol(usuarioActual.getUsername(), projecId).getNombre().equals("Administrador")) {
						int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
						           labels.getString("mensaje.confirmarEliminacion"), labels.getString("mensaje.eliminarProyecto"),
						           JOptionPane.YES_NO_OPTION);
						if (opcionSeleccionada == JOptionPane.YES_OPTION) {
							api.eliminarProyecto(projecId);
							((DefaultTableModel) tabla.getModel()).removeRow(filaSeleccionada);		
							habilitarBotones(false);
						}
					}else {
						JOptionPane.showMessageDialog(null, labels.getString("mensaje.accesoDegenado"), labels.getString("mensaje.errorPermisos"), JOptionPane.WARNING_MESSAGE);	
					}
				} catch (DataBaseConnectionException e2) {
					JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DataBaseFoundException e2) {
					JOptionPane.showMessageDialog(null,labels.getString(e2.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
					((DefaultTableModel) tabla.getModel()).removeRow(filaSeleccionada);
				}
        });
        


        JPanel botones = new JPanel(new FlowLayout());
        botones.setOpaque(false);
        botones.add(eliminarProyecto);
      
        descPanel.add(botones, BorderLayout.SOUTH);
        habilitarBotones(false);

        
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		new Inicio(api).setVisible(true);
        	}
		});
        
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
    // Método auxiliar para crear paneles con título y diseño consistente
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
}