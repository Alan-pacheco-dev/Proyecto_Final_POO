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
	private Empleado selected = null;

	public static void main(String[] args) {
		try {
			ListarEmpleados dialog = new ListarEmpleados();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarEmpleados() {
		setTitle("Listado de Empleados");
		setResizable(false);
		setBounds(100, 100, 850, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		// --- PANEL DE BÚSQUEDA ---
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Empleado (Nombre, Código, ID, Rol): ");
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		// --- PANEL DE LA TABLA ---
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
					// 1. Convertir índice visual al real por el filtro
					int indexReal = table.convertRowIndexToModel(indexVisual);
					
					// 2. Extraer el ID de la primera columna
					String idEmpleado = (String) model.getValueAt(indexReal, 0);
					
					// 3. Buscar al empleado en la lógica
					// Asumo que tienes un método buscarEmpleadoById, si no, lo puedes crear igual que el de clientes
					selected = EmpresaAltice.getInstance().buscarEmpleadoById(idEmpleado);
					
					if(selected != null) {
						btnEliminar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
		// Configuración de las columnas basadas en tu clase Empleado
		String[] headers = {"ID", "Código", "Nombre", "Rol", "Salario", "Ventas"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		// --- CONFIGURACIÓN DEL SORTER ---
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

		// --- PANEL DE BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
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
						
						// Si tienes un método empresa.eliminarEmpleado(selected) que valide cosas, úsalo aquí.
						// De lo contrario, lo removemos directo de la lista:
						boolean eliminado = empresa.getMisEmpleados().remove(selected);
						
						if(eliminado) {
							JOptionPane.showMessageDialog(null, "El empleado ha sido eliminado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
							
							// ¡AQUÍ ESTÁ LA MAGIA! Guardamos los datos para sobreescribir el archivo viejo.
							// NUNCA usamos CargarDatos() aquí.
							empresa.GuardarDatos(
									empresa.getMisClientes(), 
									empresa.getMisEmpleados(), 
									empresa.getMisPlanes(), 
									empresa.getMisServicios(), 
									empresa.getMisUsuarios(), 
									empresa.getMisContratos(), 
									empresa.getPagos() // Asegúrate que así se llame el getter de tus pagos
							);
							
						} else {
							JOptionPane.showMessageDialog(null, "No se pudo eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
						}
						
						// Refrescamos la tabla y bloqueamos el botón
						loadEmpleados();
						btnEliminar.setEnabled(false);
						selected = null;
					}
				}
			}
		});
		buttonPane.add(btnEliminar);

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		// Cargar datos al iniciar
		loadEmpleados();
	}

	private void loadEmpleados() {
		// Importante: Limpiamos la tabla para que no se dupliquen los registros
		model.setRowCount(0);
		
		for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
			Object[] row = new Object[6];
			row[0] = emp.getIdPersona(); // Viene heredado de la clase Persona
			row[1] = emp.getCodigoEmpleado();
			row[2] = emp.getNombre();    // Viene heredado de la clase Persona
			row[3] = emp.getRolEmpleado();
			row[4] = emp.getSalario();
			row[5] = emp.getVentas();
			model.addRow(row);
		}
	}
}