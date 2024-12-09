package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Date;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.InvalidDateException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class CrearTarea extends JFrame {

    private JPanel contentPane;
    private JTextField nombreTareaTextField;
    private JComboBox<String> asignarUsuarioComboBox; // ComboBox para seleccionar usuario
    private List<UsuarioDTO> usuarios = new ArrayList<>();
    private UsuarioDTO usuarioActual;

    private IApi api;

    public CrearTarea(IApi api) {
        ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));
        this.api = api;
        this.usuarios = api.obtenerMiembrosDeUnProyecto(api.getProyectoActual().getId());
        this.usuarioActual = api.getUsuarioActual();


        setTitle(labels.getString("menu.ventanaCrearTarea"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(labels.getString("menu.ventanaCrearTarea"));
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
        centerPanel1.setLayout(null); // Usar diseño absoluto para respetar los bounds definidos
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPane.add(centerPanel1, BorderLayout.CENTER);

        // Formulario para datos de tarea
        JLabel nombreTareaLabel = new JLabel(labels.getString("campo.nombreTarea"));
        nombreTareaLabel.setForeground(new Color(255, 255, 255));
        nombreTareaLabel.setBounds(50, 50, 150, 20);
        nombreTareaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Estilo aplicado
        centerPanel1.add(nombreTareaLabel);

        nombreTareaTextField = new JTextField();
        nombreTareaTextField.setBounds(220, 50, 400, 20);
        centerPanel1.add(nombreTareaTextField);

        JLabel asignarUsuarioLabel = new JLabel(labels.getString("campo.asignarUsuario"));
        asignarUsuarioLabel.setForeground(new Color(255, 255, 255));
        asignarUsuarioLabel.setBounds(50, 100, 150, 20);
        asignarUsuarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); 
        centerPanel1.add(asignarUsuarioLabel);

        asignarUsuarioComboBox = new JComboBox<>();
        asignarUsuarioComboBox.setBounds(220, 100, 400, 20);
        this.usuarios.stream().map(UsuarioDTO::getUsername).forEach(asignarUsuarioComboBox::addItem);
        centerPanel1.add(asignarUsuarioComboBox);

        JLabel prioridadTareaLabel = new JLabel(labels.getString("campo.prioridad"));
        prioridadTareaLabel.setForeground(new Color(255, 255, 255));
        prioridadTareaLabel.setBounds(50, 150, 150, 20);
        prioridadTareaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Estilo aplicado
        centerPanel1.add(prioridadTareaLabel);

        JComboBox<String> prioridadComboBox = new JComboBox<>();
        prioridadComboBox.setBounds(220, 150, 400, 20);
        prioridadComboBox.addItem("");
        for (String prioridad : Arrays.asList(labels.getString("prioridad.alta"), labels.getString("prioridad.media"),
                labels.getString("prioridad.baja"))) {
            prioridadComboBox.addItem(prioridad);
        }
        centerPanel1.add(prioridadComboBox);

        JLabel lblFechaInicio = new JLabel(labels.getString("campo.fechaInicio"));
        lblFechaInicio.setForeground(new Color(255, 255, 255));
        lblFechaInicio.setBounds(50, 200, 150, 20);
        lblFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Estilo aplicado
        centerPanel1.add(lblFechaInicio);

        JDateChooser dateChooserInicio = new JDateChooser();
        dateChooserInicio.setDateFormatString("dd-MM-yyyy");
        dateChooserInicio.setBounds(220, 200, 400, 20);
        centerPanel1.add(dateChooserInicio);

        JLabel lblFechaFin = new JLabel(labels.getString("campo.fechaFin"));
        lblFechaFin.setForeground(new Color(255, 255, 255));
        lblFechaFin.setBounds(50, 250, 150, 20);
        lblFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Estilo aplicado
        centerPanel1.add(lblFechaFin);

        JDateChooser dateChooserFin = new JDateChooser();
        dateChooserFin.setDateFormatString("dd-MM-yyyy");
        dateChooserFin.setBounds(220, 250, 400, 20);
        centerPanel1.add(dateChooserFin);

        JLabel lblDescripcion = new JLabel(labels.getString("campo.descripcion"));
        lblDescripcion.setForeground(new Color(255, 255, 255));
        lblDescripcion.setBounds(50, 300, 150, 20);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Estilo aplicado
        centerPanel1.add(lblDescripcion);

        JTextArea textAreaDescription = new JTextArea();
        textAreaDescription.setBounds(220, 300, 400, 100);
        centerPanel1.add(textAreaDescription);

        JButton aceptarButton = createButton(labels.getString("boton.guardar"), new Color(138, 102, 204));
        aceptarButton.setBounds(220, 420, 100, 30);
        centerPanel1.add(aceptarButton);

        JButton cancelarButton = createButton(labels.getString("boton.cancelar"), new Color(138, 102, 204));
        cancelarButton.setBounds(330, 420, 100, 30);
        
        
        centerPanel1.add(cancelarButton);
        
       
        aceptarButton.addActionListener(ev -> {
        	try {
                int selectedUserIndex = asignarUsuarioComboBox.getSelectedIndex();
                String nombreTarea = nombreTareaTextField.getText();
                String prioridadTarea = api.obtenerPrioridadPorIndex(prioridadComboBox.getSelectedIndex());
                UsuarioDTO usuario = usuarios.get(selectedUserIndex);
                String descripcionTarea = textAreaDescription.getText();
                java.util.Date fechaInicioDate = dateChooserInicio.getDate();
                java.util.Date fechaFinDate = dateChooserFin.getDate();
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
                   
        		} catch (DataEmptyException e) {
            		JOptionPane.showMessageDialog(null, labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
				} catch (NotNullException e) {
					JOptionPane.showMessageDialog(null,labels.getString("mensaje.campoVacioTarea") +" " + labels.getString(e.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
				} catch (InvalidDateException e) {
					JOptionPane.showMessageDialog(null,labels.getString("mensaje.fechasValidas") + labels.getString(e.getMessage()), "Error", JOptionPane.WARNING_MESSAGE);
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

