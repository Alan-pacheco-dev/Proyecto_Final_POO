package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.Cliente;
import logico.EmpresaAltice;

public class RegistrarCliente extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private Cliente miCliente = null;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtCodigoCliente;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtCedulaRNC;
	private JLabel lblCedulaRNC;

	private JRadioButton rdbtnSiEsEmpresa;
	private JRadioButton rdbtnNoEsEmpresa;

	public RegistrarCliente(Cliente cli) {
		this.miCliente = cli;

		if (miCliente != null) {
			setTitle("Actualizar Cliente");
		} else {
			setTitle("Registrar Cliente");
		}

		setSize(550, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		// Header
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel(miCliente != null ? "Actualizar Datos del Cliente" : "Registrar Nuevo Cliente");
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
				"Información del Cliente", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrincipal.setLayout(null);
		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		// Componentes del formulario
		JLabel lblNewLabel = new JLabel("ID Persona:");
		lblNewLabel.setFont(FONT_LABEL);
		lblNewLabel.setBounds(25, 30, 100, 22);
		panelPrincipal.add(lblNewLabel);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(FONT_INPUT);
		txtID.setBackground(ALTICE_LIGHT);
		txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
		txtID.setBounds(25, 55, 200, 28);
		txtID.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtID);

		JLabel lblCdigoDelCliente = new JLabel("Código del Cliente:");
		lblCdigoDelCliente.setFont(FONT_LABEL);
		lblCdigoDelCliente.setBounds(245, 30, 200, 22);
		panelPrincipal.add(lblCdigoDelCliente);

		txtCodigoCliente = new JTextField();
		txtCodigoCliente.setEditable(false);
		txtCodigoCliente.setFont(FONT_INPUT);
		txtCodigoCliente.setBackground(ALTICE_LIGHT);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigoCliente.setText("CLI-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
		txtCodigoCliente.setBounds(245, 55, 200, 28);
		txtCodigoCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtCodigoCliente);

		JLabel lblNombreDelEmpleado = new JLabel("Nombre Completo:");
		lblNombreDelEmpleado.setFont(FONT_LABEL);
		lblNombreDelEmpleado.setBounds(25, 95, 200, 22);
		panelPrincipal.add(lblNombreDelEmpleado);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setFont(FONT_INPUT);
		txtNombreCliente.setBounds(25, 120, 420, 28);
		txtNombreCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtNombreCliente);

		lblCedulaRNC = new JLabel("Cédula:");
		lblCedulaRNC.setFont(FONT_LABEL);
		lblCedulaRNC.setBounds(25, 160, 200, 22);
		panelPrincipal.add(lblCedulaRNC);

		txtCedulaRNC = new JTextField();
		txtCedulaRNC.setFont(FONT_INPUT);
		txtCedulaRNC.setBounds(25, 185, 420, 28);
		txtCedulaRNC.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtCedulaRNC);

		JLabel lblEmail = new JLabel("Email de contacto:");
		lblEmail.setFont(FONT_LABEL);
		lblEmail.setBounds(25, 225, 200, 22);
		panelPrincipal.add(lblEmail);

		txtEmailCliente = new JTextField();
		txtEmailCliente.setFont(FONT_INPUT);
		txtEmailCliente.setBounds(25, 250, 420, 28);
		txtEmailCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtEmailCliente);

		JLabel lblDireccin = new JLabel("Dirección Residencial:");
		lblDireccin.setFont(FONT_LABEL);
		lblDireccin.setBounds(25, 290, 200, 22);
		panelPrincipal.add(lblDireccin);

		txtDireccionCliente = new JTextField();
		txtDireccionCliente.setFont(FONT_INPUT);
		txtDireccionCliente.setBounds(25, 315, 420, 28);
		txtDireccionCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtDireccionCliente);

		JLabel lblEsEmpresa = new JLabel("¿Es Cliente Corporativo?");
		lblEsEmpresa.setFont(FONT_LABEL);
		lblEsEmpresa.setBounds(25, 355, 200, 22);
		panelPrincipal.add(lblEsEmpresa);

		rdbtnSiEsEmpresa = new JRadioButton("Sí (Empresa)");
		rdbtnSiEsEmpresa.setBackground(Color.WHITE);
		rdbtnSiEsEmpresa.setBounds(25, 380, 110, 23);
		panelPrincipal.add(rdbtnSiEsEmpresa);

		rdbtnNoEsEmpresa = new JRadioButton("No (Físico)");
		rdbtnNoEsEmpresa.setSelected(true);
		rdbtnNoEsEmpresa.setBackground(Color.WHITE);
		rdbtnNoEsEmpresa.setBounds(140, 380, 110, 23);
		panelPrincipal.add(rdbtnNoEsEmpresa);

		ButtonGroup bgEmpresa = new ButtonGroup();
		bgEmpresa.add(rdbtnSiEsEmpresa);
		bgEmpresa.add(rdbtnNoEsEmpresa);

		JLabel lblDeuda = new JLabel("Deuda Pendiente ($):");
		lblDeuda.setFont(FONT_LABEL);
		lblDeuda.setBounds(285, 355, 200, 22);
		panelPrincipal.add(lblDeuda);

		txtDeudaCliente = new JTextField();
		txtDeudaCliente.setText("0.00");
		txtDeudaCliente.setEditable(false);
		txtDeudaCliente.setFont(FONT_INPUT);
		txtDeudaCliente.setBackground(ALTICE_LIGHT);
		txtDeudaCliente.setBounds(285, 380, 160, 28);
		txtDeudaCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtDeudaCliente);

		// Eventos de RadioButtons
		rdbtnSiEsEmpresa.addActionListener(e -> {
			lblCedulaRNC.setText("RNC:");
			updateCodigo(true);
		});

		rdbtnNoEsEmpresa.addActionListener(e -> {
			lblCedulaRNC.setText("Cédula:");
			updateCodigo(false);
		});

		// Panel de Botones
		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = primaryButton(miCliente != null ? "Actualizar" : "Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombreCliente.getText().trim();
				String direccion = txtDireccionCliente.getText().trim();
				String email = txtEmailCliente.getText().trim();
				String cedulaOrRNC = txtCedulaRNC.getText().trim();
				boolean esEmpresa = rdbtnSiEsEmpresa.isSelected();
				String codigo = txtCodigoCliente.getText();

				if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || cedulaOrRNC.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe completar todos los campos.", "Faltan datos",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!email.contains("@") || !email.contains(".")) {
					JOptionPane.showMessageDialog(null, "Ingrese un formato de email válido.", "Email inválido",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!cedulaOrRNC.matches("[0-9]+")) {
					JOptionPane.showMessageDialog(null, "El documento solo debe contener números.", "Formato inválido",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (esEmpresa && cedulaOrRNC.length() != 9) {
					JOptionPane.showMessageDialog(null, "El RNC debe tener 9 dígitos.", "Longitud inválida",
							JOptionPane.WARNING_MESSAGE);
					return;
				} else if (!esEmpresa && cedulaOrRNC.length() != 11) {
					JOptionPane.showMessageDialog(null, "La cédula debe tener 11 dígitos.", "Longitud inválida",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				EmpresaAltice empresa = EmpresaAltice.getInstance();

				for (Cliente c : empresa.getMisClientes()) {
					if (miCliente != null && c.getIdPersona().equals(miCliente.getIdPersona()))
						continue;
					if (c.getRnc().equals(cedulaOrRNC)) {
						JOptionPane.showMessageDialog(null, "Ya existe un cliente con este documento.",
								"Documento duplicado", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}

				if (miCliente == null) {
					Cliente cli = new Cliente(null, nombre, direccion, email, 0, esEmpresa, cedulaOrRNC);
					cli.setCodigoCliente(codigo);
					empresa.getMisClientes().add(cli);
					JOptionPane.showMessageDialog(null, "Cliente registrado con éxito.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					clean();
				} else {
					miCliente.setNombre(nombre);
					miCliente.setDireccion(direccion);
					miCliente.setEmail(email);
					miCliente.setRnc(cedulaOrRNC);
					miCliente.setEsEmpresa(esEmpresa);
					miCliente.setCodigoCliente(codigo);
					JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.", "Éxito",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}

				empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
						empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
						empresa.getPagos());
			}
		});
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);

		JButton btnCancelar = outlineButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());
		buttonPane.add(btnCancelar);

		loadCliente();
	}

	private void updateCodigo(boolean esEmpresa) {
		String anio = String.valueOf(LocalDate.now().getYear());
		String idActual = EmpresaAltice.getInstance().idClientes + "";
		if (miCliente != null) {
			idActual = miCliente.getIdPersona().replace("C - ", "");
		}
		String prefijo = esEmpresa ? "CLI-EMPR-" : "CLI-FIS-";
		txtCodigoCliente.setText(prefijo + anio + "-" + idActual);
	}

	private void loadCliente() {
		if (miCliente != null) {
			txtID.setText(miCliente.getIdPersona());
			txtCodigoCliente.setText(miCliente.getCodigoCliente());
			txtNombreCliente.setText(miCliente.getNombre());
			txtEmailCliente.setText(miCliente.getEmail());
			txtDireccionCliente.setText(miCliente.getDireccion());
			txtCedulaRNC.setText(miCliente.getRnc());
			txtDeudaCliente.setText(String.format("%.2f", miCliente.getDeuda()));

			if (miCliente.isEsEmpresa()) {
				rdbtnSiEsEmpresa.setSelected(true);
				lblCedulaRNC.setText("RNC:");
			} else {
				rdbtnNoEsEmpresa.setSelected(true);
				lblCedulaRNC.setText("Cédula:");
			}
		}
	}

	private void clean() {
		txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
		updateCodigo(false);
		txtNombreCliente.setText("");
		txtDireccionCliente.setText("");
		txtEmailCliente.setText("");
		txtCedulaRNC.setText("");
		rdbtnNoEsEmpresa.setSelected(true);
		lblCedulaRNC.setText("Cédula:");
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