package servidor;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.service.ServiceRegistry;

import contenedores.ContenedorInventario;
import contenedores.ContenedorItem;
import contenedores.ContenedorMochila;
import contenedores.ContenedorPersonaje;
import contenedores.ContenedorRegistro;
import dominio.MyRandom;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

/**
 * Clase que se encarga de la conexión entre el servidor y la base de datos.
 * @author Santi
 */
public class Conector {

    private static final int ULTIMO_ID_ITEM = 29;
    private static final int CANT_ITEMS_POSIBLES = 9;
    public static final int MAX_CANT_ITEMS = 20;
    private SessionFactory factory;

    /**
     * Establece la conexión del servidor con la base de datos.
     */
    public void connect() {
	Servidor.log.append("Estableciendo conexión con la base de datos..." + System.lineSeparator());
	
	Configuration cfg = new Configuration();
	cfg.configure("hibernate.cfg.xml");
	factory = cfg.buildSessionFactory();
	
	Servidor.log.append("Conexión con la base de datos establecida con éxito." + System.lineSeparator());
    }

    /**
     * Cierra la conexión entre la base de datos y el servidor.
     */
    public void close() {
	factory.close();
    }

    /**
     * Registra un personaje en la base de datos.
     * @param paquetePersonaje El personaje a registrar.
     * @param paqueteUsuario El usuario a quién le pertenece el personaje.
     * @return devuelve true si se pudo registrar el personaje.
     */
    public boolean registrarPersonaje(final PaquetePersonaje paquetePersonaje, final PaqueteUsuario paqueteUsuario) {
	boolean pudeRegistrar = false;
	ContenedorPersonaje contPer = new ContenedorPersonaje();
	ContenedorRegistro contReg = new ContenedorRegistro();
	ContenedorMochila contMoch = new ContenedorMochila();
	ContenedorInventario contInv = new ContenedorInventario();
	Session session = factory.openSession();
	Transaction tx = session.beginTransaction();

	try {
	    Criteria c = session.createCriteria(ContenedorPersonaje.class);
	    c.addOrder(Order.desc("id"));
	    c.setMaxResults(1);
	    int lastId = ((ContenedorPersonaje) c.uniqueResult()).getIdPersonaje();
	    // Registro al personaje en la base de datos y recupero la última
	    // key generada
	    int idPersonaje = lastId + 1;

	    contPer.setIdPersonaje(idPersonaje);
	    contPer.setIdAlianza(-1);
	    contPer.setIdInventario(idPersonaje);
	    contPer.setIdMochila(idPersonaje);
	    contPer.setNombre(paquetePersonaje.getNombre());
	    contPer.setRaza(paquetePersonaje.getRaza());
	    contPer.setCasta(paquetePersonaje.getCasta());
	    contPer.setFuerza(paquetePersonaje.getFuerza());
	    contPer.setDestreza(paquetePersonaje.getDestreza());
	    contPer.setInteligencia(paquetePersonaje.getInteligencia());
	    contPer.setSaludTope(paquetePersonaje.getSaludTope());
	    contPer.setEnergiaTope(paquetePersonaje.getEnergiaTope());
	    contPer.setNivel(1);
	    contPer.setExperiencia(0);
	    contPer.setPtsSkill(paquetePersonaje.getPuntosSkill());
	    session.save(contPer);

	    // Le asigno el personaje al usuario
	    contReg.setIdPersonaje(idPersonaje);
	    contReg.setUsuario(paqueteUsuario.getUsername());
	    contReg.setPassword(paqueteUsuario.getPassword());
	    session.update(contReg);

	    // Por último registro el inventario y la mochila
	    // Preparo el inventario
	    contInv.setIdInventario(idPersonaje);
	    contInv.setAccesorio(-1);
	    contInv.setCabeza(-1);
	    contInv.setManos1(-1);
	    contInv.setManos2(-1);
	    contInv.setPecho(-1);
	    contInv.setPie(-1);
	    session.save(contInv);

	    // Preparo la mochila
	    contMoch.setIdMochila(idPersonaje);
	    contMoch.setItem1(-1);
	    contMoch.setItem2(-1);
	    contMoch.setItem3(-1);
	    contMoch.setItem4(-1);
	    contMoch.setItem5(-1);
	    contMoch.setItem6(-1);
	    contMoch.setItem7(-1);
	    contMoch.setItem8(-1);
	    contMoch.setItem9(-1);
	    contMoch.setItem10(-1);
	    contMoch.setItem11(-1);
	    contMoch.setItem12(-1);
	    contMoch.setItem13(-1);
	    contMoch.setItem14(-1);
	    contMoch.setItem15(-1);
	    contMoch.setItem16(-1);
	    contMoch.setItem17(-1);
	    contMoch.setItem18(-1);
	    contMoch.setItem19(-1);
	    contMoch.setItem20(-1);
	    session.save(contMoch);

	    pudeRegistrar = true;
	    tx.commit();
	    Servidor.log.append("El personaje " + paquetePersonaje.getNombre() + " se ha registrado con éxito"
		    + System.lineSeparator());
	} catch (HibernateException e) {
	    tx.rollback();
	    e.printStackTrace();
	} finally {
	    session.close();
	}

	return pudeRegistrar;
    }

