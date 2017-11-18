package servidor;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;

import mensajeria.PaqueteDeNpcs;
import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;

public class Servidor extends Thread {
    private static ArrayList<EscuchaCliente> clientesConectados = new ArrayList<>();

    private static Map<Integer, PaqueteMovimiento> ubicacionPersonajes = new HashMap<>();
    private static Map<Integer, PaquetePersonaje> personajesConectados = new HashMap<>();
    private static PaqueteDeNpcs paqueteDeNpcs = new PaqueteDeNpcs();

    private static Thread server;

    private static ServerSocket serverSocket;
    private static Conector conexionDB;
    private final int puerto = 55050;

    private static final int ANCHO = 700;
    private static final int ALTO = 640;
    private static final int ALTO_LOG = 520;
    private static final int ANCHO_LOG = ANCHO - 25;

    public static JTextArea log;

    public static AtencionConexiones atencionConexiones;
    public static AtencionMovimientos atencionMovimientos;

    /**
     * Programa de ejecución principal.
     * @param args
     */
    public static void main(final String[] args) {
	cargarInterfaz();
    }

    /**
     * Muestra la interfaz gráfica del servidor.
     */
    private static void cargarInterfaz() {
	JFrame ventana = new JFrame("Servidor WOME");
	ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	ventana.setSize(ANCHO, ALTO);
	ventana.setResizable(false);
	ventana.setLocationRelativeTo(null);
	ventana.setLayout(null);
	ventana.setIconImage(Toolkit.getDefaultToolkit().getImage("src/main/java/servidor/server.png"));
	JLabel titulo = new JLabel("Log del servidor...");
	titulo.setFont(new Font("Courier New", Font.BOLD, 16));
	titulo.setBounds(10, 0, 200, 30);
	ventana.add(titulo);

	log = new JTextArea();
	log.setEditable(false);
	log.setFont(new Font("Times New Roman", Font.PLAIN, 13));
	JScrollPane scroll = new JScrollPane(log, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
		ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	scroll.setBounds(10, 40, ANCHO_LOG, ALTO_LOG);
	ventana.add(scroll);

	final JButton botonIniciar = new JButton();
	final JButton botonDetener = new JButton();
	botonIniciar.setText("Iniciar");
	botonIniciar.setBounds(220, ALTO - 70, 100, 30);
	botonIniciar.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		server = new Thread(new Servidor());
		server.start();
		botonIniciar.setEnabled(false);
		botonDetener.setEnabled(true);
	    }
	});

	ventana.add(botonIniciar);

	botonDetener.setText("Detener");
	botonDetener.setBounds(360, ALTO - 70, 100, 30);
	botonDetener.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		try {
		    server.stop();
		    atencionConexiones.stop();
		    atencionMovimientos.stop();
		    for (EscuchaCliente cliente : clientesConectados) {
			cliente.getSalida().close();
			cliente.getEntrada().close();
			cliente.getSocket().close();
		    }
		    serverSocket.close();
		    log.append("El servidor se ha detenido." + System.lineSeparator());
		} catch (IOException e1) {
		    log.append("Fallo al intentar detener el servidor." + System.lineSeparator());
		}
		if (conexionDB != null) {
		    conexionDB.close();
		}
		botonDetener.setEnabled(false);
		botonIniciar.setEnabled(true);
	    }
	});
	botonDetener.setEnabled(false);
	ventana.add(botonDetener);

	ventana.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
	ventana.addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(final WindowEvent evt) {
		if (serverSocket != null) {
		    try {
			server.stop();
			atencionConexiones.stop();
			atencionMovimientos.stop();
			for (EscuchaCliente cliente : clientesConectados) {
			    cliente.getSalida().close();
			    cliente.getEntrada().close();
			    cliente.getSocket().close();
			}
			serverSocket.close();
			log.append("El servidor se ha detenido." + System.lineSeparator());
		    } catch (IOException e) {
			log.append("Fallo al intentar detener el servidor." + System.lineSeparator());
			System.exit(1);
		    }
		}
		if (conexionDB != null) {
		    conexionDB.close();
		}
		System.exit(0);
	    }
	});

	ventana.setVisible(true);
    }

    @Override
    public void run() {
	try {

	    conexionDB = new Conector();
	    conexionDB.connect();

	    log.append("Iniciando el servidor..." + System.lineSeparator());
	    serverSocket = new ServerSocket(puerto);
	    log.append("Servidor esperando conexiones..." + System.lineSeparator());
	    String ipRemota;

	    atencionConexiones = new AtencionConexiones();
	    atencionMovimientos = new AtencionMovimientos();

	    atencionConexiones.start();
	    atencionMovimientos.start();

	    while (true) {
		Socket cliente = serverSocket.accept();
		ipRemota = cliente.getInetAddress().getHostAddress();
		log.append(ipRemota + " se ha conectado" + System.lineSeparator());

		ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
		ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());

		EscuchaCliente atencion = new EscuchaCliente(ipRemota, cliente, entrada, salida);
		atencion.start();
		clientesConectados.add(atencion);
	    }
	} catch (Exception e) {
	    log.append("Fallo la conexión." + System.lineSeparator());
	}
    }

    /**
     * Envia un mensaje a un usuario conectado.
     * @param pqm PaqueteMensaje con el mensaje a enviar.
     * @return devuelve true si se pudo enviar el mensaje.
     */
    public static boolean mensajeAUsuario(final PaqueteMensaje pqm) {
	boolean result = true;
	boolean noEncontro = true;
	for (Map.Entry<Integer, PaquetePersonaje> personaje : personajesConectados.entrySet()) {
	    if (noEncontro && (!personaje.getValue().getNombre().equals(pqm.getUserReceptor()))) {
		result = false;
	    } else {
		result = true;
		noEncontro = false;
	    }
	}
	// Si existe inicio sesion
	if (result) {
	    Servidor.log
		    .append(pqm.getUserEmisor() + " envió mensaje a " + pqm.getUserReceptor() + System.lineSeparator());
	} else {
	    // Si no existe informo y devuelvo false
	    Servidor.log.append("El mensaje para " + pqm.getUserReceptor()
		    + " no se envió, ya que se encuentra desconectado." + System.lineSeparator());
	}
	
	return result;
    }

    /**
     * Método sin implementar.
     * @param contador
     * @return
     */
    public static boolean mensajeAAll(final int contador) {

	boolean result = personajesConectados.size() == contador + 1;
	System.out.println(personajesConectados.size());
	if (result) {
	    // inicio
								  // sesion
	    Servidor.log.append("Se ha enviado un mensaje a todos los usuarios" + System.lineSeparator());
	} else {
	    Servidor.log
		    .append("Uno o más de todos los usuarios se ha desconectado, se ha mandado el mensaje a los demas."
			    + System.lineSeparator());
	}

	return result; // Devuelvo resultado
    }

    /**
     * @return devuelve una lista con todos los clientes conectados.
     */
    public static ArrayList<EscuchaCliente> getClientesConectados() {
	return clientesConectados;
    }

    /**
     * @return devuelve un mapa con las ubicaciones de los personajes.
     */
    public static Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
	return ubicacionPersonajes;
    }

    /**
     * @return devuelve un mapa con los personajes conectados al servidor.
     */
    public static Map<Integer, PaquetePersonaje> getPersonajesConectados() {
	return personajesConectados;
    }

    /**
     * @return devuelve el PaqueteDeNpcs del servidor.
     */
    public static PaqueteDeNpcs getPaqueteDeNpcs() {
	return paqueteDeNpcs;
    }

    /**
     * @param paqueteDeNpcs PaqueteDeNpcs a setear.
     */
    public static void setPaqueteDeNpcs(final PaqueteDeNpcs paqueteDeNpcs) {
	Servidor.paqueteDeNpcs = paqueteDeNpcs;
    }

    /**
     * @return devuelve el conector del servidor.
     */
    public static Conector getConector() {
	return conexionDB;
    }
}
