/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
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

/**
 *
 * @author mario.fernandezr
 */
@WebServlet(name = "asignarPedidoRepartidor", urlPatterns = {"/asignarPedidoRepartidor"})
public class asignarPedidoRepartidor extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws jakarta.servlet.ServletException
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, IOException {
        Log.log.info("-- asignando paquete information from DB--");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String repartidor = request.getParameter("id_repartidor");
            String pedido = request.getParameter("id_pedido");
            int id_repartidor = Integer.parseInt(repartidor);
            int id_pedido = Integer.parseInt(pedido);
            int estado_asignar_paquete = Logic.asignarPedidoaRepartidor(id_pedido,id_repartidor);
            if (estado_asignar_paquete == 1) {
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
