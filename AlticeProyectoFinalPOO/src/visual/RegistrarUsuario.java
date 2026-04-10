package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.EmpresaAltice;
import logico.Usuario;

import javax.swing.border.LineBorder;
import java.awt.Color;

public class RegistrarUsuario extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtIdEmpleado;
	private JTextField txtNombreDeUsuarioEmp;
	private JTextField txtContraseniaUsuarioEmp;

	

	/**
	 * Create the dialog.
	 */
	public RegistrarUsuario() {
		super();
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 326);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		final JTextField txtNombreDeUsuarioEmp= new JTextField();
		final JTextField txtContraseniaUsuarioEmp = new JTextField();
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Empleado seleccionado");
			lblNewLabel.setBounds(20, 23, 185, 34);
			panel.add(lblNewLabel);
			
			txtIdEmpleado = new JTextField();
			txtIdEmpleado.setEditable(false);
			txtIdEmpleado.setBounds(20, 68, 457, 20);
			panel.add(txtIdEmpleado);
			txtIdEmpleado.setColumns(10);
			
			
			txtNombreDeUsuarioEmp.setColumns(10);
			txtNombreDeUsuarioEmp.setBounds(20, 188, 185, 20);
			panel.add(txtNombreDeUsuarioEmp);
			
			JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
			lblNombreDeUsuario.setBounds(20, 143, 117, 34);
			panel.add(lblNombreDeUsuario);
			
			
			txtContraseniaUsuarioEmp.setColumns(10);
			txtContraseniaUsuarioEmp.setBounds(288, 188, 185, 20);
			panel.add(txtContraseniaUsuarioEmp);
			
			JLabel lblContrasea = new JLabel("Contrase\u00F1a");
			lblContrasea.setBounds(288, 143, 117, 34);
			panel.add(lblContrasea);
			
			JButton btnNewButton = new JButton("Seleccionar empleado");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//Aqui debe de llamarse a una lista de empleados para que se pueda seleccionar alguno y crearle un usuario a este
					//El formato de presentación del empleado seleccionado sería: ID - Nombre - Rol
				}
			});
			btnNewButton.setBounds(20, 99, 178, 23);
			panel.add(btnNewButton);
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
						EmpresaAltice empresa= EmpresaAltice.getInstance();
						
						String nombreUsuario = txtNombreDeUsuarioEmp.getText().trim();
				        String contrasenia = txtContraseniaUsuarioEmp.getText().trim();
				        String rol = "Comercial";
				        
				        Usuario usr = new Usuario(rol, nombreUsuario, contrasenia);
				        empresa.getMisUsuarios().add(usr);
				        empresa.GuardarDatos(
				            empresa.getMisClientes(), empresa.getMisEmpleados(),
				            empresa.getMisPlanes(), empresa.getMisServicios(),
				            empresa.getMisUsuarios(), empresa.getMisContratos(),
				            empresa.getPagos()
				        );
				        JOptionPane.showMessageDialog(null, "Usuario registrado con éxito");
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
