package proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmpresa {
    public static final long VALOR_MINUTO = 2;
    /**
     * metodo abstracto para establecer los clientes de la empresa
     */
    public ArrayList<Cuenta> getCuentas();
    public void setCuentas(ArrayList<Cuenta> cuentas);
    public void setClientes(ArrayList<Cliente> clientes);
    public void setNombre(String nombre);
    public ArrayList<Cliente> getClientes();
    public Cuenta getCuentaDeCliente(Cliente cliente);
    public ArrayList<Recarga> ordenarRecargasPorFecha(ArrayList<Recarga> recargas);
    public ArrayList<Cliente> ClientesOrdenados(ArrayList<Cliente> clientes);
    public void LlenarListaClientes(String direccion, String identificacion, String nombre);
    public void LlenarListaClientesArchivo(ArrayList<Cliente> clientesArchivo);
    public long AgregarCuentaPostpago(String nombre, long numero);
    public Llamada AgregarLlamadaNacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos);
    public Llamada AgregarLlamadaInternacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos, String paisDestino, int indicativo);
    public Cuenta buscarCuenta(long idCuenta);
    public Cliente buscarCliente(long idCliente);
    public long AgregarCuentaPrepago(String nombre, long numero);

}

