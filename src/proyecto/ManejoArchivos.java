package Proyecto;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class ManejoArchivos {

    private Empresa empresa;

    public static void Serializar(String nombreArchivo, String rutaArchivo, Empresa e)
            throws EmpresaException, ArchivoException, FileNotFoundException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(rutaArchivo + nombreArchivo);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(e);
            System.out.println("Datos serializados exitosamente en " + nombreArchivo);

        } catch (EmpresaException ex) {
            System.out.println("Error al serializar los datos ya que Empresa no implementa Serializable");
        } catch (ArchivoException | FileNotFoundException exc) {
            System.out.println("El archivo no existe o no se puede crear");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Empresa Deserializar(String nombreArchivo)
            throws EmpresaException, ArchivoException, FileNotFoundException {
        Empresa empresaCargada = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(nombreArchivo);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            empresaCargada = (Empresa) objectInputStream.readObject();
            objectInputStream.close();

            System.out.println("Datos deserializados exitosamente desde " + nombreArchivo);
        } catch (ArchivoException exc) {
            System.out.println("Archivo no encontrado: " + nombreArchivo, exc);

        } catch (IOException e) {
            System.out.println("Error de deserialización");

        } catch (EmpresaException exc) {
            System.out.println("Error: la clase Empresa no se encontró", exc);

        }

        return empresaCargada;
    }

    public static ArrayList<Cliente> cargaTexto(String nombreArchivo) throws IOException {

        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        String archi = nombreArchivo; // Archivo de entrada definido correctamente
        FileReader archivo = new FileReader(archi);
        BufferedReader inStream = new BufferedReader(archivo);
        String linea;
        while ((linea = inStream.readLine()) != null) {
            if (linea.charAt(0) == '#') {
                if (linea.equals("#CURSO")) {
                    linea = inStream.readLine(); // con esto pasa a la siguiente linea
                }
            } else {
                String[] atributos = linea.split("\\*");
                String nombre = atributos[0];
                String identificacion = atributos[1];
                String direccion = atributos[2];
                Cliente c = new Cliente(nombre, identificacion, direccion);
                clientes.add(c);
            }

        }
        return clientes;
    }

    public static int encontrarIndicador(String pais) throws IOException {
        String linea;

        try (InputStreamReader input = new InputStreamReader(new FileInputStream("indicativos.txt"))) {
            BufferedReader fa = new BufferedReader(input);
            while ((linea = fa.readLine()) != null) { // leer hasta que se acabe el archivo
                linea = fa.readLine();
                String[] atributos = linea.split(";"); // tokeniza por el delimitador ";" que es el que esta en el
                                                       // archivo
                if (atributos[0].equals(pais)) {
                    String l = atributos[1]; // aqui debe estar el indicativo, ya que en el archivo primero va el pais y
                                             // luego va el indicativo
                    int indicativo = Integer.parseInt(l); // hacemos el casteo de String a entero, ya que el atributo de
                                                          // llamadaInternacional lo pusimos como int
                    return indicativo;
                } else {
                    throw new IOException("Problema con el archivo");
                }
            }
        }
        return -1; // no me dejaba retornar null porque de al metodo le puse que retornara un int
    }

    public String generarReporteRecargas(int anio, int mes) {
        ArrayList<Cliente> clientes = empresa.getClientes();
        String reporte = "";

        try (FileWriter writer = new FileWriter("ReporteRecargas.txt")) {
            for (Cliente cliente : empresa.ClientesOrdenados(clientes)) {
                Cuenta cuenta = empresa.getCuentaDeCliente(cliente);
                if (cuenta instanceof CuentaPrepago) {
                    CuentaPrepago cuentaPrepago = (CuentaPrepago) cuenta;

                    writer.write("Nombre del Cliente: " + cliente.getNombre() + "\n");
                    writer.write("Tipo de identificación del Cliente: " + cliente.getTipoId() + "\n");
                    writer.write("Numero de identificación del cliente: " + cliente.getIdentificacion() + "\n");
                    writer.write("Direccion del Cliente: " + cliente.getDireccion() + "\n");
                    writer.write("  Cuenta ID: " + cuentaPrepago.getId() + "\n");

                    reporte.concat("Nombre del Cliente: " + cliente.getNombre());
                    reporte.concat("Tipo de identificación del Cliente: " + cliente.getTipoId());
                    reporte.concat("Numero de identificación del cliente: " + cliente.getIdentificacion());
                    reporte.concat("Direccion del Cliente: " + cliente.getDireccion());
                    reporte.concat("  Cuenta ID: " + cuentaPrepago.getId());

                    ArrayList<Llamada> llamadasDelMes = empresa.ordenarLlamadasPorFecha(cuentaPrepago.getLlamadas());

                    writer.write("____________________________________'\n\n");
                    reporte.concat("____________________________________'\n");

                    long totalDuracionl = 0;
                    long totalValorl = 0;
                    for (Llamada llamada : empresa.ordenarLlamadasPorFecha(cuentaPrepago.getLlamadas())) {
                        if (llamada.getFecha().getYear() == anio && llamada.getFecha().getMonthValue() == mes) {
                            llamadasDelMes.add(llamada);
                        }
                    }

                    writer.write("  Llamadas del mes: \n");
                    reporte.concat("  Llamadas del mes: \n");

                    for (Llamada llamada : llamadasDelMes) {
                        writer.write("    " + llamada + "\n");
                        reporte.concat("    " + llamada + '\n');
                    }
                    for (Llamada llamada : cuentaPrepago.getLlamadas()) {
                        totalDuracionl += llamada.getDuracion();
                        totalValorl += llamada.calcularValor();
                    }

                    writer.write("Total Duración de Llamadas: " + totalDuracionl + " minutos");
                    writer.write("Total Valor de Llamadas: $" + totalValorl);
                    reporte.concat("Total Duración de Llamadas: " + totalDuracionl + " minutos\n");
                    reporte.concat("Total Valor de Llamadas: $" + totalValorl + "\n");

                    writer.write("____________________________________'\n\n");
                    reporte.concat("____________________________________'\n\n");

                    ArrayList<Recarga> recargasDelMes = empresa.ordenarRecargasPorFecha(cuentaPrepago.getRecargas());
                    for (Recarga recarga : empresa.ordenarRecargasPorFecha(cuentaPrepago.getRecargas())) {
                        if (recarga.getFecha().getYear() == anio && recarga.getFecha().getMonthValue() == mes) {
                            recargasDelMes.add(recarga);
                        }
                    }

                    writer.write("  Recargas del mes:\n");
                    reporte.concat("  Recargas del mes:\n");
                    for (Recarga recarga : recargasDelMes) {
                        System.out.println("    " + recarga + "\n");
                        writer.write("    " + recarga + "\n");
                    }
                    long totalValorr = 0;
                    for (Recarga recarga : cuentaPrepago.getRecargas()) {
                        totalValorl += recarga.getValor();
                    }
                    writer.write("____________________________________'\n\n");
                    reporte.concat("____________________________________'\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reporte;
    }

    public String generarReporteCuentaPostpagoEnArchivo(CuentaPostpago cuenta, int anio, int mes)
            throws ArchivoException {
        String salida = "";
        String archi = "ReportePostpago.txt";
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(archi));
            writer.println("Cuenta Postpago: " + cuenta.toString());
            salida.concat("Cuenta Postpago: " + cuenta.toString());

            long totalDuracion = 0;
            long totalValor = 0;
            for (Llamada llamada : cuenta.getLlamadas()) {
                totalDuracion += llamada.getDuracion();
                totalValor += llamada.calcularValor();
            }

            writer.println("Total Duración de Llamadas: " + totalDuracion + " minutos");
            writer.println("Total Valor de Llamadas: $" + totalValor);

            ArrayList<Llamada> llamadasOrdenadas = empresa.ordenarLlamadasPorFecha(cuenta.getLlamadas());

            writer.println("\nLlamadas del mes ordenadas por fecha:");

            for (Llamada llamada : llamadasOrdenadas) {
                if (llamada instanceof LlamadaInternacional) {
                    LlamadaInternacional llamadaInt = (LlamadaInternacional) llamada;
                    try {
                        int indicativo = ManejoArchivos.encontrarIndicador(llamadaInt.getPaisDestino());
                        writer.println("  Llamada Internacional: Fecha: " + llamada.getFecha().format(formatter)
                                + ", Destino: (" + indicativo + ") " + llamada.getTelefonoDestinatario()
                                + ", Duración: " + llamada.getDuracion() + " minutos, Valor: $"
                                + llamada.calcularValor());
                    } catch (IOException e) {
                        writer.println(
                                "  Error al encontrar el indicativo para el país: " + llamadaInt.getPaisDestino());
                    }
                } else {
                    writer.println("  Llamada Nacional: Fecha: " + llamada.getFecha().format(formatter)
                            + ", Destino: " + llamada.getTelefonoDestinatario()
                            + ", Duración: " + llamada.getDuracion() + " minutos, Valor: $" + llamada.calcularValor());
                }
            }
        } catch (ArchivoException | IOException e) {
            System.out.println(e.getMessage());
        }
        return salida;
    }

}
