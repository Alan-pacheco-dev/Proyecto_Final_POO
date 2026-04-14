package logico;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Plan implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String idPlan;
	private String nombrePlan;
	//En el precio total se incluiría el cálculo por ITBIS 18%
	private float precioMensual; //Este sería el total de la sumatoria de los servicios mensualmente
	private float precioSinImpuestos;
	private ArrayList<Servicio>serviciosPlan;
	
	public Plan(String nombrePlan, String tiempoCuota, float precioTotal) {
		super();
		this.idPlan = "PAG - " + EmpresaAltice.getInstance().idPlanes++;
		this.nombrePlan = nombrePlan;
		this.serviciosPlan = new ArrayList<Servicio>();
		this.precioMensual = calcularPrecioTotal();
		this.precioSinImpuestos = 0;
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
	public float getPrecioTotal() {
		return precioMensual;
	}
	public void setPrecioTotal(float precioMensual) {
		this.precioMensual = precioMensual;
	}
	public ArrayList<Servicio> getServiciosPlan() {
		return serviciosPlan;
	}
	public void setServiciosPlan(ArrayList<Servicio> serviciosPlan) {
		this.serviciosPlan = serviciosPlan;
	}
	
	public float calcularPrecioTotal() {
		float precioTotal = 0;
		if (serviciosPlan != null) {
			for (Servicio servis : serviciosPlan) {
				precioTotal += servis.getPrecioServicio();
			}
		}
		//Calculado con el ITBIS
		setPrecioSinImpuestos(precioTotal);
		return precioTotal * 1.18f;
	}
	
	public void actualizarPrecioPlan(float nuevoPrecio) {
	    EmpresaAltice altice = EmpresaAltice.getInstance();
	    
	    this.setPrecioTotal(nuevoPrecio);
	    
	    ArrayList<Contrato> contratosRenovados = new ArrayList<>();
	    
	    for(Contrato c : altice.getMisContratos()) {
	        if(c.getPlanContrato().getIdPlan().equalsIgnoreCase(this.idPlan) && c.isActivo()) {
	            
	        	c.setActivo(false); 
	        	c.setFechaFinContrato(LocalDate.now());
	            
	            Contrato nuevo = new Contrato(
	                c.getCliente(), 
	                c.getEmpConsiguioContrato(), 
	                c.getPorcentajeComisionAplicado(), 
	                nuevoPrecio,
	                LocalDate.now(), 
	                null, 
	                this
	            );
	            
	            contratosRenovados.add(nuevo);
	            
	            c.getCliente().getMisContratos().add(nuevo);
	        }
	    }
	    
	    altice.getMisContratos().addAll(contratosRenovados);
	}

	public float getPrecioSinImpuestos() {
		return precioSinImpuestos;
	}

	public void setPrecioSinImpuestos(float precioSinImpuestos) {
		this.precioSinImpuestos = precioSinImpuestos;
	}
	
}
