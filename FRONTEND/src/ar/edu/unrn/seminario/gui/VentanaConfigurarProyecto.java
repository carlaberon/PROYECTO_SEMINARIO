package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	
	public VentanaConfigurarProyecto(IApi api) {
		setTitle("Modificar Proyecto");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 800, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(81, 79, 89));
		setContentPane(contentPane);
		
		JLabel lblModificarProyecto = new JLabel("Modificar Proyecto");
		lblModificarProyecto.setBackground(new Color(240, 240, 240));
		lblModificarProyecto.setForeground(new Color(29, 17, 40));
		lblModificarProyecto.setFont(new Font("Segoe UI Black", Font.BOLD, 35));
		lblModificarProyecto.setBounds(58, 47, 374, 39);
		contentPane.add(lblModificarProyecto);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setForeground(new Color(255, 255, 255));
		lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 18));
		lblNombre.setBounds(83, 183, 93, 44);
		contentPane.add(lblNombre);
		
		textField_Nombre = new JTextField();
		textField_Nombre.setBounds(216, 198, 451, 26);
		contentPane.add(textField_Nombre);
		textField_Nombre.setColumns(10);
		
		JLabel lblPrioridad = new JLabel("Prioridad:");
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
		
		descripcion = new JLabel("Descripcion:");
		descripcion.setForeground(Color.WHITE);
		descripcion.setFont(new Font("Segoe UI", Font.BOLD, 18));
		descripcion.setBounds(83, 291, 123, 44);
		contentPane.add(descripcion);
		textField_Descripcion = new JTextField();
		textField_Descripcion.setColumns(10);
		textField_Descripcion.setBounds(216, 306, 451, 26);
		contentPane.add(textField_Descripcion);
		
		mostararDatosActuales(api);
		aceptar = new JButton("Aceptar");
		aceptar.setFont(new Font("Segoe UI", Font.BOLD, 14));
		aceptar.setForeground(new Color(255, 255, 255));
		aceptar.setBackground(new Color(89, 65, 169));
		aceptar.setBounds(452, 573, 147, 27);
		aceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String prioridadSeleccionada = (String) prioridadComboBox.getSelectedItem();
				try {
					
					if (prioridadSeleccionada.equals(api.getProyectoActual().getPrioridad())  && textField_Nombre.getText().equals(api.getProyectoActual().getNombre()) && textField_Descripcion.getText().equals(api.getProyectoActual().getDescripcion()) ) {
						JOptionPane.showMessageDialog(null, "No se cambio ningun campo.", "No se realizo ningun cambio!", JOptionPane.QUESTION_MESSAGE);
		            }
					else {
					
					int opcionSeleccionada = JOptionPane.showConfirmDialog(null,
							"Estas seguro que queres modificar el proyecto?", "Confirmar cambio de estado.",
							JOptionPane.YES_NO_OPTION);
					
					if (opcionSeleccionada == JOptionPane.YES_OPTION) {
						api.modificarProyecto(api.getProyectoActual().getId(), textField_Nombre.getText(), prioridadSeleccionada, textField_Descripcion.getText());
						JOptionPane.showMessageDialog(null, "Modificacion realizada con exito!", "Info", JOptionPane.INFORMATION_MESSAGE);
						api.setProyectoActual(api.getProyectoActual().getId()); //Para ver cambios en frame Resumen
						new VentanaResumen(api).setVisible(true);
						dispose();
					}
					}
					
				} catch (NotNullException e1) {
		            JOptionPane.showMessageDialog(null, "El campo " + e1.getMessage() + " no puede ser nulo.", "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataEmptyException e2) {
		            JOptionPane.showMessageDialog(null, "El campo " + e2.getMessage() + " esta vacío.", "Error", JOptionPane.ERROR_MESSAGE);
				}
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
				try {
					new VentanaResumen(api).setVisible(true);
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
				dispose();
			}
		});
		contentPane.add(cancelar);
		
		setLocationRelativeTo(null);
		
		addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent e) {
        		try {
					new VentanaResumen(api).setVisible(true);
				} catch (NotNullException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DataEmptyException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        	}
		});
	}
	
	private boolean esDatoVacio(String dato) {
		return dato.equals("");
	}
	
	private void mostararDatosActuales(IApi api) {
        try {
            String nombreProyecto = api.getProyectoActual().getNombre();
            String prioridadProyecto = api.getProyectoActual().getPrioridad();
            String descripcionProyecto = api.getProyectoActual().getDescripcion();
            textField_Nombre.setText(nombreProyecto);
            prioridadComboBox.setSelectedItem(prioridadProyecto);
            textField_Descripcion.setText(descripcionProyecto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los datos del proyecto.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	/*public static void main(String[] args) throws NotNullException, DataEmptyException, InvalidDateException{
	
	IApi api = new PersistenceApi();
	//UsuarioDTO usuario = api.obtenerUsuario("Gabriel");
	api.setUsuarioActual("Gabriel");
	api.setProyectoActual("proyecto fenix");
	VentanaConfigurarProyecto crearProyectoFrame = new VentanaConfigurarProyecto(api);
	crearProyectoFrame.setVisible(true);
}*/
}
