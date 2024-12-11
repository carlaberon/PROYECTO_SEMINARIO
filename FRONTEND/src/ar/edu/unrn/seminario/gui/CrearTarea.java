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
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
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
    private List<UsuarioDTO> usuarios = new ArrayList<>();

    
    private IApi api;
    
    public CrearTarea(IApi api) {
    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
        this.api = api; 
        try {
			this.usuarios = api.obtenerMiembrosDeUnProyecto(api.getProyectoActual().getId());
		} catch (DataBaseConnectionException e1) {
			JOptionPane.showMessageDialog(null,labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		}
        

        setTitle("Crear Tarea");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(200, 200, 600, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel nombreTareaLabel = new JLabel(labels.getString("campo.nombreTarea"));
        nombreTareaLabel.setBounds(43, 53, 150, 16);
        contentPane.add(nombreTareaLabel);

        nombreTareaTextField = new JTextField();
        nombreTareaTextField.setBounds(190, 50, 160, 22);
        contentPane.add(nombreTareaTextField);
        nombreTareaTextField.setColumns(10);

        JLabel asignarUsuarioLabel = new JLabel(labels.getString("campo.asignarUsuario"));
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

        JLabel prioridadTareaLabel = new JLabel(labels.getString("campo.prioridad"));
        prioridadTareaLabel.setBounds(43, 140, 150, 16);
        contentPane.add(prioridadTareaLabel);
        JComboBox<String> prioridadComboBox = new JComboBox<>();
		prioridadComboBox.setForeground(new Color(29, 17, 40));
		prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		prioridadComboBox.setBounds(190, 133, 160, 25);
		contentPane.add(prioridadComboBox);
		prioridadComboBox.addItem("");
		for (String prioridad :Arrays.asList(labels.getString("prioridad.alta"), labels.getString("prioridad.media"), labels.getString("prioridad.baja"))) {
            prioridadComboBox.addItem(prioridad);
        }

        JLabel lblDescripcin = new JLabel(labels.getString("campo.descripcion"));
        lblDescripcin.setBounds(43, 291, 150, 16);
        contentPane.add(lblDescripcin);

        JTextArea textAreaDescription = new JTextArea();
        textAreaDescription.setBounds(208, 291, 329, 111);
        contentPane.add(textAreaDescription);

        JLabel lblFechaInicio = new JLabel(labels.getString("campo.fechaInicio"));
        lblFechaInicio.setBounds(43, 183, 150, 16);
        contentPane.add(lblFechaInicio);

        JLabel lblFechaFin = new JLabel(labels.getString("campo.fechaFin"));
        lblFechaFin.setBounds(43, 232, 150, 16);
        contentPane.add(lblFechaFin);

        JButton aceptarButton = new JButton(labels.getString("boton.guardar"));
        aceptarButton.setBounds(312, 438, 97, 25);
        contentPane.add(aceptarButton);

        JButton cancelarButton = new JButton(labels.getString("boton.cancelar"));
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
                    String prioridadTarea = api.obtenerPrioridadPorIndex(prioridadComboBox.getSelectedIndex());
                    UsuarioDTO usuario = usuarios.get(selectedUserIndex);
                    String descripcionTarea = textAreaDescription.getText();
                    Date fechaInicioDate = dateChooserInicio.getDate();
                    Date fechaFinDate = dateChooserFin.getDate();
                    LocalDate fechaInicioLocalDate = null;
                    LocalDate fechaFinLocalDate = null;
                    
                    if(fechaInicioDate != null) 
	                	//Convertir Date a Localdate, si no cargo una fecha lanza un nullpointer
	                    fechaInicioLocalDate = fechaInicioDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    if(fechaFinDate != null)
	                    fechaFinLocalDate = fechaFinDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        
                      
                    api.registrarTarea(nombreTarea, api.getProyectoActual().getId(),prioridadTarea,usuario.getUsername(),"EN CURSO", descripcionTarea, fechaInicioLocalDate, fechaFinLocalDate);
                      
                    JOptionPane.showMessageDialog(null, labels.getString("mensaje.tareaCreada"), labels.getString("titulo.optionPaneCrearTarea"), JOptionPane.INFORMATION_MESSAGE);
					
                    new VentanaTareas(api).setVisible(true);
					dispose();
                       
            		} catch (DataEmptyException e1) {
                		JOptionPane.showMessageDialog(null, labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e1.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
					} catch (NotNullException e2) {
						JOptionPane.showMessageDialog(null,labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e2.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
					} catch (InvalidDateException e3) {
						JOptionPane.showMessageDialog(null,labels.getString("mensaje.fechasValidas") + labels.getString(e3.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
					} catch (DataBaseConnectionException e4) {
						JOptionPane.showMessageDialog(null,labels.getString(e4.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
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

