package contenedores;

public class ContenedorPersonaje {
    private int idPersonaje;
    private int idInventario;
    private int idMochila;
    private String casta;
    private String raza;
    private int fuerza;
    private int destreza;
    private int inteligencia;
    private int saludTope;
    private int energiaTope;
    private String nombre;
    private int experiencia;
    private int nivel;
    private int idAlianza;
    private int ptsSkill;

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
     * @return the idMochila
     */
    public int getIdMochila() {
	return idMochila;
    }

    /**
     * @param idMochila the idMochila to set
     */
    public void setIdMochila(final int idMochila) {
	this.idMochila = idMochila;
    }

    /**
     * @return the casta
     */
    public String getCasta() {
	return casta;
    }

    /**
     * @param casta the casta to set
     */
    public void setCasta(final String casta) {
	this.casta = casta;
    }

    /**
     * @return the raza
     */
    public String getRaza() {
	return raza;
    }

    /**
     * @param raza the raza to set
     */
    public void setRaza(final String raza) {
	this.raza = raza;
    }

    /**
     * @return the fuerza
     */
    public int getFuerza() {
	return fuerza;
    }

    /**
     * @param fuerza the fuerza to set
     */
    public void setFuerza(final int fuerza) {
	this.fuerza = fuerza;
    }

    /**
     * @return the destreza
     */
    public int getDestreza() {
	return destreza;
    }

    /**
     * @param destreza the destreza to set
     */
    public void setDestreza(final int destreza) {
	this.destreza = destreza;
    }

    /**
     * @return the inteligencia
     */
    public int getInteligencia() {
	return inteligencia;
    }

    /**
     * @param inteligencia the inteligencia to set
     */
    public void setInteligencia(final int inteligencia) {
	this.inteligencia = inteligencia;
    }

    /**
     * @return the saludTope
     */
    public int getSaludTope() {
	return saludTope;
    }

    /**
     * @param saludTope the saludTope to set
     */
    public void setSaludTope(final int saludTope) {
	this.saludTope = saludTope;
    }

    /**
     * @return the energiaTope
     */
    public int getEnergiaTope() {
	return energiaTope;
    }

    /**
     * @param energiaTope the energiaTope to set
     */
    public void setEnergiaTope(final int energiaTope) {
	this.energiaTope = energiaTope;
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
     * @return the experiencia
     */
    public int getExperiencia() {
	return experiencia;
    }

    /**
     * @param experiencia the experiencia to set
     */
    public void setExperiencia(final int experiencia) {
	this.experiencia = experiencia;
    }

    /**
     * @return the nivel
     */
    public int getNivel() {
	return nivel;
    }

    /**
     * @param nivel the nivel to set
     */
    public void setNivel(final int nivel) {
	this.nivel = nivel;
    }

    /**
     * @return the idAlianza
     */
    public int getIdAlianza() {
	return idAlianza;
    }

    /**
     * @param idAlianza the idAlianza to set
     */
    public void setIdAlianza(final int idAlianza) {
	this.idAlianza = idAlianza;
    }

    /**
     * @return the ptsSkill
     */
    public int getPtsSkill() {
	return ptsSkill;
    }

    /**
     * @param ptsSkill the ptsSkill to set
     */
    public void setPtsSkill(final int ptsSkill) {
	this.ptsSkill = ptsSkill;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return "ContenedorPersonaje [idPersonaje=" + idPersonaje + ", idInventario=" + idInventario + ", idMochila="
		+ idMochila + ", casta=" + casta + ", raza=" + raza + ", fuerza=" + fuerza + ", destreza=" + destreza
		+ ", inteligencia=" + inteligencia + ", saludTope=" + saludTope + ", energiaTope=" + energiaTope
		+ ", nombre=" + nombre + ", experiencia=" + experiencia + ", nivel=" + nivel + ", idAlianza="
		+ idAlianza + ", ptsSkill=" + ptsSkill + "]";
    }
}
