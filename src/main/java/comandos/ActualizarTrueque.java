package comandos;

import java.io.IOException;

import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase ActualizarTrueque
 */
public class ActualizarTrueque extends ComandosServer {

    @Override
    public void ejecutar() {
	escuchaCliente.setPaquetePersonaje(gson.fromJson(cadenaLeida, PaquetePersonaje.class));

	Servidor.getConector().actualizarInventario(escuchaCliente.getPaquetePersonaje());
	Servidor.getConector().actualizarPersonaje(escuchaCliente.getPaquetePersonaje());

	Servidor.getPersonajesConectados().remove(escuchaCliente.getPaquetePersonaje().getId());
	Servidor.getPersonajesConectados().put(escuchaCliente.getPaquetePersonaje().getId(),
		escuchaCliente.getPaquetePersonaje());

	for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
	    try {
		conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaquetePersonaje()));
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		Servidor.log.append("Falló al intentar enviar actualizacion de trueque a:"
			+ conectado.getPaquetePersonaje().getId() + "\n");
	    }
	}

    }

}
