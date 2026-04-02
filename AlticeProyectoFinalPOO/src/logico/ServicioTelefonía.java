package logico;

public class ServicioTelefonía {
	
	private int datosGb;
	private int minutos;
	private int sms;
	private boolean llamadasIlimitadas;
	private float costoPorMinuto;
	
	public ServicioTelefonía(boolean llamadasIlimitadas, float costoPorMinuto) {
		super();
		this.llamadasIlimitadas = llamadasIlimitadas;
		this.costoPorMinuto = costoPorMinuto;
	}
	
	public boolean isLlamadasIlimitadas() {
		return llamadasIlimitadas;
	}
	public void setLlamadasIlimitadas(boolean llamadasIlimitadas) {
		this.llamadasIlimitadas = llamadasIlimitadas;
	}
	public float getCostoPorMinuto() {
		return costoPorMinuto;
	}
	public void setCostoPorMinuto(float costoPorMinuto) {
		this.costoPorMinuto = costoPorMinuto;
	}	
	
}
