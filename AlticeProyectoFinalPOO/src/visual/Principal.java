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
import javax.swing.JOptionPane;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Dimension dim;

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

	public Principal() {
		EmpresaAltice empresa = EmpresaAltice.getInstance();
		empresa.CargarDatos(empresa.getMisClientes(), 
				empresa.getMisEmpleados(), 
				empresa.getMisPlanes(), 
				empresa.getMisServicios(), 
				empresa.getMisUsuarios(), 
				empresa.getMisContratos(), 
				empresa.getPagos());
		
		empresa.actualizarContadores();
		empresa.refrescarConteosContratos();
		empresa.actualizarDeudaClientes();
		
		// --- ALERTA DE PAGOS AL INICIO ---
		int pagosGenerados = empresa.generarPagosMensuales();
		if (pagosGenerados > 0) {
			JOptionPane.showMessageDialog(null,
				"Se generaron " + pagosGenerados + " nuevos pagos mensuales pendientes",
				"Información", JOptionPane.INFORMATION_MESSAGE);
		}
		
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
		
		// --- MENÚ USUARIOS ---
		JMenu menuUsuarios = new JMenu("Usuarios");
		menuBar.add(menuUsuarios);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar");
		menuUsuarios.add(mntmNewMenuItem_2);
		
		JMenuItem menuItemListarUsuarios = new JMenuItem("Listar");
		menuItemListarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarUsuarios listarUsers = new ListarUsuarios();
				listarUsers.setModal(true);
				listarUsers.setVisible(true);
			}
		});
		menuUsuarios.add(menuItemListarUsuarios);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				RegistrarUsuario regisUser = new RegistrarUsuario(null);
				regisUser.setModal(true);
				regisUser.setVisible(true);
			 }
		});
		
		// --- MENÚ EMPLEADOS ---
		JMenu menuEmpleados = new JMenu("Empleados");
		menuBar.add(menuEmpleados);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Registrar");
		menuEmpleados.add(mntmNewMenuItem_1);
		
		JMenuItem menuItemListarEmpleados = new JMenuItem("Listar");
		menuItemListarEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmpleados listarEmps = new ListarEmpleados(false);
				listarEmps.setModal(true);
				listarEmps.setVisible(true);
			}
		});
		menuEmpleados.add(menuItemListarEmpleados);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				RegistrarEmpleado regisEmp = new RegistrarEmpleado(null);
				regisEmp.setModal(true);
				regisEmp.setVisible(true);
			 }
		});
		
		// --- MENÚ CLIENTES ---
		JMenu menuClientes = new JMenu("Clientes");
		menuBar.add(menuClientes);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		menuClientes.add(mntmNewMenuItem);
		
		JMenuItem menuItemListarClientes = new JMenuItem("Listar");
		menuItemListarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarClientes listCli = new ListarClientes();
				listCli.setModal(true);
				listCli.setVisible(true);
			}
		});
		menuClientes.add(menuItemListarClientes);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			 public void actionPerformed(ActionEvent e) {
				RegistrarCliente regisCli = new RegistrarCliente(null);
				regisCli.setModal(true);
				regisCli.setVisible(true);
			 }
		});
		
		// --- MENÚ CONTRATOS ---
		JMenu menuContratos = new JMenu("Contratos");
		menuBar.add(menuContratos);
		
		JMenuItem menuItem_6 = new JMenuItem("Registrar");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarContrato regisCon = new RegistrarContrato(null, null);
				regisCon.setModal(true);
				regisCon.setVisible(true);
			}
		});
		menuContratos.add(menuItem_6);
		
		JMenuItem menuItem_7 = new JMenuItem("Listar");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarContratosGeneral listarCtos = new ListarContratosGeneral(false);
				listarCtos.setModal(true);
				listarCtos.setVisible(true);
			}
		});
		menuContratos.add(menuItem_7);
		
		// --- MENÚ PLANES ---
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
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarPlanes ventanaPlanes = new ListarPlanes(false);
				ventanaPlanes.setVisible(true);
			}
		});
		menuPlanes.add(menuItem_1);
		
		// --- MENÚ SERVICIOS ---
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
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarServicios listarServis = new ListarServicios("Todos", false);
				listarServis.setModal(true);
				listarServis.setVisible(true);
			}
		});
		menuServicios.add(menuItem_3);
		
		// --- MENÚ PAGOS ---
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
		
		JMenuItem menuItemGenerarPagos = new JMenuItem("Generar");
		menuItemGenerarPagos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, "¿Desea generar los pagos mensuales pendientes?", "Confirmación", 
						JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					// Guardamos el int que nos retorna y mostramos el resultado
					int generadosManuales = EmpresaAltice.getInstance().generarPagosMensuales();
					if (generadosManuales > 0) {
						JOptionPane.showMessageDialog(null, "Se generaron " + generadosManuales + " pagos correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "El sistema está al día. No hay pagos nuevos por generar.", "Información", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		menuPagos.add(menuItemGenerarPagos);
		menuPagos.add(menuItem_4);
		
		JMenuItem menuItem_5 = new JMenuItem("Listar");
		menuPagos.add(menuItem_5);
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarPagos listPag= new ListarPagos();
				listPag.setModal(true);
				listPag.setVisible(true);
			}
		});
		
		// --- MENÚ REPORTES ---
		JMenu menuReportes = new JMenu("Reportes");
		menuBar.add(menuReportes);
		
		JMenuItem mntmReporteGeneral = new JMenuItem("Reporte General");
		menuReportes.add(mntmReporteGeneral);
		
		JMenuItem mntmReporteDeRendimiento = new JMenuItem("Reporte de Rendimiento");
		menuReportes.add(mntmReporteDeRendimiento);
		
		JMenuItem mntmReporteDeEmpleados = new JMenuItem("Reporte de Mercado");
		menuReportes.add(mntmReporteDeEmpleados);
		
		// --- MENÚ RESPALDO ---
		JMenu menuRespaldo = new JMenu("Respaldo");
		menuBar.add(menuRespaldo);
		
		JMenuItem menuItemRespaldo = new JMenuItem("Generar Respaldo en Servidor");
		menuItemRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null, 
						"¿Desea generar un respaldo de toda la base de datos en el servidor?", 
						"Confirmación de Respaldo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				
				if (confirm == JOptionPane.YES_OPTION) {
					try {
						java.net.Socket socket = new java.net.Socket("localhost", 7000);
						java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(new java.io.BufferedOutputStream(socket.getOutputStream()));
						out.flush();
						
						EmpresaAltice empresaParaEnviar = EmpresaAltice.getInstance();
						out.writeObject(empresaParaEnviar);
						out.flush();
						
						JOptionPane.showMessageDialog(null, "El respaldo se ha enviado y guardado en el servidor exitosamente.", "Respaldo Completado", JOptionPane.INFORMATION_MESSAGE);
						
						out.close();
						socket.close();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "No se pudo conectar con el servidor. Verifique que esté encendido.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		menuRespaldo.add(menuItemRespaldo);
		
		JMenuItem menuItemRestaurar = new JMenuItem("Restaurar desde archivo...");
		menuItemRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				fileChooser.setDialogTitle("Seleccione el archivo de respaldo (.txt)");
				
				int seleccion = fileChooser.showOpenDialog(null);
				
				if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
					java.io.File archivoSeleccionado = fileChooser.getSelectedFile();
					
					int confirm = JOptionPane.showConfirmDialog(null, 
							"ALERTA: ¿Está seguro de restaurar el sistema con este archivo?\nSe borrarán los datos actuales y se reemplazarán por los del respaldo.", 
							"Confirmar Restauración", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
					
					if (confirm == JOptionPane.YES_OPTION) {
						boolean exito = EmpresaAltice.getInstance().CargarDatosDesdeRespaldo(archivoSeleccionado.getAbsolutePath());
						
						if (exito) {
							JOptionPane.showMessageDialog(null, "Respaldo restaurado con éxito. El sistema ha vuelto al estado guardado.", "Restauración Exitosa", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Error al leer el archivo. Puede que esté corrupto o no sea un respaldo válido.", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		menuRespaldo.add(menuItemRestaurar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}
}