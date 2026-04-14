package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Pagos implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	//Eliminé pagoAcumulado ya que los pagos serían mensuales y los contratos no tienen tiempo fijo, es decir pagarías mensualmente hasta que canceles el contrato
	
	private String idPago;
	private Contrato contrato;
	private LocalDate fechaInicioPago; //Fecha del mes en el que debe de pagar el cliente
	private LocalDate fechaVencimientoPago;//Fecha limite para que el cliente pague
	private boolean isPagadoTotal; //Boolean que si es falso indica que falta por pagar, y si es verdadero es que ha sido pagado totalmente
	private LocalDate fechaPagoDelCliente;
	private float pagoDelCliente;
	private float montoADevolverCliente;
	private float totalPorPagar;
	
	public Pagos(Contrato contrato, LocalDate fechaInicioPago, LocalDate fechaVencimientoPago,LocalDate fechaPagoDelCliente, float pagoAcumulado ,float pagoDelCliente, float totalPorPagar) {
		super();
		this.idPago = "P - " + EmpresaAltice.getInstance().idPagos++;
		this.contrato = contrato;
		this.fechaInicioPago = fechaInicioPago;
		this.fechaVencimientoPago = fechaVencimientoPago;
		this.setPagadoTotal(false);
		this.fechaPagoDelCliente = fechaPagoDelCliente;
		this.pagoDelCliente = pagoDelCliente;
		this.totalPorPagar = totalPorPagar - pagoAcumulado;
		this.montoADevolverCliente = 0;
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

	public LocalDate getFechaPagoDelCliente() {
		return fechaPagoDelCliente;
	}

	public void setFechaPagoDelCliente(LocalDate fechaPagoDelCliente) {
		this.fechaPagoDelCliente = fechaPagoDelCliente;
	}

	public boolean isPagadoTotal() {
		return isPagadoTotal;
	}

	public void setPagadoTotal(boolean isPagadoTotal) {
		this.isPagadoTotal = isPagadoTotal;
	}

	public float getMontoADevolverCliente() {
		return Math.round(montoADevolverCliente * 100) / 100.0f;
	}

	public void setMontoADevolverCliente(float montoADevolverCliente) {
		this.montoADevolverCliente = Math.round(montoADevolverCliente * 100) / 100.0f;
	}
}
