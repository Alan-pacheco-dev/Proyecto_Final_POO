package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
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
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Contrato;
import logico.EmpresaAltice;
import logico.Pagos;

public class RegistrarPago extends JDialog {

	private static final Color ALTICE_BLUE   = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT  = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font  FONT_LABEL    = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_INPUT    = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font  FONT_BTN      = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_TABLE    = new Font("SansSerif", Font.PLAIN, 12);
	private static final Font  FONT_HEADER   = new Font("SansSerif", Font.BOLD, 12);

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;

	private JTable tableContratos;
	private DefaultTableModel modelContratos;
	private TableRowSorter<DefaultTableModel> sorterContratos;

	private JTable tablePagos;
	private DefaultTableModel modelPagos;

	private JButton btnProcesarPago;
	private Contrato contratoSeleccionado = null;
	private Pagos pagoSeleccionado = null;

	public RegistrarPago() {
		setTitle("Registrar Pago");
		setResizable(false);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int ancho = pantalla.width  - 400;
		int alto  = pantalla.height - 400;
		setSize(ancho, alto);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Registrar Pago");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(Color.WHITE);
		JLabel lblAltice = new JLabel("altice");
		lblAltice.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblAltice.setForeground(new Color(255, 255, 255, 160));
		header.add(lblTitulo, BorderLayout.WEST);
		header.add(lblAltice, BorderLayout.EAST);
		getContentPane().add(header, BorderLayout.NORTH);

		contentPanel.setBorder(new EmptyBorder(12, 14, 6, 14));
		contentPanel.setBackground(Color.WHITE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		JPanel panelBusqueda = new JPanel(new BorderLayout(10, 0));
		panelBusqueda.setBackground(Color.WHITE);
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Contrato (Cliente, Plan, ID):");
		lblBuscar.setFont(FONT_LABEL);
		lblBuscar.setForeground(ALTICE_BLUE);
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setFont(FONT_INPUT);
		txtBuscar.setBackground(ALTICE_LIGHT);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(ALTICE_BORDER, 1, true),
			new EmptyBorder(4, 8, 4, 8)
		));
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		JPanel panelCentral = new JPanel(new BorderLayout(12, 0));
		panelCentral.setBackground(Color.WHITE);
		contentPanel.add(panelCentral, BorderLayout.CENTER);

		// Panel izquierdo — contratos
		JPanel panelContratos = new JPanel(new BorderLayout());
		panelContratos.setBackground(Color.WHITE);
		TitledBorder borderCto = BorderFactory.createTitledBorder(
			new LineBorder(ALTICE_BORDER, 1, true), "Contratos activos con pagos pendientes");
		borderCto.setTitleFont(FONT_LABEL);
		borderCto.setTitleColor(ALTICE_BLUE);
		panelContratos.setBorder(borderCto);
		panelCentral.add(panelContratos, BorderLayout.CENTER);

		tableContratos = new JTable();
		tableContratos.setFont(FONT_TABLE);
		tableContratos.setRowHeight(26);
		tableContratos.setGridColor(ALTICE_BORDER);
		tableContratos.setShowGrid(true);
		tableContratos.setSelectionBackground(new Color(210, 225, 255));
		tableContratos.setSelectionForeground(Color.BLACK);
		tableContratos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableContratos.getTableHeader().setFont(FONT_HEADER);
		tableContratos.getTableHeader().setBackground(ALTICE_BLUE);
		tableContratos.getTableHeader().setForeground(Color.WHITE);

		modelContratos = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		modelContratos.setColumnIdentifiers(new String[]{"ID Contrato", "Cliente", "Plan", "Precio Mensual", "Fecha Inicio"});
		tableContratos.setModel(modelContratos);

		JScrollPane scrollContratos = new JScrollPane(tableContratos);
		scrollContratos.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelContratos.add(scrollContratos, BorderLayout.CENTER);

		sorterContratos = new TableRowSorter<>(modelContratos);
		tableContratos.setRowSorter(sorterContratos);

