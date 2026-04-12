package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.Cliente;
import logico.Contrato;
import logico.EmpresaAltice;

public class ListarContratosGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private JComboBox<String> cbxClientes;
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
		setBounds(100, 100, 900, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		// Panel de búsqueda
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JPanel panelFiltros = new JPanel();
		panelFiltros.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		panelBusqueda.add(panelFiltros, BorderLayout.CENTER);

		JLabel lblBuscar = new JLabel("Buscar: ");
		panelFiltros.add(lblBuscar);

		txtBuscar = new JTextField(15);
		panelFiltros.add(txtBuscar);

		JLabel lblCliente = new JLabel("Cliente: ");
		panelFiltros.add(lblCliente);

		cbxClientes = new JComboBox<>();
		cbxClientes.setPrototypeDisplayValue("Seleccionar cliente      ");
		panelFiltros.add(cbxClientes);

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

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = table.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = table.convertRowIndexToModel(indexVisual);

					String idClienteStr = (String) model.getValueAt(indexReal, 0);
					String nombrePlanStr = (String) model.getValueAt(indexReal, 2);

					contratoSeleccionado = null;

					for (Contrato cto : EmpresaAltice.getInstance().getMisContratos()) {
						if (cto.getCliente().getCodigoCliente().equals(idClienteStr) &&
							cto.getPlanContrato().getNombrePlan().equals(nombrePlanStr)) {
							contratoSeleccionado = cto;
							break;
						}
					}

					if (contratoSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnVerServiciosPlan.setEnabled(true);
						btnCancelarContrato.setEnabled(contratoSeleccionado.isActivo());
						btnReactivarContrato.setEnabled(!contratoSeleccionado.isActivo());
					}
				}
			}
		});

		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		String[] headers = {"Cód. Cliente", "Nombre Cliente", "Plan", "Estado", "Vendedor", "Mensualidad Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		// Filtro por texto en tiempo real
		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				aplicarFiltros();
			}
		});

		// Filtro por cliente al seleccionar del desplegable
		cbxClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarFiltros();
			}
		});

		// Limpiar filtros
		btnLimpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtBuscar.setText("");
				cbxClientes.setSelectedIndex(0);
				sorter.setRowFilter(null);
			}
		});

		// Panel de botones
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btnVerServiciosPlan = new JButton("Ver Servicios del Plan");
		btnVerServiciosPlan.setEnabled(false);
		btnVerServiciosPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contratoSeleccionado != null) {
					ListarServiciosXPlan visorServicios = new ListarServiciosXPlan(contratoSeleccionado.getPlanContrato().getServiciosPlan());
					visorServicios.setVisible(true);
				}
			}
		});

		btnCancelarContrato = new JButton("Desactivar Contrato");
		btnCancelarContrato.setEnabled(false);
		btnCancelarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (contratoSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null,
							"¿Desea desactivar el contrato?", "Confirmar", JOptionPane.YES_NO_OPTION);

					if (opcion == JOptionPane.YES_OPTION) {
						contratoSeleccionado.setActivo(false);
						EmpresaAltice.getInstance().refrescarConteosContratos();

						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
								empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
								empresa.getMisContratos(), empresa.getPagos());

						loadContratos();
						btnCancelarContrato.setEnabled(false);
						btnVerServiciosPlan.setEnabled(false);
						contratoSeleccionado = null;

						JOptionPane.showMessageDialog(null, "Contrato desactivado correctamente.");
					}
				}
			}
		});
		
		 btnReactivarContrato = new JButton("Reactivar Contrato");
	        btnReactivarContrato.setEnabled(false);
	        btnReactivarContrato.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                if (contratoSeleccionado != null) {
	                    int option = JOptionPane.showConfirmDialog(null,
	                            "¿Desea reactivar el contrato " + contratoSeleccionado.getIdContrato() +
	                            " del cliente " + contratoSeleccionado.getCliente().getNombre() + "?",
	                            "Confirmación", JOptionPane.YES_NO_OPTION);

	                    if (option == JOptionPane.YES_OPTION) {
	                        contratoSeleccionado.getCliente().solicitarReactivarContratoById(contratoSeleccionado.getIdContrato());
	                        
	                        EmpresaAltice empresa = EmpresaAltice.getInstance();
	                        empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
	                                empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
	                                empresa.getMisContratos(), empresa.getPagos());
	                        
	                        loadContratos();
	                        btnCancelarContrato.setEnabled(false);
	                        btnReactivarContrato.setEnabled(false);
	                        btnVerServiciosPlan.setEnabled(false);
	                        contratoSeleccionado = null;
	                        
	                        JOptionPane.showMessageDialog(null, "Contrato reactivado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
	                    }
	                }
	            }
	        });
	        buttonPane.add(btnReactivarContrato);

		if (modoSeleccion) {
			buttonPane.add(btnSeleccionar);
			btnCancelarContrato.setVisible(false);
			btnReactivarContrato.setVisible(false);
			btnVerServiciosPlan.setVisible(false);
		} else {
			buttonPane.add(btnVerServiciosPlan);
			buttonPane.add(btnCancelarContrato);
			 buttonPane.add(btnReactivarContrato);
			btnSeleccionar.setVisible(false);
		}

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contratoSeleccionado = null;
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadClientes();
		loadContratos();
	}

	private void aplicarFiltros() {
		String textoBusqueda = txtBuscar.getText().trim();
		String clienteSeleccionado = (String) cbxClientes.getSelectedItem();
		boolean hayTexto = textoBusqueda.length() > 0;
		boolean hayCliente = clienteSeleccionado != null && !clienteSeleccionado.equals("Todos");

		if (!hayTexto && !hayCliente) {
			sorter.setRowFilter(null);
		} else if (hayTexto && !hayCliente) {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
		} else if (!hayTexto && hayCliente) {
			sorter.setRowFilter(RowFilter.regexFilter("(?i)" + clienteSeleccionado, 1)); // columna 1 = Nombre Cliente
		} else {
			sorter.setRowFilter(RowFilter.andFilter(Arrays.asList(
				RowFilter.regexFilter("(?i)" + textoBusqueda),
				RowFilter.regexFilter("(?i)" + clienteSeleccionado, 1)
			)));
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

	private void loadContratos() {
		model.setRowCount(0);
		for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
			String estado = c.isActivo() ? "Activo" : "Inactivo";

			float porcentajeDecimal = c.getPorcentajeComisionAplicado() / 100.0f;
			float costoComision = c.getPrecioMensualAcordado() * porcentajeDecimal;
			float totalMensual = c.getPrecioMensualAcordado() + costoComision;

			Object[] row = new Object[6];
			row[0] = c.getCliente().getCodigoCliente();
			row[1] = c.getCliente().getNombre();
			row[2] = c.getPlanContrato().getNombrePlan();
			row[3] = estado;
			row[4] = c.getEmpConsiguioContrato().getNombre();
			row[5] = "$ " + totalMensual;
			model.addRow(row);
		}
	}

	public Contrato getContratoSeleccionado() {
		return contratoSeleccionado;
	}
}