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

import logico.EmpresaAltice;
import logico.Servicio; 
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class ListarServicios extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private JComboBox<String> cbxFiltroTipo;
	private TableRowSorter<DefaultTableModel> sorter;
	
	private JButton btnSeleccionar;
	private JButton btnEliminar;
	
	private Servicio servicioSeleccionado = null;
	
	// NUEVO: Guardamos el modo para usarlo en el loadServicios
	private boolean modoSeleccionGlobal;

	public static void main(String[] args) {
		try {
			ListarServicios dialog = new ListarServicios("Todos", false);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarServicios(String tipoInicial, boolean modoSeleccion) {
		
		this.modoSeleccionGlobal = modoSeleccion;
		
		setTitle("Catálogo de Servicios Disponibles");
		setModal(modoSeleccion); 
		setResizable(false);
		setBounds(100, 100, 800, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15));

		JPanel panelFiltros = new JPanel();
		panelFiltros.setLayout(new BorderLayout(20, 0)); 
		contentPanel.add(panelFiltros, BorderLayout.NORTH);

		JPanel panelBuscadorTexto = new JPanel(new BorderLayout(5, 0));
		panelBuscadorTexto.add(new JLabel("Buscar (ID, Precio): "), BorderLayout.WEST);
		txtBuscar = new JTextField();
		panelBuscadorTexto.add(txtBuscar, BorderLayout.CENTER);
		panelFiltros.add(panelBuscadorTexto, BorderLayout.CENTER);

		JPanel panelComboBox = new JPanel(new BorderLayout(5, 0));
		panelComboBox.add(new JLabel("Tipo: "), BorderLayout.WEST);
		cbxFiltroTipo = new JComboBox<String>();
		cbxFiltroTipo.addItem("Todos");
		cbxFiltroTipo.addItem("Móvil");
		cbxFiltroTipo.addItem("Internet");
		cbxFiltroTipo.addItem("Telefonía");
		cbxFiltroTipo.addItem("Televisión");
		
		cbxFiltroTipo.setPreferredSize(new Dimension(180, 25));
		
		if(tipoInicial != null) {
			cbxFiltroTipo.setSelectedItem(tipoInicial);
		}
		
		panelComboBox.add(cbxFiltroTipo, BorderLayout.CENTER);
		panelFiltros.add(panelComboBox, BorderLayout.EAST);

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
					String idServicio = (String) model.getValueAt(indexReal, 0);
					
					servicioSeleccionado = null;
					for(Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
						if(s.getIdServicio().equalsIgnoreCase(idServicio)) {
							servicioSeleccionado = s;
							break;
						}
					}
					
					if (servicioSeleccionado != null) {
						btnSeleccionar.setEnabled(true);
						btnEliminar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel();
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
		
		cbxFiltroTipo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoElegido = (String) cbxFiltroTipo.getSelectedItem();
				loadServicios(tipoElegido);
				btnSeleccionar.setEnabled(false);
				btnEliminar.setEnabled(false);
				servicioSeleccionado = null;
			}
		});

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
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(servicioSeleccionado != null) {
					int opcion = JOptionPane.showConfirmDialog(null, 
							"¿Desea eliminar el servicio " + servicioSeleccionado.getIdServicio() + "?", 
							"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (opcion == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						empresa.getMisServicios().remove(servicioSeleccionado);
						
						empresa.GuardarDatos(
								empresa.getMisClientes(), 
								empresa.getMisEmpleados(), 
								empresa.getMisPlanes(), 
								empresa.getMisServicios(), 
								empresa.getMisUsuarios(), 
								empresa.getMisContratos(), 
								empresa.getPagos()
						);
						
						JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
						
						loadServicios((String) cbxFiltroTipo.getSelectedItem());
						btnEliminar.setEnabled(false);
						servicioSeleccionado = null;
					}
				}
			}
		});

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				servicioSeleccionado = null; 
				dispose();
			}
		});
		
		if(modoSeleccionGlobal == true) {
			buttonPane.add(btnSeleccionar);
			btnEliminar.setVisible(false);
		} else {
			buttonPane.add(btnEliminar);
			btnSeleccionar.setVisible(false);
		}
		
		buttonPane.add(btnCancelar);

		loadServicios((String) cbxFiltroTipo.getSelectedItem());
	}

	private void loadServicios(String tipoFiltro) {
		model.setRowCount(0);
		
		if (tipoFiltro.equals("Internet")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Velocidad (Mbps)", "Router", "En Uso"});
		} else if (tipoFiltro.equals("Móvil")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Datos (GB)", "Minutos", "SMS", "En Uso"});
		} else if (tipoFiltro.equals("Telefonía")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Min. Incluidos", "Ilimitado", "Costo/Min Extra", "En Uso"});
		} else if (tipoFiltro.equals("Televisión")) {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Precio ($)", "Canales", "Cajitas", "HD", "En Uso"});
		} else {
			model.setColumnIdentifiers(new String[]{"ID Servicio", "Tipo", "Precio Mensual ($)", "En Uso"});
		}

		for (Servicio s : EmpresaAltice.getInstance().getMisServicios()) {
			if (modoSeleccionGlobal == true) {
				if (s.isEstaEnUso() == true) {
					continue; 
				}
			}

			String textoEnUso = "";
			if (s.isEstaEnUso() == true) {
				textoEnUso = "Sí";
			} else {
				textoEnUso = "No";
			}

			if (tipoFiltro.equals("Todos")) {
				Object[] row = new Object[4];
				row[0] = s.getIdServicio(); 
				row[1] = s.getTipoServicio(); 
				row[2] = s.getPrecioServicio();
				row[3] = textoEnUso;
				model.addRow(row);
			} 
			else if (tipoFiltro.equals("Internet") && s instanceof ServicioInternet) {
				ServicioInternet net = (ServicioInternet) s;
				
				String stringRouter = "";
				if (net.isTieneRouter() == true) {
					stringRouter = "Sí";
				} else {
					stringRouter = "No";
				}
				
				Object[] row = new Object[5];
				row[0] = net.getIdServicio();
				row[1] = net.getPrecioServicio();
				row[2] = net.getVelocidadMbps();
				row[3] = stringRouter;
				row[4] = textoEnUso;
				model.addRow(row);
			} 
			else if (tipoFiltro.equals("Móvil") && s instanceof ServicioMovil) {
				ServicioMovil movil = (ServicioMovil) s;
				
				Object[] row = new Object[6];
				row[0] = movil.getIdServicio();
				row[1] = movil.getPrecioServicio();
				row[2] = movil.getDatosGb();
				row[3] = movil.getMinutos();
				row[4] = movil.getSms();
				row[5] = textoEnUso;
				model.addRow(row);
			} 
			else if (tipoFiltro.equals("Telefonía") && s instanceof ServicioTelefonia) {
				ServicioTelefonia tel = (ServicioTelefonia) s;
				
				String stringIlimitado = "";
				if (tel.isLlamadasIlimitadas() == true) {
					stringIlimitado = "Sí";
				} else {
					stringIlimitado = "No";
				}
				
				Object[] row = new Object[6];
				row[0] = tel.getIdServicio();
				row[1] = tel.getPrecioServicio();
				row[2] = tel.getMinutosIncluidos();
				row[3] = stringIlimitado;
				row[4] = tel.getCostoMinutoExtra();
				row[5] = textoEnUso;
				model.addRow(row);
			}
			else if (tipoFiltro.equals("Televisión") && s instanceof ServicioTelevision) {
				ServicioTelevision tv = (ServicioTelevision) s;
				
				String stringHD = "";
				if (tv.isTieneHD() == true) {
					stringHD = "Sí";
				} else {
					stringHD = "No";
				}
				
				Object[] row = new Object[6];
				row[0] = tv.getIdServicio();
				row[1] = tv.getPrecioServicio();
				row[2] = tv.getCantidadCanales();
				row[3] = tv.getCajitasIncluidas();
				row[4] = stringHD;
				row[5] = textoEnUso;
				model.addRow(row);
			}
		}
	}
	
	public Servicio getServicioSeleccionado() {
		return servicioSeleccionado;
	}
}