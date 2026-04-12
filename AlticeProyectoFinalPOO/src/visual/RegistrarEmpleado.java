package visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import logico.Empleado;
import logico.EmpresaAltice;

public class RegistrarEmpleado extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtId;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtCedula;
	private JSpinner spnSalario;
	private JSpinner spnComisiones;
	private JComboBox<String> cbxRol;
	
	private Empleado miEmpleado = null;

	public static void main(String[] args) {
		try {
			// Se envía null para probar el modo Registro
			RegistrarEmpleado dialog = new RegistrarEmpleado(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Constructor que recibe un Empleado (null = Registrar, Objeto = Actualizar)
	public RegistrarEmpleado(Empleado empleado) {
		
		this.miEmpleado = empleado;
		setResizable(false);
		
		// Lógica IF tradicional para el título
		if(miEmpleado == null) {
			setTitle("Registrar Nuevo Empleado");
		} else {
			setTitle("Actualizar Datos del Empleado");
		}
		
		setBounds(100, 100, 500, 405);
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

			// --- ID ---
			JLabel lblId = new JLabel("ID Persona:");
			lblId.setBounds(20, 20, 174, 14);
			panel.add(lblId);

			txtId = new JTextField();
			txtId.setEditable(false);
			txtId.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
			txtId.setBounds(20, 40, 200, 20);
			panel.add(txtId);
			txtId.setColumns(10);
			
			// --- CÓDIGO EMPLEADO ---
			JLabel lblCodigo = new JLabel("Código Empleado:");
			lblCodigo.setBounds(240, 20, 200, 14);
			panel.add(lblCodigo);

			String anioActual = String.valueOf(LocalDate.now().getYear());
			txtCodigo = new JTextField();
			txtCodigo.setEditable(false);
			txtCodigo.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
			txtCodigo.setBounds(240, 40, 200, 20);
			panel.add(txtCodigo);

			// --- NOMBRE ---
			JLabel lblNombre = new JLabel("Nombre y Apellidos:");
			lblNombre.setBounds(20, 80, 222, 14);
			panel.add(lblNombre);

			txtNombre = new JTextField();
			txtNombre.setBounds(20, 100, 420, 20);
			panel.add(txtNombre);
			txtNombre.setColumns(10);
			
			// --- CÉDULA ---
			JLabel lblCedula = new JLabel("Cédula:");
			lblCedula.setBounds(20, 140, 150, 14);
			panel.add(lblCedula);

			txtCedula = new JTextField();
			txtCedula.setBounds(20, 160, 420, 20);
			panel.add(txtCedula);
			txtCedula.setColumns(10);

			// --- ROL ---
			JLabel lblRol = new JLabel("Rol del Empleado:");
			lblRol.setBounds(20, 200, 150, 14);
			panel.add(lblRol);

			cbxRol = new JComboBox<String>();
			cbxRol.setModel(new DefaultComboBoxModel<String>(new String[] {"Seleccione...", "Administrativo", "Vendedor"}));
			cbxRol.setBounds(20, 220, 420, 20);
			panel.add(cbxRol);

			// --- SALARIO ---
			JLabel lblSalario = new JLabel("Salario Base ($):");
			lblSalario.setBounds(20, 260, 150, 14);
			panel.add(lblSalario);

			// Configurado para manejar decimales
			spnSalario = new JSpinner();
			spnSalario.setModel(new SpinnerNumberModel(0.0f, 0.0f, 1000000.0f, 500.0f));
			spnSalario.setBounds(20, 280, 200, 20);
			panel.add(spnSalario);
			
			// --- COMISIONES ---
			JLabel lblComisiones = new JLabel("Comisión (%):");
			lblComisiones.setBounds(240, 260, 141, 14);
			panel.add(lblComisiones);

			// Configurado para manejar porcentajes (0 a 100)
			spnComisiones = new JSpinner();
			spnComisiones.setModel(new SpinnerNumberModel(0.0f, 0.0f, 100.0f, 1.0f));
			spnComisiones.setBounds(240, 280, 200, 20);
			panel.add(spnComisiones);
		}
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnAccion = new JButton("Registrar");
				
				// Lógica IF tradicional para el texto del botón
				if(miEmpleado != null) {
					btnAccion.setText("Actualizar");
				}
				
				btnAccion.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						// 1. Validaciones básicas
						String nombre = txtNombre.getText().trim();
						String cedula = txtCedula.getText().trim();
						String rol = cbxRol.getSelectedItem().toString();
						
						if(nombre.isEmpty() || cedula.isEmpty() || rol.equals("Seleccione...")) {
							JOptionPane.showMessageDialog(null, "Por favor complete todos los campos y seleccione un rol.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						if(!cedula.matches("[0-9]+") || cedula.length() != 11) {
							JOptionPane.showMessageDialog(null, "La cédula debe contener exactamente 11 números, sin guiones.", "Cédula Inválida", JOptionPane.WARNING_MESSAGE);
							return;
						}
						
						// Capturamos los valores numéricos
						float salario = ((Double) spnSalario.getValue()).floatValue();
						float comisiones = ((Double) spnComisiones.getValue()).floatValue();
						
						EmpresaAltice empresa = EmpresaAltice.getInstance();

						// --- MODO REGISTRO ---
						if(miEmpleado == null) {
							
							// El constructor genera automáticamente el ID y el Código interno
							Empleado nuevoEmpleado = new Empleado(null, nombre, cedula, salario, comisiones, 0f, 0f, rol);
							
							empresa.getMisEmpleados().add(nuevoEmpleado);
							JOptionPane.showMessageDialog(null, "Empleado registrado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
							clean(); // Limpiamos para permitir otro registro
						} 
						// --- MODO ACTUALIZACIÓN ---
						else {
							miEmpleado.setNombre(nombre);
							miEmpleado.setCedula(cedula);
							miEmpleado.setRolEmpleado(rol);
							miEmpleado.setSalario(salario);
							miEmpleado.setComisiones(comisiones);
							
							JOptionPane.showMessageDialog(null, "Empleado actualizado con éxito", "Información", JOptionPane.INFORMATION_MESSAGE);
							dispose(); // Si estamos actualizando, cerramos la ventana al terminar
						}
						
						// En ambos casos, guardamos los datos inmediatamente
						empresa.GuardarDatos(
								empresa.getMisClientes(), 
								empresa.getMisEmpleados(), 
								empresa.getMisPlanes(), 
								empresa.getMisServicios(), 
								empresa.getMisUsuarios(), 
								empresa.getMisContratos(), 
								empresa.getPagos()
						);
					}
				});
				btnAccion.setActionCommand("OK");
				buttonPane.add(btnAccion);
				getRootPane().setDefaultButton(btnAccion);
			}
			{
				JButton btnCancelar = new JButton("Cancelar");
				btnCancelar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				btnCancelar.setActionCommand("Cancel");
				buttonPane.add(btnCancelar);
			}
		}
		
		// Llenamos los datos al arrancar si es modo actualización
		loadEmpleado();
	}

	// Método para llenar la ventana con los datos del empleado a modificar
	private void loadEmpleado() {
		if(miEmpleado != null) {
			txtId.setText(miEmpleado.getIdPersona());
			txtCodigo.setText(miEmpleado.getCodigoEmpleado());
			txtNombre.setText(miEmpleado.getNombre());
			txtCedula.setText(miEmpleado.getCedula());
			cbxRol.setSelectedItem(miEmpleado.getRolEmpleado());
			spnSalario.setValue((double) miEmpleado.getSalario());
			spnComisiones.setValue((double) miEmpleado.getComisiones());
		}
	}

	// Método para resetear la ventana en modo registro
	private void clean() {
		txtNombre.setText("");
		txtCedula.setText("");
		cbxRol.setSelectedIndex(0);
		spnSalario.setValue(0.0d);
		spnComisiones.setValue(0.0d);
		
		// Actualizamos los visualizadores de ID para el próximo registro
		txtId.setText("E - " + EmpresaAltice.getInstance().idEmpleados);
		String anioActual = String.valueOf(LocalDate.now().getYear());
		txtCodigo.setText("EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados);
	}
}