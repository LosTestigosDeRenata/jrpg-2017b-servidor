package comandos;

import java.io.IOException;

import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase ActualizarPersonajeLvl
 */
public class ActualizarPersonajeLvl extends ComandosServer {

    @Override
    public void ejecutar() {
	escuchaCliente.setPaquetePersonaje(gson.fromJson(cadenaLeida, PaquetePersonaje.class));

	Servidor.getConector().actualizarPersonajeSubioNivel(escuchaCliente.getPaquetePersonaje());

	Servidor.getPersonajesConectados().remove(escuchaCliente.getPaquetePersonaje().getId());
	Servidor.getPersonajesConectados().put(escuchaCliente.getPaquetePersonaje().getId(),
		escuchaCliente.getPaquetePersonaje());
	escuchaCliente.getPaquetePersonaje().ponerBonus();
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
