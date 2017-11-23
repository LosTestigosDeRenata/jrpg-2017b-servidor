package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.google.gson.Gson;
import comandos.ComandosServer;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteDeNpcs;
import mensajeria.PaqueteDePersonajes;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePelear;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

/**
 * Clase que se encarga de la comunicación entre el servidor y el cliente.
 *
 */
public class EscuchaCliente extends Thread {

    private final Socket socket;
    private final ObjectInputStream entrada;
    private final ObjectOutputStream salida;
    private int idPersonaje;
    private final Gson gson = new Gson();

    private PaquetePersonaje paquetePersonaje;
    private PaqueteMovimiento paqueteMovimiento;
    private PaqueteBatalla paqueteBatalla;
    private PaqueteAtacar paqueteAtacar;
    private PaqueteFinalizarBatalla paqueteFinalizarBatalla;
    private PaqueteUsuario paqueteUsuario;
    private PaqueteDeMovimientos paqueteDeMovimiento;
    private PaqueteDePersonajes paqueteDePersonajes;
    private PaqueteDeNpcs paqueteDeNpcs;
    private PaquetePelear paquetePelear;

    /**
     * @param ip IP del servidor.
     * @param socket Socket del servidor.
     * @param entrada Buffer de entrada.
     * @param salida Buffer de salida.
     * @throws IOException
     */
    public EscuchaCliente(final String ip, final Socket socket, final ObjectInputStream entrada,
	    final ObjectOutputStream salida) throws IOException {
	this.socket = socket;
	this.entrada = entrada;
	this.salida = salida;
	paquetePersonaje = new PaquetePersonaje();
    }

