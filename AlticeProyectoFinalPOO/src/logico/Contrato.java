package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Contrato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idContrato;
	private Cliente cliente;
	private LocalDate fechaInicioContrato;
	private String plazoContrato; //Si es mensual o anual
	private int duracionContrato; //Tiempo en anios o meses que durará el contrato
	private LocalDate fechaFinContrato;
	private boolean isActivo;
	private Plan planContrato;
	
	public Contrato(String idContrato, Cliente cliente, LocalDate fechaInicioContrato,String plazoContrato ,int duracionContrato,LocalDate fechaFinContrato, Plan planContrato) {
		super();
		this.idContrato = idContrato;
		this.cliente = cliente;
		this.fechaInicioContrato = fechaInicioContrato;
		this.setPlazoContrato(plazoContrato);
		this.setDuracionContrato(duracionContrato);
		this.fechaFinContrato = fechaFinContrato;
		this.isActivo = false;
		this.planContrato = planContrato;
	}
	
	public String getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(String idContrato) {
		this.idContrato = idContrato;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public LocalDate getFechaInicioContrato() {
		return fechaInicioContrato;
	}
	public void setFechaInicioContrato(LocalDate fechaInicioContrato) {
		this.fechaInicioContrato = fechaInicioContrato;
	}
	public LocalDate getFechaFinContrato() {
		return fechaFinContrato;
	}
	public void setFechaFinContrato(LocalDate fechaFinContrato) {
		this.fechaFinContrato = fechaFinContrato;
	}
	public boolean isActivo() {
		return isActivo;
	}
	public void setActivo(boolean isActivo) {
		this.isActivo = isActivo;
	}
	public Plan getPlanContrato() {
		return planContrato;
	}
	public void setPlanContrato(Plan planContrato) {
		this.planContrato = planContrato;
	}

	public String getPlazoContrato() {
		return plazoContrato;
	}

	public void setPlazoContrato(String plazoContrato) {
		this.plazoContrato = plazoContrato;
	}

	public int getDuracionContrato() {
		return duracionContrato;
	}

	public void setDuracionContrato(int duracionContrato) {
		this.duracionContrato = duracionContrato;
	}
	
}
