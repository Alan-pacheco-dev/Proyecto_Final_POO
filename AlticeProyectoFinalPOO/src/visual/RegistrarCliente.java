package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JRadioButton;

import logico.Cliente;
import logico.EmpresaAltice;

public class RegistrarCliente extends JDialog {

	private Cliente miCliente = null;
	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
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
	
	private EmpresaAltice empresa = EmpresaAltice.getInstance();

	public RegistrarCliente(Cliente cli) {
		super();
		miCliente= cli;
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 539); 
		setLocationRelativeTo(null);
		
		if (miCliente != null) {
			setTitle("Actualizar Cliente");
		} else {
			setTitle("Registrar Cliente");
		}
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		txtNombreCliente = new JTextField();
		txtEmailCliente = new JTextField();
		txtDireccionCliente = new JTextField();
		txtCedulaRNC = new JTextField();
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(20, 20, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
			txtID.setBounds(20, 55, 185, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblCdigoDelCliente = new JLabel("Código del Cliente");
			lblCdigoDelCliente.setBounds(288, 20, 222, 34);
			panel.add(lblCdigoDelCliente);
			
			txtCodigoCliente = new JTextField();
			txtCodigoCliente.setEditable(false);
			txtCodigoCliente.setColumns(10);
			String anioActual = String.valueOf(LocalDate.now().getYear());
			txtCodigoCliente.setText("CLI-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
			txtCodigoCliente.setBounds(288, 55, 185, 20);
			panel.add(txtCodigoCliente);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(20, 90, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 125, 453, 20);
			panel.add(txtNombreCliente);
			
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(20, 222, 148, 34);
			panel.add(lblEmail);
			
			txtEmailCliente.setColumns(10);
			txtEmailCliente.setBounds(20, 257, 453, 20);
			panel.add(txtEmailCliente);
			
			JLabel lblDireccin = new JLabel("Dirección");
			lblDireccin.setBounds(20, 292, 148, 34);
			panel.add(lblDireccin);
			
			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(20, 327, 453, 20);
			panel.add(txtDireccionCliente);
			
			JLabel lblEsEmpresa = new JLabel("¿Es empresa?");
			lblEsEmpresa.setBounds(20, 366, 117, 34);
			panel.add(lblEsEmpresa);
			
			rdbtnSiEsEmpresa = new JRadioButton("Si");
			rdbtnSiEsEmpresa.setBounds(20, 401, 60, 23);
			panel.add(rdbtnSiEsEmpresa);
			
			rdbtnNoEsEmpresa = new JRadioButton("No");
			rdbtnNoEsEmpresa.setSelected(true);
			rdbtnNoEsEmpresa.setBounds(82, 401, 60, 23);
			panel.add(rdbtnNoEsEmpresa);
			
			ButtonGroup bgEmpresa = new ButtonGroup();
			bgEmpresa.add(rdbtnSiEsEmpresa);
			bgEmpresa.add(rdbtnNoEsEmpresa);
			
			JLabel lblComision = new JLabel("Deuda mensual del cliente");
			lblComision.setBounds(288, 366, 222, 34);
			panel.add(lblComision);
			
			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setText("$ 0.0");
			txtDeudaCliente.setEditable(false);
			txtDeudaCliente.setColumns(10);
			txtDeudaCliente.setBounds(288, 401, 185, 20);
			panel.add(txtDeudaCliente);
			
			lblCedulaRNC = new JLabel("Cédula");
			lblCedulaRNC.setBounds(20, 156, 148, 34);
			panel.add(lblCedulaRNC);
			
			txtCedulaRNC.setColumns(10);
			txtCedulaRNC.setBounds(20, 191, 453, 20);
			panel.add(txtCedulaRNC);
			
			rdbtnSiEsEmpresa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblCedulaRNC.setText("RNC");
					String anio = String.valueOf(LocalDate.now().getYear());
					
					String idActual = EmpresaAltice.getInstance().idClientes + "";
					if(miCliente != null) {
						idActual = miCliente.getIdPersona().split("- ")[1]; 
					}
					
					txtCodigoCliente.setText("CLI-EMPR-" + anio + "-" + idActual);
				}
			});

			rdbtnNoEsEmpresa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					lblCedulaRNC.setText("Cédula");
					String anio = String.valueOf(LocalDate.now().getYear());
					
