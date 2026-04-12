package logico;

import java.io.Serializable;
import java.time.LocalDate;

public class Contrato implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idContrato;
	private Cliente cliente;
	private Empleado empConsiguioContrato;
	private float porcentajeComisionAplicado;
	private float precioMensualAcordado;
	private LocalDate fechaInicioContrato;
	private LocalDate fechaFinContrato;
	private boolean isActivo;
	private Plan planContrato;
	
	public Contrato(Cliente cliente, Empleado empConsiguioContrato, float porcentajeComisionAplicado, float precioMensualAcordado, LocalDate fechaInicioContrato,LocalDate fechaFinContrato, Plan planContrato) {
		super();
		this.idContrato = "Cto - " + EmpresaAltice.getInstance().idContratos++;
		this.cliente = cliente;
		this.empConsiguioContrato = empConsiguioContrato;
		this.porcentajeComisionAplicado = porcentajeComisionAplicado;
		this.fechaInicioContrato = fechaInicioContrato;
		this.fechaFinContrato = fechaFinContrato;
		//Inicia en verdadero porque a la hora de crearse un contrato este debe estar activo
		this.isActivo = true;
		this.planContrato = planContrato;
		this.precioMensualAcordado = planContrato.getPrecioTotal();
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

	public Empleado getEmpConsiguioContrato() {
		return empConsiguioContrato;
	}

	public void setEmpConsiguioContrato(Empleado empConsiguioContrato) {
		this.empConsiguioContrato = empConsiguioContrato;
	}

	public float getPorcentajeComisionAplicado() {
		return porcentajeComisionAplicado;
	}

	public void setPorcentajeComisionAplicado(float porcentajeComisionAplicado) {
		this.porcentajeComisionAplicado = porcentajeComisionAplicado;
	}

	public float getPrecioMensualAcordado() {
		return precioMensualAcordado;
	}

	public void setPrecioMensualAcordado(float precioMensualAcordado) {
		this.precioMensualAcordado = precioMensualAcordado;
	}
	
	public float calcularComisionEmpleado() {
		float totalCalculadoComision = 0;
			
		totalCalculadoComision = precioMensualAcordado * porcentajeComisionAplicado;
		
		return totalCalculadoComision;
		
	}
	
}
