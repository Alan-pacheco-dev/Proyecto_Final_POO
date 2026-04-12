package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Empleado extends Persona implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoEmpleado;
	private float salario;
	private float comisiones;
	private float montoVentas;
	private float cantidadVentas;
	private String rolEmpleado;
	private Usuario miUsuario;
	
	public Empleado(String id, String nombre, float salario, float comisiones, float cantidadVentas, float montoVentas, String rolEmpleado) {
		super(id, nombre);
		
		String anioActual = String.valueOf(LocalDate.now().getYear());
		
		//Se pasará en el main de la parte lógica el id como null, luego se actualiza solo al crearse el objeto
		this.codigoEmpleado = "EMP-" + anioActual + "-" + EmpresaAltice.getInstance().idEmpleados; // Debe ser autogenerado
		this.setIdPersona("E - " + EmpresaAltice.getInstance().idEmpleados++);
		this.salario = salario;
		this.comisiones = comisiones;
		this.montoVentas = montoVentas;
		this.cantidadVentas = cantidadVentas;
		this.miUsuario = null;
		this.rolEmpleado = rolEmpleado;
	}

	public String getCodigoEmpleado() {
		return codigoEmpleado;
	}

	public void setCodigoEmpleado(String codigoEmpleado) {
		this.codigoEmpleado = codigoEmpleado;
	}

	public float getSalario() {
		return salario;
	}

	public void setSalario(float salario) {
		this.salario = salario;
	}

	public float getComisiones() {
		return comisiones;
	}

	public void setComisiones(float comisiones) {
		this.comisiones = comisiones;
	}

	public float getMontoVentas() {
		return montoVentas;
	}

	public void setMontoVentas(float montoVentas) {
		this.montoVentas = montoVentas;
	}
	
	public Usuario getMiUsuario() {
		return miUsuario;
	}

	public void setMiUsuario(Usuario miUsuario) {
		this.miUsuario = miUsuario;
	}

	public String getRolEmpleado() {
		return rolEmpleado;
	}

	public void setRolEmpleado(String rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	}

	public float getCantidadVentas() {
		return cantidadVentas;
	}

	public void setCantidadVentas(float cantidadVentas) {
		this.cantidadVentas = cantidadVentas;
	}
	
	
}
