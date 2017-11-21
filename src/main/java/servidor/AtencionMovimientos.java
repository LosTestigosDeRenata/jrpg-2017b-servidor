package servidor;

import com.google.gson.Gson;

import estados.Estado;
import mensajeria.Comando;
import mensajeria.PaqueteDeMovimientos;

/**
 * clase AtencionMovimientos Su función es la de actualizar en caso de
 * producirse algun movimiento del personaje deltro del mapa
 */
public class AtencionMovimientos extends Thread {

	private final Gson gson = new Gson();
	public AtencionMovimientos() {
	}

	@Override
	public void run() {
		synchronized (this) {
			try {
				while (true) {
					wait();
					for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
						if (conectado.getPaquetePersonaje().getEstado() == Estado.getEstadoJuego()) {
							PaqueteDeMovimientos pdp = (PaqueteDeMovimientos) new PaqueteDeMovimientos(
									Servidor.getUbicacionPersonajes()).clone();
							pdp.setComando(Comando.MOVIMIENTO);
							synchronized (conectado) {
								conectado.getSalida().writeObject(gson.toJson(pdp));
							}
						}
					}
				}
			} catch (Exception e) {
				Servidor.log.append("Falló al intentar enviar paqueteDeMovimientos \n");
			}
		}
	}
}