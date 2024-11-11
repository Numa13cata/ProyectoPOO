
package proyecto;
public class ArchivoClientesException extends Exception{  //esta excepcion se hizo por si en la opcion 1 del menu, la persona ingresa "Seleccionar archivo de clientes" y no se encuentra el archivo.
        public String mensaje;

        public ArchivoClientesException(String mensaje) {
            this.mensaje = mensaje;
        }
        @Override
        public String toString() {
            return "ArchivoException{" +"mensaje='" + mensaje + '\'' +'}';
        }

    }


