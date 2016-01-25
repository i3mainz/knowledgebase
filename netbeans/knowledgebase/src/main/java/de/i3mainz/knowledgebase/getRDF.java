package de.i3mainz.knowledgebase;

import de.i3mainz.tools.RDF;
import de.i3mainz.tools.SesameConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.openrdf.query.BindingSet;

/**
 * SERVLET for rdf representations
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class getRDF extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		PrintWriter out = response.getWriter();
		try {
			String req_id = request.getParameter("id");
			String req_format = request.getParameter("format");
			String mainzedquery = "SELECT * WHERE { <" + Config.INSTANCE_HOST + req_id + "> ?p ?o. } ORDER BY ASC(?p)";
			List<BindingSet> rev_result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, mainzedquery);
			List<String> predicates = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(rev_result, "p");
			List<String> objects = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(rev_result, "o");
			if (predicates.size() < 1) {
				throw new SQLException("");
			}
			RDF rdf = new RDF();
			for (int i = 0; i < predicates.size(); i++) {
				rdf.setModelTriple(Config.INSTANCE_HOST + req_id, predicates.get(i), objects.get(i));
			}
			String RDFoutput = null;
			if (req_format.equals("rdf/xml")) {
				RDFoutput = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" + rdf.getModel("RDF/XML");
				response.setContentType("application/xml;charset=UTF-8");
			} else if (req_format.equals("ttl")) {
				RDFoutput = rdf.getModel("Turtle");
				response.setContentType("text/plain;charset=UTF-8");
			} else if (req_format.equals("n3")) {
				RDFoutput = rdf.getModel("N-Triples");
				response.setContentType("text/plain;charset=UTF-8");
			} else if (req_format.equals("jsonld")) {
				RDFoutput = rdf.getModel("JSON-LD");
				response.setContentType("application/json;charset=UTF-8");
			}
			out.print(RDFoutput);
		} catch (Exception e) {
			response.sendError(500, e.toString());
		} finally {
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
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(getRDF.class.getName()).log(Level.SEVERE, null, ex);
		}
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
		try {
			processRequest(request, response);
		} catch (SQLException ex) {
			Logger.getLogger(getRDF.class.getName()).log(Level.SEVERE, null, ex);
		}
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
