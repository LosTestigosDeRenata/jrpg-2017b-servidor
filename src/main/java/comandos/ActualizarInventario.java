package comandos;

import java.io.IOException;

import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase Actualizar Inventario
 */
public class ActualizarInventario extends ComandosServer {

    @Override
    public void ejecutar() {
	escuchaCliente.setPaquetePersonaje(gson.fromJson(cadenaLeida, PaquetePersonaje.class));

	Servidor.getConector().actualizarInventario(escuchaCliente.getPaquetePersonaje());
	Servidor.getPersonajesConectados().remove(escuchaCliente.getPaquetePersonaje().getId());
	Servidor.getPersonajesConectados().put(escuchaCliente.getPaquetePersonaje().getId(),
		escuchaCliente.getPaquetePersonaje());

	for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
	    try {
		conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaquetePersonaje()));
	    } catch (IOException e) {
		Servidor.log.append("Falló al intentar enviar paquetePersonaje a:"
			+ conectado.getPaquetePersonaje().getId() + "\n");
	    }
	}

    }

}
