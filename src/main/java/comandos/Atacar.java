package comandos;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import dominio.Casta;
import dominio.Personaje;
import mensajeria.Comando;
import mensajeria.PaquetePelear;
import mensajeria.PaquetePersonaje;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase Atacar Comando que efectua las acciones de la batalla en el servidor.
 */
public class Atacar extends ComandosServer {

    private static final int ENERGIA_ENERGIZADO = 10;
    private static final int ID_RAZA = 0;
    private static final int ID_NOMBRE = 1;
    private static final int ID_SALUD = 2;
    private static final int ID_ENERGIA = 3;
    private static final int ID_FUERZA = 4;
    private static final int ID_DESTREZA = 5;
    private static final int ID_INTELIGENCIA = 6;
    private static final int ID_EXPERIENCIA = 7;
    private static final int ID_NIVEL = 8;
    private static final int ID_IDPERSONAJE = 9;
    private static final int ID_SALUDTOPE = 10;
    private static final int ID_ENERGIATOPE = 11;

    private static final int ID_NOMBRECASTA = 0;

    @Override
    public void ejecutar() {
	escuchaCliente.setPaquetePelear(gson.fromJson(cadenaLeida, PaquetePelear.class));

	Personaje personaje = null; // Atacante. //
	Personaje enemigo = null;
	Casta cPersonaje = null;
	Casta cEnemigo = null;

	ArrayList<String> personajeAtacante = escuchaCliente.getPaquetePelear().getPersonajeAtacante();
	ArrayList<String> castaAtacante = escuchaCliente.getPaquetePelear().getCastaAtacante();
	ArrayList<String> personajeEnemigo = escuchaCliente.getPaquetePelear().getPersonajeEnemigo();
	ArrayList<String> castaEnemigo = escuchaCliente.getPaquetePelear().getCastaEnemigo();

	String casta = castaAtacante.get(ID_NOMBRECASTA);
	String raza = personajeAtacante.get(ID_RAZA);
	String nombre = personajeAtacante.get(ID_NOMBRE);
	int salud = Integer.parseInt(personajeAtacante.get(ID_SALUD));
	int energia = Integer.parseInt(personajeAtacante.get(ID_ENERGIA));
	int fuerza = Integer.parseInt(personajeAtacante.get(ID_FUERZA));
	int destreza = Integer.parseInt(personajeAtacante.get(ID_DESTREZA));
	int inteligencia = Integer.parseInt(personajeAtacante.get(ID_INTELIGENCIA));
	int experiencia = Integer.parseInt(personajeAtacante.get(ID_EXPERIENCIA));
	int nivel = Integer.parseInt(personajeAtacante.get(ID_NIVEL));
	int id = Integer.parseInt(personajeAtacante.get(ID_IDPERSONAJE));
	int saludTope = Integer.parseInt(personajeAtacante.get(ID_SALUDTOPE));
	int energiaTope = Integer.parseInt(personajeAtacante.get(ID_ENERGIATOPE));

	try {
	    cPersonaje = (Casta) Class.forName("dominio" + "." + casta).newInstance();
	    personaje = (Personaje) Class.forName("dominio" + "." + raza)
		    .getConstructor(String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE,
			    Casta.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE)
		    .newInstance(nombre, salud, energia, fuerza, destreza, inteligencia, cPersonaje, experiencia, nivel,
			    id, saludTope, energiaTope);
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
		| InvocationTargetException | NoSuchMethodException | SecurityException e) {
	    Servidor.log.append("Error al crear la batalla\n");
	}

	casta = castaEnemigo.get(ID_NOMBRECASTA);
	raza = personajeEnemigo.get(ID_RAZA);
	nombre = personajeEnemigo.get(ID_NOMBRE);
	salud = Integer.parseInt(personajeEnemigo.get(ID_SALUD));
	energia = Integer.parseInt(personajeEnemigo.get(ID_ENERGIA));
	fuerza = Integer.parseInt(personajeEnemigo.get(ID_FUERZA));
	destreza = Integer.parseInt(personajeEnemigo.get(ID_DESTREZA));
	inteligencia = Integer.parseInt(personajeEnemigo.get(ID_INTELIGENCIA));
	experiencia = Integer.parseInt(personajeEnemigo.get(ID_EXPERIENCIA));
	nivel = Integer.parseInt(personajeEnemigo.get(ID_NIVEL));
	id = Integer.parseInt(personajeEnemigo.get(ID_IDPERSONAJE));
	saludTope = Integer.parseInt(personajeEnemigo.get(ID_SALUDTOPE));
	energiaTope = Integer.parseInt(personajeEnemigo.get(ID_ENERGIATOPE));

	try {
	    cEnemigo = (Casta) Class.forName("dominio" + "." + casta).newInstance();
	    enemigo = (Personaje) Class.forName("dominio" + "." + raza)
		    .getConstructor(String.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE,
			    Casta.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE)
		    .newInstance(nombre, salud, energia, fuerza, destreza, inteligencia, cEnemigo, experiencia, nivel,
			    id, saludTope, energiaTope);
	} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException
		| InvocationTargetException | NoSuchMethodException | SecurityException e) {
	    Servidor.log.append("Error al crear la batalla\n");
	}

