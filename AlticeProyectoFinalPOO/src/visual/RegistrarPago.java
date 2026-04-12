package visual;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Contrato;
import logico.EmpresaAltice;
import logico.Pagos;

import java.awt.FlowLayout;

public class RegistrarPago extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;

	// Tabla de contratos
	private JTable tableContratos;
	private DefaultTableModel modelContratos;
	private TableRowSorter<DefaultTableModel> sorterContratos;

	// Tabla de pagos pendientes
	private JTable tablePagos;
	private DefaultTableModel modelPagos;

	private JButton btnProcesarPago;
	private Contrato contratoSeleccionado = null;
	private Pagos pagoSeleccionado = null;

	public RegistrarPago() {
		setTitle("Registrar Pago");
		setResizable(false);
		setBounds(100, 100, 950, 650);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		// --- PANEL SUPERIOR: BUSCADOR ---
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(null);
		panelBusqueda.setPreferredSize(new java.awt.Dimension(950, 50));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Contrato (Cliente, Plan, ID):");
		lblBuscar.setBounds(20, 15, 250, 25);
		panelBusqueda.add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(270, 12, 580, 30);
		panelBusqueda.add(txtBuscar);

		// --- PANEL CENTRAL: DOS TABLAS ---
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout(0, 10));
		contentPanel.add(panelCentral, BorderLayout.CENTER);

		// Tabla de contratos
		JPanel panelContratos = new JPanel();
		panelContratos.setLayout(new BorderLayout());
		panelContratos.setBorder(new TitledBorder("Contratos Activos"));
		panelCentral.add(panelContratos, BorderLayout.CENTER);

		JScrollPane scrollContratos = new JScrollPane();
		panelContratos.add(scrollContratos, BorderLayout.CENTER);

		tableContratos = new JTable();
		tableContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modelContratos = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] headersContratos = {"ID Contrato", "Cliente", "Plan", "Precio Mensual", "Fecha Inicio"};
		modelContratos.setColumnIdentifiers(headersContratos);
		tableContratos.setModel(modelContratos);
		scrollContratos.setViewportView(tableContratos);

		sorterContratos = new TableRowSorter<>(modelContratos);
		tableContratos.setRowSorter(sorterContratos);

		// Al seleccionar un contrato cargar sus pagos pendientes
		tableContratos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = tableContratos.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = tableContratos.convertRowIndexToModel(indexVisual);
					String idContrato = (String) modelContratos.getValueAt(indexReal, 0);

					for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
						if (c.getIdContrato().equals(idContrato)) {
							contratoSeleccionado = c;
							break;
						}
					}

					pagoSeleccionado = null;
					btnProcesarPago.setEnabled(false);
					loadPagosPendientes();
				}
			}
		});

		// Tabla de pagos pendientes
		JPanel panelPagos = new JPanel();
		panelPagos.setLayout(new BorderLayout());
		panelPagos.setBorder(new TitledBorder("Pagos Pendientes del Contrato Seleccionado"));
		panelPagos.setPreferredSize(new java.awt.Dimension(950, 200));
		panelCentral.add(panelPagos, BorderLayout.SOUTH);

		JScrollPane scrollPagos = new JScrollPane();
		panelPagos.add(scrollPagos, BorderLayout.CENTER);

		tablePagos = new JTable();
		tablePagos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		modelPagos = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] headersPagos = {"ID Pago", "Fecha Inicio", "Fecha Vencimiento", "Total por Pagar"};
		modelPagos.setColumnIdentifiers(headersPagos);
		tablePagos.setModel(modelPagos);
		scrollPagos.setViewportView(tablePagos);

		// Al seleccionar un pago habilitar el botón
		tablePagos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = tablePagos.getSelectedRow();
				if (indexVisual != -1) {
					String idPago = (String) modelPagos.getValueAt(indexVisual, 0);

					for (Pagos p : EmpresaAltice.getInstance().getPagos()) {
						if (p.getIdPago().equals(idPago)) {
							pagoSeleccionado = p;
							break;
						}
					}
					btnProcesarPago.setEnabled(pagoSeleccionado != null);
				}
			}
		});

		// Buscador en tiempo real sobre contratos
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String texto = txtBuscar.getText();
				if (texto.trim().isEmpty()) {
					sorterContratos.setRowFilter(null);
				} else {
					sorterContratos.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
				}
			}
		});

		// --- PANEL INFERIOR: BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnProcesarPago = new JButton("Procesar Pago");
		btnProcesarPago.setEnabled(false);
		btnProcesarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pagoSeleccionado != null) {
					PagosPorContrato ventanaPago = new PagosPorContrato(pagoSeleccionado);
					ventanaPago.setModal(true);
					ventanaPago.setVisible(true);
					loadPagosPendientes();
					btnProcesarPago.setEnabled(false);
					pagoSeleccionado = null;
				}
			}
		});
		buttonPane.add(btnProcesarPago);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadContratos();
	}

	private void loadContratos() {
		modelContratos.setRowCount(0);
		for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
			if (c.isActivo()) {
				Object[] row = new Object[5];
				row[0] = c.getIdContrato();
				row[1] = c.getCliente().getNombre();
				row[2] = c.getPlanContrato().getNombrePlan();
				row[3] = "$ " + c.getPrecioMensualAcordado();
				row[4] = c.getFechaInicioContrato();
				modelContratos.addRow(row);
			}
		}
	}

	private void loadPagosPendientes() {
		modelPagos.setRowCount(0);
		if (contratoSeleccionado == null) return;

		for (Pagos p : EmpresaAltice.getInstance().getPagos()) {
			if (p.getContrato().getIdContrato().equals(contratoSeleccionado.getIdContrato())
					&& !p.isPagadoTotal()) {
				Object[] row = new Object[4];
				row[0] = p.getIdPago();
				row[1] = p.getFechaInicioPago();
				row[2] = p.getFechaVencimientoPago();
				row[3] = "$ " + p.getTotalPorPagar();
				modelPagos.addRow(row);
			}
		}
	}
}