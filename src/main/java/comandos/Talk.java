package comandos;

import java.io.IOException;
import java.util.Map;

import mensajeria.Comando;
import mensajeria.PaqueteMensaje;
import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

public class Talk extends ComandosServer {

    @Override
    public void ejecutar() {
	int idUser = 0;
	int contador = 0;
	PaqueteMensaje paqueteMensaje = (gson.fromJson(cadenaLeida, PaqueteMensaje.class));

	if (!(paqueteMensaje.getUserReceptor() == null)) {
	    if (Servidor.mensajeAUsuario(paqueteMensaje)) {

		paqueteMensaje.setComando(Comando.TALK);

		for (Map.Entry<Integer, PaquetePersonaje> personaje : Servidor.getPersonajesConectados().entrySet()) {
		    if (personaje.getValue().getNombre().equals(paqueteMensaje.getUserReceptor())) {
			idUser = personaje.getValue().getId();
		    }
		}

		for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		    if (conectado.getIdPersonaje() == idUser) {
			try {
			    conectado.getSalida().writeObject(gson.toJson(paqueteMensaje));
			} catch (IOException e) {
			    Servidor.log.append("Falló al intentar enviar mensaje a:"
				    + conectado.getPaquetePersonaje().getId() + "\n");
			}
		    }
		}

	    } else {
		Servidor.log.append("No se envió el mensaje \n");
	    }
	} else {
	    if (cheat(paqueteMensaje)) {
		try {
		    escuchaCliente.getSalida().writeObject(gson.toJson(paqueteMensaje));
		} catch (IOException e) {
		    Servidor.log.append("Falló al intentar enviar mensaje a:"
			    + escuchaCliente.getPaquetePersonaje().getId() + "\n");
		}
	    } else {
		for (Map.Entry<Integer, PaquetePersonaje> personaje : Servidor.getPersonajesConectados().entrySet()) {
		    if (personaje.getValue().getNombre().equals(paqueteMensaje.getUserEmisor())) {
			idUser = personaje.getValue().getId();
		    }
		}
		for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		    if (conectado.getIdPersonaje() != idUser) {
			try {
			    conectado.getSalida().writeObject(gson.toJson(paqueteMensaje));
			} catch (IOException e) {
			    Servidor.log.append("Falló al intentar enviar mensaje a:"
				    + conectado.getPaquetePersonaje().getId() + "\n");
			}
		    }
		}
		Servidor.mensajeAAll(contador);
	    }
	}
    }

    private boolean cheat(PaqueteMensaje paqueteMensaje) {
	switch (paqueteMensaje.getMensaje()) {
	case "noclip":
	    paqueteMensaje.setComando(Comando.GODMODON);
	    return true;
	}
	return false;
    }
}
