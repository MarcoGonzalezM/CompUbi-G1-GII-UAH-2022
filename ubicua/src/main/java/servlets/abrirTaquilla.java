package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.nio.charset.StandardCharsets;
import logic.Log;
import logic.Logic;

import mqtt.MQTTBroker;
import mqtt.MQTTPublisher;

/**
 *
 * @author Italo Joel Sandoval Amoretti
 */
@WebServlet(name = "abrirTaquilla", urlPatterns =
{
    "/abrirTaquilla"
})
public class abrirTaquilla extends HttpServlet
{

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
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            int id_pedido = Integer.parseInt(request.getParameter("id_pedido"));
            int id_recogida = Integer.parseInt(request.getParameter("id_recogida"));
            String recogido = request.getParameter("recogido");

            MQTTBroker bkr = new MQTTBroker();
            
            int taquillero = Logic.getTaquilleroPedido(id_pedido);
            int taquilla = Logic.getTaquillaPedido(id_pedido);
            
            if(recogido.equals("true"))
            {
                Logic.updateRecogidaAutenticar(true, id_recogida);
                Logic.updatePedidoEstadoEntrega("recogido", id_pedido);
                
                MQTTPublisher.publish(bkr, "Taquillero" + taquillero + "/Taquillero" + taquilla + "/accion", "Abrir");
            }
            
            if(recogido.equals("false"))
            {
                MQTTPublisher.publish(bkr, "Taquillero" + taquillero + "/Taquillero" + taquilla + "/accion", "Cerrar");
            }
            
            out.print(1);
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
