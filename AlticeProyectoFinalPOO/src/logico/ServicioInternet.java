package logico;

public class ServicioInternet extends Servicio{

	private int velocidadMbps;
	private boolean tieneRouter;
	
	public ServicioInternet(String tipoServicio, float precioServicio, int velocidadMbps, boolean tieneRouter) {
		super(tipoServicio, precioServicio);
		this.velocidadMbps = velocidadMbps;
		this.tieneRouter = tieneRouter;
	}
	
	public int getVelocidadMbps() {
		return velocidadMbps;
	}
	public void setVelocidadMbps(int velocidadMbps) {
		this.velocidadMbps = velocidadMbps;
	}
	public boolean isTieneRouter() {
		return tieneRouter;
	}
	public void setTieneRouter(boolean tieneRouter) {
		this.tieneRouter = tieneRouter;
	}
	
}
