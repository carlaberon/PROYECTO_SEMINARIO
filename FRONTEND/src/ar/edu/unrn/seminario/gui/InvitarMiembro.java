package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
	private IApi api;

	/*
	private List<RolDTO> roles = new ArrayList<>(); //crear el RolDTO, crear clase Rol

	private List<UsuarioDTO> usuarios = new ArrayList<>(); //crear el UsuarioDTO, crear clase usuario
	*/
	/**
	 * Create the frame.
	 */
	public InvitarMiembro(IApi api) {
		/*
		// Obtengo los usuarios
		this.usuarios = api.obtenerUsuarios(); 
		this.roles = api.obtenerRoles();
	*/
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 400);
		getContentPane().setLayout(null);
		this.api = api;
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
			/*
				 Agregar un miembro 
				RolDTO rol = roles.get(rolComboBox.getSelectedIndex()); 
				UsuarioDTO usuario = usuarios.get(usuarioComboBox.getSelectedIndex());
			
					api.agregarMiembroProyecto(nombreProyectoTextField .getText(), proyecto.getCodigo(), usuario, rol);
			*/
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



		rolesComboBox = new JComboBox();
		rolesComboBox.setForeground(new Color(29, 17, 40));
		rolesComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		rolesComboBox.setBounds(381, 195, 280, 27);
		contentPane.add(rolesComboBox);
		rolesComboBox.addItem("");
		rolesComboBox.addItem("rol_colaborador");
		rolesComboBox.addItem("rol_observador");
		
		/*
		for (RolDTO rol : this.roles) {
			proyectoComboBox.addItem(rol.getNombre());
		} */
		
		JLabel lblNewLabel = new JLabel("Miembros");
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		lblNewLabel.setBounds(35, 47, 291, 73);
		contentPane.add(lblNewLabel);
		
		usernameInvitado = new JTextField();
		usernameInvitado.setBounds(70, 195, 268, 27);
		contentPane.add(usernameInvitado);
		usernameInvitado.setColumns(10);

		/*
		for (UsuarioDTO usuario : this.usuarios) {
			proyectoComboBox.addItem(usuario.getNombre());
		} */

		setLocationRelativeTo(null);
	}
//	   public static void main(String[] args) {
//	        InvitarMiembro frame = new InvitarMiembro();
//	        frame.setVisible(true);
//	    }
}