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
import logico.Plan;
import logico.Servicio;

public class ListarPlanes extends JDialog {

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
	private TableRowSorter<DefaultTableModel> sorter;

	private JButton btnSeleccionar;
	private JButton btnEliminar;
	private JButton btnVerServicios;
	private JButton btnAgregarServicio;

	private Plan planSeleccionado = null;

	public static void main(String[] args) {
		try {
			ListarPlanes dialog = new ListarPlanes(false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarPlanes(boolean modoSeleccion) {
		setTitle("Catálogo de Planes");
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
		JLabel lblTitulo = new JLabel("Catálogo de Planes");
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

		JPanel panelFiltros = new JPanel(new BorderLayout(10, 0));
		panelFiltros.setBackground(Color.WHITE);
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Plan (ID, Nombre):");
		lblBuscar.setFont(FONT_LABEL);
		lblBuscar.setForeground(ALTICE_BLUE);
		panelFiltros.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setFont(FONT_INPUT);
		txtBuscar.setBackground(ALTICE_LIGHT);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(ALTICE_BORDER, 1, true),
			new EmptyBorder(4, 8, 4, 8)
		));
		panelFiltros.add(txtBuscar, BorderLayout.CENTER);

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
					String idPlan = (String) model.getValueAt(indexReal, 0);
					planSeleccionado = null;
					for (Plan p : EmpresaAltice.getInstance().getMisPlanes()) {
						if (p.getIdPlan().equalsIgnoreCase(idPlan)) {
							planSeleccionado = p;
							break;
						}
					}
					if (planSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnVerServicios.setEnabled(true);
						btnAgregarServicio.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"ID Plan", "Nombre del Plan", "Cant. Servicios", "Precio sin Imp.", "Precio Mensual + Imp. ($)"};
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

		btnVerServicios = primaryButton("Ver Servicios del Plan");
		btnVerServicios.setEnabled(false);
		btnVerServicios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (planSeleccionado != null) {
					if (planSeleccionado.getServiciosPlan().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Este plan aún no tiene servicios asignados.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ListarServiciosXPlan visor = new ListarServiciosXPlan(planSeleccionado.getServiciosPlan());
						visor.setVisible(true);
					}
				}
			}
		});

		btnAgregarServicio = primaryButton("Agregar Servicio");
		btnAgregarServicio.setEnabled(false);
		btnAgregarServicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (planSeleccionado != null) {
					ListarServicios listarServis = new ListarServicios("Todos", true);
					listarServis.setVisible(true);
					Servicio s = listarServis.getServicioSeleccionado();
					if (s != null) {
						for (Servicio existing : planSeleccionado.getServiciosPlan()) {
							if (existing.getTipoServicio().equalsIgnoreCase(s.getTipoServicio())) {
								JOptionPane.showMessageDialog(null,
									"Este plan ya tiene un servicio de tipo " + s.getTipoServicio() + ".",
									"Tipo duplicado", JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
						planSeleccionado.getServiciosPlan().add(s);
						s.setEstaEnUso(true);
						planSeleccionado.actualizarPrecioPlan(planSeleccionado.calcularPrecioTotal());
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
							empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						JOptionPane.showMessageDialog(null, "Servicio agregado correctamente al plan.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
						loadPlanes();
					}
				}
			}
		});

		btnEliminar = primaryButton("Eliminar");
		btnEliminar.setBackground(new Color(200, 50, 50));
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (planSeleccionado != null) {
					int confirmacion = JOptionPane.showConfirmDialog(null,
						"¿Está seguro de que desea eliminar el plan: " + planSeleccionado.getNombrePlan() + "?",
						"Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					if (confirmacion == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						if (planSeleccionado.getServiciosPlan() != null) {
							for (Servicio s : planSeleccionado.getServiciosPlan()) {
								s.setEstaEnUso(false);
							}
						}
						empresa.getMisPlanes().remove(planSeleccionado);
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
							empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						JOptionPane.showMessageDialog(null, "Plan eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						loadPlanes();
						btnEliminar.setEnabled(false);
						btnVerServicios.setEnabled(false);
						btnAgregarServicio.setEnabled(false);
						planSeleccionado = null;
					}
				}
			}
		});

		if (modoSeleccion == true) {
			buttonPane.add(btnSeleccionar);
			btnEliminar.setVisible(false);
			btnVerServicios.setVisible(false);
			btnAgregarServicio.setVisible(false);
		} else {
			buttonPane.add(btnVerServicios);
			buttonPane.add(btnAgregarServicio);
			buttonPane.add(btnEliminar);
			btnSeleccionar.setVisible(false);
		}

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				planSeleccionado = null;
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadPlanes();
	}

	private void loadPlanes() {
		model.setRowCount(0);
		for (Plan p : EmpresaAltice.getInstance().getMisPlanes()) {
			int cantidadServicios = (p.getServiciosPlan() != null) ? p.getServiciosPlan().size() : 0;
			model.addRow(new Object[]{
				p.getIdPlan(),
				p.getNombrePlan(),
				cantidadServicios,
				"$ " + String.format("%.2f", p.getPrecioSinImpuestos()),
				"$ " + String.format("%.2f", p.getPrecioTotal())
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

	public Plan getPlanSeleccionado() {
		return planSeleccionado;
	}
}