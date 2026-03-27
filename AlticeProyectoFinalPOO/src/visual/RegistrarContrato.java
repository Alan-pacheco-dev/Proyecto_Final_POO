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
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class RegistrarContrato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtPlanDescripcion;
	private JTextField txtAnNoHa;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarContrato dialog = new RegistrarContrato();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarContrato() {
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(573, 689);
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
			
			JLabel lblNewLabel = new JLabel("ID Contrato");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setBounds(20, 82, 160, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(20, 202, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente = new JTextField();
			txtNombreCliente.setEditable(false);
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 247, 490, 20);
			panel.add(txtNombreCliente);
			
			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setEditable(false);
			txtDeudaCliente.setColumns(10);
			txtDeudaCliente.setBounds(315, 174, 185, 20);
			panel.add(txtDeudaCliente);
			
			JLabel lblComision = new JLabel("Deuda del cliente");
			lblComision.setBounds(315, 129, 222, 34);
			panel.add(lblComision);
			
			txtEmailCliente = new JTextField();
			txtEmailCliente.setEditable(false);
			txtEmailCliente.setColumns(10);
			txtEmailCliente.setBounds(20, 323, 490, 20);
			panel.add(txtEmailCliente);
			
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(20, 278, 148, 34);
			panel.add(lblEmail);
			
			txtDireccionCliente = new JTextField();
			txtDireccionCliente.setEditable(false);
			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(20, 411, 490, 20);
			panel.add(txtDireccionCliente);
			
			JLabel lblDireccin = new JLabel("Direcci\u00F3n");
			lblDireccin.setBounds(20, 366, 148, 34);
			panel.add(lblDireccin);
			
			JLabel lblPlan = new JLabel("Plan del contrato");
			lblPlan.setBounds(20, 463, 222, 34);
			panel.add(lblPlan);
			
			txtPlanDescripcion = new JTextField();
			txtPlanDescripcion.setEditable(false);
			txtPlanDescripcion.setColumns(10);
			txtPlanDescripcion.setBounds(20, 508, 490, 20);
			panel.add(txtPlanDescripcion);
			
			JButton btnEscoger = new JButton("Escoger");
			btnEscoger.setBounds(175, 546, 95, 23);
			panel.add(btnEscoger);
			
			JButton btnCrear = new JButton("Crear");
			btnCrear.setBounds(20, 546, 95, 23);
			panel.add(btnCrear);
			
			JLabel lblVigenciaDelContrato = new JLabel("Vigencia del contrato");
			lblVigenciaDelContrato.setBounds(315, 37, 222, 34);
			panel.add(lblVigenciaDelContrato);
			
			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setText("A\u00FAn no ha sido realizado");
			txtAnNoHa.setColumns(10);
			txtAnNoHa.setBounds(315, 82, 185, 20);
			panel.add(txtAnNoHa);
			
			JLabel label = new JLabel("C\u00F3digo del Cliente");
			label.setBounds(20, 129, 148, 34);
			panel.add(label);
			
			textField_1 = new JTextField();
			textField_1.setEditable(false);
			textField_1.setColumns(10);
			textField_1.setBounds(20, 174, 160, 20);
			panel.add(textField_1);
			
			JButton btnVerServiciosPlan = new JButton("Ver los servicios del plan");
			btnVerServiciosPlan.setBounds(303, 546, 207, 23);
			panel.add(btnVerServiciosPlan);
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
