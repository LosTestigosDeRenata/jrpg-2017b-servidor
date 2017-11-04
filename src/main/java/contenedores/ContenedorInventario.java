package contenedores;

/**
 * Clase contenedor de la tabla Inventario para hibernate.
 * @author Santi
 *
 */
public class ContenedorInventario {
    private int idInventario;
    private int manos1;
    private int manos2;
    private int pie;
    private int cabeza;
    private int pecho;
    private int accesorio;

    /**
     * @return the idInventario
     */
    public int getIdInventario() {
	return idInventario;
    }

    /**
     * @param idInventario the idInventario to set
     */
    public void setIdInventario(final int idInventario) {
	this.idInventario = idInventario;
    }

    /**
     * @return the manos1
     */
    public int getManos1() {
	return manos1;
    }

    /**
     * @param manos1 the manos1 to set
     */
    public void setManos1(final int manos1) {
	this.manos1 = manos1;
    }

    /**
     * @return the manos2
     */
    public int getManos2() {
	return manos2;
    }

    /**
     * @param manos2 the manos2 to set
     */
    public void setManos2(final int manos2) {
	this.manos2 = manos2;
    }

    /**
     * @return the pie
     */
    public int getPie() {
	return pie;
    }

    /**
     * @param pie the pie to set
     */
    public void setPie(final int pie) {
	this.pie = pie;
    }

    /**
     * @return the cabeza
     */
    public int getCabeza() {
	return cabeza;
    }

    /**
     * @param cabeza the cabeza to set
     */
    public void setCabeza(final int cabeza) {
	this.cabeza = cabeza;
    }

    /**
     * @return the pecho
     */
    public int getPecho() {
	return pecho;
    }

    /**
     * @param pecho the pecho to set
     */
    public void setPecho(final int pecho) {
	this.pecho = pecho;
    }

    /**
     * @return the accesorio
     */
    public int getAccesorio() {
	return accesorio;
    }

    /**
     * @param accesorio the accesoria to set
     */
    public void setAccesorio(final int accesoria) {
	this.accesorio = accesoria;
    }

}
