package proyecto;

import java.time.LocalDate;
import java.util.ArrayList;

public interface IEmpresa {
    public static final long VALOR_MINUTO = 2;
    /**
     * metodo abstracto para establecer los clientes de la empresa
     */
    public ArrayList getClientes();
    public void LlenarListaClientes(String direccion,String identificacion, String nombre);
    public void LlenarListaClientesArchivo(ArrayList<Cliente> clientesArchivo);
    public Long AgregarCuentaPostpago(String nombre,Long numero) throws CuentaPostpagoException;
    public Long AgregarCuentaPrepago (String nombre, Long numero);
    public Llamada AgregarLlamadaNacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos);
    public Llamada AgregarLlamadaInternacional (long idCuenta, LocalDate fecha, long telefonoDestinatario, long duracionMinutos, String paisDestino, int indicativo);
    public Cuenta buscarCuenta (long idCuenta);
    public String concatenar(int indicativo,long telefonoDestinatario);
    public void AgregarUnaRecarga(long id, LocalDate fecha, long valor);
    public void reporteFacturacionPostpago(int anio, int mes, String clienteIdentificacion);
}

