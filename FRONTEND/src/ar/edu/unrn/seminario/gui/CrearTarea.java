package ar.edu.unrn.seminario.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
import ar.edu.unrn.seminario.dto.ProyectoDTO;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.modelo.Rol;
import ar.edu.unrn.seminario.modelo.Tarea;
import ar.edu.unrn.seminario.modelo.Usuario;

import javax.swing.JTextArea;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class CrearTarea extends JFrame {

    private JPanel contentPane;
    private JTextField nombreTareaTextField;
    private JComboBox<String> proyectoTareaComboBox; // ComboBox para seleccionar proyecto
    private JComboBox<String> asignarUsuarioComboBox; // ComboBox para seleccionar usuario
    private JTextField prioridadTareaTextField;

    private List<ProyectoDTO> proyectos = new ArrayList<>();
    private List<UsuarioDTO> usuarios = new ArrayList<>();

    private VentanaTareas ventanaTareas;

    public CrearTarea(IApi api, JFrame ventanaTareas) {

        this.ventanaTareas = (VentanaTareas) ventanaTareas;
        this.usuarios = api.obtenerUsuarios();

        this.proyectos = api.obtenerProyectos();// con esto le envio directamente los proyectos creados

        setTitle("Crear Tarea");
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

        prioridadTareaTextField = new JTextField();
        prioridadTareaTextField.setBounds(190, 138, 160, 22);
        contentPane.add(prioridadTareaTextField);
        prioridadTareaTextField.setColumns(10);

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
                    String prioridadTarea = prioridadTareaTextField.getText();
                    UsuarioDTO usuario = usuarios.get(selectedUserIndex);
                    String descripcionTarea = textAreaDescription.getText();
                    Date fechaInicioDate = dateChooserInicio.getDate();
                    Date fechaFinDate = dateChooserFin.getDate();
                    
                		//Convertir Date a Localdatetime, si no cargo una fecha lanza un nullpointer
                        LocalDateTime fechaInicioLocalDateTime = fechaInicioDate.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();

                        LocalDateTime fechaFinLocalDateTime = fechaFinDate.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime();
                        
                     // Crear una nueva tarea
                        Tarea tarea = new Tarea(
                                nombreTarea, 
                                proyectoSeleccionado, 
                                prioridadTarea, 
                                usuario.getNombre(),  
                                false,   
                                descripcionTarea, 
                                fechaInicioLocalDateTime, 
                                fechaFinLocalDateTime
                            );
                		
                        api.añadirTareaAProyecto(proyectoSeleccionado, tarea);
                		
                        ((VentanaTareas) ventanaTareas).actualizarTabla();
                        
                        JOptionPane.showMessageDialog(null, "Tarea creada con éxito!", "Info", JOptionPane.INFORMATION_MESSAGE);
                        setVisible(false);
                        dispose();
                       
                	
                	} catch (NullPointerException excepcion) {
                		
                		JOptionPane.showMessageDialog(null,"Completar los campos de fecha", "Error", JOptionPane.ERROR_MESSAGE);
                	} catch (DataEmptyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (NotNullException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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


