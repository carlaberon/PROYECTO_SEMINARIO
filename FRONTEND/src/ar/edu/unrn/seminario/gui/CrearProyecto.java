package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.PersistenceApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;

import javax.swing.JTextPane;
import javax.swing.JTree;
import java.awt.Font;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;

public class CrearProyecto extends JFrame {
	
	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
//	 descomentar para que tome el idioma ingles (english)

	//ResourceBundle labels = ResourceBundle.getBundle("labels");
	
	
    List<String> prioridades = Arrays.asList(labels.getString("prioridad.alta"),labels.getString("prioridad.media"), labels.getString("prioridad.baja"));
	private JPanel contentPane;
	private JTextField nombreProyectoTextField;
	private JComboBox<String> proyectoComboBox;
	private IApi api;
	private JTextField descripcionTextField;
	
	public CrearProyecto(IApi api) {
		this.api = api;
		
		setTitle(labels.getString("ventana.crearProyecto"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		setSize(900,600);
		getContentPane().setLayout(null);
		
		contentPane = new JPanel();
		contentPane.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		contentPane.setBackground(new Color(81, 79, 89));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		JLabel nombreProyecto = new JLabel(labels.getString("menu.nombreProyecto"));
		nombreProyecto.setForeground(new Color(240, 240, 240));
		nombreProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		nombreProyecto.setBounds(88, 93, 227, 39);
		contentPane.add(nombreProyecto);

		nombreProyectoTextField = new JTextField();
		nombreProyectoTextField.setBounds(325, 105, 390, 25);
		contentPane.add(nombreProyectoTextField);
		nombreProyectoTextField.setColumns(10);
		
		descripcionTextField = new JTextField();
		descripcionTextField.setColumns(10);
		descripcionTextField.setBounds(325, 157, 390, 25);
		contentPane.add(descripcionTextField);
		
		JComboBox<String> prioridadComboBox = new JComboBox<>();
		prioridadComboBox.setForeground(new Color(29, 17, 40));
		prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		prioridadComboBox.setBounds(325, 205, 390, 25);
		contentPane.add(prioridadComboBox);
		
	

		// Llenar el JComboBox con las claves del mapa de prioridad
        for (String prioridad : prioridades) {
            prioridadComboBox.addItem(prioridad);
        }

		JButton aceptarButton = new JButton(labels.getString("boton.guardar"));
		aceptarButton.setForeground(new Color(229, 212, 237));
		aceptarButton.setBackground(new Color(89, 65, 169));
		aceptarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptarButton.setBounds(395, 398, 147, 27);
		contentPane.add(aceptarButton);
		aceptarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreNuevoProyecto = nombreProyectoTextField.getText();
				String descripcionNueva = descripcionTextField.getText();
                String prioridadSeleccionadaNueva = (String) prioridadComboBox.getSelectedItem();
                
                

				try {
					// Verificar si no se seleccionó una prioridad
		            if (prioridadSeleccionadaNueva == null || prioridadSeleccionadaNueva.isEmpty()) {
		                throw new DataEmptyException(labels.getString("mensaje.prioridad"));
		            }
			
					
					// Crear un nuevo proyecto
	                api.crearProyecto(nombreNuevoProyecto, api.getUsuarioActual().getUsername(), "EN CURSO", descripcionNueva, prioridadSeleccionadaNueva);
	                JOptionPane.showMessageDialog(null, labels.getString("mensaje.proyectoCreado"), "Info", JOptionPane.INFORMATION_MESSAGE);
	                setVisible(false);
	                dispose();
	                new Inicio(api).setVisible(true);
				} catch (NotNullException ex) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + ex.getMessage() + labels.getString("mensaje.null"), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataEmptyException ex) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + ex.getMessage() + labels.getString("mensaje.empty"), "Error", JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		JButton cancelarButton = new JButton(labels.getString("boton.cancelar"));
		cancelarButton.setForeground(new Color(29, 17, 40));
		cancelarButton.setBackground(new Color(229, 212, 237));
		cancelarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
				Inicio inicio;
				try {
					inicio = new Inicio(api);
					inicio.setVisible(true);
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
               
			}
		});
		cancelarButton.setBounds(568, 398, 147, 27);
		contentPane.add(cancelarButton);



		JLabel subproyectoLabel = new JLabel(labels.getString("campo.subproyecto"));
        subproyectoLabel.setForeground(new Color(240, 240, 240));
        subproyectoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        subproyectoLabel.setBounds(88, 233, 167, 39);
        contentPane.add(subproyectoLabel);



        proyectoComboBox = new JComboBox<>();
        proyectoComboBox.setForeground(new Color(29, 17, 40));
        proyectoComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        proyectoComboBox.setBounds(325, 245, 390, 25);
        contentPane.add(proyectoComboBox);
        
		proyectoComboBox.addItem("");
	
		JLabel lblNewLabel = new JLabel(labels.getString("campo.nuevoProyecto"));
		lblNewLabel.setForeground(new Color(29, 17, 40));
		lblNewLabel.setFont(new Font("Segoe UI", Font.PLAIN, 35));
		lblNewLabel.setBounds(41, 10, 291, 73);
		contentPane.add(lblNewLabel);
		
		JLabel lblDescripcion = new JLabel(labels.getString("campo.descripcion"));
		lblDescripcion.setForeground(UIManager.getColor("Button.background"));
		lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblDescripcion.setBounds(88, 142, 227, 39);
		contentPane.add(lblDescripcion);
		
		JLabel lblPrioridad = new JLabel(labels.getString("campo.prioridad"));
		lblPrioridad.setForeground(UIManager.getColor("Button.background"));
		lblPrioridad.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblPrioridad.setBounds(88, 191, 227, 39);
		contentPane.add(lblPrioridad);		
		setLocationRelativeTo(null); //Centrar frame en la pantalla
	}
	
    /*public static void main(String[] args) throws NotNullException, DataEmptyException, InvalidDateException{
		
		IApi api = new PersistenceApi();
		UsuarioDTO usuario = api.obtenerUsuario("ldifabio");
		api.setUsuarioActual(usuario.getUsername());
		CrearProyecto crearProyectoFrame = new CrearProyecto(api);
		crearProyectoFrame.setVisible(true);
	}*/
}