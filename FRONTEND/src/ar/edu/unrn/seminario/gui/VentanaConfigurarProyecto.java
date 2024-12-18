package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
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
import ar.edu.unrn.seminario.dto.ProyectoDTO;

import ar.edu.unrn.seminario.dto.UsuarioDTO;

import ar.edu.unrn.seminario.exception.DataBaseConnectionException;
import ar.edu.unrn.seminario.exception.DataBaseFoundException;
import ar.edu.unrn.seminario.exception.DataBaseUpdateException;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

public class VentanaConfigurarProyecto extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;
    private JTextField textField_Nombre;
    private JComboBox<String> prioridadComboBox;
    private JLabel descripcion;
    private JTextField textField_Descripcion;
    private JButton aceptar;
    private JButton cancelar;
    private ProyectoDTO proyectoActual;
    private UsuarioDTO usuarioActual;
    ResourceBundle labels = ResourceBundle.getBundle("labels", new Locale("en"));


    public VentanaConfigurarProyecto(IApi api) {
    	

        this.proyectoActual = api.getProyectoActual();
        this.usuarioActual = api.getUsuarioActual();

        setTitle(labels.getString("ventana.modificarProyecto"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(50, 50, 1200, 650);

        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu(labels.getString("ventana.modificarProyecto"));
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

        // Formulario
        JLabel lblNombre = new JLabel(labels.getString("menu.nombreProyecto"));
        lblNombre.setForeground(new Color(255, 255, 255));
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblNombre.setBounds(83, 183, 123, 44);
        centerPanel1.add(lblNombre);

        textField_Nombre = new JTextField();
        textField_Nombre.setBounds(216, 196, 451, 26);
        centerPanel1.add(textField_Nombre);
        textField_Nombre.setColumns(10);

        JLabel lblPrioridad = new JLabel(labels.getString("campo.prioridad"));
        lblPrioridad.setForeground(Color.WHITE);
        lblPrioridad.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblPrioridad.setBounds(83, 237, 93, 44);
        centerPanel1.add(lblPrioridad);

        prioridadComboBox = new JComboBox<>();
        prioridadComboBox.setForeground(new Color(29, 17, 40));
        prioridadComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        prioridadComboBox.setBounds(216, 251, 451, 26);
        centerPanel1.add(prioridadComboBox);
        List<String> prioridades = Arrays.asList(labels.getString("prioridad.alta"), labels.getString("prioridad.media"),
                labels.getString("prioridad.baja"));
        
        for (String prioridad : prioridades) {
            prioridadComboBox.addItem(prioridad);
        }
        
        
        descripcion = new JLabel(labels.getString("campo.descripcion"));
        descripcion.setForeground(Color.WHITE);
        descripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcion.setBounds(83, 291, 123, 44);
        centerPanel1.add(descripcion);

        textField_Descripcion = new JTextField();
        textField_Descripcion.setColumns(10);
        textField_Descripcion.setBounds(216, 306, 451, 26);
        centerPanel1.add(textField_Descripcion);

        mostrarDatosActuales(api);

        // Botón Aceptar
        aceptar = createButton(labels.getString("boton.guardar"), new Color(138, 102, 204));
        aceptar.setBounds(220, 420, 100, 30);
        centerPanel1.add(aceptar);
        
        
        aceptar.addActionListener(e -> {
            String prioridadSeleccionada = api.obtenerPrioridadPorIndex(prioridadComboBox.getSelectedIndex() + 1);
            try {

                if (prioridadSeleccionada.equals(proyectoActual.getPrioridad()) && textField_Nombre.getText().equals(proyectoActual.getNombre()) && textField_Descripcion.getText().equals(proyectoActual.getDescripcion())) {
                    JOptionPane.showMessageDialog(null, labels.getString("mensaje.noModificoCampos"), labels.getString("titulo.modificarProyecto"), JOptionPane.QUESTION_MESSAGE);
                } else {
                    int opcionSeleccionada = JOptionPane.showConfirmDialog(null, labels.getString("mensaje.preguntaModificacion"), labels.getString("titulo.mensajeModificar"), JOptionPane.YES_NO_OPTION);

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
			} catch (DataBaseUpdateException e4) {
				JOptionPane.showMessageDialog(null,labels.getString(e4.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseFoundException e1) {
				JOptionPane.showMessageDialog(null, labels.getString(e1.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			} catch (DataBaseConnectionException e3) {
				JOptionPane.showMessageDialog(null,labels.getString(e3.getMessage()), "Error", JOptionPane.ERROR_MESSAGE);
			}
        });
        centerPanel1.add(aceptar);

        // Botón Cancelar
        cancelar = createButton(labels.getString("boton.cancelar"), new Color(138, 102, 204));
        cancelar.setBounds(330, 420, 100, 30);
        cancelar.addActionListener(e -> {
        	new VentanaResumen(api).setVisible(true);
            dispose();
        });
        
        centerPanel1.add(cancelar);
        
        


        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                new VentanaResumen(api).setVisible(true);
            }
        });
    }

    private void mostrarDatosActuales(IApi api) {
        String nombreProyecto = proyectoActual.getNombre();
        String prioridadProyecto = labels.getString(api.traducirPrioridad(proyectoActual.getPrioridad()));
        String descripcionProyecto = proyectoActual.getDescripcion();
        textField_Nombre.setText(nombreProyecto);
        prioridadComboBox.setSelectedItem(prioridadProyecto);
        textField_Descripcion.setText(descripcionProyecto);
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
