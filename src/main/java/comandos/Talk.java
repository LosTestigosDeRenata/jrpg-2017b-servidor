package comandos;

import java.io.IOException;
import java.util.Map;

import mensajeria.Comando;
import mensajeria.PaqueteMensaje;
import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase Talk Esta clase permite el chat entre usuarios
 */
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
	    if (!cheat(paqueteMensaje)) {
		for (Map.Entry<Integer, PaquetePersonaje> personaje : Servidor.getPersonajesConectados().entrySet()) {
		    if (personaje.getValue().getNombre().equals(paqueteMensaje.getUserEmisor())) {
			idUser = personaje.getValue().getId();
		    }
		}
		for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		    if (conectado.getIdPersonaje() != idUser) {
			contador++;
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

    private boolean cheat(final PaqueteMensaje paqueteMensaje) {

	PaquetePersonaje paquetePersonaje;
	paquetePersonaje = giveMePaquetePersonaje(paqueteMensaje);

	switch (paqueteMensaje.getMensaje()) {
	case "noclip":
	    paquetePersonaje.setComando(Comando.NOWALL);
	    paquetePersonaje.setNoclipActivado(!paquetePersonaje.isNoclipActivado());
	    paqueteMensaje.setUserEmisor("Servidor");

	    if (paquetePersonaje.isNoclipActivado()) {
		paqueteMensaje.setMensaje("A atravesar weas!");
	    } else {
		paqueteMensaje.setMensaje("No lograras pasar!");
	    }
	    try {
		escuchaCliente.getSalida().writeObject(gson.toJson(paqueteMensaje));
		escuchaCliente.getSalida().writeObject(gson.toJson(paquetePersonaje));
	    } catch (IOException e) {
		Servidor.log.append(
			"Falló al intentar enviar mensaje a:" + escuchaCliente.getPaquetePersonaje().getId() + "\n");
	    }
	    // Finaliza el metodo para no reenviar el paquete a todos
	    return true;
	case "bigdaddy":
	    paquetePersonaje.setMultiplicadorFuerzaCheat(paquetePersonaje.getMultiplicadorFuerzaCheat() * 2);
	    break;
	case "tinydaddy":
	    if (paquetePersonaje.getFuerza() * (paquetePersonaje.getMultiplicadorFuerzaCheat() / 2) != 0) {
		paquetePersonaje.setMultiplicadorFuerzaCheat(paquetePersonaje.getMultiplicadorFuerzaCheat() / 2);
	    }
	    break;
	case "iddqd":
	    paquetePersonaje.setInvulnerabilidad(!paquetePersonaje.esInvulnerable());
	    if (paquetePersonaje.esInvulnerable()) {
		paqueteMensaje.setMensaje("Modo rambo on");
	    } else {
		paqueteMensaje.setMensaje("Modo rambo off");
	    }
	    break;
	case "war aint what it used to be":
	    paquetePersonaje.setInvisibilidad(!paquetePersonaje.esInvisible());
	    if (paquetePersonaje.esInvisible()) {
		paqueteMensaje.setMensaje("harry potter");
	    } else {
		paqueteMensaje.setMensaje("voldemort");
	    }
	    break;
	default:
	    return false;
	}

	Servidor.log.append("entré\n");
	paquetePersonaje.setComando(Comando.CHEAT);
	paqueteMensaje.setUserEmisor("Servidor");

	for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
	    try {
		conectado.getSalida().writeObject(gson.toJson(paquetePersonaje));
		if (conectado.getIdPersonaje() == paquetePersonaje.getId()) {
		    conectado.getSalida().writeObject(gson.toJson(paqueteMensaje));
		}
	    } catch (IOException e) {
		Servidor.log.append(
			"Falló al intentar enviar mensaje a:" + escuchaCliente.getPaquetePersonaje().getId() + "\n");
	    }
	}
	return true;
    }

    private PaquetePersonaje giveMePaquetePersonaje(final PaqueteMensaje paqueteMensaje) {
	for (Map.Entry<Integer, PaquetePersonaje> personaje : Servidor.getPersonajesConectados().entrySet()) {
	    if (personaje.getValue().getNombre().equals(paqueteMensaje.getUserEmisor())) {
		return personaje.getValue();
	    }
	}
	return null;
    }
}
