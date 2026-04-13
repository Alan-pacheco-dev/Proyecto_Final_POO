package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import logico.Cliente;
import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;

public class ReporteGeneral extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;

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
		setBounds(100, 100, 800, 450); 
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		// GridLayout de 3 filas y 2 columnas
		contentPanel.setLayout(new GridLayout(3, 2, 15, 15));

		// ==========================================
		// 1. LÓGICA DE CÁLCULO DE DATOS (KPIs)
		// ==========================================
		EmpresaAltice empresa = EmpresaAltice.getInstance();
		
		// Clientes y Deuda

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
		
		// Contratos y Finanzas
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
		
		// Empleados y Nómina
		float nominaBase = 0;
		for (Empleado emp : empresa.getMisEmpleados()) {
			nominaBase += emp.getSalario();
		}
		
		float totalEgresos = nominaBase + comisionesMensuales;
		float ingresoNeto = ingresoBrutoMensual - totalEgresos;

		// ==========================================
		// 2. CREACIÓN DE LAS TARJETAS VISUALES (ESTILO ESTÁNDAR)
		// ==========================================
		
		contentPanel.add(crearTarjeta("Total de Clientes activos", String.valueOf(totalClientesActivos)));
		
		String textoContratos = "Activos: " + contratosActivos + "  |  Inactivos: " + contratosInactivos;
		contentPanel.add(crearTarjeta("Estado de Contratos", textoContratos));
		
		contentPanel.add(crearTarjeta("Deuda Total a Cobrar", "$ " + String.format("%.2f", deudaTotal)));
		
		contentPanel.add(crearTarjeta("Ingreso Bruto Mensual Estimado", "$ " + String.format("%.2f", ingresoBrutoMensual)));
		
		contentPanel.add(crearTarjeta("Egresos (Nómina + Comisiones)", "$ " + String.format("%.2f", totalEgresos)));
		
		// Aquí mostramos el ingreso neto calculado
		contentPanel.add(crearTarjeta("Ganancia Neta Estimada", "$ " + String.format("%.2f", ingresoNeto)));

		// ==========================================
		// BOTÓN DE CERRAR
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
	}

	// Método auxiliar para crear recuadros clásicos de Java Swing
	private JPanel crearTarjeta(String titulo, String valor) {
		JPanel tarjeta = new JPanel();
		tarjeta.setLayout(new BorderLayout());
		
		// Usamos el TitledBorder clásico nativo de Swing
		tarjeta.setBorder(BorderFactory.createTitledBorder(titulo));

		// Etiqueta del valor centrada, con fuente y color estándar del sistema
		JLabel lblValor = new JLabel(valor);
		lblValor.setHorizontalAlignment(SwingConstants.CENTER);
		tarjeta.add(lblValor, BorderLayout.CENTER);

		return tarjeta;
	}
}