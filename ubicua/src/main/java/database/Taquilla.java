
package database;

public class Taquilla {
    private int id_taquilla;
    private int id_taquillero;
    private boolean ocupado;
    private int estado_apertura;

    public Taquilla(int id_taquilla, int id_taquillero, boolean ocupado) {
        this.id_taquilla = id_taquilla;
        this.id_taquillero = id_taquillero;
        this.ocupado = ocupado;
        this.estado_apertura = 0;
    }
    
    public Taquilla() {
        this.id_taquilla = 0;
        this.id_taquillero = 0;
        this.ocupado = false;
    }

    public int getId_taquilla() {
        return id_taquilla;
    }

    public void setId_taquilla(int id_taquilla) {
        this.id_taquilla = id_taquilla;
    }

    public int getId_taquillero() {
        return id_taquillero;
    }

    public void setId_taquillero(int id_taquillero) {
        this.id_taquillero = id_taquillero;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public int getEstado_apertura() {
        return estado_apertura;
    }

    public void setEstado_apertura(int estado_apertura) {
        this.estado_apertura = estado_apertura;
    }
    
}
