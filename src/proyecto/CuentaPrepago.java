package proyecto;

import java.util.ArrayList;

public class CuentaPrepago extends Cuenta{
    private long numeroMinutos;
    protected ArrayList<Recarga> recargas;

    public CuentaPrepago(long numero) {
        super(numero); // se llama al constructor de la clase Cuenta
        this.numeroMinutos = 5; //En el enunciado dice que para atraer más clientes se le van a regalar 5 minutos
        this.recargas = new ArrayList<Recarga>();
    }

    /**
     * metodo para retornar lista de recargas
     *
     * @return recargas lista de recargas
     */
    public ArrayList<Recarga> getRecargas() {
        return recargas;
    }

    /**
     * metodo para establecer la lista de recargas
     *
     * @param recargas lista de recargas
     */
    public void setRecargas(ArrayList<Recarga> recargas) {
        this.recargas = recargas;
    }

    /**
     * metodo para retornar el numero de minutos de la cuenta prepago
     *
     * @return numeroMinutos numero de minutos
     */
    public long getNumeroMinutos() {
        return numeroMinutos;
    }

    /**
     * metodo para establecer el numero de minutos de la cuenta prepago
     *
     * @param numeroMinutos numero de minutos
     */
    public void setNumeroMinutos(long numeroMinutos) {
        this.numeroMinutos = numeroMinutos;
    }

    @Override //suma del valor de las recargas
    public long ObtenerPagoCuenta()throws RecargaException { // sobreescribiendo el metodo de obtener pago cuenta de la clase Cuenta
        long cont = 0;
        for(Recarga r : recargas) {
            if(r!=null) {
                cont += r.getValor();
                return cont;
            }else{
                throw new RecargaException("No se encontro recargas en la cuenta");
            }
        }
        return 0;

    }

    @Override
    public String toString() {
        return "Prepago" + super.toString() + "[llamadas=" + llamadas + ", numeroMinutos=" + numeroMinutos + ", recargas="
                + recargas + "]";
    }
}
