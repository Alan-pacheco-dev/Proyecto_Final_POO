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
	
	// Se dejan solo aquí de forma global
	private JTextField txtNombreDeUsuarioEmp;
	private JTextField txtContraseniaUsuarioEmp;
	private JTextField txtDatosEmpleado;
	
	private Empleado empSeleccionado = null;

	public RegistrarUsuario() {
		super();
		setTitle("Registrar Usuario del Sistema");
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 326);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// Inicializamos las variables globales correctamente
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
			txtDatosEmpleado.setText("Ningún empleado seleccionado..."); // Corrección del error de sintaxis
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
			
			JButton btnSeleccionarEmpleado = new JButton("Seleccionar empleado");
			btnSeleccionarEmpleado.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// 1. Abrimos el modo selector
					ListarEmpleados listaEmps = new ListarEmpleados(true);
					listaEmps.setModal(true);
					listaEmps.setVisible(true);
					
					// 2. Capturamos el empleado
					Empleado capturado = listaEmps.getEmpleadoSeleccionado();
					
					// 3. Validamos
					if (capturado != null) {
						// Evitar que le creen dos usuarios al mismo empleado
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
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.setActionCommand("OK");
				btnRegistrar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// --- VALIDACIONES ---
						if (empSeleccionado == null) {
							JOptionPane.showMessageDialog(null, "Debe seleccionar un empleado para asignarle el usuario.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						String nombreUsuario = txtNombreDeUsuarioEmp.getText().trim();
						String contrasenia = txtContraseniaUsuarioEmp.getText().trim();
						
						if (nombreUsuario.isEmpty() || contrasenia.isEmpty()) {
							JOptionPane.showMessageDialog(null, "Debe llenar los campos de usuario y contraseña.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						// En lugar de forzar "Comercial", podemos usar el rol real que tiene el empleado (Administrativo o Vendedor)
						String rol = empSeleccionado.getRolEmpleado(); 
						
						// --- CREACIÓN Y VINCULACIÓN ---
						Usuario usr = new Usuario(rol, nombreUsuario, contrasenia);
						
						// ¡MUY IMPORTANTE! Le asignamos este nuevo usuario al empleado para que queden vinculados
						empSeleccionado.setMiUsuario(usr);
						
						// Guardamos el usuario en la lista general de la empresa
						empresa.getMisUsuarios().add(usr);
						
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(),
							empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						
						JOptionPane.showMessageDialog(null, "Usuario registrado y vinculado al empleado con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
						dispose();
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
	}
}