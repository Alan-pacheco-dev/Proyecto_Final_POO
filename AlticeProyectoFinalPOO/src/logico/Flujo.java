package logico;
import java.net.*;
import java.io.*;
import java.util.*;

public class Flujo extends Thread {
    Socket nsfd;
    ObjectInputStream FlujoLectura;
    ObjectOutputStream FlujoEscritura;

    public Flujo(Socket sfd) 
    {
        nsfd = sfd;
        try {
            // ObjectOutputStream primero, luego ObjectInputStream
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
        System.out.println(nsfd.getInetAddress() + "> se ha conectado");

        while (true) {
            try {
                // Recibe el objeto EmpresaAltice
                EmpresaAltice datos = (EmpresaAltice) FlujoLectura.readObject();
                System.out.println("Datos recibidos de: " + nsfd.getInetAddress());

                // Guarda el respaldo en el servidor
                datos.GuardarDatos(
                    datos.getMisClientes(),
                    datos.getMisEmpleados(),
                    datos.getMisPlanes(),
                    datos.getMisServicios(),
                    datos.getMisUsuarios(),
                    datos.getMisContratos(),
                    datos.getPagos()
                );
                System.out.println("Respaldo guardado correctamente");

                
                broadcast(datos);

            } catch (IOException | ClassNotFoundException e) {
                Servidor.usuarios.removeElement(this);
                System.out.println(nsfd.getInetAddress() + "> se ha desconectado");
                break;
            }
        }
    }

    public void broadcast(EmpresaAltice datos) 
    {
        synchronized (Servidor.usuarios) {
            Enumeration e = Servidor.usuarios.elements();
            while (e.hasMoreElements()) {
                Flujo f = (Flujo) e.nextElement();
                
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