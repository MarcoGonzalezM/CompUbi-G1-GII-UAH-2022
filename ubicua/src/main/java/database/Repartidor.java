
package database;

public class Repartidor {
    private String nombre;
    private String password;
    private int id_repartidor;

    public Repartidor() {
        this.nombre = null;
        this.password = null;
        this.id_repartidor = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId_repartidor() {
        return id_repartidor;
    }

    public void setId_repartidor(int id_repartidor) {
        this.id_repartidor = id_repartidor;
    }


    
}
