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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.UserIsAlreadyMember;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class InvitarMiembro extends JFrame {

	private JPanel contentPane;
	private JTextField campoBusqueda;
//	private JComboBox<String> asignarUsuarioComboBox;
	private JComboBox<String> asignarRolComboBox = new JComboBox<>();
	private List<UsuarioDTO> usuarios = new ArrayList<>();
	private List<RolDTO> roles = new ArrayList<>(); 
	private IApi api;

	public InvitarMiembro(IApi api)  {
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
		
		this.roles = api.obtenerRoles();
	
		setTitle(labels.getString("ventana.invitarMiembro"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(null);
		this.api = api;
		this.usuarios = api.obtenerUsuarios(api.getUsuarioActual().getUsername());
		contentPane = new JPanel();
		contentPane.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		contentPane.setBackground(new Color(81, 79, 89));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		Color fondoColor = new Color(48, 48, 48); // Color de fondo oscuro
		Color tituloColor = new Color(109, 114, 195); // Púrpura para los títulos
		Color grisFondoTextfield = new Color(83, 82, 90); //Gris para el fondo de los TextField
		Font fuente = new Font("Segoe UI", Font.BOLD, 13);
		
		JLabel nombreProyecto = new JLabel(labels.getString("label.ingresarUsuarioInvitado"));
		nombreProyecto.setForeground(new Color(240, 240, 240));
		nombreProyecto.setFont(new Font("Segoe UI", Font.BOLD, 16));
		nombreProyecto.setBounds(70, 145, 256, 39);
		contentPane.add(nombreProyecto);


		JButton invitarButton = new JButton(labels.getString("boton.invitarMiembro"));
		invitarButton.addActionListener(e -> {
			
		});
		invitarButton.setForeground(new Color(229, 212, 237));
		invitarButton.setBackground(new Color(89, 65, 169));
		invitarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
	
		invitarButton.addActionListener(e -> {
				String nombreUsuario = campoBusqueda.getText();
				try {
					if(api.existeMiembro(nombreUsuario,api.getProyectoActual().getId()) == 0) {
							int id_proyecto;
							id_proyecto = api.getProyectoActual().getId();
							String nombre_rol = (String)asignarRolComboBox.getSelectedItem();
						
							int codigo_rol = obtenerCodigoRol(nombre_rol);
						
							api.invitarMiembro(nombreUsuario,id_proyecto,codigo_rol);
				
					
							JOptionPane.showMessageDialog(null, labels.getString("mensaje.invitacionExitosa"), labels.getString("mensaje.info"), JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
							dispose();
					}
				} catch(UserIsAlreadyMember e1) {
					JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), labels.getString("mensaje.errorYaEsMiembro"), JOptionPane.ERROR_MESSAGE);
				} catch (DataEmptyException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, labels.getString(e2.getMessage()), labels.getString("titulo.optionpaneInviteMember"), JOptionPane.ERROR_MESSAGE);
				}
			}
		); 

		invitarButton.setBounds(514, 279, 147, 27);
		contentPane.add(invitarButton);



		JLabel rolLabel = new JLabel(labels.getString("label.asignarUnRol"));
		rolLabel.setForeground(new Color(240, 240, 240));
		rolLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
		rolLabel.setBounds(381, 145, 329, 39);
		contentPane.add(rolLabel);


		asignarRolComboBox = new JComboBox<>();
		asignarRolComboBox.setBounds(381, 195, 280, 27);
		asignarRolComboBox.addItem("");
		asignarRolComboBox.setFont(fuente);
		asignarRolComboBox.setForeground(tituloColor);
		asignarRolComboBox.setBackground(grisFondoTextfield);
		asignarRolComboBox.setBorder(new LineBorder(Color.BLACK,3));
		
	        if ( ! this.roles.isEmpty()) {
	        	 for (RolDTO rol : this.roles) {
	                 asignarRolComboBox.addItem(rol.getNombre());
	             }
	        }
	       
	     contentPane.add(asignarRolComboBox);
		
		
		JLabel lblNewLabel = new JLabel(labels.getString("label.invitarUnMiembro"));
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 35));
		lblNewLabel.setBounds(35, 47, 404, 73);
		contentPane.add(lblNewLabel);
		


//        asignarUsuarioComboBox = new JComboBox<>();
//        asignarUsuarioComboBox.setBounds(70, 195, 268, 27);
//        
//        
//       asignarUsuarioComboBox.addItem("");
//        if ( ! this.usuarios.isEmpty()) {
//        	 for (UsuarioDTO usuario : this.usuarios) {
//                 asignarUsuarioComboBox.addItem(usuario.getUsername());
//             }
//        }
//       
//        contentPane.add(asignarUsuarioComboBox);
        
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBounds(70, 195, 268, 100);  // Ajuste de tamaño y ubicación
		panel.setBackground(fondoColor);
		
		campoBusqueda = new JTextField();
		campoBusqueda.setBackground(grisFondoTextfield);
		campoBusqueda.setBorder(new LineBorder(Color.BLACK, 3));
		campoBusqueda.setForeground(tituloColor);
		campoBusqueda.setFont(fuente);
		panel.add(campoBusqueda, BorderLayout.NORTH);
		
		JTable tablaUsuarios = new JTable();
		DefaultTableModel modelo = new DefaultTableModel(new String[]{labels.getString("label.usuarios")},0);
		tablaUsuarios.setModel(modelo);
		tablaUsuarios.setFont(fuente);
		tablaUsuarios.getTableHeader().setVisible(false);
		tablaUsuarios.getTableHeader().setPreferredSize(new Dimension(0, 0)); //Oculto titulo de la columna
		tablaUsuarios.setBackground(fondoColor);
		tablaUsuarios.setForeground(tituloColor);
		tablaUsuarios.setBorder(null);
//		tablaUsuarios.setOpaque(true);
		
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
//		            modelo.removeRow(selectedRow);
		        }
		    }
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
		scrollPane.setBorder(new LineBorder(Color.BLACK,0));
		scrollPane.getViewport().setBackground(new Color(81, 79, 89));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER); //Oculto el ScrollPane
		
		panel.add(scrollPane,BorderLayout.CENTER);
		
		// Cargar todos los usuarios en la tabla al inicio
        cargarUsuariosEnTabla(usuarios, modelo,10);
        
        campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,10);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,10);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrarUsuarios(campoBusqueda.getText(), usuarios, modelo,10);
            }
        });
        
        contentPane.add(panel);
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
}