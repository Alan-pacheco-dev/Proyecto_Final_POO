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

import logico.Cliente;
import logico.Empleado;
import logico.EmpresaAltice;
import logico.Usuario;

public class ListarClientes extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Color DANGER_RED = Color.decode("#DC3545");
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;

	private JButton btnActualizar;
	private JButton btnEliminar;
	private JButton btnVerContratos;
	private JButton btnAgregarContrato;

	private Cliente selected = null;
	private boolean modoSeleccion;

	public ListarClientes() {
		this(false);
	}

	public ListarClientes(boolean modoSeleccion) {
		this.modoSeleccion = modoSeleccion;

		if (modoSeleccion) {
			setTitle("Seleccionar Cliente");
		} else {
			setTitle("Listado de Clientes");
		}

		setResizable(false);
		setSize(900, 550);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel(modoSeleccion ? "Seleccionar Cliente" : "Listado de Clientes");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(Color.WHITE);
		JLabel lblAltice = new JLabel("altice");
		lblAltice.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblAltice.setForeground(new Color(255, 255, 255, 160));
		header.add(lblTitulo, BorderLayout.WEST);
		header.add(lblAltice, BorderLayout.EAST);
		getContentPane().add(header, BorderLayout.NORTH);

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15));

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Cliente (Nombre, Codigo, ID): ");
		lblBuscar.setFont(FONT_LABEL);
		lblBuscar.setForeground(Color.DARK_GRAY);
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setFont(FONT_INPUT);
		txtBuscar.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(Color.WHITE);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		table.getTableHeader().setFont(FONT_LABEL);
		table.getTableHeader().setBackground(ALTICE_BLUE);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(FONT_INPUT);
		table.setSelectionBackground(ALTICE_LIGHT);
		table.setSelectionForeground(Color.BLACK);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = table.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = table.convertRowIndexToModel(indexVisual);
					String idCliente = (String) model.getValueAt(indexReal, 0);
					selected = EmpresaAltice.getInstance().buscarClienteById(idCliente);

					if (modoSeleccion) {
						btnActualizar.setEnabled(true);
					} else {
						btnActualizar.setEnabled(true);
						btnEliminar.setEnabled(true);

						if (selected.getCantContratosActivos() > 0) {
							btnVerContratos.setEnabled(true);
							btnAgregarContrato.setEnabled(true);
						} else {
							btnVerContratos.setEnabled(false);
							btnAgregarContrato.setEnabled(false);
						}
					}
				}
			}
		});

		model = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] headers = { "ID", "Codigo", "Nombre", "Email", "Contratos Activos", "Deuda" };
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

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		if (modoSeleccion) {
			btnActualizar = primaryButton("Seleccionar");
			btnActualizar.setEnabled(false);
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						int confirm = JOptionPane.showConfirmDialog(null,
								"Desea seleccionar al cliente: " + selected.getNombre() + "?", "Confirmacion",
								JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							dispose();
						}
					}
				}
			});
			buttonPane.add(btnActualizar);

		} else {

			btnVerContratos = outlineButton("Ver Contratos");
			btnVerContratos.setEnabled(false);
			btnVerContratos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						ListarContratosXCliente visorHistorial = new ListarContratosXCliente(selected);
						visorHistorial.setVisible(true);
					}
				}
			});
			buttonPane.add(btnVerContratos);

			btnAgregarContrato = primaryButton("Agregar Contrato");
			btnAgregarContrato.setEnabled(false);
			btnAgregarContrato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						Usuario usuarioActual = EmpresaAltice.getLoginUser();
						Empleado empleadoSesion = null;
						if (usuarioActual != null) {
							empleadoSesion = EmpresaAltice.getInstance().buscarEmpleadoPorUsuario(usuarioActual);
						}
						RegistrarContrato regisCto = new RegistrarContrato(selected, empleadoSesion);
						regisCto.setModal(true);
						regisCto.setVisible(true);

						loadClientes();

						btnAgregarContrato.setEnabled(false);
						btnActualizar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnVerContratos.setEnabled(false);
						selected = null;
					}
				}
			});
			buttonPane.add(btnAgregarContrato);

			btnActualizar = outlineButton("Actualizar Datos");
			btnActualizar.setEnabled(false);
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						RegistrarCliente regCli = new RegistrarCliente(selected);
						regCli.setModal(true);
						regCli.setVisible(true);

						loadClientes();

						btnActualizar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnVerContratos.setEnabled(false);
						btnAgregarContrato.setEnabled(false);
						selected = null;
					}
				}
			});
			buttonPane.add(btnActualizar);

			btnEliminar = dangerButton("Eliminar");
			btnEliminar.setEnabled(false);
			btnEliminar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						int option = JOptionPane.showConfirmDialog(null,
								"Desea realmente eliminar al cliente: " + selected.getNombre() + "?", "Confirmacion",
								JOptionPane.WARNING_MESSAGE);
						if (option == JOptionPane.YES_OPTION) {
							boolean eliminado = EmpresaAltice.getInstance().eliminarCliente(selected);
							if (eliminado) {
								JOptionPane.showMessageDialog(null, "El cliente ha sido eliminado con exito",
										"Informacion", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null,
										"El cliente no puede ser eliminado tiene contratos activos", "Informacion",
										JOptionPane.INFORMATION_MESSAGE);
							}
							EmpresaAltice empresa = EmpresaAltice.getInstance();
							empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
									empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
									empresa.getMisContratos(), empresa.getPagos());
							loadClientes();
							btnEliminar.setEnabled(false);
							btnActualizar.setEnabled(false);
							btnVerContratos.setEnabled(false);
							btnAgregarContrato.setEnabled(false);
							selected = null;
						}
					}
				}
			});
			buttonPane.add(btnEliminar);
		}

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = null;
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadClientes();
	}

	public Cliente getClienteSeleccionado() {
		return selected;
	}

	private void loadClientes() {
		model.setRowCount(0);
		for (Cliente c : EmpresaAltice.getInstance().getMisClientes()) {
			Object[] row = new Object[6];
			row[0] = c.getIdPersona();
			row[1] = c.getCodigoCliente();
			row[2] = c.getNombre();
			row[3] = c.getEmail();
			row[4] = c.getCantContratosActivos();
			row[5] = "$ " + String.format("%.2f", c.getDeuda());
			model.addRow(row);
		}
	}

	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(Color.WHITE);
		btn.setBackground(ALTICE_BLUE);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(ALTICE_BLUE);
		btn.setBackground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JButton dangerButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(DANGER_RED);
		btn.setBackground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(DANGER_RED, 2, true));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
}