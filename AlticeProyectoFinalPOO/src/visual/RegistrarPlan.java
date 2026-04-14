package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.Plan;
import logico.Servicio;

public class RegistrarPlan extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextArea txtDescripcionPlan;
	private JTextField txtPrecioTotalPlan;
	private JTextField txtEstado;
	private JTextField txtNombrePlan;

	private JRadioButton rdbtnInternet, rdbtnMovil, rdbtnTelefonia, rdbtnTelevision;
	private ArrayList<Servicio> serviciosEscogidos;

	public static void main(String[] args) {
		try {
			RegistrarPlan dialog = new RegistrarPlan();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarPlan() {
		setTitle("Configuración de Plan Comercial");
		setSize(680, 780);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Registrar Plan Comercial");
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

		serviciosEscogidos = new ArrayList<>();

		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Color.WHITE);
		panelPrincipal.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY),
				"Datos Generales del Plan", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrincipal.setLayout(null);
		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		JLabel lblID = new JLabel("ID Plan:");
		lblID.setFont(FONT_LABEL);
		lblID.setBounds(25, 35, 100, 22);
		panelPrincipal.add(lblID);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(FONT_INPUT);
		txtID.setBackground(ALTICE_LIGHT);
		txtID.setText("P - " + EmpresaAltice.getInstance().idPlanes);
		txtID.setBounds(25, 60, 180, 30);
		txtID.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtID);

		JLabel lblEst = new JLabel("Estado:");
		lblEst.setFont(FONT_LABEL);
		lblEst.setBounds(425, 35, 100, 22);
		panelPrincipal.add(lblEst);

		txtEstado = new JTextField("En Construcción");
		txtEstado.setEditable(false);
		txtEstado.setFont(FONT_INPUT);
		txtEstado.setBackground(ALTICE_LIGHT);
		txtEstado.setBounds(425, 60, 185, 30);
		txtEstado.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtEstado);

		JLabel lblNom = new JLabel("Nombre Comercial del Plan:");
		lblNom.setFont(FONT_LABEL);
		lblNom.setBounds(25, 110, 300, 22);
		panelPrincipal.add(lblNom);

		txtNombrePlan = new JTextField();
		txtNombrePlan.setFont(FONT_INPUT);
		txtNombrePlan.setBounds(25, 135, 585, 30);
		txtNombrePlan.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtNombrePlan);

		JLabel lblDesc = new JLabel("Descripción Detallada:");
		lblDesc.setFont(FONT_LABEL);
		lblDesc.setBounds(25, 185, 300, 22);
		panelPrincipal.add(lblDesc);

		txtDescripcionPlan = new JTextArea();
		txtDescripcionPlan.setFont(FONT_INPUT);
		txtDescripcionPlan.setLineWrap(true);
		txtDescripcionPlan.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(txtDescripcionPlan);
		scroll.setBounds(25, 210, 585, 80);
		scroll.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(scroll);

		JPanel panelServicios = new JPanel();
		panelServicios.setLayout(null);
		panelServicios.setBackground(ALTICE_LIGHT);
		panelServicios.setBorder(BorderFactory.createTitledBorder(new LineBorder(ALTICE_BORDER), "Gestión de Servicios",
				TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelServicios.setBounds(25, 315, 585, 140);
		panelPrincipal.add(panelServicios);

		JButton btnCrear = outlineButton("Crear Servicio");
		btnCrear.setBounds(20, 30, 150, 30);
		btnCrear.addActionListener(e -> {
			RegistrarServicio regisServi = new RegistrarServicio();
			regisServi.setModal(true);
			regisServi.setVisible(true);
		});
		panelServicios.add(btnCrear);

		JButton btnVincular = primaryButton("Vincular Servicio");
		btnVincular.setBounds(180, 30, 180, 30);
		panelServicios.add(btnVincular);

		JButton btnVer = outlineButton("Ver Detalles");
		btnVer.setBounds(370, 30, 150, 30);
		panelServicios.add(btnVer);

		rdbtnInternet = crearIndicador("Internet", 20, 85, panelServicios);
		rdbtnMovil = crearIndicador("Móvil", 150, 85, panelServicios);
		rdbtnTelefonia = crearIndicador("Telefonía", 260, 85, panelServicios);
		rdbtnTelevision = crearIndicador("TV", 390, 85, panelServicios);

		JLabel lblPrecio = new JLabel("Costo Total del Plan (Impuestos Incl.):");
		lblPrecio.setFont(FONT_LABEL);
		lblPrecio.setBounds(25, 480, 350, 22);
		panelPrincipal.add(lblPrecio);

		txtPrecioTotalPlan = new JTextField("$ 0.00");
		txtPrecioTotalPlan.setEditable(false);
		txtPrecioTotalPlan.setFont(new Font("SansSerif", Font.BOLD, 22));
		txtPrecioTotalPlan.setForeground(ALTICE_BLUE);
		txtPrecioTotalPlan.setBackground(ALTICE_LIGHT);
		txtPrecioTotalPlan.setHorizontalAlignment(JTextField.CENTER);
		txtPrecioTotalPlan.setBounds(25, 505, 250, 45);
		txtPrecioTotalPlan.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		panelPrincipal.add(txtPrecioTotalPlan);

		JTextPane hintText = new JTextPane();
		hintText.setEditable(false);
		hintText.setFont(new Font("SansSerif", Font.ITALIC, 11));
		hintText.setForeground(Color.GRAY);
		hintText.setText("* El precio se recalcula dinámicamente según los servicios agregados. Incluye ITBIS (18%).");
		hintText.setBounds(25, 560, 585, 30);
		panelPrincipal.add(hintText);

		btnVincular.addActionListener(e -> {
			ListarServicios listar = new ListarServicios("Todos", true);
			listar.setVisible(true);
			if (listar.getServicioSeleccionado() != null) {
				agregarServicio(listar.getServicioSeleccionado());
			}
		});

		btnVer.addActionListener(e -> {
			if (serviciosEscogidos.isEmpty()) {
				JOptionPane.showMessageDialog(null, "No hay servicios vinculados.", "Aviso",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				new ListarServiciosXPlan(serviciosEscogidos).setVisible(true);
			}
		});

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = primaryButton("Guardar Plan");
		btnRegistrar.addActionListener(e -> guardarPlan());
		buttonPane.add(btnRegistrar);

		JButton btnCancelar = outlineButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);
	}

	private JRadioButton crearIndicador(String texto, int x, int y, JPanel p) {
		JRadioButton rb = new JRadioButton(texto);
		rb.setFont(FONT_INPUT);
		rb.setBackground(ALTICE_LIGHT);
		rb.setBounds(x, y, 110, 25);
		rb.setEnabled(false);
		p.add(rb);
		return rb;
	}

	private void agregarServicio(Servicio s) {
		serviciosEscogidos.removeIf(ser -> ser.getTipoServicio().equalsIgnoreCase(s.getTipoServicio()));
		serviciosEscogidos.add(s);
		actualizarUI();
	}

	private void actualizarUI() {
		rdbtnInternet.setSelected(false);
		rdbtnMovil.setSelected(false);
		rdbtnTelefonia.setSelected(false);
		rdbtnTelevision.setSelected(false);

		float subtotal = 0;
		for (Servicio s : serviciosEscogidos) {
			subtotal += s.getPrecioServicio();
			if (s.getTipoServicio().equalsIgnoreCase("Internet"))
				rdbtnInternet.setSelected(true);
			if (s.getTipoServicio().equalsIgnoreCase("Móvil"))
				rdbtnMovil.setSelected(true);
			if (s.getTipoServicio().equalsIgnoreCase("Telefonía"))
				rdbtnTelefonia.setSelected(true);
			if (s.getTipoServicio().equalsIgnoreCase("Televisión"))
				rdbtnTelevision.setSelected(true);
		}

		float total = (float) (Math.round((subtotal * 1.18f) * 100.0) / 100.0);
		txtPrecioTotalPlan.setText("$ " + String.format("%.2f", total));
	}

	private void guardarPlan() {
		String nombre = txtNombrePlan.getText().trim();
		if (nombre.isEmpty() || serviciosEscogidos.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Complete el nombre y vincule al menos un servicio.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		EmpresaAltice emp = EmpresaAltice.getInstance();
		Plan nuevo = new Plan(nombre, txtDescripcionPlan.getText(), 0.0f);
		nuevo.setServiciosPlan(serviciosEscogidos);
		nuevo.setPrecioTotal(nuevo.calcularPrecioTotal());

		emp.getMisPlanes().add(nuevo);
		emp.GuardarDatos(emp.getMisClientes(), emp.getMisEmpleados(), emp.getMisPlanes(), emp.getMisServicios(),
				emp.getMisUsuarios(), emp.getMisContratos(), emp.getPagos());

		JOptionPane.showMessageDialog(null, "Plan registrado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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