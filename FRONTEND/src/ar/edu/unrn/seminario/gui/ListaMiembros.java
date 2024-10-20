package ar.edu.unrn.seminario.gui;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import ar.edu.unrn.seminario.api.IApi;
import ar.edu.unrn.seminario.api.MemoryApi;
import ar.edu.unrn.seminario.dto.UsuarioDTO;
import ar.edu.unrn.seminario.exception.DataEmptyException;
import ar.edu.unrn.seminario.exception.NotNullException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListaMiembros extends JFrame {
	private List<UsuarioDTO> usuarios;
	
    public ListaMiembros(IApi api) {
        setTitle("Lista de Miembros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 400);
        setLayout(new BorderLayout());

        // Colores basados en la imagen de LabProject
        Color fondoOscuro = new Color(81, 79, 89);
        Color fondoMedio = new Color(45, 45, 45);
        Color moradoPrincipal = new Color(109, 114, 195); // Color de los títulos de los proyectos
        Color moradoOscuro = new Color(80, 70, 150); // Color de los botones principales
        Color grisBotones = new Color(229, 212, 237); // Color de botones secundarios

        // Modelo de tabla
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Usuario");
        model.addColumn("Correo");
        model.addColumn("Estado");
        model.addColumn("Roles");
        //model.addColumn("Proyecto");
        
        usuarios = api.obtenerUsuarios();
        for (UsuarioDTO u : usuarios) {
        	model.addRow(new Object[] {u.getUsername(), u.getEmail(), u.isActivo(), u.getRol()});
		}
        
        // Crear tabla
        JTable tabla = new JTable(model);
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabla.setForeground(Color.WHITE);
        tabla.setBackground(fondoOscuro);
        tabla.setRowHeight(30);

        // Renderizado personalizado para alinear texto
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Cambiar el encabezado de la tabla
        JTableHeader header = tabla.getTableHeader();
        header.setBackground(moradoPrincipal);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));

        // Panel para la tabla
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.getViewport().setBackground(fondoOscuro); // Fondo para cuando no haya datos en la tabla

        // Crear panel inferior con botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panelBotones.setBackground(fondoMedio);

        // Botón "Agregar Miembro"
        JButton agregarBtn = new JButton("Agregar Miembro");
        agregarBtn.setBackground(moradoOscuro);
        agregarBtn.setForeground(Color.WHITE);
        agregarBtn.setFocusPainted(false);
        agregarBtn.setPreferredSize(new Dimension(140, 30));

        // Botón "Eliminar Miembro"
        JButton eliminarBtn = new JButton("Eliminar Miembro");
        eliminarBtn.setBackground(grisBotones);
        eliminarBtn.setForeground(Color.BLACK);
        eliminarBtn.setFocusPainted(false);
        eliminarBtn.setPreferredSize(new Dimension(140, 30));

        // Añadir botones al panel
        panelBotones.add(agregarBtn);
        panelBotones.add(eliminarBtn);

        // Acciones de botones
        agregarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Agregar nuevo miembro.");
            }
        });

        eliminarBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tabla.getSelectedRow();
                if (selectedRow != -1) {
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(null, "Miembro eliminado.");
                } else {
                    JOptionPane.showMessageDialog(null, "Seleccione un miembro para eliminar.");
                }
            }
        });

        // Agregar todo al frame
        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Centrar en pantalla
        setVisible(true);
    }
}