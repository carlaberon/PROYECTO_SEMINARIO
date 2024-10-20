package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;

public class VentanaConfigurarProyecto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_Nombre;
	private JTextField textField_Prioridad;
	private JLabel lblNewLabel_1_2;
	private JTextField textField_Descripcion;
	private JButton aceptar;
	private JButton cancelar;
	private JLabel lblNewLabel_1_5;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
////					IApi api = new MemoryApi(); //polimorfismo
//					VentanaConfigurarProyecto frame = new VentanaConfigurarProyecto(api);
//					frame.setLocationRelativeTo(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public VentanaConfigurarProyecto(IApi api, ProyectoDTO proyecto) {
		setTitle("Modificar Proyecto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(81, 79, 89));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Modificar Proyecto");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		lblNewLabel.setBounds(58, 47, 293, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(83, 119, 93, 44);
		contentPane.add(lblNewLabel_1);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setBounds(216, 134, 451, 26);
		contentPane.add(textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Prioridad:");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(83, 174, 93, 44);
		contentPane.add(lblNewLabel_1_1_1);
		
		textField_Prioridad = new JTextField();
		textField_Prioridad.setColumns(10);
		textField_Prioridad.setBounds(216, 188, 451, 26);
		contentPane.add(textField_Prioridad);
		
		lblNewLabel_1_2 = new JLabel("Descripcion:");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(83, 229, 106, 44);
		contentPane.add(lblNewLabel_1_2);
		
		textField_Descripcion = new JTextField();
		textField_Descripcion.setColumns(10);
		textField_Descripcion.setBounds(216, 243, 451, 26);
		contentPane.add(textField_Descripcion);
		
		aceptar = new JButton("Aceptar");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.setForeground(new Color(255, 255, 255));
		aceptar.setBackground(new Color(89, 65, 169));
		aceptar.setBounds(452, 573, 147, 27);
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				api.modificarProyecto(proyecto.getNombre(), textField_Nombre.getText(), textField_Prioridad.getText(), textField_Descripcion.getText());
				
				int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
						"Estas seguro que queres modificar el proyecto?", "Confirmar cambio de estado.",
						JOptionPane.YES_NO_OPTION);
				if (opcionSeleccionada == JOptionPane.YES_OPTION) {
					JOptionPane.showMessageDialog(null, "Modificacion realizada con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					
				}
				
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(aceptar);
		
		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelar.setForeground(new Color(29, 17, 40));
		cancelar.setBackground(new Color(229, 212, 237));
		cancelar.setBounds(627, 573, 147, 27);
		cancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(cancelar);
		
		
	}

}
