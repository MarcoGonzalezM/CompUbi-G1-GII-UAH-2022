
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;
import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;


@WebServlet(name = "entregarPaquete", urlPatterns = {"/entregarPaquete"})
public class entregarPaquete extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int estado_final =0;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try{
            String pedido = request.getParameter("id_pedido");
            int id_pedido = Integer.parseInt(pedido);
            
            String taquillero = request.getParameter("id_taquillero");
            int id_taquillero = Integer.parseInt(taquillero);
            
            String taquilla = request.getParameter("id_taquilla");
            int id_taquilla = Integer.parseInt(taquilla);
            
            String repartidor = request.getParameter("id_repartidor");
            int id_repartidor = Integer.parseInt(repartidor);

            MQTTBroker bkr = new MQTTBroker();
            MQTTPublisher.publish(bkr, "Taquillero" + taquillero + "/Taquilla" + taquilla + "/accion", "Estapaquete");
            Thread.sleep(2000);
            
            if(Logic.getEstadoTaquilla(id_taquilla, id_taquillero))
            {
                Logic.updatePedidoEstadoEntrega("entregado", id_pedido);
                Logic.updateTaquillaPedido(id_taquilla, id_pedido);
                Logic.updateRepartidor(id_repartidor, id_pedido);
                MQTTPublisher.publish(bkr, "Taquillero" + taquillero + "/Taquilla" + taquilla + "/accion", "Cerrar");
                estado_final=1;
            }
            
            else
            {
                MQTTPublisher.publish(bkr, "Taquillero" + taquillero + "/Taquilla" + taquilla + "/accion", "Abrir");
                estado_final=-1;
            }
            
            out.print(estado_final);
        }
        catch (NullPointerException e) {
            out.print("-1");
            Log.log.error("Exception: {}", e);
        } catch (Exception e) {
            out.println("EXCEPCION");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
