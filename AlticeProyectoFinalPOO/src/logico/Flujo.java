package logico;

import java.net.*;
import java.io.*;

public class Flujo extends Thread {
	Socket nsfd;
	ObjectInputStream FlujoLectura;
	ObjectOutputStream FlujoEscritura;

	public Flujo(Socket sfd) 
	{
		nsfd = sfd;
		try {
			FlujoEscritura = new ObjectOutputStream(new BufferedOutputStream(sfd.getOutputStream()));
			FlujoEscritura.flush();
			FlujoLectura = new ObjectInputStream(new BufferedInputStream(sfd.getInputStream()));
		} catch (IOException ioe) {
			System.out.println("IOException(Flujo): " + ioe);
		}
	}

	public void run() 
	{
		Servidor.usuarios.add(this);
		System.out.println(nsfd.getInetAddress() + " > se ha conectado");

		while (true) {
			try {
				EmpresaAltice datos = (EmpresaAltice) FlujoLectura.readObject();
				System.out.println("Datos recibidos de: " + nsfd.getInetAddress());

				String fechaDelRespaldo = java.time.LocalDate.now().toString();
				String nombreDelRespaldo = "EmpresaAltice_respaldo_" + fechaDelRespaldo + ".txt";
				
				datos.GuardarDatos(
					datos.getMisClientes(),
					datos.getMisEmpleados(),
					datos.getMisPlanes(),
					datos.getMisServicios(),
					datos.getMisUsuarios(),
					datos.getMisContratos(),
					datos.getPagos(),
					nombreDelRespaldo 
				);
				
				System.out.println("Respaldo guardado correctamente como: " + nombreDelRespaldo);
				
				broadcast(datos);

			} catch (IOException | ClassNotFoundException e) {
				Servidor.usuarios.removeElement(this);
				System.out.println(nsfd.getInetAddress() + " > se ha desconectado");
				break;
			}
		}
	}

	public void broadcast(EmpresaAltice datos) 
	{
		synchronized (Servidor.usuarios) {
			for (int i = 0; i < Servidor.usuarios.size(); i++) {
				Flujo f = (Flujo) Servidor.usuarios.get(i);
				
				if (f != this) {
					try {
						synchronized (f.FlujoEscritura) {
							f.FlujoEscritura.writeObject(datos);
							f.FlujoEscritura.flush();
						}
					} catch (IOException ioe) {
						System.out.println("Error enviando a cliente: " + ioe);
					}
				}
			}
		}
	}
}