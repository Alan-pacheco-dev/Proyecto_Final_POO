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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.Empleado;
import logico.EmpresaAltice;

public class RegistrarEmpleado extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtCedula;
	private JSpinner spnSalario;
	private JSpinner spnComisiones;
	private JComboBox<String> cbxRol;

	private Empleado miEmpleado = null;

	public static void main(String[] args) {
		try {
			RegistrarEmpleado dialog = new RegistrarEmpleado(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RegistrarEmpleado(Empleado empleado) {
		this.miEmpleado = empleado;

		if (miEmpleado == null) {
			setTitle("Registrar Nuevo Empleado");
		} else {
			setTitle("Actualizar Datos del Empleado");
		}

		setSize(500, 520);
		setLocationRelativeTo(null);
		setResizable(false);
		setModal(true);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel(miEmpleado != null ? "Actualizar Empleado" : "Registrar Nuevo Empleado");
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
				"Información Personal y Laboral", TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE));
		panelPrincipal.setLayout(null);
		contentPanel.add(panelPrincipal, BorderLayout.CENTER);

		JLabel lblId = new JLabel("ID Persona:");
		lblId.setFont(FONT_LABEL);
		lblId.setBounds(25, 30, 150, 22);
		panelPrincipal.add(lblId);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setFont(FONT_INPUT);
		txtId.setBackground(ALTICE_LIGHT);
		txtId.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
		txtId.setBounds(25, 55, 200, 28);
		txtId.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtId);

		JLabel lblCodigo = new JLabel("Código Empleado:");
		lblCodigo.setFont(FONT_LABEL);
		lblCodigo.setBounds(245, 30, 150, 22);
		panelPrincipal.add(lblCodigo);

		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setFont(FONT_INPUT);
		txtCodigo.setBackground(ALTICE_LIGHT);
		txtCodigo.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
		txtCodigo.setBounds(245, 55, 200, 28);
		txtCodigo.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtCodigo);

		JLabel lblNombre = new JLabel("Nombre y Apellidos:");
		lblNombre.setFont(FONT_LABEL);
		lblNombre.setBounds(25, 95, 200, 22);
		panelPrincipal.add(lblNombre);

		txtNombre = new JTextField();
		txtNombre.setFont(FONT_INPUT);
		txtNombre.setBounds(25, 120, 420, 28);
		txtNombre.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtNombre);

		JLabel lblCedula = new JLabel("Cédula:");
		lblCedula.setFont(FONT_LABEL);
		lblCedula.setBounds(25, 160, 150, 22);
		panelPrincipal.add(lblCedula);

		txtCedula = new JTextField();
		txtCedula.setFont(FONT_INPUT);
		txtCedula.setBounds(25, 185, 420, 28);
		txtCedula.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(txtCedula);

		JLabel lblRol = new JLabel("Rol del Empleado:");
		lblRol.setFont(FONT_LABEL);
		lblRol.setBounds(25, 225, 150, 22);
		panelPrincipal.add(lblRol);

		cbxRol = new JComboBox<String>();
		cbxRol.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Seleccione...", "Administrativo", "Vendedor", "Tecnico" }));
		cbxRol.setFont(FONT_INPUT);
		cbxRol.setBackground(Color.WHITE);
		cbxRol.setBounds(25, 250, 420, 28);
		panelPrincipal.add(cbxRol);

		JLabel lblSalario = new JLabel("Salario Base ($):");
		lblSalario.setFont(FONT_LABEL);
		lblSalario.setBounds(25, 290, 150, 22);
		panelPrincipal.add(lblSalario);

		spnSalario = new JSpinner();
		spnSalario.setModel(new SpinnerNumberModel(0.0d, 0.0d, 1000000.0d, 500.0d));
		spnSalario.setBounds(25, 315, 200, 28);
		spnSalario.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(spnSalario);

		JLabel lblComisiones = new JLabel("Comisión (%):");
		lblComisiones.setFont(FONT_LABEL);
		lblComisiones.setBounds(245, 290, 150, 22);
		panelPrincipal.add(lblComisiones);

		spnComisiones = new JSpinner();
		spnComisiones.setModel(new SpinnerNumberModel(0.0d, 0.0d, 100.0d, 1.0d));
		spnComisiones.setBounds(245, 315, 200, 28);
		spnComisiones.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPrincipal.add(spnComisiones);

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnAccion = primaryButton(miEmpleado != null ? "Actualizar" : "Registrar");
		btnAccion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText().trim();
				String cedula = txtCedula.getText().trim();
				String rol = cbxRol.getSelectedItem().toString();

				if (nombre.isEmpty() || cedula.isEmpty() || rol.equals("Seleccione...")) {
					JOptionPane.showMessageDialog(null, "Por favor complete todos los campos y seleccione un rol.",
							"Campos vacíos", JOptionPane.WARNING_MESSAGE);
					return;
				}

				if (!cedula.matches("[0-9]+") || cedula.length() != 11) {
					JOptionPane.showMessageDialog(null, "La cédula debe contener exactamente 11 números, sin guiones.",
							"Cédula Inválida", JOptionPane.WARNING_MESSAGE);
					return;
				}

				EmpresaAltice empresa = EmpresaAltice.getInstance();

				for (Empleado emp : empresa.getMisEmpleados()) {
					if (miEmpleado != null && emp.getIdPersona().equals(miEmpleado.getIdPersona()))
						continue;
					if (emp.getCedula().equals(cedula)) {
						JOptionPane.showMessageDialog(null, "Ya existe un empleado registrado con esa cédula.",
								"Cédula duplicada", JOptionPane.WARNING_MESSAGE);
						return;
					}
				}

				float salario = ((Double) spnSalario.getValue()).floatValue();
				float comisiones = ((Double) spnComisiones.getValue()).floatValue();

				if (miEmpleado == null) {
					Empleado nuevoEmpleado = new Empleado(null, nombre, cedula, salario, comisiones, 0f, 0f, rol);
					empresa.getMisEmpleados().add(nuevoEmpleado);
					JOptionPane.showMessageDialog(null, "Empleado registrado con éxito", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					clean();
				} else {
					miEmpleado.setNombre(nombre);
					miEmpleado.setCedula(cedula);
					miEmpleado.setRolEmpleado(rol);
					miEmpleado.setSalario(salario);
					miEmpleado.setComisiones(comisiones);
					JOptionPane.showMessageDialog(null, "Empleado actualizado con éxito", "Información",
							JOptionPane.INFORMATION_MESSAGE);
					dispose();
				}

				empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
						empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(),
						empresa.getPagos());
			}
		});
		buttonPane.add(btnAccion);
		getRootPane().setDefaultButton(btnAccion);

		JButton btnCancelar = outlineButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);

		loadEmpleado();
	}

	private void loadEmpleado() {
		if (miEmpleado != null) {
			txtId.setText(miEmpleado.getIdPersona());
			txtCodigo.setText(miEmpleado.getCodigoEmpleado());
			txtNombre.setText(miEmpleado.getNombre());
			txtCedula.setText(miEmpleado.getCedula());
			cbxRol.setSelectedItem(miEmpleado.getRolEmpleado());
			spnSalario.setValue((double) miEmpleado.getSalario());
			spnComisiones.setValue((double) miEmpleado.getComisiones());
		}
	}

	private void clean() {
		txtNombre.setText("");
		txtCedula.setText("");
		cbxRol.setSelectedIndex(0);
		spnSalario.setValue(0.0d);
		spnComisiones.setValue(0.0d);

		txtId.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigo.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
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