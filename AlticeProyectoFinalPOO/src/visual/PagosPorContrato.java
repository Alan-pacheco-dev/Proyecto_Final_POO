package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Contrato;

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
	private Contrato miCto = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PagosPorContrato dialog = new PagosPorContrato(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PagosPorContrato(Contrato cto) {
		miCto = cto;
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 595);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
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
			
			JSpinner spinner = new JSpinner();
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
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}
