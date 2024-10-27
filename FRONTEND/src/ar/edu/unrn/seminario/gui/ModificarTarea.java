package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class ModificarTarea extends JFrame {
	 private JPanel contentPane;
	    private JTextField nombreTareaTextField;
	    private JComboBox<String> proyectoTareaComboBox; // ComboBox para seleccionar proyecto
	    private JComboBox<String> asignarUsuarioComboBox; // ComboBox para seleccionar usuario
	    private JTextField prioridadTareaTextField;
	    List<String> prioridades = Arrays.asList("alta", "media", "baja");
	    private List<ProyectoDTO> proyectos = new ArrayList<>();
	    private List<UsuarioDTO> usuarios = new ArrayList<>();

	    private IApi api;
	    public ModificarTarea(IApi api, String nombre) {

	        this.api = api; 
	        
	        this.usuarios = api.obtenerUsuarios();
	        //OBTENER NOMBRE DEL USUARIO ACTUAL
	        this.proyectos = api.obtenerProyectos(api.getUsuarioActual().getUsername());

	        //setTitle("MODIFICAR TAREA");
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(200, 200, 600, 550);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(null);
	        setContentPane(contentPane);

	        JLabel nombreTareaLabel = new JLabel("Nombre de Tarea:");
	        nombreTareaLabel.setBounds(43, 20, 150, 16);
	        contentPane.add(nombreTareaLabel);

	        nombreTareaTextField = new JTextField();
	        nombreTareaTextField.setBounds(190, 20, 160, 22);
	        contentPane.add(nombreTareaTextField);
	        nombreTareaTextField.setColumns(10);

	        JLabel proyectoTareaLabel = new JLabel("Proyecto:");
	        proyectoTareaLabel.setBounds(43, 60, 150, 16);
	        contentPane.add(proyectoTareaLabel);

	        proyectoTareaComboBox = new JComboBox<>();
	        proyectoTareaComboBox.setBounds(190, 60, 160, 22);
	        if (! this.proyectos.isEmpty() ) {
	        	for (ProyectoDTO proyecto : this.proyectos) {
	                proyectoTareaComboBox.addItem(proyecto.getNombre());
	            }
	        }
	        
	        contentPane.add(proyectoTareaComboBox);

	        JLabel asignarUsuarioLabel = new JLabel("Asignar Usuario:");
	        asignarUsuarioLabel.setBounds(43, 100, 150, 16);
	        contentPane.add(asignarUsuarioLabel);

	        asignarUsuarioComboBox = new JComboBox<>();
	        asignarUsuarioComboBox.setBounds(190, 100, 160, 22);
	        
	        if ( ! this.usuarios.isEmpty()) {
	        	 for (UsuarioDTO usuario : this.usuarios) {
	                 asignarUsuarioComboBox.addItem(usuario.getUsername());
	             }
	        }
	       
	        contentPane.add(asignarUsuarioComboBox);

	        JLabel prioridadTareaLabel = new JLabel("Prioridad:");
	        prioridadTareaLabel.setBounds(43, 140, 150, 16);
	        contentPane.add(prioridadTareaLabel);
	        JComboBox<String> prioridadComboBox = new JComboBox<>();
			prioridadComboBox.setForeground(new Color(29, 17, 40));
			prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
			prioridadComboBox.setBounds(190, 133, 160, 25);
			contentPane.add(prioridadComboBox);
			
			prioridadComboBox.addItem("");

			// Llenar el JComboBox con las claves del mapa de prioridad
	        for (String prioridad : prioridades) {
	            prioridadComboBox.addItem(prioridad);
	        }

	        JLabel lblDescripcin = new JLabel("Descripción:");
	        lblDescripcin.setBounds(43, 291, 150, 16);
	        contentPane.add(lblDescripcin);

	        JTextArea textAreaDescription = new JTextArea();
	        textAreaDescription.setBounds(208, 291, 329, 111);
	        contentPane.add(textAreaDescription);

	        JLabel lblFechaInicio = new JLabel("Fecha inicio:");
	        lblFechaInicio.setBounds(43, 183, 150, 16);
	        contentPane.add(lblFechaInicio);

	        JLabel lblFechaFin = new JLabel("Fecha fin:");
	        lblFechaFin.setBounds(43, 232, 150, 16);
	        contentPane.add(lblFechaFin);

	        JButton aceptarButton = new JButton("Aceptar");
	        aceptarButton.setBounds(312, 438, 97, 25);
	        contentPane.add(aceptarButton);

	        JButton cancelarButton = new JButton("Cancelar");
	        cancelarButton.setBounds(440, 438, 97, 25);
	        contentPane.add(cancelarButton);
	        
	        JDateChooser dateChooserInicio = new JDateChooser();
	        dateChooserInicio.setBounds(190, 183, 70, 19);
	        contentPane.add(dateChooserInicio);
	        
	        JDateChooser dateChooserFin = new JDateChooser();
	        dateChooserFin.setBounds(190, 232, 70, 19);
	        contentPane.add(dateChooserFin);


	        
	        aceptarButton.addActionListener(new ActionListener() { 
	            public void actionPerformed(ActionEvent arg0) {
	             
	            	try {
	                    int selectedUserIndex = asignarUsuarioComboBox.getSelectedIndex();
	                    String nombreTarea = nombreTareaTextField.getText();
	                    String proyectoSeleccionado = (String) proyectoTareaComboBox.getSelectedItem();
	                    String prioridadTarea = (String) prioridadComboBox.getSelectedItem();
	                    UsuarioDTO usuario = usuarios.get(selectedUserIndex);
	                    //String name = usuario.getUsername();
	                    String descripcionTarea = textAreaDescription.getText();
	                    Date fechaInicioDate = dateChooserInicio.getDate();
	                    UsuarioDTO nombres = null;
	                    Date fechaFinDate = dateChooserFin.getDate();
	                    
	                		//Convertir Date a Localdatetime, si no cargo una fecha lanza un nullpointer
	                        LocalDateTime fechaInicioLocalDateTime = fechaInicioDate.toInstant()
	                                .atZone(ZoneId.systemDefault())
	                                .toLocalDateTime();

	                        LocalDateTime fechaFinLocalDateTime = fechaFinDate.toInstant()
	                                .atZone(ZoneId.systemDefault())
	                                .toLocalDateTime();
	                        
	                       
	                      api.modificarTarea(nombre,nombreTarea,proyectoSeleccionado,prioridadTarea,usuario.getUsername(), false, descripcionTarea,fechaInicioLocalDateTime, fechaFinLocalDateTime);
	                       
	                        JOptionPane.showMessageDialog(null, "Tarea creada con éxito!", "Info", JOptionPane.INFORMATION_MESSAGE);
	                        setVisible(false);
	                        dispose();
	                       
	                	
	                	} catch (NullPointerException excepcion) {
	                		
	                		JOptionPane.showMessageDialog(null,"Completar los campos de fecha", "Error", JOptionPane.ERROR_MESSAGE);
	                	} catch (DataEmptyException e) {
	                		JOptionPane.showMessageDialog(null,"La tarea debe tener" +" " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	                		
						} catch (NotNullException e) {
							
							JOptionPane.showMessageDialog(null,"La tarea debe tener" +" " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
							
						}
	                	
	     

	        
	            }

				
	        });

	        cancelarButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                setVisible(false);
	                dispose();
	            }
	        });
	    }

}


