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
	private JComboBox<String> cbxRolEmpleado;

	/**
	 * Create the dialog.
	 */
	public RegistrarEmpleado() {
		super();
		setTitle("Registrar Empleado");
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 493);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		
		// Configuración de los Spinners para evitar números negativos desde la interfaz
		spnSalarioEmpleado = new JSpinner(new SpinnerNumberModel(0.0, 0.0, null, 1000.0));
		spnPorcentajeComisionEmp = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 1.0));
		
		cbxRolEmpleado = new JComboBox<String>();
		
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
			
			JLabel label = new JLabel("Código del empleado");
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
			
			JLabel lblComision = new JLabel("Porcentaje de comisión por venta");
			lblComision.setBounds(288, 292, 222, 34);
			panel.add(lblComision);
			
			JLabel lblRolDelEmpleado = new JLabel("Rol del empleado");
			lblRolDelEmpleado.setBounds(20, 216, 148, 34);
			panel.add(lblRolDelEmpleado);
			
			cbxRolEmpleado.setModel(new DefaultComboBoxModel<String>(new String[] {"Administrativo", "Comercial", "Vendedor"}));
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
						
						// 1. Extraemos los valores de la interfaz
						String nombre = txtNombreEmpleado.getText().trim();
						float salario = ((Number) spnSalarioEmpleado.getValue()).floatValue();
						float comision = ((Number) spnPorcentajeComisionEmp.getValue()).floatValue();
						
						// 2. Validación de nombre vacío
						if (nombre.isEmpty()) {
							JOptionPane.showMessageDialog(null, "El nombre del empleado no puede estar vacío.", "Error de validación", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// 3. Validación de rol seleccionado
						if (cbxRolEmpleado.getSelectedItem() == null) {
							JOptionPane.showMessageDialog(null, "Debe seleccionar un rol para el empleado.", "Error de validación", JOptionPane.WARNING_MESSAGE);
							return;
						}
						String rol = cbxRolEmpleado.getSelectedItem().toString();
						
						// 4. Validación de Salario mayor a 0
						if (salario <= 0) {
							JOptionPane.showMessageDialog(null, "El salario del empleado debe ser mayor a 0.", "Error de validación", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// 5. Validación de Comisión (0 a 100%)
						if (comision < 0 || comision > 100) {
							JOptionPane.showMessageDialog(null, "El porcentaje de comisión debe estar entre 0 y 100.", "Error de validación", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// Si todo es correcto, guardamos el empleado
						EmpresaAltice empresa = EmpresaAltice.getInstance();
						Empleado emp = new Empleado(null, nombre, salario, comision, 0, 0, rol);
						
						empresa.getMisEmpleados().add(emp);
						
						empresa.GuardarDatos(
							empresa.getMisClientes(), empresa.getMisEmpleados(),
							empresa.getMisPlanes(), empresa.getMisServicios(),
							empresa.getMisUsuarios(), empresa.getMisContratos(),
							empresa.getPagos()
						);
						
						JOptionPane.showMessageDialog(null, "¡Empleado registrado con éxito!", "Información", JOptionPane.INFORMATION_MESSAGE);
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
		spnSalarioEmpleado.setValue(0.0);
		spnPorcentajeComisionEmp.setValue(0.0); // Faltaba limpiar la comisión
		cbxRolEmpleado.setSelectedIndex(0);
	}
}