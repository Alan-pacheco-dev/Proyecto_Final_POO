package logico;

import java.time.LocalDate;

public class Contrato {
	
	private String idContrato;
	private Cliente cliente;
	private LocalDate fechaInicioContrato;
	private LocalDate fechaFinContrato;
	private boolean isActivo;
	private Plan planContrato;
}
