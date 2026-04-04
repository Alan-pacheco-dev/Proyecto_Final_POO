package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logico.EmpresaAltice;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Dimension dim;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		EmpresaAltice empresa= new EmpresaAltice();
		empresa.CargarDatos(empresa.getMisClientes(), 
				empresa.getMisEmpleados(), 
				empresa.getMisPlanes(), 
				empresa.getMisServicios(), 
				empresa.getMisUsuarios(), 
				empresa.getMisContratos(), 
				empresa.getPagos());
		
		Login login = new Login(this, empresa.getMisUsuarios());
		login.setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 711);
		dim = getToolkit().getScreenSize();
		setSize(dim.width,dim.height);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Clientes");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		mnNewMenu.add(mntmNewMenuItem);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            RegistrarCliente dialog = new RegistrarCliente(Principal.this);
		            dialog.setVisible(true);
			 }
		});
		
		JMenu mnNewMenu_1 = new JMenu("Empleados");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Registrar");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            RegistrarEmpleado dialog = new RegistrarEmpleado(Principal.this);
		            dialog.setVisible(true);
			 }
		});
		
		JMenu mnNewMenu_2 = new JMenu("Usuarios");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar");
		mnNewMenu_2.add(mntmNewMenuItem_2);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            RegistrarUsuario dialog = new RegistrarUsuario(Principal.this);
		            dialog.setVisible(true);
			 }
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
