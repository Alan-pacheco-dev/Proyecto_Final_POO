package logico;

public class ServicioMovil {
	
	private int datosGb;
	private int minutos;
	private int sms;
	
	public ServicioMovil(int datosGb, int minutos, int sms) {
		super();
		this.datosGb = datosGb;
		this.minutos = minutos;
		this.sms = sms;
	}
	
	public int getDatosGb() {
		return datosGb;
	}
	public void setDatosGb(int datosGb) {
		this.datosGb = datosGb;
	}
	public int getMinutos() {
		return minutos;
	}
	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}
	public int getSms() {
		return sms;
	}
	public void setSms(int sms) {
		this.sms = sms;
	}
	
}
