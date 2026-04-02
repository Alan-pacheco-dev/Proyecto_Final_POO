package logico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Cliente extends Persona{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String codigoCliente;
	private String direccion;
	private String email;
	private ArrayList<Contrato> misContratos;
	private float deuda;
	private boolean esEmpresa;
	private String rnc;
	private ArrayList<Pagos>misPagos;

	public Cliente(String idPersona, String nombre, String direccion, String email, float deuda, boolean esEmpresa, String rnc) {
		super(idPersona, nombre);
		
		String anioActual = String.valueOf(LocalDate.now().getYear());
		
		this.codigoCliente = "CLTE-" + anioActual + "-" +  EmpresaAltice.getInstance().idClientes;
		this.setIdPersona("C - " + EmpresaAltice.getInstance().idClientes++);
		this.direccion = direccion;
		this.email = email;
		this.deuda = deuda;
		this.setEsEmpresa(esEmpresa);
		this.setRnc(rnc);
		this.misPagos = new ArrayList<Pagos>();
		this.misContratos = new ArrayList<Contrato>();
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
	
	public ArrayList<Contrato> getMisContratos() {
		return misContratos;
	}
	
	public void setMisContratos(ArrayList<Contrato> misContratos) {
		this.misContratos = misContratos;
	}
	

	public void verResumenMiInfo(Contrato miContrato) {
		System.out.println("ID del Contrato: " + miContrato.getIdContrato());
		System.out.println("Fecha de inicio del contrato" + miContrato.getFechaInicioContrato());
		System.out.println("Fecha de fin del contrato" + miContrato.getFechaFinContrato());
		
		System.out.println("ID del Plan: " + miContrato.getPlanContrato().getIdPlan());
		System.out.println("Nombre del plan: " + miContrato.getPlanContrato().getNombrePlan());
		
		float precioTotalPlan = miContrato.getPlanContrato().getPrecioTotal();
		int duracionContratoDias = (int) ChronoUnit.DAYS.between(miContrato.getFechaInicioContrato(), LocalDate.now());
		
		System.out.println("Cuotas mensuales: " + precioTotalPlan);
		System.out.println("Días con el contrato: " + duracionContratoDias);
			
		if(misPagos.size() > 0) {
			System.out.println("Total pagado: " + misPagos.get(misPagos.size()-1).getPagoDelCliente());
		}
		System.out.println("Total por pagar: " + miContrato.getPlanContrato().getPrecioTotal());
	}
	
	public void verMisPagos() {
		for(Pagos p: misPagos) {
			System.out.println("Id del pago" + p.getIdPago());
			System.out.println("Fecha del pago: " + p.getFechaPagoDelCliente());
			System.out.println("Monto del pago: " + p.getPagoDelCliente());
			System.out.println("Cuota mensual por pagar: " + p.getTotalPorPagar());
		}
	}
	
	public void agregarContrato(Contrato cont) {
		misContratos.add(cont);
	}
	
	public int getCantContratosActivos() {
		int cantContratosActivos = 0;
		for(Contrato c: misContratos) {
			if(c.isActivo()) {
				cantContratosActivos++;
			}
		}
		return cantContratosActivos;
	}
	
	public int getTotalServiciosActivos() {
		int cantServiciosActivos = 0;
		for(Contrato c: misContratos){
			for(int ind = 0; ind < c.getPlanContrato().getServiciosPlan().size(); ind++)
			if(c.getPlanContrato().getServiciosPlan().get(ind).isActivo()) {
				cantServiciosActivos++;
			}
		}
		return cantServiciosActivos;
	}
	
	public boolean cancelarContratoByID(String idContrato) {
		boolean cancelado = false;
		//En la parte visual emitir un mensaje que diga que el cliente ha decidido cancelar el contrato
		for(Contrato c: misContratos) {
			if(c.getIdContrato().equalsIgnoreCase(idContrato)) {
				c.setActivo(false);
				cancelado = true;
			}
		}
		
		return cancelado;
	}
	
	public boolean solicitarReactivarContratoById(String idContrato) {
		boolean reactivado = false;
		//Este metodo debe ser editado luego en la parte visual para que sea Altice quien decida si reactivarlo o no
		for(Contrato c: misContratos) {
			if(c.getIdContrato().equalsIgnoreCase(idContrato)) {
				c.setActivo(true);
				reactivado = true;
			}
		}
		
		return reactivado;
	}
	
	public String getIdentificadorUnicoCliente() {

		if(esEmpresa == true) {
			return "RNC: " + rnc;
		}
		else {
			return "Cedula: " + rnc;
		}
	}
	

	
	
	
}
