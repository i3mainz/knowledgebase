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
 * SERVLET for modify data
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class Modify extends HttpServlet {

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String query;
		List<BindingSet> result;
		JSONArray jsonarray_tmp;
		try {
			String req_mode = request.getParameter("mode");
			String req_project = request.getParameter("project");
			if (req_mode.equals("list")) {
				// get projects
				query = "SELECT * WHERE { ?s <http://eurocris.org/ontologies/cerif/1.3#akronym> ?akronym. ?s <http://eurocris.org/ontologies/cerif/1.3#internalIdentifier> ?id. } ORDER BY ASC(?akronym)";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				List<String> list_projects = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "akronym");
				List<String> list_id = SesameConnect.getValuesFromBindingSet_ORDEREDLIST(result, "id");
				JSONArray jsonarray_data = new JSONArray(); // []
				for (int i = 0; i < list_projects.size(); i++) {
					JSONObject jsonobj = new JSONObject();
					jsonobj.put("name", list_projects.toArray()[i]);
					jsonobj.put("id", list_id.toArray()[i]);
					jsonarray_data.add(jsonobj);
				}
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				out.print(gson.toJson(jsonarray_data));
			} else if (req_mode.equals("data")) {
				query = "SELECT * WHERE { "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#internalIdentifier> \"" + req_project + "\". "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#name> ?projektname. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#akronym> ?akronym. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#abstract> ?projektbeschreibung. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#uri> ?projektwebsite. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#head> ?projektleitung. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#organisation> ?institut. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#partner> ?projektpartner. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#staff> ?mitwirkende. "
						+ "?s <http://eurocris.org/ontologies/cerifX/1.3#sponsor> ?foerderer. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#funding> ?foerdersumme. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#startDate> ?beginn. "
						+ "?s <http://eurocris.org/ontologies/cerif/1.3#endDate> ?ende. "
						+ "?s <http://purl.org/dc/elements/1.1/date> ?epoche. "
						+ "?s <http://purl.org/dc/elements/1.1/subject> ?disziplin. "
						+ "?s <http://purl.org/dc/elements/1.1/coverage> ?geografischerraum. "
						+ "?s <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?lat. "
						+ "?s <http://www.w3.org/2003/01/geo/wgs84_pos#lon> ?lon. "
						+ "?s <http://purl.org/dc/elements/1.1/format> ?datentyp. "
						+ "?s <http://purl.org/dc/elements/1.1/rights> ?lizenz. "
						+ "}";
				result = SesameConnect.SPARQLquery(Config.TRIPLESTORE_REPOSITORY, query);
				HashSet<String> list_projektname = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektname");
				HashSet<String> list_akronym = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "akronym");
				HashSet<String> list_projektbeschreibung = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektbeschreibung");
				HashSet<String> list_projektwebsite = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektwebsite");
				HashSet<String> list_projektleitung = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektleitung");
				HashSet<String> list_institut = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "institut");
				HashSet<String> list_projektpartner = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "projektpartner");
				HashSet<String> list_mitwirkende = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "mitwirkende");
				HashSet<String> list_foerderer = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "foerderer");
				HashSet<String> list_foerdersumme = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "foerdersumme");
				HashSet<String> list_beginn = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "beginn");
				HashSet<String> list_ende = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "ende");
				HashSet<String> list_epoche = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "epoche");
				HashSet<String> list_disziplin = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "disziplin");
				HashSet<String> list_geografischerraum = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "geografischerraum");
				HashSet<String> list_lat = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "lat");
				HashSet<String> list_lon = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "lon");
				HashSet<String> list_datentyp = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "datentyp");
				HashSet<String> list_lizenz = SesameConnect.getValuesFromBindingSet_UNIQUESET(result, "lizenz");
				int z = 0;
				// create json
				JSONArray jsonarray_data = new JSONArray(); // []
				JSONObject jsonobj = new JSONObject(); // {}
				// fill object
				jsonobj.put("projektname", list_projektname.toArray()[0]);
				jsonobj.put("akronym", list_akronym.toArray()[0]);
				jsonobj.put("projektbeschreibung", list_projektbeschreibung.toArray()[0]);
				jsonobj.put("projektwebsite", list_projektwebsite.toArray()[0]);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_projektleitung) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("projektleitung", jsonarray_tmp);
				jsonobj.put("institut", list_institut.toArray()[0]);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_projektpartner) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("projektpartner", jsonarray_tmp);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_mitwirkende) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("mitwirkende", jsonarray_tmp);
				jsonarray_tmp = new JSONArray(); // []
				for (String element_tmp : list_foerderer) {
					jsonarray_tmp.add(element_tmp);
				}
				jsonobj.put("foerderer", jsonarray_tmp);
				jsonobj.put("foerdersumme", Integer.parseInt((String) list_foerdersumme.toArray()[0]));
				jsonobj.put("beginn", Integer.parseInt((String) list_beginn.toArray()[0]));
				jsonobj.put("ende", Integer.parseInt((String) list_ende.toArray()[0]));
				jsonobj.put("epoche", list_epoche.toArray()[0]);
				jsonobj.put("disziplin", list_disziplin.toArray()[0]);
				jsonobj.put("geografischerraum", list_geografischerraum.toArray()[0]);
				jsonobj.put("lat", Double.parseDouble((String) list_lat.toArray()[0]));
				jsonobj.put("lon", Double.parseDouble((String) list_lon.toArray()[0]));
				jsonobj.put("datentyp", list_datentyp.toArray()[0]);
				jsonobj.put("lizenz", list_lizenz.toArray()[0]);
				jsonobj.put("id", req_project);
				// add to output array
				jsonarray_data.add(jsonobj);
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				out.print(gson.toJson(jsonarray_data));
			} else {
				// delete data
				String req_id = request.getParameter("mode");
				/*String update = "DELETE { ?l ?p1 ?o . ?s ?p2 ?l . } "
						+ "WHERE { ?l ?p1 ?o . OPTIONAL { ?s ?p2 ?l } "
						+ "?l <http://example.com/vocab/id> \"" + req_id + "\" . }";*/
				String update = "DELETE { ?l ?p ?o } "
						+ "WHERE { ?l ?p ?o . "
						+ "?l <http://eurocris.org/ontologies/cerif/1.3#internalIdentifier> \"" + req_id + "\" . }";
				SesameConnect.SPARQLupdate(Config.TRIPLESTORE_REPOSITORY, update);
				// import new data
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
				String triples = Triples.setTriplesCERIFOntologyLight(req_id, req_projektname, req_akronym, req_projektbeschreibung, req_projektwebsite, req_projektleitung_split, req_institut, req_projektpartner_split, req_mitwirkende_split, req_foerderer_split, req_foerdersumme, req_beginn, req_ende, req_epoche, req_disziplin, req_geografischerraum, req_lat, req_lon, req_datentyp, req_lizenz);
				// send triples to triplestore
				String updateString = "INSERT DATA { " + triples + " }";
				SesameConnect.SPARQLupdate(Config.TRIPLESTORE_REPOSITORY, updateString);
				out.print("{ \"action\": \"ok\" }");
			}
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
