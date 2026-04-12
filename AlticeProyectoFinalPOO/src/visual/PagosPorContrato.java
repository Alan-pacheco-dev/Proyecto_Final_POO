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
	private JTextField txtIdContratoAPagar;
	private JTextField txtCodigoEmpleado;
	private JTextField txtNombreEmpleado;
	private JTextField txtSalarioEmpleado;
	private JTextField textField_4;
	private JTextField txtNombreDeUsuarioEmp;
	private JTextField txtContraseniaUsuarioEmp;
	private JTextField textField;
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

		final JSpinner spinner;

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("ID del Cliente con el contrato");
			lblNewLabel.setBounds(20, 37, 185, 34);
			panel.add(lblNewLabel);

			txtIdContratoAPagar = new JTextField();
			txtIdContratoAPagar.setEditable(false);
			txtIdContratoAPagar.setBounds(20, 82, 185, 20);
			panel.add(txtIdContratoAPagar);
			txtIdContratoAPagar.setColumns(10);

			JLabel lblIdDelContrato = new JLabel("ID del contrato");
			lblIdDelContrato.setBounds(288, 37, 185, 34);
			panel.add(lblIdDelContrato);

			txtCodigoEmpleado = new JTextField();
			txtCodigoEmpleado.setEditable(false);
			txtCodigoEmpleado.setColumns(10);
			txtCodigoEmpleado.setBounds(288, 82, 185, 20);
			panel.add(txtCodigoEmpleado);

			JLabel lblNombreDelClienteDelContrato = new JLabel("Nombre del Cliente con el contrato");
			lblNombreDelClienteDelContrato.setBounds(20, 220, 245, 34);
			panel.add(lblNombreDelClienteDelContrato);

			txtNombreEmpleado = new JTextField();
			txtNombreEmpleado.setEditable(false);
			txtNombreEmpleado.setColumns(10);
			txtNombreEmpleado.setBounds(20, 265, 453, 20);
			panel.add(txtNombreEmpleado);

			JLabel lblSalario = new JLabel("Fecha de inicio del pago");
			lblSalario.setBounds(20, 308, 147, 34);
			panel.add(lblSalario);

			txtSalarioEmpleado = new JTextField();
			txtSalarioEmpleado.setEditable(false);
			txtSalarioEmpleado.setColumns(10);
			txtSalarioEmpleado.setBounds(20, 353, 185, 20);
			panel.add(txtSalarioEmpleado);

			textField_4 = new JTextField();
			textField_4.setEditable(false);
			textField_4.setColumns(10);
			textField_4.setBounds(288, 353, 185, 20);
			panel.add(textField_4);

			txtNombreDeUsuarioEmp = new JTextField();
			txtNombreDeUsuarioEmp.setEditable(false);
			txtNombreDeUsuarioEmp.setColumns(10);
			txtNombreDeUsuarioEmp.setBounds(20, 446, 185, 20);
			panel.add(txtNombreDeUsuarioEmp);

			JLabel lblTotalPorPagar = new JLabel("Total por pagar");
			lblTotalPorPagar.setBounds(20, 401, 117, 34);
			panel.add(lblTotalPorPagar);

			txtContraseniaUsuarioEmp = new JTextField();
			txtContraseniaUsuarioEmp.setEditable(false);
			txtContraseniaUsuarioEmp.setColumns(10);
			txtContraseniaUsuarioEmp.setBounds(20, 175, 185, 20);
			panel.add(txtContraseniaUsuarioEmp);

			JLabel lblIdPlanPorPagar = new JLabel("ID del plan por pagar");
			lblIdPlanPorPagar.setBounds(20, 130, 117, 34);
			panel.add(lblIdPlanPorPagar);

			JLabel lblFechaDeVencimiento = new JLabel("Fecha de vencimiento del pago");
			lblFechaDeVencimiento.setBounds(287, 308, 186, 34);
			panel.add(lblFechaDeVencimiento);

			textField = new JTextField();
			textField.setEditable(false);
			textField.setColumns(10);
			textField.setBounds(288, 175, 185, 20);
			panel.add(textField);

			JLabel lblIdDelClientepor = new JLabel("ID del cliente");
			lblIdDelClientepor.setBounds(288, 130, 117, 34);
			panel.add(lblIdDelClientepor);

			JLabel lblPago = new JLabel("Monto a pagar");
			lblPago.setBounds(288, 401, 117, 34);
			panel.add(lblPago);

			spinner = new JSpinner();
			spinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
			spinner.setBounds(288, 446, 185, 20);
			panel.add(spinner);
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

				        float montoPagado = ((Number) spinner.getValue()).floatValue();

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
	        txtIdContratoAPagar.setText(miPago.getContrato().getCliente().getIdPersona());
	        txtCodigoEmpleado.setText(miPago.getContrato().getIdContrato());
	        txtContraseniaUsuarioEmp.setText(miPago.getContrato().getPlanContrato().getIdPlan());
	        textField.setText(miPago.getContrato().getCliente().getIdPersona());
	        txtNombreEmpleado.setText(miPago.getContrato().getCliente().getNombre());
	        txtSalarioEmpleado.setText(miPago.getFechaInicioPago().toString());
	        textField_4.setText(miPago.getFechaVencimientoPago().toString());
	        txtNombreDeUsuarioEmp.setText("$ " + miPago.getTotalPorPagar());
	    }
	}
}