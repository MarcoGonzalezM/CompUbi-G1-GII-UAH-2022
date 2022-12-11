package database;


public class Pedido {
    
    private int id_pedido;
    private String cliente;
    private String repartidor;
    private int taquilla;
    private int taquillero;
    private String estado_entrega;
    private int estado_apertura;
    private String codigo;

    public Pedido() {
        this.id_pedido = 0;
        this.cliente = null;
        this.repartidor = null;
        this.taquilla = 0;
        this.taquillero = 0;
        this.estado_entrega = null;
        this.estado_apertura = 0;
        this.codigo = null;
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

    public int getEstado_apertura() {
        return estado_apertura;
    }

    public void setEstado_apertura(int estado_apertura) {
        this.estado_apertura = estado_apertura;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getRepartidor() {
        return repartidor;
    }

    public void setRepartidor(String repartidor) {
        this.repartidor = repartidor;
    }

    public int getTaquilla() {
        return taquilla;
    }

    public void setTaquilla(int taquilla) {
        this.taquilla = taquilla;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

}