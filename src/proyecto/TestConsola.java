package proyecto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.System.exit;

public class TestConsola {
    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Cual es el nombre de tu empresa");
        String nombreEmpresa = entrada.nextLine();
        Empresa empresa = new Empresa(nombreEmpresa);
        ManejoArchivos reportes = new ManejoArchivos();
        String workingDirectory = System.getProperty("user.dir");
        System.out.println("Working Directory: " + workingDirectory);
        int op;
        do {
            System.out.println("Bienvenido al menu");
            System.out.println("1. Ingresar clientes");
            System.out.println("2. Agregar nueva cuenta de prepago o postpago");
            System.out.println("3. Agregar una llamada nacional o internacional");
            System.out.println("4. Agregar una recarga");
            System.out.println("5. Reporte de facturación postpago a fin de mes");
            System.out.println("6. Reporte de recargas a fin de mes");
            System.out.println("7.Guardar la empresa en un archivo como un objeto");
            System.out.println("8.Cargar de un archivo el objeto empresa");
            System.out.println("9. Salir");
            op = entrada.nextInt();
            entrada.nextLine();

            switch (op) {
                case 1:
                    int op2;
                    System.out.println("Ingrese una opcion");
                    System.out.println("1. Seleccionar archivo de clientes");
                    System.out.println("2.Ingresar los datos manualmente ");
                    System.out.println("3. Regresar al menú de servicios");
                    op2 = entrada.nextInt();
                    entrada.nextLine();
                    switch (op2) {
                        case 1:
                            ArrayList<Cliente> clientes = new ArrayList<>();
                            System.out.println("Ingrese el nombre el nombre del archivo");
                            String nombreArchivo = entrada.nextLine();
                            clientes = ManejoArchivos.cargaTexto("Clientes.txt");
                            System.out.println("Se cargaron los clientes correctamente");
                            empresa.LlenarListaClientesArchivo(clientes);
                            System.out.println(empresa.toString());
                            break;
                        case 2:
                            String opcion;
                            System.out.println("Ingrese los datos del cliente");
                            System.out.println("Ingrese direccion");
                            String direccion = entrada.nextLine();
                            System.out.println("Ingrese identificacion del cliente");
                            String identificacion = entrada.nextLine();
                            System.out.println("Ingrese el nombre del cliente");
                            String nombre = entrada.nextLine();
                            do {
                                try {
                                    empresa.LlenarListaClientes(direccion, identificacion, nombre);
                                } catch (EmpresaException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("Quieres añadir otro cliente, Si/No");
                                opcion = entrada.nextLine();
                            } while (!opcion.equalsIgnoreCase("No"));
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opcion invalida");
                            break;
                    }
                    break;
                case 2:
                    String nomCuenta;
                    String nombre;
                    long numero;
                    System.out.println("Ingrese si la cuenta que quiere crear es Postpago o Prepago");
                    nomCuenta = entrada.nextLine();
                    System.out.println("Ingrese el nombre del cliente");
                    nombre = entrada.nextLine();
                    System.out.println("Ingrese el numero de telefono asociado a la cuenta");
                    numero = entrada.nextLong();
                    entrada.nextLine();
                    if (nomCuenta.equalsIgnoreCase("Postpago")) {
                        try {
                            long id = empresa.AgregarCuentaPostpago(nombre, numero);
                            System.out.println("La cuenta postpago se creo con el id: " + id);
                            System.out.println(empresa.toString());
                        } catch (CuentaPostpagoException e) {
                            System.out.println(e.getMessage());
                        }
                    } else if (nomCuenta.equalsIgnoreCase("Prepago")) {
                        try {
                            long id = empresa.AgregarCuentaPrepago(nombre, numero);
                            System.out.println("La cuenta prepago se creo con el id: " + id);
                        } catch (CuentaPrepagoException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Opcion invalida...");
                    }
                    break;
                case 3:
                    long id;
                    System.out.println("Ingrese el id de la cuenta");
                    id = entrada.nextLong();
                    entrada.nextLine();
                    System.out.println("Ingrese si fue una llamada internacional o nacional");
                    nombre = entrada.nextLine();
                    System.out.println("Fecha de la llamada:");
                    System.out.println("Ingrese el anio");
                    int year = entrada.nextInt();
                    System.out.println("Ingrese el mes");
                    int month = entrada.nextInt();
                    System.out.println("Ingrese el dia");
                    int day = entrada.nextInt();
                    LocalDate fecha = LocalDate.of(year, month, day);
                    entrada.nextLine();
                    System.out.println("Indique el telefono destinatario");
                    long telefonoDestinatario = entrada.nextLong();
                    System.out.println("Ingrese la duracion en minutos");
                    long duracionMinutos = entrada.nextLong();
                    entrada.nextLine();
                    int indicativo;
                    if (nombre.equalsIgnoreCase("Internacional")) {
                        System.out.println("Ingrese el pais de destino");
                        String paisDestino = entrada.nextLine();
                        indicativo = ManejoArchivos.encontrarIndicador(paisDestino);
                        Llamada llamadaRetorno = null;
                        try {
                            llamadaRetorno = empresa.AgregarLlamadaInternacional(id, fecha, telefonoDestinatario, duracionMinutos, paisDestino, indicativo);
                        } catch (EmpresaException e) {
                            System.out.println(e.getMessage());
                        }
                        String telefonoCompleto = empresa.concatenar(indicativo, telefonoDestinatario);
                        System.out.println("Se adiciono correctamente la llamada Internacional para el numero: " + telefonoCompleto + " con un costo de " + llamadaRetorno.getValor());
                    } else if (nombre.equalsIgnoreCase("Nacional")) {
                        try {
                            Llamada llamadaRetorno = empresa.AgregarLlamadaNacional(id, fecha, telefonoDestinatario, duracionMinutos);
                            System.out.println("Se creo la llamada nacional con costo: " + llamadaRetorno.valor);
                        } catch (EmpresaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Opcion invalida...");
                    }
                    break;
                case 4:
                    long valor_recarga;
                    long id_cuenta;
                    LocalDate fecha_recarga;
                    int ms;
                    int an;
                    int d;
                    System.out.println("Ingrese el id de la cuenta");
                    id_cuenta = entrada.nextLong();
                    System.out.println("Ingrese el año");
                    an = entrada.nextInt();
                    System.out.println("Ingrese el mes");
                    ms = entrada.nextInt();
                    System.out.println("Ingrese el dia");
                    d = entrada.nextInt();
                    fecha_recarga = LocalDate.of(an, ms, d);
                    System.out.println("Ingrese el valor de la recarga");
                    valor_recarga = entrada.nextLong();
                    try {
                        empresa.AgregarUnaRecarga(id_cuenta, fecha_recarga, valor_recarga);
                    } catch (RecargaException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Se hizo la recarga con exito ");
                    break;
                case 5:
                    int a, m;
                    String clienteIdentificacion;
                    try {
                        System.out.println("Ingrese el año");
                        a = entrada.nextInt();
                        System.out.println("Ingrese el mes (en numero)");
                        m = entrada.nextInt();
                        entrada.nextLine();
                        System.out.println("Ingrese el la identificacion del cliente");
                        clienteIdentificacion = entrada.nextLine();
                        Cliente c = empresa.buscarCliente(Long.parseLong(clienteIdentificacion));
                        Cuenta cuenta = c.getCuenta();
                        if (cuenta instanceof CuentaPostpago) {
                            CuentaPostpago cuentaPost = (CuentaPostpago) cuenta;
                            System.out.println(reportes.generarReporteCuentaPostpagoEnArchivo(cuentaPost, a, m));
                        }
                    } catch (ArchivoException exc) {
                        System.out.println("El reporte no pudo ser procesado en el archivo");
                    } catch (CuentaPostpagoException e) {
                        System.out.println("El cliente no tiene cuentas postpago");
                    }
                    break;
                case 6:
                    int anio2, mes2;
                    System.out.println("Vamos a ingresar una fecha para el Reporte de Cuentas Prepago:");
                    System.out.println("(Este reporte incluirá unicamente a clientes con cuentas prepago)");
                    System.out.println("Indique el año");
                    anio2 = entrada.nextInt();
                    System.out.println("Indique el mes");
                    mes2 = entrada.nextInt();
                    System.out.println("Reporte de Facturación Clientes con Cuenta Prepago:");
                    reportes.generarReporteRecargas(anio2, mes2);
                    break;
                case 7:
                    String nomarchi = " ";
                    String ruta = " ";
                    try {
                        System.out.println("A continuación se guardará la empresa en un archivo\n");
                        System.out.println("Ingresa un nombre para el archivo\n");
                        nomarchi = entrada.nextLine();
                        System.out.println("Ingresa la localizacion del archivo\n");
                        ruta = entrada.nextLine();
                        ManejoArchivos.Serializar(nomarchi, ruta, empresa);
                        System.out.println("La empresa se ha guardado correctamente!");
                    } catch (EmpresaException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 8:
                    Empresa empresacargada = null;
                    String rutaArchivo = "empresa.dat";
                    try {
                        empresa = reportes.Deserializar(rutaArchivo);
                        System.out.println("sistema cargado exitosamente!");
                    } catch (ArchivoException e) {
                        System.out.println("error al cargar el archivo: " + e.getMessage());
                    } catch (EmpresaException e) {
                        System.out.println("error de estructura en la clase Empresa: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("ocurrió un error inesperado al cargar los datos: " + e.getMessage());
                    }
                    break;
                case 9:
                    exit(1);
                default:
                    break;
            }
        } while (op != 9);
    }
}