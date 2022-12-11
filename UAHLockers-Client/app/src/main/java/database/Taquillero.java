package database;

public class Taquillero {

    private int id_taquillero;
    private String pais;
    private String ciudad;
    private int codigo_postal;
    private String calle;
    private String numero;

    public Taquillero() {
        this.id_taquillero = 0;
        this.pais = null;
        this.ciudad = null;
        this.codigo_postal = 0;
        this.calle = null;
        this.numero = null;
    }

    public int getId_taquillero() {
        return id_taquillero;
    }

    public void setId_taquillero(int id_taquillero) {
        this.id_taquillero = id_taquillero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }


}
