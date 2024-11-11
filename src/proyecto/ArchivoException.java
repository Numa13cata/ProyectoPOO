package proyecto;

public class ArchivoException extends RuntimeException {
    public ArchivoException(String message) {
        super(message != null && !message.isEmpty() ? message : "Error al abrir archivo o al escribir en el archivo");
    }
}
