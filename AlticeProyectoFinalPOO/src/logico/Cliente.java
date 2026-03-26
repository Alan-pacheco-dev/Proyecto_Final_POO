package logico;

public class Cliente extends Persona{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCliente;
	private String direccion;
	private String email;
	private Contrato miContrato;
	private float deuda;
	private boolean esEmpresa;
	private String rnc;

	public Cliente(String idPersona, String nombre, String codigoCliente, String direccion, String email, float deuda, boolean esEmpresa, String rnc) {
		super(idPersona, nombre);
		this.codigoCliente = codigoCliente;
		this.direccion = direccion;
		this.email = email;
		this.deuda = deuda;
		this.setEsEmpresa(esEmpresa);
		this.setRnc(rnc);
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

	public Contrato getMiContrato() {
		return miContrato;
	}

	public void setMiContrato(Contrato miContrato) {
		this.miContrato = miContrato;
	}

	public boolean isEsEmpresa() {
		return esEmpresa;
	}

	public void setEsEmpresa(boolean esEmpresa) {
		this.esEmpresa = esEmpresa;
	}

	public String getRnc() {
		return rnc;
	}

	public void setRnc(String rnc) {
		this.rnc = rnc;
	}
	
	
}
