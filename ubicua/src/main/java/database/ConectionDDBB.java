package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import logic.Log;

public class ConectionDDBB {

    public Connection obtainConnection(boolean autoCommit) throws NullPointerException {
        Connection con = null;
        int intentos = 5;
        for (int i = 0; i < intentos; i++) {
            Log.logdb.info("Attempt {} to connect to the database", i);
            try {
                Context ctx = new InitialContext();
                // Get the connection factory configured in Tomcat
                DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/postgres");

                // Obtiene una conexion
                con = ds.getConnection();
                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                Log.logdb.debug("Connection creation. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.setAutoCommit(autoCommit);
                Log.logdb.info("Conection obtained in the attempt: " + i);
                i = intentos;
            } catch (NamingException ex) {
                Log.logdb.error("Error getting connection while trying: {} = {}", i, ex);
            } catch (SQLException ex) {
                Log.logdb.error("ERROR sql getting connection while trying:{ }= {}", i, ex);
                throw (new NullPointerException("SQL connection is null"));
            }
        }
        return con;
    }

    public void closeTransaction(Connection con) {
        try {
            con.commit();
            Log.logdb.debug("Transaction closed");
        } catch (SQLException ex) {
            Log.logdb.error("Error closing the transaction: {}", ex);
        }
    }

    public void cancelTransaction(Connection con) {
        try {
            con.rollback();
            Log.logdb.debug("Transaction canceled");
        } catch (SQLException ex) {
            Log.logdb.error("ERROR sql when canceling the transation: {}", ex);
        }
    }

    public void closeConnection(Connection con) {
        try {
            Log.logdb.info("Closing the connection");
            if (null != con) {
                Calendar calendar = Calendar.getInstance();
                java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
                Log.logdb.debug("Connection closed. Bd connection identifier: {} obtained in {}", con.toString(), date.toString());
                con.close();
            }

            Log.logdb.info("The connection has been closed");
        } catch (SQLException e) {
            Log.logdb.error("ERROR sql closing the connection: {}", e);
            e.printStackTrace();
        }
    }

    public static PreparedStatement getStatement(Connection con, String sql) {
        PreparedStatement ps = null;
        try {
            if (con != null) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            }
        } catch (SQLException ex) {
            Log.logdb.warn("ERROR sql creating PreparedStatement:{} ", ex);
        }

        return ps;
    }

    //************** CALLS TO THE DATABASE ***************************//
    //Statements PARA NUESTRO PROYECTO
    public static PreparedStatement GetCliente(Connection con) {
        return getStatement(con, "SELECT * FROM CLIENTE WHERE nombre= ?;");
    }

    public static PreparedStatement GetRepartidor(Connection con) {
        return getStatement(con, "SELECT * FROM repartidor WHERE nombre= ?;");
    }

    public static PreparedStatement GetTaquillasFromTaquillero(Connection con) {
        return getStatement(con, "SELECT id_taquilla FROM TAQUILLA WHERE id_taquillero_taquillero= ? AND ocupado=?");
    }

    public static PreparedStatement GetTaquilleros(Connection con) {
        return getStatement(con, "SELECT * FROM TAQUILLERO");
    }

    public static PreparedStatement UpdateOcupadoTaquilla(Connection con) {
        return getStatement(con, "UPDATE Taquilla SET ocupado = ? where id_taquilla = ? AND id_taquillero_taquillero = ?");
    }

    public static PreparedStatement UpdateEstadoTaquilla(Connection con) {
        return getStatement(con, "UPDATE Taquilla SET estado_apertura = ? where id_taquilla = ? AND id_taquillero_taquillero = ?");
    }
    
    public static PreparedStatement getRecogida_autenticar(Connection con) {
        return getStatement(con, "select id_recogida,descripcion,id_pedido_pedido,recogido,id_cliente_cliente from recogida_autenticar inner join pedido ON pedido.id_pedido = recogida_autenticar.id_pedido_pedido where (pedido.id_cliente_cliente = ?)");
    }
    
