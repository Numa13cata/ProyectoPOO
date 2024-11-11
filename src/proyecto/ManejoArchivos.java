package proyecto;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManejoArchivos {
    private Empresa empresa;


    public static void Serializar(String nombreArchivo, Empresa e) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(nombreArchivo);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(e);
        objectOutputStream.close();
    }

    public static Empresa Deserializar(String nombreArchivo) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(nombreArchivo);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Empresa e= (Empresa) objectInputStream.readObject();
        objectInputStream.close();
        return e;
    }
    public static ArrayList<Cliente> cargaTexto (String nombreArchivo) throws IOException {
        Cuenta cuenta=null;
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        String archi = nombreArchivo;  // Archivo de entrada definido correctamente
        FileReader archivo = new FileReader(archi);
        BufferedReader inStream = new BufferedReader(archivo);
        String linea;
        while ((linea = inStream.readLine()) != null)
        {
            if (linea.charAt(0) == '#')
            {
                if (linea.equals("#CURSO"))
                {
                    linea = inStream.readLine(); //con esto pasa a la siguiente linea
                }
            }
            else
            {
                System.out.println("Linea leida : " + linea);
                String[] atributos = linea.split("\\*");
                String nombre = atributos[0];
                System.out.println("Nombre :" + nombre);
                String identificacion = atributos[1];
                System.out.println("Identificacion : " + identificacion);
                String direccion = atributos[2];
                System.out.println("Direccion : " + direccion);
                Cliente c = new Cliente(nombre, identificacion, direccion,cuenta); //se pone como atributo cuenta null mientras la persona añade la opcion de agregar cuenta
                clientes.add(c);
            }

        }
        for(Cliente c: clientes){
            System.out.println(c);
        }
        return clientes;
    }

    public static int encontrarIndicador(String pais)throws IOException {
        String linea;

        try (InputStreamReader input = new InputStreamReader(new FileInputStream("indicativos.txt"))) {
            BufferedReader fa = new BufferedReader(input);
            while ((linea = fa.readLine()) != null) { //leer hasta que se acabe el archivo
                linea = fa.readLine();
                String[] atributos = linea.split(";"); //tokeniza por el delimitador ";" que es el que esta en el archivo
                if (atributos[0].equals(pais)) {
                    String l = atributos[1]; //aqui debe estar el indicativo, ya que en el archivo primero va el pais y luego va el indicativo
                    int indicativo = Integer.parseInt(l); //hacemos el casteo de String a entero, ya que el atributo de llamadaInternacional lo pusimos como int
                    return indicativo;
                } else {
                    throw new IOException("Problema con el archivo");
                }
            }
        }
        return -1; //no me dejaba retornar null porque de al metodo le puse que retornara un int
    }

    public void generarReporte(Cuenta cuenta,int  anio,int  mes,Cliente cliente) throws IOException{
        /*
            Aparecer todos los datos de la cuenta postpago del cliente
            Debe aparecer todas las llamadas, ordenadas por fecha en el mes seleccionado
            si es llamadaInternacional, mostrar telefono destinatario, indicativo y el telefono

            -total de duracion de las llamadas y valor de las llamadas

        */
        try(FileWriter salida = new FileWriter("ReporteFacturacionPostPagoFinDeMes.txt")){ //flujo de escritura
            ArrayList<Llamada> llamadasDelMes = new ArrayList<>(); //esto es como una lista temporal  para guardar las llamadas que se hicieron en el mes que se ingreso por parametro

                for(Llamada l: cuenta.getLlamadas()){ //accedemos a la lista de llamadas de cuenta
                    if(l.getFecha().getYear()==anio && l.getFecha().getMonthValue()==mes){ //se verifica que la fecha de la llamada sea igual al mes y año que se ingreso por parametro
                        llamadasDelMes.add(l); //se agrega la llamada a la lista auxiliar
                    }

                Utils.ordenarLlamadasPorFecha(llamadasDelMes); //ordenamos la lista de llamadas por fecha que esta en la clase utils, se usa el metodo de ordenamiento de burbuja
                //Escribir en el archivo
                salida.write("Cuenta Postpago: " + cuenta.toString()); //escribe el toString de la cuenta, toda la info de la cuenta postpago
                //colocar en el archivo la duracion de las llamadas y el valor de las llamadas
                int totalDuracion = 0;
                for(Llamada lm: llamadasDelMes){
                    totalDuracion += lm.getDuracion();

                }
                salida.write("\nTotal Duracion de Llamadas: " + totalDuracion + " minutos");
                salida.write("\nTotal Valor de Llamadas: $" + cuenta.ObtenerPagoCuenta()+"\n");
            }
            salida.close();
        }

    }

}


