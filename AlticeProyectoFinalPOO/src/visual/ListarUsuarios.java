package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.EmpresaAltice;
import logico.Usuario;

public class ListarUsuarios extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnEliminar;
    private Usuario selected = null;
    
    public static void main(String[] args) {
		try {
			ListarUsuarios dialog = new ListarUsuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public ListarUsuarios() {
        setTitle("Listado de Usuarios");
        setResizable(false);
        setBounds(100, 100, 600, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BorderLayout(10, 0));
        contentPanel.add(panelBusqueda, BorderLayout.NORTH);

        JLabel lblBuscar = new JLabel("Buscar Usuario (Nombre, Rol): ");
        panelBusqueda.add(lblBuscar, BorderLayout.WEST);

        txtBuscar = new JTextField();
        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        // Panel de la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout(0, 0));
        contentPanel.add(panelTabla, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int indexVisual = table.getSelectedRow();
                if (indexVisual != -1) {
                    int indexReal = table.convertRowIndexToModel(indexVisual);
                    String nombreUsuario = (String) model.getValueAt(indexReal, 0);

                    for (Usuario u : EmpresaAltice.getInstance().getMisUsuarios()) {
                        if (u.getNombreUsuario().equals(nombreUsuario)) {
                            selected = u;
                            break;
                        }
                    }
                    btnEliminar.setEnabled(true);
                }
            }
        });

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headers = {"Nombre de Usuario", "Rol"};
        model.setColumnIdentifiers(headers);
        table.setModel(model);
        scrollPane.setViewportView(table);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String textoBusqueda = txtBuscar.getText();
                if (textoBusqueda.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
                }
            }
        });

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnEliminar = new JButton("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    //if (selected.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
                        //JOptionPane.showMessageDialog(null, "No se puede eliminar el usuario Administrativo", "Error", JOptionPane.ERROR_MESSAGE);
                        //return;
                    //}

                    int option = JOptionPane.showConfirmDialog(null,
                            "¿Desea realmente eliminar al usuario: " + selected.getNombreUsuario() + "?",
                            "Confirmación",
                            JOptionPane.WARNING_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        EmpresaAltice empresa = EmpresaAltice.getInstance();
                        empresa.getMisUsuarios().remove(selected);
                        empresa.GuardarDatos(
                                empresa.getMisClientes(),
                                empresa.getMisEmpleados(),
                                empresa.getMisPlanes(),
                                empresa.getMisServicios(),
                                empresa.getMisUsuarios(),
                                empresa.getMisContratos(),
                                empresa.getPagos());
                        JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
                        loadUsuarios();
                        btnEliminar.setEnabled(false);
                        selected = null;
                    }
                }
            }
        });
        buttonPane.add(btnEliminar);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCerrar);

        loadUsuarios();
    }

    private void loadUsuarios() {
        model.setRowCount(0);
        for (Usuario u : EmpresaAltice.getInstance().getMisUsuarios()) {
            Object[] row = new Object[2];
            row[0] = u.getNombreUsuario();
            row[1] = u.getRolEmpleado();
            model.addRow(row);
        }
    }
}