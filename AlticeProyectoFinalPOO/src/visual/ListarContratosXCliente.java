package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logico.Cliente;
import logico.Contrato;

public class ListarContratosXCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

	public ListarContratosXCliente(Cliente clienteActual) {
		
		String nombreTitulo = "";
		if (clienteActual != null) {
			nombreTitulo = clienteActual.getNombre();
		}
		setTitle("Historial de Contratos - " + nombreTitulo);
		
		setModal(true); 
		setResizable(false);
		setBounds(100, 100, 800, 450);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(bgWhite);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setEnabled(false);
		
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		table.getTableHeader().setBackground(alticeBlue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(fontInput);
		
		model = new DefaultTableModel();
		String[] headers = {"Id Contrato", "Plan Adquirido", "Fecha de Inicio", "Estado", "Vendedor", "Mensualidad Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar Historial");
		btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCerrar.setForeground(alticeBlue);
		btnCerrar.setBackground(bgWhite);
		btnCerrar.setFocusPainted(false);
		btnCerrar.setBorder(new LineBorder(alticeBlue, 2, true));
		btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});
		buttonPane.add(btnCerrar);

		if (clienteActual != null) {
			loadContratosCliente(clienteActual);
		}
	}

	private void loadContratosCliente(Cliente cliente) {
		model.setRowCount(0);
		
		for (Contrato c : cliente.getMisContratos()) {
			
			String estado = "";
			if (c.isActivo() == true) {
				estado = "Activo";
			} else {
				estado = "Inactivo";
			}
			
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
			row[5] = "$ " + String.format("%.2f", c.getPrecioMensualAcordado()); 
			
			model.addRow(row);
		}
	}
}