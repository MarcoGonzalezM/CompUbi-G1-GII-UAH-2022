package logic;

import java.util.ArrayList;

import database.Cliente;
import database.Recogida_autenticar;
import database.Pedido;
import database.Repartidor;
import database.Taquilla;
import database.Taquillero;
import database.ConectionDDBB;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logic {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Cliente getUsuarioDB(String nombre) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        Cliente nuevoCliente = new Cliente();
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.GetCliente(con);
            ps.setString(1, nombre);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nuevoCliente.setNombre(rs.getString("nombre"));
                nuevoCliente.setPassword(rs.getString("password"));
                nuevoCliente.setId_cliente(rs.getInt("id_cliente"));
            }

        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
        return nuevoCliente;
    }

}
