package database;

public class Cliente {

    private String nombre;
    private String password;
    private int id_cliente;

    public Cliente() {
        this.nombre = null;
        this.password = null;
        this.id_cliente = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
