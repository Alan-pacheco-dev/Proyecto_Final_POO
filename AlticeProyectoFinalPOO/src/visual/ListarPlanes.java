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

import logico.EmpresaAltice;
import logico.Plan;
import logico.Servicio;

public class ListarPlanes extends JDialog {

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
		setBounds(100, 100, 750, 480);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15));

		JPanel panelFiltros = new JPanel();
		panelFiltros.setLayout(new BorderLayout(10, 0)); 
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Plan (ID, Nombre): ");
		panelFiltros.add(lblBuscar, BorderLayout.WEST);
		
		txtBuscar = new JTextField();
		panelFiltros.add(txtBuscar, BorderLayout.CENTER);

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
		String[] headers = {"ID Plan", "Nombre del Plan", "Cant. Servicios", "Precio Mensual ($)"};
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

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		btnVerServicios = new JButton("Ver Servicios del Plan");
		btnVerServicios.setEnabled(false);
		btnVerServicios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(planSeleccionado != null) {
					if(planSeleccionado.getServiciosPlan().isEmpty()) {
						JOptionPane.showMessageDialog(null, "Este plan aún no tiene servicios asignados.", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ListarServiciosXPlan visor = new ListarServiciosXPlan(planSeleccionado.getServiciosPlan());
						visor.setVisible(true);
					}
				}
			}
		});
		
		btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.setEnabled(false);
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		
		btnAgregarServicio = new JButton("Agregar Servicio");
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
		                    empresa.getMisClientes(),
		                    empresa.getMisEmpleados(),
		                    empresa.getMisPlanes(),
		                    empresa.getMisServicios(),
		                    empresa.getMisUsuarios(),
		                    empresa.getMisContratos(),
		                    empresa.getPagos()
		                );

		                JOptionPane.showMessageDialog(null,
		                    "Servicio agregado correctamente al plan.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

		                loadPlanes();
		            }
		        }
		    }
		});
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(planSeleccionado != null) {
					
					int confirmacion = JOptionPane.showConfirmDialog(null, 
							"¿Está seguro de que desea eliminar el plan: " + planSeleccionado.getNombrePlan() + "?", 
							"Confirmar Eliminación", 
							JOptionPane.YES_NO_OPTION, 
							JOptionPane.WARNING_MESSAGE);
					
					if (confirmacion == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						if (planSeleccionado.getServiciosPlan() != null) {
							for (Servicio s : planSeleccionado.getServiciosPlan()) {
								s.setEstaEnUso(false);
							}
						}
						
						empresa.getMisPlanes().remove(planSeleccionado);
						
						empresa.GuardarDatos(
								empresa.getMisClientes(), 
								empresa.getMisEmpleados(), 
								empresa.getMisPlanes(), 
								empresa.getMisServicios(), 
								empresa.getMisUsuarios(), 
								empresa.getMisContratos(), 
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

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				planSeleccionado = null; 
				dispose();
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
		
		buttonPane.add(btnCancelar);

		loadPlanes();
	}

	private void loadPlanes() {
		model.setRowCount(0); 
		
		for (Plan p : EmpresaAltice.getInstance().getMisPlanes()) {
			
			int cantidadServicios = 0;
			if (p.getServiciosPlan() != null) {
				cantidadServicios = p.getServiciosPlan().size();
			}
			
			Object[] row = new Object[4];
			row[0] = p.getIdPlan();
			row[1] = p.getNombrePlan();
			row[2] = cantidadServicios; 
			row[3] = p.getPrecioTotal(); 
			
			model.addRow(row);
		}
	}
	
	public Plan getPlanSeleccionado() {
		return planSeleccionado;
	}
}