					String idActual = EmpresaAltice.getInstance().idClientes + "";
					if(miCliente != null) {
						idActual = miCliente.getIdPersona().split("- ")[1]; 
					}
					
					txtCodigoCliente.setText("CLI-FIS-" + anio + "-" + idActual);
				}
			});
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton();
				
				if (miCliente != null) {
					btnRegistrar.setText("Actualizar");
				} else {
					btnRegistrar.setText("Registrar");
				}
				
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String nombre = txtNombreCliente.getText().trim();
						String direccion = txtDireccionCliente.getText().trim();
						String email = txtEmailCliente.getText().trim();
						String cedulaOrRNC = txtCedulaRNC.getText().trim();
						boolean esEmpresa = rdbtnSiEsEmpresa.isSelected();
						String codigo = txtCodigoCliente.getText();

						
						if (nombre.isEmpty() || email.isEmpty() || direccion.isEmpty() || cedulaOrRNC.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos requeridos.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
							return;
						}

						if (!email.contains("@") || !email.contains(".")) {
							JOptionPane.showMessageDialog(null, "Por favor, ingrese un correo electrónico válido (ej: usuario@correo.com).", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
							return;
						}

						String tipoDoc = "";
						if (esEmpresa == true) {
							tipoDoc = "El RNC";
						} else {
							tipoDoc = "La Cédula";
						}

						if (!cedulaOrRNC.matches("[0-9]+")) {
							JOptionPane.showMessageDialog(null, tipoDoc + " debe contener únicamente números (sin guiones, espacios ni letras).", "Formato incorrecto", JOptionPane.WARNING_MESSAGE);
							return;
						}

						if (esEmpresa == true) {
							if (cedulaOrRNC.length() != 9) {
								JOptionPane.showMessageDialog(null, "El RNC debe tener exactamente 9 dígitos.", "Longitud incorrecta", JOptionPane.WARNING_MESSAGE);
								return;
							}
						} else {
							if (cedulaOrRNC.length() != 11) {
								JOptionPane.showMessageDialog(null, "La Cédula debe tener exactamente 11 dígitos.", "Longitud incorrecta", JOptionPane.WARNING_MESSAGE);
								return;
							}
						}
						
						EmpresaAltice empresa = EmpresaAltice.getInstance();

						if (miCliente == null) {
							Cliente cli = new Cliente(null, nombre, direccion, email, 0, esEmpresa, cedulaOrRNC);
							cli.setCodigoCliente(codigo);
							
							empresa.getMisClientes().add(cli);
							JOptionPane.showMessageDialog(null, "Cliente registrado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
							clean();
						} else {
							miCliente.setNombre(nombre);
							miCliente.setDireccion(direccion);
							miCliente.setEmail(email);
							miCliente.setRnc(cedulaOrRNC);
							miCliente.setEsEmpresa(esEmpresa);
							miCliente.setCodigoCliente(codigo);
							
							JOptionPane.showMessageDialog(null, "Cliente actualizado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
							dispose();
						}
						
						empresa.GuardarDatos(
								empresa.getMisClientes(), empresa.getMisEmpleados(),
								empresa.getMisPlanes(), empresa.getMisServicios(),
								empresa.getMisUsuarios(), empresa.getMisContratos(),
								empresa.getPagos()
						);
					}
				});
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
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
		
		loadCliente();
	}
	
	private void loadCliente() {
		if (miCliente != null) {
			txtID.setText(miCliente.getIdPersona());
			txtCodigoCliente.setText(miCliente.getCodigoCliente());
			txtNombreCliente.setText(miCliente.getNombre());
			txtEmailCliente.setText(miCliente.getEmail());
			txtDireccionCliente.setText(miCliente.getDireccion());
			txtCedulaRNC.setText(miCliente.getRnc());
			
			if (miCliente.isEsEmpresa() == true) {
				rdbtnSiEsEmpresa.doClick(); 
			} else {
				rdbtnNoEsEmpresa.doClick();
			}
		}
	}
	
	private void clean() {
		txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigoCliente.setText("CLI-FIS-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
		txtNombreCliente.setText("");
		txtDireccionCliente.setText("");
		txtEmailCliente.setText("");
		txtCedulaRNC.setText("");
		rdbtnNoEsEmpresa.doClick(); 
	}
}