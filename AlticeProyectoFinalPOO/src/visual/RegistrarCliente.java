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

public class RegistrarCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtCodigoCliente;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarCliente dialog = new RegistrarCliente();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarCliente() {
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 586);
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
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setBounds(20, 82, 185, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblCdigoDelCliente = new JLabel("C\u00F3digo del Cliente");
			lblCdigoDelCliente.setBounds(288, 37, 222, 34);
			panel.add(lblCdigoDelCliente);
			
			txtCodigoCliente = new JTextField();
			txtCodigoCliente.setColumns(10);
			txtCodigoCliente.setBounds(288, 82, 185, 20);
			panel.add(txtCodigoCliente);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(20, 129, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente = new JTextField();
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 174, 453, 20);
			panel.add(txtNombreCliente);
			
			JLabel lblVentas = new JLabel("Contrato");
			lblVentas.setBounds(20, 390, 117, 34);
			panel.add(lblVentas);
			
			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setColumns(10);
			txtDeudaCliente.setBounds(288, 435, 185, 20);
			panel.add(txtDeudaCliente);
			
			JLabel lblComision = new JLabel("Deuda del cliente");
			lblComision.setBounds(288, 390, 222, 34);
			panel.add(lblComision);
			
			txtEmailCliente = new JTextField();
			txtEmailCliente.setColumns(10);
			txtEmailCliente.setBounds(20, 250, 453, 20);
			panel.add(txtEmailCliente);
			
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(20, 205, 148, 34);
			panel.add(lblEmail);
			
			txtDireccionCliente = new JTextField();
			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(20, 338, 453, 20);
			panel.add(txtDireccionCliente);
			
			JLabel lblDireccin = new JLabel("Direcci\u00F3n");
			lblDireccin.setBounds(20, 293, 148, 34);
			panel.add(lblDireccin);
			
			JButton btnCrearUnContrato = new JButton("Crear un contrato");
			btnCrearUnContrato.setBounds(20, 435, 185, 23);
			panel.add(btnCrearUnContrato);
			
			JTextPane txtpnPulseAquPara = new JTextPane();
			txtpnPulseAquPara.setText("Pulse aqu\u00ED para registrar un contrato");
			txtpnPulseAquPara.setBounds(20, 469, 207, 20);
			panel.add(txtpnPulseAquPara);
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
