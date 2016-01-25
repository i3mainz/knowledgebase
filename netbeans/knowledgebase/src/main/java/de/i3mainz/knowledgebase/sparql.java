package de.i3mainz.knowledgebase;

import com.hp.hpl.jena.shared.ConfigException;
import de.i3mainz.tools.Logging;
import de.i3mainz.tools.SesameConnect;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * SERVLET representing a sparql endpoint
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class sparql extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ConfigException {
		// http://de.slideshare.net/olafhartig/querying-linked-data-with-sparql
		String acceptHeader = request.getHeader("Accept");
		String format = "application/sparql-results+xml"; // default: application/sparql-results+xml --> change variable name to out!!!
		String file = "false"; // default: false
		String result = "";
		String query = "SELECT * WHERE { ?s ?p ?o } LIMIT 10"; // default: query vocabs
		// PARSE PARAMETER
		if (request.getParameter("query") == null) {
			format = "html";
			result = "<html>"
					+ "<head>"
					+ "<title>knowledegbase</title>"
					+ "<link rel='stylesheet' href='style.css'>"
					+ "</head>"
					+ "<h1 class='left top'>Knowledgebase SPARQL endpoint</h1><p>&nbsp;</p>"
					+ "<h3 class='left top'>methods: HTTP-GET / HTTP-POST</h3>"
					+ "<h3 class='left top'>Header Accept: {application/sparql-results+xml;application/sparql-results+json} default:application/sparql-results+xml</h3>"
					+ "<h3 class='left top'>param: query {encoded SPARQL QUERY} | <a href='http://www.w3schools.com/jsref/jsref_encodeuricomponent.asp'>JavaScript-example</a></h3>"
					+ "<h3 class='left top'>param: format {xml;json;csv;tsv;application/sparql-results+xml;application/sparql-results+json}</h3>"
					+ "</html>";
		} else {
			query = request.getParameter("query");
			query = URLDecoder.decode(query, "UTF-8");
		}
		if (request.getParameter("format") != null) {
			format = request.getParameter("format");
		}
		if (request.getParameter("file") != null) {
			file = request.getParameter("file");
		}
		if (acceptHeader.equals("application/sparql-results+xml")) {
			format = "application/sparql-results+xml";
		} else if (acceptHeader.equals("application/sparql-results+json")) {
			format = "application/sparql-results+json";
		} else {
			format = format;
		}
		if (format.equals("xml")) {
			response.setContentType("application/xml;charset=UTF-8");
			if (file.equals("true")) {
				response.setHeader("Content-disposition", "attachment;filename=sparql_result.xml");
			}
		} else if (format.equals("json")) {
			response.setContentType("application/json;charset=UTF-8");
			if (file.equals("true")) {
				response.setHeader("Content-disposition", "attachment;filename=sparql_result.json");
			}
		} else if (format.equals("csv")) {
			response.setContentType("text/plain;charset=UTF-8");
			if (file.equals("true")) {
				response.setHeader("Content-disposition", "attachment;filename=sparql_result.csv");
			}
		} else if (format.equals("application/sparql-results+xml")) {
			response.setContentType("application/sparql-results+xml;charset=UTF-8");
			if (file.equals("true")) {
				response.setHeader("Content-disposition", "attachment;filename=sparql_result.xml");
			}
		} else if (format.equals("application/sparql-results+json")) {
			response.setContentType("application/sparql-results+json;charset=UTF-8");
			if (file.equals("true")) {
				response.setHeader("Content-disposition", "attachment;filename=sparql_result.json");
			}
		} else if (format.equals("html")) {
			response.setContentType("text/html;charset=UTF-8");
		} else {
			response.setContentType("application/sparql-results+xml;charset=UTF-8");
		}
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setCharacterEncoding("UTF-8");
		ServletOutputStream outstream = response.getOutputStream();
		try {
			if (format.equals("html")) {
				outstream.write(result.getBytes(Charset.forName("UTF-8")));
			} else {
				if (format.equals("application/sparql-results+xml")) {
					format = "xml";
				} else if (format.equals("application/sparql-results+json")) {
					format = "json";
				}
				SesameConnect.SPARQLqueryOutputFile(Config.TRIPLESTORE_REPOSITORY, query, format, outstream);
			}
			response.setStatus(200);
		} catch (Exception e) {
			outstream.write(Logging.getMessageJSON(e, getClass().getName()).getBytes(Charset.forName("UTF-8")));
			response.setContentType("application/json;charset=UTF-8");
			response.setStatus(500);
		} finally {
			outstream.flush();
			outstream.close();
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
		} catch (ConfigException ex) {
			Logger.getLogger(sparql.class.getName()).log(Level.SEVERE, null, ex);
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
		} catch (ConfigException ex) {
			Logger.getLogger(sparql.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Servlet returns SPARQL XML/JSON/CSV/TSV from triplestore (repository labelingsystem";
	}// </editor-fold>

}
