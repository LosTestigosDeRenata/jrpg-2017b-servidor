package comandos;

import java.io.IOException;

import estados.Estado;
import mensajeria.PaqueteBatalla;
import servidor.EscuchaCliente;
import servidor.Servidor;

public class Batalla extends ComandosServer {

    @Override
    public void ejecutar() {
	// Le reenvio al id del personaje batallado que quieren pelear
	escuchaCliente.setPaqueteBatalla(gson.fromJson(cadenaLeida, PaqueteBatalla.class));

	Servidor.log.append(escuchaCliente.getPaqueteBatalla().getId() + " quiere batallar con "
		+ escuchaCliente.getPaqueteBatalla().getIdEnemigo() + System.lineSeparator());

	if (escuchaCliente.getPaqueteBatalla().getIdEnemigo() > 0) // Batalló
								   // contra
								   // otro
								   // personaje
	{
	    try {
		// seteo estado de batalla
		Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteBatalla().getId())
			.setEstado(Estado.getEstadoBatalla());
		Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteBatalla().getIdEnemigo())
			.setEstado(Estado.getEstadoBatalla());
		escuchaCliente.getPaqueteBatalla().setMiTurno(true);
		escuchaCliente.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteBatalla()));

		for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		    if (conectado.getIdPersonaje() == escuchaCliente.getPaqueteBatalla().getIdEnemigo()) {
			int aux = escuchaCliente.getPaqueteBatalla().getId();
			escuchaCliente.getPaqueteBatalla().setId(escuchaCliente.getPaqueteBatalla().getIdEnemigo());
			escuchaCliente.getPaqueteBatalla().setIdEnemigo(aux);
			escuchaCliente.getPaqueteBatalla().setMiTurno(false);
			conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteBatalla()));
			break;
		    }
		}
	    } catch (IOException e) {
		Servidor.log.append("Falló al intentar enviar Batalla \n");
	    }
	} else // Batalló contra un NPC
	{
	    try {
		// seteo estado de batalla
		Servidor.getPersonajesConectados().get(escuchaCliente.getPaqueteBatalla().getId())
			.setEstado(Estado.getEstadoBatallaNpc());
		Servidor.getPaqueteDeNpcs().getPaquetesNpcs()
			.get(escuchaCliente.getPaqueteBatalla().getIdEnemigo() * -1).setEstado(Estado.getEstadoBatallaNpc());

		for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		    conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteBatalla()));
		}
	    } catch (IOException e) {
		Servidor.log.append("Falló al intentar enviar Batalla \n");
	    }
	}

	synchronized (Servidor.atencionConexiones) {
	    Servidor.atencionConexiones.notify();
	}

    }

}
