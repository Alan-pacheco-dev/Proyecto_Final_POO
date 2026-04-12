package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Servicio;

public class ListarServiciosXPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	// Constructor que recibe directamente la lista temporal
	public ListarServiciosXPlan(ArrayList<Servicio> serviciosEscogidos) {
		setTitle("Servicios actualmente en el Plan");
		setModal(true); // Bloquea la ventana de atrás mientras esta esté abierta
		setResizable(false);
		setBounds(100, 100, 500, 350);
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
		table.setEnabled(false); // Desactiva los clics, es solo lectura
		
		model = new DefaultTableModel();
		String[] headers = {"ID Servicio", "Tipo", "Precio ($)"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		// --- PANEL INFERIOR: BOTÓN CERRAR ---
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose(); // Solo cierra esta pequeña ventana
			}
		});
		buttonPane.add(btnCerrar);

		// Llenamos la tabla al construir la ventana
		loadServicios(serviciosEscogidos);
	}

	private void loadServicios(ArrayList<Servicio> serviciosEscogidos) {
		model.setRowCount(0);
		
		for (Servicio s : serviciosEscogidos) {
			Object[] row = new Object[3];
			row[0] = s.getIdServicio(); 
			row[1] = s.getTipoServicio(); 
			row[2] = s.getPrecioServicio();
			model.addRow(row);
		}
	}
}