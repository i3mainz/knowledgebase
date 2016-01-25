package de.i3mainz.knowledgebase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.i3mainz.tools.SesameConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.openrdf.query.BindingSet;

/**
 * Get hints for input form
 *
 * @author Florian Thiery M.Sc. | i3mainz
 */
public class Calc extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String query;
		List<BindingSet> result;
		JSONArray jsonarray_tmp;
		try {
			String req_query = request.getParameter("query");
			JSONArray jsonarray_data = new JSONArray(); // []
			if (req_query.equals("PIE")) {
				query = "SELECT ?institut (SUM(?foerdersumme) AS ?sumfoerdersumme) WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#funding> ?foerdersumme. "
						+ "} GROUP BY ?institut";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> list_institut = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "institut");
				List<String> list_sumfoerdersumme = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "sumfoerdersumme");
				// CREATE JSON
				JSONObject jsonobj = new JSONObject(); // {}
				jsonarray_tmp = new JSONArray(); // []
				for (int i = 0; i < list_institut.size(); i++) {
					JSONObject jsonobj_temp = new JSONObject();// []
					jsonobj_temp.put("name", list_institut.get(i));
					jsonobj_temp.put("y", new Double(list_sumfoerdersumme.get(i)));
					jsonobj_temp.put("sliced", new Boolean(true));
					jsonobj_temp.put("selected", new Boolean(true));
					jsonarray_tmp.add(jsonobj_temp);
				}
				jsonobj.put("data", jsonarray_tmp);
				jsonarray_data.add(jsonobj);
			} else if (req_query.equals("COLUMN")) {
				// year min max
				query = "SELECT (MIN(?beginn) AS ?min) (MAX(?beginn) AS ?max) WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#startDate> ?beginn. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> hs_min = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "min");
				List<String> hs_max = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "max");
				List<Integer> period = new ArrayList<Integer>();
				int start = Integer.parseInt(hs_min.get(0));
				int end = Integer.parseInt(hs_max.get(0));
				for (int i = start; i <= end; i++) {
					period.add(i);
				}
				// institutes
				query = "SELECT DISTINCT ?institut WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> list_institutes = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "institut");
				// CREATE JSON
				JSONArray jsonarray_institutes = new JSONArray(); // []
				for (String element : list_institutes) {
					jsonarray_institutes.add(element);
				}
				for (int i = 0; i < period.size(); i++) {
					JSONObject jsonobj = new JSONObject(); // {}
					if (i == 0) {
						jsonobj.put("categories", jsonarray_institutes);
					}
					jsonobj.put("name", period.get(i));
					JSONArray jsonarray_datavalues = new JSONArray(); // []
					for (int ii = 0; ii < list_institutes.size(); ii++) {
						query = "SELECT (SUM(?foerdersumme) AS ?sum) WHERE { "
								+ "?s <http://eurocris.org/ontologies/cerif/1.3#funding> ?foerdersumme. "
								+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
								+ "?s <http://eurocris.org/ontologies/cerif/1.3#startDate> ?beginn. "
								+ "FILTER (?beginn = \""+period.get(i)+"\"^^<http://www.w3.org/2001/XMLSchema#integer>) "
								+ "FILTER (?institut = \""+jsonarray_institutes.get(ii)+"\") "
								+ "} GROUP BY ?institut";
						result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
						List<String> list_sum = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "sum");
						jsonarray_datavalues.add(new Integer(list_sum.get(0)));
					}
					jsonobj.put("data", jsonarray_datavalues);
					jsonarray_data.add(jsonobj);
				}
			} else if (req_query.equals("LINE")) {
				// year min max
				query = "SELECT (MIN(?beginn) AS ?min) (MAX(?beginn) AS ?max) WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#startDate> ?beginn. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> hs_min = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "min");
				List<String> hs_max = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "max");
				List<Integer> period = new ArrayList<Integer>();
				int start = Integer.parseInt(hs_min.get(0));
				int end = Integer.parseInt(hs_max.get(0));
				for (int i = start; i <= end; i++) {
					period.add(i);
				}
				// institutes
				query = "SELECT DISTINCT ?institut WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> list_institutes = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "institut");
				// CREATE JSON
				JSONArray jsonarray_periods = new JSONArray(); // []
				for (Integer element : period) {
					jsonarray_periods.add(element);
				}
				for (int i = 0; i < list_institutes.size(); i++) {
					JSONObject jsonobj = new JSONObject(); // {}
					if (i == 0) {
						jsonobj.put("categories", jsonarray_periods);
					}
					jsonobj.put("name", list_institutes.get(i));
					JSONArray jsonarray_datavalues = new JSONArray(); // []
					for (int ii = 0; ii < period.size(); ii++) {
						query = "SELECT (SUM(?foerdersumme) AS ?sum) WHERE { "
								+ "?s <http://eurocris.org/ontologies/cerif/1.3#funding> ?foerdersumme. "
								+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
								+ "?s <http://eurocris.org/ontologies/cerif/1.3#startDate> ?beginn. "
								+ "FILTER (?beginn = \""+period.get(ii)+"\"^^<http://www.w3.org/2001/XMLSchema#integer>) "
								+ "FILTER (?institut = \""+list_institutes.get(i)+"\") "
								+ "} GROUP BY ?institut";
						result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
						List<String> list_sum = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "sum");
						jsonarray_datavalues.add(new Integer(list_sum.get(0)));
						int z = 0;
					}
					jsonobj.put("data", jsonarray_datavalues);
					jsonarray_data.add(jsonobj);
				}
			}
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
