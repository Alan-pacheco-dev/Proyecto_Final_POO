package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Cliente;
import logico.EmpresaAltice;
import javax.swing.JRadioButton;

public class RegistrarCliente extends JDialog {
	
	/*
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtCodigoCliente;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtCedulaRNC;
	private EmpresaAltice empresa = EmpresaAltice.getInstance();

	public RegistrarCliente() {
		super();
		setTitle("Registrar Cliente");
		setBounds(100, 100, 450, 300);
		setSize(555, 596);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		txtNombreCliente    = new JTextField();
		txtEmailCliente     = new JTextField();
		txtDireccionCliente = new JTextField();
		txtCedulaRNC        = new JTextField();

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			// ── ID ──
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(30, 22, 117, 34);
			panel.add(lblNewLabel);

			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
			txtID.setBounds(30, 67, 185, 20);
			panel.add(txtID);
			txtID.setColumns(10);

			// ── CÓDIGO ──
			JLabel lblCdigoDelCliente = new JLabel("Código del Cliente");
			lblCdigoDelCliente.setBounds(298, 22, 222, 34);
			panel.add(lblCdigoDelCliente);

			String anioActual = String.valueOf(LocalDate.now().getYear());

			txtCodigoCliente = new JTextField();
			txtCodigoCliente.setEditable(false);
			txtCodigoCliente.setColumns(10);
			// Por defecto "No" está seleccionado → código de persona física
			txtCodigoCliente.setText("CLTE-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
			txtCodigoCliente.setBounds(298, 67, 185, 20);
			panel.add(txtCodigoCliente);

			// ── NOMBRE ──
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(30, 108, 148, 34);
			panel.add(lblNombreDelEmpleado);

			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(30, 153, 453, 20);
			panel.add(txtNombreCliente);

			// ── CÉDULA / RNC (label dinámico) ──
			JLabel lblCedulaRNC = new JLabel("Cédula");
			lblCedulaRNC.setBounds(30, 184, 148, 34);
			panel.add(lblCedulaRNC);

			txtCedulaRNC.setColumns(10);
			txtCedulaRNC.setBounds(30, 229, 453, 20);
			panel.add(txtCedulaRNC);

			// ── EMAIL ──
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(30, 260, 148, 34);
			panel.add(lblEmail);

			txtEmailCliente.setColumns(10);
			txtEmailCliente.setBounds(30, 305, 453, 20);
			panel.add(txtEmailCliente);

			// ── DIRECCIÓN ──
			JLabel lblDireccin = new JLabel("Dirección");
			lblDireccin.setBounds(30, 336, 148, 34);
			panel.add(lblDireccin);

			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(30, 381, 453, 20);
			panel.add(txtDireccionCliente);

			// ── ¿ES EMPRESA? ──
			JLabel lblesEmpresa = new JLabel("¿Es empresa?");
			lblesEmpresa.setBounds(30, 419, 222, 34);
			panel.add(lblesEmpresa);

			JRadioButton rdbtnEsEmpresaSi = new JRadioButton("Si");
			rdbtnEsEmpresaSi.setBounds(30, 464, 47, 23);
			panel.add(rdbtnEsEmpresaSi);

			JRadioButton rdbtnEsEmpresaNo = new JRadioButton("No");
			rdbtnEsEmpresaNo.setBounds(100, 464, 47, 23);
			panel.add(rdbtnEsEmpresaNo);

			ButtonGroup bgEsEmpresa = new ButtonGroup();
			bgEsEmpresa.add(rdbtnEsEmpresaSi);
			bgEsEmpresa.add(rdbtnEsEmpresaNo);

			// Por defecto: No
			rdbtnEsEmpresaNo.setSelected(true);

			// ── DEUDA ──
			JLabel lblComision = new JLabel("Deuda mensual del cliente");
			lblComision.setBounds(298, 419, 222, 34);
			panel.add(lblComision);

			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setText("$ 0.0");
			txtDeudaCliente.setEditable(false);
			txtDeudaCliente.setColumns(10);
			txtDeudaCliente.setBounds(298, 464, 185, 20);
			panel.add(txtDeudaCliente);

			// ── EVENTOS: cambia label y código según tipo de cliente ──
			rdbtnEsEmpresaSi.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblCedulaRNC.setText("RNC");
					txtCodigoCliente.setText("CLTE-EMPR-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
				}
			});

			rdbtnEsEmpresaNo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblCedulaRNC.setText("Cédula");
					txtCodigoCliente.setText("CLTE-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
				}
			});

			// ── BOTONES ──
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);

			JButton btnRegistrar = new JButton("Registrar");
			btnRegistrar.setActionCommand("OK");
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					// 1. Extraemos los datos eliminando espacios en blanco al inicio y al final
					String id        = txtID.getText();
					String codigo    = txtCodigoCliente.getText();
					String nombre    = txtNombreCliente.getText().trim();
					String email     = txtEmailCliente.getText().trim();
					String direccion = txtDireccionCliente.getText().trim();
					String cedulaRNC = txtCedulaRNC.getText().trim();

					// 2. Validación de campos vacíos
					if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || cedulaRNC.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
						return;
					}

					// 3. Validación de correo electrónico (debe contener un @ y un .)
					if (!email.contains("@") || !email.contains(".")) {
						JOptionPane.showMessageDialog(null, "Por favor, ingrese un correo electrónico válido.", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
						return;
					}

					// 4. Determinamos si es empresa (SIN OPERADOR TERNARIO)
					boolean esEmpresa = rdbtnEsEmpresaSi.isSelected();
					String tipoDocumento = "";
					if (esEmpresa == true) {
						tipoDocumento = "El RNC";
					} else {
						tipoDocumento = "La Cédula";
					}

					// 5. Validación de Cédula / RNC (Solo números)
					if (!cedulaRNC.matches("[0-9]+")) {
						JOptionPane.showMessageDialog(null, tipoDocumento + " debe contener únicamente números (sin guiones ni letras).", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
						return;
					}

					// 6. Validación de longitud (Cédula: 11 dígitos | RNC: 9 dígitos)
					if (esEmpresa && cedulaRNC.length() != 9) {
						JOptionPane.showMessageDialog(null, "El RNC debe tener exactamente 9 números.", "Longitud incorrecta", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (!esEmpresa && cedulaRNC.length() != 11) {
						JOptionPane.showMessageDialog(null, "La Cédula debe tener exactamente 11 números.", "Longitud incorrecta", JOptionPane.WARNING_MESSAGE);
						return;
					}

					// 7. Si pasa todas las validaciones, creamos y guardamos el cliente
					Cliente nuevoCliente = new Cliente(id, nombre, direccion, email, 0, esEmpresa, cedulaRNC);
					nuevoCliente.setCodigoCliente(codigo);
					empresa.getMisClientes().add(nuevoCliente);

					// 8. GUARDAMOS EN DISCO DURO (.txt / .dat)
					empresa.GuardarDatos(
							empresa.getMisClientes(), 
							empresa.getMisEmpleados(),
							empresa.getMisPlanes(), 
							empresa.getMisServicios(),
							empresa.getMisUsuarios(), 
							empresa.getMisContratos(),
							empresa.getPagos()
					);

					JOptionPane.showMessageDialog(null, "¡Cliente registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);

					// 9. Preguntar si desea registrar contrato
					int respuesta = JOptionPane.showConfirmDialog(null,
							"¿Desea registrarle un contrato a este nuevo cliente ahora mismo?",
							"Crear Contrato",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);

					if (respuesta == JOptionPane.YES_OPTION) {
						dispose(); // Cerramos la ventana de registro de cliente
						
						// --- LA SOLUCIÓN AQUÍ ---
						// Creamos el empleado simulado para poder abrir el contrato
						logico.Empleado empleadoFalso = new logico.Empleado(null, "Admin", 1000, 10, 0, 0, "Administrativo");
						
						// Le pasamos AMBOS parámetros
						RegistrarContrato ventanaContrato = new RegistrarContrato(nuevoCliente, empleadoFalso);
						ventanaContrato.setModal(true); // Aseguramos que sea modal
						ventanaContrato.setVisible(true);
					} else {
						clean(); // Limpiamos la pantalla en lugar de cerrarla, por si quiere registrar otro
					}
				}
			});
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

	private void clean() {
		txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigoCliente.setText("CLTE-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
		txtNombreCliente.setText("");
		txtDireccionCliente.setText("");
		txtEmailCliente.setText("");
		txtCedulaRNC.setText("");
	}
	*/
}