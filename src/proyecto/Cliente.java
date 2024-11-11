package proyecto;

public class Cliente {
    private String direccion;
    private String identificacion; // Atributos de esta clase Cliente
    private String nombre; //nombre del cliente
    private String tipoId="Cedula";
    private Cuenta cuenta;

    public Cliente(String nombre, String direccion, String identificacion, Cuenta cuenta) {// metodo constructor
        this.nombre = nombre;
        this.direccion = direccion;
        this.identificacion = identificacion;
        this.tipoId = "Cedula";
        this.cuenta = cuenta;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion){
        this.identificacion = identificacion;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public String getTipoId(){
        return tipoId;
    }
    public void setTipoId(String tipoId){
        this.tipoId = tipoId;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }

    @Override
    public String toString() {
        return "Cliente{" + "nombre=" + nombre + ", direccion=" + direccion + ", identificacion=" + identificacion + ", tipoId=" + tipoId +",cuenta=" + cuenta + '}';
    }

}
