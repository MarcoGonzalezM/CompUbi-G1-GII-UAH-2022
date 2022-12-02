
package database;

public class Repartidor {
    private String correo_repartidor;
    private String password;

    public Repartidor(String correo_repartidor, String password) {
        this.correo_repartidor = null;
        this.password = null;
    }

    public String getCorreo_repartidor() {
        return correo_repartidor;
    }

    public void setCorreo_repartidor(String correo_repartidor) {
        this.correo_repartidor = correo_repartidor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
