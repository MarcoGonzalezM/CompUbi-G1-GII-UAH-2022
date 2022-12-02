
package database;

public class Recogida_autenticar {

    private int id_notificacion;
    private String titulo;
    private String descripcion;
    private int id_pedido;
    private boolean recogido;

    public Recogida_autenticar() {
        this.id_notificacion = 0;
        this.titulo = null;
        this.descripcion = null;
        this.id_pedido = 0;
        this.recogido = false;
    }
    

    public boolean getRecogido() {
        return recogido;
    }

    public void setRecogido(boolean recogido) {
        this.recogido = recogido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getId_notificacion() {
        return id_notificacion;
    }

    public void setId_notificacion(int id_notificacion) {
        this.id_notificacion = id_notificacion;
    }

    
}
