package visual;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;

public class RegistrarEmpleado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private Dimension dim;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	

	/**
	 * Create the dialog.
	 */
	public RegistrarEmpleado(JFrame owner) {
		super(owner, true);
		setBounds(100, 100, 450, 300);
		dim = getToolkit().getScreenSize();
		setSize(553, 493);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("ID");
			lblNewLabel.setBounds(20, 37, 117, 34);
			panel.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(20, 82, 185, 20);
			panel.add(textField);
			textField.setColumns(10);
			
			JLabel label = new JLabel("C\u00F3digo del empleado");
			label.setBounds(288, 37, 222, 34);
			panel.add(label);
			
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(288, 82, 185, 20);
			panel.add(textField_1);
			
			JLabel lblNombreDelEmpleado = new JLabel("Nombre del Empleado");
			lblNombreDelEmpleado.setBounds(20, 129, 148, 34);
			panel.add(lblNombreDelEmpleado);
			
			textField_2 = new JTextField();
			textField_2.setColumns(10);
			textField_2.setBounds(20, 174, 453, 20);
			panel.add(textField_2);
			
			JLabel lblVentas = new JLabel("Salario");
			lblVentas.setBounds(20, 292, 117, 34);
			panel.add(lblVentas);
			
			JLabel lblComision = new JLabel("Porcentaje de comisi\u00F3n por venta");
			lblComision.setBounds(288, 292, 222, 34);
			panel.add(lblComision);
			
			JLabel lblRolDelEmpleado = new JLabel("Rol del empleado");
			lblRolDelEmpleado.setBounds(20, 216, 148, 34);
			panel.add(lblRolDelEmpleado);
			
			JComboBox cbxRolEmpleado = new JComboBox();
			cbxRolEmpleado.setModel(new DefaultComboBoxModel(new String[] {"Administrativo", "Comercial", "Vendedor"}));
			cbxRolEmpleado.setBounds(20, 261, 200, 20);
			panel.add(cbxRolEmpleado);
			
			JSpinner spinner = new JSpinner();
			spinner.setBounds(20, 337, 185, 20);
			panel.add(spinner);
			
			JSpinner spinner_1 = new JSpinner();
			spinner_1.setBounds(288, 337, 185, 20);
			panel.add(spinner_1);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new LineBorder(new Color(0, 0, 0)));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRegistrar = new JButton("Registrar");
				btnRegistrar.setActionCommand("OK");
				buttonPane.add(btnRegistrar);
				getRootPane().setDefaultButton(btnRegistrar);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
	}
}
