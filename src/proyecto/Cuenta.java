package proyecto;

import java.util.ArrayList;

public abstract class Cuenta {
    private long id;
    private long numero; //numero de telefono
    protected ArrayList<Llamada> llamadas;

    public Cuenta(long numero) {
        this.id=Utils.CONSECUTIVO; //se accede al atributo estatico de la clase Utils
        Utils.CONSECUTIVO++; //se incrementa el atributo estatico de la clase Utils
        //se puede hacer esto porque el atributo es estatico y publico, no hace falta instanciarla, ni tener un método para acceder a ella
        this.numero = numero;
        this.llamadas = new ArrayList<>();

    }


    /**
     * metodo para retornar lista de llamadas
     *
     * @return llamadas lista de llamadas
     */
    public ArrayList<Llamada> getLlamadas() {
        return llamadas;
    }

    /**
     * metodo para establecer la lista de llamadas
     *
     * @param llamadas lista de llamadas
     */
    public void setLlamadas(ArrayList<Llamada> llamadas) {
        this.llamadas = llamadas;
    }

    /**
     * metodo para retornar el id de la cuenta
     *
     * @return id de la cuenta
     */
    public long getId() {
        return id;
    }

    /**
     * metodo para establecer el id de la cuenta
     *
     * @param id de la cuenta
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * metodo para retornar el numero de la cuenta
     *
     * @return numero de la cuenta
     */
    public long getNumero() {
        return numero;
    }

    /**
     * metodo para establecer el numero de la cuenta
     *
     * @param numero de la cuenta
     */
    public void setNumero(long numero) {
        this.numero = numero;
    }

    /**
     * metodo abstracto para retornar el saldo de la cuenta
     */
    public abstract long ObtenerPagoCuenta(); //este metodo si o si se implementa en las clases hijas, es decir Prepago y Postpago

    @Override
    public String toString() {
        return "Cuenta{" +
                "id=" + id +
                ", numero=" + numero +
                ", llamadas=" + llamadas +
                '}';
    }
}
