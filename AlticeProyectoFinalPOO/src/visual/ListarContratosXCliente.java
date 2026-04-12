package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cliente;
import logico.Contrato;

public class ListarContratosXCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	// Recibe directamente el cliente del que queremos ver el historial
	public ListarContratosXCliente(Cliente clienteActual) {
		
		// Personalizamos el título con el nombre del cliente
		String nombreTitulo = "";
		if (clienteActual != null) {
			nombreTitulo = clienteActual.getNombre();
		}
		setTitle("Historial de Contratos - " + nombreTitulo);
		
		setModal(true); 
		setResizable(false);
		setBounds(100, 100, 700, 400); // Un tamaño cómodo para leer
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// --- PANEL CENTRAL: TABLA ---
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setEnabled(false); // Es un visor histórico, solo lectura
		
		model = new DefaultTableModel();
		// Quitamos las columnas de cliente y agregamos Fecha
		String[] headers = {"Id Contrato", "Plan Adquirido", "Fecha de Inicio", "Estado", "Vendedor", "Mensualidad Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		// --- PANEL INFERIOR: BOTONES ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar Historial");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		buttonPane.add(btnCerrar);

		// Llenamos la tabla al abrir la ventana, validando que el cliente no sea nulo
		if (clienteActual != null) {
			loadContratosCliente(clienteActual);
		}
	}

	private void loadContratosCliente(Cliente cliente) {
		model.setRowCount(0);
		
		// Iteramos SOLAMENTE sobre la lista de contratos de este cliente específico
		for (Contrato c : cliente.getMisContratos()) {
			
			String estado = "";
			if (c.isActivo() == true) {
				estado = "Activo";
			} else {
				estado = "Inactivo";
			}
			
			// Cálculo del total mensual
			float porcentajeDecimal = c.getPorcentajeComisionAplicado() / 100.0f;
			float costoComision = c.getPrecioMensualAcordado() * porcentajeDecimal;
			float totalMensual = c.getPrecioMensualAcordado() + costoComision;
			
			// Manejo de la fecha por si acaso viene nula
			String fechaInicioStr = "Sin fecha";
			if (c.getFechaInicioContrato() != null) {
				fechaInicioStr = c.getFechaInicioContrato().toString();
			}
			
			Object[] row = new Object[6];
			row[0] = c.getIdContrato();
			row[1] = c.getPlanContrato().getNombrePlan();
			row[2] = fechaInicioStr;
			row[3] = estado;
			row[4] = c.getEmpConsiguioContrato().getNombre();
			row[5] = "$ " + totalMensual;
			
			model.addRow(row);
		}
	}
}