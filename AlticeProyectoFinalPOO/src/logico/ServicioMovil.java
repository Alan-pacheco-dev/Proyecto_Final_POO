package logico;

public class ServicioMovil extends Servicio{
	
	private int datosGb;
	private int minutos;
	private int sms;
	
	public ServicioMovil(String tipoServicio, float precioServicio, int datosGb, int minutos, int sms) {
		super(tipoServicio, precioServicio);
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
