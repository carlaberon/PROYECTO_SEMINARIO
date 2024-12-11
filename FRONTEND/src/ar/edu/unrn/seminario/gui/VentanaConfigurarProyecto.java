package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataEmptyException;

import ar.edu.unrn.seminario.exception.NotNullException;

public class VentanaConfigurarProyecto extends JFrame {

	private static final long serialVersionUID = 1L;
	List<String> prioridades = Arrays.asList("Alta", "Media", "Baja");
	private JPanel contentPane;
	private JTextField textField_Nombre;
	private JComboBox<String> prioridadComboBox;
	private JLabel descripcion;
	private JTextField textField_Descripcion;
	private JButton aceptar;
	private JButton cancelar;
	private ProyectoDTO proyectoActual;
	
	public VentanaConfigurarProyecto(IApi api) {
		ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
		
		this.proyectoActual = api.getProyectoActual();
		
		setTitle(labels.getString("ventana.modificarProyecto"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(81, 79, 89));
		setContentPane(contentPane);
		
		JLabel lblModificarProyecto = new JLabel(labels.getString("ventana.modificarProyecto"));
		lblModificarProyecto.setBackground(new Color(240, 240, 240));
		lblModificarProyecto.setForeground(new Color(29, 17, 40));
		lblModificarProyecto.setFont(new Font("Segoe UI Black", Font.BOLD, 35));
		lblModificarProyecto.setBounds(58, 47, 374, 39);
		contentPane.add(lblModificarProyecto);
		
		JLabel lblNombre = new JLabel(labels.getString("menu.nombreProyecto"));
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNombre.setBounds(83, 183, 123, 44);
		contentPane.add(lblNombre);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setBounds(216, 196, 451, 26);
		contentPane.add(textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel lblPrioridad = new JLabel(labels.getString("campo.prioridad"));
		lblPrioridad.setForeground(Color.WHITE);
		lblPrioridad.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblPrioridad.setBounds(83, 237, 93, 44);
		contentPane.add(lblPrioridad);
		
		prioridadComboBox = new JComboBox<>();
		prioridadComboBox.setForeground(new Color(29, 17, 40));
		prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		prioridadComboBox.setBounds(216, 251, 451, 26);
		contentPane.add(prioridadComboBox);

		for (String prioridad : prioridades) {
			prioridadComboBox.addItem(prioridad);
		}
		
		descripcion = new JLabel(labels.getString("campo.descripcion"));
		descripcion.setForeground(Color.WHITE);
		descripcion.setFont(new Font("Segoe UI", Font.BOLD, 18));
		descripcion.setBounds(83, 291, 123, 44);
		contentPane.add(descripcion);
		textField_Descripcion = new JTextField();
		textField_Descripcion.setColumns(10);
		textField_Descripcion.setBounds(216, 306, 451, 26);
		contentPane.add(textField_Descripcion);
		
		mostrarDatosActuales(api);
		aceptar = new JButton("Aceptar");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.setForeground(new Color(255, 255, 255));
		aceptar.setBackground(new Color(89, 65, 169));
		aceptar.setBounds(452, 573, 147, 27);
		aceptar.addActionListener(e -> {
				String prioridadSeleccionada = api.obtenerPrioridadPorIndex(prioridadComboBox.getSelectedIndex()+1);
				try {
					
					if (prioridadSeleccionada.equals(proyectoActual.getPrioridad())  && textField_Nombre.getText().equals(proyectoActual.getNombre()) && textField_Descripcion.getText().equals(proyectoActual.getDescripcion()) ) {
						JOptionPane.showMessageDialog(null, labels.getString("mensaje.noModificoCampos"), labels.getString("titulo.modificarProyecto"), JOptionPane.QUESTION_MESSAGE);
		            }
					else {
						int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
								"Estas seguro que queres modificar el proyecto?", "Confirmar cambio de estado.",
								JOptionPane.YES_NO_OPTION);
						
						if (opcionSeleccionada == JOptionPane.YES_OPTION) {
							api.modificarProyecto(api.getProyectoActual().getId(), textField_Nombre.getText(), prioridadSeleccionada, textField_Descripcion.getText());
							JOptionPane.showMessageDialog(null, labels.getString("mensaje.modificacionExitosa"), "Info", JOptionPane.INFORMATION_MESSAGE);
							api.setProyectoActual(api.getProyectoActual().getId()); 
							new VentanaResumen(api).setVisible(true);
							dispose();
						}
					}
					
				} catch (NotNullException e1) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + labels.getString(e1.getMessage()) + labels.getString("mensaje.null"), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataEmptyException e2) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + labels.getString(e2.getMessage()) + labels.getString("mensaje.empty"), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DataBaseConnectionException e3) {
					JOptionPane.showMessageDialog(null,labels.getString(e3.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);;
				}
			}
		);
		contentPane.add(aceptar);
		
		cancelar = new JButton("Cancelar");
		cancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		cancelar.setForeground(new Color(29, 17, 40));
		cancelar.setBackground(new Color(229, 212, 237));
		cancelar.setBounds(627, 573, 147, 27);
		cancelar.addActionListener(e -> {
				new VentanaResumen(api).setVisible(true);
				dispose();
			}
		);
		contentPane.add(cancelar);
		
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent e) {
        		new VentanaResumen(api).setVisible(true);
        	}
		});
	}
	
	private void mostrarDatosActuales(IApi api) {
            String nombreProyecto = api.getProyectoActual().getNombre();
            String prioridadProyecto = api.getProyectoActual().getPrioridad();
            String descripcionProyecto = api.getProyectoActual().getDescripcion();
            textField_Nombre.setText(nombreProyecto);
            prioridadComboBox.setSelectedItem(prioridadProyecto);
            textField_Descripcion.setText(descripcionProyecto);
    }
}
