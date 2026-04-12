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

import logico.Contrato;
import logico.EmpresaAltice;

public class ListarContratosGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private JButton btnSeleccionar;
	private JButton btnCancelarContrato; 
	private JButton btnVerServiciosPlan; // NUEVO BOTÓN
	
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

		// --- PANEL SUPERIOR: BÚSQUEDA ---
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Contrato (Cliente, Plan, Empleado): ");
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

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
					
					String idClienteStr = (String) model.getValueAt(indexReal, 0);
					String nombrePlanStr = (String) model.getValueAt(indexReal, 2);
					
					contratoSeleccionado = null;
					
					for(Contrato cto : EmpresaAltice.getInstance().getMisContratos()) {
						if(cto.getCliente().getCodigoCliente().equals(idClienteStr) && 
						   cto.getPlanContrato().getNombrePlan().equals(nombrePlanStr)) {
							contratoSeleccionado = cto;
							break;
						}
					}
					
					if (contratoSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnVerServiciosPlan.setEnabled(true); // Encendemos el nuevo botón
						
						if (contratoSeleccionado.isActivo() == true) {
							btnCancelarContrato.setEnabled(true);
						} else {
							btnCancelarContrato.setEnabled(false);
						}
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"Cód. Cliente", "Nombre Cliente", "Plan", "Estado", "Vendedor", "Mensualidad Total"};
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
		
		// CONFIGURACIÓN DEL NUEVO BOTÓN
		btnVerServiciosPlan = new JButton("Ver Servicios del Plan");
		btnVerServiciosPlan.setEnabled(false);
		btnVerServiciosPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contratoSeleccionado != null) {
					// Abrimos el visor reciclando la clase que ya existe, pasándole la lista del plan
					ListarServiciosXPlan visorServicios = new ListarServiciosXPlan(contratoSeleccionado.getPlanContrato().getServiciosPlan());
					visorServicios.setVisible(true);
				}
			}
		});
		
		btnCancelarContrato = new JButton("Desactivar Contrato");
		btnCancelarContrato.setEnabled(false);
		btnCancelarContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(contratoSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null, 
							"¿Desea desactivar el contrato?", "Confirmar", JOptionPane.YES_NO_OPTION);
					
					if (opcion == JOptionPane.YES_OPTION) {
						// 1. Cambiamos el estado lógico
						contratoSeleccionado.setActivo(false);
						
						// 2. RE-CONTAMOS para que el cliente pierda ese contrato activo en su contador
						EmpresaAltice.getInstance().refrescarConteosContratos();
						
						// 3. Guardamos los archivos
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(), 
							empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(), 
							empresa.getMisContratos(), empresa.getPagos());
						
						// 4. ¡CRÍTICO! Volver a cargar la tabla para que el botón se apague y el texto cambie
						loadContratos(); 
						btnCancelarContrato.setEnabled(false);
						btnVerServiciosPlan.setEnabled(false); // Apagamos por seguridad
						contratoSeleccionado = null;
						
						JOptionPane.showMessageDialog(null, "Contrato desactivado correctamente.");
					}
				}
			}
		});
		
		// LÓGICA DE DISTRIBUCIÓN DE BOTONES (MODO DUAL)
		if (modoSeleccion == true) {
			buttonPane.add(btnSeleccionar);
			btnCancelarContrato.setVisible(false);
			btnVerServiciosPlan.setVisible(false); // Si es selector, escondemos las funciones de admin
		} else {
			buttonPane.add(btnVerServiciosPlan); // Lo mostramos antes de desactivar
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

		loadContratos();
	}

	private void loadContratos() {
		model.setRowCount(0);
		for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
			String estado = "";
			if (c.isActivo() == true) {
				estado = "Activo";
			} else {
				estado = "Inactivo";
			}
			
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