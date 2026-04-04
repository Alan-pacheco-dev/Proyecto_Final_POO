package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Usuario;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.security.acl.Owner;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtNombreDeUsuario;
	private JPasswordField txtContrsenia;
	private ArrayList<Usuario> usuarios;


	/**
	 * Create the dialog.
	 */
	public Login(JFrame owner, ArrayList<Usuario> usuarios) {
		super(owner, true);
		this.usuarios =usuarios;
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(403, 323);
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
			
			JLabel lblNombreDeUsuario = new JLabel("Nombre de usuario");
			lblNombreDeUsuario.setBounds(20, 37, 117, 34);
			panel.add(lblNombreDeUsuario);
			
			txtNombreDeUsuario = new JTextField();
			txtNombreDeUsuario.setBounds(20, 82, 333, 20);
			panel.add(txtNombreDeUsuario);
			txtNombreDeUsuario.setColumns(10);
			
			JLabel lblContrasenia = new JLabel("Contrase\u00F1a");
			lblContrasenia.setBounds(20, 113, 148, 34);
			panel.add(lblContrasenia);
			
			JButton btnIngresar = new JButton("Ingresar");
			btnIngresar.setBounds(20, 208, 89, 23);
			btnIngresar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String nombre= txtNombreDeUsuario.getText().trim();
					char[] con= txtContrsenia.getPassword();
					String contrsenia = new String(con);
					boolean encontrado=false; 
					boolean correcto=false;
					for(Usuario u:usuarios) {
						if(u.getNombreUsuario().equals(nombre)) {
							encontrado=true;
							break;
						}
					}
					
					for(Usuario u: usuarios) {
						if(u.getContrasenia().equals(contrsenia)) {
							correcto=true;
							break;
						}
					}
					
					if(!encontrado){
					    JOptionPane.showMessageDialog(btnIngresar, "El usuario ingresado no existe", "Error", JOptionPane.ERROR_MESSAGE);
					}else if(!correcto){
					    JOptionPane.showMessageDialog(btnIngresar, "La contraseña es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
					}else {
					    dispose();
					}
					
					
				}
			});
			panel.add(btnIngresar);
			
			JButton btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
			btnSalir.setBounds(264, 208, 89, 23);
			panel.add(btnSalir);
			
			txtContrsenia = new JPasswordField();
			txtContrsenia.setBounds(20, 148, 333, 20);
			panel.add(txtContrsenia);
		}
	}
}
