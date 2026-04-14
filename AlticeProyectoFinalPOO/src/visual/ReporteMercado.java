package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import logico.Contrato;
import logico.EmpresaAltice;
import logico.Plan;

public class ReporteMercado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private Dimension dim;
	
	private DefaultPieDataset datasetGrafico;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color alticeLight = new Color(245, 248, 255);
	private Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

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
		setBounds(100, 100, 1050, 550);
		dim = getToolkit().getScreenSize();
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
		panelTabla.setBorder(BorderFactory.createTitledBorder(
				new LineBorder(Color.LIGHT_GRAY), 
				"Distribución de Clientes por Plan", 
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
		
		String[] headers = {"ID Plan", "Nombre del Plan", "Servicios Incluidos", "Precio Unitario", "Usuarios Activos", "Ingreso Mensual Acumulado"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 0));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnGrafico = primaryButton("Ver Gráfico de Mercado");
		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarGraficoMercado();
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

		// Ejecutamos la lógica de carga
		cargarDatosMercado();
	}

	private void cargarDatosMercado() {
		model.setRowCount(0);
		datasetGrafico = new DefaultPieDataset();
		
		EmpresaAltice empresa = EmpresaAltice.getInstance();

		for (Plan p : empresa.getMisPlanes()) {
			
			int usuariosActivos = 0;
			float ingresosDelPlan = 0;

			for (Contrato c : empresa.getMisContratos()) {
				if (c.getPlanContrato().getIdPlan().equals(p.getIdPlan())) {
					if (c.isActivo() == true) {
						usuariosActivos++;
						ingresosDelPlan = ingresosDelPlan + c.getPrecioMensualAcordado();
					}
				}
			}

			int cantServicios = 0;
			if (p.getServiciosPlan() != null) {
				cantServicios = p.getServiciosPlan().size();
			}

			Object[] row = new Object[6];
			row[0] = p.getIdPlan();
			row[1] = p.getNombrePlan();
			row[2] = cantServicios + " servicios";
			row[3] = "$ " + String.format("%.2f", p.getPrecioTotal());
			row[4] = usuariosActivos + " clientes";
			row[5] = "$ " + String.format("%.2f", ingresosDelPlan);

			model.addRow(row);
			
			// Si hay usuarios en este plan, lo agregamos al gráfico de pastel
			if (usuariosActivos > 0) {
				datasetGrafico.setValue(p.getNombrePlan() + " (" + usuariosActivos + ")", usuariosActivos);
			}
		}
	}
	
	private void mostrarGraficoMercado() {
		if (datasetGrafico.getItemCount() == 0) {
			JOptionPane.showMessageDialog(this, "No hay clientes activos en ningún plan para generar el gráfico.", "Sin datos", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		// Usamos un gráfico de pastel para la cuota de mercado
		JFreeChart chart = ChartFactory.createPieChart(
			"Distribución de Clientes Activos por Plan",
			datasetGrafico,
			true,  // legend
			true,  // tooltips
			false  // urls
		);

		chart.setBackgroundPaint(bgWhite);
		chart.getTitle().setPaint(Color.DARK_GRAY);
		chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setBackgroundPaint(bgWhite);
		plot.setOutlineVisible(false);
		plot.setLabelBackgroundPaint(alticeLight);
		plot.setLabelShadowPaint(null);
		plot.setLabelOutlinePaint(bgWhite);
		plot.setShadowPaint(null);

		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 500));
		
		JDialog ventanaGrafico = new JDialog(this, "Gráfico de Mercado", true);
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