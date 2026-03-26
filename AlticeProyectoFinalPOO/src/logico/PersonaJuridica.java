package logico;

public class PersonaJuridica {
	
	private String rncEmpresa;
	private boolean registroMercantilVigente;
	private String cedulaRepresentante;
	
	public PersonaJuridica(String rncEmpresa, boolean registroMercantilVigente, String cedulaRepresentante) {
		super();
		this.rncEmpresa = rncEmpresa;
		this.registroMercantilVigente = registroMercantilVigente;
		this.cedulaRepresentante = cedulaRepresentante;
	}
	
	public String getRncEmpresa() {
		return rncEmpresa;
	}
	public void setRncEmpresa(String rncEmpresa) {
		this.rncEmpresa = rncEmpresa;
	}
	public boolean isRegistroMercantilVigente() {
		return registroMercantilVigente;
	}
	public void setRegistroMercantilVigente(boolean registroMercantilVigente) {
		this.registroMercantilVigente = registroMercantilVigente;
	}
	public String getCedulaRepresentante() {
		return cedulaRepresentante;
	}
	public void setCedulaRepresentante(String cedulaRepresentante) {
		this.cedulaRepresentante = cedulaRepresentante;
	}
	
}
