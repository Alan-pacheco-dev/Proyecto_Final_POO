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

	// Paleta de colores Altice
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
		// Cambia esta línea para hacerla más grande (Ancho: 1000, Alto: 600)
		setBounds(100, 100, 1000, 600); 
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);

		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		contentPanel.setLayout(new GridLayout(3, 2, 20, 20)); // Mayor espaciado para que respire el diseño

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

		float totalEgresos = nominaBase + comisionesMensuales;
		float ingresoNeto = ingresoBrutoMensual - totalEgresos;

		// ==========================================
		// 2. CREACIÓN DE LAS TARJETAS VISUALES (ESTILO ALTICE)
		// ==========================================
		contentPanel.add(crearTarjeta("Total de Clientes activos", String.valueOf(totalClientesActivos)));

		String textoContratos = "Activos: " + contratosActivos + "  |  Inactivos: " + contratosInactivos;
		contentPanel.add(crearTarjeta("Estado de Contratos", textoContratos));

		contentPanel.add(crearTarjeta("Deuda Total a Cobrar", "$ " + String.format("%.2f", deudaTotal)));
		contentPanel.add(crearTarjeta("Ingreso Bruto Mensual Estimado", "$ " + String.format("%.2f", ingresoBrutoMensual)));
		contentPanel.add(crearTarjeta("Egresos (Nómina + Comisiones)", "$ " + String.format("%.2f", totalEgresos)));
		contentPanel.add(crearTarjeta("Ganancia Neta Estimada", "$ " + String.format("%.2f", ingresoNeto)));

		// ==========================================
		// 3. PANEL DE BOTONES (INFERIOR)
		// ==========================================
		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 20, 20, 20));
		buttonPane.setLayout(new BorderLayout());
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		// Botón de Gráfico (A la izquierda)
		JButton btnGrafico = new JButton("Ver Gráfico Financiero");
		btnGrafico.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnGrafico.setBackground(alticeBlue);
		btnGrafico.setForeground(Color.WHITE);
		btnGrafico.setFocusPainted(false);
		btnGrafico.setBorderPainted(false);
		btnGrafico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnGrafico.setPreferredSize(new Dimension(200, 40));

		// Variables finales para pasarlas al evento del botón
		final float finalIngresoBruto = ingresoBrutoMensual;
		final float finalEgresos = totalEgresos;
		final float finalIngresoNeto = ingresoNeto;

		btnGrafico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarGraficoBalance(finalIngresoBruto, finalEgresos, finalIngresoNeto);
			}
		});

		JPanel pnlIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlIzquierda.setBackground(bgWhite);
		pnlIzquierda.add(btnGrafico);
		buttonPane.add(pnlIzquierda, BorderLayout.WEST);

		// Botón Cerrar (A la derecha)
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

	// ==========================================
	// HERRAMIENTA: CREAR TARJETAS ESTILO MODERNO
	// ==========================================
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

	// ==========================================
	// LÓGICA DE JFREECHART (GRÁFICO DE BARRAS)
	// ==========================================
	private void mostrarGraficoBalance(float ingresos, float egresos, float neto) {

		// 1. Preparamos los datos
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(ingresos, "Finanzas", "Ingreso Bruto");
		dataset.addValue(egresos, "Finanzas", "Egresos");
		dataset.addValue(neto, "Finanzas", "Ganancia Neta");

		// 2. Creamos el gráfico usando ChartFactory
		JFreeChart chart = ChartFactory.createBarChart(
				"Balance Financiero Mensual", // Título
				"",                           // Etiqueta Eje X
				"Monto en $",                 // Etiqueta Eje Y
				dataset,                      // Datos
				PlotOrientation.VERTICAL,     // Orientación
				false,                        // Incluir leyenda
				true,                         // Tooltips (Al pasar el mouse)
				false                         // URLs
				);

		// 3. Estilizamos el gráfico con los colores de Altice
		chart.setBackgroundPaint(bgWhite);
		chart.getTitle().setPaint(textColor);
		chart.getTitle().setFont(new Font("SansSerif", Font.BOLD, 18));

		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(alticeLight);
		plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
		plot.setOutlineVisible(false);

		// Coloreamos las barras del azul de Altice y las hacemos planas (modernas)
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setBarPainter(new StandardBarPainter()); 
		renderer.setSeriesPaint(0, alticeBlue);
		renderer.setDrawBarOutline(false);
		renderer.setItemMargin(0.1);

		// 4. Lo montamos en una nueva ventana (Dialog)
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(600, 400));

		JDialog ventanaGrafico = new JDialog(this, "Gráfico Financiero", true);
		ventanaGrafico.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		ventanaGrafico.getContentPane().setBackground(bgWhite);
		ventanaGrafico.getContentPane().add(chartPanel, BorderLayout.CENTER);
		ventanaGrafico.pack();
		ventanaGrafico.setLocationRelativeTo(this);
		ventanaGrafico.setVisible(true);
	}
}