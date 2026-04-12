package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Contrato;
import logico.EmpresaAltice;
import logico.Pagos;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class PagosPorContrato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtIdDelClienteContrato;
	private JTextField txtIdContrato;
	private JTextField txtNombreCliente;
	private JTextField txtFechaInicioPago;
	private JTextField txtFechaVencimientoPago;
	private JTextField txtTotalPorPagar;
	private JTextField txtIdPlan;
	private JTextField txtIdcliente;
	private Pagos miPago = null;

	public PagosPorContrato(Pagos pago) {
		miPago = pago;
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 595);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		final JSpinner spnMontoAPagar;

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("ID del Cliente con el contrato");
			lblNewLabel.setBounds(20, 37, 185, 34);
			panel.add(lblNewLabel);

			txtIdDelClienteContrato = new JTextField();
			txtIdDelClienteContrato.setEditable(false);
			txtIdDelClienteContrato.setBounds(20, 82, 185, 20);
			panel.add(txtIdDelClienteContrato);
			txtIdDelClienteContrato.setColumns(10);

			JLabel lblIdDelContrato = new JLabel("ID del contrato");
			lblIdDelContrato.setBounds(288, 37, 185, 34);
			panel.add(lblIdDelContrato);

			txtIdContrato = new JTextField();
			txtIdContrato.setEditable(false);
			txtIdContrato.setColumns(10);
			txtIdContrato.setBounds(288, 82, 185, 20);
			panel.add(txtIdContrato);

			JLabel lblNombreDelClienteDelContrato = new JLabel("Nombre del Cliente con el contrato");
			lblNombreDelClienteDelContrato.setBounds(20, 220, 245, 34);
			panel.add(lblNombreDelClienteDelContrato);

			txtNombreCliente = new JTextField();
			txtNombreCliente.setEditable(false);
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 265, 453, 20);
			panel.add(txtNombreCliente);

			JLabel lblSalario = new JLabel("Fecha de inicio del pago");
			lblSalario.setBounds(20, 308, 147, 34);
			panel.add(lblSalario);

			txtFechaInicioPago = new JTextField();
			txtFechaInicioPago.setEditable(false);
			txtFechaInicioPago.setColumns(10);
			txtFechaInicioPago.setBounds(20, 353, 185, 20);
			panel.add(txtFechaInicioPago);

			txtFechaVencimientoPago = new JTextField();
			txtFechaVencimientoPago.setEditable(false);
			txtFechaVencimientoPago.setColumns(10);
			txtFechaVencimientoPago.setBounds(288, 353, 185, 20);
			panel.add(txtFechaVencimientoPago);

			txtTotalPorPagar = new JTextField();
			txtTotalPorPagar.setEditable(false);
			txtTotalPorPagar.setColumns(10);
			txtTotalPorPagar.setBounds(20, 446, 185, 20);
			panel.add(txtTotalPorPagar);

			JLabel lblTotalPorPagar = new JLabel("Total por pagar");
			lblTotalPorPagar.setBounds(20, 401, 117, 34);
			panel.add(lblTotalPorPagar);

			txtIdPlan = new JTextField();
			txtIdPlan.setEditable(false);
			txtIdPlan.setColumns(10);
			txtIdPlan.setBounds(20, 175, 185, 20);
			panel.add(txtIdPlan);

			JLabel lblIdPlanPorPagar = new JLabel("ID del plan por pagar");
			lblIdPlanPorPagar.setBounds(20, 130, 117, 34);
			panel.add(lblIdPlanPorPagar);

			JLabel lblFechaDeVencimiento = new JLabel("Fecha de vencimiento del pago");
			lblFechaDeVencimiento.setBounds(287, 308, 186, 34);
			panel.add(lblFechaDeVencimiento);

			txtIdcliente = new JTextField();
			txtIdcliente.setEditable(false);
			txtIdcliente.setColumns(10);
			txtIdcliente.setBounds(288, 175, 185, 20);
			panel.add(txtIdcliente);

			JLabel lblIdDelClientepor = new JLabel("ID del cliente");
			lblIdDelClientepor.setBounds(288, 130, 117, 34);
			panel.add(lblIdDelClientepor);

			JLabel lblPago = new JLabel("Monto a pagar");
			lblPago.setBounds(288, 401, 117, 34);
			panel.add(lblPago);

			spnMontoAPagar = new JSpinner();
			spnMontoAPagar.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
			spnMontoAPagar.setBounds(288, 446, 185, 20);
			panel.add(spnMontoAPagar);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				        if (miPago == null) return;

				        float montoPagado = ((Number) spnMontoAPagar.getValue()).floatValue();

				        if (montoPagado <= 0) {
				            JOptionPane.showMessageDialog(null, "El monto a pagar debe ser mayor a 0",
				                "Error", JOptionPane.ERROR_MESSAGE);
				            return;
				        }

				        EmpresaAltice empresa = EmpresaAltice.getInstance();

				        // Registrar lo que se pagó en este pago, sin modificar totalPorPagar
				        miPago.setPagoDelCliente(montoPagado);
				        miPago.setFechaPagoDelCliente(LocalDate.now());
				        miPago.setPagadoTotal(true); // este pago queda cerrado como evidencia

				        float restante = miPago.getTotalPorPagar() - montoPagado;

				        if (restante > 0) {
				            // Crear nuevo pago pendiente con el restante
				        	Pagos pagoRestante = new Pagos(
				        		    miPago.getContrato(),
				        		    miPago.getFechaInicioPago(),       
				        		    miPago.getFechaVencimientoPago(),  
				        		    null,
				        		    0,
				        		    0,
				        		    restante
				        	);
				            pagoRestante.setPagadoTotal(false);
				            empresa.getPagos().add(pagoRestante);

				            JOptionPane.showMessageDialog(null,
				                "Pago parcial registrado. Queda pendiente: $ " + restante,
				                "Información", JOptionPane.INFORMATION_MESSAGE);
				        } else {
				            JOptionPane.showMessageDialog(null, "Pago completado con éxito",
				                "Información", JOptionPane.INFORMATION_MESSAGE);
				        }
				        empresa.actualizarDeudaClientes();
				        empresa.GuardarDatos(
				            empresa.getMisClientes(), empresa.getMisEmpleados(),
				            empresa.getMisPlanes(), empresa.getMisServicios(),
				            empresa.getMisUsuarios(), empresa.getMisContratos(),
				            empresa.getPagos()
				        );

				        dispose();
				    }
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}

		// Cargar datos del contrato
		loadContrato();
	}

	private void loadContrato() {
	    if (miPago != null) {
	        txtIdDelClienteContrato.setText(miPago.getContrato().getCliente().getIdPersona());
	        txtIdContrato.setText(miPago.getContrato().getIdContrato());
	        txtIdPlan.setText(miPago.getContrato().getPlanContrato().getIdPlan());
	        txtIdcliente.setText(miPago.getContrato().getCliente().getIdPersona());
	        txtNombreCliente.setText(miPago.getContrato().getCliente().getNombre());
	        txtFechaInicioPago.setText(miPago.getFechaInicioPago().toString());
	        txtFechaVencimientoPago.setText(miPago.getFechaVencimientoPago().toString());
	        txtTotalPorPagar.setText("$ " + miPago.getTotalPorPagar());
	    }
	}
}