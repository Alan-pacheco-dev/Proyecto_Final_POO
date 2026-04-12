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
	
	/*
	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private JButton btnEliminar;
	private JButton btnCrearContrato;
	private JButton btnVerContratos;
	private JButton btnSeleccionar; // NUEVO BOTÓN
	
	private Cliente selected = null;

	public static void main(String[] args) {
		try {
			ListarClientes dialog = new ListarClientes(false); // Falso para pruebas normales
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// NUEVO: Agregamos el boolean modoSeleccion
	public ListarClientes(boolean modoSeleccion) {
		setTitle("Listado de Clientes");
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
				if(indexVisual != -1) {
					
					int indexReal = table.convertRowIndexToModel(indexVisual);
					String idCliente = (String) model.getValueAt(indexReal, 0);
					
					selected = EmpresaAltice.getInstance().buscarClienteById(idCliente);
					
					// Encendemos los botones
					btnEliminar.setEnabled(true);
					btnCrearContrato.setEnabled(true);
					btnSeleccionar.setEnabled(true); // Encendemos el botón de seleccionar
					
					if(selected.getCantContratosActivos() > 0) {
						btnVerContratos.setVisible(true);
					} else {
						btnVerContratos.setVisible(false);
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"ID", "Código", "Nombre", "Email", "Deuda", "Contratos activos"};
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
		
		// --- CONFIGURACIÓN DE BOTONES ---
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Solo cierra la ventana, guardando el "selected"
			}
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected != null) {
					int option = JOptionPane.showConfirmDialog(null, 
							"¿Desea realmente eliminar al cliente: " + selected.getNombre() + "?", 
							"Confirmación",
							JOptionPane.WARNING_MESSAGE);
					
					if(option == JOptionPane.YES_OPTION) {
						boolean eliminado = EmpresaAltice.getInstance().eliminarCliente(selected);
						
						if(eliminado == true) {
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
						empresa.actualizarContadores();
						
						loadClientes();
						btnEliminar.setEnabled(false);
						btnCrearContrato.setEnabled(false);
						btnSeleccionar.setEnabled(false);
						btnVerContratos.setVisible(false);
						selected = null;
					}
				}
			}
		});
		
		btnCrearContrato = new JButton("Crear Contrato");
		btnCrearContrato.setEnabled(false);
		btnCrearContrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				logico.Empleado empleadoFalso = new logico.Empleado(null, "Admin", 1000, 10, 0, 0, "Administrativo");
				
				RegistrarContrato regisCto = new RegistrarContrato(selected, empleadoFalso);
				regisCto.setModal(true);
				regisCto.setVisible(true); 
				
				// --- NUEVO: Refrescar los conteos lógicos globales primero ---
				EmpresaAltice.getInstance().refrescarConteosContratos();
				
				// --- Luego recargar la tabla visual ---
				loadClientes();
				
				btnEliminar.setEnabled(false);
				btnCrearContrato.setEnabled(false);
				btnVerContratos.setVisible(false);
				selected = null;
			}
		});
		
		btnVerContratos = new JButton("Ver Contratos");
		btnVerContratos.setVisible(false);
		btnVerContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarContratosXCliente listarCtosClientes = new ListarContratosXCliente(selected);
				listarCtosClientes.setModal(true);
				listarCtosClientes.setVisible(true);
			}
		});
		
		// --- LÓGICA MODO DUAL ---
		if (modoSeleccion == true) {
			// Si solo lo abrimos para buscar a un cliente, escondemos todo lo demás
			buttonPane.add(btnSeleccionar);
			btnVerContratos.setVisible(false);
			btnCrearContrato.setVisible(false);
			btnEliminar.setVisible(false);
		} else {
			// Modo normal (Menú -> Clientes -> Listar)
			buttonPane.add(btnVerContratos);
			buttonPane.add(btnCrearContrato);
			buttonPane.add(btnEliminar);
			btnSeleccionar.setVisible(false); // Ocultamos seleccionar
		}

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = null; // Limpiamos la selección si el usuario cancela
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		loadClientes();
	}

	private void loadClientes() {
		model.setRowCount(0);
		for (Cliente c : EmpresaAltice.getInstance().getMisClientes()) {
			Object[] row = new Object[6];
			row[0] = c.getIdPersona();
			row[1] = c.getCodigoCliente();
			row[2] = c.getNombre();
			row[3] = c.getEmail();
			row[4] = c.getDeuda();
			row[5] = c.getCantContratosActivos();
			model.addRow(row);
		}
	}
	
	// Getter para que Principal reciba al cliente
	public Cliente getClienteSeleccionado() {
		return selected;
	}
	*/
}