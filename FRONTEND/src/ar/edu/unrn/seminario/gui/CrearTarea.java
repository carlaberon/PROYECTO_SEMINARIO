package ar.edu.unrn.seminario.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;
import javax.swing.JTextArea;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class CrearTarea extends JFrame {

    private JPanel contentPane;
    private JTextField nombreTareaTextField;
    private JComboBox<String> asignarUsuarioComboBox; // ComboBox para seleccionar usuario
    List<String> prioridades = Arrays.asList("Alta", "Media", "Baja");
    private List<UsuarioDTO> usuarios = new ArrayList<>();

    
    private IApi api;
    
    public CrearTarea(IApi api) {
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
        this.api = api; 
        this.usuarios = api.obtenerMiembrosDeUnProyecto(api.getProyectoActual().getId());
        

        setTitle("Crear Tarea");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 600, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel nombreTareaLabel = new JLabel("Nombre de Tarea:");
        nombreTareaLabel.setBounds(43, 53, 150, 16);
        contentPane.add(nombreTareaLabel);

        nombreTareaTextField = new JTextField();
        nombreTareaTextField.setBounds(190, 50, 160, 22);
        contentPane.add(nombreTareaTextField);
        nombreTareaTextField.setColumns(10);

        JLabel asignarUsuarioLabel = new JLabel("Asignar Usuario:");
        asignarUsuarioLabel.setBounds(43, 100, 150, 16);
        contentPane.add(asignarUsuarioLabel);

        asignarUsuarioComboBox = new JComboBox<>();
        asignarUsuarioComboBox.setBounds(190, 97, 160, 22);
        
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
        dateChooserInicio.setDateFormatString("dd-MM-yyyy");
        dateChooserInicio.setBounds(190, 183, 160, 19);
        contentPane.add(dateChooserInicio);
        
        JDateChooser dateChooserFin = new JDateChooser();
        dateChooserFin.setDateFormatString("dd-MM-yyyy");
        dateChooserFin.setBounds(190, 232, 160, 19);
        contentPane.add(dateChooserFin);

        contentPane.revalidate();
        contentPane.repaint();

        
        aceptarButton.addActionListener(ev -> {
            	try {
                    int selectedUserIndex = asignarUsuarioComboBox.getSelectedIndex();
                    String nombreTarea = nombreTareaTextField.getText();
                    String prioridadTarea = (String) prioridadComboBox.getSelectedItem();
                    UsuarioDTO usuario = usuarios.get(selectedUserIndex);
                    String descripcionTarea = textAreaDescription.getText();
                    Date fechaInicioDate = dateChooserInicio.getDate();
                    Date fechaFinDate = dateChooserFin.getDate();
                    
                	//Convertir Date a Localdate, si no cargo una fecha lanza un nullpointer
                    LocalDate fechaInicioLocalDate = fechaInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    LocalDate fechaFinLocalDate = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        
                      
                    api.registrarTarea(nombreTarea, api.getProyectoActual().getId(),prioridadTarea,usuario.getUsername(),"EN CURSO", descripcionTarea, fechaInicioLocalDate, fechaFinLocalDate);
                      
                    JOptionPane.showMessageDialog(null, "Tarea creada con éxito!", "Info", JOptionPane.INFORMATION_MESSAGE);
					
                    new VentanaTareas(api).setVisible(true);
					dispose();
                       
                	} catch (NullPointerException e) {
                		JOptionPane.showMessageDialog(null,"Las fechas no pueden ser nulas.", "Error", JOptionPane.ERROR_MESSAGE);
                	} catch (DataEmptyException e) {
                		JOptionPane.showMessageDialog(null,"La tarea debe tener" +" " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (NotNullException e) {
						JOptionPane.showMessageDialog(null,"La tarea debe tener" +" " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} catch (InvalidDateException e) {
						JOptionPane.showMessageDialog(null,"Ingrese fechas válidas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					} 
            }
        );

        cancelarButton.addActionListener(e -> {
				new VentanaTareas(api).setVisible(true);
                dispose();
            }
        );
        
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() { 
          	public void windowClosing(WindowEvent e) {
          		new VentanaTareas(api).setVisible(true);
          	}
    	});
    }
}