	// Cheat de invulnerabilidad
	PaquetePersonaje paqueteAtacante = Servidor.getPersonajesConectados().get(personaje.getIdPersonaje());
	PaquetePersonaje paqueteEnemigo = Servidor.getPersonajesConectados().get(enemigo.getIdPersonaje());

	if (!(paqueteAtacante.esInvulnerable() && paqueteEnemigo.esInvulnerable())) {
	    personaje.setInvulnerabilidad(paqueteAtacante.esInvulnerable());
	    enemigo.setInvulnerabilidad(paqueteEnemigo.esInvulnerable());
	}

	String accion = escuchaCliente.getPaquetePelear().getAccion();
	switch (accion) // Ya sé que a Lucas no le gustan los switch's pero es
			// lo que hay por ahora jaja. //
	{
	case "hr1":
	    personaje.habilidadRaza1(enemigo);
	    break;
	case "hr2":
	    personaje.habilidadRaza2(enemigo);
	    break;
	case "hc1":
	    personaje.habilidadCasta1(enemigo);
	    break;
	case "hc2":
	    personaje.habilidadCasta2(enemigo);
	    break;
	case "hc3":
	    personaje.habilidadCasta3(enemigo);
	    break;
	case "energizar":
	    personaje.serEnergizado(ENERGIA_ENERGIZADO);
	    break;
	default:
	    Servidor.log.append("Acción inválida en la batalla\n");
	}

	PaquetePelear enviarAAtacante;
	PaquetePelear enviarAAtacado;
	ArrayList<String> enviarPersonajeAtacante = new ArrayList<>();
	ArrayList<String> enviarCastaAtacante = new ArrayList<>();
	ArrayList<String> enviarPersonajeEnemigo = new ArrayList<>();
	ArrayList<String> enviarCastaEnemigo = new ArrayList<>();

	enviarCastaAtacante.add(cPersonaje.getClass().getSimpleName());
	enviarCastaAtacante.add(String.valueOf(cPersonaje.getProbabilidadGolpeCritico()));
	enviarCastaAtacante.add(String.valueOf(cPersonaje.getProbabilidadEvitarDanio()));
	enviarCastaAtacante.add(String.valueOf(cPersonaje.getDanioCritico()));

	enviarCastaEnemigo.add(cEnemigo.getClass().getSimpleName());
	enviarCastaEnemigo.add(String.valueOf(cEnemigo.getProbabilidadGolpeCritico()));
	enviarCastaEnemigo.add(String.valueOf(cEnemigo.getProbabilidadEvitarDanio()));
	enviarCastaEnemigo.add(String.valueOf(cEnemigo.getDanioCritico()));

	enviarPersonajeAtacante.add(personaje.getNombreRaza());
	enviarPersonajeAtacante.add(personaje.getNombre());
	enviarPersonajeAtacante.add(String.valueOf(personaje.getSalud()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getEnergia()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getFuerza()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getDestreza()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getDestreza()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getExperiencia()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getNivel()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getIdPersonaje()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getSaludTope()));
	enviarPersonajeAtacante.add(String.valueOf(personaje.getEnergiaTope()));

	enviarPersonajeEnemigo.add(enemigo.getNombreRaza());
	enviarPersonajeEnemigo.add(enemigo.getNombre());
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getSalud()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getEnergia()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getFuerza()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getDestreza()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getDestreza()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getExperiencia()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getNivel()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getIdPersonaje()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getSaludTope()));
	enviarPersonajeEnemigo.add(String.valueOf(enemigo.getEnergiaTope()));

	enviarAAtacante = new PaquetePelear(enviarPersonajeAtacante, enviarPersonajeEnemigo, enviarCastaAtacante,
		enviarCastaEnemigo, null);
	enviarAAtacado = new PaquetePelear(Comando.ATACADO, enviarPersonajeAtacante, enviarPersonajeEnemigo,
		enviarCastaAtacante, enviarCastaEnemigo, null);

	for (EscuchaCliente conectado : Servidor.getClientesConectados()) {
	    if (conectado.getIdPersonaje() == personaje.getIdPersonaje()) {
		try {
		    conectado.getSalida().writeObject(gson.toJson(enviarAAtacante));
		} catch (IOException e) {
		    Servidor.log.append("Falló al intentar enviar el resultado de ataque al atacante: "
			    + conectado.getPaquetePersonaje().getId() + "\n");
		}
	    }

	    if (conectado.getIdPersonaje() == enemigo.getIdPersonaje()) {
		try {
		    conectado.getSalida().writeObject(gson.toJson(enviarAAtacado));
		} catch (IOException e) {
		    Servidor.log.append("Falló al intentar enviar el resultado de ataque al atacado: "
			    + conectado.getPaquetePersonaje().getId() + "\n");
		}
	    }
	}
    }

}
