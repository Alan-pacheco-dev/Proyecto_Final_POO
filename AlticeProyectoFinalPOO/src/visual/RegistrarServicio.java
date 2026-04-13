package visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegistrarServicio extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtAnNoHa;

	private JPanel panelServicioMovil;
	private JPanel panelServicioTelefonia;
	private JPanel panelServicioInternet;
	private JPanel panelServicioTelevision;
	private JPanel panelCard;
	private CardLayout cardLayout;

	public static void main(String[] args) {
		try {
			RegistrarServicio dialog = new RegistrarServicio();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarServicio() {
		setTitle("Registrar Servicio");
		setBounds(100, 100, 530, 560);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

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
		txtID.setText("S - " + EmpresaAltice.getInstance().idServicios);
		txtID.setBounds(21, 67, 160, 20);
		panelRegistrarServicio.add(txtID);
		txtID.setColumns(10);

		JLabel lblVigenciaDelContrato = new JLabel("¿Está activo este servicio?");
		lblVigenciaDelContrato.setBounds(316, 22, 222, 34);
		panelRegistrarServicio.add(lblVigenciaDelContrato);

		txtAnNoHa = new JTextField();
		txtAnNoHa.setEditable(false);
		txtAnNoHa.setText("Está siendo registrado");
		txtAnNoHa.setColumns(10);
		txtAnNoHa.setBounds(316, 67, 175, 20);
		panelRegistrarServicio.add(txtAnNoHa);

		JLabel lblTipoDeServicio = new JLabel("Tipo de Servicio");
		lblTipoDeServicio.setBounds(21, 114, 148, 34);
		panelRegistrarServicio.add(lblTipoDeServicio);

		JRadioButton rdbtnMovil = new JRadioButton("Móvil");
		rdbtnMovil.setBounds(21, 155, 75, 23);
		panelRegistrarServicio.add(rdbtnMovil);

		JRadioButton rdbtnInternet = new JRadioButton("Internet");
		rdbtnInternet.setBounds(127, 155, 85, 23);
		panelRegistrarServicio.add(rdbtnInternet);

		JRadioButton rdbtnTelefonia = new JRadioButton("Telefonía");
		rdbtnTelefonia.setBounds(250, 155, 90, 23);
		panelRegistrarServicio.add(rdbtnTelefonia);

		JRadioButton rdbtnTelevision = new JRadioButton("Televisión");
		rdbtnTelevision.setBounds(376, 155, 100, 23);
		panelRegistrarServicio.add(rdbtnTelevision);

		ButtonGroup bgTipoServicio = new ButtonGroup();
		bgTipoServicio.add(rdbtnMovil);
		bgTipoServicio.add(rdbtnInternet);
		bgTipoServicio.add(rdbtnTelefonia);
		bgTipoServicio.add(rdbtnTelevision);

		cardLayout = new CardLayout();
		panelCard = new JPanel(cardLayout);
		panelCard.setBounds(5, 215, 499, 153);
		contentPanel.add(panelCard);

		panelServicioMovil = new JPanel();
		panelServicioMovil.setLayout(null);
		panelServicioMovil.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextPane txtpnServicioMvil = new JTextPane();
		txtpnServicioMvil.setEditable(false);
		txtpnServicioMvil.setText("SERVICIO MÓVIL");
		txtpnServicioMvil.setBounds(202, 23, 126, 20);
		panelServicioMovil.add(txtpnServicioMvil);

		JLabel labelDatos = new JLabel("Datos en GB");
		labelDatos.setBounds(10, 54, 148, 34);
		panelServicioMovil.add(labelDatos);
		JSpinner spnDatos = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnDatos.setBounds(10, 92, 117, 20);
		panelServicioMovil.add(spnDatos);

		JLabel labelMinutos = new JLabel("Minutos");
		labelMinutos.setBounds(182, 54, 148, 34);
		panelServicioMovil.add(labelMinutos);
		JSpinner spnMinutos = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnMinutos.setBounds(182, 92, 117, 20);
		panelServicioMovil.add(spnMinutos);

		JLabel labelSms = new JLabel("SMS");
		labelSms.setBounds(356, 54, 148, 34);
		panelServicioMovil.add(labelSms);
		JSpinner spnSms = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnSms.setBounds(356, 92, 117, 20);
		panelServicioMovil.add(spnSms);

		panelServicioInternet = new JPanel();
		panelServicioInternet.setLayout(null);
		panelServicioInternet.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextPane txtpnServicioInternet = new JTextPane();
		txtpnServicioInternet.setEditable(false);
		txtpnServicioInternet.setText("SERVICIO INTERNET");
		txtpnServicioInternet.setBounds(202, 23, 148, 20);
		panelServicioInternet.add(txtpnServicioInternet);

		JLabel lbltieneRouter = new JLabel("¿Tiene Router?");
		lbltieneRouter.setBounds(39, 59, 148, 34);
		panelServicioInternet.add(lbltieneRouter);

		JRadioButton rbRouterSi = new JRadioButton("Si");
		rbRouterSi.setBounds(35, 96, 56, 23);
		panelServicioInternet.add(rbRouterSi);
		JRadioButton rbRouterNo = new JRadioButton("No");
		rbRouterNo.setBounds(100, 96, 56, 23);
		panelServicioInternet.add(rbRouterNo);

		ButtonGroup bgRouter = new ButtonGroup();
		bgRouter.add(rbRouterSi);
		bgRouter.add(rbRouterNo);

		JLabel lblVelocidadDeMbps = new JLabel("Velocidad de Mbps");
		lblVelocidadDeMbps.setBounds(260, 59, 148, 34);
		panelServicioInternet.add(lblVelocidadDeMbps);
		JSpinner spnVelocidad = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnVelocidad.setBounds(260, 97, 117, 20);
		panelServicioInternet.add(spnVelocidad);

		panelServicioTelefonia = new JPanel();
		panelServicioTelefonia.setLayout(null);
		panelServicioTelefonia.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextPane txtpnTelefonia = new JTextPane();
		txtpnTelefonia.setEditable(false);
		txtpnTelefonia.setText("SERVICIO TELEFONÍA FIJA");
		txtpnTelefonia.setBounds(180, 23, 170, 20);
		panelServicioTelefonia.add(txtpnTelefonia);

		JLabel lblLlamadasIlimitadas = new JLabel("¿Llamadas Ilimitadas?");
		lblLlamadasIlimitadas.setBounds(10, 57, 148, 34);
		panelServicioTelefonia.add(lblLlamadasIlimitadas);

		JRadioButton rbIlimitadoSi = new JRadioButton("Si");
		rbIlimitadoSi.setBounds(10, 94, 50, 23);
		panelServicioTelefonia.add(rbIlimitadoSi);
		JRadioButton rbIlimitadoNo = new JRadioButton("No");
		rbIlimitadoNo.setBounds(65, 94, 50, 23);
		panelServicioTelefonia.add(rbIlimitadoNo);

		ButtonGroup bgIlimitado = new ButtonGroup();
		bgIlimitado.add(rbIlimitadoSi);
		bgIlimitado.add(rbIlimitadoNo);

		JLabel lblMinIncluidos = new JLabel("Minutos Incluidos");
		lblMinIncluidos.setBounds(170, 57, 120, 34);
		panelServicioTelefonia.add(lblMinIncluidos);
		JSpinner spnMinutosIncluidos = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnMinutosIncluidos.setBounds(170, 97, 100, 20);
		panelServicioTelefonia.add(spnMinutosIncluidos);

		JLabel lblCostoMinuto = new JLabel("Costo Minuto Extra");
		lblCostoMinuto.setBounds(330, 57, 148, 34);
		panelServicioTelefonia.add(lblCostoMinuto);
		JSpinner spnCostoMinuto = new JSpinner(new SpinnerNumberModel(0.0, 0.0, null, 0.5));
		spnCostoMinuto.setBounds(330, 97, 117, 20);
		panelServicioTelefonia.add(spnCostoMinuto);

		panelServicioTelevision = new JPanel();
		panelServicioTelevision.setLayout(null);
		panelServicioTelevision.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JTextPane txtpnTelevision = new JTextPane();
		txtpnTelevision.setEditable(false);
		txtpnTelevision.setText("SERVICIO TELEVISIÓN");
		txtpnTelevision.setBounds(180, 23, 170, 20);
		panelServicioTelevision.add(txtpnTelevision);

		JLabel lblCanales = new JLabel("Cantidad de Canales");
		lblCanales.setBounds(10, 57, 148, 34);
		panelServicioTelevision.add(lblCanales);
		JSpinner spnCanales = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnCanales.setBounds(10, 97, 117, 20);
		panelServicioTelevision.add(spnCanales);

		JLabel lblCajitas = new JLabel("Cajitas Incluidas");
		lblCajitas.setBounds(180, 57, 148, 34);
		panelServicioTelevision.add(lblCajitas);
		JSpinner spnCajitas = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		spnCajitas.setBounds(180, 97, 117, 20);
		panelServicioTelevision.add(spnCajitas);

		JLabel lblHD = new JLabel("¿Tiene HD?");
		lblHD.setBounds(350, 57, 148, 34);
		panelServicioTelevision.add(lblHD);
		JRadioButton rbHDSi = new JRadioButton("Si");
		rbHDSi.setBounds(350, 97, 45, 23);
		panelServicioTelevision.add(rbHDSi);
		JRadioButton rbHDNo = new JRadioButton("No");
		rbHDNo.setBounds(400, 97, 45, 23);
		panelServicioTelevision.add(rbHDNo);

		ButtonGroup bgHD = new ButtonGroup();
		bgHD.add(rbHDSi);
		bgHD.add(rbHDNo);

		panelCard.add(panelServicioMovil,     "MOVIL");
		panelCard.add(panelServicioInternet,  "INTERNET");
		panelCard.add(panelServicioTelefonia, "TELEFONIA");
		panelCard.add(panelServicioTelevision,"TELEVISION");

		JPanel panelPrecioDelServicio = new JPanel();
		panelPrecioDelServicio.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelPrecioDelServicio.setBounds(5, 380, 499, 99);
		contentPanel.add(panelPrecioDelServicio);
		panelPrecioDelServicio.setLayout(null);

		JLabel lblPrecio = new JLabel("Precio del servicio");
		lblPrecio.setBounds(10, 30, 148, 14);
		panelPrecioDelServicio.add(lblPrecio);

		JSpinner spnPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, null, 50.0));
		spnPrecio.setBounds(10, 55, 117, 20);
		panelPrecioDelServicio.add(spnPrecio);

		rdbtnMovil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelCard, "MOVIL");
			}
		});

		rdbtnInternet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelCard, "INTERNET");
			}
		});

		rdbtnTelefonia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelCard, "TELEFONIA");
			}
		});

		rdbtnTelevision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelCard, "TELEVISION");
			}
		});

		rdbtnMovil.setSelected(true);
		cardLayout.show(panelCard, "MOVIL");

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String idServicio = txtID.getText();
				float precio = ((Double) spnPrecio.getValue()).floatValue();

				if (precio <= 0) {
					JOptionPane.showMessageDialog(null, "El precio del servicio debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				EmpresaAltice empresa = EmpresaAltice.getInstance();

				if (rdbtnMovil.isSelected()) {

					int datos   = (Integer) spnDatos.getValue();
					int minutos = (Integer) spnMinutos.getValue();
					int sms     = (Integer) spnSms.getValue();

					if (datos <= 0 || minutos <= 0 || sms <= 0) {
						JOptionPane.showMessageDialog(null, "Los datos, minutos y SMS deben ser mayores a 0.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					ServicioMovil movil = new ServicioMovil("Móvil", precio, datos, minutos, sms);
					empresa.getMisServicios().add(movil);

				} else if (rdbtnInternet.isSelected()) {

					if (!rbRouterSi.isSelected() && !rbRouterNo.isSelected()) {
						JOptionPane.showMessageDialog(null, "Debe indicar si tiene o no Router.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					boolean tieneRouter = rbRouterSi.isSelected();
					int velocidad = (Integer) spnVelocidad.getValue();

					if (velocidad <= 0) {
						JOptionPane.showMessageDialog(null, "La velocidad del internet debe ser mayor a 0 Mbps.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					ServicioInternet internet = new ServicioInternet("Internet", precio, velocidad, tieneRouter);
					empresa.getMisServicios().add(internet);

				} else if (rdbtnTelefonia.isSelected()) {

					if (!rbIlimitadoSi.isSelected() && !rbIlimitadoNo.isSelected()) {
						JOptionPane.showMessageDialog(null, "Debe indicar si las llamadas son ilimitadas.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					boolean llamadasIlimitadas  = rbIlimitadoSi.isSelected();
					int     minutosIncluidos    = (Integer) spnMinutosIncluidos.getValue();
					float   costoMinuto         = ((Double) spnCostoMinuto.getValue()).floatValue();

					if (!llamadasIlimitadas && costoMinuto <= 0) {
						JOptionPane.showMessageDialog(null, "Si las llamadas no son ilimitadas, el costo extra por minuto debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					ServicioTelefonia telefonia = new ServicioTelefonia("Telefonía", precio, minutosIncluidos, llamadasIlimitadas, costoMinuto);
					empresa.getMisServicios().add(telefonia);

				} else if (rdbtnTelevision.isSelected()) {

					if (!rbHDSi.isSelected() && !rbHDNo.isSelected()) {
						JOptionPane.showMessageDialog(null, "Debe indicar si el servicio cuenta con canales HD.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					int     canales  = (Integer) spnCanales.getValue();
					int     cajitas  = (Integer) spnCajitas.getValue();
					boolean tieneHD  = rbHDSi.isSelected();

					if (canales <= 0 || cajitas <= 0) {
						JOptionPane.showMessageDialog(null, "La cantidad de canales y cajitas debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					ServicioTelevision cable = new ServicioTelevision("Televisión", precio, canales, cajitas, tieneHD);
					empresa.getMisServicios().add(cable);
				}

				JOptionPane.showMessageDialog(null, "¡Servicio registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);

				txtID.setText("S - " + EmpresaAltice.getInstance().idServicios);
				spnDatos.setValue(0);
				spnMinutos.setValue(0);
				spnSms.setValue(0);
				spnVelocidad.setValue(0);
				spnMinutosIncluidos.setValue(0);
				spnCostoMinuto.setValue(0.0);
				spnCanales.setValue(0);
				spnCajitas.setValue(0);
				spnPrecio.setValue(0.0);

				bgRouter.clearSelection();
				bgIlimitado.clearSelection();
				bgHD.clearSelection();
				
				empresa.GuardarDatos(
						empresa.getMisClientes(), 
						empresa.getMisEmpleados(),
						empresa.getMisPlanes(), 
						empresa.getMisServicios(),
						empresa.getMisUsuarios(), 
						empresa.getMisContratos(),
						empresa.getPagos()
				);
			}
		});
		btnRegistrar.setActionCommand("OK");
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setActionCommand("Cancel");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);
	}
}