    public static PreparedStatement getClaves(Connection con) {
        return getStatement(con, "SELECT codigo FROM taquillero NATURAL JOIN taquilla NATURAL JOIN pedido WHERE taquillero.id_taquillero = ? AND (pedido.estado_entrega = ? OR pedido.estado_entrega = ?)");
    }
    
    public static PreparedStatement getMaxIdPedido(Connection con) {
        return getStatement(con, "SELECT MAX(id_pedido) max_id_pedido FROM pedido");
    }
    
    public static PreparedStatement insertPedidoPrueba(Connection con) {
            return getStatement(con, "INSERT INTO pedido (id_pedido, id_cliente_cliente, id_taquillero_taquillero_taquilla, estado_entrega, codigo, nombre_producto) values (?,?,?,?,?,?)");
    }
    
    public static PreparedStatement getPedidosEstadoEntrega(Connection con) {
            return getStatement(con, "SELECT id_pedido,id_cliente_cliente,id_taquillero_taquillero_taquilla FROM pedido WHERE estado_entrega=?;");
    }
    
    public static PreparedStatement UpdatePedido(Connection con) {
        return getStatement(con, "UPDATE pedido SET estado_entrega = ?, id_cliente_repartidor=? where id_pedido = ?");
    }
    
    public static PreparedStatement getClavePedido(Connection con){
        return getStatement(con, "SELECT codigo, id_taquilla_taquilla, id_pedido FROM pedido WHERE id_taquillero_taquillero_taquilla = ? AND estado_entrega = 'entregado'");
    }
    
    public static PreparedStatement getMaxIdRecogida_autenticar(Connection con){
        return getStatement(con, "SELECT MAX(id_recogida) max_id_recogida FROM recogida_autenticar");
    }
    
    public static PreparedStatement insertRecogidaAutenticar(Connection con){
        return getStatement(con, "INSERT INTO recogida_autenticar (id_recogida, descripcion, id_pedido_pedido, recogido) values(?, ?, ?, ?)");
    }
    
    public static PreparedStatement updateRecogidaAutenticar(Connection con){
        return getStatement(con, "UPDATE recogida_autenticar SET recogido = ? where id_recogida = ?");
    }
    
    public static PreparedStatement updatePedidoEstadoEntrega(Connection con){
        return getStatement(con, "UPDATE pedido SET estado_entrega = ? where id_pedido = ?");
    }
    
    public static PreparedStatement getPedidoTaquillaTaquillero(Connection con){
        return getStatement(con, "SELECT id_taquilla_taquilla, id_taquillero_taquillero_taquilla FROM pedido where id_pedido = ?");
    }
    
    public static PreparedStatement getPedidosRepartidor(Connection con) {
            return getStatement(con, "SELECT id_pedido,id_cliente,id_taquillero_taquillero_taquilla FROM pedido WHERE id_repartidor=?;");
    }
    
    public static PreparedStatement updateTaquillaPedido(Connection con){
        return getStatement(con, "UPDATE pedido SET id_taquilla_taquilla = ? WHERE id_pedido=?;");
    }
    
    public static PreparedStatement getEstadoTaquilla(Connection con){
        return getStatement(con, "SELECT ocupado FROM taquilla WHERE id_taquillero_taquillero=? AND id_taquilla=?;");
    }
    
    public static PreparedStatement getPedidosCliente(Connection con) {
        return getStatement(con, "SELECT * FROM pedido where id_cliente_cliente=? and estado_entrega!='recogido'");
    }
    
    public static PreparedStatement getDatosPedido(Connection con) {
        return getStatement(con, "SELECT * from pedido where id_pedido=?");
    }
    
    public static PreparedStatement getRecogidaNotificacionIdPedido(Connection con){
        return getStatement(con, "SELECT id_recogida FROM recogida_autenticar WHERE id_pedido_pedido = ?");
    }
    
    public static PreparedStatement getRecogidaNotificacionIdRecogida(Connection con){
        return getStatement(con, "SELECT id_pedido_pedido FROM recogida_autenticar WHERE id_recogida = ?");
    }
    
    public static PreparedStatement getTaquillero(Connection con) {
        return getStatement(con, "SELECT id_taquillero_taquillero_taquilla FROM pedido WHERE id_pedido=?");
    }
}
