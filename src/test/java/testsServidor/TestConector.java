package testsServidor;

import java.io.IOException;
import java.util.Random;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import contenedores.ContenedorMochila;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;
import servidor.Conector;
import servidor.Servidor;

public class TestConector {

	Conector conector;
	
	@SuppressWarnings("unused")
	private String getRandomString() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 8) { // length of the random string.
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}
	
	@Before
	public void prepararPrueba() {
		new Servidor();
		Servidor.main(null);

		conector = new Conector();
		conector.connect();
	}
	
	@After
	public void concluirPrueba() {
		conector.close();
	}
	
	@Test
	public void testGetMochila() throws IOException {
		PaqueteUsuario user = conector.getUsuario("TestMochila");
		ContenedorMochila moch = conector.getContMoch(user.getIdPj());
		Assert.assertEquals(5, moch.getItem1());
		Assert.assertEquals(7, moch.getItem2());
		Assert.assertEquals(9, moch.getItem3());
		Assert.assertEquals(11, moch.getItem4());
	}
	
	@Test
	public void testActualizarInventario () throws IOException {
		PaqueteUsuario user = conector.getUsuario("TestMochila");
		PaquetePersonaje personaje = conector.getPersonaje(user);
		personaje.eliminarItems();
		personaje.anadirItem(5);
		personaje.anadirItem(7);
		personaje.anadirItem(9);
		personaje.anadirItem(11);
		conector.actualizarInventario(personaje);
		personaje = conector.getPersonaje(user);
		Assert.assertEquals(5, personaje.getItemID(0));
		Assert.assertEquals(7, personaje.getItemID(1));
		Assert.assertEquals(9, personaje.getItemID(2));
		Assert.assertEquals(11, personaje.getItemID(3));
	}
	
	@Test
	public void testPersonaje() throws IOException {
		PaquetePersonaje personaje = new PaquetePersonaje();
		
		PaqueteUsuario user = new PaqueteUsuario();
		user.setUsername("TestPersonaje");
		user.setPassword("testpersonaje");
		
		//Assert.assertEquals(true, conector.eliminarPersonaje(63, user));
		
		personaje.setCasta("Guerrero");
		personaje.setRaza("Humano");
		personaje.setFuerza(15);
		personaje.setDestreza(10);
		personaje.setInteligencia(10);
		personaje.setSaludTope(180);
		personaje.setEnergiaTope(155);
		personaje.setNombre("Riquelme");
		Assert.assertEquals(true, conector.registrarPersonaje(personaje, user));
		
		// actualizo el user
		user = conector.getUsuario(user.getUsername());
		personaje = conector.getPersonaje(user);
		
		Assert.assertEquals(user.getIdPj(), personaje.getId());
		Assert.assertEquals("Guerrero", personaje.getCasta());
		Assert.assertEquals("Humano", personaje.getRaza());
		Assert.assertEquals(15, personaje.getFuerza());
		Assert.assertEquals(10, personaje.getDestreza());
		Assert.assertEquals(10, personaje.getInteligencia());
		Assert.assertEquals(180, personaje.getSaludTope());
		Assert.assertEquals(155, personaje.getEnergiaTope());
		Assert.assertEquals("Riquelme", personaje.getNombre());
		Assert.assertEquals(0, personaje.getExperiencia());
		Assert.assertEquals(1, personaje.getNivel());
		Assert.assertEquals(1, personaje.getNivel());
		Assert.assertEquals(1, personaje.getNivel());
		Assert.assertEquals(1, personaje.getNivel());
		
		personaje.setNombre("Arturo");
		personaje.anadirItem(3);
		personaje.anadirItem(5);
		personaje.anadirItem(7);
		Assert.assertEquals(3, personaje.getCantItems());
		conector.actualizarPersonajeSubioNivel(personaje);
		conector.actualizarInventario(personaje);
		Assert.assertEquals(3, personaje.getCantItems());
		personaje = conector.getPersonaje(user);
		Assert.assertEquals("Arturo", personaje.getNombre());
		Assert.assertEquals(3, personaje.getItemID(0));
		Assert.assertEquals(5, personaje.getItemID(1));
		Assert.assertEquals(7, personaje.getItemID(2));
		
		Assert.assertEquals(true, conector.eliminarPersonaje(user.getIdPj(), user));
	}
	
	@Test
	public void testUsuario() {
		
		PaqueteUsuario usuario = new PaqueteUsuario();
		usuario.setIdPj(-1);
		usuario.setUsername("Test");
		usuario.setPassword("test");
		
		Assert.assertEquals(true, conector.registrarUsuario(usuario));
		
		PaqueteUsuario usuario2 = conector.getUsuario(usuario.getUsername());
		Assert.assertEquals("Test", usuario2.getUsername());
		Assert.assertEquals("test", usuario2.getPassword());
		Assert.assertEquals(-1, usuario2.getIdPj());
		Assert.assertEquals(true, conector.loguearUsuario(usuario2));
		
		Assert.assertEquals(true, conector.eliminarUsuario(usuario2));
		
		usuario.setUsername("Kataparuto");
		Assert.assertEquals(false, conector.loguearUsuario(usuario));
	}

}
