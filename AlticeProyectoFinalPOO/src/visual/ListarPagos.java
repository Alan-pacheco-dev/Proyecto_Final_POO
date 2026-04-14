package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
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
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Cliente;
import logico.EmpresaAltice;
import logico.Pagos;

public class ListarPagos extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private JComboBox<String> cbxClientes;
	private JComboBox<String> cbxEstado;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

	public static void main(String[] args) {
		try {
			ListarPagos dialog = new ListarPagos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Ha ocurrido un error en ListarPagos, cierre el programa", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public ListarPagos() {
		setTitle("Listado de Pagos y Transacciones");
		setResizable(false);
		setBounds(100, 100, 1150, 650);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15));

		JPanel panelFiltros = new JPanel();
		panelFiltros.setBackground(bgWhite);
		panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 10));
		panelFiltros.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.LIGHT_GRAY, 1, true), 
				new EmptyBorder(5, 5, 5, 5)
		));
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar:");
		lblBuscar.setFont(fontLabel);
		panelFiltros.add(lblBuscar);

		txtBuscar = new JTextField(15);
		txtBuscar.setFont(fontInput);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.LIGHT_GRAY, 1, true),
				new EmptyBorder(5, 5, 5, 5)
		));
		panelFiltros.add(txtBuscar);

		JLabel lblCliente = new JLabel("Cliente:");
		lblCliente.setFont(fontLabel);
		panelFiltros.add(lblCliente);

		cbxClientes = new JComboBox<>();
		cbxClientes.setFont(fontInput);
		cbxClientes.setPrototypeDisplayValue("Seleccionar cliente      ");
		panelFiltros.add(cbxClientes);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(fontLabel);
		panelFiltros.add(lblEstado);

		cbxEstado = new JComboBox<>();
		cbxEstado.setFont(fontInput);
		cbxEstado.addItem("Todos");
		cbxEstado.addItem("Pagados");
		cbxEstado.addItem("Pendientes");
		panelFiltros.add(cbxEstado);

		JButton btnLimpiar = primaryButton("Limpiar Filtros");
		panelFiltros.add(btnLimpiar);

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(bgWhite);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		table.getTableHeader().setBackground(alticeBlue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(fontInput);
		table.setSelectionBackground(new Color(208, 223, 247));
		table.setSelectionForeground(Color.BLACK);

		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] headers = {"ID Pago", "Cliente", "ID Contrato", "Fecha Inicio", "Fecha Venc.", "Fecha Pago", "Monto Pagado", "Por Pagar", "Devolución", "Estado"};
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

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = outlineButton("Cerrar Listado");
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

			String fechaPagoReal = "Pendiente";
			if (p.getFechaPagoDelCliente() != null) {
				fechaPagoReal = p.getFechaPagoDelCliente().toString().replace("T", " ");
			}

			Object[] row = new Object[10];
			row[0] = p.getIdPago();
			row[1] = p.getContrato().getCliente().getNombre();
			row[2] = p.getContrato().getIdContrato();
			row[3] = p.getFechaInicioPago();
			row[4] = p.getFechaVencimientoPago();
			row[5] = fechaPagoReal;
			row[6] = "$ " + String.format("%.2f", p.getPagoDelCliente());
			row[7] = "$ " + String.format("%.2f", p.getTotalPorPagar());
			row[8] = "$ " + String.format("%.2f", p.getMontoADevolverCliente());
			row[9] = p.isPagadoTotal() ? "Pagado" : "Pendiente";
			model.addRow(row);
		}
	}
	
	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(alticeBlue);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(alticeBlue);
		btn.setBackground(bgWhite);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(alticeBlue, 2, true));
		btn.setPreferredSize(new java.awt.Dimension(140, 35));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
}