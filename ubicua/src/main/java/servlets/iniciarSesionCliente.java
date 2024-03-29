
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import database.Cliente;
import java.nio.charset.StandardCharsets;
import logic.Log;
import logic.Logic;

/**
 *
 * @author Heras
 */
@WebServlet(name = "iniciarSesionCliente", urlPatterns = {"/iniciarSesionCliente"})
public class iniciarSesionCliente extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String nombre = request.getParameter("nombre");
            String password = request.getParameter("password");
            //password = unhash(password);
            Cliente cli = Logic.getUsuarioDB(nombre);
            if (cli.getId_cliente() == 0) {
                //Cliente no existe
                out.print("-1");
            } else if (password.equals(cli.getPassword())) {
                out.print(Integer.toString(cli.getId_cliente()));
            } else {
                //Contraseña incorrecta
                out.print("-2");
            }

        } catch (NullPointerException e) {
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
        doGet(request, response);
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

    public String unhash(String pwd) {
        byte[] pwdBytes = pwd.getBytes(StandardCharsets.UTF_8);
        String hashPwd = "";
        for (int i = 0; i < pwdBytes.length; i++) {
            hashPwd += (char) ~pwdBytes[i];
        }
        return hashPwd;
    }
}
