package logico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmpresaAltice implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int idPagos = 1;
	public static int idContratos = 1;
	public static int idPlanes = 1;
	public static int idEmpleados = 1;
	public static int idClientes = 1;
	public static int idServicios = 1;
	public static int idUsuarios = 1;

	private static EmpresaAltice empresaAltice = null;
	private ArrayList<Cliente>misClientes;
	private ArrayList<Empleado>misEmpleados;
	private ArrayList<Plan>misPlanes;
	private ArrayList<Servicio>misServicios;
	private ArrayList<Usuario>misUsuarios;
	private ArrayList<Contrato>misContratos;
	private ArrayList<Pagos>pagosClientes;
	private static Usuario loginUser;

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

	public static EmpresaAltice getInstance() {

		if(empresaAltice == null) {
			empresaAltice = new EmpresaAltice();
		}
		return empresaAltice;
	}

	public void GuardarDatos(ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, 
			ArrayList<Plan> planes, ArrayList<Servicio> servicios, ArrayList<Usuario> usuarios, ArrayList<Contrato> contratos, 
			ArrayList<Pagos> pagos) 
	{

		boolean tieneAdministrativo = false;
		for (Usuario u : usuarios) {
			if (u.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
				tieneAdministrativo = true;
				break;
			}
		}


		if (!tieneAdministrativo) {
			Usuario admin = new Usuario("Administrativo", "Admin", "1234");
			usuarios.add(admin);
		}

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

			//Limpiar para que no hayan duplicados con addAll
			clientes.clear();
			empleados.clear();
			planes.clear();
			servicios.clear();
			usuarios.clear();
			contratos.clear();
			pagos.clear();

			//Los warnings son por que java automaticamente va a volver los objetos que lee al tipo que tiene ArrayList de forma automatica
			//Se tiene que leer en el mismo orden con el cual se escribio
			//abierto a cambios aqui
			clientes.addAll((ArrayList<Cliente>) file.readObject());
			empleados.addAll((ArrayList<Empleado>) file.readObject());
			planes.addAll((ArrayList<Plan>) file.readObject());
			servicios.addAll((ArrayList<Servicio>) file.readObject());
			usuarios.addAll((ArrayList<Usuario>) file.readObject());
			contratos.addAll((ArrayList<Contrato>) file.readObject());
			pagos.addAll((ArrayList<Pagos>) file.readObject());

		}catch (Exception e) {
			System.out.println("no se pudo cargar los datos");
		}

	}
	
	public void generarPagosMensuales() {
	    LocalDate hoy = LocalDate.now();
	    
	    for (Contrato c : misContratos) {
	        if (!c.isActivo()) continue;
	        
	        Pagos ultimoPago = null;
	        for (Pagos p : pagosClientes) {
	            if (p.getContrato().getIdContrato().equals(c.getIdContrato())) {
	                if (ultimoPago == null || p.getFechaVencimientoPago().isAfter(ultimoPago.getFechaVencimientoPago())) {
	                    ultimoPago = p;
	                }
	            }
	        }
	        LocalDate fechaInicioPago;
	        LocalDate fechaVencimiento;
	        
	        if (ultimoPago == null) {
	            fechaInicioPago = c.getFechaInicioContrato();
	            fechaVencimiento = fechaInicioPago.plusMonths(1);
	        } else {
	            if (hoy.isBefore(ultimoPago.getFechaVencimientoPago())) {
	                continue;
	            }
	            fechaInicioPago = ultimoPago.getFechaVencimientoPago();
	            fechaVencimiento = fechaInicioPago.plusMonths(1);
	        }
	        
	        Pagos nuevoPago = new Pagos(
	            c,
	            fechaInicioPago,
	            fechaVencimiento,
	            null, 
	            0,
	            0,    
	            c.getPrecioMensualAcordado()
	        );
	        pagosClientes.add(nuevoPago);
	    }
	    
	    actualizarDeudaClientes();
	    GuardarDatos(misClientes, misEmpleados, misPlanes, misServicios, 
	                 misUsuarios, misContratos, pagosClientes);
	}
	
	public void actualizarDeudaClientes() {
	    for (Cliente c : misClientes) {
	        c.setDeuda(0);
	    }
	    
	    for (Pagos p : pagosClientes) {
	        if (!p.isPagadoTotal()) {
	            Cliente cliente = p.getContrato().getCliente();
	            cliente.setDeuda(cliente.getDeuda() + p.getTotalPorPagar());
	        }
	    }
	}


	public boolean registrarCliente(Empleado empleado, Cliente cliente) {
		boolean registrado = false;
		if (empleado.getMiUsuario() == null) {
			throw new RuntimeException("El empleado no tiene un usuario asignado");
		}
		if (!empleado.getMiUsuario().getRolEmpleado().equalsIgnoreCase("Comercial")) {
			throw new RuntimeException("Solo empleados comerciales pueden registrar clientes");
		}

		// lï¿½gica de registro
		misClientes.add(cliente);
		//Usuario usuarioCliente = new Usuario()
		registrado = true;
		return registrado;
	}

	public boolean crearUsuario(Empleado emp) {
		boolean usuarioCreado = false;

		if(emp != null) {
			//La contrasenia debe ser pedida en la parte visual al igual que el nombre de usuario
			Usuario nuevoUsuario = new Usuario(emp.getRolEmpleado(), "username", "*********");
			misUsuarios.add(nuevoUsuario);
			usuarioCreado = true;
		}

		return usuarioCreado;
	}

	public static Usuario getLoginUser() {
		return loginUser;
	}

	public static void setLoginUser(Usuario loginUser) {
		EmpresaAltice.loginUser = loginUser;
	}

	public Contrato buscarContratoById(String idContrato) {
		Contrato encontrado = null;
		for(Contrato c: misContratos) {
			if(c.getIdContrato().equalsIgnoreCase(idContrato)) {
				encontrado =  c;
			}
		}
		return encontrado;
	}

	public Cliente buscarClienteById(String idCliente) {
		Cliente encontrado = null;
		for(Cliente cli: misClientes) {
			if(cli.getIdPersona().equalsIgnoreCase(idCliente)) {
				encontrado =  cli;
			}
		}
		return encontrado;
	}



	public Empleado buscarEmpleadoById(String idEmpleado) {
		Empleado encontrado = null;
		for(Empleado emp: misEmpleados) {
			if(emp.getIdPersona().equalsIgnoreCase(idEmpleado)) {
				encontrado =  emp;
			}
		}
		return encontrado;
	}

	public Servicio buscarServicioById(String idServicio) {
		Servicio encontrado = null;
		for(Servicio servi: misServicios) {
			if(servi.getIdServicio().equalsIgnoreCase(idServicio)) {
				encontrado =  servi;
			}
		}
		return encontrado;
	}

	//Funciones de eliminación

	public boolean eliminarCliente(Cliente selected) {
		boolean eliminado = false;
		if(selected.getCantContratosActivos() == 0) {
			misClientes.remove(selected);
			eliminado = true;
		}
		return eliminado;
	}
	
	public boolean eliminarUsuarioEmpleadoByID(String idUsuario) {
		boolean eliminado = false;
		for (Empleado emp : misEmpleados) {
			if (emp.getMiUsuario() != null) {
				if (emp.getMiUsuario().getIdUsuario().equalsIgnoreCase(idUsuario)) {
					Usuario usuarioAEliminar = emp.getMiUsuario();
					emp.setMiUsuario(null);
					misUsuarios.remove(usuarioAEliminar);
					eliminado = true;
					break; 
				}
			}
		}
		return eliminado;
	}

	public boolean eliminarEmpleado(Empleado selected) {
		boolean eliminado = false;
		//if(selected == 0) {
		misEmpleados.remove(selected);
		eliminado = true;
		//}
		return eliminado;
	}



	public void actualizarContadores() {

		// 1. Actualizar el contador de CLIENTES
		int maxIdCliente = 0;
		for (Cliente c : misClientes) {
			// El truco de magia: replaceAll("[^0-9]", "") borra todas las letras y símbolos (como "C - ") 
			// y nos deja solo el número limpio para poder convertirlo a entero.
			String numeroLimpio = c.getIdPersona().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdCliente) {
					maxIdCliente = numero;
				}
			}
		}
		idClientes = maxIdCliente + 1;
		if(misClientes.size() == 0) {
			idClientes = 1;
		}

		// 2. Actualizar el contador de EMPLEADOS
		int maxIdEmpleado = 0;
		for (Empleado emp : misEmpleados) {
			String numeroLimpio = emp.getIdPersona().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdEmpleado) {
					maxIdEmpleado = numero;
				}
			}
		}
		idEmpleados = maxIdEmpleado + 1;
		if(misServicios.size() == 0) {
			idEmpleados = 1;
		}

		// 3. Actualizar el contador de SERVICIOS
		int maxIdServicio = 0;
		for (Servicio s : misServicios) {
			String numeroLimpio = s.getIdServicio().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdServicio) {
					maxIdServicio = numero;
				}
			}
		}
		idServicios = maxIdServicio + 1;
		if(misServicios.size() == 0) {
			idServicios = 1;
		}

		int maxIdContrato = 0;
		for (Contrato c : misContratos) {
			String numeroLimpio = c.getIdContrato().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdContrato) {
					maxIdContrato = numero;
				}
			}
		}
		idContratos = maxIdContrato + 1;
		if(misContratos.size() == 0) {
			idContratos = 1;
		}

		int maxIdPlanes = 0;
		for (Plan p : misPlanes) {
			String numeroLimpio = p.getIdPlan().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdPlanes) {
					maxIdPlanes = numero;
				}
			}
		}
		idPlanes= maxIdPlanes + 1;
		if(misPlanes.size() == 0) {
			idPlanes = 1;
		}

		int maxIdPagos = 0;
		for (Pagos p : pagosClientes) {
			String numeroLimpio = p.getIdPago().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdPagos) {
					maxIdPagos = numero;
				}
			}
		}
		idPagos = maxIdPagos + 1;
		if(pagosClientes.size() == 0) {
			idPagos = 1;
		}
		
		/*
		int maxIdUsuarios= 0;
		for (Usuario u : misUsuarios) {
			String numeroLimpio = u.getIdUsuario().replaceAll("[^0-9]", "");

			if (numeroLimpio.isEmpty() == false) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdUsuarios) {
					maxIdUsuarios = numero;
				}
			}
		}
		idUsuarios = maxIdUsuarios + 1;
		if(misUsuarios.size() == 0) {
			idUsuarios = 1;
		}
		*/
	}

	public void refrescarConteosContratos() {
		// Ponemos todos los contadores de los clientes en 0 primero
		for (Cliente cli : misClientes) {
			cli.setCantContratosActivos(0);
		}

		// Recorremos la lista maestra de contratos y sumamos solo los activos
		for (Contrato cto : misContratos) {
			if (cto.isActivo() == true) {
				Cliente dueño = cto.getCliente();
				dueño.setCantContratosActivos(dueño.getCantContratosActivos() + 1);
			}
		}
	}

}
