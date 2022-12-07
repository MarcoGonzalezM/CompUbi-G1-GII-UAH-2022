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

    public static ArrayList<Taquilla> getTaquillasFromTaquilleroDB() {
        ArrayList<Taquilla> taquillas = new ArrayList<Taquilla>();

        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.GetTaquillasFromTaquillero(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Taquilla taquilla = new Taquilla();
                taquilla.setId_taquillero(rs.getInt("id_taquilla"));
                taquillas.add(taquilla);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            taquillas = new ArrayList<Taquilla>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            taquillas = new ArrayList<Taquilla>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            taquillas = new ArrayList<Taquilla>();
        } finally {
            conector.closeConnection(con);
        }
        return taquillas;
    }

    public static void updateOcupadoTaquilla(int idTaquillero, int idTaquilla, boolean ocupado) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.UpdateOcupadoTaquilla(con);

            ps.setBoolean(1, ocupado);
            ps.setInt(2, idTaquilla);
            ps.setInt(3, idTaquillero);

            Log.log.info("Query=> {}", ps.toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }

    public static void updateEstadoTaquilla(int idTaquillero, int idTaquilla, int estado) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.UpdateEstadoTaquilla(con);

            ps.setInt(1, estado);
            ps.setInt(2, idTaquilla);
            ps.setInt(3, idTaquillero);

            Log.log.info("Query=> {}", ps.toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
        } finally {
            conector.closeConnection(con);
        }
    }

    public static ArrayList<Taquillero> getTaquilleros() {
        ArrayList<Taquillero> taquilleros = new ArrayList<>();
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.GetTaquilleros(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Taquillero tllero = new Taquillero();

                tllero.setCalle(rs.getString("calle"));
                tllero.setCiudad(rs.getString("ciudad"));
                tllero.setCodigo_postal(rs.getInt("codigo_postal"));
                tllero.setId_taquillero(rs.getInt("id_taquillero"));
                tllero.setNumero(rs.getString("numero"));
                tllero.setPais(rs.getString("pais"));

                taquilleros.add(tllero);
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
        return taquilleros;
    }

}
