package proyecto;

public class CuentaPostpago extends Cuenta {
    private long cargoFijo;

    public CuentaPostpago(long numero) {
        super(numero); // constructor de Cuenta
        this.cargoFijo = 20000; //en el enunciado dice que el valor inicial es de 20 000
    }

    /**
     * metodo para retornar el cargo fijo de la cuenta postpago
     *
     * @return cargoFijo valor del cargo fijo
     */
    public long getCargoFijo() {
        return cargoFijo;
    }

    /**
     * metodo para establecer el cargo fijo de la cuenta postpago
     *
     * @param cargoFijo valor del cargo fijo
     */
    public void setCargoFijo(long cargoFijo) {
        this.cargoFijo = cargoFijo;
    }

    @Override
    public String toString() {
        return "CuentaPostpago{" + super.toString() + cargoFijo +
                '}';
    }

    @Override
    public long ObtenerPagoCuenta()throws LlamadaInternacionalException{// el cargo fijo, más las llamadas internacionales que se hayan realizado.
        long cont=0;
        long resultado;
        for(Llamada l:llamadas){
        if(l!=null){
            if(l instanceof LlamadaInternacional) {
                LlamadaInternacional li = (LlamadaInternacional) l;
                cont = cont + li.getValor();
            }else{
                throw new LlamadaInternacionalException("No se encontro llamadas internacionales");
            }
        }
        }
        resultado=cont+cargoFijo;
            return resultado;



}

}

