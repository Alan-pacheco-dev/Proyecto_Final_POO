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
	private JTextField txtCodigoEmpleado;
	private JTextField txtNombreEmpleado;
	private JTextField txtSalarioEmpleado;
	private JTextField textField_4;
	private JTextField txtNombreDeUsuarioEmp;
	private JTextField txtContraseniaUsuarioEmp;

	

	/**
	 * Create the dialog.
	 */
	public RegistrarUsuario(JFrame owner) {
		super(owner, true);
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 469);
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
			
			JLabel lblNewLabel = new JLabel("ID del Empleado");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtIdEmpleado = new JTextField();
			txtIdEmpleado.setEditable(false);
			txtIdEmpleado.setBounds(20, 82, 185, 20);
			panel.add(txtIdEmpleado);
			txtIdEmpleado.setColumns(10);
			
			JLabel label = new JLabel("C\u00F3digo del empleado");
			label.setBounds(288, 37, 222, 34);
			panel.add(label);
			
			txtCodigoEmpleado = new JTextField();
			txtCodigoEmpleado.setEditable(false);
			txtCodigoEmpleado.setColumns(10);
			txtCodigoEmpleado.setBounds(288, 82, 185, 20);
			panel.add(txtCodigoEmpleado);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del Empleado");
			lblNombreDelEmpleado.setBounds(20, 113, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreEmpleado = new JTextField();
			txtNombreEmpleado.setEditable(false);
			txtNombreEmpleado.setColumns(10);
			txtNombreEmpleado.setBounds(20, 158, 453, 20);
			panel.add(txtNombreEmpleado);
			
			JLabel lblSalario = new JLabel("Salario del empleado");
			lblSalario.setBounds(20, 189, 117, 34);
			panel.add(lblSalario);
			
			txtSalarioEmpleado = new JTextField();
			txtSalarioEmpleado.setEditable(false);
			txtSalarioEmpleado.setColumns(10);
			txtSalarioEmpleado.setBounds(20, 234, 185, 20);
			panel.add(txtSalarioEmpleado);
			
			textField_4 = new JTextField();
			textField_4.setEditable(false);
			textField_4.setColumns(10);
			textField_4.setBounds(288, 234, 185, 20);
			panel.add(textField_4);
			
			JLabel lblComision = new JLabel("Porcentaje de comisi\u00F3n por venta");
			lblComision.setBounds(288, 189, 222, 34);
			panel.add(lblComision);
			
			
			txtNombreDeUsuarioEmp.setColumns(10);
			txtNombreDeUsuarioEmp.setBounds(20, 315, 185, 20);
			panel.add(txtNombreDeUsuarioEmp);
			
			JLabel lblNombreDeUsuario = new JLabel("Nombre de Usuario");
			lblNombreDeUsuario.setBounds(20, 270, 117, 34);
			panel.add(lblNombreDeUsuario);
			
			
			txtContraseniaUsuarioEmp.setColumns(10);
			txtContraseniaUsuarioEmp.setBounds(288, 315, 185, 20);
			panel.add(txtContraseniaUsuarioEmp);
			
			JLabel lblContrasea = new JLabel("Contrase\u00F1a");
			lblContrasea.setBounds(288, 270, 117, 34);
			panel.add(lblContrasea);
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
