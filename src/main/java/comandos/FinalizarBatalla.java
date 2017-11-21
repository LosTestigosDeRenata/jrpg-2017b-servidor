package comandos;

import java.io.IOException;

import estados.Estado;
import mensajeria.PaqueteFinalizarBatalla;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase FinalizarBatalla Se evalua las condiciones tras finalizar un
 * enfrentamiento
 */
public class FinalizarBatalla extends ComandosServer {

    @Override
    public void ejecutar() {

	PaqueteFinalizarBatalla paqueteFinalizarBatalla = gson.fromJson(cadenaLeida,
		PaqueteFinalizarBatalla.class);
	escuchaCliente.setPaqueteFinalizarBatalla(paqueteFinalizarBatalla);
	Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteFinalizarBatalla().getId())
		.setEstado(Estado.getEstadoJuego());

	if (escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo() > 0) // Batalló
									    // contra
									    // otro
									    // personaje
	{
	    Servidor.getConector().actualizarInventario(paqueteFinalizarBatalla.getGanadorBatalla());
	    Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo())
		    .setEstado(Estado.getEstadoJuego());

	    for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		if (conectado.getIdPersonaje() == escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo()) {
		    try {
			conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteFinalizarBatalla()));
		    } catch (IOException e) {
			// TODO Auto-generated catch block
			Servidor.log.append("Falló al intentar enviar finalizarBatalla a:"
				+ conectado.getPaquetePersonaje().getId() + "\n");
		    }
		}
	    }
	} else // Batalló contra un npc
	{
	    int idNpc = escuchaCliente.getPaqueteFinalizarBatalla().getIdEnemigo() * -1;

	    // Si el personaje ganó, le doy un item y saco al infeliz del
	    // server.
	    if (paqueteFinalizarBatalla.getGanadorBatalla() == paqueteFinalizarBatalla.getId()) {
		Servidor.getConector().actualizarInventario(paqueteFinalizarBatalla.getGanadorBatalla());
		Servidor.getPaqueteDeNpcs().getPaquetesNpcs().remove(idNpc);
		Servidor.getPaqueteDeNpcs().getUbicacionNpcs().remove(idNpc);
	    }

	    for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		try {
		    conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteFinalizarBatalla()));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    Servidor.log.append("Falló al intentar enviar finalizarBatalla a:"
			    + conectado.getPaquetePersonaje().getId() + "\n");
		}
	    }
	}

	synchronized (Servidor.atencionConexiones) {
	    Servidor.atencionConexiones.notify();
	}

    }

}
