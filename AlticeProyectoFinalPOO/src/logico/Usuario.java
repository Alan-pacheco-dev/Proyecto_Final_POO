package logico;

public class Usuario {
	
	private String rolEmpleado; //El rol solo le pertenece al empleado porque no se crearán usuarios del cliente
	//Más adelante si se quiere podemos crearle Usuario
	private String nombreUsuario;
	private String contrasenia;
	
	public Usuario(String rolEmpleado, String nombreUsuario, String contrasenia) {
		super();
		this.rolEmpleado = rolEmpleado;
		this.nombreUsuario = nombreUsuario;
		this.contrasenia = contrasenia;
	}

	public String getRolEmpleado() {
		return rolEmpleado;
	}

	public void setRolEmpleado(String rolEmpleado) {
		this.rolEmpleado = rolEmpleado;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
	
	
	
}
