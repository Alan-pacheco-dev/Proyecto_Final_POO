package visual;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class RegistrarServicio extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtEstado;
	private JSpinner spnPrecio;

	private JPanel panelCard;
	private CardLayout cardLayout;

	private JSpinner spnDatos, spnMinutos, spnSms;
	private JSpinner spnVelocidad, spnMinutosIncluidos, spnCostoMinuto;
	private JSpinner spnCanales, spnCajitas;
	private JRadioButton rbRouterSi, rbIlimitadoSi, rbHDSi;

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
		setTitle("Configuración de Nuevo Servicio");
		setSize(580, 700);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Registrar Nuevo Servicio");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(Color.WHITE);
		JLabel lblAltice = new JLabel("altice");
		lblAltice.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblAltice.setForeground(new Color(255, 255, 255, 160));
		header.add(lblTitulo, BorderLayout.WEST);
		header.add(lblAltice, BorderLayout.EAST);
		getContentPane().add(header, BorderLayout.NORTH);

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(15, 15, 5, 15));
		contentPanel.setLayout(new BorderLayout());
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.WHITE);
		panelPrincipal.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY),
				"Definición Técnica", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrincipal.setLayout(null);
		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		JLabel lblID = new JLabel("ID Servicio:");
		lblID.setFont(FONT_LABEL);
		lblID.setBounds(25, 35, 120, 22);
		panelPrincipal.add(lblID);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(FONT_INPUT);
		txtID.setBackground(ALTICE_LIGHT);
		txtID.setText("S - " + EmpresaAltice.getInstance().idServicios);
		txtID.setBounds(25, 60, 160, 30);
		txtID.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtID);

		JLabel lblEstadoLabel = new JLabel("Estado:");
		lblEstadoLabel.setFont(FONT_LABEL);
		lblEstadoLabel.setBounds(345, 35, 120, 22);
		panelPrincipal.add(lblEstadoLabel);

		txtEstado = new JTextField("En Definición");
		txtEstado.setEditable(false);
		txtEstado.setFont(FONT_INPUT);
		txtEstado.setBackground(ALTICE_LIGHT);
		txtEstado.setBounds(345, 60, 175, 30);
		txtEstado.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtEstado);

		JPanel panelTipo = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
		panelTipo.setBackground(ALTICE_LIGHT);
		panelTipo.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelTipo.setBounds(25, 110, 495, 50);
		panelPrincipal.add(panelTipo);

		JRadioButton rdbtnMovil = new JRadioButton("Móvil");
		rdbtnMovil.setBackground(ALTICE_LIGHT);
		rdbtnMovil.setFont(FONT_LABEL);
		JRadioButton rdbtnInternet = new JRadioButton("Internet");
		rdbtnInternet.setBackground(ALTICE_LIGHT);
		rdbtnInternet.setFont(FONT_LABEL);
		JRadioButton rdbtnTelefonia = new JRadioButton("Telefonía");
		rdbtnTelefonia.setBackground(ALTICE_LIGHT);
		rdbtnTelefonia.setFont(FONT_LABEL);
		JRadioButton rdbtnTelevision = new JRadioButton("TV");
		rdbtnTelevision.setBackground(ALTICE_LIGHT);
		rdbtnTelevision.setFont(FONT_LABEL);

		ButtonGroup bgTipo = new ButtonGroup();
		bgTipo.add(rdbtnMovil);
		bgTipo.add(rdbtnInternet);
		bgTipo.add(rdbtnTelefonia);
		bgTipo.add(rdbtnTelevision);
		panelTipo.add(rdbtnMovil);
		panelTipo.add(rdbtnInternet);
		panelTipo.add(rdbtnTelefonia);
		panelTipo.add(rdbtnTelevision);

		cardLayout = new CardLayout();
		panelCard = new JPanel(cardLayout);
		panelCard.setBounds(25, 180, 495, 200);
		panelCard.setBackground(Color.WHITE);
		panelPrincipal.add(panelCard);

		panelCard.add(crearPanelMovil(), "MOVIL");
		panelCard.add(crearPanelInternet(), "INTERNET");
		panelCard.add(crearPanelTelefonia(), "TELEFONIA");
		panelCard.add(crearPanelTV(), "TV");

		JPanel panelPrecio = new JPanel();
		panelPrecio.setLayout(null);
		panelPrecio.setBackground(ALTICE_LIGHT);
		panelPrecio.setBorder(BorderFactory.createTitledBorder(new LineBorder(ALTICE_BORDER), "Costo del Servicio",
				TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrecio.setBounds(25, 400, 495, 90);
		panelPrincipal.add(panelPrecio);

		JLabel lblMonto = new JLabel("Precio Base Mensual ($):");
		lblMonto.setFont(FONT_LABEL);
		lblMonto.setBounds(20, 40, 180, 22);
		panelPrecio.add(lblMonto);

		spnPrecio = new JSpinner(new SpinnerNumberModel(0.0, 0.0, null, 100.0));
		spnPrecio.setFont(new Font("SansSerif", Font.BOLD, 16));
		spnPrecio.setBounds(200, 35, 180, 35);
		panelPrecio.add(spnPrecio);

		rdbtnMovil.addActionListener(e -> cardLayout.show(panelCard, "MOVIL"));
		rdbtnInternet.addActionListener(e -> cardLayout.show(panelCard, "INTERNET"));
		rdbtnTelefonia.addActionListener(e -> cardLayout.show(panelCard, "TELEFONIA"));
		rdbtnTelevision.addActionListener(e -> cardLayout.show(panelCard, "TV"));

		rdbtnMovil.setSelected(true);
		cardLayout.show(panelCard, "MOVIL");

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = primaryButton("Registrar Servicio");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ejecutarRegistro(rdbtnMovil, rdbtnInternet, rdbtnTelefonia, rdbtnTelevision);
			}
		});
		buttonPane.add(btnRegistrar);

		JButton btnCancelar = outlineButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);
	}

	private JPanel crearPanelMovil() {
		JPanel p = crearPanelBaseCard("Especificaciones de Red Móvil");
		spnDatos = agregarCampoSpinner(p, "Datos (GB):", 20, 60);
		spnMinutos = agregarCampoSpinner(p, "Minutos:", 170, 60);
		spnSms = agregarCampoSpinner(p, "SMS:", 320, 60);
		return p;
	}

	private JPanel crearPanelInternet() {
		JPanel p = crearPanelBaseCard("Especificaciones de Banda Ancha");
		spnVelocidad = agregarCampoSpinner(p, "Velocidad (Mbps):", 20, 60);

		JLabel lbl = new JLabel("¿Incluye Router?");
		lbl.setFont(FONT_LABEL);
		lbl.setBounds(250, 40, 150, 22);
		p.add(lbl);
		rbRouterSi = new JRadioButton("Si");
		rbRouterSi.setBackground(ALTICE_LIGHT);
		rbRouterSi.setBounds(250, 65, 50, 25);
		JRadioButton rbNo = new JRadioButton("No");
		rbNo.setBackground(ALTICE_LIGHT);
		rbNo.setBounds(310, 65, 50, 25);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbRouterSi);
		bg.add(rbNo);
		p.add(rbRouterSi);
		p.add(rbNo);
		return p;
	}

	private JPanel crearPanelTelefonia() {
		JPanel p = crearPanelBaseCard("Especificaciones Voz Fija");
		spnMinutosIncluidos = agregarCampoSpinner(p, "Minutos Incl.:", 20, 60);
		spnCostoMinuto = agregarCampoSpinner(p, "Costo Min. Extra:", 320, 60);

		JLabel lbl = new JLabel("Llamadas Ilimitadas:");
		lbl.setFont(FONT_LABEL);
		lbl.setBounds(160, 40, 150, 22);
		p.add(lbl);
		rbIlimitadoSi = new JRadioButton("Si");
		rbIlimitadoSi.setBackground(ALTICE_LIGHT);
		rbIlimitadoSi.setBounds(160, 65, 50, 25);
		JRadioButton rbNo = new JRadioButton("No");
		rbNo.setBackground(ALTICE_LIGHT);
		rbNo.setBounds(220, 65, 50, 25);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbIlimitadoSi);
		bg.add(rbNo);
		p.add(rbIlimitadoSi);
		p.add(rbNo);
		return p;
	}

	private JPanel crearPanelTV() {
		JPanel p = crearPanelBaseCard("Especificaciones de Televisión");
		spnCanales = agregarCampoSpinner(p, "Cant. Canales:", 20, 60);
		spnCajitas = agregarCampoSpinner(p, "Cajitas Incl.:", 170, 60);

		JLabel lbl = new JLabel("¿Soporta HD?");
		lbl.setFont(FONT_LABEL);
		lbl.setBounds(330, 40, 100, 22);
		p.add(lbl);
		rbHDSi = new JRadioButton("Si");
		rbHDSi.setBackground(ALTICE_LIGHT);
		rbHDSi.setBounds(330, 65, 50, 25);
		JRadioButton rbNo = new JRadioButton("No");
		rbNo.setBackground(ALTICE_LIGHT);
		rbNo.setBounds(390, 65, 50, 25);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbHDSi);
		bg.add(rbNo);
		p.add(rbHDSi);
		p.add(rbNo);
		return p;
	}

	private JPanel crearPanelBaseCard(String titulo) {
		JPanel p = new JPanel(null);
		p.setBackground(ALTICE_LIGHT);
		p.setBorder(BorderFactory.createTitledBorder(new LineBorder(ALTICE_BORDER), titulo, TitledBorder.LEADING,
				TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		return p;
	}

	private JSpinner agregarCampoSpinner(JPanel p, String label, int x, int y) {
		JLabel lbl = new JLabel(label);
		lbl.setFont(FONT_LABEL);
		lbl.setBounds(x, 40, 120, 22);
		p.add(lbl);
		JSpinner sp = new JSpinner(new SpinnerNumberModel(0, 0, null, 1));
		sp.setBounds(x, 65, 110, 28);
		p.add(sp);
		return sp;
	}

	private void ejecutarRegistro(JRadioButton m, JRadioButton i, JRadioButton t, JRadioButton tv) {
		float precio = ((Double) spnPrecio.getValue()).floatValue();
		if (precio <= 0) {
			JOptionPane.showMessageDialog(null, "El precio debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		EmpresaAltice emp = EmpresaAltice.getInstance();
		if (m.isSelected()) {
			emp.getMisServicios().add(new ServicioMovil("Móvil", precio, (Integer) spnDatos.getValue(),
					(Integer) spnMinutos.getValue(), (Integer) spnSms.getValue()));
		} else if (i.isSelected()) {
			emp.getMisServicios().add(new ServicioInternet("Internet", precio, (Integer) spnVelocidad.getValue(),
					rbRouterSi.isSelected()));
		} else if (t.isSelected()) {
			emp.getMisServicios()
					.add(new ServicioTelefonia("Telefonía", precio, (Integer) spnMinutosIncluidos.getValue(),
							rbIlimitadoSi.isSelected(), ((Double) spnCostoMinuto.getValue()).floatValue()));
		} else if (tv.isSelected()) {
			emp.getMisServicios().add(new ServicioTelevision("Televisión", precio, (Integer) spnCanales.getValue(),
					(Integer) spnCajitas.getValue(), rbHDSi.isSelected()));
		}

		emp.GuardarDatos(emp.getMisClientes(), emp.getMisEmpleados(), emp.getMisPlanes(), emp.getMisServicios(),
				emp.getMisUsuarios(), emp.getMisContratos(), emp.getPagos());
		JOptionPane.showMessageDialog(null, "Servicio registrado correctamente.", "Éxito",
				JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(Color.WHITE);
		btn.setBackground(ALTICE_BLUE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(ALTICE_BLUE);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}