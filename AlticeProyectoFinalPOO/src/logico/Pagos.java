package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Pagos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String idPago;
	private Contrato contrato;
	private LocalDate fechaInicioPago;
	private LocalDate fechaVencimientoPago;
	private boolean isPagado; //Boolean que si es falso indica que falta por pagar, y si es verdadero es que ha sido pagado totalmente
	private float pagoDelCliente;
	private float totalPorPagar;
	
	public Pagos(String idPago, Contrato contrato, LocalDate fechaInicioPago, LocalDate fechaVencimientoPago,float pagoDelCliente, float totalPorPagar) {
		super();
		this.idPago = idPago;
		this.contrato = contrato;
		this.fechaInicioPago = fechaInicioPago;
		this.fechaVencimientoPago = fechaVencimientoPago;
		this.isPagado = false;
		this.totalPorPagar = totalPorPagar;
		this.pagoDelCliente = pagoDelCliente;
		this.totalPorPagar = totalPorPagar;
	}
	
	public String getIdPago() {
		return idPago;
	}
	public void setIdPago(String idPago) {
		this.idPago = idPago;
	}
	public Contrato getContrato() {
		return contrato;
	}
	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	public LocalDate getFechaInicioPago() {
		return fechaInicioPago;
	}
	public void setFechaInicioPago(LocalDate fechaInicioPago) {
		this.fechaInicioPago = fechaInicioPago;
	}
	public LocalDate getFechaVencimientoPago() {
		return fechaVencimientoPago;
	}
	public void setFechaVencimientoPago(LocalDate fechaVencimientoPago) {
		this.fechaVencimientoPago = fechaVencimientoPago;
	}
	public boolean isEstadoPagado() {
		return isPagado;
	}
	public void setEstadoPagado(boolean isPagado) {
		this.isPagado = isPagado;
	}
	public float getTotalPorPagar() {
		return totalPorPagar;
	}
	public void setTotalPorPagar(float totalPorPagar) {
		this.totalPorPagar = totalPorPagar;
	}
	public float getPagoDelCliente() {
		return pagoDelCliente;
	}
	public void setPagoDelCliente(float pagoDelCliente) {
		this.pagoDelCliente = pagoDelCliente;
	}
}
