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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static Repartidor getRepartidorDB(String nombre) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        Repartidor nuevoRepartidor = new Repartidor();
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.GetRepartidor(con);
            ps.setString(1, nombre);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                nuevoRepartidor.setNombre(rs.getString("nombre"));
                nuevoRepartidor.setPassword(rs.getString("password"));
                nuevoRepartidor.setId_repartidor(rs.getInt("id_repartidor"));
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
        return nuevoRepartidor;
    }

    public static ArrayList<Taquilla> getTaquillasFromTaquilleroDB(int taquillero) {
        ArrayList<Taquilla> taquillas = new ArrayList<Taquilla>();

        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.GetTaquillasFromTaquillero(con);
            ps.setInt(1, taquillero);
            ps.setBoolean(2, false);
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

    public static ArrayList<Recogida_autenticar> getRecogidaNotificaciones(int idCliente) {
        ArrayList<Recogida_autenticar> recogidas = new ArrayList<Recogida_autenticar>();

        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getRecogida_autenticar(con);
            Log.log.info("Query=> {}", ps.toString());

            ps.setInt(1, idCliente);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Recogida_autenticar notificacion = new Recogida_autenticar();
                notificacion.setDescripcion(rs.getString("descripcion"));
                notificacion.setId_pedido(rs.getInt("id_pedido_pedido"));
                notificacion.setId_recogida(rs.getInt("id_recogida"));
                notificacion.setRecogido(rs.getBoolean("recogido"));

                recogidas.add(notificacion);
            }
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            recogidas = new ArrayList<Recogida_autenticar>();
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            recogidas = new ArrayList<Recogida_autenticar>();
        } catch (Exception e) {
            Log.log.error("Error:{}", e);
            recogidas = new ArrayList<Recogida_autenticar>();
        } finally {
            conector.closeConnection(con);
        }
        return recogidas;
    }

    public static int pedirPaquetePrueba(int id_cliente, int taquillero) {
        int estado_final = -1;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            int id_pedido = getMaxIdPedido() + 1;

            PreparedStatement ps = ConectionDDBB.insertPedidoPrueba(con);
            ps.setInt(1, id_pedido);
            ps.setInt(2, id_cliente);
            ps.setInt(3, taquillero);
            String estado_entrega = "creado";
            ps.setString(4, estado_entrega);
            int clave = generarClave(taquillero);
            ps.setInt(5, clave);
            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

            estado_final = 1;
        } catch (SQLException e) {
            Log.log.error("Error: {}", e);
            estado_final = -1;
        } catch (NullPointerException e) {
            Log.log.error("Error: {}", e);
            estado_final = -1;
        } finally {
            conector.closeConnection(con);
        }
        return estado_final;
    }

    public static int generarClave(int taquillero) {
        ArrayList<Integer> claves = new ArrayList<Integer>();
        int numero = 0;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getClaves(con);
            ps.setInt(1, taquillero);
            String en_reparto = "en reparto";
            String entregado = "entregado";
            ps.setString(2, en_reparto);
            ps.setString(3, entregado);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int clave = 0;
                clave = rs.getInt("clave");
                claves.add(clave);
            }
            Random r = new Random();
            numero = r.nextInt(8999) + 1001;
            while (claves.contains(numero)) {
                numero = r.nextInt(8999) + 1001;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return numero;
    }

    public static int getMaxIdPedido() {
        int id = 0;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getMaxIdPedido(con);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                id = 1;
            } else {
                id = rs.getInt("max_id_pedido");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }

    public static int validarClaveTaquillero(int taquillero, String clave) {
        int taquilla = -1;

        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getClavePedido(con);

            ps.setInt(1, taquillero);

            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            String pedClave;
            while (rs.next()) {
                pedClave = rs.getString("codigo");
                if (pedClave.equals(clave)) {
                    taquilla = rs.getInt("id_taquilla_taquilla");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }

        return taquilla;
    }

    public static void insertRecogidaAutenticar(int taquillero, String clave) {
        int id_pedido = -1;
        int id_recogido = -1;
        String descripcion;
        boolean recogido = false;

        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            //Obtenemos el id del pedido correspondiente a la clave y taquillero
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getClavePedido(con);

            ps.setInt(1, taquillero);

            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            String pedClave;
            while (rs.next()) {
                pedClave = rs.getString("codigo");
                if (pedClave.equals(clave)) {
                    id_pedido = rs.getInt("id_pedido_pedido");
                }
            }

            //Verificamos si hay un pedido asociado
            if (id_pedido != -1) {
                //Buscamos el id maximo en Recogida_autenticar
                PreparedStatement ps1 = ConectionDDBB.getMaxIdRecogida_autenticar(con);
                ResultSet rs1 = ps.executeQuery();
                if (rs.next()) {
                    id_recogido = rs.getInt("max_id_recogida") + 1;
                } else {
                    id_recogido = 1;
                }

                //insertamos en Recogida_autenticar
                PreparedStatement insert = ConectionDDBB.insertRecogidaAutenticar(con);

                descripcion = "Mensaje de autenticacion del pedido" + id_pedido + "en el taquillero" + taquillero;

                ps.setInt(1, id_recogido);
                ps.setString(2, descripcion);
                ps.setInt(3, id_pedido);
                ps.setBoolean(4, recogido);

                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateRecogidaAutenticar(boolean recogido, int id_recogida) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            //Obtenemos el id del pedido correspondiente a la clave y taquillero
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.updateRecogidaAutenticar(con);

            ps.setBoolean(1, recogido);
            ps.setInt(2, id_recogida);

            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updatePedidoEstadoEntrega(String estado_entrega, int id_pedido) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            //Obtenemos el id del pedido correspondiente a la clave y taquillero
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.updatePedidoEstadoEntrega(con);

            ps.setString(1, estado_entrega);
            ps.setInt(2, id_pedido);

            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static int getTaquillaPedido(int id_pedido) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        int taquilla = -1;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getPedidoTaquillaTaquillero(con);

            ps.setInt(1, id_pedido);

            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                taquilla = rs.getInt("id_taquilla_taquilla");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }

        return taquilla;
    }

    public static int getTaquilleroPedido(int id_pedido) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        int taquillero = -1;

        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getPedidoTaquillaTaquillero(con);

            ps.setInt(1, id_pedido);

            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                taquillero = rs.getInt("id_taquillero_taquillero_taquilla");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }

        return taquillero;
    }

    public static ArrayList<Pedido> getPedidosEstadoEntrega() {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getPedidosEstadoEntrega(con);
            ps.setString(1, "creado");
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setTaquillero(rs.getInt("id_taquillero_taquillero_taquilla"));

                pedidos.add(pedido);
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
        return pedidos;
    }

    public static int asignarPedidoaRepartidor(int id_pedido, int id_repartidor) {
        int estado_final = 0;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.UpdatePedido(con);
            ps.setString(1, "en reparto");
            ps.setInt(2, id_repartidor);
            ps.setInt(3, id_pedido);
            Log.log.info("Query=> {}", ps.toString());
            int row = ps.executeUpdate();
            if (row == 1) {
                estado_final = 1;
            } else {
                estado_final = -1;
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
        return estado_final;
    }

    public static ArrayList<Pedido> getPedidosRepartidor(int id_repartidor) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getPedidosRepartidor(con);
            ps.setInt(1, id_repartidor);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setTaquillero(rs.getInt("id_taquillero_taquillero_taquilla"));

                pedidos.add(pedido);
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
        return pedidos;
    }

    public static void updateTaquillaPedido(String id_taquilla, int id_pedido) {
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            //Obtenemos el id del pedido correspondiente a la clave y taquillero
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.updateTaquillaPedido(con);

            ps.setString(1, id_taquilla);
            ps.setInt(2, id_pedido);

            Log.log.info("Query=> {}", ps.toString());
            ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean getEstadoTaquilla(int id_taquilla, int id_taquillero) {
        boolean ocupado = false;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;

        try {
            //Obtenemos el id del pedido correspondiente a la clave y taquillero
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");
            PreparedStatement ps = ConectionDDBB.getEstadoTaquilla(con);

            ps.setInt(1, id_taquilla);
            ps.setInt(2, id_taquillero);

            Log.log.info("Query=> {}", ps.toString());
            ps.executeQuery();

        } catch (SQLException ex) {
            Logger.getLogger(Logic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ocupado;
    }

    public static ArrayList<Pedido> getPedidosCliente(int id_cliente) {
        ArrayList<Pedido> pedidos = new ArrayList<>();
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getPedidosCliente(con);
            ps.setInt(1, id_cliente);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Pedido pedido = new Pedido();

                pedido.setId_pedido(rs.getInt("id_pedido"));
                pedido.setId_cliente(rs.getInt("id_cliente_cliente"));
                pedido.setTaquillero(rs.getInt("id_taquillero_taquillero_taquilla"));
                pedido.setCodigo(rs.getString("codigo"));
                pedido.setEstado_entrega(rs.getString("estado_entrega"));
                pedido.setTaquilla(rs.getInt("id_taquilla_taquilla"));
                pedido.setNombre_producto(rs.getString("nombre_producto"));

                pedidos.add(pedido);
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
        return pedidos;
    }
    
    public static int getRecogidaNotificacionPedido(int id_pedido)
    {
        int id_recogida = -1;
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getRecogidaNotificacionIdPedido(con);
            ps.setInt(1, id_pedido);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id_recogida = rs.getInt("id_recogida");
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
        return id_recogida;
    }
    
    public static int getRecogidaNotificacionIDPedido(int id_recogida)
    {
        int id_pedido = -1;
        
        ConectionDDBB conector = new ConectionDDBB();
        Connection con = null;
        try {
            con = conector.obtainConnection(true);
            Log.log.debug("Database Connected");

            PreparedStatement ps = ConectionDDBB.getRecogidaNotificacionIdRecogida(con);
            ps.setInt(1, id_pedido);
            Log.log.info("Query=> {}", ps.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id_recogida = rs.getInt("id_pedido_pedido");
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
        return id_pedido;
        
    }
}
