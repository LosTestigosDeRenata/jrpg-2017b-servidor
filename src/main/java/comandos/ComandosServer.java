package comandos;

import mensajeria.Comando;
import servidor.EscuchaCliente;

/**
 * Clase ComandosServer de esté heredan el resto de comandos
 */
public abstract class ComandosServer extends Comando {
    protected EscuchaCliente escuchaCliente;

    /**
     * @param escuchaCliente Escucha la perición de los clientes
     */
    public void setEscuchaCliente(final EscuchaCliente escuchaCliente) {
	this.escuchaCliente = escuchaCliente;
    }

}
