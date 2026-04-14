package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Cliente;
import logico.Empleado;
import logico.EmpresaAltice;
import logico.Usuario;

public class ListarClientes extends JDialog {

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
	
	// Paleta de colores y fuentes institucionales
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color dangerRed = Color.decode("#DC3545");
	private Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

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
		setBounds(100, 100, 900, 550); // Un poco más ancho para que la tabla respire
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15));

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(bgWhite);
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Cliente (Nombre, Código, ID): ");
		lblBuscar.setFont(fontLabel);
		lblBuscar.setForeground(Color.DARK_GRAY);
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setFont(fontInput);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(Color.LIGHT_GRAY, 1, true),
				new EmptyBorder(5, 10, 5, 10)));
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(bgWhite);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// Estilizando la tabla
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		table.getTableHeader().setBackground(alticeBlue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(fontInput);
		table.setSelectionBackground(new Color(208, 223, 247)); // Azul muy claro para la selección
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
				return false; // Evitar que se edite directo en la tabla
			}
		};
		String[] headers = {"ID", "Código", "Nombre", "Email", "Contratos Activos", "Deuda"};
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
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		if (modoSeleccion) {
			btnActualizar = primaryButton("Seleccionar");
			btnActualizar.setEnabled(false);
			btnActualizar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (selected != null) {
						int confirm = JOptionPane.showConfirmDialog(null,
								"¿Está seguro que desea seleccionar al cliente: " + selected.getNombre() + "?",
								"Confirmación", JOptionPane.YES_NO_OPTION);
						if (confirm == JOptionPane.YES_OPTION) {
							dispose();
						}
					}
				}
			});
			buttonPane.add(btnActualizar);

		} else {
			
			btnVerContratos = primaryButton("Ver Contratos");
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
			
			btnActualizar = primaryButton("Actualizar Datos");
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
								"¿Desea realmente eliminar al cliente: " + selected.getNombre() + "?",
								"Confirmación", JOptionPane.WARNING_MESSAGE);
						if (option == JOptionPane.YES_OPTION) {
							boolean eliminado = EmpresaAltice.getInstance().eliminarCliente(selected);
							if (eliminado) {
								JOptionPane.showMessageDialog(null, "El cliente ha sido eliminado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
							} else {
								JOptionPane.showMessageDialog(null, "El cliente no puede ser eliminado ya que tiene contratos activos", "Información", JOptionPane.INFORMATION_MESSAGE);
							}
							EmpresaAltice empresa = EmpresaAltice.getInstance();
							empresa.GuardarDatos(empresa.getMisClientes(),
									empresa.getMisEmpleados(),
									empresa.getMisPlanes(),
									empresa.getMisServicios(),
									empresa.getMisUsuarios(),
									empresa.getMisContratos(),
									empresa.getPagos());
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
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	private JButton dangerButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(dangerRed);
		btn.setBackground(bgWhite);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(dangerRed, 2, true));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
}