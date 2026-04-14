package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;

public class ReporteRendimiento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Dimension dim;
	
	private DefaultCategoryDataset datasetGrafico;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color alticeLight = new Color(245, 248, 255);
	private Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

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
		setBounds(100, 100, 1050, 550); 
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		LocalDate hoy = LocalDate.now();
		String nombreMes = hoy.getMonth().toString();
		int anio = hoy.getYear();

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(bgWhite);
		panelTabla.setLayout(new BorderLayout(0, 0));
		panelTabla.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY), 
				"Rendimiento de Ventas - Ciclo: " + nombreMes + " " + anio, 
				TitledBorder.LEADING, TitledBorder.TOP, fontLabel, alticeBlue));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		table.getTableHeader().setBackground(alticeBlue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(fontInput);
		table.setSelectionBackground(new Color(208, 223, 247));
		table.setSelectionForeground(Color.BLACK);
		
		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		String[] headers = {"ID Empleado", "Nombre", "Salario Base", "Ventas (Mes)", "Monto Vendido", "Comisiones", "Pago Total"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnGrafico = primaryButton("Ver Gráfico de Rendimiento");
		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarGraficoRendimiento();
			}
		});
		buttonPane.add(btnGrafico);
		
		JButton btnCerrar = outlineButton("Cerrar Reporte");
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
		datasetGrafico = new DefaultCategoryDataset();
		
		EmpresaAltice empresa = EmpresaAltice.getInstance();

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
						
						LocalDate fechaContrato = c.getFechaInicioContrato();
						
						if (fechaContrato.getMonthValue() == mesActual && fechaContrato.getYear() == anioActual) {
							if (c.isActivo() == true) {
								cantidadVentasMes++;
								montoVendidoMes += c.getPrecioMensualAcordado();
								comisionesMes += c.calcularComisionEmpleado();
							}
						}
					}
				}
			}

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
			
			// Solo agregamos al gráfico a los empleados que vendieron algo para no saturar la vista
			if (montoVendidoMes > 0) {
				datasetGrafico.addValue(montoVendidoMes, "Monto Vendido ($)", emp.getNombre());
			}
		}
	}
	
	private void mostrarGraficoRendimiento() {
		if (datasetGrafico.getColumnCount() == 0) {
			JOptionPane.showMessageDialog(this, "No hay ventas registradas en este mes para generar el gráfico.", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		JFreeChart chart = ChartFactory.createBarChart(
			"Top Rendimiento en Ventas del Mes",
			"Empleado",
			"Monto Vendido ($)",
			datasetGrafico,
			PlotOrientation.VERTICAL,
			false, 
			true, 
			false
		);

		chart.setBackgroundPaint(bgWhite);
		chart.getTitle().setPaint(Color.DARK_GRAY);
		chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
		
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(alticeLight);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setOutlineVisible(false);
		
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter()); 
		renderer.setSeriesPaint(0, alticeBlue);
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0.1); 

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 500));
		
		JDialog ventanaGrafico = new JDialog(this, "Gráfico de Rendimiento", true);
		ventanaGrafico.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ventanaGrafico.getContentPane().setBackground(bgWhite);
		ventanaGrafico.getContentPane().add(chartPanel, BorderLayout.CENTER);
		ventanaGrafico.pack();
		ventanaGrafico.setLocationRelativeTo(this);
		ventanaGrafico.setVisible(true);
	}

	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(alticeBlue);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(alticeBlue);
		btn.setBackground(bgWhite);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(alticeBlue, 2, true));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
}