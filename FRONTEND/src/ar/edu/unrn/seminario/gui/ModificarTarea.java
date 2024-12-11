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
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

import javax.swing.JTextArea;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class ModificarTarea extends JFrame {
	 private JPanel contentPane;
	    private JTextField nombreTareaTextField;
	    private JComboBox<String> asignarUsuarioComboBox; // ComboBox para seleccionar usuario
	    private JComboBox<String> prioridadComboBox;
	    private JTextArea textAreaDescription;
	    private JDateChooser dateChooserInicio;
	    private JDateChooser dateChooserFin;
	    private List<UsuarioDTO> usuarios = new ArrayList<>();
	    private TareaDTO tarea;
	    private IApi api;
	    
	    public ModificarTarea(IApi api) {

	    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 
//			 descomentar para que tome el idioma ingles (english)

			//ResourceBundle labels = ResourceBundle.getBundle("labels");
	        this.api = api; 
	        try {
				this.usuarios = api.obtenerMiembrosDeUnProyecto(api.getProyectoActual().getId());
			} catch (DataBaseConnectionException e1) {
				JOptionPane.showMessageDialog(null,labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
	        this.tarea = api.getTareaActual();
	        
	        setTitle(labels.getString("menu.modificarTarea"));
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(200, 200, 600, 550);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        contentPane.setLayout(null);
	        setContentPane(contentPane);
	        
	        JLabel nombreTareaLabel = new JLabel(labels.getString("campo.nombreTarea"));
	        nombreTareaLabel.setBounds(43, 67, 150, 16);
	        contentPane.add(nombreTareaLabel);

	        nombreTareaTextField = new JTextField();
	        nombreTareaTextField.setBounds(190, 64, 160, 22);
	        contentPane.add(nombreTareaTextField);
	        nombreTareaTextField.setColumns(10);

	        JLabel asignarUsuarioLabel = new JLabel(labels.getString("campo.asignarUsuario"));
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

	        JLabel prioridadTareaLabel = new JLabel(labels.getString("campo.prioridad"));
	        prioridadTareaLabel.setBounds(43, 140, 150, 16);
	        contentPane.add(prioridadTareaLabel);
	        prioridadComboBox = new JComboBox<>();
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

	        textAreaDescription = new JTextArea();
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
	        
	        dateChooserInicio = new JDateChooser();
	        dateChooserInicio.setBounds(190, 183, 160, 19);
	        contentPane.add(dateChooserInicio);
	        
	        dateChooserFin = new JDateChooser();
	        dateChooserFin.getCalendarButton().addActionListener(e -> {
	        });
	        dateChooserFin.setBounds(190, 232, 160, 19);
	        contentPane.add(dateChooserFin);

	        cargarDatosTarea();
	        
	        aceptarButton.addActionListener(e -> {
	            	try {
	  
	                    int selectedUserIndex = asignarUsuarioComboBox.getSelectedIndex();
	                    String nuevoNombreTarea = nombreTareaTextField.getText();
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
                        
	                    api.modificarTarea(tarea.getId(), nuevoNombreTarea, prioridadTarea, usuario.getUsername(), "EN CURSO", descripcionTarea, fechaInicioLocalDate, fechaFinLocalDate);
	                    
	                      
	                    JOptionPane.showMessageDialog(null, labels.getString("mensaje.tareaModificada"), "Info", JOptionPane.INFORMATION_MESSAGE);
	                    new VentanaTareas(api).setVisible(true);
	                    dispose();
	                       
	                	
	                	} catch (DataEmptyException e1) {
	                		JOptionPane.showMessageDialog(null, labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e1.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
	                		
						} catch (NotNullException e1) {
							JOptionPane.showMessageDialog(null,labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e1.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
							
						} catch (InvalidDateException e1) {
							JOptionPane.showMessageDialog(null,labels.getString("mensaje.fechasValidas") + labels.getString(e1.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
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

	    private void cargarDatosTarea() {
            nombreTareaTextField.setText(tarea.getName());
            textAreaDescription.setText(tarea.getDescription());
            asignarUsuarioComboBox.setSelectedItem(tarea.getUser());
            prioridadComboBox.setSelectedItem(tarea.getPriority());

            dateChooserInicio.setDate(convertirADate(tarea.getInicio()));
            dateChooserFin.setDate(convertirADate(tarea.getFin()));
            
        }

        private Date convertirADate(LocalDate localDate) {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        }
    }


