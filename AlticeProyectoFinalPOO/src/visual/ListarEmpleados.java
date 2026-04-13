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

import logico.Empleado;
import logico.EmpresaAltice;

public class ListarEmpleados extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private JButton btnEliminar;
	private JButton btnSeleccionar;
	private JButton btnActualizar; 
	
	private Empleado selected = null;

	public static void main(String[] args) {
		try {
			ListarEmpleados dialog = new ListarEmpleados(false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarEmpleados(boolean modoSeleccion) {
		setTitle("Listado de Empleados");
		setModal(modoSeleccion);
		setResizable(false);
		setBounds(100, 100, 850, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Empleado (Nombre, Código, ID, Rol): ");
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
					
					String idEmpleado = (String) model.getValueAt(indexReal, 0);
					
					selected = null;
					for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
						if (emp.getIdPersona().equalsIgnoreCase(idEmpleado)) {
							selected = emp;
							break;
						}
					}
					
					if(selected != null) {
						btnEliminar.setEnabled(true);
						btnSeleccionar.setEnabled(true);
						btnActualizar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
		String[] headers = {"ID", "Código", "Nombre", "Rol", "Salario", "Cantidad Ventas", "Monto Ventas"};
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

		// --- PANEL DE BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Cierra y guarda el selected
			}
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(selected != null) {
					int option = JOptionPane.showConfirmDialog(null, 
							"¿Desea realmente eliminar al empleado: " + selected.getNombre() + "?", 
							"Confirmación",
							JOptionPane.WARNING_MESSAGE);
					
					if(option == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						boolean eliminado = empresa.eliminarEmpleado(selected);
						
						if(eliminado) {
							JOptionPane.showMessageDialog(null, "El empleado ha sido eliminado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
							empresa.GuardarDatos(
									empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(), 
									empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(), 
									empresa.getPagos()
							);
						} else {
							JOptionPane.showMessageDialog(null, "No se pudo eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						loadEmpleados();
						btnEliminar.setEnabled(false);
						btnActualizar.setEnabled(false);
						btnSeleccionar.setEnabled(false);
						selected = null;
					}
				}
			}
		});
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.setEnabled(false);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (selected != null) {
					
					RegistrarEmpleado actualizarEmp = new RegistrarEmpleado(selected);
					actualizarEmp.setModal(true);
					actualizarEmp.setVisible(true);
					
					loadEmpleados();
					btnActualizar.setEnabled(false);
					btnEliminar.setEnabled(false);
					btnSeleccionar.setEnabled(false);
					
					selected = null;
				}
			}
		});
		
		if (modoSeleccion == true) {
			buttonPane.add(btnSeleccionar);
			btnEliminar.setVisible(false);
			btnActualizar.setVisible(false);
		} else {
			buttonPane.add(btnEliminar);
			buttonPane.add(btnActualizar);
			btnSeleccionar.setVisible(false);
		}

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selected = null;
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		loadEmpleados();
	}

	private void loadEmpleados() {
		model.setRowCount(0);
		
		for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
			Object[] row = new Object[7];
			row[0] = emp.getIdPersona(); 
			row[1] = emp.getCodigoEmpleado();
			row[2] = emp.getNombre();    
			row[3] = emp.getRolEmpleado();
			row[4] = emp.getSalario();
			row[5] = emp.getCantidadVentas();
			row[6] = emp.getMontoVentas(); 
			model.addRow(row);
		}
	}
	
	public Empleado getEmpleadoSeleccionado() {
		return selected;
	}
}