package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

import logico.Contrato;
import logico.EmpresaAltice;

public class ListarContratosGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private JComboBox<String> cbxFiltroEstado;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private JButton btnSeleccionar;
	private JButton btnCancelarContrato; 
	private JButton btnReactivarContrato; // NUEVO BOTÓN
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

		// --- PANEL SUPERIOR: FILTROS Y BÚSQUEDA ---
		JPanel panelFiltros = new JPanel();
		panelFiltros.setLayout(new BorderLayout(20, 0)); 
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		// 1. Barra de Búsqueda
		JPanel panelBuscadorTexto = new JPanel(new BorderLayout(5, 0));
		panelBuscadorTexto.add(new JLabel("Buscar (ID, Cliente, Plan): "), BorderLayout.WEST);
		txtBuscar = new JTextField();
		panelBuscadorTexto.add(txtBuscar, BorderLayout.CENTER);
		panelFiltros.add(panelBuscadorTexto, BorderLayout.CENTER);

		// 2. ComboBox para Estado
		JPanel panelComboBox = new JPanel(new BorderLayout(5, 0));
		panelComboBox.add(new JLabel("Estado: "), BorderLayout.WEST);
		cbxFiltroEstado = new JComboBox<String>();
		cbxFiltroEstado.addItem("Todo");
		cbxFiltroEstado.addItem("Activos");
		cbxFiltroEstado.addItem("Inactivos");
		cbxFiltroEstado.setPreferredSize(new Dimension(150, 25));
		panelComboBox.add(cbxFiltroEstado, BorderLayout.CENTER);
		panelFiltros.add(panelComboBox, BorderLayout.EAST);

		// --- PANEL CENTRAL: TABLA ---
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
				if(indexVisual != -1) {
					int indexReal = table.convertRowIndexToModel(indexVisual);
					
					// AHORA BUSCAMOS POR EL ID DEL CONTRATO (Columna 0) para que sea 100% exacto
					String idCto = (String) model.getValueAt(indexReal, 0);
					
					contratoSeleccionado = null;
					
					for(Contrato cto : EmpresaAltice.getInstance().getMisContratos()) {
						if(cto.getIdContrato().equals(idCto)) {
							contratoSeleccionado = cto;
							break;
						}
					}
					
					if (contratoSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnVerServiciosPlan.setEnabled(true); 
						
						if (contratoSeleccionado.isActivo() == true) {
							btnCancelarContrato.setEnabled(true);
							btnReactivarContrato.setEnabled(false); // Apagamos reactivar si ya está activo
						} else {
							btnCancelarContrato.setEnabled(false); // Apagamos desactivar si ya está inactivo
							btnReactivarContrato.setEnabled(true);
						}
					}
				}
			}
		});

		model = new DefaultTableModel();
		// AGREGAMOS EL ID AL PRINCIPIO
		String[] headers = {"ID Contrato", "Cód. Cliente", "Nombre Cliente", "Plan", "Estado", "Vendedor", "Mensual. Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

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

		// --- PANEL INFERIOR: BOTONES ---
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
				if(contratoSeleccionado != null) {
					ListarServiciosXPlan visorServicios = new ListarServiciosXPlan(contratoSeleccionado.getPlanContrato().getServiciosPlan());
					visorServicios.setVisible(true);
				}
			}
		});
		
		// NUEVO BOTÓN REACTIVAR
		btnReactivarContrato = new JButton("Reactivar Contrato");
		btnReactivarContrato.setEnabled(false);
		btnReactivarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contratoSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null, 
							"¿Desea volver a activar el contrato " + contratoSeleccionado.getIdContrato() + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
					
					if (opcion == JOptionPane.YES_OPTION) {
						contratoSeleccionado.setActivo(true); // Lo activamos directo
						
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
		
		btnCancelarContrato = new JButton("Desactivar Contrato");
		btnCancelarContrato.setEnabled(false);
		btnCancelarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contratoSeleccionado != null) {
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

		JButton btnCerrar = new JButton("Cerrar");
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
			
			if (filtro.equals("Activos")) {
				if (c.isActivo() == false) {
					continue;
				}
			} else if (filtro.equals("Inactivos")) {
				if (c.isActivo() == true) {
					continue; 
				}
			}
			
			String estado = "";
			if (c.isActivo() == true) {
				estado = "Activo";
			} else {
				estado = "Inactivo";
			}
			
			float porcentajeDecimal = c.getPorcentajeComisionAplicado() / 100.0f;
			float costoComision = c.getPrecioMensualAcordado() * porcentajeDecimal;
			float totalMensual = c.getPrecioMensualAcordado() + costoComision;
			
			// EL ARREGLO AHORA TIENE 7 ESPACIOS POR EL ID
			Object[] row = new Object[7];
			row[0] = c.getIdContrato();
			row[1] = c.getCliente().getCodigoCliente();
			row[2] = c.getCliente().getNombre();
			row[3] = c.getPlanContrato().getNombrePlan();
			row[4] = estado;
			row[5] = c.getEmpConsiguioContrato().getNombre();
			row[6] = "$ " + totalMensual;
			
			model.addRow(row);
		}
	}
	
	public Contrato getContratoSeleccionado() {
		return contratoSeleccionado;
	}
}