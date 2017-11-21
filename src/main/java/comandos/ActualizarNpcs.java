package comandos;

import java.io.IOException;

import mensajeria.PaqueteDeNpcs;
import servidor.EscuchaCliente;
import servidor.Servidor;


/**
 * Clase ActualizarPersonaje  Se actualiza al personaje 
 */
public class ActualizarNpcs extends ComandosServer {
    @Override
    public void ejecutar() {
	PaqueteDeNpcs paqueteActualizarNpcs = gson.fromJson(cadenaLeida, PaqueteDeNpcs.class);
	escuchaCliente.setPaqueteDeNpcs(paqueteActualizarNpcs);

	Servidor.setPaqueteDeNpcs(paqueteActualizarNpcs);

	for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
	    try {
		conectado.getSalida().writeObject(gson.toJson(escuchaCliente.getPaqueteDeNpcs()));
	    } catch (IOException e) {
		Servidor.log.append("Falló al intentar actualizar npcs  \n");
	    }
	}
    }

}
