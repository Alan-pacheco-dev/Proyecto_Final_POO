package logico;

import java.io.Serializable;
import java.util.ArrayList;

public class EmpresaAltice implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EmpresaAltice empresaAltice = null;
	private ArrayList<Cliente>misClientes;
	private ArrayList<Empleado>misEmpleados;
	private ArrayList<Plan>misPlanes;
	private ArrayList<Servicio>misServicios;
	private ArrayList<Usuario>misUsuarios;
	private ArrayList<Contrato>misContratos;
	private ArrayList<Pagos>pagos;

	public EmpresaAltice() {
		super();
		this.misClientes = new ArrayList<Cliente>();;
		this.misEmpleados = new ArrayList<Empleado>();
		this.misPlanes = new ArrayList<Plan>();
		this.misServicios = new ArrayList<Servicio>();
		this.misUsuarios = new ArrayList<Usuario>();
		this.misContratos = new ArrayList<Contrato>();
		this.pagos = new ArrayList<Pagos>();
	}

	public EmpresaAltice getEmpresaAltice() {
		return empresaAltice;
	}

	public void setEmpresaAltice(EmpresaAltice empresaAltice) {
		this.empresaAltice = empresaAltice;
	}

	public ArrayList<Cliente> getMisClientes() {
		return misClientes;
	}

	public void setMisClientes(ArrayList<Cliente> misClientes) {
		this.misClientes = misClientes;
	}

	public ArrayList<Empleado> getMisEmpleados() {
		return misEmpleados;
	}

	public void setMisEmpleados(ArrayList<Empleado> misEmpleados) {
		this.misEmpleados = misEmpleados;
	}

	public ArrayList<Plan> getMisPlanes() {
		return misPlanes;
	}

	public void setMisPlanes(ArrayList<Plan> misPlanes) {
		this.misPlanes = misPlanes;
	}

	public ArrayList<Servicio> getMisServicios() {
		return misServicios;
	}

	public void setMisServicios(ArrayList<Servicio> misServicios) {
		this.misServicios = misServicios;
	}

	public ArrayList<Usuario> getMisUsuarios() {
		return misUsuarios;
	}

	public void setMisUsuarios(ArrayList<Usuario> misUsuarios) {
		this.misUsuarios = misUsuarios;
	}

	public ArrayList<Contrato> getMisContratos() {
		return misContratos;
	}

	public void setMisContratos(ArrayList<Contrato> misContratos) {
		this.misContratos = misContratos;
	}

	public ArrayList<Pagos> getPagos() {
		return pagos;
	}

	public void setPagos(ArrayList<Pagos> pagos) {
		this.pagos = pagos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public EmpresaAltice getInstance() {
		
		if(empresaAltice == null) {
			empresaAltice = new EmpresaAltice();
		}
		return empresaAltice;
	}
	
}
