//package ar.edu.unrn.seminario.gui;
//
//import java.awt.BorderLayout;
//import java.awt.EventQueue;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.JFrame;
//import javax.swing.JMenu;
//import javax.swing.JMenuBar;
//import javax.swing.JMenuItem;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//
//import ar.edu.unrn.seminario.api.IApi;
//import ar.edu.unrn.seminario.api.MemoryApi;
//import javax.swing.JButton;
//import java.awt.Font;
//import java.awt.Color;
//import javax.swing.JLabel;
//import javax.swing.JTextField;
//import javax.swing.JTabbedPane;
//
//public class VentanaPrincipal extends JFrame {
//
//	private JPanel contentPane;
//	private JTextField txtCorreoElectronico;
//	private JTextField txtContrasea;
//	private IApi api;
//
//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					IApi api = new MemoryApi(); //polimorfismo
//					VentanaPrincipal frame = new VentanaPrincipal(api);
//					frame.setLocationRelativeTo(null);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//
//	/**
//	 * Create the frame.
//	 */
//	public VentanaPrincipal(IApi api) {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 800, 650);
//		
//		JMenuBar menuBar = new JMenuBar();
//		setJMenuBar(menuBar);
//
//		JMenu usuarioMenu = new JMenu("Usuarios");
//		menuBar.add(usuarioMenu);
//
//		JMenuItem altaUsuarioMenuItem = new JMenuItem("Alta/Modificación");
//		altaUsuarioMenuItem.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent arg0) {
//				AltaUsuario alta = new AltaUsuario(api);
//				alta.setLocationRelativeTo(null);
//				alta.setVisible(true);
//			}
//			
//		});
//		usuarioMenu.add(altaUsuarioMenuItem);
//
//		JMenuItem listadoUsuarioMenuItem = new JMenuItem("Listado");
//		listadoUsuarioMenuItem.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent arg0) {
//				ListadoUsuario listado= new ListadoUsuario(api);
//				listado.setLocationRelativeTo(null);
//				listado.setVisible(true);
//			}
//			
//		});
//		//cambio
//		usuarioMenu.add(listadoUsuarioMenuItem);
//
//		JMenu configuracionMenu = new JMenu("Configuración");
//		menuBar.add(configuracionMenu);
//
//		JMenuItem salirMenuItem = new JMenuItem("Salir");
//		configuracionMenu.add(salirMenuItem);
//		contentPane = new JPanel();
//		contentPane.setBackground(new Color(81, 79, 89));
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(null);
//		setContentPane(contentPane);
//		
//		JButton btnNewButton = new JButton("Iniciar sesion");
//		btnNewButton.setForeground(new Color(255, 255, 255));
//		btnNewButton.setBackground(new Color(138, 102, 204));
//		btnNewButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
//		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
//		btnNewButton.setBounds(512, 385, 147, 27);
//		contentPane.add(btnNewButton);
//		
//		JButton btnRegistrarUsuario = new JButton("Crear cuenta");
//		btnRegistrarUsuario.setForeground(new Color(29, 17, 40));
//		btnRegistrarUsuario.setBackground(new Color(83, 82, 90));
//		btnRegistrarUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
//		btnRegistrarUsuario.setBounds(512, 449, 147, 27);
//		btnRegistrarUsuario.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				CrearCuenta crearCuenta = new CrearCuenta(api);
//				crearCuenta.setLocationRelativeTo(null);
//				crearCuenta.setVisible(true);
//			}
//		});
//		contentPane.add(btnRegistrarUsuario);
//		
//		JLabel lblNewLabel = new JLabel("LABPROJECT\r\n");
//		lblNewLabel.setBackground(new Color(240, 240, 240));
//		lblNewLabel.setForeground(new Color(29, 17, 40));
//		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
//		lblNewLabel.setBounds(26, 212, 206, 39);
//		contentPane.add(lblNewLabel);
//		
//		JLabel lblTrabajarDeManera = new JLabel("y a trabajar en equipo");
//		lblTrabajarDeManera.setForeground(new Color(255, 255, 255));
//		lblTrabajarDeManera.setFont(new Font("Segoe UI", Font.PLAIN, 25));
//		lblTrabajarDeManera.setBounds(26, 336, 369, 39);
//		contentPane.add(lblTrabajarDeManera);
//		
//		JLabel lblAOrganizarTus_1 = new JLabel("a organizar tus projectos");
//		lblAOrganizarTus_1.setForeground(Color.WHITE);
//		lblAOrganizarTus_1.setFont(new Font("Segoe UI", Font.PLAIN, 25));
//		lblAOrganizarTus_1.setBounds(26, 299, 296, 39);
//		contentPane.add(lblAOrganizarTus_1);
//		
//		JLabel lblAOrganizarTus_1_1 = new JLabel("LabProject te ayuda");
//		lblAOrganizarTus_1_1.setForeground(Color.WHITE);
//		lblAOrganizarTus_1_1.setFont(new Font("Segoe UI", Font.PLAIN, 25));
//		lblAOrganizarTus_1_1.setBounds(26, 261, 296, 39);
//		contentPane.add(lblAOrganizarTus_1_1);
//		
//		txtCorreoElectronico = new JTextField();
//		txtCorreoElectronico.setFont(new Font("Segoe UI", Font.PLAIN, 10));
//		txtCorreoElectronico.setText("Correo electronico");
//		txtCorreoElectronico.setBounds(512, 311, 147, 27);
//		contentPane.add(txtCorreoElectronico);
//		txtCorreoElectronico.setColumns(10);
//		
//		txtContrasea = new JTextField();
//		txtContrasea.setFont(new Font("Segoe UI", Font.PLAIN, 10));
//		txtContrasea.setText("Contraseña");
//		txtContrasea.setColumns(10);
//		txtContrasea.setBounds(512, 348, 147, 27);
//		contentPane.add(txtContrasea);
//		
//		salirMenuItem.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				System.exit(0);
//			}
//			
//		});
//	}
//}