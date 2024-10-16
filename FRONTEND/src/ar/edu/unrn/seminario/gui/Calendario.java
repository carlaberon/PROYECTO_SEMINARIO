package ar.edu.unrn.seminario.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.Descriptor;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Calendario extends JFrame {

    private JPanel contentPane;

    public Calendario() {

        setTitle("");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 900, 600);
        
     
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);
        
   
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(138, 102, 204));
        menuBar.setPreferredSize(new Dimension(100, 50));

        JMenu menuProyecto = new JMenu("nombreProyecto");
        menuProyecto.setForeground(Color.WHITE);
        menuProyecto.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JMenuItem item1 = new JMenuItem("Opción 1");
        JMenuItem item2 = new JMenuItem("Opción 2");
        JMenuItem item3 = new JMenuItem("Opción 3");
        menuProyecto.add(item1);
        menuProyecto.add(item2);
        menuProyecto.add(item3);

        menuBar.add(menuProyecto);

        JLabel appName = new JLabel("LabProject");
        appName.setForeground(Color.WHITE);
        appName.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.add(appName);
        menuBar.add(centerPanel);

        JMenu accountMenu = new JMenu("nombreCuenta");
        accountMenu.setForeground(Color.WHITE);
        accountMenu.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JMenuItem logoutItem = new JMenuItem("Cerrar sesión");
        JMenuItem confItem = new JMenuItem("Configurar cuenta");
        accountMenu.add(confItem);
        accountMenu.add(logoutItem);
        
        logoutItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
        	
        });

        menuBar.add(accountMenu);
        //comentario
        this.setJMenuBar(menuBar);
        
     // Panel lateral (Menú)-----------------------------------------------------------------------------
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(7, 1, 10, 10)); // Espacio entre botones
        menuPanel.setPreferredSize(new Dimension(200, 0));
        menuPanel.setBackground(new Color(65, 62, 77));

        String[] menuItems = {"Calendario", "Volver"};
        for (String item : menuItems) {
            JButton menuButton = new JButton(item);
            menuButton.setForeground(Color.WHITE);
            menuButton.setBackground(new Color(83, 82, 90));
            menuButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            menuButton.setBorderPainted(false);
            menuButton.setFocusPainted(false);
            menuButton.setHorizontalAlignment(SwingConstants.LEFT); // Alineación izquierda
            menuButton.setMargin(new Insets(10, 10, 10, 10)); // Margen interno
            menuPanel.add(menuButton);
        }
    
        contentPane.add(menuPanel, BorderLayout.WEST);
        
        JPanel centerPanel1 = new JPanel();
        centerPanel1.setLayout(new GridLayout(1, 1, 10, 10));
        centerPanel1.setBackground(new Color(45, 44, 50));
        centerPanel1.setBorder(new EmptyBorder(20, 20, 20, 20)); // Margen alrededor del contenido
        contentPane.add(centerPanel1, BorderLayout.CENTER);
    
        JPanel descPanel = createPanel("","");
        descPanel.setLayout(new BorderLayout());
        
        
   
        centerPanel1.add(descPanel);
    }
    
 // Método auxiliar para crear paneles con título y diseño consistente
    private JPanel createPanel(String title, String subtitle) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(53, 52, 60));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        JLabel label = new JLabel(title);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(label);

        if (subtitle != null) {
            JLabel subLabel = new JLabel(subtitle);
            subLabel.setForeground(Color.GRAY);
            subLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            panel.add(subLabel);
        }

        return panel;
    }
    
    // Método para crear botones con estilo
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

    public static void main(String[] args) {
        Calendario calendario = new Calendario();
        calendario.setVisible(true);
    }
}