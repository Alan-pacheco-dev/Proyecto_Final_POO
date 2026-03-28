package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class Plan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idPlan;
	private String nombrePlan;
	private String tiempoCuota; //Aquí se indica si es mensual o anual
	
	//En el precio total se incluirá el cálculo por ITBIS 18%
	private float precioTotal; //Este será el total de la sumatoria de los servicios
	private ArrayList<Servicio>serviciosPlan;
	
	public Plan(String idPlan, String nombrePlan, String tiempoCuota, float precioTotal) {
		super();
		this.idPlan = idPlan;
		this.nombrePlan = nombrePlan;
		this.tiempoCuota = tiempoCuota;
		this.precioTotal = precioTotal;
		this.serviciosPlan = new ArrayList<Servicio>();
	}
	
	public String getIdPlan() {
		return idPlan;
	}
	public void setIdPlan(String idPlan) {
		this.idPlan = idPlan;
	}
	public String getNombrePlan() {
		return nombrePlan;
	}
	public void setNombrePlan(String nombrePlan) {
		this.nombrePlan = nombrePlan;
	}
	public String getTiempoCuota() {
		return tiempoCuota;
	}
	public void setTiempoCuota(String descripcionCuota) {
		this.tiempoCuota = descripcionCuota;
	}
	public float getPrecioTotal() {
		return precioTotal;
	}
	public void setPrecioTotal(float precioTotal) {
		this.precioTotal = precioTotal;
	}
	public ArrayList<Servicio> getServiciosPlan() {
		return serviciosPlan;
	}
	public void setServiciosPlan(ArrayList<Servicio> serviciosPlan) {
		this.serviciosPlan = serviciosPlan;
	}
	
	
}
