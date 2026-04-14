package logico;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idTicket;
	private Cliente clienteAfectado;
	private Contrato contratoAsociado;
	private Empleado tecnicoAsignado; // Puede ser null hasta que se asigne a alguien
	
	private String descripcionFalla;
	private String estado; // "Abierto", "En Proceso", "Resuelto"
	private String prioridad; // "Alta", "Media", "Baja"
	
	private LocalDateTime fechaReporte;
	private LocalDateTime fechaResolucion;
	
	public Ticket(Cliente clienteAfectado, Contrato contratoAsociado, String descripcionFalla, String prioridad) {
		super();
		// Nota: Debes crear la variable 'public int idTickets = 1;' en tu clase EmpresaAltice
		this.idTicket = "TK - " + EmpresaAltice.getInstance().idTickets++; 
		this.clienteAfectado = clienteAfectado;
		this.contratoAsociado = contratoAsociado;
		this.descripcionFalla = descripcionFalla;
		this.prioridad = prioridad;
		
		this.estado = "Abierto"; 
		this.fechaReporte = LocalDateTime.now(); // Captura la fecha y hora exacta del reporte
		this.tecnicoAsignado = null; 
		this.fechaResolucion = null;
	}

	public String getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}

	public Cliente getClienteAfectado() {
		return clienteAfectado;
	}

	public void setClienteAfectado(Cliente clienteAfectado) {
		this.clienteAfectado = clienteAfectado;
	}

	public Contrato getContratoAsociado() {
		return contratoAsociado;
	}

	public void setContratoAsociado(Contrato contratoAsociado) {
		this.contratoAsociado = contratoAsociado;
	}

	public Empleado getTecnicoAsignado() {
		return tecnicoAsignado;
	}

	// Método de negocio: Al asignar un técnico, el ticket pasa a estar 'En Proceso'
	public void setTecnicoAsignado(Empleado tecnicoAsignado) {
		this.tecnicoAsignado = tecnicoAsignado;
		if (this.estado.equalsIgnoreCase("Abierto")) {
			this.estado = "En Proceso";
		}
	}

	public String getDescripcionFalla() {
		return descripcionFalla;
	}

	public void setDescripcionFalla(String descripcionFalla) {
		this.descripcionFalla = descripcionFalla;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public LocalDateTime getFechaReporte() {
		return fechaReporte;
	}

	public void setFechaReporte(LocalDateTime fechaReporte) {
		this.fechaReporte = fechaReporte;
	}

	public LocalDateTime getFechaResolucion() {
		return fechaResolucion;
	}

	public void setFechaResolucion(LocalDateTime fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}
	
	// Método de negocio: Marca el ticket como solucionado y guarda la hora exacta
	public void marcarComoResuelto() {
		this.estado = "Resuelto";
		this.fechaResolucion = LocalDateTime.now();
	}
}