package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import logico.Contrato;
import logico.EmpresaAltice;
import logico.Plan;

public class ReporteMercado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Dimension dim;

	public static void main(String[] args) {
		try {
			ReporteMercado dialog = new ReporteMercado();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ReporteMercado() {
		setTitle("Reporte de Penetración de Mercado - Análisis de Planes");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 850, 500);
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// ==========================================
		// PANEL DE TABLA CON ESTILO ESTÁNDAR
		// ==========================================
		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout(0, 0));
		panelTabla.setBorder(BorderFactory.createTitledBorder("Distribución de Clientes por Plan"));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		String[] headers = {"ID Plan", "Nombre del Plan", "Servicios Incluidos", "Precio Unitario", "Usuarios Activos", "Ingreso Mensual Acumulado"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		// ==========================================
		// BOTÓN CERRAR
		// ==========================================
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		// Ejecutamos la lógica de carga
		cargarDatosMercado();
	}

	// ==========================================
	// LÓGICA DE CRUCE DE DATOS
	// ==========================================
	private void cargarDatosMercado() {
		model.setRowCount(0);
		EmpresaAltice empresa = EmpresaAltice.getInstance();

		// 1. Iteramos por cada plan que ofrece la empresa
		for (Plan p : empresa.getMisPlanes()) {
			
			int usuariosActivos = 0;
			float ingresosDelPlan = 0;

			// 2. Buscamos en todos los contratos quiénes tienen este plan
			for (Contrato c : empresa.getMisContratos()) {
				
				// Comparamos el ID del plan del contrato con el plan actual del bucle
				if (c.getPlanContrato().getIdPlan().equals(p.getIdPlan())) {
					
					// Solo contamos si el contrato está vigente
					if (c.isActivo() == true) {
						usuariosActivos++;
						ingresosDelPlan = ingresosDelPlan + c.getPrecioMensualAcordado();
					}
				}
			}

			// 3. Obtenemos la cantidad de servicios que tiene el plan para mostrarlo
			int cantServicios = 0;
			if (p.getServiciosPlan() != null) {
				cantServicios = p.getServiciosPlan().size();
			}

			// 4. Agregamos los datos calculados a la fila
			Object[] row = new Object[6];
			row[0] = p.getIdPlan();
			row[1] = p.getNombrePlan();
			row[2] = cantServicios + " servicios";
			row[3] = "$ " + String.format("%.2f", p.getPrecioTotal());
			row[4] = usuariosActivos + " clientes";
			row[5] = "$ " + String.format("%.2f", ingresosDelPlan);

			model.addRow(row);
		}
	}
}