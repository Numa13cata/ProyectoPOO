package proyecto;

import java.time.LocalDate;

public class LlamadaInternacional extends Llamada {
    private String paisDestino;
    int indicativo;
    public LlamadaInternacional(long duracion, LocalDate fecha, long telefonoDestinatario, String paisDestino, int indicativo) {
        super(duracion, fecha, telefonoDestinatario);
        this.paisDestino = paisDestino;
        this.valor = calcularValor(); //asi se calcula el valor automaticamente cuando instanciemos una llamada
        this.indicativo = indicativo;

    }


    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
    }


    @Override
    public long calcularValor() {
        long valorLlamada = this.duracion * IEmpresa.VALOR_MINUTO;
        return (long) (valorLlamada * 1.20); //
    }

    @Override //aqui traemos el toString de la clase padre con el super.toString()
    public String toString() {
        return "LlamadaInternacional{" + super.toString() + "paisDestino=" + paisDestino + "indicativo= ("+indicativo+")}"; //se puso el indicativo entre parentesis

    }
}
