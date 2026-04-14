package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logico.Empleado;
import logico.EmpresaAltice;
import logico.Ticket;
import logico.Usuario;

public class DashboardSoporte extends JDialog {

	private static final Color ALTICE_BLUE   = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT  = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Color BG_WHITE      = Color.WHITE;
	private static final Font  FONT_LABEL    = new Font("SansSerif", Font.BOLD, 13);
	private static final Font  FONT_SMALL    = new Font("SansSerif", Font.PLAIN, 12);
	private static final Font  FONT_KPI      = new Font("SansSerif", Font.BOLD, 28);
	private static final Font  FONT_BTN      = new Font("SansSerif", Font.BOLD, 13);

	private JTable tabla;
	private DefaultTableModel modeloTabla;
	private JComboBox<String> cbxFiltroEstado;
	private JComboBox<String> cbxFiltroPrioridad;

	private JLabel lblTotalVal, lblAbiertosVal, lblEnProcesoVal, lblResueltosVal;

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	public DashboardSoporte() {
		setTitle("Dashboard de Soporte Técnico");
		setModal(true);
		setSize(950, 620);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());

		getContentPane().add(buildHeader(),   BorderLayout.NORTH);
		getContentPane().add(buildCenter(),   BorderLayout.CENTER);
		getContentPane().add(buildFooter(),   BorderLayout.SOUTH);

