package logico;

public class Persona {
	
	private String idPersona;
	private String nombre;
	
	public Persona(String idPersona, String nombre) {
		super();
		this.setIdPersona(idPersona);
		this.setNombre(nombre);
	}

	public String getIdPersona() {
		return idPersona;
	}

	public void setIdPersona(String idPersona) {
		this.idPersona = idPersona;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
