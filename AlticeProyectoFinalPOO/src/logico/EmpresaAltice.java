package logico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private ArrayList<Pagos>pagosClientes;

	public EmpresaAltice() {
		super();
		this.misClientes = new ArrayList<Cliente>();;
		this.misEmpleados = new ArrayList<Empleado>();
		this.misPlanes = new ArrayList<Plan>();
		this.misServicios = new ArrayList<Servicio>();
		this.misUsuarios = new ArrayList<Usuario>();
		this.misContratos = new ArrayList<Contrato>();
		this.pagosClientes = new ArrayList<Pagos>();
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
		return pagosClientes;
	}

	public void setPagos(ArrayList<Pagos> pagos) {
		this.pagosClientes = pagos;
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
	
	public void GuardarDatos(ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, 
			ArrayList<Plan> planes, ArrayList<Servicio> servicios, ArrayList<Usuario> usuarios, ArrayList<Contrato> contratos, 
			ArrayList<Pagos> pagos) 
	{
		//ObjectOutputStream file variable para controlar donde se va a escribir
		//new ObjectOutputStream() serializa los objetos y permite escribirlos en un fichero
		//new FileOutStream() abre/crea/sobrescribe el archivo
		try(ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream("Datos.txt")))
		{
			//writeObject() guarda los objetos mientras sea serializables
			file.writeObject(clientes);
			file.writeObject(empleados);
			file.writeObject(planes);
			file.writeObject(servicios);
			file.writeObject(usuarios);
			file.writeObject(contratos);
			file.writeObject(pagos);
			
		}catch(IOException e) {
			System.out.println("No se pudo guardar los datos");
		}
		
	}
	
	public void CargarDatos(ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, 
			ArrayList<Plan> planes, ArrayList<Servicio> servicios, ArrayList<Usuario> usuarios, ArrayList<Contrato> contratos, 
			ArrayList<Pagos> pagos) 
	{
		File archivo = new File("Datos.txt");
		
		if(!archivo.exists()) {
			System.out.println("No hay informacion previa");
			return;
		}
		
		//ObjectInputStream lo mismo que su contraparte pero para leer
		//new ObjectInputStream        ||                 ||
		//new FileInputStream          ||                 ||
		try(ObjectInputStream file= new ObjectInputStream(new FileInputStream(archivo))){
			
			//Los warnings son por que java automaticamente va a volver los objetos que lee al tipo que tiene ArrayList de forma automatica
			//Se tiene que leer en el mismo orden con el cual se escribio
			//abierto a cambios aqui
			clientes.addAll((ArrayList<Cliente>) file.readObject());
	        empleados.addAll((ArrayList<Empleado>) file.readObject());
	        planes.addAll((ArrayList<Plan>) file.readObject());
	        servicios.addAll((ArrayList<Servicio>) file.readObject());
	        usuarios.addAll((ArrayList<Usuario>) file.readObject());
	        contratos.addAll((ArrayList<Contrato>) file.readObject());
			
		}catch (Exception e) {
			System.out.println("no se pudo cargar los datos");
		}
		
	}
	
}
