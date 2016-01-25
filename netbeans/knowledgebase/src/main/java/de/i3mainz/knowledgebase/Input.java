package de.i3mainz.knowledgebase;

import de.i3mainz.tools.SesameConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SERVLET for input data
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class Input extends HttpServlet {
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			// parse params
			String req_projektname = request.getParameter("projektname");
			String req_akronym = request.getParameter("akronym");
			String req_projektbeschreibung = request.getParameter("projektbeschreibung");
			String req_projektwebsite = request.getParameter("projektwebsite");
			String req_projektleitung = request.getParameter("projektleitung");
			String[] req_projektleitung_split = req_projektleitung.split(";");
			String req_institut = request.getParameter("institut");
			String req_projektpartner = request.getParameter("projektpartner");
			String[] req_projektpartner_split = req_projektpartner.split(";");
			String req_mitwirkende = request.getParameter("mitwirkende");
			String[] req_mitwirkende_split = req_mitwirkende.split(";");
			String req_foerderer = request.getParameter("foerderer");
			String[] req_foerderer_split = req_foerderer.split(";");
			int req_foerdersumme = Integer.parseInt(request.getParameter("foerdersumme"));
			int req_beginn = Integer.parseInt(request.getParameter("beginn"));
			int req_ende = Integer.parseInt(request.getParameter("ende"));
			String req_epoche = request.getParameter("epoche");
			String req_disziplin = request.getParameter("disziplin");
			String req_geografischerraum = request.getParameter("geografischerraum");
			double req_lat = Double.parseDouble(request.getParameter("lat"));
			double req_lon = Double.parseDouble(request.getParameter("lon"));
			String req_datentyp = request.getParameter("datentyp");
			String req_lizenz = request.getParameter("lizenz");
			// transform into triples
			UUID newUUID = UUID.randomUUID();
			String triples = Triples.setTriplesCERIFOntologyLight(newUUID.toString(), req_projektname, req_akronym, req_projektbeschreibung, req_projektwebsite, req_projektleitung_split, req_institut, req_projektpartner_split, req_mitwirkende_split, req_foerderer_split, req_foerdersumme, req_beginn, req_ende, req_epoche, req_disziplin, req_geografischerraum, req_lat, req_lon, req_datentyp, req_lizenz);
			// send triples to triplestore
			String updateString = "INSERT DATA { " + triples + " }";
			SesameConnect.SPARQLupdate(Config.TRIPLESTORE_REPOSITORY,updateString);
			response.setStatus(200);
		} catch (Exception e) {
			response.sendError(500, e.toString());
		} finally {
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
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
