package logico;

import java.util.ArrayList;

public class Cliente extends Persona{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCliente;
	private String direccion;
	private String email;
	private Contrato miContrato;
	private float deuda;
	private boolean esEmpresa;
	private String rnc;
	private ArrayList<Pagos>misPagos;

	public Cliente(String idPersona, String nombre, String codigoCliente, String direccion, String email, float deuda, boolean esEmpresa, String rnc) {
		super(idPersona, nombre);
		this.codigoCliente = codigoCliente;
		this.direccion = direccion;
		this.email = email;
		this.deuda = deuda;
		this.setEsEmpresa(esEmpresa);
		this.setRnc(rnc);
		this.misPagos = new ArrayList<Pagos>();
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public float getDeuda() {
		return deuda;
	}

	public void setDeuda(float deuda) {
		this.deuda = deuda;
	}

	public Contrato getMiContrato() {
		return miContrato;
	}

	public void setMiContrato(Contrato miContrato) {
		this.miContrato = miContrato;
	}

	public boolean isEsEmpresa() {
		return esEmpresa;
	}

	public void setEsEmpresa(boolean esEmpresa) {
		this.esEmpresa = esEmpresa;
	}

	public String getRnc() {
		return rnc;
	}

	public void setRnc(String rnc) {
		this.rnc = rnc;
	}
	
	public void actualizarPagosAcumulados() {
		float pagoAcumulado = 0;
		for(Pagos p: misPagos) {
			pagoAcumulado = p.getPagoDelCliente();
		}
		misPagos.get(misPagos.size()-1).setPagoAcumulado(pagoAcumulado);
	}
	
	public void verResumenMiInfo() {
		System.out.println("ID del Contrato: " + miContrato.getIdContrato());
		System.out.println("Fecha de inicio del contrato" + miContrato.getFechaInicioContrato());
		System.out.println("Fecha de fin del contrato" + miContrato.getFechaFinContrato());
		
		System.out.println("ID del Plan: " + miContrato.getPlanContrato().getIdPlan());
		System.out.println("Nombre del plan: " + miContrato.getPlanContrato().getNombrePlan());
		
		float precioTotalPlan = miContrato.getPlanContrato().getPrecioTotal();
		float duracionContrato = miContrato.getDuracionContrato();
		
		if(miContrato.getPlazoContrato().equalsIgnoreCase("Mensual")) {
			System.out.println("Duración en meses del contrato: " + duracionContrato);
			System.out.println("Cuotas mensuales: " + precioTotalPlan / duracionContrato);
		}
		else if(miContrato.getPlazoContrato().equalsIgnoreCase("Anual")) {
			System.out.println("Duración en meses del contrato: " + duracionContrato * 12);
			System.out.println("Cuotas anuales: " + precioTotalPlan / duracionContrato);
		}
		if(misPagos.size() > 0) {
			actualizarPagosAcumulados();
			System.out.println("Total pagado: " + misPagos.get(misPagos.size()-1).getPagoAcumulado());
		}
		System.out.println("Total por pagar: " + miContrato.getPlanContrato().getPrecioTotal());
	}
	
	public void verMisPagos() {
		for(Pagos p: misPagos) {
			System.out.println("Id del pago" + p.getIdPago());
			System.out.println("Fecha del pago: " + p.getFechaPagoDelCliente());
			System.out.println("Monto del pago: " + p.getPagoDelCliente());
			System.out.println("Total del plan por pagar: " + p.getTotalPorPagar());
		}
	}
	
	
}
