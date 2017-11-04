package contenedores;

import java.io.IOException;

import dominio.Item;

public class ContenedorItem {
    private int idItem;
    private String nombre;
    private int wereable;
    private int bonusSalud;
    private int bonusEnergia;
    private int bonusFuerza;
    private int bonusDestreza;
    private int bonusInteligencia;
    private String foto;
    private String fotoEquipado;
    private int fuerzaRequerida;
    private int destrezaRequerida;
    private int inteligenciaRequerida;

    /**
     * @return the idItem
     */
    public int getIdItem() {
	return idItem;
    }

    /**
     * @param idItem the idItem to set
     */
    public void setIdItem(final int idItem) {
	this.idItem = idItem;
    }

    /**
     * @return the nomre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @param nomre the nomre to set
     */
    public void setNombre(final String nombre) {
	this.nombre = nombre;
    }

    /**
     * @return the wereable
     */
    public int getWereable() {
	return wereable;
    }

    /**
     * @param wereable the wereable to set
     */
    public void setWereable(final int wereable) {
	this.wereable = wereable;
    }

    /**
     * @return the bonusSalud
     */
    public int getBonusSalud() {
	return bonusSalud;
    }

    /**
     * @param bonusSalud the bonusSalud to set
     */
    public void setBonusSalud(final int bonusSalud) {
	this.bonusSalud = bonusSalud;
    }

    /**
     * @return the bonusEnergia
     */
    public int getBonusEnergia() {
	return bonusEnergia;
    }

    /**
     * @param bonusEnergia the bonusEnergia to set
     */
    public void setBonusEnergia(final int bonusEnergia) {
	this.bonusEnergia = bonusEnergia;
    }

    /**
     * @return the bonusFuerza
     */
    public int getBonusFuerza() {
	return bonusFuerza;
    }

    /**
     * @param bonusFuerza the bonusFuerza to set
     */
    public void setBonusFuerza(final int bonusFuerza) {
	this.bonusFuerza = bonusFuerza;
    }

    /**
     * @return the bonusDestreza
     */
    public int getBonusDestreza() {
	return bonusDestreza;
    }

    /**
     * @param bonusDestreza the bonusDestreza to set
     */
    public void setBonusDestreza(final int bonusDestreza) {
	this.bonusDestreza = bonusDestreza;
    }

    /**
     * @return the bonusInteligencia
     */
    public int getBonusInteligencia() {
	return bonusInteligencia;
    }

    /**
     * @param bonusInteligencia the bonusInteligencia to set
     */
    public void setBonusInteligencia(final int bonusInteligencia) {
	this.bonusInteligencia = bonusInteligencia;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
	return foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(final String foto) {
	this.foto = foto;
    }

    /**
     * @return the fotoEquipado
     */
    public String getFotoEquipado() {
	return fotoEquipado;
    }

    /**
     * @param fotoEquipado the fotoEquipado to set
     */
    public void setFotoEquipado(final String fotoEquipado) {
	this.fotoEquipado = fotoEquipado;
    }

    /**
     * @return the fuerzaRequerida
     */
    public int getFuerzaRequerida() {
	return fuerzaRequerida;
    }

    /**
     * @param fuerzaRequerida the fuerzaRequerida to set
     */
    public void setFuerzaRequerida(final int fuerzaRequerida) {
	this.fuerzaRequerida = fuerzaRequerida;
    }

    /**
     * @return the destrezaRequerida
     */
    public int getDestrezaRequerida() {
	return destrezaRequerida;
    }

    /**
     * @param destrezaRequerida the destrezaRequerida to set
     */
    public void setDestrezaRequerida(final int destrezaRequerida) {
	this.destrezaRequerida = destrezaRequerida;
    }

    /**
     * @return the inteligenciaRequerida
     */
    public int getInteligenciaRequerida() {
	return inteligenciaRequerida;
    }

    /**
     * @param inteligenciaRequerida the inteligenciaRequerida to set
     */
    public void setInteligenciaRequerida(final int inteligenciaRequerida) {
	this.inteligenciaRequerida = inteligenciaRequerida;
    }

    /**
     * Método que genera un objeto Item a partir de los datos del contenedor
     * @return devuelve una instancia de item generador con los datos del
     *         contenedor
     * @throws IOException
     */
    public Item getItem() {
	try {
	    return new Item(this.idItem, this.nombre, this.wereable, this.bonusSalud, this.bonusEnergia,
		    this.bonusFuerza, this.bonusDestreza, this.bonusInteligencia, this.foto, this.fotoEquipado);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	return null;
    }
}
