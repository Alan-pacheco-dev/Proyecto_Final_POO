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

import logico.EmpresaAltice;
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
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
		        System.exit(0);
		    }
		});
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
					String nombre = txtNombreDeUsuario.getText().trim();
			        char[] con = txtContrsenia.getPassword();
			        String contrasenia = new String(con);
			        
			        
			        Usuario usuarioEncontrado = null;
			        
			        boolean encontrado = false;
			        int ind = 0;

			        while(ind < usuarios.size() && !encontrado) {
			            if(usuarios.get(ind).getNombreUsuario().equals(nombre)) {
			                usuarioEncontrado = usuarios.get(ind);
			                encontrado = true;
			            }
			            ind++;
			        }

			        if (usuarioEncontrado == null) {
			            JOptionPane.showMessageDialog(null, "El usuario ingresado no existe");
			        } 
			        else if (!usuarioEncontrado.getContrasenia().equals(contrasenia)) {
			            JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
			        } 
			        else {
			            EmpresaAltice.getInstance().setLoginUser(usuarioEncontrado);
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
