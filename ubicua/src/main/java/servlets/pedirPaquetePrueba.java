
package servlets;

import com.google.gson.Gson;
import database.Taquilla;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        Log.log.info("-- Get Taquilleros information from DB--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String cliente = request.getParameter("id_cliente");
            String taquillero = request.getParameter("taquillero");
            int id_cliente = Integer.parseInt(cliente);
            int id_taquillero = Integer.parseInt(taquillero);
            int estado_peticion_pedido = Logic.pedirPaquetePrueba(id_cliente,id_taquillero);
            if (estado_peticion_pedido == 1) {
                //Cliente no existe
                out.print("1");
            } else {
                out.print("-1");
            }
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
