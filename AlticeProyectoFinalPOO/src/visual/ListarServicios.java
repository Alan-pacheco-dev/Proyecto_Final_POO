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

import logico.EmpresaAltice;
import logico.Servicio;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class ListarServicios extends JDialog {

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
	private JComboBox<String> cbxFiltroTipo;
	private TableRowSorter<DefaultTableModel> sorter;

	private JButton btnSeleccionar;
	private JButton btnEliminar;

	private Servicio servicioSeleccionado = null;
	private boolean modoSeleccionGlobal;

	public static void main(String[] args) {
		try {
			ListarServicios dialog = new ListarServicios("Todos", false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarServicios(String tipoInicial, boolean modoSeleccion) {
		this.modoSeleccionGlobal = modoSeleccion;

		setTitle("Catálogo de Servicios Disponibles");
		setModal(modoSeleccion);
		setResizable(false);

		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(pantalla.width - 400, pantalla.height - 400);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Catálogo de Servicios Disponibles");
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

		JPanel panelFiltros = new JPanel(new BorderLayout(20, 0));
		panelFiltros.setBackground(Color.WHITE);
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JPanel panelBuscadorTexto = new JPanel(new BorderLayout(6, 0));
		panelBuscadorTexto.setBackground(Color.WHITE);
		JLabel lblBuscar = new JLabel("Buscar (ID, Precio):");
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
		JLabel lblTipo = new JLabel("Tipo:");
		lblTipo.setFont(FONT_LABEL);
		lblTipo.setForeground(ALTICE_BLUE);
		panelComboBox.add(lblTipo, BorderLayout.WEST);
		cbxFiltroTipo = new JComboBox<String>();
		cbxFiltroTipo.addItem("Todos");
		cbxFiltroTipo.addItem("Móvil");
		cbxFiltroTipo.addItem("Internet");
		cbxFiltroTipo.addItem("Telefonía");
		cbxFiltroTipo.addItem("Televisión");
		cbxFiltroTipo.setFont(FONT_INPUT);
		cbxFiltroTipo.setPreferredSize(new Dimension(180, 30));
		if (tipoInicial != null) {
			cbxFiltroTipo.setSelectedItem(tipoInicial);
		}
		panelComboBox.add(cbxFiltroTipo, BorderLayout.CENTER);
		panelFiltros.add(panelComboBox, BorderLayout.EAST);

		JPanel panelTabla = new JPanel(new BorderLayout());
		panelTabla.setBackground(Color.WHITE);
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		table = new JTable();
		table.setFont(FONT_TABLE);
		table.setRowHeight(26);
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
					String idServicio = (String) model.getValueAt(indexReal, 0);
					servicioSeleccionado = null;
					for (Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
						if (s.getIdServicio().equalsIgnoreCase(idServicio)) {
							servicioSeleccionado = s;
							break;
						}
					}
					if (servicioSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnEliminar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
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

		cbxFiltroTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoElegido = (String) cbxFiltroTipo.getSelectedItem();
				loadServicios(tipoElegido);
				btnSeleccionar.setEnabled(false);
				btnEliminar.setEnabled(false);
				servicioSeleccionado = null;
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

		btnEliminar = primaryButton("Eliminar");
		btnEliminar.setBackground(new Color(200, 50, 50));
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (servicioSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null,
						"¿Desea eliminar el servicio " + servicioSeleccionado.getIdServicio() + "?",
						"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (opcion == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.getMisServicios().remove(servicioSeleccionado);
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
							empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						loadServicios((String) cbxFiltroTipo.getSelectedItem());
						btnEliminar.setEnabled(false);
						servicioSeleccionado = null;
					}
				}
			}
		});

		if (modoSeleccionGlobal == true) {
			buttonPane.add(btnSeleccionar);
			btnEliminar.setVisible(false);
		} else {
			buttonPane.add(btnEliminar);
			btnSeleccionar.setVisible(false);
		}

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicioSeleccionado = null;
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadServicios((String) cbxFiltroTipo.getSelectedItem());
	}

	private void loadServicios(String tipoFiltro) {
		model.setRowCount(0);

		if (tipoFiltro.equals("Internet")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Velocidad (Mbps)", "Router", "En Uso"});
		} else if (tipoFiltro.equals("Móvil")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Datos (GB)", "Minutos", "SMS", "En Uso"});
		} else if (tipoFiltro.equals("Telefonía")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Min. Incluidos", "Ilimitado", "Costo/Min Extra", "En Uso"});
		} else if (tipoFiltro.equals("Televisión")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Canales", "Cajitas", "HD", "En Uso"});
		} else {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Tipo", "Precio Mensual ($)", "Detalles", "En Uso"});
		}

		for (Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
			if (modoSeleccionGlobal == true && s.isEstaEnUso() == true) continue;

			String textoEnUso = s.isEstaEnUso() ? "Sí" : "No";

			if (tipoFiltro.equals("Todos")) {
				model.addRow(new Object[]{
					s.getIdServicio(),
					s.getTipoServicio(),
					"$ " + String.format("%.2f", s.getPrecioServicio()),
					buildDetalles(s),
					textoEnUso
				});
			} else if (tipoFiltro.equals("Internet") && s instanceof ServicioInternet) {
				ServicioInternet net = (ServicioInternet) s;
				model.addRow(new Object[]{
					net.getIdServicio(),
					"$ " + String.format("%.2f", net.getPrecioServicio()),
					net.getVelocidadMbps(),
					net.isTieneRouter() ? "Sí" : "No",
					textoEnUso
				});
			} else if (tipoFiltro.equals("Móvil") && s instanceof ServicioMovil) {
				ServicioMovil movil = (ServicioMovil) s;
				model.addRow(new Object[]{
					movil.getIdServicio(),
					"$ " + String.format("%.2f", movil.getPrecioServicio()),
					movil.getDatosGb(),
					movil.getMinutos(),
					movil.getSms(),
					textoEnUso
				});
			} else if (tipoFiltro.equals("Telefonía") && s instanceof ServicioTelefonia) {
				ServicioTelefonia tel = (ServicioTelefonia) s;
				model.addRow(new Object[]{
					tel.getIdServicio(),
					"$ " + String.format("%.2f", tel.getPrecioServicio()),
					tel.getMinutosIncluidos(),
					tel.isLlamadasIlimitadas() ? "Sí" : "No",
					"$ " + String.format("%.2f", tel.getCostoMinutoExtra()),
					textoEnUso
				});
			} else if (tipoFiltro.equals("Televisión") && s instanceof ServicioTelevision) {
				ServicioTelevision tv = (ServicioTelevision) s;
				model.addRow(new Object[]{
					tv.getIdServicio(),
					"$ " + String.format("%.2f", tv.getPrecioServicio()),
					tv.getCantidadCanales(),
					tv.getCajitasIncluidas(),
					tv.isTieneHD() ? "Sí" : "No",
					textoEnUso
				});
			}
		}

		if (tipoFiltro.equals("Todos") && table.getColumnCount() > 3) {
			table.getColumnModel().getColumn(3).setPreferredWidth(300);
		}
	}

	private String buildDetalles(Servicio s) {
		if (s instanceof ServicioInternet) {
			ServicioInternet net = (ServicioInternet) s;
			return "Velocidad: " + net.getVelocidadMbps() + " Mbps | Router: " + (net.isTieneRouter() ? "Sí" : "No");
		} else if (s instanceof ServicioMovil) {
			ServicioMovil movil = (ServicioMovil) s;
			return "Datos: " + movil.getDatosGb() + " GB | Minutos: " + movil.getMinutos() + " | SMS: " + movil.getSms();
		} else if (s instanceof ServicioTelefonia) {
			ServicioTelefonia tel = (ServicioTelefonia) s;
			return "Min. incluidos: " + tel.getMinutosIncluidos() + " | Ilimitado: " + (tel.isLlamadasIlimitadas() ? "Sí" : "No") + " | Costo/min extra: $" + String.format("%.2f", tel.getCostoMinutoExtra());
		} else if (s instanceof ServicioTelevision) {
			ServicioTelevision tv = (ServicioTelevision) s;
			return "Canales: " + tv.getCantidadCanales() + " | Cajitas: " + tv.getCajitasIncluidas() + " | HD: " + (tv.isTieneHD() ? "Sí" : "No");
		} else {
			return "-";
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

	public Servicio getServicioSeleccionado() {
		return servicioSeleccionado;
	}
}