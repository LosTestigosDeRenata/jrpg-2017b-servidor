package comandos;

import java.io.IOException;

import mensajeria.Paquete;
import servidor.Servidor;

/**
 * Clase Salir Produce el cierre de todo; incluyendo clientes y su desconexión
 */
public class Salir extends ComandosServer {

    @SuppressWarnings("unlikely-arg-type")
    @Override
    public void ejecutar() {
	// Cierro todo
	try {
	    escuchaCliente.getEntrada().close();
	    escuchaCliente.getSalida().close();
	    escuchaCliente.getSocket().close();
	} catch (IOException e) {
	    Servidor.log.append("Falló al intentar salir \n");

	}
	// Lo elimino de los clientes conectados
	Servidor.getClientesConectados().remove(this);
	Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
	// Indico que se desconecto
	Servidor.log.append(paquete.getIp() + " se ha desconectado." + System.lineSeparator());
    }

}
