package logico;

public class PersonaFisica {
	private String cedula;

	public PersonaFisica(String cedula) {
		super();
		this.setCedula(cedula);
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
}
