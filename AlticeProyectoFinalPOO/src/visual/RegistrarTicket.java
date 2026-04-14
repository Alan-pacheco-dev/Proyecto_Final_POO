package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import logico.Cliente;
import logico.Contrato;
import logico.Empleado;
import logico.EmpresaAltice;
import logico.Ticket;
import logico.Usuario;

public class RegistrarTicket extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtClienteInfo;
	private JComboBox<String> cbxContratos;
	private JComboBox<String> cbxPrioridad;
	private JTextArea txtDescripcion;
	private JTextField txtTecnicoInfo;
	
	private Cliente clienteSeleccionado = null;
	private Empleado tecnicoSeleccionado = null;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Color alticeLight = new Color(245, 248, 255);
	private Color alticeBorder = new Color(208, 223, 247);

	public RegistrarTicket() {
		setTitle("Abrir Nuevo Ticket de Soporte");
		setModal(true);
		setBounds(100, 100, 520, 620);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		Font fontLabel = new Font("SansSerif", Font.BOLD, 14);
		Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

		Usuario userActual = EmpresaAltice.getLoginUser();
		Empleado empLogueado = null;
		if (userActual != null) {
			empLogueado = EmpresaAltice.getInstance().buscarEmpleadoPorUsuario(userActual);
		}

		JLabel lblCliente = new JLabel("Cliente Afectado:");
		lblCliente.setFont(fontLabel);
		lblCliente.setBounds(20, 20, 150, 25);
		contentPanel.add(lblCliente);
		
		txtClienteInfo = new JTextField("Seleccione un cliente...");
		txtClienteInfo.setEditable(false);
		txtClienteInfo.setFont(fontInput);
		txtClienteInfo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		txtClienteInfo.setBounds(20, 50, 280, 30);
		contentPanel.add(txtClienteInfo);
		
		JButton btnBuscarCliente = new JButton("Buscar");
		btnBuscarCliente.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBuscarCliente.setBackground(alticeBlue);
		btnBuscarCliente.setForeground(Color.WHITE);
		btnBuscarCliente.setFocusPainted(false);
		btnBuscarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscarCliente.setBounds(310, 50, 150, 30);
		btnBuscarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarClientes ventanaClientes = new ListarClientes(true);
				ventanaClientes.setModal(true);
				ventanaClientes.setVisible(true);

				Cliente clienteElegido = ventanaClientes.getClienteSeleccionado();
				if (clienteElegido != null) {
					clienteSeleccionado = clienteElegido;
					txtClienteInfo.setText(clienteSeleccionado.getNombre() + " (" + clienteSeleccionado.getCodigoCliente() + ")");
					cargarContratosDelCliente(); 
				}
			}
		});
		contentPanel.add(btnBuscarCliente);

		JLabel lblContrato = new JLabel("Contrato / Servicio a Revisar:");
		lblContrato.setFont(fontLabel);
		lblContrato.setBounds(20, 100, 250, 25);
		contentPanel.add(lblContrato);
		
		cbxContratos = new JComboBox<String>();
		cbxContratos.setFont(fontInput);
		cbxContratos.setEnabled(false); 
		cbxContratos.setBounds(20, 130, 440, 30);
		contentPanel.add(cbxContratos);

		JLabel lblPrioridad = new JLabel("Nivel de Prioridad:");
		lblPrioridad.setFont(fontLabel);
		lblPrioridad.setBounds(20, 180, 150, 25);
		contentPanel.add(lblPrioridad);
		
		cbxPrioridad = new JComboBox<String>();
		cbxPrioridad.setModel(new DefaultComboBoxModel<String>(new String[] {"Baja", "Media", "Alta"}));
		cbxPrioridad.setFont(fontInput);
		cbxPrioridad.setBounds(20, 210, 150, 30);
		contentPanel.add(cbxPrioridad);

		JLabel lblDescripcion = new JLabel("Descripción de la avería / queja:");
		lblDescripcion.setFont(fontLabel);
		lblDescripcion.setBounds(20, 260, 250, 25);
		contentPanel.add(lblDescripcion);
		
		txtDescripcion = new JTextArea();
		txtDescripcion.setFont(fontInput);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		
		JScrollPane scrollPane = new JScrollPane(txtDescripcion);
		scrollPane.setBounds(20, 290, 440, 100);
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		contentPanel.add(scrollPane);

		JPanel panelTecnico = new JPanel();
		panelTecnico.setLayout(null);
		panelTecnico.setBackground(alticeLight);
		panelTecnico.setBorder(BorderFactory.createTitledBorder(
			new LineBorder(alticeBorder), "Técnico Asignado",
			TitledBorder.LEADING, TitledBorder.TOP, fontLabel, alticeBlue
		));
		panelTecnico.setBounds(20, 410, 440, 90);
		contentPanel.add(panelTecnico);

		txtTecnicoInfo = new JTextField();
		txtTecnicoInfo.setEditable(false);
		txtTecnicoInfo.setFont(fontInput);
		txtTecnicoInfo.setBackground(Color.WHITE);
		txtTecnicoInfo.setBounds(15, 30, 260, 30);
		txtTecnicoInfo.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panelTecnico.add(txtTecnicoInfo);

		JButton btnBuscarTecnico = new JButton("Buscar Técnico");
		btnBuscarTecnico.setFont(new Font("SansSerif", Font.BOLD, 12));
		btnBuscarTecnico.setBackground(alticeBlue);
		btnBuscarTecnico.setForeground(Color.WHITE);
		btnBuscarTecnico.setFocusPainted(false);
		btnBuscarTecnico.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnBuscarTecnico.setBounds(285, 30, 140, 30);
		panelTecnico.add(btnBuscarTecnico);

		btnBuscarTecnico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmpleados listEmps = new ListarEmpleados(true, "Tecnico");
				listEmps.setVisible(true);
				Empleado empElegido = listEmps.getEmpleadoSeleccionado();
				if (empElegido != null) {
					tecnicoSeleccionado = empElegido;
					txtTecnicoInfo.setText(tecnicoSeleccionado.getNombre() + " (" + tecnicoSeleccionado.getCodigoEmpleado() + ")");
				}
			}
		});

		String rolUsuario = (userActual != null && userActual.getRolEmpleado() != null) ? userActual.getRolEmpleado().toLowerCase() : "";

		// Validamos conteniendo palabras clave para evitar errores por espacios o tildes
		boolean isAdministrador = rolUsuario.contains("admin");
		boolean isTecnico = rolUsuario.contains("tecnico") || rolUsuario.contains("técnico") || rolUsuario.contains("soporte");

		if (isAdministrador) {
			btnBuscarTecnico.setVisible(true);
			btnBuscarTecnico.setEnabled(true);
			txtTecnicoInfo.setText("Sin asignar (Opcional)...");
		} 
		else if (isTecnico) {
			// Es técnico: Ocultamos y desactivamos el botón
			btnBuscarTecnico.setVisible(false);
			btnBuscarTecnico.setEnabled(false);
			
			if (empLogueado != null) {
				tecnicoSeleccionado = empLogueado;
				txtTecnicoInfo.setText(tecnicoSeleccionado.getNombre() + " (" + tecnicoSeleccionado.getCodigoEmpleado() + ")");
			} else {
				txtTecnicoInfo.setText(userActual.getNombreUsuario() + " (Falta vincular empleado)");
			}
		} 
		else {
			// Por si entra algún otro rol no previsto
			btnBuscarTecnico.setVisible(false);
			btnBuscarTecnico.setEnabled(false);
			txtTecnicoInfo.setText("Sin permisos para asignar.");
		}

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnRegistrar = new JButton("Registrar Ticket");
		btnRegistrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnRegistrar.setBackground(alticeBlue);
		btnRegistrar.setForeground(Color.WHITE);
		btnRegistrar.setFocusPainted(false);
		btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarTicket();
			}
		});
		buttonPane.add(btnRegistrar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCancelar.setBackground(bgWhite);
		btnCancelar.setForeground(alticeBlue);
		btnCancelar.setBorder(new LineBorder(alticeBlue, 2, true));
		btnCancelar.setFocusPainted(false);
		btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCancelar);
	}
	
	private void cargarContratosDelCliente() {
		cbxContratos.removeAllItems();
		if (clienteSeleccionado != null) {
			boolean tieneContratos = false;
			for (Contrato c : clienteSeleccionado.getMisContratos()) {
				if (c.isActivo()) {
					cbxContratos.addItem(c.getIdContrato() + " | " + c.getPlanContrato().getNombrePlan());
					tieneContratos = true;
				}
			}
			
			if (tieneContratos) {
				cbxContratos.setEnabled(true);
			} else {
				cbxContratos.addItem("El cliente no tiene contratos activos.");
				cbxContratos.setEnabled(false);
			}
		}
	}
	
	private void guardarTicket() {
		if (clienteSeleccionado == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (cbxContratos.getSelectedIndex() == -1 || !cbxContratos.isEnabled()) {
			JOptionPane.showMessageDialog(this, "El cliente debe tener un contrato válido para abrir un ticket.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (txtDescripcion.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Debe escribir una descripción del problema.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String seleccionComboBox = cbxContratos.getSelectedItem().toString();
		String idContrato = seleccionComboBox.split(" \\| ")[0]; 
		
		Contrato contratoAfectado = null;
		for (Contrato c : clienteSeleccionado.getMisContratos()) {
			if (c.getIdContrato().equals(idContrato)) {
				contratoAfectado = c;
				break;
			}
		}
		
		String prioridad = cbxPrioridad.getSelectedItem().toString();
		String queja = txtDescripcion.getText().trim();
		
		Ticket nuevoTicket = new Ticket(clienteSeleccionado, contratoAfectado, queja, prioridad);
		
		if (tecnicoSeleccionado != null) {
			nuevoTicket.setTecnicoAsignado(tecnicoSeleccionado);
		}
		
		EmpresaAltice empresa = EmpresaAltice.getInstance();
		empresa.getMisTickets().add(nuevoTicket); 
		
		empresa.GuardarDatos(
				empresa.getMisClientes(), 
				empresa.getMisEmpleados(),
				empresa.getMisPlanes(), 
				empresa.getMisServicios(),
				empresa.getMisUsuarios(), 
				empresa.getMisContratos(),
				empresa.getPagos()
		);
		
		JOptionPane.showMessageDialog(this, "¡Ticket " + nuevoTicket.getIdTicket() + " generado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}
}