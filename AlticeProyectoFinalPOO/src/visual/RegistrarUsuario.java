package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Empleado;
import logico.EmpresaAltice;
import logico.Usuario;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class RegistrarUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	
	private JTextField txtNombreDeUsuarioEmp;
	private JTextField txtContraseniaUsuarioEmp;
	private JTextField txtDatosEmpleado;
	
	private Empleado empSeleccionado = null;
	private Usuario miUsuario = null;
	
	private JButton btnSeleccionarEmpleado;

	public RegistrarUsuario(Usuario user) {
		super();
		
		this.miUsuario = user;
		
		if (miUsuario != null) {
			setTitle("Actualizar Usuario del Sistema");
		} else {
			setTitle("Registrar Usuario del Sistema");
		}
		
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 326);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		txtNombreDeUsuarioEmp = new JTextField();
		txtContraseniaUsuarioEmp = new JTextField();
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Empleado seleccionado");
			lblNewLabel.setBounds(20, 23, 185, 34);
			panel.add(lblNewLabel);
			
			txtDatosEmpleado = new JTextField();
			txtDatosEmpleado.setEditable(false);
			txtDatosEmpleado.setText("Ningún empleado seleccionado..."); 
			txtDatosEmpleado.setBounds(20, 68, 457, 20);
			panel.add(txtDatosEmpleado);
			txtDatosEmpleado.setColumns(10);
			
			txtNombreDeUsuarioEmp.setColumns(10);
			txtNombreDeUsuarioEmp.setBounds(20, 188, 185, 20);
			panel.add(txtNombreDeUsuarioEmp);
			
			JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
			lblNombreDeUsuario.setBounds(20, 143, 172, 34);
			panel.add(lblNombreDeUsuario);
			
			txtContraseniaUsuarioEmp.setColumns(10);
			txtContraseniaUsuarioEmp.setBounds(288, 188, 185, 20);
			panel.add(txtContraseniaUsuarioEmp);
			
			JLabel lblContrasea = new JLabel("Contraseña");
			lblContrasea.setBounds(288, 143, 117, 34);
			panel.add(lblContrasea);
			
			btnSeleccionarEmpleado = new JButton("Seleccionar empleado");
			btnSeleccionarEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ListarEmpleados listaEmps = new ListarEmpleados(true);
					listaEmps.setModal(true);
					listaEmps.setVisible(true);
					
					Empleado capturado = listaEmps.getEmpleadoSeleccionado();
					
					if (capturado != null) {
						if (capturado.getMiUsuario() != null) {
							JOptionPane.showMessageDialog(null, "Este empleado ya tiene un usuario asignado en el sistema.", "Aviso", JOptionPane.WARNING_MESSAGE);
						} else {
							empSeleccionado = capturado;
							txtDatosEmpleado.setText(empSeleccionado.getCodigoEmpleado() + " - " + empSeleccionado.getNombre() + " - " + empSeleccionado.getRolEmpleado());
						}
					}
				}
			});
			btnSeleccionarEmpleado.setBounds(20, 99, 252, 23);
			panel.add(btnSeleccionarEmpleado);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton();
				
				if (miUsuario != null) {
					btnRegistrar.setText("Actualizar");
				} else {
					btnRegistrar.setText("Registrar");
				}
				
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						String nombreUsuario = txtNombreDeUsuarioEmp.getText().trim();
						String contrasenia = txtContraseniaUsuarioEmp.getText().trim();
						
						if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Debe llenar los campos de usuario y contraseña.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						if (miUsuario == null) {
							if (empSeleccionado == null) {
								JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para asignarle el usuario.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
								return;
							}
							
							String rol = empSeleccionado.getRolEmpleado(); 
							Usuario usr = new Usuario(rol, nombreUsuario, contrasenia);
							empSeleccionado.setMiUsuario(usr);
							empresa.getMisUsuarios().add(usr);
							
							JOptionPane.showMessageDialog(null, "Usuario registrado y vinculado al empleado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
						} 
						else {
							miUsuario.setNombreUsuario(nombreUsuario);
							miUsuario.setContrasenia(contrasenia);
							
							JOptionPane.showMessageDialog(null, "Usuario actualizado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
						}
						
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(),
							empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						
						dispose();
					}
				});
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
				buttonPane.add(btnCancelar);
			}
		}
		
		loadUsuario();
	}


	private void loadUsuario() {
		if (miUsuario != null) {
			txtNombreDeUsuarioEmp.setText(miUsuario.getNombreUsuario());
			txtContraseniaUsuarioEmp.setText(miUsuario.getContrasenia());
			
			btnSeleccionarEmpleado.setEnabled(false);
			
			for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
				if (emp.getMiUsuario() != null) {
					if (emp.getMiUsuario().getIdUsuario().equals(miUsuario.getIdUsuario())) {
						empSeleccionado = emp;
						txtDatosEmpleado.setText(empSeleccionado.getCodigoEmpleado() + " - " + empSeleccionado.getNombre() + " - " + empSeleccionado.getRolEmpleado());
						break;
					}
				}
			}
		}
	}
}