package contenedores;

/**
 * Clase contenedor de la tabla Registro para hibernate.
 *
 */
public class ContenedorRegistro {
    private String usuario;
    private String password;
    private int idPersonaje;

    /**
     * @return the usuario
     */
    public String getUsuario() {
	return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(final String usuario) {
	this.usuario = usuario;
    }

    /**
     * @return the password
     */
    public String getPassword() {
	return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(final String password) {
	this.password = password;
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
}
