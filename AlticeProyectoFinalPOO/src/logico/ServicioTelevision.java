package logico;

public class ServicioTelevision extends Servicio {
	
	private int cantidadCanales;
	private int cajitasIncluidas; // Cuántas cajas para TV trae el plan base
	private boolean tieneHD; // ¿El plan incluye canales en alta definición?
	
	public ServicioTelevision(String tipoServicio, float precioServicio, int cantidadCanales, int cajitasIncluidas, boolean tieneHD) {
		super(tipoServicio, precioServicio);
		this.cantidadCanales = cantidadCanales;
		this.cajitasIncluidas = cajitasIncluidas;
		this.tieneHD = tieneHD;
	}

	public int getCantidadCanales() {
		return cantidadCanales;
	}

	public void setCantidadCanales(int cantidadCanales) {
		this.cantidadCanales = cantidadCanales;
	}

	public int getCajitasIncluidas() {
		return cajitasIncluidas;
	}

	public void setCajitasIncluidas(int cajitasIncluidas) {
		this.cajitasIncluidas = cajitasIncluidas;
	}

	public boolean isTieneHD() {
		return tieneHD;
	}

	public void setTieneHD(boolean tieneHD) {
		this.tieneHD = tieneHD;
	}
}