package logico;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        // ============================================================
        // SECCION 1: Obtener instancia Singleton de EmpresaAltice
        // ============================================================
        EmpresaAltice altice = EmpresaAltice.getInstance();
        System.out.println("=== Sistema Altice Iniciado ===\n");

        // ============================================================
        // SECCION 2: Crear empleados (Comercial, Administrativo, Trabajador)
        // ============================================================
        Empleado empComercial     = new Empleado(null, "Maria Perez",   35000f, 5f, 0f, "Comercial");
        Empleado empAdministrativo = new Empleado(null, "Carlos Diaz",  45000f, 0f, 0f, "Administrativo");
        Empleado empTrabajador     = new Empleado(null, "Luis Martinez", 28000f, 0f, 0f, "Trabajador");

        altice.getMisEmpleados().add(empComercial);
        altice.getMisEmpleados().add(empAdministrativo);
        altice.getMisEmpleados().add(empTrabajador);

        System.out.println("--- Empleados registrados ---");
        for (Empleado e : altice.getMisEmpleados()) {
            System.out.println("  ID: " + e.getIdPersona()
                + " | Codigo: " + e.getCodigoEmpleado()
                + " | Nombre: " + e.getNombre()
                + " | Rol: "    + e.getRolEmpleado()
                + " | Salario: RD$" + e.getSalario());
        }

        // ============================================================
        // SECCION 3: Asignar usuarios a empleados
        // ============================================================
        Usuario usuComercial      = new Usuario("Comercial",      "mperez",   "pass123");
        Usuario usuAdministrativo = new Usuario("Administrativo", "cdiaz",    "pass456");
        Usuario usuTrabajador     = new Usuario("Trabajador",     "lmartinez","pass789");

        empComercial.setMiUsuario(usuComercial);
        empAdministrativo.setMiUsuario(usuAdministrativo);
        empTrabajador.setMiUsuario(usuTrabajador);

        altice.getMisUsuarios().add(usuComercial);
        altice.getMisUsuarios().add(usuAdministrativo);
        altice.getMisUsuarios().add(usuTrabajador);

        System.out.println("\n--- Usuarios asignados ---");
        for (Empleado e : altice.getMisEmpleados()) {
            System.out.println("  Empleado: " + e.getNombre()
                + " -> Usuario: " + e.getMiUsuario().getNombreUsuario()
                + " | Rol: "      + e.getMiUsuario().getRolEmpleado());
        }

        // ============================================================
        // SECCION 4: Crear servicios disponibles
        // ============================================================
        Servicio svcInternet  = new Servicio("Internet 100 Mbps", 1200f);
        Servicio svcMovil     = new Servicio("Movil 10 GB",        800f);
        Servicio svcTelefonia = new Servicio("Telefonia Fija",     500f);
        Servicio svcCable     = new Servicio("Cable TV",           900f);

        svcInternet.setActivo(true);
        svcMovil.setActivo(true);
        svcTelefonia.setActivo(true);
        svcCable.setActivo(true);

        altice.getMisServicios().add(svcInternet);
        altice.getMisServicios().add(svcMovil);
        altice.getMisServicios().add(svcTelefonia);
        altice.getMisServicios().add(svcCable);

        System.out.println("\n--- Servicios disponibles ---");
        for (Servicio s : altice.getMisServicios()) {
            System.out.println("  " + s.getIdServicio()
                + " | " + s.getTipoServicio()
                + " | RD$" + s.getPrecioServicio()
                + " | Activo: " + s.isActivo());
        }

        // ============================================================
        // SECCION 5: Crear planes y agregar servicios
        // ============================================================
        Plan planBasico   = new Plan("Plan Basico",   "Mensual", 0f);
        Plan planCompleto = new Plan("Plan Completo", "Mensual", 0f);

        // Plan Basico: Internet + Telefonia
        planBasico.getServiciosPlan().add(svcInternet);
        planBasico.getServiciosPlan().add(svcTelefonia);
        planBasico.setPrecioTotal(planBasico.calcularPrecioTotal());

        // Plan Completo: Internet + Movil + Telefonia + Cable
        planCompleto.getServiciosPlan().add(svcInternet);
        planCompleto.getServiciosPlan().add(svcMovil);
        planCompleto.getServiciosPlan().add(svcTelefonia);
        planCompleto.getServiciosPlan().add(svcCable);
        planCompleto.setPrecioTotal(planCompleto.calcularPrecioTotal());

        altice.getMisPlanes().add(planBasico);
        altice.getMisPlanes().add(planCompleto);

        System.out.println("\n--- Planes disponibles ---");
        for (Plan p : altice.getMisPlanes()) {
            System.out.println("  " + p.getIdPlan()
                + " | " + p.getNombrePlan()
                + " | Precio mensual: RD$" + p.getPrecioTotal()
                + " | Servicios incluidos: " + p.getServiciosPlan().size());
        }

        // ============================================================
        // SECCION 6: Registrar clientes (solo empleado Comercial puede hacerlo)
        // ============================================================

        // Cliente persona fisica
        Cliente cliFisico = new Cliente(null, "Pedro Ramirez",
                "Calle Las Flores #12, Santiago",
                "pedro.ramirez@gmail.com",
                0f, false, "001-1234567-8");

        // Cliente empresa
        Cliente cliEmpresa = new Cliente(null, "TechSolutions SRL",
                "Av. 27 de Febrero #45, Santo Domingo",
                "contacto@techsolutions.com",
                0f, true, "1-30-12345-6");

        boolean regFisico  = altice.registrarCliente(empComercial, cliFisico);
        boolean regEmpresa = altice.registrarCliente(empComercial, cliEmpresa);

        System.out.println("\n--- Registro de clientes ---");
        System.out.println("  Cliente persona fisica registrado: " + regFisico);
        System.out.println("  Cliente empresa registrado: "        + regEmpresa);

        System.out.println("\n--- Clientes en el sistema ---");
        for (Cliente c : altice.getMisClientes()) {
            System.out.println("  " + c.getIdPersona()
                + " | Codigo: "  + c.getCodigoCliente()
                + " | Nombre: "  + c.getNombre()
                + " | "          + c.getIdentificadorUnicoCliente()
                + " | Email: "   + c.getEmail()
                + " | Empresa: " + c.isEsEmpresa());
        }

        // Verificar que un NO-Comercial no puede registrar clientes
        System.out.println("\n--- Intento de registro por empleado NO Comercial ---");
        try {
            Cliente cliExtra = new Cliente(null, "Juan Lopez",
                    "Av. Independencia", "jlopez@mail.com", 0f, false, "001-9999999-9");
            altice.registrarCliente(empAdministrativo, cliExtra);
        } catch (RuntimeException ex) {
            System.out.println("  Excepcion esperada: " + ex.getMessage());
        }

        // ============================================================
        // SECCION 7: Crear contratos para los clientes
        // ============================================================
        LocalDate hoy          = LocalDate.now();
        LocalDate fin1Anio     = hoy.plusYears(1);
        LocalDate fin6Meses    = hoy.plusMonths(6);

        Contrato contFisico  = new Contrato(cliFisico,  hoy, fin1Anio,  planBasico);
        Contrato contEmpresa = new Contrato(cliEmpresa, hoy, fin6Meses, planCompleto);

        cliFisico.agregarContrato(contFisico);
        cliEmpresa.agregarContrato(contEmpresa);

        altice.getMisContratos().add(contFisico);
        altice.getMisContratos().add(contEmpresa);

        System.out.println("\n--- Contratos registrados ---");
        for (Contrato ct : altice.getMisContratos()) {
            System.out.println("  " + ct.getIdContrato()
                + " | Cliente: "     + ct.getCliente().getNombre()
                + " | Plan: "        + ct.getPlanContrato().getNombrePlan()
                + " | Inicio: "      + ct.getFechaInicioContrato()
                + " | Fin: "         + ct.getFechaFinContrato()
                + " | Activo: "      + ct.isActivo());
        }

        // ============================================================
        // SECCION 8: Registrar pagos de los clientes
        // ============================================================
        LocalDate vencimiento1 = hoy.plusDays(30);
        LocalDate vencimiento2 = hoy.plusDays(30);

        // Pago del cliente fisico (pago completo)
        float totalPlanBasico   = planBasico.getPrecioTotal();
        Pagos pagoFisico = new Pagos(contFisico,
                hoy, vencimiento1,
                hoy,
                0f,
                totalPlanBasico,
                totalPlanBasico);
        pagoFisico.setPagadoTotal(true);

        // Pago del cliente empresa (pago parcial)
        float totalPlanCompleto = planCompleto.getPrecioTotal();
        Pagos pagoEmpresa = new Pagos(contEmpresa,
                hoy, vencimiento2,
                hoy,
                0f,
                totalPlanCompleto * 0.5f,
                totalPlanCompleto);
        pagoEmpresa.setPagadoTotal(false);

        altice.getPagos().add(pagoFisico);
        altice.getPagos().add(pagoEmpresa);

        System.out.println("\n--- Pagos registrados ---");
        for (Pagos pg : altice.getPagos()) {
            System.out.println("  " + pg.getIdPago()
                + " | Contrato: "     + pg.getContrato().getIdContrato()
                + " | Cliente: "      + pg.getContrato().getCliente().getNombre()
                + " | Pago: RD$"      + pg.getPagoDelCliente()
                + " | Total: RD$"     + pg.getTotalPorPagar()
                + " | Pagado: "       + pg.isPagadoTotal()
                + " | Vence: "        + pg.getFechaVencimientoPago());
        }

        // ============================================================
        // SECCION 9: Consultas - resumen de cliente y contratos activos
        // ============================================================
        System.out.println("\n--- Resumen del cliente persona fisica ---");
        cliFisico.verResumenMiInfo(contFisico);

        System.out.println("\n--- Contratos activos por cliente ---");
        for (Cliente c : altice.getMisClientes()) {
            System.out.println("  " + c.getNombre()
                + " -> Contratos activos: "   + c.getCantContratosActivos()
                + " | Servicios activos: "     + c.getTotalServiciosActivos());
        }

        // ============================================================
        // SECCION 10: Cancelar y reactivar un contrato
        // ============================================================
        System.out.println("\n--- Cancelar contrato del cliente fisico ---");
        boolean cancelado = cliFisico.cancelarContratoByID(contFisico.getIdContrato());
        System.out.println("  Contrato " + contFisico.getIdContrato()
                + " cancelado: " + cancelado
                + " | Activo ahora: " + contFisico.isActivo());

        System.out.println("\n--- Reactivar contrato del cliente fisico ---");
        boolean reactivado = cliFisico.solicitarReactivarContratoById(contFisico.getIdContrato());
        System.out.println("  Contrato " + contFisico.getIdContrato()
                + " reactivado: " + reactivado
                + " | Activo ahora: " + contFisico.isActivo());

        // ============================================================
        // SECCION 11: Reporte por periodo - pagos entre fechas
        // ============================================================
        System.out.println("\n--- Reporte de pagos del periodo actual ---");
        LocalDate inicioMes = hoy.withDayOfMonth(1);
        LocalDate finMes    = hoy.withDayOfMonth(hoy.lengthOfMonth());
        float totalRecaudado = 0f;
        int   pagosPendientes = 0;

        for (Pagos pg : altice.getPagos()) {
            LocalDate fechaPago = pg.getFechaInicioPago();
            if (!fechaPago.isBefore(inicioMes) && !fechaPago.isAfter(finMes)) {
                totalRecaudado += pg.getPagoDelCliente();
                if (!pg.isPagadoTotal()) {
                    pagosPendientes++;
                }
            }
        }
        System.out.println("  Periodo: " + inicioMes + " al " + finMes);
        System.out.println("  Total recaudado: RD$"    + totalRecaudado);
        System.out.println("  Pagos pendientes: "      + pagosPendientes);

        // ============================================================
        // SECCION 12: Guardar datos en archivo
        // ============================================================
        System.out.println("\n--- Guardando datos en archivo ---");
        altice.GuardarDatos(
                altice.getMisClientes(),
                altice.getMisEmpleados(),
                altice.getMisPlanes(),
                altice.getMisServicios(),
                altice.getMisUsuarios(),
                altice.getMisContratos(),
                altice.getPagos()
        );
        System.out.println("  Datos guardados exitosamente.");

        System.out.println("\n=== Pruebas finalizadas ===");
    }

}
