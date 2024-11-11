package proyecto;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class TestConsola {
    public static void main(String[] args) throws IOException {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Cual es el nombre de tu empresa");
        String nombreEmpresa = entrada.nextLine();
        Empresa empresa = new Empresa(nombreEmpresa);
        String opcion;

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
            int op = entrada.nextInt();
            String nombre;
            long id;
            switch (op) {
                case 1: //para la opcion 1
                    System.out.println("Ingrese una opcion");
                    System.out.println("1. Seleccionar archivo de clientes");
                    System.out.println("2.Ingresar los datos manualmente ");
                    System.out.println("3. Regresar al menú de servicios");
                    int op2 = entrada.nextInt();
                    entrada.nextInt();
                    switch (op2) {

                        case 1:
                            System.out.println("Ingrese el nombre el nombre del archivo");
                            nombre = entrada.nextLine();
                            ArrayList<Cliente> clientes = ManejoArchivos.cargaTexto("Proyecto/Clientes.txt");
                            empresa.LlenarListaClientesArchivo(clientes);
                            break;
                        case 2:
                            do {
                                System.out.println("Ingrese los datos del cliente");
                                System.out.println("Ingrese direccion");
                                String direccion = entrada.nextLine();
                                System.out.println("Ingrese identificacion del cliente");
                                String identificacion = entrada.nextLine();
                                System.out.println("Ingrese el nombre del cliente");
                                nombre = entrada.nextLine();
                                try {
                                    empresa.LlenarListaClientes(direccion, identificacion, nombre);
                                } catch (EmpresaException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("Quieres añadir otro cliente, Si/No");
                                opcion = entrada.nextLine();
                            }while(!opcion.equalsIgnoreCase("No"));
                        case 3:
                            return; //se devuelve al menu principal
                        default:
                            System.out.println("Opcion invalida");
                            break;
                    }
                case 2: //case 2 del primer switch
                    entrada.nextLine();
                    System.out.println("Ingrese si la cuenta que quiere crear es Postpago o Prepago");
                    String nomCuenta = entrada.nextLine();
                    System.out.println("Ingrese el nombre del cliente");
                    nombre = entrada.nextLine();
                    System.out.println("Ingrese el numero de telefono asociado a la cuenta");
                    Long numero = entrada.nextLong();
                    entrada.nextLine();
                    if (nomCuenta.equalsIgnoreCase("Postpago")) {
                        try {
                            id = empresa.AgregarCuentaPostpago(nombre, numero); //entra el nombre del cliente al que le queremos agregar la cuenta postpago
                            System.out.println("La cuenta postpago se creo con el id: " + id);
                        } catch (CuentaPostpagoException e) {
                            System.out.println(e.getMessage()); // por si no se pudo crear la cuenta postpago
                        }
                    } else if (nomCuenta.equalsIgnoreCase("Prepago")) { //en cambio si se queria añadir una cuenta prepago
                        try {
                            id = empresa.AgregarCuentaPrepago(nombre, numero);
                            System.out.println("La cuenta prepago se creo con el id: " + id);
                        } catch (CuentaPrepagoException e) {
                            System.out.println(e.getMessage()); //por si no se pudo crear la cuenta prepago
                        }
                    } else {
                        System.out.println("Opcion invalida...");
                    }
                    break;
                case 3: //opcion para registrar una llamada
                    entrada.nextLine();
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
                    LocalDate fecha = LocalDate.of(year, month, day); // se arma la fecha
                    entrada.nextLine();
                    System.out.println("Indique el telefono destinatario");
                    long telefonoDestinatario = entrada.nextLong();
                    System.out.println("Ingrese la duracion en minutos");
                    long duracionMinutos = entrada.nextLong();
                    entrada.nextLine();
                    int indicativo; //indicativo del pais
                    if (nombre.equalsIgnoreCase("Internacional")) {
                        System.out.println("Ingrese el pais de destino"); //como es una llamada internacional tiene un atributo adicional
                        String paisDestino = entrada.nextLine();
                        indicativo=ManejoArchivos.encontrarIndicador(paisDestino); //se llama al metodo de ManejoArchivos para que encuentre el indicativo dependiendo del pais
                        Llamada llamadaRetorno=null;
                        try {

                            llamadaRetorno= empresa.AgregarLlamadaInternacional(id, fecha, telefonoDestinatario, duracionMinutos, paisDestino,indicativo); //el id es para indicar en cual cuenta
                        } catch (EmpresaException e) {

                            System.out.println(e.getMessage());
                        }
                        String telefonoCompleto=empresa.concatenar(indicativo,telefonoDestinatario);

                        System.out.println("Se adiciono correctamente la llamada Internacional para el numero: " + telefonoCompleto+" con un costo de "+llamadaRetorno.getValor());


                    } else if (nombre.equalsIgnoreCase("Nacional")) {
                        try {
                            Llamada llamadaRetorno = empresa.AgregarLlamadaNacional(id, fecha, telefonoDestinatario, duracionMinutos);
                            System.out.println("Se creo la llamada nacional con costo: " + llamadaRetorno.valor); //se quiso mostrar el costo para ver si calcularValor() funciona
                        } catch (EmpresaException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Opcion invalida...");
                    }
                    break;

                case 4: //opcion para Agregar una recarga
                    long valor_recarga;
                    long id_cuenta;
                    LocalDate fecha_recarga;
                    int ms; //mes
                    int an; //año
                    int d; //dia

                    System.out.println("Ingrese el id de la cuenta");
                    id_cuenta = entrada.nextLong();
                    System.out.println("Ingrese el año");
                    an=entrada.nextInt();
                    System.out.println("Ingrese el mes");
                    ms=entrada.nextInt();
                    System.out.println("Ingrese el dia");
                    d=entrada.nextInt();
                    fecha_recarga=LocalDate.of(an,ms,d); //se arma la fecha
                    System.out.println("Ingrese el valor de la recarga");
                    valor_recarga=entrada.nextLong();
                    try{
                        empresa.AgregarUnaRecarga(id_cuenta,fecha_recarga,valor_recarga);
                    } catch (RecargaException e) {
                        System.out.println(e.getMessage());
                    }
                    System.out.println("Se hizo la recarga con exito ");

                    return; //se puso return porque en el enunciado dice que regresar al menu de servicios
                case 5: //generar reporte de cuenta postpago
                    int a,m; //año y mes
                     String clienteIdentificacion;
                    Cuenta cuenta_para_reporte;
                    System.out.println("Ingrese el año");
                    a=entrada.nextInt();
                    System.out.println("Ingrese el mes");
                    m=entrada.nextInt();
                    System.out.println("Ingrese  la identificacion del cliente");
                    clienteIdentificacion=entrada.nextLine();
                    empresa.reporteFacturacionPostpago(a,m,clienteIdentificacion); //aqui se busca al cliente, para encontrar su cuenta postpago y poder hacer el reporte con sus llamadas


                    break;

            }
        } while(entrada.nextInt() != 9);
    }
}
