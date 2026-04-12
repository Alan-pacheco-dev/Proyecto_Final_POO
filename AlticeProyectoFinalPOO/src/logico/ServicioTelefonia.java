package logico;

public class ServicioTelefonia extends Servicio {
	
	private int minutosIncluidos; // Minutos que trae el plan (0 si es ilimitado o no trae)
	private boolean llamadasIlimitadas; // True si puede llamar sin límite de tiempo
	private float costoMinutoExtra; // Cuánto cuesta el minuto si se pasa de su límite
	
	public ServicioTelefonia(String tipoServicio, float precioServicio, int minutosIncluidos, boolean llamadasIlimitadas, float costoMinutoExtra) {
		super(tipoServicio, precioServicio);
		this.minutosIncluidos = minutosIncluidos;
		this.llamadasIlimitadas = llamadasIlimitadas;
		this.costoMinutoExtra = costoMinutoExtra;
	}

	public int getMinutosIncluidos() {
		return minutosIncluidos;
	}

	public void setMinutosIncluidos(int minutosIncluidos) {
		this.minutosIncluidos = minutosIncluidos;
	}

	public boolean isLlamadasIlimitadas() {
		return llamadasIlimitadas;
	}

	public void setLlamadasIlimitadas(boolean llamadasIlimitadas) {
		this.llamadasIlimitadas = llamadasIlimitadas;
	}

	public float getCostoMinutoExtra() {
		return costoMinutoExtra;
	}

	public void setCostoMinutoExtra(float costoMinutoExtra) {
		this.costoMinutoExtra = costoMinutoExtra;
	}	
}