package contenedores;

/**
 * Clase contenedor de la tabla Alumno para hibernate.
 * @author Santi
 *
 */
public class ContenedorAlumno {
    private int idAlumno;
    private String nombre;
    private int edad;

    /**
     * @return the idAlumno
     */
    public int getIdAlumno() {
	return idAlumno;
    }

    /**
     * @param idAlumno the idAlumno to set
     */
    public void setIdAlumno(final int idAlumno) {
	this.idAlumno = idAlumno;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(final String nombre) {
	this.nombre = nombre;
    }

    /**
     * @return the edad
     */
    public int getEdad() {
	return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(final int edad) {
	this.edad = edad;
    }

}
