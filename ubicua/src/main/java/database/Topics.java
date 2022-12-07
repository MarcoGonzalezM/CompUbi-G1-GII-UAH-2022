package database;

public class Topics 
{
    private String idTopic;
    private int idTaquillero;
    private int idTaquilla;
    private int clave;
    private String accion;
    private String estado;
    private String hay_paquete;
    private String value;
 
    // constructors
    public Topics() 
    {
    	this.idTopic = null;
    	this.idTaquillero = -1;
    	this.idTaquilla = -1;
    	this.clave = -1;
    	this.accion = null;
        this.estado = null;
        this.hay_paquete = null;
    	this.setValue(null);
    }

    public Topics(String idTopic, int idTaquillero, int idTaquilla, int clave, String abrir, String estado, String hay_paquete, String value) {
        this.idTopic = idTopic;
        this.idTaquillero = idTaquillero;
        this.clave = clave;
        this.idTaquilla = idTaquilla;
        this.accion = abrir;
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

    public int getIdTaquillero() {
        return idTaquillero;
    }

    public void setIdTaquillero(int idTaquillero) {
        this.idTaquillero = idTaquillero;
    }

    public int getIdTaquilla() {
        return idTaquilla;
    }

    public void setIdTaquilla(int idTaquilla) {
        this.idTaquilla = idTaquilla;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getAccion() {
        return accion;
    }

    public void setAbrir(String abrir) {
        this.accion = abrir;
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
