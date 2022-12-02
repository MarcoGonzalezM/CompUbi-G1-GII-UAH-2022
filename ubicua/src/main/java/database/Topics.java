package database;

public class Topics 
{
    private String idTopic;
    private String idTaquillero;
    private String idTaquilla;
    private String clave;
    private String abrir;
    private String estado;
    private String hay_paquete;
    private String value;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idTaquillero = null;
    	this.idTaquilla = null;
    	this.clave = null;
    	this.abrir = null;
        this.estado = null;
        this.hay_paquete = null;
    	this.setValue(null);
    }

    public Topics(String idTopic, String idTaquillero, String idTaquilla, String clave, String abrir, String estado, String hay_paquete, String value) {
        this.idTopic = idTopic;
        this.idTaquillero = idTaquillero;
        this.idTaquilla = idTaquilla;
        this.clave = clave;
        this.abrir = abrir;
        this.estado = estado;
        this.hay_paquete = hay_paquete;
        this.value = value;
    }

    public String getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(String idTopic) {
        this.idTopic = idTopic;
    }

    public String getIdTaquillero() {
        return idTaquillero;
    }

    public void setIdTaquillero(String idTaquillero) {
        this.idTaquillero = idTaquillero;
    }

    public String getIdTaquilla() {
        return idTaquilla;
    }

    public void setIdTaquilla(String idTaquilla) {
        this.idTaquilla = idTaquilla;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getAbrir() {
        return abrir;
    }

    public void setAbrir(String abrir) {
        this.abrir = abrir;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHay_paquete() {
        return hay_paquete;
    }

    public void setHay_paquete(String hay_paquete) {
        this.hay_paquete = hay_paquete;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
 }
