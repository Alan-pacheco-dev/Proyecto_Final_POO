package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import logico.Empleado;
import logico.EmpresaAltice;
import logico.Usuario;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Dimension dim;
	private Image imagenFondo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Ha ocurrido un error, cierre el programa", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	public Principal() {
		EmpresaAltice empresa = EmpresaAltice.getInstance();
		empresa.CargarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(), empresa.getMisPlanes(),
				empresa.getMisServicios(), empresa.getMisUsuarios(), empresa.getMisContratos(), empresa.getPagos());

		empresa.actualizarContadores();
		empresa.refrescarConteosContratos();
		empresa.actualizarDeudaClientes();
		empresa.generarPagosMensuales();

		Login login = new Login(this, empresa.getMisUsuarios());
		login.setModal(true);
		login.setVisible(true);

		Color alticeBlue = Color.decode("#0066FF");
		Font menuFont = new Font("SansSerif", Font.BOLD, 14);
		Font itemFont = new Font("SansSerif", Font.PLAIN, 14);

		setTitle("Sistema de Gestión - Altice");
		setResizable(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar el programa?",
						"Confirmar cierre", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (respuesta == JOptionPane.YES_OPTION) {
					EmpresaAltice.getInstance().GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
							empresa.getMisContratos(), empresa.getPagos());
					System.exit(0);
				}
			}
		});

		dim = getToolkit().getScreenSize();
		setSize(dim.width, dim.height);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(alticeBlue);
		menuBar.setOpaque(true);
		menuBar.setBorder(new EmptyBorder(5, 10, 5, 10));
		setJMenuBar(menuBar);

		JMenu menuUsuarios = createStyledMenu(" Usuarios ", menuFont);
		menuBar.add(menuUsuarios);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Registrar");
		mntmNewMenuItem_2.setFont(itemFont);
		menuUsuarios.add(mntmNewMenuItem_2);

		JMenuItem menuItemListarUsuarios = new JMenuItem("Listar");
		menuItemListarUsuarios.setFont(itemFont);
		menuItemListarUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarUsuarios listarUsers = new ListarUsuarios();
				listarUsers.setModal(true);
				listarUsers.setVisible(true);
			}
		});
		menuUsuarios.add(menuItemListarUsuarios);
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarUsuario regisUser = new RegistrarUsuario(null);
				regisUser.setModal(true);
				regisUser.setVisible(true);
			}
		});

		JMenu menuEmpleados = createStyledMenu(" Empleados ", menuFont);
		menuBar.add(menuEmpleados);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Registrar");
		mntmNewMenuItem_1.setFont(itemFont);
		menuEmpleados.add(mntmNewMenuItem_1);

		JMenuItem menuItemListarEmpleados = new JMenuItem("Listar");
		menuItemListarEmpleados.setFont(itemFont);
		menuItemListarEmpleados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarEmpleados listarEmps = new ListarEmpleados(false);
				listarEmps.setModal(true);
				listarEmps.setVisible(true);
			}
		});
		menuEmpleados.add(menuItemListarEmpleados);
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarEmpleado regisEmp = new RegistrarEmpleado(null);
				regisEmp.setModal(true);
				regisEmp.setVisible(true);
			}
		});

		JMenu menuClientes = createStyledMenu(" Clientes ", menuFont);
		menuBar.add(menuClientes);

		JMenuItem mntmNewMenuItem = new JMenuItem("Registrar");
		mntmNewMenuItem.setFont(itemFont);
		menuClientes.add(mntmNewMenuItem);

		JMenuItem menuItemListarClientes = new JMenuItem("Listar");
		menuItemListarClientes.setFont(itemFont);
		menuItemListarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarClientes listCli = new ListarClientes();
				listCli.setModal(true);
				listCli.setVisible(true);
			}
		});
		menuClientes.add(menuItemListarClientes);
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarCliente regisCli = new RegistrarCliente(null);
				regisCli.setModal(true);
				regisCli.setVisible(true);
			}
		});

		JMenu menuContratos = createStyledMenu(" Contratos ", menuFont);
		menuBar.add(menuContratos);

		JMenuItem menuItem_6 = new JMenuItem("Registrar");
		menuItem_6.setFont(itemFont);
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				Usuario usuarioActual = EmpresaAltice.getLoginUser();
				Empleado empleadoSesion = EmpresaAltice.getInstance().buscarEmpleadoPorUsuario(usuarioActual);
				RegistrarContrato regisCon = new RegistrarContrato(null, empleadoSesion);
				regisCon.setModal(true);
				regisCon.setVisible(true);
			}
		});
		menuContratos.add(menuItem_6);

		JMenuItem menuItem_7 = new JMenuItem("Listar");
		menuItem_7.setFont(itemFont);
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarContratosGeneral listarCtos = new ListarContratosGeneral(false);
				listarCtos.setModal(true);
				listarCtos.setVisible(true);
			}
		});
		menuContratos.add(menuItem_7);

		JMenu menuPlanes = createStyledMenu(" Planes ", menuFont);
		menuBar.add(menuPlanes);

		JMenuItem menuItem = new JMenuItem("Registrar");
		menuItem.setFont(itemFont);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarPlan regisPlan = new RegistrarPlan();
				regisPlan.setModal(true);
				regisPlan.setVisible(true);
			}
		});
		menuPlanes.add(menuItem);

		JMenuItem menuItem_1 = new JMenuItem("Listar");
		menuItem_1.setFont(itemFont);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarPlanes ventanaPlanes = new ListarPlanes(false);
				ventanaPlanes.setVisible(true);
			}
		});
		menuPlanes.add(menuItem_1);

		JMenu menuServicios = createStyledMenu(" Servicios ", menuFont);
		menuBar.add(menuServicios);

		JMenuItem menuItem_2 = new JMenuItem("Registrar");
		menuItem_2.setFont(itemFont);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarServicio regisServi = new RegistrarServicio();
				regisServi.setModal(true);
				regisServi.setVisible(true);
			}
		});
		menuServicios.add(menuItem_2);

		JMenuItem menuItem_3 = new JMenuItem("Listar");
		menuItem_3.setFont(itemFont);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarServicios listarServis = new ListarServicios("Todos", false);
				listarServis.setModal(true);
				listarServis.setVisible(true);
			}
		});
		menuServicios.add(menuItem_3);

		JMenu menuPagos = createStyledMenu(" Pagos ", menuFont);
		menuBar.add(menuPagos);

		JMenuItem menuItem_4 = new JMenuItem("Registrar");
		menuItem_4.setFont(itemFont);
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				RegistrarPago regisPago = new RegistrarPago();
				regisPago.setModal(true);
				regisPago.setVisible(true);
			}
		});
		menuPagos.add(menuItem_4);

		JMenuItem menuItem_5 = new JMenuItem("Listar");
		menuItem_5.setFont(itemFont);
		menuPagos.add(menuItem_5);
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ListarPagos listPag = new ListarPagos();
				listPag.setModal(true);
				listPag.setVisible(true);
			}
		});

		JMenu menuSoporte = createStyledMenu(" Soporte ", menuFont);
		menuBar.add(menuSoporte);

		JMenuItem mntmPanelDiagnostico = new JMenuItem("Panel de Diagnóstico");
		mntmPanelDiagnostico.setFont(itemFont);
		mntmPanelDiagnostico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DashboardSoporte diag = new DashboardSoporte();
				diag.setVisible(true);
				//JOptionPane.showMessageDialog(null, "Módulo en construcción.");
			}
		});
		menuSoporte.add(mntmPanelDiagnostico);

		JMenuItem mntmRegistrarTicket = new JMenuItem("Abrir Ticket Falla");
		mntmRegistrarTicket.setFont(itemFont);
		mntmRegistrarTicket.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrarTicket regisTicket = new RegistrarTicket();
				regisTicket.setVisible(true);
			}
		});
		menuSoporte.add(mntmRegistrarTicket);

		JMenu menuReportes = createStyledMenu(" Reportes ", menuFont);
		menuBar.add(menuReportes);

		JMenuItem mntmReporteGeneral = new JMenuItem("Reporte General");
		mntmReporteGeneral.setFont(itemFont);
		mntmReporteGeneral.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				empresa.actualizarDeudaClientes();
				ReporteGeneral dashboard = new ReporteGeneral();
				dashboard.setVisible(true);
			}
		});
		menuReportes.add(mntmReporteGeneral);

		JMenuItem mntmReporteDeRendimiento = new JMenuItem("Reporte de Rendimiento");
		mntmReporteDeRendimiento.setFont(itemFont);
		mntmReporteDeRendimiento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				ReporteRendimiento ventanaRendimiento = new ReporteRendimiento();
				ventanaRendimiento.setVisible(true);
			}
		});
		menuReportes.add(mntmReporteDeRendimiento);

		JMenuItem mntmReporteDeMercado = new JMenuItem("Reporte de Mercado");
		mntmReporteDeMercado.setFont(itemFont);
		mntmReporteDeMercado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empresa.generarPagosMensuales();
				ReporteMercado ventanaMercado = new ReporteMercado();
				ventanaMercado.setVisible(true);
			}
		});
		menuReportes.add(mntmReporteDeMercado);

		JMenu menuRespaldo = createStyledMenu(" Respaldo ", menuFont);
		menuBar.add(menuRespaldo);

		JMenuItem menuItemRespaldo = new JMenuItem("Generar Respaldo en Servidor");
		menuItemRespaldo.setFont(itemFont);
		menuItemRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirm = JOptionPane.showConfirmDialog(null,
						"¿Desea generar un respaldo de toda la base de datos en el servidor?",
						"Confirmación de Respaldo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

				if (confirm == JOptionPane.YES_OPTION) {
					try {
						java.net.Socket socket = new java.net.Socket("localhost", 7001);
						java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(
								new java.io.BufferedOutputStream(socket.getOutputStream()));
						out.flush();

						EmpresaAltice empresaParaEnviar = EmpresaAltice.getInstance();
						out.writeObject(empresaParaEnviar);
						out.flush();

						JOptionPane.showMessageDialog(null,
								"El respaldo se ha enviado y guardado en el servidor exitosamente.",
								"Respaldo Completado", JOptionPane.INFORMATION_MESSAGE);

						out.close();
						socket.close();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,
								"No se pudo conectar con el servidor. Verifique que esté encendido.",
								"Error de Conexión", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		menuRespaldo.add(menuItemRespaldo);

		JMenuItem menuItemRespaldoLocal = new JMenuItem("Generar Respaldo Local en PC...");
		menuItemRespaldoLocal.setFont(itemFont);
		menuItemRespaldoLocal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				fileChooser.setDialogTitle("Guardar respaldo de seguridad");

				// Filtro para que se guarde como .dat
				javax.swing.filechooser.FileNameExtensionFilter filtro = new javax.swing.filechooser.FileNameExtensionFilter("Respaldo Altice (*.dat)", "dat");
				fileChooser.setFileFilter(filtro);

				// Abre la ventana en modo "Guardar" en lugar de "Abrir"
				int seleccion = fileChooser.showSaveDialog(null);

				if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
					java.io.File archivoDestino = fileChooser.getSelectedFile();
					String rutaCompleta = archivoDestino.getAbsolutePath();

					// Si al usuario se le olvida escribir ".dat" al final, el sistema se lo pone automáticamente
					if (!rutaCompleta.toLowerCase().endsWith(".dat")) {
						rutaCompleta += ".dat";
					}

					// Usamos tu método GuardarDatos, pero le pasamos la ruta que eligió el usuario
					EmpresaAltice empresa = EmpresaAltice.getInstance();
					empresa.GuardarDatos(
							empresa.getMisClientes(), 
							empresa.getMisEmpleados(),
							empresa.getMisPlanes(), 
							empresa.getMisServicios(),
							empresa.getMisUsuarios(), 
							empresa.getMisContratos(),
							empresa.getPagos(),
							empresa.getMisTickets(), // Ojo: incluimos la lista de tickets que agregamos antes
							rutaCompleta
							);

					JOptionPane.showMessageDialog(null, "Copia de seguridad guardada exitosamente en:\n" + rutaCompleta, "Respaldo Local Exitoso", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		menuRespaldo.add(menuItemRespaldoLocal);

		JMenuItem menuItemRestaurar = new JMenuItem("Restaurar desde archivo...");
		menuItemRestaurar.setFont(itemFont);
		menuItemRestaurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
				fileChooser.setDialogTitle("Seleccione el archivo de respaldo (.dat)");

				javax.swing.filechooser.FileNameExtensionFilter filtro = new javax.swing.filechooser.FileNameExtensionFilter("Respaldo Altice (*.dat)", "dat");
				fileChooser.setFileFilter(filtro);

				int seleccion = fileChooser.showOpenDialog(null);

				if (seleccion == javax.swing.JFileChooser.APPROVE_OPTION) {
					java.io.File archivoSeleccionado = fileChooser.getSelectedFile();

					int confirm = JOptionPane.showConfirmDialog(null,
							"ALERTA: ¿Está seguro de restaurar el sistema con este archivo?\nSe borrarán los datos actuales y se reemplazarán por los del respaldo.",
							"Confirmar Restauración", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (confirm == JOptionPane.YES_OPTION) {
						boolean exito = EmpresaAltice.getInstance()
								.CargarDatosDesdeRespaldo(archivoSeleccionado.getAbsolutePath());

						if (exito) {
							JOptionPane.showMessageDialog(null,
									"Respaldo restaurado con éxito. El sistema ha vuelto al estado guardado.",
									"Restauración Exitosa", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null,
									"Error al leer el archivo. Puede que esté corrupto o no sea un respaldo válido.",
									"Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		menuRespaldo.add(menuItemRestaurar);

		Usuario usuarioActual = EmpresaAltice.getLoginUser();
		if (usuarioActual != null) {
			String rol = usuarioActual.getRolEmpleado();

			if (rol.equalsIgnoreCase("Tecnico")) {
				menuUsuarios.setVisible(false);
				menuEmpleados.setVisible(false);
				menuClientes.setVisible(false);
				menuContratos.setVisible(false);
				menuPlanes.setVisible(false);
				menuServicios.setVisible(false);
				menuPagos.setVisible(false);
				menuReportes.setVisible(false);
				menuRespaldo.setVisible(false);
				menuSoporte.setVisible(true);
			} else if (!rol.equalsIgnoreCase("Administrativo")) { // Asume Comercial o Ventas
				menuUsuarios.setVisible(false);
				menuEmpleados.setVisible(false);
				menuPlanes.setVisible(false);
				menuServicios.setVisible(false);
				menuReportes.setVisible(false);
				menuRespaldo.setVisible(false);
				menuSoporte.setVisible(false);

				menuClientes.setVisible(true);
				menuContratos.setVisible(true);
				menuPagos.setVisible(true);
			}
		}

		try {
			imagenFondo = new ImageIcon(getClass().getResource("/recursos/fondo_pantalla_principal.jpg")).getImage();
		} catch (Exception e) {
			imagenFondo = null;
		}

		contentPane = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imagenFondo != null) {
					g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
				} else {
					Graphics2D g2d = (Graphics2D) g;
					GradientPaint gp = new GradientPaint(0, 0, Color.WHITE, 0, getHeight(), new Color(230, 240, 255));
					g2d.setPaint(gp);
					g2d.fillRect(0, 0, getWidth(), getHeight());
				}
			}
		};
		contentPane.setLayout(new GridBagLayout());
		setContentPane(contentPane);

		JPanel pnlBienvenida = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(255, 255, 255, 220));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
				g2.dispose();
				super.paintComponent(g);
			}
		};
		pnlBienvenida.setOpaque(false);
		pnlBienvenida.setBorder(new EmptyBorder(50, 80, 50, 80));
		contentPane.add(pnlBienvenida);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.gridx = 0;
		gbc.gridy = 0;

		JLabel lblIconoLogo = new JLabel();
		try {
			ImageIcon iconoOriginal = new ImageIcon(
					getClass().getResource("/recursos/Altice_logo_azul_sin_letras.png"));
			Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH);
			lblIconoLogo.setIcon(new ImageIcon(imagenEscalada));
		} catch (Exception e) {
		}
		pnlBienvenida.add(lblIconoLogo, gbc);

		gbc.gridy = 1;
		JLabel lblLogoText = new JLabel("altice");
		lblLogoText.setFont(new Font("SansSerif", Font.BOLD, 70));
		lblLogoText.setForeground(alticeBlue);
		pnlBienvenida.add(lblLogoText, gbc);

		gbc.gridy = 2;
		String rolActual = "";
		String nombreActual = "";

		if (usuarioActual != null) {
			rolActual = usuarioActual.getRolEmpleado();
			nombreActual = usuarioActual.getNombreUsuario();
		}

		JLabel lblSubtitulo = new JLabel("Bienvenido al sistema, " + rolActual + ": " + nombreActual);
		lblSubtitulo.setFont(new Font("SansSerif", Font.PLAIN, 22));
		lblSubtitulo.setForeground(new Color(60, 60, 60));
		pnlBienvenida.add(lblSubtitulo, gbc);

		gbc.gridy = 3;
		gbc.insets = new Insets(30, 0, 0, 0); // Separación superior para los botones

		JPanel pnlAccesosRapidos = new JPanel(new java.awt.GridLayout(0, 3, 15, 15));
		pnlAccesosRapidos.setOpaque(false);

		boolean isAdmon = usuarioActual == null || usuarioActual.getRolEmpleado().equalsIgnoreCase("Administrativo");
		boolean isTecnico = usuarioActual != null && usuarioActual.getRolEmpleado().equalsIgnoreCase("Tecnico");
		boolean isComercial = !isAdmon && !isTecnico; // Si no es admin ni técnico, es comercial/ventas

		// Botones para Administrativos y Comerciales
		if (isAdmon || isComercial) {
			pnlAccesosRapidos.add(crearBotonDashboard("Registrar Cliente", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					empresa.generarPagosMensuales();
					empresa.actualizarDeudaClientes();
					RegistrarCliente regisCli = new RegistrarCliente(null);
					regisCli.setModal(true);
					regisCli.setVisible(true);
				}
			}));

			pnlAccesosRapidos.add(crearBotonDashboard("Nuevo Contrato", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					empresa.generarPagosMensuales();
					empresa.actualizarDeudaClientes();
					Empleado empleadoSesion = null;
					if (usuarioActual != null) {
						empleadoSesion = EmpresaAltice.getInstance().buscarEmpleadoPorUsuario(usuarioActual);
					}
					RegistrarContrato regisCon = new RegistrarContrato(null, empleadoSesion);
					regisCon.setModal(true);
					regisCon.setVisible(true);
				}
			}));

			pnlAccesosRapidos.add(crearBotonDashboard("Cobrar Pago", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					empresa.generarPagosMensuales();
					empresa.actualizarDeudaClientes();
					RegistrarPago regisPago = new RegistrarPago();
					regisPago.setModal(true);
					regisPago.setVisible(true);
				}
			}));
		}

		// Botones para Administrativos y Técnicos de Soporte
		if (isAdmon || isTecnico) {
			pnlAccesosRapidos.add(crearBotonDashboard("Abrir Ticket Falla", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RegistrarTicket regisTicket = new RegistrarTicket();
					regisTicket.setVisible(true);
				}
			}));

			pnlAccesosRapidos.add(crearBotonDashboard("Panel Diagnóstico", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					DashboardSoporte diag = new DashboardSoporte();
					diag.setVisible(true);
					//JOptionPane.showMessageDialog(null, "Módulo de panel en construcción.");
				}
			}));
		}

		// Botón exclusivo para Administrativo
		if (isAdmon) {
			pnlAccesosRapidos.add(crearBotonDashboard("Reporte General", new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					empresa.generarPagosMensuales();
					empresa.actualizarDeudaClientes();
					ReporteGeneral dashboard = new ReporteGeneral();
					dashboard.setVisible(true);
				}
			}));
		}

		pnlBienvenida.add(pnlAccesosRapidos, gbc);
	}

	// Método auxiliar para estilizar los menús principales
	private JMenu createStyledMenu(String text, Font font) {
		JMenu menu = new JMenu(text);
		menu.setFont(font);
		menu.setForeground(Color.WHITE);
		menu.setOpaque(true);
		menu.setBackground(Color.decode("#0066FF"));
		return menu;
	}

	// Método auxiliar para crear los botones grandes del Dashboard
	private JButton crearBotonDashboard(String texto, ActionListener accion) {
		JButton btn = new JButton(texto);
		btn.setFont(new Font("SansSerif", Font.BOLD, 15));
		btn.setForeground(Color.decode("#0066FF")); 
		btn.setBackground(Color.WHITE);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(Color.decode("#0066FF"), 2, true));
		btn.setPreferredSize(new Dimension(180, 60)); 
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBackground(Color.decode("#0066FF"));
				btn.setForeground(Color.WHITE);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBackground(Color.WHITE);
				btn.setForeground(Color.decode("#0066FF"));
			}
		});

		btn.addActionListener(accion);
		return btn;
	}
}