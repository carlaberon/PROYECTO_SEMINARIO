package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.RolDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

import javax.swing.JTextPane;
import javax.swing.JTree;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class InvitarMiembro extends JFrame {

	private JPanel contentPane;
	private JComboBox rolesComboBox;
	private JTextField usernameInvitado;
	private JComboBox<String> asignarUsuarioComboBox;
	private JComboBox<String> asignarRolComboBox = new JComboBox<>();
	private List<UsuarioDTO> usuarios = new ArrayList<>();
	private List<RolDTO> roles = new ArrayList<>(); 
	private IApi api;

	public InvitarMiembro(IApi api) throws NotNullException, DataEmptyException {
		
		this.roles = api.obtenerRoles();
	
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(null);
		this.api = api;
		this.usuarios = api.obtenerUsuarios();
		contentPane = new JPanel();
		contentPane.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		contentPane.setBackground(new Color(81, 79, 89));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel nombreProyecto = new JLabel("Agregar usuarios existentes*:");
		nombreProyecto.setForeground(new Color(240, 240, 240));
		nombreProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		nombreProyecto.setBounds(70, 145, 256, 39);
		contentPane.add(nombreProyecto);


		JButton invitarButton = new JButton("Invitar");
		invitarButton.addActionListener(e -> {
			
		});
		invitarButton.setForeground(new Color(229, 212, 237));
		invitarButton.setBackground(new Color(89, 65, 169));
		invitarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
	
		invitarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					try {
						String nombreUsuario = (String)asignarUsuarioComboBox.getSelectedItem();
						int id_proyecto;
						id_proyecto = api.getProyectoActual().getId();
						String nombre_rol = (String)asignarRolComboBox.getSelectedItem();
						
						int codigo_rol = obtenerCodigoRol(nombre_rol);
						
						api.invitarMiembro(nombreUsuario,id_proyecto,codigo_rol);
					} catch (NotNullException | DataEmptyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					
					JOptionPane.showMessageDialog(null, "Miembro agregado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				
			}
		}); 

		invitarButton.setBounds(514, 279, 147, 27);
		contentPane.add(invitarButton);



		JLabel rolLabel = new JLabel("Asignar rol a nuevos miembros:");
		rolLabel.setForeground(new Color(240, 240, 240));
		rolLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		rolLabel.setBounds(381, 145, 329, 39);
		contentPane.add(rolLabel);


		asignarRolComboBox = new JComboBox<>();
		asignarRolComboBox.setBounds(381, 195, 280, 27);
		asignarRolComboBox.addItem("");
		asignarRolComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		asignarRolComboBox.setForeground(new Color(29, 17, 40));
		
	        if ( ! this.roles.isEmpty()) {
	        	 for (RolDTO rol : this.roles) {
	                 asignarRolComboBox.addItem(rol.getNombre());
	             }
	        }
	       
	     contentPane.add(asignarRolComboBox);
		
		
		JLabel lblNewLabel = new JLabel("Miembros");
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		lblNewLabel.setBounds(35, 47, 291, 73);
		contentPane.add(lblNewLabel);
		


        asignarUsuarioComboBox = new JComboBox<>();
        asignarUsuarioComboBox.setBounds(70, 195, 268, 27);
        
       asignarUsuarioComboBox.addItem("");
        if ( ! this.usuarios.isEmpty()) {
        	 for (UsuarioDTO usuario : this.usuarios) {
                 asignarUsuarioComboBox.addItem(usuario.getUsername());
             }
        }
       
        contentPane.add(asignarUsuarioComboBox);
		setLocationRelativeTo(null);
	}
	public int obtenerCodigoRol(String nombreRol) {
		int codigo=0; 
		for (RolDTO rolDTO : roles) {
			if (nombreRol.equals(rolDTO.getNombre())) {
				codigo = rolDTO.getCodigo(); 
				break;
			}
		}
		return codigo;
	}
//	   public static void main(String[] args) {
//	        InvitarMiembro frame = new InvitarMiembro();
//	        frame.setVisible(true);
//	    }
}