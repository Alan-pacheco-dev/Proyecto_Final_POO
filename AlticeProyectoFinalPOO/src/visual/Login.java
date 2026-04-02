package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtNombreDeUsuario;
	private JTextField txtNombreEmpleado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Login dialog = new Login();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Login() {
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
			
			txtNombreEmpleado = new JTextField();
			txtNombreEmpleado.setColumns(10);
			txtNombreEmpleado.setBounds(20, 158, 333, 20);
			panel.add(txtNombreEmpleado);
			
			JButton btnIngresar = new JButton("Ingresar");
			btnIngresar.setBounds(20, 208, 89, 23);
			panel.add(btnIngresar);
			
			JButton btnSalir = new JButton("Salir");
			btnSalir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			btnSalir.setBounds(264, 208, 89, 23);
			panel.add(btnSalir);
		}
	}
}
