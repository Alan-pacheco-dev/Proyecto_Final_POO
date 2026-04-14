
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

public class EmpresaAltice implements Serializable {

	private static final long serialVersionUID = 1L;

	public static int idPagos = 1;
	public static int idContratos = 1;
	public static int idPlanes = 1;
	public static int idEmpleados = 1;
	public static int idClientes = 1;
	public static int idServicios = 1;
	public static int idUsuarios = 1;

	private static EmpresaAltice empresaAltice = null;
	private ArrayList<Cliente> misClientes;
	private ArrayList<Empleado> misEmpleados;
	private ArrayList<Plan> misPlanes;
	private ArrayList<Servicio> misServicios;
	private ArrayList<Usuario> misUsuarios;
	private ArrayList<Contrato> misContratos;
	private ArrayList<Pagos> pagosClientes;
	private static Usuario loginUser;

	public EmpresaAltice() {
		super();
		this.misClientes = new ArrayList<Cliente>();
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
		GuardarDatos(clientes, empleados, planes, servicios, usuarios, contratos, pagos, "Datos.txt");
	}


	public void GuardarDatos(ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, 
			ArrayList<Plan> planes, ArrayList<Servicio> servicios, ArrayList<Usuario> usuarios, ArrayList<Contrato> contratos, 
			ArrayList<Pagos> pagos, String nombreArchivo) 
	{
		try(ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(nombreArchivo)))
		{
			file.writeObject(clientes);
			file.writeObject(empleados);
			file.writeObject(planes);
			file.writeObject(servicios);
			file.writeObject(usuarios);
			file.writeObject(contratos);
			file.writeObject(pagos);

		}catch(IOException e) {
			System.out.println("No se pudo guardar los datos: " + e.getMessage());
		}
	}

	public void CargarDatos(ArrayList<Cliente> clientes, ArrayList<Empleado> empleados, 
			ArrayList<Plan> planes, ArrayList<Servicio> servicios, ArrayList<Usuario> usuarios, ArrayList<Contrato> contratos, 
			ArrayList<Pagos> pagos) 
	{
		File archivo = new File("Datos.txt");

		if(archivo.exists()) {
			try(ObjectInputStream file = new ObjectInputStream(new FileInputStream(archivo))){
				
				clientes.clear();
				empleados.clear();
				planes.clear();
				servicios.clear();
				usuarios.clear();
				contratos.clear();
				pagos.clear();

				clientes.addAll((ArrayList<Cliente>) file.readObject());
				empleados.addAll((ArrayList<Empleado>) file.readObject());
				planes.addAll((ArrayList<Plan>) file.readObject());
				servicios.addAll((ArrayList<Servicio>) file.readObject());
				usuarios.addAll((ArrayList<Usuario>) file.readObject());
				contratos.addAll((ArrayList<Contrato>) file.readObject());
				pagos.addAll((ArrayList<Pagos>) file.readObject());

			}catch (Exception e) {
				System.out.println("No se pudo cargar los datos");
			}
		} else {
			System.out.println("No hay información previa, se iniciará el sistema desde cero.");
		}
		
		boolean tieneAdministrativo = false;
		for (Usuario u : usuarios) {
			if (u.getRolEmpleado().equalsIgnoreCase("Administrativo")) {
				tieneAdministrativo = true;
				break;
			}
		}

		if (!tieneAdministrativo) {
			Usuario admin = new Usuario("Administrativo", "Admin", "Admin");
			admin.setIdUsuario("U - 0");
			usuarios.add(admin);
			idUsuarios--; 
		}
	}
	
	public Empleado buscarEmpleadoPorUsuario(Usuario usuario) {
	    for (Empleado emp : misEmpleados) {
	        if (emp.getMiUsuario() != null && 
	            emp.getMiUsuario().getNombreUsuario().equals(usuario.getNombreUsuario())) {
	            return emp;
	        }
	    }
	    return null;
	}
	
	public boolean CargarDatosDesdeRespaldo(String rutaArchivo) {
		File archivo = new File(rutaArchivo);

		if (!archivo.exists()) {
			return false; 
		}

		try (ObjectInputStream file = new ObjectInputStream(new FileInputStream(archivo))) {
			
			misClientes.clear();
			misEmpleados.clear();
			misPlanes.clear();
			misServicios.clear();
			misUsuarios.clear();
			misContratos.clear();
			pagosClientes.clear();

			misClientes.addAll((ArrayList<Cliente>) file.readObject());
			misEmpleados.addAll((ArrayList<Empleado>) file.readObject());
			misPlanes.addAll((ArrayList<Plan>) file.readObject());
			misServicios.addAll((ArrayList<Servicio>) file.readObject());
			misUsuarios.addAll((ArrayList<Usuario>) file.readObject());
			misContratos.addAll((ArrayList<Contrato>) file.readObject());
			pagosClientes.addAll((ArrayList<Pagos>) file.readObject());

			actualizarContadores();
			refrescarConteosContratos();
			actualizarDeudaClientes();

			GuardarDatos(misClientes, misEmpleados, misPlanes, misServicios, 
						 misUsuarios, misContratos, pagosClientes);

			return true;

		} catch (Exception e) {
			System.out.println("Error al restaurar el respaldo: " + e.getMessage());
			return false; 
		}
	}
	
	public void generarPagosMensuales() {
		int generados = 0;
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
			generados++;
		}

		if (generados > 0) {
			actualizarDeudaClientes();
			GuardarDatos(misClientes, misEmpleados, misPlanes, misServicios,
						 misUsuarios, misContratos, pagosClientes);
		}
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

		misClientes.add(cliente);
		registrado = true;
		return registrado;
	}

	public boolean crearUsuario(Empleado emp) {
		boolean usuarioCreado = false;

		if(emp != null) {
			Usuario nuevoUsuario = new Usuario(emp.getRolEmpleado(), "username", "***");
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
		misEmpleados.remove(selected);
		eliminado = true;
		return eliminado;
	}

	public void actualizarContadores() {
		int maxIdCliente = 0;
		for (Cliente c : misClientes) {
			String numeroLimpio = c.getIdPersona().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdCliente) {
					maxIdCliente = numero;
				}
			}
		}
		idClientes = maxIdCliente + 1;

		int maxIdEmpleado = 0;
		for (Empleado emp : misEmpleados) {
			String numeroLimpio = emp.getIdPersona().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdEmpleado) {
					maxIdEmpleado = numero;
				}
			}
		}
		idEmpleados = maxIdEmpleado + 1;

		int maxIdServicio = 0;
		for (Servicio s : misServicios) {
			String numeroLimpio = s.getIdServicio().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdServicio) {
					maxIdServicio = numero;
				}
			}
		}
		idServicios = maxIdServicio + 1;

		int maxIdContrato = 0;
		for (Contrato c : misContratos) {
			String numeroLimpio = c.getIdContrato().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdContrato) {
					maxIdContrato = numero;
				}
			}
		}
		idContratos = maxIdContrato + 1;

		int maxIdPlanes = 0;
		for (Plan p : misPlanes) {
			String numeroLimpio = p.getIdPlan().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdPlanes) {
					maxIdPlanes = numero;
				}
			}
		}
		idPlanes = maxIdPlanes + 1;

		int maxIdPagos = 0;
		for (Pagos p : pagosClientes) {
			String numeroLimpio = p.getIdPago().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdPagos) {
					maxIdPagos = numero;
				}
			}
		}
		idPagos = maxIdPagos + 1;
		
		int maxIdUsuarios = 0;
		for (Usuario u : misUsuarios) {
			String numeroLimpio = u.getIdUsuario().replaceAll("[^0-9]", "");
			if (!numeroLimpio.isEmpty()) {
				int numero = Integer.parseInt(numeroLimpio);
				if (numero > maxIdUsuarios) {
					maxIdUsuarios = numero;
				}
			}
		}
		idUsuarios = maxIdUsuarios + 1;
	}

	public void refrescarConteosContratos() {
		for (Cliente cli : misClientes) {
			cli.setCantContratosActivos(0);
		}

		for (Contrato cto : misContratos) {
			if (cto.isActivo() == true) {
				Cliente dueño = cto.getCliente();
				dueño.setCantContratosActivos(dueño.getCantContratosActivos() + 1);
			}
		}
	}
}