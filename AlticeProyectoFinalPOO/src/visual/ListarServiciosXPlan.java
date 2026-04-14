package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

import logico.Servicio;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class ListarServiciosXPlan extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;

	public ListarServiciosXPlan(ArrayList<Servicio> serviciosEscogidos) {
		setTitle("Detalle de Servicios");
		setModal(true);
		setResizable(false);
		setSize(900, 550);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Servicios Vinculados al Plan");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(Color.WHITE);
		JLabel lblAltice = new JLabel("altice");
		lblAltice.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblAltice.setForeground(new Color(255, 255, 255, 160));
		header.add(lblTitulo, BorderLayout.WEST);
		header.add(lblAltice, BorderLayout.EAST);
		getContentPane().add(header, BorderLayout.NORTH);

		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(20, 20, 10, 20));
		contentPanel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		contentPanel.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setEnabled(false);
		table.setRowHeight(32);
		table.setFont(FONT_INPUT);
		table.setSelectionBackground(ALTICE_LIGHT);
		table.setGridColor(ALTICE_LIGHT);

		table.getTableHeader().setFont(FONT_LABEL);
		table.getTableHeader().setBackground(ALTICE_BLUE);
		table.getTableHeader().setForeground(Color.WHITE);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 35));

		model = new DefaultTableModel();
		String[] headers = { "ID Servicio", "Tipo de Servicio", "Precio Mensual", "Especificaciones Técnicas" };
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = outlineButton("Cerrar Visor");
		btnCerrar.setPreferredSize(new java.awt.Dimension(130, 35));
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadServicios(serviciosEscogidos);
	}

	private void loadServicios(ArrayList<Servicio> serviciosEscogidos) {
		model.setRowCount(0);

		for (Servicio s : serviciosEscogidos) {
			Object[] row = new Object[4];
			row[0] = s.getIdServicio();
			row[1] = s.getTipoServicio();
			row[2] = "$ " + String.format("%.2f", s.getPrecioServicio());
			row[3] = buildDetalles(s);
			model.addRow(row);
		}

		if (table.getColumnModel().getColumnCount() > 0) {
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(150);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			table.getColumnModel().getColumn(3).setPreferredWidth(500);
		}
	}

	private String buildDetalles(Servicio s) {
		if (s instanceof ServicioInternet) {
			ServicioInternet net = (ServicioInternet) s;
			String router = net.isTieneRouter() ? "Sí" : "No";
			return "Velocidad: " + net.getVelocidadMbps() + " Mbps | Router: " + router;
		} else if (s instanceof ServicioMovil) {
			ServicioMovil movil = (ServicioMovil) s;
			return "Datos: " + movil.getDatosGb() + " GB | Minutos: " + movil.getMinutos() + " | SMS: "
					+ movil.getSms();
		} else if (s instanceof ServicioTelefonia) {
			ServicioTelefonia tel = (ServicioTelefonia) s;
			String ilimitado = tel.isLlamadasIlimitadas() ? "Sí" : "No";
			return "Min. incl: " + tel.getMinutosIncluidos() + " | Ilimitado: " + ilimitado + " | Extra: $"
					+ tel.getCostoMinutoExtra();
		} else if (s instanceof ServicioTelevision) {
			ServicioTelevision tv = (ServicioTelevision) s;
			String hd = tv.isTieneHD() ? "Sí" : "No";
			return "Canales: " + tv.getCantidadCanales() + " | Cajitas: " + tv.getCajitasIncluidas() + " | HD: " + hd;
		}
		return "N/A";
	}

	private JButton outlineButton(String text) {
		JButton btn = new JButton(text);
		btn.setFont(FONT_BTN);
		btn.setForeground(ALTICE_BLUE);
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}