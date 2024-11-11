package proyecto;

import java.time.LocalDate;

public class LlamadaNacional extends Llamada {
    public LlamadaNacional(long duracion, LocalDate fecha, long telefonoDestinatario){
        super(duracion,fecha,telefonoDestinatario);
        this.valor = calcularValor();//asi se calcula el valor automaticamente cuando instanciemos una llamada
    }

    @Override //Sobreescribimos el metodo abstracto de la clase padre Llamada
    public long calcularValor() {
        return this.duracion * IEmpresa.VALOR_MINUTO;
    } //segun las reglas de negocio, en este caso quisimos usar una constante para que cada minuto costara una cantidad
    @Override
    public String toString(){
        return "LlamadaNacional{"+super.toString()+"}";
    }
}
