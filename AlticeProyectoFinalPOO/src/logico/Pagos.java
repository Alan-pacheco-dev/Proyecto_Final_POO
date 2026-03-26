package logico;

import java.time.LocalDate;

public class Pagos {
	
	private String idPago;
	private Contrato contrato;
	private LocalDate fechaInicioPago;
	private LocalDate fechaVencimientoPago;
	private boolean estadoPagado; //Boolean que si es falso indica que falta por pagar, y si es verdadero es que ha sido pagado totalmente
	private float totalPorPagar;
	private Plan planPorpagar;
}
