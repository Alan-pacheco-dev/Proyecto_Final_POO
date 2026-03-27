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
import javax.swing.JRadioButton;

public class RegistrarPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtNombreCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtAnNoHa;
	private JTextField textField;
	private JTextField txtEditarEstoPara;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarPlan dialog = new RegistrarPlan();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarPlan() {
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(551, 650);
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
			
			JLabel lblNewLabel = new JLabel("ID Plan");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setBounds(20, 82, 160, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblNombreDelEmpleado = new JLabel("Descripci\u00F3n del plan");
			lblNombreDelEmpleado.setBounds(20, 202, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente = new JTextField();
			txtNombreCliente.setEditable(false);
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 247, 490, 20);
			panel.add(txtNombreCliente);
			
			txtDireccionCliente = new JTextField();
			txtDireccionCliente.setEditable(false);
			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(20, 477, 250, 20);
			panel.add(txtDireccionCliente);
			
			JLabel lblDireccin = new JLabel("Precio total");
			lblDireccin.setBounds(20, 432, 148, 34);
			panel.add(lblDireccin);
			
			JLabel lblVigenciaDelContrato = new JLabel("\u00BFEst\u00E1 activo?");
			lblVigenciaDelContrato.setBounds(315, 37, 222, 34);
			panel.add(lblVigenciaDelContrato);
			
			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setText("Est\u00E1 siendo registrado");
			txtAnNoHa.setColumns(10);
			txtAnNoHa.setBounds(315, 82, 185, 20);
			panel.add(txtAnNoHa);
			
			JLabel lblTipoDeServicio = new JLabel("Nombre del plan");
			lblTipoDeServicio.setBounds(20, 129, 148, 34);
			panel.add(lblTipoDeServicio);
			
			textField = new JTextField();
			textField.setEditable(false);
			textField.setColumns(10);
			textField.setBounds(20, 174, 490, 20);
			panel.add(textField);
			
			JTextPane txtpnSeCalculaCon = new JTextPane();
			txtpnSeCalculaCon.setText("Se calcula con el precio de los servicios");
			txtpnSeCalculaCon.setBounds(20, 508, 250, 20);
			panel.add(txtpnSeCalculaCon);
			
			JLabel label = new JLabel("Servicios del Plan");
			label.setBounds(20, 292, 222, 34);
			panel.add(label);
			
			JButton button = new JButton("Crear");
			button.setBounds(20, 364, 95, 23);
			panel.add(button);
			
			JButton button_1 = new JButton("Escoger");
			button_1.setBounds(125, 364, 95, 23);
			panel.add(button_1);
			
			JLabel lblCuotas = new JLabel("Cuotas del Plan");
			lblCuotas.setBounds(313, 292, 198, 34);
			panel.add(lblCuotas);
			
			JComboBox comboBox = new JComboBox();
			comboBox.setBounds(313, 337, 185, 20);
			panel.add(comboBox);
			
			JRadioButton rdbtnMensual = new JRadioButton("Mensual");
			rdbtnMensual.setBounds(313, 375, 85, 23);
			panel.add(rdbtnMensual);
			
			JRadioButton rdbtnAnual = new JRadioButton("Anual");
			rdbtnAnual.setBounds(410, 375, 109, 23);
			panel.add(rdbtnAnual);
			
			JButton btnVerServiciosEscogidos = new JButton("Ver servicios escogidos");
			btnVerServiciosEscogidos.setBounds(20, 398, 200, 23);
			panel.add(btnVerServiciosEscogidos);
			
			txtEditarEstoPara = new JTextField();
			txtEditarEstoPara.setText("Poner aqui la cantidad de servicios escogidos");
			txtEditarEstoPara.setEditable(false);
			txtEditarEstoPara.setColumns(10);
			txtEditarEstoPara.setBounds(20, 333, 200, 20);
			panel.add(txtEditarEstoPara);
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
