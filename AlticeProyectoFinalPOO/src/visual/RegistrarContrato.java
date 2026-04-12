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

import logico.Cliente;
import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;
import logico.Plan;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class RegistrarContrato extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private JTextField txtAnNoHa;
	private JTextField txtCodigoCliente;
	
	// Variables para guardar la selección de las otras ventanas
	private Cliente clienteActual = null;
	private Plan planEscogido = null;
	
	// Variables de seguridad para los empleados y comisiones
	private Empleado empleadoLogueado = null; 
	private Empleado empleadoComision = null; 
	
	private JTextField txtPlanNombre;
	private JTextField txtNombreEmpleadoCargo;
	private JTextField txtPorcentajeAplicado;

	// Constructor principal que recibe la data de quien lo llama (El menú principal)
	public RegistrarContrato(Cliente cliente, Empleado empleadoSesion) {
		
		this.clienteActual = cliente;
		this.empleadoLogueado = empleadoSesion;
		
		
		setTitle("Generar Nuevo Contrato");
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(573, 810);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			// --- DATOS DEL CONTRATO ---
			JLabel lblNewLabel = new JLabel("ID Contrato");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setText("Cto - " + EmpresaAltice.getInstance().idContratos);
			txtID.setBounds(20, 82, 160, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblVigenciaDelContrato = new JLabel("Vigencia del contrato");
			lblVigenciaDelContrato.setBounds(315, 37, 222, 34);
			panel.add(lblVigenciaDelContrato);
			
			txtAnNoHa = new JTextField();
			txtAnNoHa.setEditable(false);
			txtAnNoHa.setText("Inicia al registrar");
			txtAnNoHa.setColumns(10);
			txtAnNoHa.setBounds(315, 82, 185, 20);
			panel.add(txtAnNoHa);
			
			// --- DATOS DEL CLIENTE ---
			JLabel label = new JLabel("Código del Cliente");
			label.setBounds(20, 129, 148, 34);
			panel.add(label);
			
			txtCodigoCliente = new JTextField();
			txtCodigoCliente.setEditable(false);
			txtCodigoCliente.setColumns(10);
			if (clienteActual != null) {
				txtCodigoCliente.setText(clienteActual.getCodigoCliente());
			}
			txtCodigoCliente.setBounds(20, 174, 160, 20);
			panel.add(txtCodigoCliente);
			
			JLabel lblComision = new JLabel("Deuda del cliente");
			lblComision.setBounds(315, 129, 222, 34);
			panel.add(lblComision);
			
			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setEditable(false);
			txtDeudaCliente.setColumns(10);
			if (clienteActual != null) {
				txtDeudaCliente.setText("$ " + clienteActual.calcularDeuda());
			}
			txtDeudaCliente.setBounds(315, 174, 185, 20);
			panel.add(txtDeudaCliente);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(20, 202, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente = new JTextField();
			txtNombreCliente.setEditable(false);
			txtNombreCliente.setColumns(10);
			if (clienteActual != null) {
				txtNombreCliente.setText(clienteActual.getNombre());
			}
			txtNombreCliente.setBounds(20, 247, 490, 20);
			panel.add(txtNombreCliente);
			
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(20, 278, 148, 34);
			panel.add(lblEmail);
			
			txtEmailCliente = new JTextField();
			txtEmailCliente.setEditable(false);
			txtEmailCliente.setColumns(10);
			if (clienteActual != null) {
				txtEmailCliente.setText(clienteActual.getEmail());
			}
			txtEmailCliente.setBounds(20, 323, 490, 20);
			panel.add(txtEmailCliente);
			
			JLabel lblDireccin = new JLabel("Dirección");
			lblDireccin.setBounds(20, 366, 148, 34);
			panel.add(lblDireccin);
			
			txtDireccionCliente = new JTextField();
			txtDireccionCliente.setEditable(false);
			txtDireccionCliente.setColumns(10);
			if (clienteActual != null) {
				txtDireccionCliente.setText(clienteActual.getDireccion());
			}
			txtDireccionCliente.setBounds(20, 411, 490, 20);
			panel.add(txtDireccionCliente);
			
			// --- SELECCIÓN DEL PLAN ---
			JLabel lblPlan = new JLabel("Plan del contrato");
			lblPlan.setBounds(20, 463, 222, 34);
			panel.add(lblPlan);
			
			txtPlanNombre = new JTextField();
			txtPlanNombre.setEditable(false);
			txtPlanNombre.setColumns(10);
			txtPlanNombre.setBounds(20, 508, 490, 20);
			panel.add(txtPlanNombre);
			
			JButton btnEscogerPlan = new JButton("Escoger Plan");
			btnEscogerPlan.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarPlanes ventanaPlanes = new ListarPlanes(true); 
					ventanaPlanes.setVisible(true);

					Plan planElegido = ventanaPlanes.getPlanSeleccionado();
					if (planElegido != null) {
						planEscogido = planElegido;
						txtPlanNombre.setText(planEscogido.getNombrePlan() + " ($" + planEscogido.getPrecioTotal() + ")");
					}
				}
			});
			btnEscogerPlan.setBounds(20, 546, 150, 23);
			panel.add(btnEscogerPlan);
			
			JButton btnVerServiciosPlan = new JButton("Ver los servicios del plan");
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
			btnVerServiciosPlan.setBounds(185, 546, 207, 23);
			panel.add(btnVerServiciosPlan);
			
			// --- ASIGNACIÓN DEL EMPLEADO / COMISIÓN ---
			JLabel lblEmpleadoACargo = new JLabel("Empleado a cargo del contrato");
			lblEmpleadoACargo.setBounds(20, 585, 278, 34);
			panel.add(lblEmpleadoACargo);
			
			txtNombreEmpleadoCargo = new JTextField();
			txtNombreEmpleadoCargo.setEditable(false);
			txtNombreEmpleadoCargo.setColumns(10);
			txtNombreEmpleadoCargo.setBounds(20, 630, 278, 20);
			panel.add(txtNombreEmpleadoCargo);
			
			JLabel lblPorcentajeAplicado = new JLabel("Porcentaje aplicado (%)");
			lblPorcentajeAplicado.setBounds(355, 585, 160, 34);
			panel.add(lblPorcentajeAplicado);
			
			txtPorcentajeAplicado = new JTextField();
			txtPorcentajeAplicado.setEditable(false);
			txtPorcentajeAplicado.setColumns(10);
			txtPorcentajeAplicado.setBounds(355, 630, 139, 20);
			panel.add(txtPorcentajeAplicado);
			
			JButton btnEscogerEmpleado = new JButton("Buscar Empleado");
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
			btnEscogerEmpleado.setBounds(20, 666, 222, 23);
			panel.add(btnEscogerEmpleado);
			
			// Lógica de seguridad para Roles
			if (empleadoLogueado != null) {
				if (empleadoLogueado.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
					txtNombreEmpleadoCargo.setText("Seleccione un empleado...");
					btnEscogerEmpleado.setVisible(true); // El admin puede buscar a quien sea
				} else {
					// Si es vendedor, se le asigna obligatoriamente a él
					empleadoComision = empleadoLogueado;
					txtNombreEmpleadoCargo.setText(empleadoComision.getNombre());
					txtPorcentajeAplicado.setText(String.valueOf(empleadoComision.getComisiones()));
					btnEscogerEmpleado.setVisible(false); // No puede cederle la venta a otro
				}
			}
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnEscogerCliente = new JButton("Buscar Cliente");
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
				            txtDeudaCliente.setText("$ " + clienteActual.calcularDeuda());
				        }
				    }
				});
				buttonPane.add(btnEscogerCliente);
				
				JButton btnRegistrar = new JButton("Registrar");
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
						
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						float precioPlan = planEscogido.getPrecioTotal();
						float comision = empleadoComision.getComisiones();
						LocalDate fechaInicio = LocalDate.now();
						
						// Generamos el contrato
						Contrato cto = new Contrato(clienteActual, empleadoComision, comision, precioPlan, fechaInicio, null, planEscogido);
						
						// Actualizamos los contadores del cliente
						clienteActual.setCantContratosActivos(clienteActual.getCantContratosActivos() + 1);
						clienteActual.getMisContratos().add(cto);
						
						// Sumamos las ventas al empleado
						empleadoComision.setCantidadVentas(empleadoComision.getCantidadVentas() + 1);
						empleadoComision.setMontoVentas(empleadoComision.getMontoVentas() + precioPlan);
						
						// Guardamos en el Singleton
						empresa.getMisContratos().add(cto);
						empresa.GuardarDatos(
								empresa.getMisClientes(), 
								empresa.getMisEmpleados(),
								empresa.getMisPlanes(), 
								empresa.getMisServicios(),
								empresa.getMisUsuarios(), 
								empresa.getMisContratos(),
								empresa.getPagos()
						);
						
						JOptionPane.showMessageDialog(null, "¡Contrato registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
						dispose(); 
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
}