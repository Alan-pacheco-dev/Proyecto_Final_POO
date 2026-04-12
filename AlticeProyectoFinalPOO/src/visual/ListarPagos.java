package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Cliente;
import logico.EmpresaAltice;
import logico.Pagos;

import java.util.ArrayList;
import java.util.List;

public class ListarPagos extends JDialog {

    private final JPanel contentPanel = new JPanel();
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtBuscar;
    private JComboBox<String> cbxClientes;
    private JComboBox<String> cbxEstado;
    private TableRowSorter<DefaultTableModel> sorter;

    public static void main(String[] args) {
        try {
            ListarPagos dialog = new ListarPagos();
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ListarPagos() {
        setTitle("Listado de Pagos");
        setResizable(false);
        setBounds(100, 100, 950, 500);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BorderLayout(0, 10));

        // Panel de filtros
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
        contentPanel.add(panelFiltros, BorderLayout.NORTH);

        JLabel lblBuscar = new JLabel("Buscar: ");
        panelFiltros.add(lblBuscar);

        txtBuscar = new JTextField(15);
        panelFiltros.add(txtBuscar);

        JLabel lblCliente = new JLabel("Cliente: ");
        panelFiltros.add(lblCliente);

        cbxClientes = new JComboBox<>();
        cbxClientes.setPrototypeDisplayValue("Seleccionar cliente      ");
        panelFiltros.add(cbxClientes);

        JLabel lblEstado = new JLabel("Estado: ");
        panelFiltros.add(lblEstado);

        cbxEstado = new JComboBox<>();
        cbxEstado.addItem("Todos");
        cbxEstado.addItem("Pagados");
        cbxEstado.addItem("Pendientes");
        panelFiltros.add(cbxEstado);

        JButton btnLimpiar = new JButton("Limpiar filtros");
        panelFiltros.add(btnLimpiar);

        // Panel de la tabla
        JPanel panelTabla = new JPanel();
        panelTabla.setLayout(new BorderLayout(0, 0));
        contentPanel.add(panelTabla, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        table = new JTable();
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        String[] headers = {"ID Pago", "Cliente", "ID Contrato", "Fecha Inicio", "Fecha Vencimiento", "Fecha Pago", "Monto Pagado", "Total por Pagar", "Pagado"};
        model.setColumnIdentifiers(headers);
        table.setModel(model);
        scrollPane.setViewportView(table);

        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                aplicarFiltros();
            }
        });

        cbxClientes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        cbxEstado.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                aplicarFiltros();
            }
        });

        btnLimpiar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                txtBuscar.setText("");
                cbxClientes.setSelectedIndex(0);
                cbxEstado.setSelectedIndex(0);
                loadPagos("Todos");
                sorter.setRowFilter(null);
            }
        });

        // Panel de botones
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonPane.add(btnCerrar);

        loadClientes();
        loadPagos("Todos");
    }

    private void aplicarFiltros() {
        String textoBusqueda = txtBuscar.getText().trim();
        String clienteSeleccionado = (String) cbxClientes.getSelectedItem();
        String estadoSeleccionado = (String) cbxEstado.getSelectedItem();

        boolean hayTexto = textoBusqueda.length() > 0;
        boolean hayCliente = clienteSeleccionado != null && !clienteSeleccionado.equals("Todos");

        loadPagos(estadoSeleccionado);

        List<RowFilter<Object, Object>> filtros = new ArrayList<>();

        if (hayTexto) {
            filtros.add(RowFilter.regexFilter("(?i)" + textoBusqueda));
        }
        if (hayCliente) {
            filtros.add(RowFilter.regexFilter("(?i)" + clienteSeleccionado, 1));
        }

        if (filtros.isEmpty()) {
            sorter.setRowFilter(null);
        } else if (filtros.size() == 1) {
            sorter.setRowFilter(filtros.get(0));
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        }
    }

    private void loadClientes() {
        DefaultComboBoxModel<String> cbxModel = new DefaultComboBoxModel<>();
        cbxModel.addElement("Todos");
        for (Cliente c : EmpresaAltice.getInstance().getMisClientes()) {
            cbxModel.addElement(c.getNombre());
        }
        cbxClientes.setModel(cbxModel);
    }

    private void loadPagos(String filtroEstado) {
        model.setRowCount(0);
        for (Pagos p : EmpresaAltice.getInstance().getPagos()) {
            if (filtroEstado.equals("Pagados") && !p.isPagadoTotal()) continue;
            if (filtroEstado.equals("Pendientes") && p.isPagadoTotal()) continue;

            Object[] row = new Object[9];
            row[0] = p.getIdPago();
            row[1] = p.getContrato().getCliente().getNombre();
            row[2] = p.getContrato().getIdContrato();
            row[3] = p.getFechaInicioPago();
            row[4] = p.getFechaVencimientoPago();
            row[5] = p.getFechaPagoDelCliente() != null ? p.getFechaPagoDelCliente() : "Pendiente";
            row[6] = p.getPagoDelCliente();
            row[7] = p.getTotalPorPagar();
            row[8] = p.isPagadoTotal() ? "Sí" : "No";
            model.addRow(row);
        }
    }
}