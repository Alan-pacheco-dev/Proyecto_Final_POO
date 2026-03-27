package logico;

public class ServicioInternet {

	private int velocidadMbps;
	private boolean tieneRouter;
	
	public ServicioInternet(int velocidadMbps, boolean tieneRouter) {
		super();
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
