package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;

import logico.Cliente;
import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;

public class ReporteGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;

	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color alticeLight = new Color(245, 248, 255);
	private Color alticeBorder = new Color(208, 223, 247);
	private Color textColor = new Color(50, 50, 50);

	public static void main(String[] args) {
		try {
			ReporteGeneral dialog = new ReporteGeneral();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ReporteGeneral() {
		setTitle("Reporte General - Estado de la Empresa");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 1000, 700); 
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);

		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new GridLayout(4, 2, 20, 20));

		// ==========================================
		// 1. LÓGICA DE CÁLCULO DE DATOS (KPIs)
		// ==========================================
		EmpresaAltice empresa = EmpresaAltice.getInstance();

		int totalClientesActivos = 0;
		for(Cliente c: empresa.getMisClientes()) {
			if(c.getTotalServiciosActivos() > 0) {
				totalClientesActivos++;
			}
		}

		float deudaTotal = 0;
		for (Cliente cli : empresa.getMisClientes()) {
			deudaTotal += cli.getDeuda();
		}

		int contratosActivos = 0;
		int contratosInactivos = 0;
		float ingresoBrutoMensual = 0;
		float comisionesMensuales = 0;

		for (Contrato c : empresa.getMisContratos()) {
			if (c.isActivo() == true) {
				contratosActivos++;
				ingresoBrutoMensual += c.getPrecioMensualAcordado();
				comisionesMensuales += c.calcularComisionEmpleado();
			} else {
				contratosInactivos++;
			}
		}

		float nominaBase = 0;
		for (Empleado emp : empresa.getMisEmpleados()) {
			nominaBase += emp.getSalario();
		}

		float ingresoNetoSinImpuestos = ingresoBrutoMensual / 1.18f;
		float impuestosTotales = ingresoBrutoMensual - ingresoNetoSinImpuestos;

		float totalEgresos = nominaBase + comisionesMensuales;
		float ingresoNeto = ingresoBrutoMensual - impuestosTotales - totalEgresos;

		// ==========================================
		// 2. CREACIÓN DE LAS TARJETAS VISUALES (ESTILO ALTICE)
		// ==========================================
		
		// Fila 1
		contentPanel.add(crearTarjeta("Total de Clientes Activos", String.valueOf(totalClientesActivos)));
		String textoContratos = "Activos: " + contratosActivos + "  |  Inactivos: " + contratosInactivos;
		contentPanel.add(crearTarjeta("Estado de Contratos", textoContratos));

		// Fila 2
		contentPanel.add(crearTarjeta("Deuda Total a Cobrar", "$ " + String.format("%.2f", deudaTotal)));
		contentPanel.add(crearTarjeta("Ingreso Bruto Mensual Estimado", "$ " + String.format("%.2f", ingresoBrutoMensual)));

		// Fila 3: Egresos Fijos en la izquierda, Impuestos a la derecha
		contentPanel.add(crearTarjeta("Egresos Fijos (Nómina Base)", "$ " + String.format("%.2f", nominaBase)));
		contentPanel.add(crearTarjeta("Impuestos a Pagar (ITBIS 18%)", "$ " + String.format("%.2f", impuestosTotales)));

		// Fila 4: Egresos Variables en la izquierda, Ganancia Neta a la derecha (Resultado Final)
		contentPanel.add(crearTarjeta("Egresos Variables (Comisiones)", "$ " + String.format("%.2f", comisionesMensuales)));
		contentPanel.add(crearTarjeta("Ganancia Neta Estimada", "$ " + String.format("%.2f", ingresoNeto)));

		// ==========================================
		// 3. PANEL DE BOTONES (INFERIOR)
		// ==========================================
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 20, 20, 20));
		buttonPane.setLayout(new BorderLayout());
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnGrafico = new JButton("Ver Gráfico Financiero");
		btnGrafico.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnGrafico.setBackground(alticeBlue);
		btnGrafico.setForeground(Color.WHITE);
		btnGrafico.setFocusPainted(false);
		btnGrafico.setBorderPainted(false);
		btnGrafico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGrafico.setPreferredSize(new Dimension(200, 40));

		final float finalIngresoBruto = ingresoBrutoMensual;
		final float finalImpuestos = impuestosTotales;
		final float finalEgresos = totalEgresos;
		final float finalIngresoNeto = ingresoNeto;

		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarGraficoBalance(finalIngresoBruto, finalImpuestos, finalEgresos, finalIngresoNeto);
			}
		});

		JPanel pnlIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlIzquierda.setBackground(bgWhite);
		pnlIzquierda.add(btnGrafico);
		buttonPane.add(pnlIzquierda, BorderLayout.WEST);

		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCerrar.setBackground(bgWhite);
		btnCerrar.setForeground(alticeBlue);
		btnCerrar.setBorder(new LineBorder(alticeBlue, 2, true));
		btnCerrar.setFocusPainted(false);
		btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCerrar.setPreferredSize(new Dimension(120, 40));

		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		JPanel pnlDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlDerecha.setBackground(bgWhite);
		pnlDerecha.add(btnCerrar);
		buttonPane.add(pnlDerecha, BorderLayout.EAST);
	}

	private JPanel crearTarjeta(String titulo, String valor) {
		JPanel tarjeta = new JPanel();
		tarjeta.setLayout(new BorderLayout());
		tarjeta.setBackground(alticeLight); 
		tarjeta.setBorder(BorderFactory.createCompoundBorder(
				new LineBorder(alticeBorder, 2, true), 
				new EmptyBorder(10, 10, 10, 10)
				));

		JLabel lblTitulo = new JLabel(titulo);
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
		lblTitulo.setForeground(alticeBlue);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		tarjeta.add(lblTitulo, BorderLayout.NORTH);

		JLabel lblValor = new JLabel(valor);
		lblValor.setFont(new Font("SansSerif", Font.PLAIN, 20));
		lblValor.setForeground(textColor);
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		tarjeta.add(lblValor, BorderLayout.CENTER);

		return tarjeta;
	}

	private void mostrarGraficoBalance(float ingresos, float impuestos, float egresos, float neto) {

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(ingresos, "Finanzas", "Ingreso Bruto");
		dataset.addValue(impuestos, "Finanzas", "Impuestos (ITBIS)");
		dataset.addValue(egresos, "Finanzas", "Total Egresos");
		dataset.addValue(neto, "Finanzas", "Ganancia Neta");

		JFreeChart chart = ChartFactory.createBarChart(
				"Balance Financiero Mensual",
				"", 
				"Monto en $",
				dataset,
				PlotOrientation.VERTICAL,
				false, 
				true, 
				false 
				);

		chart.setBackgroundPaint(bgWhite);
		chart.getTitle().setPaint(textColor);
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
		chartPanel.setPreferredSize(new Dimension(700, 450));

		JDialog ventanaGrafico = new JDialog(this, "Gráfico Financiero", true);
		ventanaGrafico.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ventanaGrafico.getContentPane().setBackground(bgWhite);
		ventanaGrafico.getContentPane().add(chartPanel, BorderLayout.CENTER);
		ventanaGrafico.pack();
		ventanaGrafico.setLocationRelativeTo(this);
		ventanaGrafico.setVisible(true);
	}
}