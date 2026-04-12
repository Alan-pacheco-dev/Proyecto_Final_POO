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

import logico.Cliente;
import logico.EmpresaAltice;

public class ListarClientes extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;
	private JButton btnActualizar;
	private JButton btnEliminar;
	private Cliente selected = null;
	private boolean modoSeleccion;

	public ListarClientes() {
		this(false);
	}

	public ListarClientes(boolean modoSeleccion) {
		this.modoSeleccion = modoSeleccion;
		setTitle(modoSeleccion ? "Seleccionar Cliente" : "Listado de Clientes");
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Cliente (Nombre, Código, ID): ");
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

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
					String idCliente = (String) model.getValueAt(indexReal, 0);
					selected = EmpresaAltice.getInstance().buscarClienteById(idCliente);

					if (modoSeleccion) {
						btnActualizar.setEnabled(true);
					} else {
						btnActualizar.setEnabled(true);
						btnEliminar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"ID", "Código", "Nombre", "Email", "Deuda"};
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
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		if (modoSeleccion) {
			btnActualizar = new JButton("Seleccionar");
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
			btnActualizar = new JButton("Actualizar");
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
						selected = null;
					}
				}
			});
			buttonPane.add(btnActualizar);

			btnEliminar = new JButton("Eliminar");
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
							selected = null;
						}
					}
				}
			});
			buttonPane.add(btnEliminar);
		}

		JButton btnCerrar = new JButton("Cerrar");
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
			Object[] row = new Object[5];
			row[0] = c.getIdPersona();
			row[1] = c.getCodigoCliente();
			row[2] = c.getNombre();
			row[3] = c.getEmail();
			row[4] = c.getDeuda();
			model.addRow(row);
		}
	}
}