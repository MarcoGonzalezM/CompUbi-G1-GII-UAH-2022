
package database;

public class Taquilla {
    private int id_taquilla;
    private int id_taquillero;
    private boolean ocupado;

    public Taquilla(int id_taquilla, int id_taquillero, boolean ocupado) {
        this.id_taquilla = id_taquilla;
        this.id_taquillero = id_taquillero;
        this.ocupado = ocupado;
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
    
    
}
