package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtNombreCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtAnNoHa;
	private JTextField textField;

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
			txtpnSeCalculaCon.setText("Se calcula colocando el precio de cada uno de los servicios");
			txtpnSeCalculaCon.setBounds(20, 508, 405, 20);
			panel.add(txtpnSeCalculaCon);
			
			JLabel label = new JLabel("Servicios del Plan");
			label.setBounds(20, 292, 222, 34);
			panel.add(label);
			
			JButton btnVerServiciosEscogidos = new JButton("Ver servicios escogidos");
			btnVerServiciosEscogidos.setBounds(146, 386, 200, 23);
			panel.add(btnVerServiciosEscogidos);
			
			JRadioButton rdbtnInternet = new JRadioButton("Internet");
			rdbtnInternet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Mostrar servicio de internet
				}
			});
			rdbtnInternet.setBounds(20, 334, 109, 23);
			panel.add(rdbtnInternet);
			
			JRadioButton rdbtnCable = new JRadioButton("Cable");
			rdbtnCable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Mostrar servicio de Cable
				}
			});
			rdbtnCable.setBounds(214, 333, 109, 23);
			panel.add(rdbtnCable);
			
			JRadioButton rdbtnMovil = new JRadioButton("M\u00F3vil");
			rdbtnMovil.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Mostrar servicio Movil
					//JOptionPane.showMessageDialog(null, "Has ingresado a este botón", "Información", JOptionPane.INFORMATION_MESSAGE);
					
				}
			});
			rdbtnMovil.setBounds(401, 334, 109, 23);
			panel.add(rdbtnMovil);
			
			JTextPane txtpnSiCualquieraDe = new JTextPane();
			txtpnSiCualquieraDe.setText("Si cualquiera de los servicios es seleccionado, mostrar su ventana de creaci\u00F3n");
			txtpnSiCualquieraDe.setBounds(20, 414, 405, 20);
			panel.add(txtpnSiCualquieraDe);
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