    @Override
    public void run() {
	try {
	    ComandosServer comand;
	    Paquete paquete;
	    paqueteUsuario = new PaqueteUsuario();

	    String cadenaLeida = (String) entrada.readObject();

	    while (!((paquete = gson.fromJson(cadenaLeida, Paquete.class)).getComando() == Comando.DESCONECTAR)) {

		comand = (ComandosServer) paquete.getObjeto(Comando.NOMBREPAQUETE);
		comand.setCadena(cadenaLeida);
		comand.setEscuchaCliente(this);
		comand.ejecutar();
		cadenaLeida = (String) entrada.readObject();
	    }

	    entrada.close();
	    salida.close();
	    socket.close();

	    Servidor.getPersonajesConectados().remove(paquetePersonaje.getId());
	    Servidor.getUbicacionPersonajes().remove(paquetePersonaje.getId());
	    Servidor.getClientesConectados().remove(this);

	    for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
		paqueteDePersonajes = new PaqueteDePersonajes(Servidor.getPersonajesConectados());
		paqueteDePersonajes.setComando(Comando.CONEXION);
		conectado.salida.writeObject(gson.toJson(paqueteDePersonajes, PaqueteDePersonajes.class));
	    }

	    Servidor.log.append(paquete.getIp() + " se ha desconectado." + System.lineSeparator());

	} catch (IOException | ClassNotFoundException e) {
	    Servidor.log.append("Error de conexion: " + e.getMessage() + System.lineSeparator());
	}
    }

    /**
     * @return the idPersonaje
     */
    public int getIdPersonaje() {
	return idPersonaje;
    }

    /**
     * @param idPersonaje the idPersonaje to set
     */
    public void setIdPersonaje(final int idPersonaje) {
	this.idPersonaje = idPersonaje;
    }

    /**
     * @return the paquetePersonaje
     */
    public PaquetePersonaje getPaquetePersonaje() {
	return paquetePersonaje;
    }

    /**
     * @param paquetePersonaje the paquetePersonaje to set
     */
    public void setPaquetePersonaje(final PaquetePersonaje paquetePersonaje) {
	this.paquetePersonaje = paquetePersonaje;
    }

    /**
     * @return the paqueteMovimiento
     */
    public PaqueteMovimiento getPaqueteMovimiento() {
	return paqueteMovimiento;
    }

    /**
     * @param paqueteMovimiento the paqueteMovimiento to set
     */
    public void setPaqueteMovimiento(final PaqueteMovimiento paqueteMovimiento) {
	this.paqueteMovimiento = paqueteMovimiento;
    }

    /**
     * @return the paqueteBatalla
     */
    public PaqueteBatalla getPaqueteBatalla() {
	return paqueteBatalla;
    }

    /**
     * @param paqueteBatalla the paqueteBatalla to set
     */
    public void setPaqueteBatalla(final PaqueteBatalla paqueteBatalla) {
	this.paqueteBatalla = paqueteBatalla;
    }

    /**
     * @return the paqueteAtacar
     */
    public PaqueteAtacar getPaqueteAtacar() {
	return paqueteAtacar;
    }

    /**
     * @param paqueteAtacar the paqueteAtacar to set
     */
    public void setPaqueteAtacar(final PaqueteAtacar paqueteAtacar) {
	this.paqueteAtacar = paqueteAtacar;
    }

    /**
     * @return the paqueteFinalizarBatalla
     */
    public PaqueteFinalizarBatalla getPaqueteFinalizarBatalla() {
	return paqueteFinalizarBatalla;
    }

    /**
     * @param paqueteFinalizarBatalla the paqueteFinalizarBatalla to set
     */
    public void setPaqueteFinalizarBatalla(final PaqueteFinalizarBatalla paqueteFinalizarBatalla) {
	this.paqueteFinalizarBatalla = paqueteFinalizarBatalla;
    }

    /**
     * @return the paqueteUsuario
     */
    public PaqueteUsuario getPaqueteUsuario() {
	return paqueteUsuario;
    }

    /**
     * @param paqueteUsuario the paqueteUsuario to set
     */
    public void setPaqueteUsuario(final PaqueteUsuario paqueteUsuario) {
	this.paqueteUsuario = paqueteUsuario;
    }

    /**
     * @return the paqueteDeMovimiento
     */
    public PaqueteDeMovimientos getPaqueteDeMovimiento() {
	return paqueteDeMovimiento;
    }

    /**
     * @param paqueteDeMovimiento the paqueteDeMovimiento to set
     */
    public void setPaqueteDeMovimiento(final PaqueteDeMovimientos paqueteDeMovimiento) {
	this.paqueteDeMovimiento = paqueteDeMovimiento;
    }

    /**
     * @return the paqueteDePersonajes
     */
    public PaqueteDePersonajes getPaqueteDePersonajes() {
	return paqueteDePersonajes;
    }

    /**
     * @param paqueteDePersonajes the paqueteDePersonajes to set
     */
    public void setPaqueteDePersonajes(final PaqueteDePersonajes paqueteDePersonajes) {
	this.paqueteDePersonajes = paqueteDePersonajes;
    }

    /**
     * @return the paqueteDeNpcs
     */
    public PaqueteDeNpcs getPaqueteDeNpcs() {
	return paqueteDeNpcs;
    }

    /**
     * @param paqueteDeNpcs the paqueteDeNpcs to set
     */
    public void setPaqueteDeNpcs(final PaqueteDeNpcs paqueteDeNpcs) {
	this.paqueteDeNpcs = paqueteDeNpcs;
    }
    
    public PaquetePelear getPaquetePelear() {
		return paquetePelear;
	}

	public void setPaquetePelear(PaquetePelear paquetePelear) {
		this.paquetePelear = paquetePelear;
	}

    /**
     * @return the socket
     */
    public Socket getSocket() {
	return socket;
    }

    /**
     * @return the entrada
     */
    public ObjectInputStream getEntrada() {
	return entrada;
    }

    /**
     * @return the salida
     */
    public ObjectOutputStream getSalida() {
	return salida;
    }

    /**
     * @return the gson
     */
    public Gson getGson() {
	return gson;
    }

}
