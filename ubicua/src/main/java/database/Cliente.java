package database;

public class Cliente {
    
    private String correo_cliente;
    private String password;

    public Cliente() {
        this.correo_cliente = null;
        this.password = null;
    }
    
    

    public String getCorreo_cliente() {
        return correo_cliente;
    }

    public void setCorreo_cliente(String correo_cliente) {
        this.correo_cliente = correo_cliente;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

      
    
}
