package logico;

import java.util.ArrayList;

public class Plan {
	
	private String idPlan;
	private String nombrePlan;
	private String descripcionCuota; //Aquí se indica cada qué tiempo debe ser pagado el plan. Ejemplo: Cada 5 meses
	
	//En el precio total se incluirá el cálculo por ITBIS 18%
	private float precioTotal; //Este será el total de la sumatoria de los servicios
	private ArrayList<Servicio>serviciosPlan;
	
}
