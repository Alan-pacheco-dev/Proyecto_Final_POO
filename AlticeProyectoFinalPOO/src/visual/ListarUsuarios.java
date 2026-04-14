package visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import logico.EmpresaAltice;
import logico.Usuario;

public class ListarUsuarios extends JDialog {

	private static final Color ALTICE_BLUE = Color.decode("#0066FF");
	private static final Color ALTICE_LIGHT = new Color(245, 248, 255);
	private static final Color ALTICE_BORDER = new Color(208, 223, 247);
	private static final Font FONT_LABEL = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_INPUT = new Font("SansSerif", Font.PLAIN, 13);
	private static final Font FONT_BTN = new Font("SansSerif", Font.BOLD, 13);
	private static final Font FONT_TABLE = new Font("SansSerif", Font.PLAIN, 12);
	private static final Font FONT_HEADER = new Font("SansSerif", Font.BOLD, 12);

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtBuscar;
	private TableRowSorter<DefaultTableModel> sorter;

	private JButton btnEliminar;
	private JButton btnActualizar;

	private Usuario selected = null;

	public static void main(String[] args) {
		try {
			ListarUsuarios dialog = new ListarUsuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListarUsuarios() {
		setTitle("Listado de Usuarios");
		setResizable(false);
		setSize(700, 500);
		setLocationRelativeTo(null);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().setBackground(Color.WHITE);

		JPanel header = new JPanel(new BorderLayout());
		header.setBackground(ALTICE_BLUE);
		header.setBorder(new EmptyBorder(12, 18, 12, 18));
		JLabel lblTitulo = new JLabel("Listado de Usuarios del Sistema");
		lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 15));
		lblTitulo.setForeground(Color.WHITE);
		JLabel lblAltice = new JLabel("altice");
		lblAltice.setFont(new Font("SansSerif", Font.BOLD, 17));
		lblAltice.setForeground(new Color(255, 255, 255, 160));
		header.add(lblTitulo, BorderLayout.WEST);
		header.add(lblAltice, BorderLayout.EAST);
		getContentPane().add(header, BorderLayout.NORTH);

		contentPanel.setBorder(new EmptyBorder(12, 14, 6, 14));
		contentPanel.setBackground(Color.WHITE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 10));

		JPanel panelBusqueda = new JPanel();
		panelBusqueda.setBackground(Color.WHITE);
		panelBusqueda.setLayout(new BorderLayout(10, 0));
		contentPanel.add(panelBusqueda, BorderLayout.NORTH);

		JLabel lblBuscar = new JLabel("Buscar Usuario (Nombre, Rol): ");
		lblBuscar.setFont(FONT_LABEL);
		lblBuscar.setForeground(ALTICE_BLUE);
		panelBusqueda.add(lblBuscar, BorderLayout.WEST);

		txtBuscar = new JTextField();
		txtBuscar.setFont(FONT_INPUT);
		txtBuscar.setBackground(ALTICE_LIGHT);
		txtBuscar.setBorder(BorderFactory.createCompoundBorder(new LineBorder(ALTICE_BORDER, 1, true),
				new EmptyBorder(4, 8, 4, 8)));
		panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

		JPanel panelTabla = new JPanel();
		panelTabla.setBackground(Color.WHITE);
		panelTabla.setLayout(new BorderLayout(0, 0));
		contentPanel.add(panelTabla, BorderLayout.CENTER);

		table = new JTable();
		table.setFont(FONT_TABLE);
		table.setRowHeight(26);
		table.setGridColor(ALTICE_BORDER);
		table.setShowGrid(true);
		table.setSelectionBackground(new Color(210, 225, 255));
		table.setSelectionForeground(Color.BLACK);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(FONT_HEADER);
		table.getTableHeader().setBackground(ALTICE_BLUE);
		table.getTableHeader().setForeground(Color.WHITE);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int indexVisual = table.getSelectedRow();
				if (indexVisual != -1) {
					int indexReal = table.convertRowIndexToModel(indexVisual);
					String idUsuario = (String) model.getValueAt(indexReal, 0);

					for (Usuario u : EmpresaAltice.getInstance().getMisUsuarios()) {
						if (u.getIdUsuario().equals(idUsuario)) {
							selected = u;
							break;
						}
					}

					if (selected != null) {
						btnEliminar.setEnabled(true);
						btnActualizar.setEnabled(true);
					}
				}
			}
		});

		model = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		String[] headers = { "ID User", "Nombre de Usuario", "Rol" };
		model.setColumnIdentifiers(headers);
		table.setModel(model);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new LineBorder(ALTICE_BORDER, 1, true));
		panelTabla.add(scrollPane, BorderLayout.CENTER);

		sorter = new TableRowSorter<>(model);
		table.setRowSorter(sorter);

		txtBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String textoBusqueda = txtBuscar.getText();
				if (textoBusqueda.trim().length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
				}
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setBackground(Color.WHITE);
		buttonPane.setBorder(new MatteBorder(1, 0, 0, 0, ALTICE_BORDER));
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 8));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnActualizar = primaryButton("Actualizar");
		btnActualizar.setEnabled(false);
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					RegistrarUsuario ventanaUpdate = new RegistrarUsuario(selected);
					ventanaUpdate.setModal(true);
					ventanaUpdate.setVisible(true);

					loadUsuarios();
					btnActualizar.setEnabled(false);
					btnEliminar.setEnabled(false);
					selected = null;
				}
			}
		});
		buttonPane.add(btnActualizar);

		btnEliminar = primaryButton("Eliminar");
		btnEliminar.setBackground(new Color(200, 50, 50));
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selected != null) {
					if (selected.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
						JOptionPane.showMessageDialog(null, "No se puede eliminar el usuario Administrativo", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}

					int option = JOptionPane.showConfirmDialog(null,
							"¿Desea realmente eliminar al usuario: " + selected.getNombreUsuario() + "?",
							"Confirmación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

					if (option == JOptionPane.YES_OPTION) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();

						boolean eliminado = empresa.eliminarUsuarioEmpleadoByID(selected.getIdUsuario());

						empresa.GuardarDatos(empresa.getMisClientes(), empresa.getMisEmpleados(),
								empresa.getMisPlanes(), empresa.getMisServicios(), empresa.getMisUsuarios(),
								empresa.getMisContratos(), empresa.getPagos());
						if (eliminado == true) {
							JOptionPane.showMessageDialog(null, "Usuario eliminado con éxito", "Información",
									JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "El Usuario no ha podido ser eliminado con éxito",
									"Información", JOptionPane.INFORMATION_MESSAGE);
						}

						loadUsuarios();
						btnEliminar.setEnabled(false);
						btnActualizar.setEnabled(false);
						selected = null;
					}
				}
			}
		});
		buttonPane.add(btnEliminar);

		JButton btnCerrar = outlineButton("Cerrar");
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		buttonPane.add(btnCerrar);

		loadUsuarios();
	}

	private void loadUsuarios() {
		model.setRowCount(0);
		for (Usuario u : EmpresaAltice.getInstance().getMisUsuarios()) {
			Object[] row = new Object[3];
			row[0] = u.getIdUsuario();
			row[1] = u.getNombreUsuario();
			row[2] = u.getRolEmpleado();
			model.addRow(row);
		}
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
		btn.setBackground(Color.WHITE);
		btn.setOpaque(true);
		btn.setContentAreaFilled(true);
		btn.setFocusPainted(false);
		btn.setBorder(new LineBorder(ALTICE_BLUE, 2, true));
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		return btn;
	}
}