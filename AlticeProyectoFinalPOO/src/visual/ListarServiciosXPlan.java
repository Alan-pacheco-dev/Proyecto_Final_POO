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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import logico.Servicio;
import logico.ServicioInternet;
import logico.ServicioMovil;
import logico.ServicioTelefonia;
import logico.ServicioTelevision;

public class ListarServiciosXPlan extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	
	private Color alticeBlue = Color.decode("#0066FF");
	private Color bgWhite = Color.WHITE;
	private Font fontInput = new Font("SansSerif", Font.PLAIN, 14);

	public ListarServiciosXPlan(ArrayList<Servicio> serviciosEscogidos) {
		setTitle("Servicios actualmente en el Plan");
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 900, 500);
		setLocationRelativeTo(null);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(bgWhite);
		
		contentPanel.setBackground(bgWhite);
		contentPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(bgWhite);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		table.setEnabled(false);
		
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
		table.getTableHeader().setBackground(alticeBlue);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setRowHeight(28);
		table.setFont(fontInput);

		model = new DefaultTableModel();
		String[] headers = {"ID Servicio", "Tipo", "Precio", "Detalles"};
		model.setColumnIdentifiers(headers);
		table.setModel(model);
		scrollPane.setViewportView(table);

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(bgWhite);
		buttonPane.setBorder(new EmptyBorder(10, 15, 15, 15));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		JButton btnCerrar = new JButton("Cerrar Visor");
		btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 14));
		btnCerrar.setForeground(alticeBlue);
		btnCerrar.setBackground(bgWhite);
		btnCerrar.setFocusPainted(false);
		btnCerrar.setBorder(new LineBorder(alticeBlue, 2, true));
		btnCerrar.setPreferredSize(new java.awt.Dimension(120, 35));
		btnCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
			String detalles = buildDetalles(s);

			Object[] row = new Object[4];
			row[0] = s.getIdServicio();
			row[1] = s.getTipoServicio();
			row[2] = "$ " + String.format("%.2f", s.getPrecioServicio());
			row[3] = detalles;
			model.addRow(row);
		}
		table.getColumnModel().getColumn(3).setPreferredWidth(450);
	}

	private String buildDetalles(Servicio s) {
		if (s instanceof ServicioInternet) {
			ServicioInternet net = (ServicioInternet) s;
			String router = net.isTieneRouter() ? "Sí" : "No";
			return "Velocidad: " + net.getVelocidadMbps() + " Mbps | Router: " + router;

		} else if (s instanceof ServicioMovil) {
			ServicioMovil movil = (ServicioMovil) s;
			return "Datos: " + movil.getDatosGb() + " GB | Minutos: " + movil.getMinutos() + " | SMS: " + movil.getSms();

		} else if (s instanceof ServicioTelefonia) {
			ServicioTelefonia tel = (ServicioTelefonia) s;
			String ilimitado = tel.isLlamadasIlimitadas() ? "Sí" : "No";
			return "Min. incluidos: " + tel.getMinutosIncluidos() + " | Ilimitado: " + ilimitado + " | Costo/min extra: $" + tel.getCostoMinutoExtra();

		} else if (s instanceof ServicioTelevision) {
			ServicioTelevision tv = (ServicioTelevision) s;
			String hd = tv.isTieneHD() ? "Sí" : "No";
			return "Canales: " + tv.getCantidadCanales() + " | Cajitas: " + tv.getCajitasIncluidas() + " | HD: " + hd;

		} else {
			return "-";
		}
	}
}