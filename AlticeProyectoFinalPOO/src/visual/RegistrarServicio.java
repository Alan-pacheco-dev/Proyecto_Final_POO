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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegistrarServicio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtAnNoHa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegistrarServicio dialog = new RegistrarServicio();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegistrarServicio() {
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(530, 891);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel panelRegistrarServicio = new JPanel();
			panelRegistrarServicio.setBounds(5, 5, 499, 203);
			panelRegistrarServicio.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panelRegistrarServicio);
			panelRegistrarServicio.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("ID Servicio");
			lblNewLabel.setBounds(21, 22, 117, 34);
			panelRegistrarServicio.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setBounds(21, 67, 160, 20);
			panelRegistrarServicio.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblVigenciaDelContrato = new JLabel("\u00BFEst\u00E1 activo este servicio?");
			lblVigenciaDelContrato.setBounds(316, 22, 222, 34);
			panelRegistrarServicio.add(lblVigenciaDelContrato);
			
			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setText("Est\u00E1 siendo registrado");
			txtAnNoHa.setColumns(10);
			txtAnNoHa.setBounds(316, 67, 175, 20);
			panelRegistrarServicio.add(txtAnNoHa);
			
			JLabel lblTipoDeServicio = new JLabel("Tipo de Servicio");
			lblTipoDeServicio.setBounds(21, 114, 148, 34);
			panelRegistrarServicio.add(lblTipoDeServicio);
			
			JRadioButton rdbtnMovil = new JRadioButton("M\u00F3vil");
			rdbtnMovil.setBounds(21, 155, 109, 23);
			panelRegistrarServicio.add(rdbtnMovil);
			
			JRadioButton rdbtnInternet = new JRadioButton("Internet");
			rdbtnInternet.setBounds(198, 155, 109, 23);
			panelRegistrarServicio.add(rdbtnInternet);
			
			JRadioButton rdbtnTelefonia = new JRadioButton("Telefon\u00EDa");
			rdbtnTelefonia.setBounds(360, 155, 109, 23);
			panelRegistrarServicio.add(rdbtnTelefonia);
		}
		
		JPanel panelServicioMovil = new JPanel();
		panelServicioMovil.setLayout(null);
		panelServicioMovil.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelServicioMovil.setBounds(5, 219, 499, 153);
		contentPanel.add(panelServicioMovil);
		
		JTextPane txtpnServicioMvil = new JTextPane();
		txtpnServicioMvil.setText("SERVICIO M\u00D3VIL");
		txtpnServicioMvil.setBounds(202, 23, 126, 20);
		panelServicioMovil.add(txtpnServicioMvil);
		
		JLabel label = new JLabel("Minutos");
		label.setBounds(182, 54, 148, 34);
		panelServicioMovil.add(label);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(182, 92, 117, 20);
		panelServicioMovil.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setBounds(356, 92, 117, 20);
		panelServicioMovil.add(spinner_1);
		
		JLabel label_1 = new JLabel("SMS");
		label_1.setBounds(356, 54, 148, 34);
		panelServicioMovil.add(label_1);
		
		JLabel label_2 = new JLabel("Datos en GB");
		label_2.setBounds(10, 54, 148, 34);
		panelServicioMovil.add(label_2);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setBounds(10, 92, 117, 20);
		panelServicioMovil.add(spinner_2);
		
		JPanel panelServicioTelefonía = new JPanel();
		panelServicioTelefonía.setLayout(null);
		panelServicioTelefonía.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelServicioTelefonía.setBounds(5, 383, 499, 147);
		contentPanel.add(panelServicioTelefonía);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("SERVICIO TELEFON\u00CDA");
		textPane.setBounds(202, 23, 148, 20);
		panelServicioTelefonía.add(textPane);
		
		JLabel label_3 = new JLabel("Costo por minuto");
		label_3.setBounds(313, 59, 148, 34);
		panelServicioTelefonía.add(label_3);
		
		JSpinner spinner_4 = new JSpinner();
		spinner_4.setBounds(313, 97, 117, 20);
		panelServicioTelefonía.add(spinner_4);
		
		JLabel label_4 = new JLabel("Llamadas ilimitadas");
		label_4.setBounds(30, 57, 148, 34);
		panelServicioTelefonía.add(label_4);
		
		JRadioButton radioButton = new JRadioButton("Si");
		radioButton.setBounds(26, 94, 56, 23);
		panelServicioTelefonía.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("No");
		radioButton_1.setBounds(91, 94, 56, 23);
		panelServicioTelefonía.add(radioButton_1);
		
		JPanel panelServicioInternet = new JPanel();
		panelServicioInternet.setLayout(null);
		panelServicioInternet.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelServicioInternet.setBounds(5, 541, 499, 138);
		contentPanel.add(panelServicioInternet);
		
		JTextPane txtpnServicioMvil_1 = new JTextPane();
		txtpnServicioMvil_1.setText("SERVICIO INTERNET");
		txtpnServicioMvil_1.setBounds(202, 23, 148, 20);
		panelServicioInternet.add(txtpnServicioMvil_1);
		
		JLabel lblVelocidadDeMbps = new JLabel("Velocidad de Mbps");
		lblVelocidadDeMbps.setBounds(260, 59, 148, 34);
		panelServicioInternet.add(lblVelocidadDeMbps);
		
		JSpinner spinner_6 = new JSpinner();
		spinner_6.setBounds(260, 97, 117, 20);
		panelServicioInternet.add(spinner_6);
		
		JLabel lbltieneRouter = new JLabel("\u00BFTiene Router?");
		lbltieneRouter.setBounds(39, 59, 148, 34);
		panelServicioInternet.add(lbltieneRouter);
		
		JRadioButton radioButton_2 = new JRadioButton("Si");
		radioButton_2.setBounds(35, 96, 56, 23);
		panelServicioInternet.add(radioButton_2);
		
		JRadioButton radioButton_3 = new JRadioButton("No");
		radioButton_3.setBounds(100, 96, 56, 23);
		panelServicioInternet.add(radioButton_3);
		
		JPanel panelPrecioDelServicio = new JPanel();
		panelPrecioDelServicio.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPrecioDelServicio.setBounds(5, 690, 499, 99);
		contentPanel.add(panelPrecioDelServicio);
		panelPrecioDelServicio.setLayout(null);
		
		JSpinner spinner_7 = new JSpinner();
		spinner_7.setBounds(10, 55, 117, 20);
		panelPrecioDelServicio.add(spinner_7);
		
		JLabel label_6 = new JLabel("Precio del servicio");
		label_6.setBounds(10, 30, 148, 14);
		panelPrecioDelServicio.add(label_6);
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
