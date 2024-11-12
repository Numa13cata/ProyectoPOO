package proyecto;

import java.io.Serializable;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class Empresa implements IEmpresa, Serializable {
    protected ArrayList<Cliente> clientes;
    private String nombre; // nombre de la empresa
    protected ArrayList<Cuenta> cuentas;

    public Empresa() {
        this.clientes = new ArrayList<Cliente>();
        this.cuentas = new ArrayList<Cuenta>();
    }

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.clientes = new ArrayList<Cliente>();
        this.cuentas = new ArrayList<Cuenta>();
    }

    /**
     * metodo para retornar las cuentas de la empresa
     *
     * @return cuentas lista de cuentas de la empresa
     */
    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    /**
     * metodo para establecer las cuentas de la empresa
     *
     * @param cuentas la lista de cuentas de la empresa
     */
    public void setCuentas(ArrayList<Cuenta> cuentas) {
        this.cuentas = cuentas;
    }

    /**
     * metodo para establecer los clientes de la empresa
     *
     * @param clientes la lista de clientes de la empresa
     */
    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    /**
     * metodo para retornar el nombre de la empresa
     *
     * @return nombre nombre de la empresa
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * metodo para establecer el nombre de la empresa
     *
     * @param nombre nombre de la empresa
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * metodo para establecer los clientes de la empresa
     *
     * @return clientes Retorna la lista de clientes de la empresa
     */
    public ArrayList<Cliente> getClientes() {

        return clientes;
    }

    /**
     * Metodo de tipo cuenta que nos sirve para obtener la cuenta de un cliente
     *
     * @param cliente  Cliente de tipo cliente
     * @return cliente.getCuenta() Retorna la cuenta del cliente
     *
     */
    public Cuenta getCuentaDeCliente(Cliente cliente) {
        return cliente.getCuenta();
    }
    @Override
    public String toString() {
        return "Empresa{" +
                "clientes=" + clientes +
                ", nombre='" + nombre + '\'' +
                ", cuentas=" + cuentas +
                '}';
    }

    /**
     * Metodo de tipo ArrayList de Recarga que va a ordenar una lista de recargas
     *
     * @param recargas  La lista de recarga desordenada
     * @return recargas La lista de recargas ordenada
     *
     */

    public ArrayList<Recarga> ordenarRecargasPorFecha(ArrayList<Recarga> recargas) {
        recargas.sort(Comparator.comparing(Recarga::getFecha));
        return recargas;
    }
    /**
     * metodo para establecer los clientes de la empresa
     */



    public ArrayList<Cliente> ClientesOrdenados(ArrayList<Cliente> clientes) {
        clientes.sort(Comparator.comparing(Cliente::getIdentificacion));
        return clientes;
    }


    //metodo para llenar lista de clientes ingresando los datos por consola
    public void LlenarListaClientes(String direccion,String identificacion, String nombre) throws EmpresaException{
        Cuenta cuenta=null;
        Cliente cliente=new Cliente(nombre,identificacion,direccion,cuenta); //ya cree un cliente

        // Verificacion
        for (Cliente c : this.getClientes()){
            if (c != null){
                if (c.getIdentificacion().equals(identificacion)){ //para no ingresar un cliente con identificacion repetida
                    throw new EmpresaException("No es posible crear, el id " + identificacion + " ya estra reigstrado");
                }
            }
        }

        this.clientes.add(cliente); //lo añado a la lista llamada clientes
        System.out.println("Cliente agregado" + cliente);
        System.out.println(this.toString());
    }

    public void LlenarListaClientesArchivo(ArrayList<Cliente> clientesArchivo){
        for(Cliente c:clientesArchivo){
            if(c!=null){
                this.clientes.add(c); //se añade a la lista de clientes que podemos acceder desde esta clase
            }
        }
    }

    public long AgregarCuentaPostpago(String nombre,long numero) throws CuentaPostpagoException{
        //vamos a buscar en la lista de clientes, el cliente que tenga el nombre del parametro
        Cuenta cuenta = null;
        System.out.println("Nombre que entra: " + nombre);
        for(Cliente c: this.clientes){
            if(c!=null){
                if(c.getNombre().equalsIgnoreCase(nombre)){ //verifica si el cliente tiene el mismo nombre
                    System.out.println(c.getNombre());
                    CuentaPostpago cuentaPostpago =new CuentaPostpago(numero); //se crea la cuenta postpago con el numero que queria el usuario
                    c.setCuenta(cuentaPostpago); //se le agrega al cliente el atributo de la cuenta
                    cuentas.add(cuentaPostpago);

                    return cuentaPostpago.getId(); //se retorna el id para acceder a el en el main y poder mostrarlo
                }

            }else{
                throw new CuentaPostpagoException("No existe una cuenta con el nombre " + nombre);
            }
        }
        return -1;

    }

    public Llamada AgregarLlamadaNacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos){
        Cuenta cuentaBuscar;
        cuentaBuscar=buscarCuenta(idCuenta);
        if (cuentaBuscar== null){
            throw new EmpresaException("No existe una cuenta con el nombre "+nombre);
        }

        if (cuentaBuscar instanceof CuentaPrepago){
            LlamadaNacional llamadaNacional = new LlamadaNacional(duracionMinutos, fecha, telefonoDestinatario);
            CuentaPrepago cuentaPrepago = (CuentaPrepago) cuentaBuscar;

            long sumatoriaRecargas = 0;
            // Verificacion con recargas que haya saldo suficiente
            for (Recarga r : cuentaPrepago.getRecargas()){
                sumatoriaRecargas += r.getValor();
            }

            long saldo = sumatoriaRecargas;
            if (saldo < llamadaNacional.getValor()){
                throw new EmpresaException("No tiene saldo para hacer la llamada");
            }

            if (cuentaPrepago.getNumeroMinutos() < duracionMinutos){
                throw new EmpresaException("No puedes realizar la llamada, minutos insuficientes");
            }

            // Agregar la llamada a la lista de llamadas de la cuenta
            cuentaPrepago.llamadas.add(llamadaNacional);
            return llamadaNacional;
        }
        else{
            // Es intancia de Postpago
            LlamadaNacional llamadaNacional = new LlamadaNacional(duracionMinutos, fecha, telefonoDestinatario);
            CuentaPostpago cuentaPostpago = (CuentaPostpago) cuentaBuscar;

            // Regla de negocio A
            llamadaNacional.valor = 0;
            cuentaPostpago.llamadas.add(llamadaNacional);
            return llamadaNacional;
        }
    }

    public Llamada AgregarLlamadaInternacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos, String paisDestino, int indicativo){
        Cuenta cuentaBuscar;
        cuentaBuscar=buscarCuenta(idCuenta); //se le asigna el valor de la cuenta encontrada
        if (cuentaBuscar== null){
            throw new EmpresaException("No existe una cuenta con el nombre "+nombre);
        }
        //se revisa si es prepago o postpago por las reglas de negocio
        if (cuentaBuscar instanceof CuentaPrepago){
            LlamadaInternacional llamadaInternacional = new LlamadaInternacional(duracionMinutos, fecha, telefonoDestinatario, paisDestino, indicativo);
            //se crea la llamada internacional con los atributos que se ingresaron por parametro
            CuentaPrepago cuentaPrepago = (CuentaPrepago) cuentaBuscar; //se hace el casteo

            long sumatoriaRecargas = 0;
            // Verificacion con recargas que haya saldo suficiente
            for (Recarga r : cuentaPrepago.getRecargas()){ //recorre la lista de recargas que tenga la cuenta prepago que encontramos
                sumatoriaRecargas += r.getValor(); //se va acumulando el valor en el contador para saber cuanto valor en total tiene disponible
            }

            long saldo = sumatoriaRecargas;
            if (saldo < llamadaInternacional.getValor()){ //no hay valor suficiente
                throw new EmpresaException("No tiene saldo para hacer la llamada");
            }
            //otra verificacion para saber si hay minutos suficientes para hacer la llamada en la cuenta prepago que habiamos encontrado
            if (cuentaPrepago.getNumeroMinutos() < duracionMinutos){
                throw new EmpresaException("No puedes realizar la llamada, minutos insuficientes");
            }

            // Agregar la llamada a la lista de llamadas de la cuenta
            cuentaPrepago.llamadas.add(llamadaInternacional);
            return llamadaInternacional;
        }
        else{
            // Es intancia de Postpago
            LlamadaInternacional llamadaInternacional = new LlamadaInternacional(duracionMinutos, fecha, telefonoDestinatario, paisDestino,indicativo);
            CuentaPostpago cuentaPostpago = (CuentaPostpago) cuentaBuscar;
            cuentaPostpago.llamadas.add(llamadaInternacional); //se le adiciona a la lista de llamadas de postpago la nueva llamada internacional
            return llamadaInternacional;
        }
    }

    public Cuenta buscarCuenta (long idCuenta){ //busca en la lista de cuentas segun el id de la cuenta
        for (Cuenta c : this.cuentas){
            if (c!= null){
                if (c.getId() == idCuenta){
                    return c; //si la encuentra retorna la cuenta
                }
            }
        }
        return null;
    }

    public Cliente buscarCliente(long idCliente) {
        for (Cliente c : this.clientes) {
            if (c != null) {
                long num = Long.parseLong(c.getIdentificacion());
                if (num == idCliente) {
                    return c;
                }
            }
        }
        return null;
    }


    public long AgregarCuentaPrepago (String nombre, long numero)throws CuentaPrepagoException {
        for (Cliente c : clientes){

                System.out.println(this.toString());
                if (c.getNombre().equalsIgnoreCase(nombre)){
                    CuentaPrepago cuentaPrepago= new CuentaPrepago(numero);
                    c.setCuenta(cuentaPrepago); //no olvidar añadir el atributo de Cliente ya que estaba null
                    cuentas.add(cuentaPrepago); //se añade a la lista de cuentas
                    return cuentaPrepago.getId();
                }

        }
        return -1;

    }

    public String concatenar(int indicativo,long telefonoDestinatario){
        String i=Integer.toString(indicativo);
        String t=Long.toString(telefonoDestinatario);
        String resultado=i + "-" + t;
        return resultado;
    }
    public void AgregarUnaRecarga(long id, LocalDate fecha, long valor){ //es el id de la cuenta y la fecha de la recarga
        Cuenta cuentaBuscar;
        cuentaBuscar=buscarCuenta(id); //encuentra la cuenta
        if(cuentaBuscar != null){
            if(cuentaBuscar instanceof CuentaPrepago){
                Recarga recarga=new Recarga(valor,fecha); //se crea la nueva recarga con los valores que quiere el usuario
                ((CuentaPrepago) cuentaBuscar).recargas.add(recarga);
                //se adiciona la recarga a la lista de recargas de la cuenta

            }
        }else{
            throw new RecargaException("No se pudo crear la recarga, no existe la cuenta indicada");
        }

    }

 /*public void crearCuenta(String nombre, long telefono, String nomCuenta){
     if (nomCuenta.equalsIgnoreCase("Postpago")) {
         CuentaPostpago cuenta = new CuentaPostpago(telefono);
     }else if(nomCuenta.equalsIgnoreCase("Prepago")){
         CuentaPrepago cuenta = new CuentaPrepago(telefono);

     }
        for(Cliente c:clientes){
            if(c.getNombre().equalsIgnoreCase(nombre)){

                c.setCuenta(cuenta);
                cuentas.add(cuenta);
               break;
            }
        }

 }
*/





}