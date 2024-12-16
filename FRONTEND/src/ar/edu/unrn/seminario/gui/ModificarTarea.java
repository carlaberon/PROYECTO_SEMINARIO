package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.TareaDTO;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
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
	    private UsuarioDTO usuarioActual;
	    
	    
	    public ModificarTarea(IApi api) {

	    	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("es")); 

	        this.api = api; 
	        try {
				this.usuarios = api.obtenerMiembrosDeUnProyecto(api.getProyectoActual().getId());
			} catch (DataBaseConnectionException e1) {
				JOptionPane.showMessageDialog(null,labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
	        this.tarea = api.getTareaActual();
	        this.usuarioActual = api.getUsuarioActual();
	        
	        setTitle(labels.getString("menu.modificarTarea"));
	        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        setBounds(50, 50, 1200, 650);
	        
	        contentPane = new JPanel();
	        contentPane.setLayout(new BorderLayout());
	        setContentPane(contentPane);
	        

	        // Menú superior
	        JMenuBar menuBar = new JMenuBar();
	        menuBar.setBackground(new Color(138, 102, 204));
	        menuBar.setPreferredSize(new Dimension(100, 50));

	        JMenu menuProyecto = new JMenu(labels.getString("menu.modificarTarea"));
	        menuProyecto.setForeground(Color.WHITE);
	        menuProyecto.setFont(new Font("Segoe UI", Font.BOLD, 18));
	        menuBar.add(menuProyecto);

	        JLabel appName = new JLabel(labels.getString("menu.proyecto"));
	        appName.setForeground(Color.WHITE);
	        appName.setFont(new Font("Segoe UI", Font.BOLD, 18));

	        JPanel centerPanel = new JPanel();
	        centerPanel.setOpaque(false);
	        centerPanel.add(appName);
	        menuBar.add(centerPanel);

	        JMenu accountMenu = new JMenu(usuarioActual.getUsername());
	        accountMenu.setForeground(Color.WHITE);
	        accountMenu.setFont(new Font("Segoe UI", Font.BOLD, 14));

	        JMenuItem logoutItem = new JMenuItem(labels.getString("menu.cerrarSesion"));
	        JMenuItem confItem = new JMenuItem(labels.getString("menu.configurarCuenta"));
	        accountMenu.add(confItem);
	        accountMenu.add(logoutItem);

	        logoutItem.addActionListener(e -> System.exit(0));
	        menuBar.add(accountMenu);
	        this.setJMenuBar(menuBar);

	        // Panel lateral
	        JPanel menuPanel = new JPanel();
	        menuPanel.setLayout(new GridLayout(7, 1, 10, 10));
	        menuPanel.setPreferredSize(new Dimension(200, 0));
	        menuPanel.setBackground(new Color(65, 62, 77));

	        String[] menuItems = { labels.getString("menu.tareas"), labels.getString("menu.volver") };
	        for (String item : menuItems) {
	            JButton menuButton = new JButton(item);
	            menuButton.setForeground(Color.WHITE);
	            menuButton.setBackground(new Color(83, 82, 90));
	            menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	            menuButton.setBorderPainted(false);
	            menuButton.setFocusPainted(false);
	            menuButton.setHorizontalAlignment(SwingConstants.LEFT);
	            menuButton.setMargin(new Insets(10, 10, 10, 10));
	            menuPanel.add(menuButton);

	            // Acción para botón "Volver"
	            if (item.equals("Volver") || item.equals("Return")) {
	                menuButton.addActionListener(e -> {
	                    new VentanaResumen(api).setVisible(true);
	                    dispose();
	                });
	            }
	        }

	        contentPane.add(menuPanel, BorderLayout.WEST);

	        // Panel central
	        JPanel centerPanel1 = new JPanel();
	        centerPanel1.setLayout(null); 
	        centerPanel1.setBackground(new Color(45, 44, 50));
	        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20));
	        contentPane.add(centerPanel1, BorderLayout.CENTER);
	        
	        // Labels y Componentes agregados a centerPanel1
	        JLabel nombreTareaLabel = new JLabel(labels.getString("campo.nombreTarea"));
	        nombreTareaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        nombreTareaLabel.setForeground(new Color(255, 255, 255));
	        nombreTareaLabel.setBounds(43, 67, 150, 16);
	        centerPanel1.add(nombreTareaLabel);

	        nombreTareaTextField = new JTextField();
	        nombreTareaTextField.setBounds(190, 64, 349, 22);
	        centerPanel1.add(nombreTareaTextField);
	        nombreTareaTextField.setColumns(10);

	        JLabel asignarUsuarioLabel = new JLabel(labels.getString("campo.asignarUsuario"));
	        asignarUsuarioLabel.setForeground(new Color(255, 255, 255));
	        asignarUsuarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        asignarUsuarioLabel.setBounds(43, 100, 150, 16);
	        centerPanel1.add(asignarUsuarioLabel);

	        asignarUsuarioComboBox = new JComboBox<>();
	        asignarUsuarioComboBox.setBounds(190, 100, 349, 22);
	        this.usuarios.stream().map(UsuarioDTO::getUsername).forEach(asignarUsuarioComboBox::addItem);
	        centerPanel1.add(asignarUsuarioComboBox);

	        JLabel prioridadTareaLabel = new JLabel(labels.getString("campo.prioridad"));
	        prioridadTareaLabel.setForeground(new Color(255, 255, 255));
	        prioridadTareaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        prioridadTareaLabel.setBounds(43, 140, 150, 16);
	        centerPanel1.add(prioridadTareaLabel);

	        prioridadComboBox = new JComboBox<>();
	        prioridadComboBox.setForeground(new Color(29, 17, 40));
	        prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
	        prioridadComboBox.setBounds(190, 133, 349, 25);
	        centerPanel1.add(prioridadComboBox);
	        prioridadComboBox.addItem("");
	        for (String prioridad : Arrays.asList(labels.getString("prioridad.alta"), labels.getString("prioridad.media"), labels.getString("prioridad.baja"))) {
	            prioridadComboBox.addItem(prioridad);
	        }

	        JLabel lblDescripcin = new JLabel(labels.getString("campo.descripcion"));
	        lblDescripcin.setForeground(new Color(255, 255, 255));
	        lblDescripcin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        lblDescripcin.setBounds(43, 291, 150, 16);
	        centerPanel1.add(lblDescripcin);

	        textAreaDescription = new JTextArea();
	        textAreaDescription.setBounds(208, 291, 329, 111);
	        centerPanel1.add(textAreaDescription);

	        JLabel lblFechaInicio = new JLabel(labels.getString("campo.fechaInicio"));
	        lblFechaInicio.setForeground(new Color(255, 255, 255));
	        lblFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        lblFechaInicio.setBounds(43, 183, 150, 16);
	        centerPanel1.add(lblFechaInicio);

	        JLabel lblFechaFin = new JLabel(labels.getString("campo.fechaFin"));
	        lblFechaFin.setForeground(new Color(255, 255, 255));
	        lblFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        lblFechaFin.setBounds(43, 232, 150, 16);
	        centerPanel1.add(lblFechaFin);

	        JButton aceptarButton = createButton(labels.getString("boton.guardar"), new Color(138, 102, 204));
	        aceptarButton.setBounds(312, 438, 97, 25);
	        centerPanel1.add(aceptarButton);

	        JButton cancelarButton = createButton(labels.getString("boton.cancelar"), new Color(138, 102, 204));
	        cancelarButton.setBounds(440, 438, 97, 25);
	        centerPanel1.add(cancelarButton);

	        dateChooserInicio = new JDateChooser();
	        dateChooserInicio.setBounds(190, 183, 349, 19);
	        centerPanel1.add(dateChooserInicio);

	        dateChooserFin = new JDateChooser();
	        dateChooserFin.getCalendarButton().addActionListener(e -> {});
	        dateChooserFin.setBounds(190, 232, 349, 19);
	        centerPanel1.add(dateChooserFin);

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
				        } catch (DataBaseConnectionException e1) {
				        	JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
						} catch (DataBaseUpdateException e1) {
							JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
						}	        
	            }				
	        );


	        cancelarButton.addActionListener(e -> {
	            new VentanaTareas(api).setVisible(true);
	            dispose();
	        });

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
	    
	    private JButton createButton(String text, Color backgroundColor) {
	        JButton button = new JButton(text);
	        button.setForeground(Color.WHITE);
	        button.setBackground(backgroundColor);
	        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	        button.setBorderPainted(false);
	        button.setFocusPainted(false);
	        button.setPreferredSize(new Dimension(120, 40));
	        return button;
	    }
	}