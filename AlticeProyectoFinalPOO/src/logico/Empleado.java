package logico;

public class Empleado {
	private String codigoEmpleado;
	private float salario;
	private float comisiones;
	private float ventas;
	private Usuario miUsuario;
	
	public Empleado(String codigoEmpleado, float salario, float comisiones, float ventas, Usuario miUsuario) {
		super();
		this.codigoEmpleado = codigoEmpleado;
		this.salario = salario;
		this.comisiones = comisiones;
		this.ventas = ventas;
		this.miUsuario = miUsuario;
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

	public float getVentas() {
		return ventas;
	}

	public void setVentas(float ventas) {
		this.ventas = ventas;
	}
	
	public Usuario getMiUsuario() {
		return miUsuario;
	}

	public void setMiUsuario(Usuario miUsuario) {
		this.miUsuario = miUsuario;
	}
	
	
}
