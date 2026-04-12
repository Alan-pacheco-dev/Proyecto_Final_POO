package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Servicio implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idServicio;
	private String tipoServicio;
	private boolean isActivo;
	private boolean estaEnUso;
	private float precioServicio; //Precio por cada servicio individual que ayudará a calcular el total del plan
	
	public Servicio(String tipoServicio, float precioServicio) {
		super();
		this.idServicio = "S - " + EmpresaAltice.getInstance().idServicios++;
		this.tipoServicio = tipoServicio;
		this.precioServicio = precioServicio;
		this.isActivo = true;
		this.setEstaEnUso(false);
	}
	 
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

	public boolean isEstaEnUso() {
		return estaEnUso;
	}

	public void setEstaEnUso(boolean estaEnUso) {
		this.estaEnUso = estaEnUso;
	}
	
	
}
