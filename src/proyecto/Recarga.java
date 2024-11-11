package proyecto;

import java.time.LocalDate;

public class Recarga {
    private LocalDate fecha; // LocalDate es como un tipo de variable
    private long valor;

    public Recarga(long valor, LocalDate fecha) {
        this.valor = valor;
        this.fecha = fecha;
    }

    /**
     * metodo para retornar la fecha de la recarga
     *
     * @return fecha fecha de la recarga
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * metodo para establecer la fecha de la recarga
     *
     * @param fecha fecha de la recarga
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * metodo para retornar el valor de la recarga
     *
     * @return valor valor de la recarga
     */
    public long getValor() {
        return valor;
    }

    /**
     * metodo para establecer el valor de la recarga
     *
     * @param valor valor de la recarga
     */
    public void setValor(long valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Recarga" +
                "[fecha=" + fecha +
                ", valor=" + valor +
                "]";
    }
}
