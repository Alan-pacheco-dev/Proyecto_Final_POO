package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

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
import logico.Empleado;
import logico.EmpresaAltice;

public class ReporteRendimiento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Dimension dim;

	public static void main(String[] args) {
		try {
			ReporteRendimiento dialog = new ReporteRendimiento();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ReporteRendimiento() {
		setTitle("Reporte de Rendimiento y Nómina Mensual");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 950, 500); 
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// Obtener fecha actual para el título del borde
		LocalDate hoy = LocalDate.now();
		String nombreMes = hoy.getMonth().toString();
		int anio = hoy.getYear();

		JPanel panelTabla = new JPanel();
		panelTabla.setLayout(new BorderLayout(0, 0));
		panelTabla.setBorder(BorderFactory.createTitledBorder("Rendimiento de Ventas - Ciclo: " + nombreMes + " " + anio));
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
		
		String[] headers = {"ID Empleado", "Nombre Empleado", "Salario Base", "Ventas del Mes", "Monto del Mes", "Comisiones del Mes", "Pago Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

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

		cargarDatosRendimiento();
	}

	private void cargarDatosRendimiento() {
		model.setRowCount(0);
		EmpresaAltice empresa = EmpresaAltice.getInstance();

		// 1. Identificar el mes y año actual para el filtro
		LocalDate fechaActual = LocalDate.now();
		int mesActual = fechaActual.getMonthValue();
		int anioActual = fechaActual.getYear();

		for (Empleado emp : empresa.getMisEmpleados()) {
			
			int cantidadVentasMes = 0;
			float montoVendidoMes = 0;
			float comisionesMes = 0;

			for (Contrato c : empresa.getMisContratos()) {
				
				if (c.getEmpConsiguioContrato() != null) {
					if (c.getEmpConsiguioContrato().getIdPersona().equals(emp.getIdPersona())) {
						
						// 2. Extraer fecha del contrato para comparar
						LocalDate fechaContrato = c.getFechaInicioContrato();
						
						// 3. Solo procesamos si el contrato empezó en el mes y año en curso
						if (fechaContrato.getMonthValue() == mesActual) {
							if (fechaContrato.getYear() == anioActual) {
								if (c.isActivo() == true) {
									cantidadVentasMes++;
									montoVendidoMes = montoVendidoMes + c.getPrecioMensualAcordado();
									comisionesMes = comisionesMes + c.calcularComisionEmpleado();
								}
							}
						}
					}
				}
			}

			// El pago total es su sueldo base fijo + lo que ganó en comisiones este mes
			float totalAPagar = emp.getSalario() + comisionesMes;

			Object[] row = new Object[7];
			row[0] = emp.getIdPersona();
			row[1] = emp.getNombre();
			row[2] = "$ " + String.format("%.2f", emp.getSalario());
			row[3] = cantidadVentasMes;
			row[4] = "$ " + String.format("%.2f", montoVendidoMes);
			row[5] = "$ " + String.format("%.2f", comisionesMes);
			row[6] = "$ " + String.format("%.2f", totalAPagar);

			model.addRow(row);
		}
	}
}