    /**
     * Actualiza los datos de un personaje en la base de datos.
     * @param personaje El personaje a actualizar.
     * @return devuelve true si se pudo actualizar el personaje.
     */
    public boolean actualizarPersonaje(final PaquetePersonaje personaje) {
	boolean pudeActualizar = false;
	ContenedorPersonaje contPer = new ContenedorPersonaje();
	ContenedorMochila contMoch = new ContenedorMochila();
	Session session = factory.openSession();
	Transaction tx = session.beginTransaction();

	contPer.setIdPersonaje(personaje.getId());
	contPer.setNombre(personaje.getNombre());
	contPer.setRaza(personaje.getRaza());
	contPer.setCasta(personaje.getCasta());
	contPer.setFuerza(personaje.getFuerza());
	contPer.setDestreza(personaje.getDestreza());
	contPer.setInteligencia(personaje.getInteligencia());
	contPer.setSaludTope(personaje.getSaludTope());
	contPer.setEnergiaTope(personaje.getEnergiaTope());
	contPer.setExperiencia(personaje.getExperiencia());
	contPer.setNivel(personaje.getNivel());
	contPer.setPtsSkill(personaje.getPuntosSkill());

	try {
	    session.update(contPer);
	    tx.commit();
	    pudeActualizar = true;
	    Servidor.log.append(
		    "El personaje " + personaje.getId() + " se ha actualizado con éxito." + System.lineSeparator());
	} catch (HibernateException e) {
	    tx.rollback();
	    e.printStackTrace();
	} finally {
	    session.close();
	}

	// agrego items al paquete
	personaje.eliminarItems();
	contMoch = getContMoch(personaje.getId());

	if (contMoch.getItem1() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem1()).getItem());
	}
	if (contMoch.getItem2() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem2()).getItem());
	}
	if (contMoch.getItem3() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem3()).getItem());
	}
	if (contMoch.getItem4() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem4()).getItem());
	}
	if (contMoch.getItem5() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem5()).getItem());
	}
	if (contMoch.getItem6() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem6()).getItem());
	}
	if (contMoch.getItem7() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem7()).getItem());
	}
	if (contMoch.getItem8() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem8()).getItem());
	}
	if (contMoch.getItem9() != -1) {
	    personaje.anadirItem(getContItem(contMoch.getItem9()).getItem());
	}

	return pudeActualizar;
    }

    /**
     * Elimina un personaje de la base de datos.
     * @param idPersonaje El ID del personaje a eliminar.
     * @param usuario El usuario al que le pertenece el personaje.
     * @return devuelve true si se pudo eliminar el personaje.
     */
    public boolean eliminarPersonaje(final int idPersonaje, final PaqueteUsuario usuario) {
	boolean pudeEliminar = false;
	Session session = factory.openSession();
	ContenedorPersonaje contPer = new ContenedorPersonaje();
	ContenedorInventario contInv = new ContenedorInventario();
	ContenedorMochila contMoch = new ContenedorMochila();
	ContenedorRegistro contReg = new ContenedorRegistro();
	Transaction tx = session.beginTransaction();

	contPer.setIdPersonaje(idPersonaje);
	contInv.setIdInventario(idPersonaje);
	contMoch.setIdMochila(idPersonaje);
	contReg.setUsuario(usuario.getUsername());
	contReg.setPassword(usuario.getPassword());
	contReg.setIdPersonaje(-1);

	try {
	    session.delete(contPer);
	    session.delete(contInv);
	    session.delete(contMoch);
	    session.update(contReg);
	    tx.commit();
	    pudeEliminar = true;
	    Servidor.log.append("El personaje" + idPersonaje + " se ha eliminado con éxito." + System.lineSeparator());
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}
	return pudeEliminar;
    }

    /**
     * Trae un personaje de la base de datos.
     * @param user Usuario al que le pertenece el personaje.
     * @return devuelve un PaquetePersonaje con los datos cargados.
     * @throws IOException
     */
    public PaquetePersonaje getPersonaje(PaqueteUsuario user) throws IOException {
	PaquetePersonaje paquete = new PaquetePersonaje();

	Session session = factory.openSession();
	ContenedorPersonaje contPer = new ContenedorPersonaje();
	ContenedorMochila contMoch = new ContenedorMochila();
	Transaction tx = session.beginTransaction();

	try {
	    // busco el usuario para obtener id del pj
	    user = getUsuario(user.getUsername());

	    // busco personaje
	    Servidor.log.append("Buscando personaje " + user.getIdPj() + System.lineSeparator());

	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<ContenedorPersonaje> cq = cb.createQuery(ContenedorPersonaje.class);
	    Root<ContenedorPersonaje> rp = cq.from(ContenedorPersonaje.class);
	    ParameterExpression<Integer> p = cb.parameter(Integer.class, "idPersonaje");
	    cq.select(rp).where(cb.equal(rp.get("idPersonaje"), p));

	    contPer = session.createQuery(cq).setParameter("idPersonaje", user.getIdPj()).getSingleResult();

	    tx.commit();
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}

	// seteo mochila
	paquete.setId(contPer.getIdPersonaje());
	paquete.setNombre(contPer.getNombre());
	paquete.setRaza(contPer.getRaza());
	paquete.setCasta(contPer.getCasta());
	paquete.setFuerza(contPer.getFuerza());
	paquete.setDestreza(contPer.getDestreza());
	paquete.setInteligencia(contPer.getInteligencia());
	paquete.setSaludTope(contPer.getSaludTope());
	paquete.setEnergiaTope(contPer.getEnergiaTope());
	paquete.setExperiencia(contPer.getExperiencia());
	paquete.setNivel(contPer.getNivel());
	paquete.setPuntosSkill(contPer.getPtsSkill());

	// busco mochila
	contMoch = getContMoch(user.getIdPj());

	// agrego items
	if (contMoch.getItem1() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem1()).getItem());
	}
	if (contMoch.getItem2() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem2()).getItem());
	}
	if (contMoch.getItem3() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem3()).getItem());
	}
	if (contMoch.getItem4() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem4()).getItem());
	}
	if (contMoch.getItem5() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem5()).getItem());
	}
	if (contMoch.getItem6() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem6()).getItem());
	}
	if (contMoch.getItem7() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem7()).getItem());
	}
	if (contMoch.getItem8() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem8()).getItem());
	}
	if (contMoch.getItem9() != -1) {
	    paquete.anadirItem(getContItem(contMoch.getItem9()).getItem());
	}

	return paquete;
    }

    /**
     * Trae la información de la mochila de un personaje de la base de datos.
     * @param idMochila ID de la Mochila a buscar.
     * @return devuelve un ContenedorMochila con los datos cargados.
     */
    public ContenedorMochila getContMoch(final int idMochila) {
	Session session = factory.openSession();
	ContenedorMochila cont = new ContenedorMochila();
	Transaction tx = session.beginTransaction();

	Servidor.log.append("Buscando mochila del personaje " + idMochila + System.lineSeparator());
	// busco personaje
	try {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<ContenedorMochila> cq = cb.createQuery(ContenedorMochila.class);
	    Root<ContenedorMochila> rp = cq.from(ContenedorMochila.class);
	    ParameterExpression<Integer> p = cb.parameter(Integer.class, "idMochila");
	    cq.select(rp).where(cb.equal(rp.get("idMochila"), p));

	    cont = session.createQuery(cq).setParameter("idMochila", idMochila).getSingleResult();

	    tx.commit();
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}

	return cont;
    }

    /**
     * Trae la información de un item de la base de datos.
     * @param itemId ID del item a buscar.
     * @return devuelve un ContenedorItem con los datos cargados.
     */
    public ContenedorItem getContItem(final int itemId) {
	Session session = factory.openSession();
	ContenedorItem cont = new ContenedorItem();
	Transaction tx = session.beginTransaction();

	Servidor.log.append("Buscando item: " + itemId + System.lineSeparator());
	try {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<ContenedorItem> cq = cb.createQuery(ContenedorItem.class);
	    Root<ContenedorItem> rp = cq.from(ContenedorItem.class);
	    ParameterExpression<Integer> p = cb.parameter(Integer.class, "idItem");
	    cq.select(rp).where(cb.equal(rp.get("idItem"), p));

	    cont = session.createQuery(cq).setParameter("idItem", itemId).getSingleResult();
	    tx.commit();
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}

	return cont;
    }

    /**
     * Registra a un usuario en la base de datos.
     * @param user Usuario a registrar.
     * @return devuelve true si se pudo registrar el usuario.
     */
    public boolean registrarUsuario(final PaqueteUsuario user) {

	boolean pudoRegistrar = false;
	Session session = factory.openSession();
	ContenedorRegistro cont = new ContenedorRegistro();
	Transaction tx = session.beginTransaction();

	cont.setIdPersonaje(user.getIdPj());
	cont.setUsuario(user.getUsername());
	cont.setPassword(user.getPassword());

	try {
	    session.save(cont);
	    tx.commit();
	    pudoRegistrar = true;
	    Servidor.log.append(
		    "El usuario " + cont.getUsuario() + " se ha registrado con éxito." + System.lineSeparator());
	} catch (HibernateException e) {
	    tx.rollback();
	    e.printStackTrace();
	} finally {
	    session.close();
	}

	return pudoRegistrar;
    }

    /**
     * Trae un usuario de la base de datos.
     * @param usuario El nombre del usuario a buscar.
     * @return devuelve un PaqueteUsuario con los datos cargados.
     */
    public PaqueteUsuario getUsuario(String usuario) {
	PaqueteUsuario paquete = new PaqueteUsuario();
	Session session = factory.openSession();
	ContenedorRegistro cont = new ContenedorRegistro();
	Transaction tx = session.beginTransaction();

	if (usuario.isEmpty()) {
	    usuario = "Ben";
	}

	Servidor.log.append("Buscando al usuario " + usuario + System.lineSeparator());
	try {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaQuery<ContenedorRegistro> cq = cb.createQuery(ContenedorRegistro.class);
	    Root<ContenedorRegistro> rp = cq.from(ContenedorRegistro.class);
	    ParameterExpression<String> p = cb.parameter(String.class, "usuario");
	    cq.select(rp).where(cb.equal(rp.get("usuario"), p));

	    cont = session.createQuery(cq).setParameter("usuario", usuario).getSingleResult();
	    tx.commit();

	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}

	paquete.setUsername(cont.getUsuario());
	paquete.setPassword(cont.getPassword());
	paquete.setIdPj(cont.getIdPersonaje());
	Servidor.log.append("Se encontró al usuario " + cont.getUsuario() + " con personaje " + cont.getIdPersonaje()
		+ System.lineSeparator());

	return paquete;
    }

    /**
     * Verifica que exista un usuario determinado en la base de datos.
     * @param user Usuario a verificar.
     * @return devuelve true si el usuario existe en la base de datos.
     */
    public boolean loguearUsuario(final PaqueteUsuario user) {
	boolean pudeLoguear = false;

	try {
	    getUsuario(user.getUsername());
	    pudeLoguear = true;
	    Servidor.log.append("El usuario " + user.getUsername() + " ha iniciado sesión." + System.lineSeparator());
	} catch (Exception e) {
	    e.printStackTrace();
	    Servidor.log.append("El usuario " + user.getUsername()
		    + " ha realizado un intento fallido de inicio de sesión." + System.lineSeparator());
	}

	return pudeLoguear;
    }

    /**
     * Elimina a un usuario del a base de datos.
     * @param user El usuario a eliminar.
     * @return devuelve true si se pudo eliminar.
     */
    public boolean eliminarUsuario(final PaqueteUsuario user) {
	boolean pudeEliminar = false;
	Session session = factory.openSession();
	ContenedorRegistro cont = new ContenedorRegistro();
	Transaction tx = session.beginTransaction();

	cont.setIdPersonaje(user.getIdPj());
	cont.setUsuario(user.getUsername());
	cont.setPassword(user.getPassword());

	try {
	    session.delete(cont);
	    tx.commit();
	    pudeEliminar = true;
	    Servidor.log.append(
		    "El usuario " + user.getUsername() + " ha sido eliminado con éxtio." + System.lineSeparator());
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}

	return pudeEliminar;
    }

    /**
     * Actualiza el inventario de un personaje de la base de datos.
     * @param paquetePersonaje Personaje al cual se le actualizará el
     *            inventario.
     */
    public void actualizarInventario(final PaquetePersonaje paquetePersonaje) {
	Session session = factory.openSession();
	// ContenedorMochila cont = new ContenedorMochila();
	Transaction tx = session.beginTransaction();
	int cantItems = paquetePersonaje.getCantItems();
	int[] vectorItems = new int[MAX_CANT_ITEMS];

	for (int i = 0; i < cantItems; i++) {
	    vectorItems[i] = paquetePersonaje.getItemID(i);
	}
	for (int i = cantItems; i < MAX_CANT_ITEMS; i++) {
	    vectorItems[i] = -1;
	}

	Servidor.log.append("Actualizando inventario del pj " + paquetePersonaje.getId() + System.lineSeparator());
	for (int i = 0; i < cantItems; i++) {
	    vectorItems[i] = paquetePersonaje.getItemID(i);
	}

	try {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaUpdate<ContenedorMochila> cu = cb.createCriteriaUpdate(ContenedorMochila.class);
	    Root<ContenedorMochila> root = cu.from(ContenedorMochila.class);

	    for (int i = 0; i < MAX_CANT_ITEMS; i++) {
		cu.set(root.get("item" + (i + 1)), vectorItems[i]);
	    }

	    cu.where(cb.equal(root.get("idMochila"), paquetePersonaje.getId()));
	    session.createQuery(cu).executeUpdate();
	    tx.commit();
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}
    }

    /**
     * Le agrega un item aleatorio a un personaje.
     * @param idPersonaje Personaje al cual se le agregará el item.
     */
    public void actualizarInventario(final int idPersonaje) {
	PaquetePersonaje paquetePersonaje = Servidor.getPersonajesConectados().get(idPersonaje);
	Session session = factory.openSession();
	// ContenedorMochila cont = new ContenedorMochila();
	Transaction tx = session.beginTransaction();
	int cantItems = paquetePersonaje.getCantItems();
	int[] vectorItems = new int[MAX_CANT_ITEMS];

	Servidor.log.append("Actualizando inventario del pj " + paquetePersonaje.getId() + System.lineSeparator());
	for (int i = 0; i < cantItems; i++) {
	    vectorItems[i] = paquetePersonaje.getItemID(i);
	}
	for (int i = cantItems; i < MAX_CANT_ITEMS; i++) {
	    vectorItems[i] = -1;
	}

	// le agrego un item aleatorio
	if (paquetePersonaje.getCantItems() < CANT_ITEMS_POSIBLES) {
	    int itemGanado = new MyRandom().rangoInt(1, ULTIMO_ID_ITEM);
	    vectorItems[cantItems] = itemGanado;
	    cantItems++;
	}

	for (int i = 0; i < cantItems; i++) {
	    Servidor.log.append("Item " + (i + 1) + ": " + vectorItems[i] + System.lineSeparator());
	}

	try {
	    CriteriaBuilder cb = session.getCriteriaBuilder();
	    CriteriaUpdate<ContenedorMochila> cu = cb.createCriteriaUpdate(ContenedorMochila.class);
	    Root<ContenedorMochila> root = cu.from(ContenedorMochila.class);

	    for (int i = 0; i < MAX_CANT_ITEMS; i++) {
		cu.set(root.get("item" + (i + 1)), vectorItems[i]);
	    }

	    cu.where(cb.equal(root.get("idMochila"), paquetePersonaje.getId()));
	    session.createQuery(cu).executeUpdate();
	    tx.commit();
	} catch (HibernateException e) {
	    e.printStackTrace();
	    tx.rollback();
	} finally {
	    session.close();
	}
    }

    /**
     * Actualiza un personaje. Solo actualiza sus atributos por lo que debe
     * emplearse únicamente para actualizarlo cuando sube de nivel.
     * @param personaje Personaje al cual se le actualizaran los atributos.
     * @return devuelve true si se pudo actualizar el personaje.
     */
    public boolean actualizarPersonajeSubioNivel(final PaquetePersonaje personaje) {
	boolean pudeActualizar = false;
	ContenedorPersonaje contPer = new ContenedorPersonaje();
	Session session = factory.openSession();
	Transaction tx = session.beginTransaction();

	contPer.setIdPersonaje(personaje.getId());
	contPer.setNombre(personaje.getNombre());
	contPer.setRaza(personaje.getRaza());
	contPer.setCasta(personaje.getCasta());
	contPer.setFuerza(personaje.getFuerza());
	contPer.setDestreza(personaje.getDestreza());
	contPer.setInteligencia(personaje.getInteligencia());
	contPer.setSaludTope(personaje.getSaludTope());
	contPer.setEnergiaTope(personaje.getEnergiaTope());
	contPer.setExperiencia(personaje.getExperiencia());
	contPer.setNivel(personaje.getNivel());
	contPer.setPtsSkill(personaje.getPuntosSkill());

	Servidor.log.append("Salud tope " + personaje.getSaludTope() + " Energia tope " + personaje.getEnergiaTope()
		+ System.lineSeparator());
	Servidor.log.append("Fuerza " + personaje.getFuerza() + " Destreza " + personaje.getDestreza()
		+ " Inteligencia " + personaje.getInteligencia() + System.lineSeparator());

	try {
	    session.update(contPer);
	    tx.commit();
	    pudeActualizar = true;
	    Servidor.log.append(
		    "El personaje " + personaje.getId() + " se ha actualizado con éxito." + System.lineSeparator());
	} catch (HibernateException e) {
	    tx.rollback();
	    e.printStackTrace();
	} finally {
	    session.close();
	}

	return pudeActualizar;
    }

}
