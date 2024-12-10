package ar.edu.unrn.seminario.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.ExistNotification;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.UserIsAlreadyMember;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class InvitarMiembro extends JFrame {

	private JPanel contentPane;
	private JTextField campoBusqueda;
	private JComboBox<String> asignarRolComboBox = new JComboBox<>();
	private List<UsuarioDTO> usuarios = new ArrayList<>();
	private List<RolDTO> roles = new ArrayList<>(); 
	private ProyectoDTO proyectoActual;
	private UsuarioDTO usuarioActual;
	private IApi api;

	public InvitarMiembro(IApi api)  {
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 
		
		this.proyectoActual = api.getProyectoActual();
		this.usuarioActual = api.getUsuarioActual();
		this.roles = api.obtenerRoles();
		this.api = api;
		this.usuarios = api.obtenerUsuarios(api.getUsuarioActual().getUsername());
		
		setTitle(labels.getString("ventana.invitarMiembro"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 50, 1200, 650);
		getContentPane().setLayout(null);
		
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

		Color fondoColor = new Color(45, 44, 50);
		Font fuente = new Font("Segoe UI", Font.PLAIN, 14);
		
		// Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(labels.getString("ventana.invitarMiembro"));
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

        String[] menuItems = { labels.getString("menu.tareas"), labels.getString("menu.volver") };
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

            // Acción para botón "Volver"
            if (item.equals("Volver") || item.equals("Return")) {
                menuButton.addActionListener(e -> {
                    new VentanaResumen(api).setVisible(true);
                    dispose();
                });
            }
        }

        contentPane.add(menuPanel, BorderLayout.WEST);

        // Panel central
        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(null); // Usar diseño absoluto para respetar los bounds definidos
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(centerPanel1, BorderLayout.CENTER);

		
		JLabel nombreProyecto = new JLabel(labels.getString("label.ingresarUsuarioInvitado"));
		nombreProyecto.setForeground(new Color(240, 240, 240));
		nombreProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nombreProyecto.setBounds(70, 145, 256, 39);
		centerPanel1.add(nombreProyecto);


		JButton invitarButton = createButton(labels.getString("boton.invitarMiembro"), new Color(138, 102, 204));
		invitarButton.addActionListener(e -> {
			
		});
	
		invitarButton.addActionListener(e -> {
				String nombreUsuario = campoBusqueda.getText();
				String rol_seleccionado = api.obtenerRolPorIndex(asignarRolComboBox.getSelectedIndex());
				int codigo_rol = obtenerCodigoRol(rol_seleccionado);
					try {
						if(api.existeMiembro(nombreUsuario,proyectoActual.getId()) == 0 && api.existeNotificacion(proyectoActual.getId(), nombreUsuario) == 0) {
								int id_proyecto = api.getProyectoActual().getId();
								String nombreProject = api.getProyectoActual().getNombre();
								
								
								api.crearNotificacion(id_proyecto, nombreUsuario, codigo_rol, nombreProject, LocalDate.now());
					
						
								JOptionPane.showMessageDialog(null, labels.getString("mensaje.invitacionExitosa"), labels.getString("mensaje.info"), JOptionPane.INFORMATION_MESSAGE);
								dispose();
						}
					} catch(ExistNotification e1) {
						JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), labels.getString("mensaje.info"), JOptionPane.ERROR_MESSAGE);
					} catch(UserIsAlreadyMember e2) {
						JOptionPane.showMessageDialog(null, labels.getString(e2.getMessage()), labels.getString("mensaje.errorYaEsMiembro"), JOptionPane.ERROR_MESSAGE);
					} catch (NotNullException e3) {
						JOptionPane.showMessageDialog(null,labels.getString("mensaje.elCampo") + labels.getString(e3.getMessage()) + labels.getString("mensaje.null"), labels.getString("mensaje.campoObligatorio"),JOptionPane.WARNING_MESSAGE);
					} catch (DataEmptyException e4) {
						JOptionPane.showMessageDialog(null,labels.getString("mensaje.elCampo") + labels.getString(e4.getMessage()) + labels.getString("mensaje.empty"), labels.getString("mensaje.campoObligatorio"),JOptionPane.WARNING_MESSAGE);
					}
				
		}); 

		invitarButton.setBounds(514, 279, 147, 27);
		centerPanel1.add(invitarButton);



		JLabel rolLabel = new JLabel(labels.getString("label.asignarUnRol"));
		rolLabel.setForeground(new Color(240, 240, 240));
		rolLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		rolLabel.setBounds(381, 145, 329, 39);
		centerPanel1.add(rolLabel);


		asignarRolComboBox = new JComboBox<>();
		asignarRolComboBox.setBounds(381, 195, 280, 27);
		asignarRolComboBox.addItem("");
		asignarRolComboBox.setFont(fuente);
		asignarRolComboBox.setBorder(new LineBorder(Color.BLACK,1));
		
	        if ( ! this.roles.isEmpty()) {
	        	String rol1;
	        	 for (RolDTO rol : this.roles) {
	        		 switch (rol.getNombre()) {
					case "Administrador":
						rol1 = labels.getString("rol.Admin");
						break;
					case "Colaborador":
						rol1 = labels.getString("rol.Colaborador");
						break;
					default:
						rol1 = labels.getString("rol.Observador");
						break;
					}
	                 asignarRolComboBox.addItem(rol1);
	             }
	        }
	       
	     centerPanel1.add(asignarRolComboBox);
		

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBounds(70, 200, 268, 100);  // Ajuste de tamaño y ubicación
		panel.setBackground(fondoColor);
		
		campoBusqueda = new JTextField();
		campoBusqueda.setBorder(new LineBorder(Color.BLACK, 1));
		campoBusqueda.setFont(fuente);
		panel.add(campoBusqueda, BorderLayout.NORTH);
		
		JTable tablaUsuarios = new JTable();
		DefaultTableModel modelo = new DefaultTableModel(new String[]{labels.getString("label.usuarios")},0);
		tablaUsuarios.setModel(modelo);
		tablaUsuarios.setFont(fuente);
		tablaUsuarios.getTableHeader().setVisible(false);
		tablaUsuarios.getTableHeader().setPreferredSize(new Dimension(0, 0)); //Oculto titulo de la columna
		tablaUsuarios.setBorder(null);

		
		// Agregar un ListSelectionListener para detectar cuando se selecciona una fila
		tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
		    if (!e.getValueIsAdjusting()) {
		        // Obtener la fila seleccionada
		        int selectedRow = tablaUsuarios.getSelectedRow();
		        if (selectedRow != -1) {
		            // Obtener el valor de la columna (nombre de usuario) en la fila seleccionada
		            String usuarioSeleccionado = (String) modelo.getValueAt(selectedRow, 0);
		            // Establecer el valor seleccionado en el campo de búsqueda
		            campoBusqueda.setText(usuarioSeleccionado);
		            
//		            // Eliminar la fila seleccionada del modelo de la tabla
		            modelo.removeRow(selectedRow);
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
		scrollPane.setBorder(new LineBorder(Color.BLACK,0));
		scrollPane.getViewport().setBackground(new Color(45, 44, 50));
		
		
		panel.add(scrollPane,BorderLayout.CENTER);
		
		// Cargar todos los usuarios en la tabla al inicio
        cargarUsuariosEnTabla(usuarios, modelo,0);
        

        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,1);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,0);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,0);
            }
        });
        centerPanel1.add(panel);
		setLocationRelativeTo(null);
	}
	
	private int obtenerCodigoRol(String nombreRol) {
		int codigo=0; 
		for (RolDTO rolDTO : roles) {
			if (nombreRol.equals(rolDTO.getNombre())) {
				codigo = rolDTO.getCodigo(); 
				break;
			}
		}
		return codigo;
	}
	
	private static void cargarUsuariosEnTabla(List<UsuarioDTO> usuarios, DefaultTableModel modeloTabla, int cantidad) {
	    modeloTabla.setRowCount(0); // Limpiar la tabla
	    // Cargar solo los primeros 'cantidad' usuarios
	    List<UsuarioDTO> usuariosParaMostrar = usuarios.stream()
	            .limit(cantidad)
	            .collect(Collectors.toList());
	    for (UsuarioDTO usuario : usuariosParaMostrar) {
	        modeloTabla.addRow(new Object[]{usuario.getUsername()});
	    }
	}

	private static void filtrarUsuarios(String texto, List<UsuarioDTO> usuarios, DefaultTableModel modeloTabla, int cantidad) {
	    List<UsuarioDTO> usuariosFiltrados = usuarios.stream()
	            .filter(usuario -> usuario.getUsername().toLowerCase().contains(texto.toLowerCase()))
	            .collect(Collectors.toList());
	    // Cargar solo los primeros 'cantidad' usuarios filtrados
	    cargarUsuariosEnTabla(usuariosFiltrados, modeloTabla, cantidad);
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