		cargarTabla(null, null);
		actualizarKPIs();
	}

	private JPanel buildHeader() {
		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(14, 20, 14, 20));

		JLabel lblTitulo = new JLabel("Panel de Soporte Técnico — Gestión de Tickets");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblTitulo.setForeground(Color.WHITE);
		header.add(lblTitulo, BorderLayout.WEST);

		JLabel lblSub = new JLabel("altice");
		lblSub.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblSub.setForeground(new Color(255, 255, 255, 160));
		header.add(lblSub, BorderLayout.EAST);

		return header;
	}

	private JPanel buildCenter() {
		JPanel center = new JPanel(new BorderLayout(0, 10));
		center.setBackground(BG_WHITE);
		center.setBorder(new EmptyBorder(14, 16, 0, 16));

		center.add(buildKPIs(),    BorderLayout.NORTH);
		center.add(buildFiltros(), BorderLayout.CENTER);

		return center;
	}

	private JPanel buildKPIs() {
		JPanel row = new JPanel(new GridLayout(1, 4, 12, 0));
		row.setBackground(BG_WHITE);
		row.setBorder(new EmptyBorder(0, 0, 10, 0));

		lblTotalVal      = new JLabel("0", SwingConstants.CENTER);
		lblAbiertosVal   = new JLabel("0", SwingConstants.CENTER);
		lblEnProcesoVal  = new JLabel("0", SwingConstants.CENTER);
		lblResueltosVal  = new JLabel("0", SwingConstants.CENTER);

		row.add(crearKPI("Total de tickets",  lblTotalVal,     ALTICE_BLUE));
		row.add(crearKPI("Abiertos",          lblAbiertosVal,  new Color(220, 60, 60)));
		row.add(crearKPI("En proceso",        lblEnProcesoVal, new Color(220, 140, 0)));
		row.add(crearKPI("Resueltos",         lblResueltosVal, new Color(30, 140, 70)));

		return row;
	}

	private JPanel crearKPI(String titulo, JLabel lblValor, Color color) {
		JPanel card = new JPanel(new BorderLayout(0, 4));
		card.setBackground(ALTICE_LIGHT);
		card.setBorder(BorderFactory.createCompoundBorder(
			new LineBorder(ALTICE_BORDER, 1, true),
			new EmptyBorder(12, 16, 12, 16)
		));

		JLabel lblTit = new JLabel(titulo, SwingConstants.CENTER);
		lblTit.setFont(FONT_SMALL);
		lblTit.setForeground(new Color(90, 110, 150));

		lblValor.setFont(FONT_KPI);
		lblValor.setForeground(color);

		card.add(lblTit,   BorderLayout.NORTH);
		card.add(lblValor, BorderLayout.CENTER);
		return card;
	}

	private JPanel buildFiltros() {
		JPanel wrapper = new JPanel(new BorderLayout(0, 8));
		wrapper.setBackground(BG_WHITE);

		JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		filtros.setBackground(BG_WHITE);

		JLabel lblEstado = new JLabel("Estado:");
		lblEstado.setFont(FONT_LABEL);
		filtros.add(lblEstado);

		cbxFiltroEstado = new JComboBox<>(new String[]{"Todos", "Abierto", "En Proceso", "Resuelto"});
		cbxFiltroEstado.setFont(FONT_SMALL);
		cbxFiltroEstado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarFiltros();
			}
		});
		filtros.add(cbxFiltroEstado);

		JLabel lblPrioridad = new JLabel("Prioridad:");
		lblPrioridad.setFont(FONT_LABEL);
		filtros.add(lblPrioridad);

		cbxFiltroPrioridad = new JComboBox<>(new String[]{"Todas", "Alta", "Media", "Baja"});
		cbxFiltroPrioridad.setFont(FONT_SMALL);
		cbxFiltroPrioridad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aplicarFiltros();
			}
		});
		filtros.add(cbxFiltroPrioridad);

		wrapper.add(filtros, BorderLayout.NORTH);
		wrapper.add(buildTabla(), BorderLayout.CENTER);
		return wrapper;
	}

	private JScrollPane buildTabla() {
		String[] columnas = {"ID Ticket", "Cliente", "Contrato", "Prioridad", "Estado", "Técnico Asignado", "Fecha Reporte"};
		modeloTabla = new DefaultTableModel(columnas, 0) {
			public boolean isCellEditable(int row, int col) { return false; }
		};

		tabla = new JTable(modeloTabla);
		tabla.setFont(FONT_SMALL);
		tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
		tabla.getTableHeader().setBackground(ALTICE_BLUE);
		tabla.getTableHeader().setForeground(Color.WHITE);
		tabla.setRowHeight(26);
		tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabla.setGridColor(ALTICE_BORDER);
		tabla.setShowGrid(true);

		tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(1).setPreferredWidth(160);
		tabla.getColumnModel().getColumn(2).setPreferredWidth(130);
		tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
		tabla.getColumnModel().getColumn(4).setPreferredWidth(90);
		tabla.getColumnModel().getColumn(5).setPreferredWidth(140);
		tabla.getColumnModel().getColumn(6).setPreferredWidth(130);

		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		return scroll;
	}

	private JPanel buildFooter() {
		JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
		footer.setBackground(BG_WHITE);
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, ALTICE_BORDER));

		JButton btnAsignar = primaryButton("Asignar Técnico");
		btnAsignar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionAsignarTecnico();
			}
		});

		JButton btnResolver = primaryButton("Marcar como Resuelto");
		btnResolver.setBackground(new Color(30, 140, 70));
		btnResolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionMarcarResuelto();
			}
		});

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		footer.add(btnAsignar);
		footer.add(btnResolver);
		footer.add(btnCerrar);
		return footer;
	}

	private void cargarTabla(String filtroEstado, String filtroPrioridad) {
		modeloTabla.setRowCount(0);
		ArrayList<Ticket> tickets = EmpresaAltice.getInstance().getMisTickets();

		for (Ticket t : tickets) {
			boolean pasaEstado    = (filtroEstado == null    || filtroEstado.equals("Todos")   || t.getEstado().equalsIgnoreCase(filtroEstado));
			boolean pasaPrioridad = (filtroPrioridad == null || filtroPrioridad.equals("Todas") || t.getPrioridad().equalsIgnoreCase(filtroPrioridad));

			if (pasaEstado && pasaPrioridad) {
				String tecnico     = (t.getTecnicoAsignado() != null) ? t.getTecnicoAsignado().getNombre() : "Sin asignar";
				String contrato    = (t.getContratoAsociado() != null) ? t.getContratoAsociado().getIdContrato() : "—";
				String fechaRep    = (t.getFechaReporte() != null) ? t.getFechaReporte().format(FORMATTER) : "—";

				modeloTabla.addRow(new Object[]{
					t.getIdTicket(),
					t.getClienteAfectado().getNombre(),
					contrato,
					t.getPrioridad(),
					t.getEstado(),
					tecnico,
					fechaRep
				});
			}
		}
	}

	private void actualizarKPIs() {
		ArrayList<Ticket> tickets = EmpresaAltice.getInstance().getMisTickets();
		int total = tickets.size(), abiertos = 0, enProceso = 0, resueltos = 0;

		for (Ticket t : tickets) {
			if (t.getEstado().equalsIgnoreCase("Abierto"))      abiertos++;
			else if (t.getEstado().equalsIgnoreCase("En Proceso")) enProceso++;
			else if (t.getEstado().equalsIgnoreCase("Resuelto"))   resueltos++;
		}

		lblTotalVal.setText(String.valueOf(total));
		lblAbiertosVal.setText(String.valueOf(abiertos));
		lblEnProcesoVal.setText(String.valueOf(enProceso));
		lblResueltosVal.setText(String.valueOf(resueltos));
	}

	private void aplicarFiltros() {
		String estado    = cbxFiltroEstado.getSelectedItem().toString();
		String prioridad = cbxFiltroPrioridad.getSelectedItem().toString();
		cargarTabla(estado, prioridad);
	}

	private void accionAsignarTecnico() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un ticket de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idTicket = modeloTabla.getValueAt(fila, 0).toString();
		Ticket ticket = buscarTicketPorId(idTicket);

		if (ticket == null) return;

		if (ticket.getEstado().equalsIgnoreCase("Resuelto")) {
			JOptionPane.showMessageDialog(this, "El ticket ya está resuelto.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		Usuario userActual = EmpresaAltice.getLoginUser();
		String rolActual = (userActual != null) ? userActual.getRolEmpleado().toLowerCase() : "";
		boolean isAdmin = rolActual.contains("admin");

		if (!isAdmin) {
			// Si es técnico, se asigna a sí mismo directamente
			Empleado empActual = EmpresaAltice.getInstance().buscarEmpleadoPorUsuario(userActual);
			if (empActual != null) {
				ticket.setTecnicoAsignado(empActual);
				guardarYRefrescar();
				JOptionPane.showMessageDialog(this, "Te has auto-asignado el ticket exitosamente.", "Asignación Rápida", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "No tienes un empleado vinculado a tu usuario.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			return;
		}

		// Si es Administrador: Mostramos el Dropdown solo con técnicos
		ArrayList<Empleado> tecnicos = new ArrayList<>();
		for (Empleado emp : EmpresaAltice.getInstance().getMisEmpleados()) {
			String r = emp.getRolEmpleado().toLowerCase();
			if (r.contains("tecnico") || r.contains("técnico") || r.contains("soporte")) {
				tecnicos.add(emp);
			}
		}

		if (tecnicos.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No hay técnicos registrados en el sistema.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		String[] nombres = new String[tecnicos.size()];
		for (int i = 0; i < tecnicos.size(); i++) {
			nombres[i] = tecnicos.get(i).getNombre();
		}

		String seleccion = (String) JOptionPane.showInputDialog(
			this,
			"Seleccione el técnico a asignar:",
			"Asignar Técnico",
			JOptionPane.PLAIN_MESSAGE,
			null,
			nombres,
			nombres[0]
		);

		if (seleccion != null) {
			for (Empleado emp : tecnicos) {
				if (emp.getNombre().equals(seleccion)) {
					ticket.setTecnicoAsignado(emp);
					break;
				}
			}
			guardarYRefrescar();
			JOptionPane.showMessageDialog(this, "Técnico asignado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void accionMarcarResuelto() {
		int fila = tabla.getSelectedRow();
		if (fila == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione un ticket de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		String idTicket = modeloTabla.getValueAt(fila, 0).toString();
		Ticket ticket = buscarTicketPorId(idTicket);

		if (ticket == null) return;

		if (ticket.getEstado().equalsIgnoreCase("Resuelto")) {
			JOptionPane.showMessageDialog(this, "El ticket ya está resuelto.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		int confirmar = JOptionPane.showConfirmDialog(
			this,
			"¿Confirma marcar el ticket " + idTicket + " como resuelto?",
			"Confirmar resolución",
			JOptionPane.YES_NO_OPTION
		);

		if (confirmar == JOptionPane.YES_OPTION) {
			ticket.marcarComoResuelto();
			guardarYRefrescar();
			JOptionPane.showMessageDialog(this, "Ticket marcado como resuelto.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private Ticket buscarTicketPorId(String id) {
		for (Ticket t : EmpresaAltice.getInstance().getMisTickets()) {
			if (t.getIdTicket().equals(id)) return t;
		}
		return null;
	}

	private void guardarYRefrescar() {
		EmpresaAltice empresa = EmpresaAltice.getInstance();
		// Pasando todos los atributos sin modificar la firma original de GuardarDatos
		empresa.GuardarDatos(
			empresa.getMisClientes(),
			empresa.getMisEmpleados(),
			empresa.getMisPlanes(),
			empresa.getMisServicios(),
			empresa.getMisUsuarios(),
			empresa.getMisContratos(),
			empresa.getPagos()
		);
		aplicarFiltros();
		actualizarKPIs();
	}

	private JButton primaryButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(Color.WHITE);
		btn.setBackground(ALTICE_BLUE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setBorderPainted(false);
		btn.setFocusPainted(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}

	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(ALTICE_BLUE);
		btn.setBackground(BG_WHITE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}