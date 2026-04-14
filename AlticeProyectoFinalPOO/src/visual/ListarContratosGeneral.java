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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
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
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Contrato;
import logico.EmpresaAltice;

public class ListarContratosGeneral extends JDialog {

	private static final Color ALTICE_BLUE   = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT  = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font  FONT_LABEL    = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_INPUT    = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font  FONT_BTN      = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_TABLE    = new Font("SansSerif", Font.PLAIN, 12);
	private static final Font  FONT_HEADER   = new Font("SansSerif", Font.BOLD, 12);

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private JComboBox<String> cbxFiltroEstado;
	private TableRowSorter<DefaultTableModel> sorter;

	private JButton btnSeleccionar;
	private JButton btnCancelarContrato;
	private JButton btnReactivarContrato;
	private JButton btnVerServiciosPlan;

	private Contrato contratoSeleccionado = null;

	public ListarContratosGeneral(boolean modoSeleccion) {
		setTitle("Registro General de Contratos");
		setModal(modoSeleccion);
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
		JLabel lblTitulo = new JLabel("Registro General de Contratos");
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

		JPanel panelFiltros = new JPanel(new BorderLayout(16, 0));
		panelFiltros.setBackground(Color.WHITE);
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JPanel panelBuscadorTexto = new JPanel(new BorderLayout(6, 0));
		panelBuscadorTexto.setBackground(Color.WHITE);
		JLabel lblBuscar = new JLabel("Buscar (ID, Cliente, Plan):");
		lblBuscar.setFont(FONT_LABEL);
		lblBuscar.setForeground(ALTICE_BLUE);
		panelBuscadorTexto.add(lblBuscar, BorderLayout.WEST);
		txtBuscar = new JTextField();
		txtBuscar.setFont(FONT_INPUT);
		txtBuscar.setBackground(ALTICE_LIGHT);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(ALTICE_BORDER, 1, true),
			new EmptyBorder(4, 8, 4, 8)
		));
		panelBuscadorTexto.add(txtBuscar, BorderLayout.CENTER);
		panelFiltros.add(panelBuscadorTexto, BorderLayout.CENTER);

		JPanel panelComboBox = new JPanel(new BorderLayout(6, 0));
		panelComboBox.setBackground(Color.WHITE);
		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(FONT_LABEL);
		lblEstado.setForeground(ALTICE_BLUE);
		panelComboBox.add(lblEstado, BorderLayout.WEST);
		cbxFiltroEstado = new JComboBox<String>();
		cbxFiltroEstado.addItem("Todo");
		cbxFiltroEstado.addItem("Activos");
		cbxFiltroEstado.addItem("Inactivos");
		cbxFiltroEstado.setFont(FONT_INPUT);
		cbxFiltroEstado.setPreferredSize(new Dimension(150, 30));
		panelComboBox.add(cbxFiltroEstado, BorderLayout.CENTER);
		panelFiltros.add(panelComboBox, BorderLayout.EAST);

		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setBackground(Color.WHITE);
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		table = new JTable();
		table.setFont(FONT_TABLE);
		table.setRowHeight(24);
		table.setGridColor(ALTICE_BORDER);
		table.setShowGrid(true);
		table.setSelectionBackground(new Color(210, 225, 255));
		table.setSelectionForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(FONT_HEADER);
		table.getTableHeader().setBackground(ALTICE_BLUE);
		table.getTableHeader().setForeground(Color.WHITE);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = table.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = table.convertRowIndexToModel(indexVisual);
					String idCto = (String) model.getValueAt(indexReal, 0);
					contratoSeleccionado = null;
					for (Contrato cto : EmpresaAltice.getInstance().getMisContratos()) {
						if (cto.getIdContrato().equals(idCto)) {
							contratoSeleccionado = cto;
							break;
						}
					}
					if (contratoSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnVerServiciosPlan.setEnabled(true);
						if (contratoSeleccionado.isActivo() == true) {
							btnCancelarContrato.setEnabled(true);
							btnReactivarContrato.setEnabled(false);
						} else {
							btnCancelarContrato.setEnabled(false);
							btnReactivarContrato.setEnabled(true);
						}
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"ID Contrato", "Cód. Cliente", "Nombre Cliente", "Plan", "Estado", "Vendedor", "Mensual. Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String textoBusqueda = txtBuscar.getText();
				if (textoBusqueda.trim().isEmpty()) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
				}
			}
		});

		cbxFiltroEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String estadoElegido = (String) cbxFiltroEstado.getSelectedItem();
				loadContratos(estadoElegido);
				btnSeleccionar.setEnabled(false);
				btnCancelarContrato.setEnabled(false);
				btnReactivarContrato.setEnabled(false);
				btnVerServiciosPlan.setEnabled(false);
				contratoSeleccionado = null;
			}
		});

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnSeleccionar = primaryButton("Seleccionar");
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnVerServiciosPlan = primaryButton("Ver Servicios del Plan");
		btnVerServiciosPlan.setEnabled(false);
		btnVerServiciosPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contratoSeleccionado != null) {
					ListarServiciosXPlan visorServicios = new ListarServiciosXPlan(contratoSeleccionado.getPlanContrato().getServiciosPlan());
					visorServicios.setVisible(true);
				}
			}
		});

		btnReactivarContrato = primaryButton("Reactivar Contrato");
		btnReactivarContrato.setBackground(new Color(30, 140, 70));
		btnReactivarContrato.setEnabled(false);
		btnReactivarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contratoSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null,
						"¿Desea volver a activar el contrato " + contratoSeleccionado.getIdContrato() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION) {
						contratoSeleccionado.setActivo(true);
						EmpresaAltice.getInstance().refrescarConteosContratos();
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
							empresa.getMisContratos(), empresa.getPagos());
						loadContratos((String) cbxFiltroEstado.getSelectedItem());
						btnCancelarContrato.setEnabled(false);
						btnReactivarContrato.setEnabled(false);
						btnVerServiciosPlan.setEnabled(false);
						contratoSeleccionado = null;
						JOptionPane.showMessageDialog(null, "Contrato reactivado correctamente.");
					}
				}
			}
		});

		btnCancelarContrato = primaryButton("Desactivar Contrato");
		btnCancelarContrato.setBackground(new Color(200, 50, 50));
		btnCancelarContrato.setEnabled(false);
		btnCancelarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contratoSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null,
						"¿Desea desactivar el contrato " + contratoSeleccionado.getIdContrato() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					if (opcion == JOptionPane.YES_OPTION) {
						contratoSeleccionado.setActivo(false);
						EmpresaAltice.getInstance().refrescarConteosContratos();
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
							empresa.getMisContratos(), empresa.getPagos());
						loadContratos((String) cbxFiltroEstado.getSelectedItem());
						btnCancelarContrato.setEnabled(false);
						btnReactivarContrato.setEnabled(false);
						btnVerServiciosPlan.setEnabled(false);
						contratoSeleccionado = null;
						JOptionPane.showMessageDialog(null, "Contrato desactivado correctamente.");
					}
				}
			}
		});

		if (modoSeleccion == true) {
			buttonPane.add(btnSeleccionar);
			btnCancelarContrato.setVisible(false);
			btnReactivarContrato.setVisible(false);
			btnVerServiciosPlan.setVisible(false);
		} else {
			buttonPane.add(btnVerServiciosPlan);
			buttonPane.add(btnReactivarContrato);
			buttonPane.add(btnCancelarContrato);
			btnSeleccionar.setVisible(false);
		}

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contratoSeleccionado = null;
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadContratos((String) cbxFiltroEstado.getSelectedItem());
	}

	private void loadContratos(String filtro) {
		model.setRowCount(0);
		for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
			if (filtro.equals("Activos") && c.isActivo() == false) continue;
			if (filtro.equals("Inactivos") && c.isActivo() == true) continue;

			String estado = c.isActivo() ? "Activo" : "Inactivo";
			float totalMensual = c.getPrecioMensualAcordado();

			model.addRow(new Object[]{
				c.getIdContrato(),
				c.getCliente().getCodigoCliente(),
				c.getCliente().getNombre(),
				c.getPlanContrato().getNombrePlan(),
				estado,
				c.getEmpConsiguioContrato().getNombre(),
				"$ " + String.format("%.2f", totalMensual)
			});
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

	public Contrato getContratoSeleccionado() {
		return contratoSeleccionado;
	}
}