package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;

public class CrearCuenta extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JLabel lblNewLabel_1_2;
	private JTextField textField_3;
	private JButton aceptar;
	private JButton cancelar;
	private JLabel lblNewLabel_1_5;
	private JTextField textField_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					IApi api = new MemoryApi(); //polimorfismo
					CrearCuenta frame = new CrearCuenta(api);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CrearCuenta(IApi api) {
		setTitle("Registrarse");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(81, 79, 89));
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("Crear Cuenta");
		lblNewLabel.setBackground(new Color(240, 240, 240));
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		lblNewLabel.setBounds(58, 47, 234, 39);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Usuario*:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(83, 119, 93, 44);
		contentPane.add(lblNewLabel_1);
		
		textField = new JTextField();
		textField.setBounds(216, 134, 451, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1_1 = new JLabel("Nombre*:");
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1.setBounds(83, 174, 93, 44);
		contentPane.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(216, 189, 451, 26);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Apellido*:");
		lblNewLabel_1_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_1_1.setBounds(83, 228, 93, 44);
		contentPane.add(lblNewLabel_1_1_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(216, 243, 451, 26);
		contentPane.add(textField_2);
		
		lblNewLabel_1_2 = new JLabel("Email*:");
		lblNewLabel_1_2.setForeground(Color.WHITE);
		lblNewLabel_1_2.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_2.setBounds(83, 280, 93, 44);
		contentPane.add(lblNewLabel_1_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(216, 295, 451, 26);
		contentPane.add(textField_3);
		
		aceptar = new JButton("Aceptar");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.setForeground(new Color(255, 255, 255));
		aceptar.setBackground(new Color(89, 65, 169));
		aceptar.setBounds(342, 452, 147, 27);
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Usuario registrado con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
				setVisible(false);
				dispose();
			}
		});
		contentPane.add(aceptar);
		
		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelar.setForeground(new Color(29, 17, 40));
		cancelar.setBackground(new Color(229, 212, 237));
		cancelar.setBounds(520, 452, 147, 27);
		contentPane.add(cancelar);
		
		lblNewLabel_1_5 = new JLabel("Contraseña*:");
		lblNewLabel_1_5.setForeground(Color.WHITE);
		lblNewLabel_1_5.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblNewLabel_1_5.setBounds(83, 334, 120, 44);
		contentPane.add(lblNewLabel_1_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(216, 349, 451, 26);
		contentPane.add(textField_6);
	}
}
