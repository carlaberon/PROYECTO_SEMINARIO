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

public class CrearProyecto extends JFrame {

	private JPanel contentPane;
	private JTextField nombreProyectoTextField;
	private JComboBox proyectoComboBox;

	/*
	private List<ProyectoDTO> proyectos = new ArrayList<>(); //crear el proyectoDTO, crear el proyecto
	*/
	/**
	 * Create the frame.
	 */
	public CrearProyecto(/* IApi api */) {
		/*
		// Obtengo los proyecto 
		this.proyectos = api.obtenerProyectos(); 
	*/
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel nombreProyecto = new JLabel("Nombre Proyecto:");
		nombreProyecto.setFont(new Font("Gill Sans MT", Font.PLAIN, 10));
		nombreProyecto.setBounds(64, 76, 113, 16);
		contentPane.add(nombreProyecto);

		

		nombreProyectoTextField = new JTextField();
		nombreProyectoTextField.setBounds(187, 74, 160, 22);
		contentPane.add(nombreProyectoTextField);
		nombreProyectoTextField.setColumns(10);


		JButton aceptarButton = new JButton("Guardar");
		aceptarButton.setForeground(new Color(255, 255, 255));
		aceptarButton.setBackground(new Color(10, 118, 58));
		aceptarButton.setFont(new Font("Gill Sans MT", Font.PLAIN, 10));
		/*
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 Registrar un proyecto
				ProyectoDTO proyecto = proyectos.get(proyectoComboBox.getSelectedIndex()); 
			
					api.registrarProyecto(nombreProyectoTextField .getText(), proyecto.getCodigo());
					JOptionPane.showMessageDialog(null, "Proyecto registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					setVisible(false);
					dispose();
				
			}
		}); 
		*/
		aceptarButton.setBounds(218, 215, 97, 25);
		contentPane.add(aceptarButton);

		JButton cancelarButton = new JButton("Cancelar");
		cancelarButton.setBackground(new Color(238, 238, 238));
		cancelarButton.setFont(new Font("Gill Sans MT", Font.PLAIN, 10));
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		cancelarButton.setBounds(323, 215, 97, 25);
		contentPane.add(cancelarButton);



		JLabel rolLabel = new JLabel("Subproyecto:");
		rolLabel.setFont(new Font("Gill Sans MT", Font.PLAIN, 10));
		rolLabel.setBounds(64, 129, 97, 16);
		contentPane.add(rolLabel);



		proyectoComboBox = new JComboBox();
		proyectoComboBox.setBounds(187, 126, 160, 22);
		contentPane.add(proyectoComboBox);
		
		JLabel lblNewLabel = new JLabel("Nuevo Proyecto");
		lblNewLabel.setFont(new Font("Gill Sans MT", Font.BOLD, 20));
		lblNewLabel.setBounds(20, 22, 192, 31);
		contentPane.add(lblNewLabel);

		/*
		for (ProyectoDTO proyect : this.proyectos) {
			proyectoComboBox.addItem(proyect.getNombre());
		} */

		
	}
	   public static void main(String[] args) {
	 
	        CrearProyecto frame = new CrearProyecto();
	        frame.setVisible(true);
	    }
}
