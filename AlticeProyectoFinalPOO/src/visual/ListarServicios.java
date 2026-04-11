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
import logico.Plan;
import logico.Servicio;

public class ListarServicios extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtBuscar;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnEliminar;
    private Servicio selected = null;
    
    public static void main(String[] args) {
		try {
			ListarServicios dialog = new ListarServicios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public ListarServicios() {
        setTitle("Listado de Servicios");
        setResizable(false);
        setBounds(100, 100, 650, 400);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BorderLayout(10, 0));
        contentPanel.add(panelBusqueda, BorderLayout.NORTH);

        JLabel lblBuscar = new JLabel("Buscar Servicio (Tipo, ID): ");
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
                    String idServicio = (String) model.getValueAt(indexReal, 0);

                    for (Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
                        if (s.getIdServicio().equals(idServicio)) {
                            selected = s;
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
        String[] headers = {"ID", "Tipo de Servicio", "Precio", "Activo"};
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

                    // Verificar si el servicio está siendo usado en algún plan
                    boolean enUso = false;
                    for (Plan p : EmpresaAltice.getInstance().getMisPlanes()) {
                        if (p.getServiciosPlan().contains(selected)) {
                            enUso = true;
                            break;
                        }
                    }

                    if (enUso) {
                        JOptionPane.showMessageDialog(null,
                                "No se puede eliminar el servicio porque está siendo usado en un plan",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int option = JOptionPane.showConfirmDialog(null,
                            "¿Desea realmente eliminar el servicio: " + selected.getTipoServicio() + "?",
                            "Confirmación", JOptionPane.WARNING_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        EmpresaAltice empresa = EmpresaAltice.getInstance();
                        empresa.getMisServicios().remove(selected);
                        empresa.GuardarDatos(
                                empresa.getMisClientes(),
                                empresa.getMisEmpleados(),
                                empresa.getMisPlanes(),
                                empresa.getMisServicios(),
                                empresa.getMisUsuarios(),
                                empresa.getMisContratos(),
                                empresa.getPagos());
                        JOptionPane.showMessageDialog(null, "Servicio eliminado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
                        loadServicios();
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

        loadServicios();
    }

    private void loadServicios() {
        model.setRowCount(0);
        for (Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
            Object[] row = new Object[4];
            row[0] = s.getIdServicio();
            row[1] = s.getTipoServicio();
            row[2] = s.getPrecioServicio();
            row[3] = s.isActivo() ? "Sí" : "No";
            model.addRow(row);
        }
    }
}