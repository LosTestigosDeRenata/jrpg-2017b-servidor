package comandos;

import mensajeria.PaqueteMovimiento;
import servidor.Servidor;


/**
 * Clase Movimiento Produce que los jugadores de muevan a través del mapa con
 * fluidez
 */
public class Movimiento extends ComandosServer {

    @Override
    public void ejecutar() {
	escuchaCliente.setPaqueteMovimiento(
		(gson.fromJson(cadenaLeida, PaqueteMovimiento.class)));

	Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
		.setPosX(escuchaCliente.getPaqueteMovimiento().getPosX());
	Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
		.setPosY(escuchaCliente.getPaqueteMovimiento().getPosY());
	Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
		.setDireccion(escuchaCliente.getPaqueteMovimiento().getDireccion());
	Servidor.getUbicacionPersonajes().get(escuchaCliente.getPaqueteMovimiento().getIdPersonaje())
		.setFrame(escuchaCliente.getPaqueteMovimiento().getFrame());

	synchronized (Servidor.atencionMovimientos) {
	    Servidor.atencionMovimientos.notify();
	}

    }

}
