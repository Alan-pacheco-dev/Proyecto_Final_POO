package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
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
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.Plan;
import logico.Servicio;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class RegistrarPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextArea  txtDescripcionPlan;
	private JTextField txtPrecioTotalPlan;
	private JTextField txtAnNoHa;
	private JTextField txtNombrePlan;

	private JRadioButton rdbtnInternet;
	private JRadioButton rdbtnMovil;
	private JRadioButton rdbtnTelefonia;
	private JRadioButton rdbtnTelevision;

	private ArrayList<Servicio> serviciosEscogidos;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color alticeLight = new Color(245, 248, 255);
	private Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

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
		setTitle("Registrar Plan Comercial");
		setBounds(100, 100, 750, 800);
		dim = getToolkit().getScreenSize();
		setLocationRelativeTo(null);
		setResizable(false);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		serviciosEscogidos = new ArrayList<Servicio>();

		{
			JPanel panel = new JPanel();
			panel.setBackground(bgWhite);
			panel.setBorder(BorderFactory.createTitledBorder(new LineBorder(Color.LIGHT_GRAY), "Datos del Plan", TitledBorder.LEADING, TitledBorder.TOP, fontLabel, alticeBlue));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("ID Plan:");
			lblNewLabel.setFont(fontLabel);
			lblNewLabel.setBounds(20, 30, 117, 25);
			panel.add(lblNewLabel);

			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setFont(fontInput);
			txtID.setText("P - " + EmpresaAltice.getInstance().idPlanes);
			txtID.setBounds(20, 60, 160, 30);
			txtID.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panel.add(txtID);

			JLabel lblVigenciaDelContrato = new JLabel("Estado:");
			lblVigenciaDelContrato.setFont(fontLabel);
			lblVigenciaDelContrato.setBounds(500, 30, 200, 25);
			panel.add(lblVigenciaDelContrato);

			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setFont(fontInput);
			txtAnNoHa.setText("En Creación");
			txtAnNoHa.setBounds(500, 60, 185, 30);
			txtAnNoHa.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panel.add(txtAnNoHa);

			JLabel lblTipoDeServicio = new JLabel("Nombre del plan:");
			lblTipoDeServicio.setFont(fontLabel);
			lblTipoDeServicio.setBounds(20, 110, 148, 25);
			panel.add(lblTipoDeServicio);

			txtNombrePlan = new JTextField();
			txtNombrePlan.setFont(fontInput);
			txtNombrePlan.setBounds(20, 140, 665, 30);
			txtNombrePlan.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panel.add(txtNombrePlan);

			JLabel lblNombreDelEmpleado = new JLabel("Descripción del plan:");
			lblNombreDelEmpleado.setFont(fontLabel);
			lblNombreDelEmpleado.setBounds(20, 190, 180, 25);
			panel.add(lblNombreDelEmpleado);

			txtDescripcionPlan = new JTextArea();
			txtDescripcionPlan.setFont(fontInput);
			txtDescripcionPlan.setLineWrap(true);
			txtDescripcionPlan.setWrapStyleWord(true);
			
			JScrollPane scrollDescripcion = new JScrollPane(txtDescripcionPlan);
			scrollDescripcion.setBounds(20, 220, 665, 90);
			scrollDescripcion.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panel.add(scrollDescripcion);

			// ==========================================
			// SECCIÓN SERVICIOS
			// ==========================================
			JPanel panelServicios = new JPanel();
			panelServicios.setLayout(null);
			panelServicios.setBackground(alticeLight);
			panelServicios.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panelServicios.setBounds(20, 340, 665, 130);
			panel.add(panelServicios);

			JLabel lblServiciosDelPlan = new JLabel("Gestión de Servicios");
			lblServiciosDelPlan.setFont(fontLabel);
			lblServiciosDelPlan.setForeground(alticeBlue);
			lblServiciosDelPlan.setBounds(15, 10, 222, 25);
			panelServicios.add(lblServiciosDelPlan);

			JButton btnCrear = outlineButton("Nuevo Servicio");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegistrarServicio regisServi = new RegistrarServicio();
					regisServi.setModal(true);
					regisServi.setVisible(true);
				}
			});
			btnCrear.setBounds(15, 45, 160, 30);
			panelServicios.add(btnCrear);

			JButton btnEscogerServicio = primaryButton("Vincular Servicio");
			btnEscogerServicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarServicios listarServis = new ListarServicios("Todos", true);
					listarServis.setVisible(true);

					Servicio s = listarServis.getServicioSeleccionado();
					if (s != null) {
						agregarServicio(s);
					}
				}
			});
			btnEscogerServicio.setBounds(195, 45, 180, 30);
			panelServicios.add(btnEscogerServicio);

			JButton btnVerServiciosEscogidos = outlineButton("Ver Vinculados");
			btnVerServiciosEscogidos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (serviciosEscogidos.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Aún no ha escogido ningún servicio para este plan.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ListarServiciosXPlan ventanaVisor = new ListarServiciosXPlan(serviciosEscogidos);
						ventanaVisor.setVisible(true);
					}
				}
			});
			btnVerServiciosEscogidos.setBounds(395, 45, 180, 30);
			panelServicios.add(btnVerServiciosEscogidos);

			JLabel lblPlanIncluye = new JLabel("Servicios vinculados actualmente:");
			lblPlanIncluye.setFont(new Font("SansSerif", Font.ITALIC, 12));
			lblPlanIncluye.setBounds(15, 90, 222, 25);
			panelServicios.add(lblPlanIncluye);

			rdbtnInternet = new JRadioButton("Internet");
			rdbtnInternet.setBackground(alticeLight);
			rdbtnInternet.setFont(fontInput);
			rdbtnInternet.setBounds(250, 90, 80, 25);
			panelServicios.add(rdbtnInternet);

			rdbtnMovil = new JRadioButton("Móvil");
			rdbtnMovil.setBackground(alticeLight);
			rdbtnMovil.setFont(fontInput);
			rdbtnMovil.setBounds(350, 90, 70, 25);
			panelServicios.add(rdbtnMovil);

			rdbtnTelefonia = new JRadioButton("Telefonía");
			rdbtnTelefonia.setBackground(alticeLight);
			rdbtnTelefonia.setFont(fontInput);
			rdbtnTelefonia.setBounds(440, 90, 90, 25);
			panelServicios.add(rdbtnTelefonia);

			rdbtnTelevision = new JRadioButton("TV");
			rdbtnTelevision.setBackground(alticeLight);
			rdbtnTelevision.setFont(fontInput);
			rdbtnTelevision.setBounds(550, 90, 50, 25);
			panelServicios.add(rdbtnTelevision);

			// ==========================================
			// PRECIO
			// ==========================================
			JLabel lblDireccin = new JLabel("Precio Total Calculado:");
			lblDireccin.setFont(fontLabel);
			lblDireccin.setBounds(20, 500, 200, 25);
			panel.add(lblDireccin);

			txtPrecioTotalPlan = new JTextField();
			txtPrecioTotalPlan.setEditable(false);
			txtPrecioTotalPlan.setFont(new Font("SansSerif", Font.BOLD, 18));
			txtPrecioTotalPlan.setForeground(alticeBlue);
			txtPrecioTotalPlan.setText("$ 0.00");
			txtPrecioTotalPlan.setBounds(20, 530, 250, 35);
			txtPrecioTotalPlan.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panel.add(txtPrecioTotalPlan);

			JTextPane txtpnSeCalculaCon = new JTextPane();
			txtpnSeCalculaCon.setEditable(false);
			txtpnSeCalculaCon.setFont(new Font("SansSerif", Font.ITALIC, 12));
			txtpnSeCalculaCon.setForeground(Color.GRAY);
			txtpnSeCalculaCon.setBackground(bgWhite);
			txtpnSeCalculaCon.setText("* El precio se calcula automáticamente sumando el costo de los servicios vinculados y aplicando los impuestos de ley correspondientes.");
			txtpnSeCalculaCon.setBounds(20, 580, 665, 34);
			panel.add(txtpnSeCalculaCon);

			// Eventos de RadioButtons
			rdbtnInternet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnInternet.isSelected()) {
						rdbtnInternet.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Vincular servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Internet");
					}
				}
			});

			rdbtnMovil.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnMovil.isSelected()) {
						rdbtnMovil.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Vincular servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Móvil");
					}
				}
			});

			rdbtnTelefonia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnTelefonia.isSelected()) {
						rdbtnTelefonia.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Vincular servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Telefonía");
					}
				}
			});

			rdbtnTelevision.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnTelevision.isSelected()) {
						rdbtnTelevision.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Vincular servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Televisión");
					}
				}
			});
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBackground(bgWhite);
			buttonPane.setBorder(new EmptyBorder(10, 15, 10, 15));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			
			JButton btnRegistrar = primaryButton("Guardar Plan");
			btnRegistrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					String nombre = txtNombrePlan.getText().trim();
					String descripcion = txtDescripcionPlan.getText().trim();

					if (nombre.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para el plan.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (serviciosEscogidos.isEmpty()) {
						JOptionPane.showMessageDialog(null, "El plan debe tener al menos un servicio seleccionado.", "Error", JOptionPane.WARNING_MESSAGE);
						return;
					}

					EmpresaAltice empresa = EmpresaAltice.getInstance();

					for (Plan p : empresa.getMisPlanes()) {
						if (p.getNombrePlan().equalsIgnoreCase(nombre)) {
							JOptionPane.showMessageDialog(null,
									"Ya existe un plan registrado con ese nombre.", "Nombre duplicado", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}

					Plan nuevoPlan = new Plan(nombre, "", 0.0f);
					nuevoPlan.setServiciosPlan(serviciosEscogidos);

					float precioTotalMensual = nuevoPlan.calcularPrecioTotal();
					nuevoPlan.setPrecioTotal(precioTotalMensual);

					for (Servicio s : serviciosEscogidos) {
						s.setEstaEnUso(true);
					}

					empresa.getMisPlanes().add(nuevoPlan);

					empresa.GuardarDatos(
							empresa.getMisClientes(),
							empresa.getMisEmpleados(),
							empresa.getMisPlanes(),
							empresa.getMisServicios(),
							empresa.getMisUsuarios(),
							empresa.getMisContratos(),
							empresa.getPagos()
					);

					JOptionPane.showMessageDialog(null, "¡Plan registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
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
	}

	private void agregarServicio(Servicio nuevoServicio) {
		removerServicioPorTipo(nuevoServicio.getTipoServicio());

		serviciosEscogidos.add(nuevoServicio);

		if (nuevoServicio instanceof ServicioInternet || nuevoServicio.getTipoServicio().equalsIgnoreCase("Internet")) {
			rdbtnInternet.setSelected(true);
		} else if (nuevoServicio instanceof ServicioMovil || nuevoServicio.getTipoServicio().equalsIgnoreCase("Móvil")) {
			rdbtnMovil.setSelected(true);
		} else if (nuevoServicio instanceof ServicioTelefonia || nuevoServicio.getTipoServicio().equalsIgnoreCase("Telefonía")) {
			rdbtnTelefonia.setSelected(true);
		} else if (nuevoServicio instanceof ServicioTelevision || nuevoServicio.getTipoServicio().equalsIgnoreCase("Televisión")) {
			rdbtnTelevision.setSelected(true);
		}

		actualizarPrecio();
	}

	private void removerServicioPorTipo(String tipoServicio) {
		Servicio servicioAEliminar = null;

		for (Servicio s : serviciosEscogidos) {
			if (s.getTipoServicio().equalsIgnoreCase(tipoServicio)) {
				servicioAEliminar = s;
				break;
			}
		}

		if (servicioAEliminar != null) {
			serviciosEscogidos.remove(servicioAEliminar);
			
			if (tipoServicio.equalsIgnoreCase("Internet")) rdbtnInternet.setSelected(false);
			if (tipoServicio.equalsIgnoreCase("Móvil")) rdbtnMovil.setSelected(false);
			if (tipoServicio.equalsIgnoreCase("Telefonía")) rdbtnTelefonia.setSelected(false);
			if (tipoServicio.equalsIgnoreCase("Televisión")) rdbtnTelevision.setSelected(false);
			
			actualizarPrecio();
		}
	}

	private void actualizarPrecio() {
		float precioBase = 0;
		for (Servicio s : serviciosEscogidos) {
			precioBase += s.getPrecioServicio();
		}
		
		float conImpuesto = precioBase * 1.18f;
		float totalFinal = Math.round(conImpuesto * 100.0f) / 100.0f;
		
		txtPrecioTotalPlan.setText("$ " + String.format("%.2f", totalFinal));
	}
	
	// ==========================================
	// HERRAMIENTAS VISUALES DE BOTONES
	// ==========================================
	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(Color.WHITE);
		btn.setBackground(alticeBlue);
		btn.setFocusPainted(false);
		btn.setBorderPainted(false);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
	
	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("SansSerif", Font.BOLD, 14));
		btn.setForeground(alticeBlue);
		btn.setBackground(bgWhite);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(alticeBlue, 2, true));
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return btn;
	}
}