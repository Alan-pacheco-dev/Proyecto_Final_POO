package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
import logico.Contrato;

public class RegistrarPago extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtBuscar;
	private JTable table;
	private DefaultTableModel model;
	private JButton btnProcesarPago;
	
	// El componente estrella para la búsqueda en tiempo real
	private TableRowSorter<DefaultTableModel> sorter;

	public static void main(String[] args) {
		try {
			RegistrarPago dialog = new RegistrarPago();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarPago() {
		setTitle("Buscador de Cuentas por Cobrar");
		setResizable(false);
		setBounds(100, 100, 950, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 15)); // Espaciado vertical entre paneles

		// --- PANEL SUPERIOR: BARRA DE BÚSQUEDA ---
		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setLayout(null);
		// Le damos un tamaño fijo de altura al panel superior
		panelBusqueda.setPreferredSize(new java.awt.Dimension(950, 70));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Contrato (Nombre, Cédula o ID):");
		lblBuscar.setBounds(20, 23, 280, 25);
		panelBusqueda.add(lblBuscar);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(300, 20, 580, 30);
		panelBusqueda.add(txtBuscar);

		// --- PANEL CENTRAL: TABLA DE CONTRATOS ---
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		model = new DefaultTableModel();
		String[] headers = {"ID Contrato", "Cédula", "Cliente", "Plan", "Precio Acordado"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		// --- MAGIA DEL BUSCADOR: Conectar el Sorter ---
		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String textoBusqueda = txtBuscar.getText();
				if (textoBusqueda.trim().length() == 0) {
					sorter.setRowFilter(null); // Si está vacío, muestra todo
				} else {
					// El "(?i)" ignora mayúsculas y minúsculas
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
				}
			}
		});

		// Habilitar el botón solo si hay una fila seleccionada
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				btnProcesarPago.setEnabled(true);
			} else {
				btnProcesarPago.setEnabled(false);
			}
		});

		// --- PANEL INFERIOR: BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnProcesarPago = new JButton("Procesar Pago");
		btnProcesarPago.setEnabled(false); // Deshabilitado por defecto
		btnProcesarPago.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int indexVisual = table.getSelectedRow();
				if (indexVisual != -1) {
					// 1. Convertimos el índice visual al real por si la tabla está filtrada
					int indexReal = table.convertRowIndexToModel(indexVisual);
					
					// 2. Extraemos el ID del contrato de la primera columna (columna 0)
					String idContrato = (String) model.getValueAt(indexReal, 0);
					
					// 3. Buscamos el contrato en la lógica de negocio
					Contrato seleccionado = EmpresaAltice.getInstance().buscarContratoById(idContrato);
					
					if (seleccionado != null) {
					//  4. Abrimos la ventana que me enviaste por imagen, pasándole el contrato
					    PagosPorContrato ventanaPago = new PagosPorContrato(seleccionado);
					    ventanaPago.setModal(true);
					    ventanaPago.setVisible(true);
					    
					//  5. Al cerrar la ventana de pago, actualizamos la tabla por si acaso
					    loadContratos(); 
					}
					
					// *MOCKUP TEMPORAL PARA QUE VEAS QUE FUNCIONA*
					JOptionPane.showMessageDialog(null, "Abriendo ventana de pago para el contrato: " + idContrato);
				}
			}
		});
		buttonPane.add(btnProcesarPago);
		getRootPane().setDefaultButton(btnProcesarPago);

		JButton btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		// Cargamos los datos en la tabla
		loadContratos();
	}

	// Método para llenar la tabla
	private void loadContratos() {
		model.setRowCount(0);
		
		// AQUÍ VA TU LÓGICA REAL:
		for (Contrato c : EmpresaAltice.getInstance().getMisContratos()) {
		     if (c.isActivo()) {
		         Object[] row = new Object[5];
		         row[0] = c.getIdContrato();
		         row[1] = c.getCliente().getRnc();
		         row[2] = c.getCliente().getNombre();
		         row[3] = c.getPlanContrato().getNombrePlan();
		         row[4] = "$" + c.getPrecioMensualAcordado();
		         model.addRow(row);
		     }
		 }
		
		// DATOS DE PRUEBA 
		model.addRow(new Object[]{"CON-001", "402-1234567-8", "Juan Pérez", "Plan Básico", "$100.0"});
		model.addRow(new Object[]{"CON-002", "031-9876543-2", "María Gómez", "Plan Premium", "$250.0"});
		model.addRow(new Object[]{"CON-003", "402-1112223-3", "Carlos López", "Plan Básico", "$100.0"});
		model.addRow(new Object[]{"CON-004", "031-4445556-6", "Ana Martínez", "Plan Corporativo", "$500.0"});
	}
}