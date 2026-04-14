package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.Empleado;
import logico.EmpresaAltice;
import logico.Usuario;

public class RegistrarUsuario extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTextField txtNombreDeUsuarioEmp;
	private JPasswordField txtContraseniaUsuarioEmp;
	private JPasswordField txtConfirmarContrasenia;
	private JTextField txtDatosEmpleado;

	private Empleado empSeleccionado = null;
	private Usuario miUsuario = null;

	private JButton btnSeleccionarEmpleado;

	public RegistrarUsuario(Usuario user) {
		this.miUsuario = user;

		if (miUsuario != null) {
			setTitle("Actualizar Usuario del Sistema");
		} else {
			setTitle("Registrar Usuario del Sistema");
		}

		setSize(550, 480); // Ajusté la altura para acomodar el campo extra
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel(miUsuario != null ? "Actualizar Usuario" : "Registrar Nuevo Usuario");
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
				"Credenciales de Acceso", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrincipal.setLayout(null);
		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		JPanel panelEmp = new JPanel();
		panelEmp.setLayout(null);
		panelEmp.setBackground(ALTICE_LIGHT);
		panelEmp.setBorder(BorderFactory.createTitledBorder(new LineBorder(ALTICE_BORDER), "Vincular Empleado",
				TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelEmp.setBounds(20, 25, 475, 100);
		panelPrincipal.add(panelEmp);

		txtDatosEmpleado = new JTextField();
		txtDatosEmpleado.setEditable(false);
		txtDatosEmpleado.setFont(FONT_INPUT);
		txtDatosEmpleado.setBackground(Color.WHITE);
		txtDatosEmpleado.setText("Ningún empleado seleccionado...");
		txtDatosEmpleado.setBounds(15, 25, 445, 28);
		txtDatosEmpleado.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelEmp.add(txtDatosEmpleado);

		btnSeleccionarEmpleado = primaryButton("Seleccionar Empleado");
		btnSeleccionarEmpleado.setBounds(15, 60, 180, 28);
		btnSeleccionarEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmpleados listaEmps = new ListarEmpleados(true);
				listaEmps.setModal(true);
				listaEmps.setVisible(true);
				Empleado capturado = listaEmps.getEmpleadoSeleccionado();
				if (capturado != null) {
					if (capturado.getMiUsuario() != null) {
						JOptionPane.showMessageDialog(null, "Este empleado ya tiene un usuario asignado en el sistema.",
								"Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						empSeleccionado = capturado;
						txtDatosEmpleado.setText(empSeleccionado.getCodigoEmpleado() + " - "
								+ empSeleccionado.getNombre() + " - " + empSeleccionado.getRolEmpleado());
					}
				}
			}
		});
		panelEmp.add(btnSeleccionarEmpleado);

		JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario:");
		lblNombreDeUsuario.setFont(FONT_LABEL);
		lblNombreDeUsuario.setBounds(20, 140, 172, 22);
		panelPrincipal.add(lblNombreDeUsuario);

		txtNombreDeUsuarioEmp = new JTextField();
		txtNombreDeUsuarioEmp.setFont(FONT_INPUT);
		txtNombreDeUsuarioEmp.setBackground(ALTICE_LIGHT);
		txtNombreDeUsuarioEmp.setBounds(20, 165, 475, 28);
		txtNombreDeUsuarioEmp.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtNombreDeUsuarioEmp);

		JLabel lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setFont(FONT_LABEL);
		lblContrasea.setBounds(20, 210, 117, 22);
		panelPrincipal.add(lblContrasea);

		txtContraseniaUsuarioEmp = new JPasswordField();
		txtContraseniaUsuarioEmp.setFont(FONT_INPUT);
		txtContraseniaUsuarioEmp.setBackground(ALTICE_LIGHT);
		txtContraseniaUsuarioEmp.setBounds(20, 235, 225, 28);
		txtContraseniaUsuarioEmp.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtContraseniaUsuarioEmp);

		JLabel lblConfirmar = new JLabel("Confirmar Contraseña:");
		lblConfirmar.setFont(FONT_LABEL);
		lblConfirmar.setBounds(270, 210, 160, 22);
		panelPrincipal.add(lblConfirmar);

		txtConfirmarContrasenia = new JPasswordField();
		txtConfirmarContrasenia.setFont(FONT_INPUT);
		txtConfirmarContrasenia.setBackground(ALTICE_LIGHT);
		txtConfirmarContrasenia.setBounds(270, 235, 225, 28);
		txtConfirmarContrasenia.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtConfirmarContrasenia);

		// Checkbox para mostrar contraseñas
		JCheckBox chkMostrarContrasena = new JCheckBox("Mostrar contraseñas");
		chkMostrarContrasena.setFont(new Font("SansSerif", Font.PLAIN, 12));
		chkMostrarContrasena.setBackground(Color.WHITE);
		chkMostrarContrasena.setBounds(20, 270, 180, 20);
		chkMostrarContrasena.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkMostrarContrasena.isSelected()) {
					txtContraseniaUsuarioEmp.setEchoChar((char) 0);
					txtConfirmarContrasenia.setEchoChar((char) 0);
				} else {
					txtContraseniaUsuarioEmp.setEchoChar('\u2022'); // Punto grueso
					txtConfirmarContrasenia.setEchoChar('\u2022');
				}
			}
		});
		panelPrincipal.add(chkMostrarContrasena);

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = primaryButton(miUsuario != null ? "Actualizar" : "Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombreUsuario = txtNombreDeUsuarioEmp.getText().trim();
				String contrasenia = new String(txtContraseniaUsuarioEmp.getPassword()).trim();
				String confirmar = new String(txtConfirmarContrasenia.getPassword()).trim();

				if (nombreUsuario.isEmpty() || contrasenia.isEmpty() || confirmar.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Debe llenar todos los campos (usuario, contraseña y confirmación).",
							"Faltan datos", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!contrasenia.equals(confirmar)) {
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden. Por favor, verifique.",
							"Error de coincidencia", JOptionPane.ERROR_MESSAGE);
					return;
				}

				EmpresaAltice empresa = EmpresaAltice.getInstance();

				for (Usuario u : empresa.getMisUsuarios()) {
					if (miUsuario != null && u.getIdUsuario().equals(miUsuario.getIdUsuario()))
						continue;
					if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
						JOptionPane.showMessageDialog(null,
								"Ya existe un usuario registrado con ese nombre de usuario.", "Usuario duplicado",
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}

				if (miUsuario == null) {
					if (empSeleccionado == null) {
						JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para asignarle el usuario.",
								"Faltan datos", JOptionPane.WARNING_MESSAGE);
						return;
					}
					String rol = empSeleccionado.getRolEmpleado();
					Usuario usr = new Usuario(rol, nombreUsuario, contrasenia);
					empSeleccionado.setMiUsuario(usr);
					empresa.getMisUsuarios().add(usr);
					JOptionPane.showMessageDialog(null, "Usuario registrado y vinculado al empleado con éxito.",
							"Información", JOptionPane.INFORMATION_MESSAGE);
				} else {
					miUsuario.setNombreUsuario(nombreUsuario);
					miUsuario.setContrasenia(contrasenia);
					JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.", "Información",
							JOptionPane.INFORMATION_MESSAGE);
				}

				empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
						empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
						empresa.getPagos());
				dispose();
			}
		});
		buttonPane.add(btnRegistrar);
		getRootPane().setDefaultButton(btnRegistrar);

		JButton btnCancelar = outlineButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		loadUsuario();
	}

	private void loadUsuario() {
		if (miUsuario != null) {
			txtNombreDeUsuarioEmp.setText(miUsuario.getNombreUsuario());
			txtContraseniaUsuarioEmp.setText(miUsuario.getContrasenia());
			txtConfirmarContrasenia.setText(miUsuario.getContrasenia()); // Prellena la confirmación
			btnSeleccionarEmpleado.setEnabled(false);
			for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
				if (emp.getMiUsuario() != null) {
					if (emp.getMiUsuario().getIdUsuario().equals(miUsuario.getIdUsuario())) {
						empSeleccionado = emp;
						txtDatosEmpleado.setText(empSeleccionado.getCodigoEmpleado() + " - "
								+ empSeleccionado.getNombre() + " - " + empSeleccionado.getRolEmpleado());
						break;
					}
				}
			}
		}
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