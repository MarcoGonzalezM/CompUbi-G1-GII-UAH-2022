
package database;

public class Recogida_autenticar {

    private int id_recogida;
    private String descripcion;
    private int id_pedido;
    private boolean recogido;

    public Recogida_autenticar() {
        this.id_recogida = 0;
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

    public int getId_recogida() {
        return id_recogida;
    }

    public void setId_recogida(int id_recogida) {
        this.id_recogida = id_recogida;
    }

}
