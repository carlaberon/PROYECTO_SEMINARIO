package ar.edu.unrn.seminario.gui;
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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.NotNullException;
import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.DataBaseInsertionException;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class CrearProyecto extends JFrame {
	
	ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en")); 

	
    List<String> prioridades = Arrays.asList(labels.getString("prioridad.alta"),labels.getString("prioridad.media"), labels.getString("prioridad.baja"));
	private JPanel contentPane;
	private JTextField nombreProyectoTextField;
	private IApi api;
	private JTextField descripcionTextField;
	private UsuarioDTO usuarioActual;
	
	public CrearProyecto(IApi api) {
		this.api = api;
		this.usuarioActual = api.getUsuarioActual();
		
		setTitle(labels.getString("ventana.crearProyecto"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(50, 50, 1200, 650);
		getContentPane().setLayout(null);
		
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
     // Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(labels.getString("ventana.crearProyecto"));
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

        String[] menuItems = { labels.getString("menu.proyectos"), labels.getString("menu.volver") };
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


            // Agregar ActionListener solo al botón de "Volver o Back"
            if (item.equals("Volver") || item.equals("Return")) {
                menuButton.addActionListener(e -> {
                	new Inicio(api).setVisible(true);
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
        
        //Formulario

		JLabel nombreProyecto = new JLabel(labels.getString("menu.nombreProyecto"));
		nombreProyecto.setForeground(new Color(255, 255, 255));
		nombreProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		nombreProyecto.setBounds(192, 163, 123, 44);
		centerPanel1.add(nombreProyecto);

		nombreProyectoTextField = new JTextField();
		nombreProyectoTextField.setBounds(325, 175, 390, 25);
		centerPanel1.add(nombreProyectoTextField);
		nombreProyectoTextField.setColumns(10);
		
		descripcionTextField = new JTextField();
		descripcionTextField.setColumns(10);
		descripcionTextField.setBounds(325, 225, 390, 25);
		centerPanel1.add(descripcionTextField);
		
		JComboBox<String> prioridadComboBox = new JComboBox<>();
		prioridadComboBox.setForeground(new Color(29, 17, 40));
		prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
		prioridadComboBox.setBounds(325, 274, 390, 25);
		centerPanel1.add(prioridadComboBox);
		prioridadComboBox.addItem("");
        for (String prioridad : prioridades) {
            prioridadComboBox.addItem(prioridad);
        }

		JButton aceptarButton = createButton(labels.getString("boton.guardar"), new Color(138, 102, 204));
		aceptarButton.setBounds(395, 398, 147, 27);
		centerPanel1.add(aceptarButton);
		aceptarButton.addActionListener(e -> {
				String nombreNuevoProyecto = nombreProyectoTextField.getText();
				String descripcionNueva = descripcionTextField.getText();
                String prioridadSeleccionadaNueva = api.obtenerPrioridadPorIndex(prioridadComboBox.getSelectedIndex());

				try {
	                api.crearProyecto(nombreNuevoProyecto, api.getUsuarioActual().getUsername(), "EN CURSO", descripcionNueva, prioridadSeleccionadaNueva);
	                JOptionPane.showMessageDialog(null, labels.getString("mensaje.proyectoCreado"), "Info", JOptionPane.INFORMATION_MESSAGE);
	                new Inicio(api).setVisible(true);
	                dispose();
				} catch (NotNullException e1) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + labels.getString(e1.getMessage()) + labels.getString("mensaje.null"), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataEmptyException e2) {
		            JOptionPane.showMessageDialog(null, labels.getString("mensaje.elCampo") + labels.getString(e2.getMessage()) + labels.getString("mensaje.empty"), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataBaseInsertionException e3) {
		        	JOptionPane.showMessageDialog(null, labels.getString(e3.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
		        } catch (DataBaseConnectionException e4) {
		        	JOptionPane.showMessageDialog(null,labels.getString(e4.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (DataBaseFoundException e1) {
					JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
		);
		JButton cancelarButton = createButton(labels.getString("boton.cancelar"), new Color(138, 102, 204));
		cancelarButton.setBounds(568, 398, 147, 27);
		centerPanel1.add(cancelarButton);
		
		cancelarButton.addActionListener(e -> {
				new Inicio(api).setVisible(true);;
				dispose();
			}
		);
		
		
		JLabel lblDescripcion = new JLabel(labels.getString("campo.descripcion"));
		lblDescripcion.setForeground(new Color(255, 255, 255));
		lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblDescripcion.setBounds(192, 217, 113, 39);
		centerPanel1.add(lblDescripcion); 
		
		JLabel lblPrioridad = new JLabel(labels.getString("campo.prioridad"));
		lblPrioridad.setForeground(new Color(255, 255, 255));
		lblPrioridad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblPrioridad.setBounds(198, 264, 97, 39);
		centerPanel1.add(lblPrioridad); 
		setLocationRelativeTo(null); 
		
		addWindowListener(new WindowAdapter() { 
        	public void windowClosing(WindowEvent e) {
        		new Inicio(api).setVisible(true);
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
