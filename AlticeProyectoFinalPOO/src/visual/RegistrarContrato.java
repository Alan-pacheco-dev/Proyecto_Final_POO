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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;

import logico.Cliente;
import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;
import logico.Plan;
import logico.Usuario;

public class RegistrarContrato extends JDialog {

	private static final Color ALTICE_BLUE   = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT  = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font  FONT_LABEL    = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_INPUT    = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font  FONT_BTN      = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtAnNoHa;
	private JTextField txtCodigoCliente;

	private Cliente clienteActual = null;
	private Plan planEscogido = null;

	private Empleado empleadoLogueado = null;
	private Empleado empleadoComision = null;

	private JTextField txtPlanNombre;
	private JTextField txtNombreEmpleadoCargo;
	private JTextField txtPorcentajeAplicado;

	public RegistrarContrato(Cliente cliente, Empleado empleadoSesion) {

		this.clienteActual = cliente;
		this.empleadoLogueado = empleadoSesion;

		setTitle("Generar Nuevo Contrato");
		dim = getToolkit().getScreenSize();
		setSize(600, 830);
		setLocationRelativeTo(null);
		setResizable(false);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Generar Nuevo Contrato");
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

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBorder(BorderFactory.createTitledBorder(
			new LineBorder(Color.LIGHT_GRAY), "Datos del Contrato",
			TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE
		));
		panel.setLayout(null);
		contentPanel.add(panel, BorderLayout.CENTER);

		JLabel lblID = new JLabel("ID Contrato:");
		lblID.setFont(FONT_LABEL);
		lblID.setBounds(20, 30, 140, 22);
		panel.add(lblID);

		txtID = new JTextField();
		txtID.setEditable(false);
		txtID.setFont(FONT_INPUT);
		txtID.setBackground(ALTICE_LIGHT);
		txtID.setText("Cto - " + EmpresaAltice.getInstance().idContratos);
		txtID.setBounds(20, 55, 160, 28);
		txtID.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panel.add(txtID);

		JLabel lblVigencia = new JLabel("Vigencia:");
		lblVigencia.setFont(FONT_LABEL);
		lblVigencia.setBounds(380, 30, 180, 22);
		panel.add(lblVigencia);

		txtAnNoHa = new JTextField();
		txtAnNoHa.setEditable(false);
		txtAnNoHa.setFont(FONT_INPUT);
		txtAnNoHa.setBackground(ALTICE_LIGHT);
		txtAnNoHa.setText("Inicia al registrar");
		txtAnNoHa.setBounds(380, 55, 175, 28);
		txtAnNoHa.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panel.add(txtAnNoHa);

		JPanel panelCliente = new JPanel();
		panelCliente.setLayout(null);
		panelCliente.setBackground(ALTICE_LIGHT);
		panelCliente.setBorder(BorderFactory.createTitledBorder(
			new LineBorder(ALTICE_BORDER), "Información del Cliente",
			TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE
		));
		panelCliente.setBounds(20, 100, 540, 240);
		panel.add(panelCliente);

		JButton btnEscogerCliente = primaryButton("Buscar Cliente");
		btnEscogerCliente.setBounds(15, 25, 160, 28);
		panelCliente.add(btnEscogerCliente);

		JLabel lblCodigo = new JLabel("Código:");
		lblCodigo.setFont(FONT_LABEL);
		lblCodigo.setBounds(15, 65, 120, 22);
		panelCliente.add(lblCodigo);

		txtCodigoCliente = new JTextField();
		txtCodigoCliente.setEditable(false);
		txtCodigoCliente.setFont(FONT_INPUT);
		txtCodigoCliente.setBackground(Color.WHITE);
		if (clienteActual != null) txtCodigoCliente.setText(clienteActual.getCodigoCliente());
		txtCodigoCliente.setBounds(15, 88, 150, 28);
		txtCodigoCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelCliente.add(txtCodigoCliente);

		JLabel lblDeuda = new JLabel("Deuda Actual:");
		lblDeuda.setFont(FONT_LABEL);
		lblDeuda.setBounds(300, 65, 130, 22);
		panelCliente.add(lblDeuda);

		txtDeudaCliente = new JTextField();
		txtDeudaCliente.setEditable(false);
		txtDeudaCliente.setFont(FONT_INPUT);
		txtDeudaCliente.setBackground(Color.WHITE);
		if (clienteActual != null) txtDeudaCliente.setText("$ " + String.format("%.2f", clienteActual.calcularDeuda()));
		txtDeudaCliente.setBounds(300, 88, 220, 28);
		txtDeudaCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelCliente.add(txtDeudaCliente);

		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(FONT_LABEL);
		lblNombre.setBounds(15, 125, 120, 22);
		panelCliente.add(lblNombre);

		txtNombreCliente = new JTextField();
		txtNombreCliente.setEditable(false);
		txtNombreCliente.setFont(FONT_INPUT);
		txtNombreCliente.setBackground(Color.WHITE);
		if (clienteActual != null) txtNombreCliente.setText(clienteActual.getNombre());
		txtNombreCliente.setBounds(15, 148, 505, 28);
		txtNombreCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelCliente.add(txtNombreCliente);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(FONT_LABEL);
		lblEmail.setBounds(15, 183, 120, 22);
		panelCliente.add(lblEmail);

		txtEmailCliente = new JTextField();
		txtEmailCliente.setEditable(false);
		txtEmailCliente.setFont(FONT_INPUT);
		txtEmailCliente.setBackground(Color.WHITE);
		if (clienteActual != null) txtEmailCliente.setText(clienteActual.getEmail());
		txtEmailCliente.setBounds(15, 206, 240, 28);
		txtEmailCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelCliente.add(txtEmailCliente);

		JLabel lblDir = new JLabel("Dirección:");
		lblDir.setFont(FONT_LABEL);
		lblDir.setBounds(270, 183, 120, 22);
		panelCliente.add(lblDir);

		txtDireccionCliente = new JTextField();
		txtDireccionCliente.setEditable(false);
		txtDireccionCliente.setFont(FONT_INPUT);
		txtDireccionCliente.setBackground(Color.WHITE);
		if (clienteActual != null) txtDireccionCliente.setText(clienteActual.getDireccion());
		txtDireccionCliente.setBounds(270, 206, 250, 28);
		txtDireccionCliente.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelCliente.add(txtDireccionCliente);

		btnEscogerCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarClientes ventanaClientes = new ListarClientes(true);
				ventanaClientes.setModal(true);
				ventanaClientes.setVisible(true);
				Cliente clienteElegido = ventanaClientes.getClienteSeleccionado();
				if (clienteElegido != null) {
					clienteActual = clienteElegido;
					txtCodigoCliente.setText(clienteActual.getCodigoCliente());
					txtNombreCliente.setText(clienteActual.getNombre());
					txtEmailCliente.setText(clienteActual.getEmail());
					txtDireccionCliente.setText(clienteActual.getDireccion());
					txtDeudaCliente.setText("$ " + String.format("%.2f", clienteActual.calcularDeuda()));
				}
			}
		});

		JPanel panelPlan = new JPanel();
		panelPlan.setLayout(null);
		panelPlan.setBackground(ALTICE_LIGHT);
		panelPlan.setBorder(BorderFactory.createTitledBorder(
			new LineBorder(ALTICE_BORDER), "Plan del Contrato",
			TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE
		));
		panelPlan.setBounds(20, 355, 540, 100);
		panel.add(panelPlan);

		txtPlanNombre = new JTextField();
		txtPlanNombre.setEditable(false);
		txtPlanNombre.setFont(FONT_INPUT);
		txtPlanNombre.setBackground(Color.WHITE);
		txtPlanNombre.setBounds(15, 25, 505, 28);
		txtPlanNombre.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelPlan.add(txtPlanNombre);

		JButton btnCrearPlan = outlineButton("Crear Plan");
		btnCrearPlan.setBounds(15, 60, 120, 28);
		panelPlan.add(btnCrearPlan);

		JButton btnEscogerPlan = primaryButton("Escoger Plan");
		btnEscogerPlan.setBounds(145, 60, 130, 28);
		panelPlan.add(btnEscogerPlan);

		JButton btnVerServiciosPlan = outlineButton("Ver Servicios");
		btnVerServiciosPlan.setBounds(285, 60, 130, 28);
		panelPlan.add(btnVerServiciosPlan);

		btnEscogerPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarPlanes ventanaPlanes = new ListarPlanes(true);
				ventanaPlanes.setVisible(true);
				Plan planElegido = ventanaPlanes.getPlanSeleccionado();
				if (planElegido != null) {
					planEscogido = planElegido;
					txtPlanNombre.setText(planEscogido.getNombrePlan() + " ($" + String.format("%.2f", planEscogido.getPrecioTotal()) + ")");
				}
			}
		});

		btnVerServiciosPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (planEscogido == null) {
					JOptionPane.showMessageDialog(null, "Debe escoger un plan primero.", "Aviso", JOptionPane.WARNING_MESSAGE);
				} else {
					ListarServiciosXPlan listarServisPlan = new ListarServiciosXPlan(planEscogido.getServiciosPlan());
					listarServisPlan.setVisible(true);
				}
			}
		});

		btnCrearPlan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPlan regisPlan = new RegistrarPlan();
				regisPlan.setModal(true);
				regisPlan.setVisible(true);
			}
		});

		JPanel panelEmpleado = new JPanel();
		panelEmpleado.setLayout(null);
		panelEmpleado.setBackground(ALTICE_LIGHT);
		panelEmpleado.setBorder(BorderFactory.createTitledBorder(
			new LineBorder(ALTICE_BORDER), "Empleado a Cargo",
			TitledBorder.LEADING, TitledBorder.TOP, FONT_LABEL, ALTICE_BLUE
		));
		panelEmpleado.setBounds(20, 465, 540, 110);
		panel.add(panelEmpleado);

		txtNombreEmpleadoCargo = new JTextField();
		txtNombreEmpleadoCargo.setEditable(false);
		txtNombreEmpleadoCargo.setFont(FONT_INPUT);
		txtNombreEmpleadoCargo.setBackground(Color.WHITE);
		txtNombreEmpleadoCargo.setBounds(15, 30, 310, 28);
		txtNombreEmpleadoCargo.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelEmpleado.add(txtNombreEmpleadoCargo);

		JLabel lblPorcentaje = new JLabel("Comisión (%):");
		lblPorcentaje.setFont(FONT_LABEL);
		lblPorcentaje.setBounds(335, 10, 120, 22);
		panelEmpleado.add(lblPorcentaje);

		txtPorcentajeAplicado = new JTextField();
		txtPorcentajeAplicado.setEditable(false);
		txtPorcentajeAplicado.setFont(FONT_INPUT);
		txtPorcentajeAplicado.setBackground(Color.WHITE);
		txtPorcentajeAplicado.setBounds(335, 30, 185, 28);
		txtPorcentajeAplicado.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelEmpleado.add(txtPorcentajeAplicado);

		JButton btnEscogerEmpleado = primaryButton("Elegir Empleado");
		btnEscogerEmpleado.setBounds(15, 70, 160, 28);
		panelEmpleado.add(btnEscogerEmpleado);

		btnEscogerEmpleado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmpleados listEmps = new ListarEmpleados(true);
				listEmps.setVisible(true);
				Empleado empElegido = listEmps.getEmpleadoSeleccionado();
				if (empElegido != null) {
					empleadoComision = empElegido;
					txtNombreEmpleadoCargo.setText(empleadoComision.getNombre());
					txtPorcentajeAplicado.setText(String.valueOf(empleadoComision.getComisiones()));
				}
			}
		});

		Usuario userActual = EmpresaAltice.getLoginUser();
		String rolUsuario = (userActual != null) ? userActual.getRolEmpleado() : "";

		if (rolUsuario.equalsIgnoreCase("Administrativo")) {
			btnCrearPlan.setVisible(true);
			btnEscogerEmpleado.setVisible(true);
			txtNombreEmpleadoCargo.setText("Seleccione un empleado...");
		} 
		else if (empleadoLogueado != null) {
			String rolEmp = empleadoLogueado.getRolEmpleado();
			btnCrearPlan.setVisible(!rolEmp.equalsIgnoreCase("Vendedor"));
			
			empleadoComision = empleadoLogueado;
			txtNombreEmpleadoCargo.setText(empleadoComision.getNombre());
			txtPorcentajeAplicado.setText(String.valueOf(empleadoComision.getComisiones()));
			btnEscogerEmpleado.setVisible(false);
		} 
		else {
			btnCrearPlan.setVisible(false);
			btnEscogerEmpleado.setVisible(false);
		}

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnRegistrar = primaryButton("Registrar Contrato");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (clienteActual == null) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (planEscogido == null) {
					JOptionPane.showMessageDialog(null, "Debe seleccionar un plan para el contrato.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (empleadoComision == null) {
					JOptionPane.showMessageDialog(null, "Debe asignar un empleado a cargo del contrato.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				for (Contrato c : clienteActual.getMisContratos()) {
					if (c.isActivo() && c.getPlanContrato().getIdPlan().equals(planEscogido.getIdPlan())) {
						JOptionPane.showMessageDialog(null,
							"El cliente ya tiene un contrato activo con ese plan.", "Error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}

				EmpresaAltice empresa = EmpresaAltice.getInstance();
				float precioPlan = planEscogido.getPrecioTotal();
				float comision = empleadoComision.getComisiones();
				LocalDate fechaInicio = LocalDate.now();

				Contrato cto = new Contrato(clienteActual, empleadoComision, comision, precioPlan, fechaInicio, null, planEscogido);

				clienteActual.setCantContratosActivos(clienteActual.getCantContratosActivos() + 1);
				clienteActual.getMisContratos().add(cto);
				clienteActual.setDeuda(clienteActual.getDeuda() + cto.getPrecioMensualAcordado());

				empleadoComision.setCantidadVentas(empleadoComision.getCantidadVentas() + 1);
				empleadoComision.setMontoVentas(empleadoComision.getMontoVentas() + precioPlan);

				empresa.getMisContratos().add(cto);
				empresa.GuardarDatos(
					empresa.getMisClientes(), empresa.getMisEmpleados(),
					empresa.getMisPlanes(), empresa.getMisServicios(),
					empresa.getMisUsuarios(), empresa.getMisContratos(),
					empresa.getPagos()
				);

				JOptionPane.showMessageDialog(null, "¡Contrato registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
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