package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JTextPane;

import logico.Cliente;
import logico.EmpresaAltice;

public class RegistrarCliente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtID;
	private JTextField txtCodigoCliente;
	private JTextField txtNombreCliente;
	private JTextField txtDeudaCliente;
	private JTextField txtEmailCliente;
	private JTextField txtDireccionCliente;
	private EmpresaAltice empresa = EmpresaAltice.getInstance();

	/**
	 * Create the dialog.
	 */
	public RegistrarCliente() {
		super();
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 586);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// AQUÍ ESTÁ LA SOLUCIÓN: Ya no dice "JTextField" al inicio, 
		// por lo que ahora sí usa las variables globales declaradas arriba.
		txtNombreCliente = new JTextField();
		txtEmailCliente = new JTextField();
		txtDireccionCliente = new JTextField();
		
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			txtID = new JTextField();
			txtID.setEditable(false);
			txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
			txtID.setBounds(20, 82, 185, 20);
			panel.add(txtID);
			txtID.setColumns(10);
			
			JLabel lblCdigoDelCliente = new JLabel("Código del Cliente");
			lblCdigoDelCliente.setBounds(288, 37, 222, 34);
			panel.add(lblCdigoDelCliente);
			
			txtCodigoCliente = new JTextField();
			txtCodigoCliente.setEditable(false);
			txtCodigoCliente.setColumns(10);
			String anioActual = String.valueOf(LocalDate.now().getYear());
			txtCodigoCliente.setText("CLTE-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
			txtCodigoCliente.setBounds(288, 82, 185, 20);
			panel.add(txtCodigoCliente);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del cliente");
			lblNombreDelEmpleado.setBounds(20, 129, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreCliente.setColumns(10);
			txtNombreCliente.setBounds(20, 174, 453, 20);
			panel.add(txtNombreCliente);
			
			JLabel lblVentas = new JLabel("Contrato");
			lblVentas.setEnabled(false);
			lblVentas.setBounds(20, 390, 117, 34);
			panel.add(lblVentas);
			
			txtDeudaCliente = new JTextField();
			txtDeudaCliente.setEnabled(false);
			txtDeudaCliente.setEditable(false);
			txtDeudaCliente.setColumns(10);
			txtDeudaCliente.setBounds(288, 435, 185, 20);
			panel.add(txtDeudaCliente);
			
			JLabel lblComision = new JLabel("Deuda mensual del cliente");
			lblComision.setEnabled(false);
			lblComision.setBounds(288, 390, 222, 34);
			panel.add(lblComision);
			
			txtEmailCliente.setColumns(10);
			txtEmailCliente.setBounds(20, 250, 453, 20);
			panel.add(txtEmailCliente);
			
			JLabel lblEmail = new JLabel("Email del cliente");
			lblEmail.setBounds(20, 205, 148, 34);
			panel.add(lblEmail);
			
			txtDireccionCliente.setColumns(10);
			txtDireccionCliente.setBounds(20, 338, 453, 20);
			panel.add(txtDireccionCliente);
			
			JLabel lblDireccin = new JLabel("Dirección");
			lblDireccin.setBounds(20, 293, 148, 34);
			panel.add(lblDireccin);
			
			JButton btnCrearUnContrato = new JButton("Crear un contrato");
			btnCrearUnContrato.setEnabled(false);
			btnCrearUnContrato.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//RegistrarContrato regisCto = new RegistrarContrato(cliente);
					//regisCto.setModal(true);
					//regisCto.setVisible(true);
				}
			});
			btnCrearUnContrato.setBounds(20, 435, 185, 23);
			panel.add(btnCrearUnContrato);
			
			JTextPane txtpnPulseAquPara = new JTextPane();
			txtpnPulseAquPara.setEnabled(false);
			txtpnPulseAquPara.setText("Pulse aquí para registrar un contrato");
			txtpnPulseAquPara.setBounds(20, 469, 207, 20);
			panel.add(txtpnPulseAquPara);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.setActionCommand("OK");
				btnRegistrar.addActionListener(new ActionListener() {
				
					public void actionPerformed(ActionEvent e) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						String nombre = txtNombreCliente.getText().trim();
						String direccion = txtDireccionCliente.getText().trim();
						String email = txtEmailCliente.getText().trim();
						
						Cliente cli = new Cliente(null, nombre, direccion, email, 0, false, null);
						empresa.getMisClientes().add(cli);
						//empresa.GuardarDatos(
						//    empresa.getMisClientes(), empresa.getMisEmpleados(),
						//    empresa.getMisPlanes(), empresa.getMisServicios(),
						//    empresa.getMisUsuarios(), empresa.getMisContratos(),
						//    empresa.getPagos()
						//);
						JOptionPane.showMessageDialog(null, "Cliente registrado con éxito");
						clean();
					}
				});
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setActionCommand("Cancel");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(btnCancelar);
			}
		}
	}
	
	private void clean() {
		txtID.setText("C - " + EmpresaAltice.getInstance().idClientes);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigoCliente.setText("CLTE-" + anioActual + "-" + EmpresaAltice.getInstance().idClientes);
		txtNombreCliente.setText("");
		txtDireccionCliente.setText("");
		txtEmailCliente.setText("");
	}
}