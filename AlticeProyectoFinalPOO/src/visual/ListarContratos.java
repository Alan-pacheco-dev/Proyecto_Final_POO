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
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

import logico.Contrato;
import logico.EmpresaAltice;

public class ListarContratos extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtBuscarCliente;
    private TableRowSorter<DefaultTableModel> sorter;
    private JButton btnCancelarContrato;
    private JCheckBox chkSoloActivos;
    private Contrato selected = null;
    
    public static void main(String[] args) {
		try {
			ListarContratos dialog = new ListarContratos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    public ListarContratos() {
        setTitle("Listado de Contratos");
        setResizable(false);
        setBounds(100, 100, 900, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel();
        panelBusqueda.setLayout(new BorderLayout(10, 0));
        contentPanel.add(panelBusqueda, BorderLayout.NORTH);

        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelBusqueda.add(panelFiltros, BorderLayout.CENTER);

        JLabel lblBuscar = new JLabel("Buscar por cliente: ");
        panelFiltros.add(lblBuscar);

        txtBuscarCliente = new JTextField(20);
        panelFiltros.add(txtBuscarCliente);

        chkSoloActivos = new JCheckBox("Solo activos");
        chkSoloActivos.setSelected(true);
        panelFiltros.add(chkSoloActivos);

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
                    String idContrato = (String) model.getValueAt(indexReal, 0);

                    for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
                        if (c.getIdContrato().equals(idContrato)) {
                            selected = c;
                            break;
                        }
                    }
                    // Solo habilitar cancelar si el contrato está activo
                    btnCancelarContrato.setEnabled(selected != null && selected.isActivo());
                }
            }
        });

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headers = {"ID", "Cliente", "Empleado", "Plan", "Precio Mensual", "Fecha Inicio", "Fecha Fin", "Activo"};
        model.setColumnIdentifiers(headers);
        table.setModel(model);
        scrollPane.setViewportView(table);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // Filtro por nombre de cliente en tiempo real
        txtBuscarCliente.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });

        // Filtro por activo al cambiar el checkbox
        chkSoloActivos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        btnCancelarContrato = new JButton("Cancelar Contrato");
        btnCancelarContrato.setEnabled(false);
        btnCancelarContrato.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selected != null) {
                    int option = JOptionPane.showConfirmDialog(null,
                            "¿Desea cancelar el contrato " + selected.getIdContrato() +
                            " del cliente " + selected.getCliente().getNombre() + "?",
                            "Confirmación", JOptionPane.WARNING_MESSAGE);

                    if (option == JOptionPane.YES_OPTION) {
                        selected.getCliente().cancelarContratoByID(selected.getIdContrato());

                        EmpresaAltice empresa = EmpresaAltice.getInstance();
                        empresa.GuardarDatos(
                                empresa.getMisClientes(),
                                empresa.getMisEmpleados(),
                                empresa.getMisPlanes(),
                                empresa.getMisServicios(),
                                empresa.getMisUsuarios(),
                                empresa.getMisContratos(),
                                empresa.getPagos());

                        JOptionPane.showMessageDialog(null, "Contrato cancelado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
                        loadContratos();
                        btnCancelarContrato.setEnabled(false);
                        selected = null;
                    }
                }
            }
        });
        buttonPane.add(btnCancelarContrato);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCerrar);

        loadContratos();
    }

    private void aplicarFiltros() {
        String textoBusqueda = txtBuscarCliente.getText().trim();
        boolean soloActivos = chkSoloActivos.isSelected();

        // Recargar tabla completa y luego filtrar
        loadContratos();

        if (soloActivos && textoBusqueda.length() == 0) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)Sí", 7)); // columna 7 = Activo
        } else if (!soloActivos && textoBusqueda.length() == 0) {
            sorter.setRowFilter(null);
        } else if (soloActivos && textoBusqueda.length() > 0) {
            sorter.setRowFilter(RowFilter.andFilter(java.util.Arrays.asList(
                RowFilter.regexFilter("(?i)" + textoBusqueda, 1), // columna 1 = Cliente
                RowFilter.regexFilter("(?i)Sí", 7)               // columna 7 = Activo
            )));
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 1)); // solo por cliente
        }
    }

    private void loadContratos() {
        model.setRowCount(0);
        for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
            Object[] row = new Object[8];
            row[0] = c.getIdContrato();
            row[1] = c.getCliente().getNombre();
            row[2] = c.getEmpConsiguioContrato() != null ? c.getEmpConsiguioContrato().getNombre() : "N/A";
            row[3] = c.getPlanContrato().getNombrePlan();
            row[4] = c.getPrecioMensualAcordado();
            row[5] = c.getFechaInicioContrato();
            row[6] = c.getFechaFinContrato() != null ? c.getFechaFinContrato() : "Activo";
            row[7] = c.isActivo() ? "Sí" : "No";
            model.addRow(row);
        }
    }
}