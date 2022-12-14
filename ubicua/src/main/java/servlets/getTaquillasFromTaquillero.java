
package servlets;

import database.Taquillero;
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
import com.google.gson.Gson;
import database.Taquilla;

/**
 *
 * @author mario.fernandezr
 */
@WebServlet(name = "getTaquillasFromTaquillero", urlPatterns = {"/getTaquillasFromTaquillero"})
public class getTaquillasFromTaquillero extends HttpServlet {
    
    private static final long serialVersionUID = 1L;


    public getTaquillasFromTaquillero() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Log.log.info("-- Get Taquilleros information from DB--");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
                int id_taquillero = Integer.parseInt(request.getParameter("id_taquillero"));
		try 
		{
			ArrayList<Taquilla> values = Logic.getTaquillasFromTaquilleroDB(id_taquillero);
			String jsonStations = new Gson().toJson(values);
			Log.log.info("JSON Values=> {}", jsonStations);
			out.println(jsonStations);
		} catch (NumberFormatException nfe) 
		{
			out.println("-1");
			Log.log.error("Number Format Exception: {}", nfe);
		} catch (IndexOutOfBoundsException iobe) 
		{
			out.println("-1");
			Log.log.error("Index out of bounds Exception: {}", iobe);
		} catch (Exception e) 
		{
			out.println("-1");
			Log.log.error("Exception: {}", e);
		} finally 
		{
			out.close();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doGet(request, response);
	}

}
