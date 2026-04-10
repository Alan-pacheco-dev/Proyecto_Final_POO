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
import logico.Usuario;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error, cierre el programa", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		EmpresaAltice empresa= EmpresaAltice.getInstance();
		empresa.CargarDatos(empresa.getMisClientes(), 
				empresa.getMisEmpleados(), 
				empresa.getMisPlanes(), 
				empresa.getMisServicios(), 
				empresa.getMisUsuarios(), 
				empresa.getMisContratos(), 
				empresa.getPagos());
		
		//Comentado para acceder directamente a la pantalla principal sin el login
		/*
		Login login = new Login(this, empresa.getMisUsuarios());
		login.setModal(true);
		login.setVisible(true);
		 */
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1071, 711);
		dim = getToolkit().getScreenSize();
		setSize(dim.width,dim.height);
		setLocationRelativeTo(null);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu_2 = new JMenu("Usuarios");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar");
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem menuItemListarUsuarios = new JMenuItem("Listar");
		menuItemListarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ListarUsuarios
			}
		});
		mnNewMenu_2.add(menuItemListarUsuarios);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		
				RegistrarUsuario regisUser = new RegistrarUsuario();
				regisUser.setModal(true);
				regisUser.setVisible(true);
				
			 }
		});
		
		JMenu mnNewMenu_1 = new JMenu("Empleados");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Registrar");
		mnNewMenu_1.add(mntmNewMenuItem_1);
		
		JMenuItem menuItemListarEmpleados = new JMenuItem("Listar");
		menuItemListarEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmpleados listarEmps = new ListarEmpleados();
				listarEmps.setModal(true);
				listarEmps.setVisible(true);
			}
		});
		mnNewMenu_1.add(menuItemListarEmpleados);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            RegistrarEmpleado regisEmp = new RegistrarEmpleado();
		            regisEmp.setModal(true);
		            regisEmp.setVisible(true);
			 }
		});
		
		JMenu mnNewMenu = new JMenu("Clientes");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem menuItemListarClientes = new JMenuItem("Listar");
		menuItemListarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarClientes listCli = new ListarClientes();
				listCli.setModal(true);
				listCli.setVisible(true);
			}
		});
		mnNewMenu.add(menuItemListarClientes);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
		            RegistrarCliente regisCli = new RegistrarCliente();
		            regisCli.setModal(true);
		            regisCli.setVisible(true);
			 }
		});
		
		JMenu mnContratos = new JMenu("Contratos");
		menuBar.add(mnContratos);
		
		JMenuItem menuItem_6 = new JMenuItem("Registrar");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarContrato regisCto = new RegistrarContrato();
				regisCto.setModal(true);
				regisCto.setVisible(true);
			}
		});
		mnContratos.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("Listar");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//ListarContratos
				//Este listarContratos debe permitir filtro según el cliente y si está activo el contrato o no
			}
		});
		mnContratos.add(menuItem_7);
		
		JMenu menuPlanes = new JMenu("Planes");
		menuBar.add(menuPlanes);
		
		JMenuItem menuItem = new JMenuItem("Registrar");
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPlan regisPlan = new RegistrarPlan();
				regisPlan.setModal(true);
				regisPlan.setVisible(true);
			}
		});
		menuPlanes.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Listar");
		menuPlanes.add(menuItem_1);
		
		JMenu menuServicios = new JMenu("Servicios");
		menuBar.add(menuServicios);
		
		JMenuItem menuItem_2 = new JMenuItem("Registrar");
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarServicio regisServi = new RegistrarServicio();
				regisServi.setModal(true);
				regisServi.setVisible(true);
			}
		});
		menuServicios.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("Listar");
		menuServicios.add(menuItem_3);
		
		JMenu menuPagos = new JMenu("Pagos");
		menuBar.add(menuPagos);
		
		JMenuItem menuItem_4 = new JMenuItem("Registrar");
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarPago regisPago = new RegistrarPago();
				regisPago.setModal(true);
				regisPago.setVisible(true);
			}
		});
		menuPagos.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("Listar");
		menuPagos.add(menuItem_5);
		
		//Comentado para acceder directamente a la pantalla principal sin el login
		/*
		Usuario usuarioActual = EmpresaAltice.getLoginUser();
	    if (!usuarioActual.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
	        mnNewMenu_1.setVisible(false);
	        mnNewMenu_2.setVisible(false); 
	    }
		*/
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
