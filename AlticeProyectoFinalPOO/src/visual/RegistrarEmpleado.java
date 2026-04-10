package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import logico.Empleado;
import logico.EmpresaAltice;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegistrarEmpleado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField txtIdEmpleado;
	private JTextField txtCodigoEmpleado;
	private JTextField txtNombreEmpleado;
	private JSpinner spnSalarioEmpleado;
	private JSpinner spnPorcentajeComisionEmp;
	private JComboBox cbxRolEmpleado;

	

	/**
	 * Create the dialog.
	 */
	public RegistrarEmpleado() {
		super();
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 493);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		spnSalarioEmpleado = new JSpinner();
		spnPorcentajeComisionEmp = new JSpinner();
		spnPorcentajeComisionEmp.setModel(new SpinnerNumberModel(0, null, 100, 1));
		cbxRolEmpleado = new JComboBox();
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblIDEmpleado = new JLabel("ID del Empleado");
			lblIDEmpleado.setBounds(20, 37, 117, 34);
			panel.add(lblIDEmpleado);
			
			txtIdEmpleado = new JTextField();
			txtIdEmpleado.setEditable(false);
			txtIdEmpleado.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
			txtIdEmpleado.setBounds(20, 82, 185, 20);
			panel.add(txtIdEmpleado);
			txtIdEmpleado.setColumns(10);
			
			JLabel label = new JLabel("C\u00F3digo del empleado");
			label.setBounds(288, 37, 222, 34);
			panel.add(label);
			
			txtCodigoEmpleado = new JTextField();
			txtCodigoEmpleado.setEditable(false);
			txtCodigoEmpleado.setColumns(10);
			String anioActual = String.valueOf(LocalDate.now().getYear());
			txtCodigoEmpleado.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
			txtCodigoEmpleado.setBounds(288, 82, 185, 20);
			panel.add(txtCodigoEmpleado);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del Empleado");
			lblNombreDelEmpleado.setBounds(20, 129, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			txtNombreEmpleado = new JTextField();
			txtNombreEmpleado.setColumns(10);
			txtNombreEmpleado.setBounds(20, 174, 453, 20);
			panel.add(txtNombreEmpleado);
			
			JLabel lblVentas = new JLabel("Salario");
			lblVentas.setBounds(20, 292, 117, 34);
			panel.add(lblVentas);
			
			JLabel lblComision = new JLabel("Porcentaje de comisi\u00F3n por venta");
			lblComision.setBounds(288, 292, 222, 34);
			panel.add(lblComision);
			
			JLabel lblRolDelEmpleado = new JLabel("Rol del empleado");
			lblRolDelEmpleado.setBounds(20, 216, 148, 34);
			panel.add(lblRolDelEmpleado);
			
			cbxRolEmpleado.setModel(new DefaultComboBoxModel(new String[] {"Administrativo", "Comercial", "Vendedor"}));
			cbxRolEmpleado.setBounds(20, 261, 200, 20);
			panel.add(cbxRolEmpleado);
			
			
			spnSalarioEmpleado.setBounds(20, 337, 185, 20);
			panel.add(spnSalarioEmpleado);
			
	
			spnPorcentajeComisionEmp.setBounds(288, 337, 185, 20);
			panel.add(spnPorcentajeComisionEmp);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.setActionCommand("OK");
				btnRegistrar.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						
						String nombre= txtNombreEmpleado.getText().trim();
						float salario= ((Number)spnSalarioEmpleado.getValue()).floatValue();
						float comision=((Number)spnPorcentajeComisionEmp.getValue()).floatValue();
						String rol =cbxRolEmpleado.getSelectedItem().toString();
						
						Empleado emp = new Empleado(null, nombre, salario, comision, 0, rol);
						empresa.getMisEmpleados().add(emp);
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(),
							empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						JOptionPane.showMessageDialog(null, "Empleado registrado con éxito");
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
		txtIdEmpleado.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigoEmpleado.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
		txtNombreEmpleado.setText("");
		spnSalarioEmpleado.setValue(new Float(0));
		cbxRolEmpleado.setSelectedIndex(0);
	}
}
