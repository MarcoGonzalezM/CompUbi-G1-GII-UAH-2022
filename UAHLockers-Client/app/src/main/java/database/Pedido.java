package database;


public class Pedido {

    private int id_pedido;
    private int id_cliente;
    private int id_repartidor;
    private int taquilla;
    private int taquillero;
    private String estado_entrega;
    private String codigo;
    private String nombre_producto;

    public Pedido() {
        this.id_pedido = 0;
        this.id_cliente = 0;
        this.id_repartidor = 0;
        this.taquilla = 0;
        this.taquillero = 0;
        this.estado_entrega = null;
        this.codigo = null;
        this.nombre_producto = null;
    }

    public int getTaquillero() {
        return taquillero;
    }

    public void setTaquillero(int taquillero) {
        this.taquillero = taquillero;
    }

    public String getEstado_entrega() {
        return estado_entrega;
    }

    public void setEstado_entrega(String estado_entrega) {
        this.estado_entrega = estado_entrega;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(int taquilla) {
        this.taquilla = taquilla;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(int id_repartidor) {
        this.id_repartidor = id_repartidor;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre) {
        this.nombre_producto = nombre;
    }

}