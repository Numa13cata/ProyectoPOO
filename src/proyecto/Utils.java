package proyecto;

import java.io.IOException;
import java.util.ArrayList;
public class Utils {
    public static long CONSECUTIVO=1;
    //Se hace la clase utils que es la que nos permite darle un identificador consecutivo  a la cuenta, estamos haciendo este atributo estatico.
    /*ATRIBUTO ESTATICO: este nos permite acceder a la variable sin necesidad de instanciarla, ademas el valor de consecutivo se va actualizando,
     cuando la pongamos en el constructor de cuenta, va ir guardando el valor anterior de la anterior instancia, asi cuando vaya a crear otra cuenta
     no se repetira el valor y sera consecutivo al incrementarala. Por ejemplo si no utilizaramos esta clase ni este atributo estatico y utilizaramos
     una variable se reiniciaria el valor cada vez que instanciaramos una cuenta.
     */
    public static void ordenarLlamadasPorFecha(ArrayList<Llamada> llamadas){
        for(int i=0;i<llamadas.size();i++){
            for(int j=0;j<llamadas.size()-1;j++){
                if(llamadas.get(j).getFecha().isAfter(llamadas.get(j+1).getFecha())){
                    Llamada aux=llamadas.get(j);
                    llamadas.set(j,llamadas.get(j+1));
                    llamadas.set(j+1,aux);
                }
            }
        }
    }
}
