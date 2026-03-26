package logico;

import java.util.ArrayList;

public class Servicio {
	
	private String idServicio;
	private String tipoServicio;
	private boolean isActivo=false;
	private float precioServicio; //Precio por cada servicio individual que ayudará a calcular el total del plan
	
	public String getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(String idServicio) {
		this.idServicio = idServicio;
	}
	public String getTipoServicio() {
		return tipoServicio;
	}
	public void setTipoServicio(String tipoServicio) {
		this.tipoServicio = tipoServicio;
	}
	public boolean isActivo() {
		return isActivo;
	}
	public void setActivo(boolean isActivo) {
		this.isActivo = isActivo;
	}
	public float getPrecioServicio() {
		return precioServicio;
	}
	public void setPrecioServicio(float precioServicio) {
		this.precioServicio = precioServicio;
	}
	
	public Servicio(String idServicio, String tipoServicio, boolean isActivo, float precioServicio) {
		super();
		this.idServicio = idServicio;
		this.tipoServicio = tipoServicio;
		this.precioServicio = precioServicio;
	}
	
}