		tableContratos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				int indexVisual = tableContratos.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = tableContratos.convertRowIndexToModel(indexVisual);
					String idContrato = (String) modelContratos.getValueAt(indexReal, 0);
					contratoSeleccionado = null;
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

		// Panel derecho — pagos (más ancho: mitad del ancho total disponible)
		int anchoPanelDerecho = (ancho - 28) / 2;
		JPanel panelPagos = new JPanel(new BorderLayout());
		panelPagos.setBackground(Color.WHITE);
		TitledBorder borderPag = BorderFactory.createTitledBorder(
			new LineBorder(ALTICE_BORDER, 1, true), "Pagos pendientes del contrato seleccionado");
		borderPag.setTitleFont(FONT_LABEL);
		borderPag.setTitleColor(ALTICE_BLUE);
		panelPagos.setBorder(borderPag);
		panelPagos.setPreferredSize(new Dimension(anchoPanelDerecho, 0));
		panelCentral.add(panelPagos, BorderLayout.EAST);

		tablePagos = new JTable();
		tablePagos.setFont(FONT_TABLE);
		tablePagos.setRowHeight(26);
		tablePagos.setGridColor(ALTICE_BORDER);
		tablePagos.setShowGrid(true);
		tablePagos.setSelectionBackground(new Color(210, 225, 255));
		tablePagos.setSelectionForeground(Color.BLACK);
		tablePagos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePagos.getTableHeader().setFont(FONT_HEADER);
		tablePagos.getTableHeader().setBackground(ALTICE_BLUE);
		tablePagos.getTableHeader().setForeground(Color.WHITE);

		modelPagos = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		modelPagos.setColumnIdentifiers(new String[]{"ID Pago", "Fecha Inicio", "Fecha Vencimiento", "Total por Pagar"});
		tablePagos.setModel(modelPagos);

		JScrollPane scrollPagos = new JScrollPane(tablePagos);
		scrollPagos.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPagos.add(scrollPagos, BorderLayout.CENTER);

		tablePagos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) return;
				int indexVisual = tablePagos.getSelectedRow();
				if (indexVisual != -1) {
					String idPago = (String) modelPagos.getValueAt(indexVisual, 0);
					pagoSeleccionado = null;
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

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnProcesarPago = primaryButton("Procesar Pago");
		btnProcesarPago.setEnabled(false);
		btnProcesarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pagoSeleccionado != null) {
					PagosPorContrato ventanaPago = new PagosPorContrato(pagoSeleccionado);
					ventanaPago.setModal(true);
					ventanaPago.setVisible(true);
					loadContratos();
					loadPagosPendientes();
					btnProcesarPago.setEnabled(false);
					pagoSeleccionado = null;
				}
			}
		});
		buttonPane.add(btnProcesarPago);

		JButton btnCerrar = outlineButton("Cerrar");
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
			if (!c.isActivo()) continue;
			boolean tienePendientes = false;
			for (Pagos p : EmpresaAltice.getInstance().getPagos()) {
				if (p.getContrato().getIdContrato().equals(c.getIdContrato()) && !p.isPagadoTotal()) {
					tienePendientes = true;
					break;
				}
			}
			if (!tienePendientes) continue;
			modelContratos.addRow(new Object[]{
				c.getIdContrato(),
				c.getCliente().getNombre(),
				c.getPlanContrato().getNombrePlan(),
				"$ " + String.format("%.2f", c.getPrecioMensualAcordado()),
				c.getFechaInicioContrato()
			});
		}
	}

	private void loadPagosPendientes() {
		modelPagos.setRowCount(0);
		if (contratoSeleccionado == null) return;
		for (Pagos p : EmpresaAltice.getInstance().getPagos()) {
			if (p.getContrato().getIdContrato().equals(contratoSeleccionado.getIdContrato())
					&& !p.isPagadoTotal()) {
				modelPagos.addRow(new Object[]{
					p.getIdPago(),
					p.getFechaInicioPago(),
					p.getFechaVencimientoPago(),
					"$ " + String.format("%.2f", p.getTotalPorPagar())
				});
			}
		}
	}

	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(Color.WHITE);
		btn.setBackground(ALTICE_BLUE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(ALTICE_BLUE);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}