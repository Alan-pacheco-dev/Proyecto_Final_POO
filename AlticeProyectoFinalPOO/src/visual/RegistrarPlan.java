package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.Plan;
import logico.Servicio;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegistrarPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtDescripcionPlan;
	private JTextField txtPrecioTotalPlan;
	private JTextField txtAnNoHa;
	private JTextField txtNombrePlan;

	// Los RadioButtons ahora los declaramos globales para poder acceder a ellos desde los métodos
	private JRadioButton rdbtnInternet;
	private JRadioButton rdbtnMovil;
	private JRadioButton rdbtnTelefonia;
	private JRadioButton rdbtnTelevision;

	// === MEMORIA TEMPORAL PARA LOS SERVICIOS DEL PLAN ===
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
		setTitle("Registrar Plan");
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(579, 724);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		// Inicializamos la lista temporal
		serviciosEscogidos = new ArrayList<Servicio>();

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);

			JLabel lblNewLabel = new JLabel("ID Plan");
			lblNewLabel.setBounds(20, 21, 117, 34);
			panel.add(lblNewLabel);

			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setText("P - " + EmpresaAltice.getInstance().idPlanes);
			txtID.setBounds(20, 66, 160, 20);
			panel.add(txtID);
			txtID.setColumns(10);

			JLabel lblNombreDelEmpleado = new JLabel("Descripción del plan");
			lblNombreDelEmpleado.setBounds(20, 189, 148, 34);
			panel.add(lblNombreDelEmpleado);

			txtDescripcionPlan = new JTextField();
			txtDescripcionPlan.setColumns(10);
			txtDescripcionPlan.setBounds(20, 223, 517, 72);
			panel.add(txtDescripcionPlan);

			txtPrecioTotalPlan = new JTextField();
			txtPrecioTotalPlan.setEditable(false);
			txtPrecioTotalPlan.setColumns(10);
			txtPrecioTotalPlan.setText("$ 0.0"); // Empieza en 0
			txtPrecioTotalPlan.setBounds(20, 537, 250, 20);
			panel.add(txtPrecioTotalPlan);

			JLabel lblDireccin = new JLabel("Precio total");
			lblDireccin.setBounds(20, 487, 148, 34);
			panel.add(lblDireccin);

			JLabel lblVigenciaDelContrato = new JLabel("¿Está activo?");
			lblVigenciaDelContrato.setBounds(352, 21, 222, 34);
			panel.add(lblVigenciaDelContrato);

			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setText("Está siendo registrado");
			txtAnNoHa.setColumns(10);
			txtAnNoHa.setBounds(352, 66, 185, 20);
			panel.add(txtAnNoHa);

			JLabel lblTipoDeServicio = new JLabel("Nombre del plan");
			lblTipoDeServicio.setBounds(20, 113, 148, 34);
			panel.add(lblTipoDeServicio);

			txtNombrePlan = new JTextField();
			txtNombrePlan.setColumns(10);
			txtNombrePlan.setBounds(20, 158, 517, 20);
			panel.add(txtNombrePlan);

			JTextPane txtpnSeCalculaCon = new JTextPane();
			txtpnSeCalculaCon.setEditable(false);
			txtpnSeCalculaCon.setText("Se calcula sumando el precio de cada uno de los servicios");
			txtpnSeCalculaCon.setBounds(20, 573, 473, 34);
			panel.add(txtpnSeCalculaCon);

			JLabel lblServiciosDelPlan = new JLabel("Servicios del Plan");
			lblServiciosDelPlan.setBounds(20, 304, 222, 34);
			panel.add(lblServiciosDelPlan);

			JLabel lblPlanIncluye = new JLabel("Este plan incluye:");
			lblPlanIncluye.setBounds(20, 397, 222, 34);
			panel.add(lblPlanIncluye);

			// ==============================================================
			// INICIO DE BOTONES Y RADIO BUTTONS DE SERVICIOS
			// ==============================================================

			rdbtnInternet = new JRadioButton("Internet");
			rdbtnInternet.setBounds(20, 443, 109, 23);
			panel.add(rdbtnInternet);

			rdbtnMovil = new JRadioButton("Móvil");
			rdbtnMovil.setBounds(130, 443, 109, 23); // Ajusté las coordenadas "X" para que quepan
			panel.add(rdbtnMovil);

			rdbtnTelefonia = new JRadioButton("Telefonía");
			rdbtnTelefonia.setBounds(240, 443, 109, 23);
			panel.add(rdbtnTelefonia);

			rdbtnTelevision = new JRadioButton("Televisión");
			rdbtnTelevision.setBounds(360, 443, 109, 23);
			panel.add(rdbtnTelevision);

			// EVENTOS DE LOS RADIOBUTTONS (Bloqueo de marcado manual)
			rdbtnInternet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnInternet.isSelected()) {
						rdbtnInternet.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Escoger servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Internet");
					}
				}
			});

			rdbtnMovil.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnMovil.isSelected()) {
						rdbtnMovil.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Escoger servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Móvil");
					}
				}
			});

			rdbtnTelefonia.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnTelefonia.isSelected()) {
						rdbtnTelefonia.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Escoger servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Telefonía");
					}
				}
			});

			rdbtnTelevision.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (rdbtnTelevision.isSelected()) {
						rdbtnTelevision.setSelected(false);
						JOptionPane.showMessageDialog(null, "Debe seleccionar el servicio utilizando el botón 'Escoger servicio'.", "Aviso", JOptionPane.WARNING_MESSAGE);
					} else {
						removerServicioPorTipo("Televisión");
					}
				}
			});

			// BOTÓN: CREAR SERVICIO (Desde 0)
			JButton btnCrear = new JButton("Crear servicio");
			btnCrear.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegistrarServicio regisServi = new RegistrarServicio();
					regisServi.setModal(true);
					regisServi.setVisible(true);
				}
			});
			btnCrear.setBounds(15, 354, 129, 23);
			panel.add(btnCrear);

			// BOTÓN: ESCOGER SERVICIO
			JButton btnEscogerServicio = new JButton("Escoger servicio");
			btnEscogerServicio.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// Abrimos en modo Selector (true)
					ListarServicios listarServis = new ListarServicios("Todos", true);
					listarServis.setVisible(true);

					// Cuando la ventana se cierra, atrapamos el servicio seleccionado
					Servicio s = listarServis.getServicioSeleccionado();
					if (s != null) {
						agregarServicio(s);
					}
				}
			});
			btnEscogerServicio.setBounds(154, 354, 153, 23); // Ajusté posición
			panel.add(btnEscogerServicio);

			// BOTÓN: VER SERVICIOS ESCOGIDOS
			JButton btnVerServiciosEscogidos = new JButton("Ver servicios escogidos");
			btnVerServiciosEscogidos.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (serviciosEscogidos.isEmpty()) {
						JOptionPane.showMessageDialog(null, "Aún no ha escogido ningún servicio para este plan.", "Lista Vacía", JOptionPane.INFORMATION_MESSAGE);
					}else {
						// Llamamos a tu nueva clase dedicada
						ListarServiciosXPlan ventanaVisor = new ListarServiciosXPlan(serviciosEscogidos);
						ventanaVisor.setVisible(true);
					}
				}
			});
			btnVerServiciosEscogidos.setBounds(317, 354, 200, 23); // Ajusté posición
			panel.add(btnVerServiciosEscogidos);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {

						String nombre = txtNombrePlan.getText().trim();
						String descripcion = txtDescripcionPlan.getText().trim();

						// VALIDACIONES
						if (nombre.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Debe ingresar un nombre para el plan.", "Error", JOptionPane.WARNING_MESSAGE);
							return;
						}
						if (serviciosEscogidos.isEmpty()) {
							JOptionPane.showMessageDialog(null, "El plan debe tener al menos un servicio seleccionado.", "Error", JOptionPane.WARNING_MESSAGE);
							return;
						}

						EmpresaAltice empresa = EmpresaAltice.getInstance();

						// Creación del Plan
						Plan nuevoPlan = new Plan(nombre, "", 0.0f); // El tiempoCuota no se usa y el precio se recalcula luego
						nuevoPlan.setServiciosPlan(serviciosEscogidos);

						// IMPORTANTE: En tu clase "Plan" no tienes atributo "descripcionPlan".
						// Si se lo agregas luego en tu paquete lógico, descomenta esta línea:
						// nuevoPlan.setDescripcionPlan(descripcion);

						// Recalculamos el precio total internamente y lo asignamos
						float precioTotalMensual = nuevoPlan.calcularPrecioTotal();
						nuevoPlan.setPrecioTotal(precioTotalMensual);

						// Marcamos los servicios como "En Uso" para que ya no aparezcan en el listado general
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
						dispose(); // Cerramos la ventana al terminar
					}
				});
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}

	// =========================================================
	// MÉTODOS AYUDANTES PARA GESTIONAR LA LÓGICA DE SERVICIOS
	// =========================================================

	private void agregarServicio(Servicio nuevoServicio) {
		// 1. Borramos cualquier servicio del mismo tipo que ya estuviera en la lista (Para reemplazarlo)
		removerServicioPorTipo(nuevoServicio.getTipoServicio());

		// 2. Lo agregamos a la memoria temporal
		serviciosEscogidos.add(nuevoServicio);

		// 3. Encendemos el RadioButton correspondiente (Validando la clase padre o el String)
		if (nuevoServicio instanceof ServicioInternet || nuevoServicio.getTipoServicio().equalsIgnoreCase("Internet")) {
			rdbtnInternet.setSelected(true);
		} else if (nuevoServicio instanceof ServicioMovil || nuevoServicio.getTipoServicio().equalsIgnoreCase("Móvil")) {
			rdbtnMovil.setSelected(true);
		} else if (nuevoServicio instanceof ServicioTelefonia || nuevoServicio.getTipoServicio().equalsIgnoreCase("Telefonía")) {
			rdbtnTelefonia.setSelected(true);
		} else if (nuevoServicio instanceof ServicioTelevision || nuevoServicio.getTipoServicio().equalsIgnoreCase("Televisión")) {
			rdbtnTelevision.setSelected(true);
		}

		// 4. Sumamos el nuevo precio
		actualizarPrecio();
	}

	private void removerServicioPorTipo(String tipoServicio) {
		Servicio servicioAEliminar = null;

		// Buscamos si existe un servicio de ese tipo en nuestra lista
		for (Servicio s : serviciosEscogidos) {
			if (s.getTipoServicio().equalsIgnoreCase(tipoServicio)) {
				servicioAEliminar = s;
				break;
			}
		}

		// Si lo encontramos, lo borramos
		if (servicioAEliminar != null) {
			serviciosEscogidos.remove(servicioAEliminar);
			actualizarPrecio();
		}
	}

	private void actualizarPrecio() {
		float precioTotal = 0;
		for (Servicio s : serviciosEscogidos) {
			precioTotal += s.getPrecioServicio();
		}
		txtPrecioTotalPlan.setText("$ " + precioTotal);
	}
}