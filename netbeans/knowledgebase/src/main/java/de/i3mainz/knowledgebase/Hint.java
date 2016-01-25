package de.i3mainz.knowledgebase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.i3mainz.tools.SesameConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openrdf.query.BindingSet;

/**
 * SERVLET representing a hint json
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class Hint extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String query;
		List<BindingSet> result;
		JSONArray jsonarray_tmp;
		try {
			JSONArray jsonarray_data = new JSONArray(); // []
				query = "SELECT * WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#head> ?projektleitung. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#partner> ?projektpartner. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#staff> ?mitwirkende. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#sponsor> ?foerderer. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				HashSet<String> list_projektleitung = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektleitung");
				HashSet<String> list_institut = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "institut");
				HashSet<String> list_projektpartner = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektpartner");
				HashSet<String> list_mitwirkende = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "mitwirkende");
				HashSet<String> list_foerderer = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "foerderer");
				// create json
				JSONObject jsonobj = new JSONObject(); // {}
				// fill object
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_institut) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("institut", jsonarray_tmp);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_projektpartner) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("projektpartner", jsonarray_tmp);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_foerderer) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("foerderer", jsonarray_tmp);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_projektleitung) {
					jsonarray_tmp.add(element_tmp);
				}
				for (String element_tmp : list_mitwirkende) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("persons", jsonarray_tmp);
				// add to output array
				jsonarray_data.add(jsonobj);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out.print(gson.toJson(jsonarray_data));
			response.setStatus(200);
		} catch (Exception e) {
			response.sendError(500, e.toString());
		} finally {
			response.setContentType("application/json;charset=UTF-8");
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
