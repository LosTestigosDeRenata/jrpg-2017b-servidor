package comandos;

import java.io.IOException;
import java.util.ArrayList;

import dominio.Asesino;
import dominio.Casta;
import dominio.Elfo;
import dominio.Guerrero;
import dominio.Hechicero;
import dominio.Humano;
import dominio.Orco;
import dominio.Personaje;
import mensajeria.Comando;
import mensajeria.PaquetePelear;
import servidor.EscuchaCliente;
import servidor.Servidor;

/**
 * Clase Atacar Se evalua la condicion necesaria para pelear
 */
public class Atacar extends ComandosServer {
	
	@Override
	public void ejecutar()
	{
		System.out.println("Hola");		
		escuchaCliente.setPaquetePelear((PaquetePelear)gson.fromJson(cadenaLeida, PaquetePelear.class));
		
		Personaje personaje = null; // Atacante. //
		Personaje enemigo = null;
		Casta cPersonaje = null;
		Casta cEnemigo = null;
		
		
		ArrayList<String> personajeAtacante = escuchaCliente.getPaquetePelear().getPersonajeAtacante();
		ArrayList<String> castaAtacante = escuchaCliente.getPaquetePelear().getCastaAtacante();
		ArrayList<String> personajeEnemigo = escuchaCliente.getPaquetePelear().getPersonajeEnemigo();
		ArrayList<String> castaEnemigo = escuchaCliente.getPaquetePelear().getCastaEnemigo();
		
		/////////////////// Empiezo: ARMO LAS CASTAS DE LOS CONTENDIENTES. //////////////////////////
		String tipoDeCasta = castaAtacante.get(0);
		if(tipoDeCasta.equals("Guerrero"))
		{
			cPersonaje = new Guerrero(Double.parseDouble(castaAtacante.get(1)), Double.parseDouble(castaAtacante.get(2)), Double.parseDouble(castaAtacante.get(3)));
		}
		if(tipoDeCasta.equals("Hechicero"))
		{
			cPersonaje = new Hechicero(Double.parseDouble(castaAtacante.get(1)), Double.parseDouble(castaAtacante.get(2)), Double.parseDouble(castaAtacante.get(3)));
		}
		if(tipoDeCasta.equals("Asesino"))
		{
			cPersonaje = new Asesino(Double.parseDouble(castaAtacante.get(1)), Double.parseDouble(castaAtacante.get(2)), Double.parseDouble(castaAtacante.get(3)));
		}
		
		
		tipoDeCasta = castaEnemigo.get(0);
		if(tipoDeCasta.equals("Guerrero"))
		{
			cEnemigo = new Guerrero(Double.parseDouble(castaEnemigo.get(1)), Double.parseDouble(castaEnemigo.get(2)), Double.parseDouble(castaEnemigo.get(3)));
		}
		if(tipoDeCasta.equals("Hechicero"))
		{
			cEnemigo = new Hechicero(Double.parseDouble(castaEnemigo.get(1)), Double.parseDouble(castaEnemigo.get(2)), Double.parseDouble(castaEnemigo.get(3)));
		}
		if(tipoDeCasta.equals("Asesino"))
		{
			cEnemigo = new Asesino(Double.parseDouble(castaEnemigo.get(1)), Double.parseDouble(castaEnemigo.get(2)), Double.parseDouble(castaEnemigo.get(3)));
		}
		/////////////////// Fin: ARMO LAS CASTAS DE LOS CONTENDIENTES. //////////////////////////
		
		/////////////////// Empiezo: ARMO LOS PERSONAJES CONTENDIENTES. //////////////////////////
		String raza = personajeAtacante.get(0);
		if(raza.equals("Humano"))
		{
			personaje = new Humano(personajeAtacante.get(1), Integer.parseInt(personajeAtacante.get(2)), Integer.parseInt(personajeAtacante.get(3)), Integer.parseInt(personajeAtacante.get(4)), Integer.parseInt(personajeAtacante.get(5)), Integer.parseInt(personajeAtacante.get(6)), cPersonaje, Integer.parseInt(personajeAtacante.get(7)), Integer.parseInt(personajeAtacante.get(8)), Integer.parseInt(personajeAtacante.get(9)), Integer.parseInt(personajeAtacante.get(10)), Integer.parseInt(personajeAtacante.get(11)));
		}
		if(raza.equals("Orco"))
		{
			personaje = new Orco(personajeAtacante.get(1), Integer.parseInt(personajeAtacante.get(2)), Integer.parseInt(personajeAtacante.get(3)), Integer.parseInt(personajeAtacante.get(4)), Integer.parseInt(personajeAtacante.get(5)), Integer.parseInt(personajeAtacante.get(6)), cPersonaje, Integer.parseInt(personajeAtacante.get(7)), Integer.parseInt(personajeAtacante.get(8)), Integer.parseInt(personajeAtacante.get(9)), Integer.parseInt(personajeAtacante.get(10)), Integer.parseInt(personajeAtacante.get(11)));
		}
		if(raza.equals("Elfo"))
		{
			personaje = new Elfo(personajeAtacante.get(1), Integer.parseInt(personajeAtacante.get(2)), Integer.parseInt(personajeAtacante.get(3)), Integer.parseInt(personajeAtacante.get(4)), Integer.parseInt(personajeAtacante.get(5)), Integer.parseInt(personajeAtacante.get(6)), cPersonaje, Integer.parseInt(personajeAtacante.get(7)), Integer.parseInt(personajeAtacante.get(8)), Integer.parseInt(personajeAtacante.get(9)), Integer.parseInt(personajeAtacante.get(10)), Integer.parseInt(personajeAtacante.get(10)));
		}
		
		
		raza = personajeEnemigo.get(0);
		if(raza.equals("Humano"))
		{
			enemigo = new Humano(personajeEnemigo.get(1), Integer.parseInt(personajeEnemigo.get(2)), Integer.parseInt(personajeEnemigo.get(3)), Integer.parseInt(personajeEnemigo.get(4)), Integer.parseInt(personajeEnemigo.get(5)), Integer.parseInt(personajeEnemigo.get(6)), cPersonaje, Integer.parseInt(personajeEnemigo.get(7)), Integer.parseInt(personajeEnemigo.get(8)), Integer.parseInt(personajeEnemigo.get(9)), Integer.parseInt(personajeEnemigo.get(10)), Integer.parseInt(personajeEnemigo.get(11)));
		}
		if(raza.equals("Orco"))
		{
			enemigo = new Orco(personajeEnemigo.get(1), Integer.parseInt(personajeEnemigo.get(2)), Integer.parseInt(personajeEnemigo.get(3)), Integer.parseInt(personajeEnemigo.get(4)), Integer.parseInt(personajeEnemigo.get(5)), Integer.parseInt(personajeEnemigo.get(6)), cPersonaje, Integer.parseInt(personajeEnemigo.get(7)), Integer.parseInt(personajeEnemigo.get(8)), Integer.parseInt(personajeEnemigo.get(9)), Integer.parseInt(personajeEnemigo.get(10)), Integer.parseInt(personajeEnemigo.get(11)));
		}
		if(raza.equals("Elfo"))
		{
			enemigo = new Elfo(personajeEnemigo.get(1), Integer.parseInt(personajeEnemigo.get(2)), Integer.parseInt(personajeEnemigo.get(3)), Integer.parseInt(personajeEnemigo.get(4)), Integer.parseInt(personajeEnemigo.get(5)), Integer.parseInt(personajeEnemigo.get(6)), cPersonaje, Integer.parseInt(personajeEnemigo.get(7)), Integer.parseInt(personajeEnemigo.get(8)), Integer.parseInt(personajeEnemigo.get(9)), Integer.parseInt(personajeEnemigo.get(10)), Integer.parseInt(personajeEnemigo.get(11)));
		}
		/////////////////// Fin: ARMO LOS PERSONAJES CONTENDIENTES. //////////////////////////
		
		
		String accion = escuchaCliente.getPaquetePelear().getAccion();
		
		
		switch(accion) // Ya sé que a Lucas no le gustan los switch's pero es lo que hay por ahora jaja. //
		{
			case "hr1":
				System.out.println(personaje.habilidadRaza1(enemigo));				
				break;
			
			case "hr2":
				System.out.println(personaje.habilidadRaza2(enemigo));					
				break;
				
			case "hc1":
				System.out.println(personaje.habilidadCasta1(enemigo));				
				break;
				
			case "hc2":
				System.out.println(personaje.habilidadCasta2(enemigo));				
				break;
				
			case "hc3":
				System.out.println(personaje.habilidadCasta3(enemigo));
				
				break;
				
			case "energizar":
				personaje.serEnergizado(10);
				break;
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
		//enviarPersonajeAtacante.add(String.valueOf(personaje.getDefensa()));
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
		//enviarPersonajeEnemigo.add(String.valueOf(enemigo.getDefensa()));
		enviarPersonajeEnemigo.add(String.valueOf(enemigo.getSaludTope()));
		enviarPersonajeEnemigo.add(String.valueOf(enemigo.getEnergiaTope()));
		
		enviarAAtacante = new PaquetePelear(enviarPersonajeAtacante, enviarPersonajeEnemigo, enviarCastaAtacante, enviarCastaEnemigo, null);
		enviarAAtacado = new PaquetePelear(Comando.ATACADO, enviarPersonajeAtacante, enviarPersonajeEnemigo, enviarCastaAtacante, enviarCastaEnemigo, null);		
		
		
		System.out.println("Salud personaje: "+ personaje.getNombre() + " es " + personaje.getSalud());
		System.out.println("Magia personaje: " + personaje.getEnergia());
		System.out.println("Salud enemigo: "+ enemigo.getNombre() + " es " + enemigo.getSalud());
		System.out.println("Magia enemigo: " + enemigo.getEnergia());
		
		
		for(EscuchaCliente conectado : Servidor.getClientesConectados()) {
			if(conectado.getIdPersonaje() == personaje.getIdPersonaje()) {
				try {
					conectado.getSalida().writeObject(gson.toJson(enviarAAtacante));
				} catch (IOException e) {
					Servidor.log.append("Falló al intentar enviar el resultado de ataque al atacante: " + conectado.getPaquetePersonaje().getId() + "\n");
				}
			}
			
			if(conectado.getIdPersonaje() == enemigo.getIdPersonaje()) {
				try {
					conectado.getSalida().writeObject(gson.toJson(enviarAAtacado));
				} catch (IOException e) {
					Servidor.log.append("Falló al intentar enviar el resultado de ataque al atacado: " + conectado.getPaquetePersonaje().getId() + "\n");
				}
			}
		}
	}

}
