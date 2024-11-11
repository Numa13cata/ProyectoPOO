package proyecto;

import java.time.LocalDate;

public abstract class Llamada {
    protected long duracion;
    protected LocalDate fecha;
    protected long telefonoDestinatario;
    protected long valor;

    public Llamada(long duracion, LocalDate fecha, long telefonoDestinatario) {
        this.duracion = duracion;
        this.fecha = fecha;
        this.telefonoDestinatario = telefonoDestinatario;
    }

    /**
     * metodo para retornar el valor de la llamada
     *
     * @return valor valor de la llamada
     */
    public long getValor() {
        return valor;
    }

    /**
     * metodo para establecer el valor de la llamada
     *
     * @param valor valor de la llamada
     */
    public void setValor(long valor) {
        this.valor = valor;
    }

    /**
     * metodo para retornar el telefono del destinatario de la llamada
     *
     * @return telefonoDestinatario telefono del destinatario de la llamada
     */
    public long getTelefonoDestinatario() {
        return telefonoDestinatario;
    }

    /**
     * metodo para establecer el telefono del destinatario de la llamada
     *
     * @param telefonoDestinatario telefono del destinatario de la llamada
     */
    public void setTelefonoDestinatario(long telefonoDestinatario) {
        this.telefonoDestinatario = telefonoDestinatario;
    }

    /**
     * metodo para retornar la fecha de la llamada
     *
     * @return fecha fecha de la llamada
     */
    public LocalDate getFecha() {
        return fecha;
    }

    /**
     * metodo para establecer la fecha de la llamada
     *
     * @param fecha fecha de la llamada
     */
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    /**
     * metodo para retornar la duracion de la llamada
     *
     * @return duracion duracion de la llamada
     */
    public long getDuracion() {
        return duracion;
    }

    /**
     * metodo para establecer la duarcion de la llamada
     *
     * @param duracion duracion de la llamada
     */
    public void setDuracion(long duracion) {
        this.duracion = duracion;
    }

    /**
     * metodo abstracto para calcular el valor de una llamada
     */
    public abstract long calcularValor();

    @Override
    public String toString() {
        return "Llamada{" +
                "duracion=" + duracion +
                ", fecha=" + fecha +
                ", telefonoDestinatario=" + telefonoDestinatario +
                ", valor=" + valor +
                '}';
    }
}
