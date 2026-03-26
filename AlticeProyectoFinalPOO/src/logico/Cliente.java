package logico;

public class Cliente extends Persona{
	
	private String codigoCliente;
	private String direccion;
	private String email;
	//private Contrato miContrato;
	private float deuda;

	public Cliente(String idPersona, String nombre, String codigoCliente, String direccion, String email, float deuda) {
		super(idPersona, nombre);
		this.codigoCliente = codigoCliente;
		this.direccion = direccion;
		this.email = email;
		this.deuda = deuda;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getDeuda() {
		return deuda;
	}

	public void setDeuda(float deuda) {
		this.deuda = deuda;
	}
	
	
}
