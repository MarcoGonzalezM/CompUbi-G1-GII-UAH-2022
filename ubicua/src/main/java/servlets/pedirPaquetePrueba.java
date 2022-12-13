
package servlets;

import com.google.gson.Gson;
import database.Taquilla;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import logic.Log;
import logic.Logic;


@WebServlet(name = "pedirPaquetePrueba", urlPatterns = {"/pedirPaquetePrueba"})
public class pedirPaquetePrueba extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;


    public pedirPaquetePrueba() 
    {
        super();
    }
    
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, IOException {
        Log.log.info("-- pidiendo paquete prueba information from DB--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String cliente = request.getParameter("id_cliente");
            String taquillero = request.getParameter("taquillero");
            int id_cliente = Integer.parseInt(cliente);
            int id_taquillero = Integer.parseInt(taquillero);
            String estado_peticion_pedido = Logic.pedirPaquetePrueba(id_cliente,id_taquillero);
            out.print(estado_peticion_pedido);
        } catch (NumberFormatException nfe) {
            out.println("-1");
            Log.log.error("Number Format Exception: {}", nfe);
        } catch (IndexOutOfBoundsException iobe) {
            out.println("-1");
            Log.log.error("Index out of bounds Exception: {}", iobe);
        } catch (Exception e) {
            out.println("-1");
            Log.log.error("Exception: {}", e);
        } finally {
            out.close();
        }
    }

    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, IOException {
        doGet(request, response);
    }

}
