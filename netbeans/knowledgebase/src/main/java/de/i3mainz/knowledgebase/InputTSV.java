package de.i3mainz.knowledgebase;

import de.i3mainz.tools.Logging;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * SERVLET imports TSV to triplestore
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100)      // 100 MB
public class InputTSV extends HttpServlet {

	public static double status = -1.0;
	public static String action = "";
	public static String mode = "";
	public static PrintWriter out;
	public static int maxSteps = -1;
	public static String FILELINK = null;
	public static StringBuffer UPLOADFILE;
	public static String FILEPATH;
	public static List<String> TRIPLE_LIST = new ArrayList<String>();

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		out = response.getWriter();
		try {
			// parse mode
			if (request.getParameter("mode") != null) {
				mode = request.getParameter("mode");
			}
			if (mode.equals("start")) {
				FILEPATH = getServletContext().getRealPath("InputTSV").replace("InputTSV", "");
				String fileName = "";
				for (Part part : request.getParts()) {
					fileName = getFileName(part);
					String f = FILEPATH + fileName;
					part.write(f);
				}
				FILELINK = request.getRequestURL().toString().replace("InputTSV", "");
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILEPATH + fileName), "UTF8"));
				String line;
				UPLOADFILE = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					UPLOADFILE.append(line + "\r\n");
				}
				reader.close();
				File file = new File(FILEPATH + fileName);
				file.delete();
				start();
				response.setStatus(200);
			} else if (mode.equals("update")) {
				update();
				response.setStatus(200);
			} else if (mode.equals("finish")) {
				finish();
				response.setStatus(200);
			} else {
				throw new IllegalArgumentException();
			}
		} catch (Exception e) {
			response.setStatus(500);
			out.print("\n\n");
			out.print(Logging.getMessageJSON(e, getClass().getName()));
		} finally {
			response.setContentType("application/json;charset=UTF-8");
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setCharacterEncoding("UTF-8");
			out.close();
		}
	}

	/**
	 * start thread and parse data
	 */
	public void start() {
		status = 0.0;
		action = "start parsing...";
		String[] csvLines = UPLOADFILE.toString().split("\r\n");
		maxSteps = csvLines.length;
		(new Thread(new TSV())).start();
		update();
	}

	/**
	 * get update status
	 */
	public void update() {
		out.print("{ \"status\": \"" + status + "\",  \"action\": \"" + action + "\"}");
	}

	/**
	 * get result if finished
	 */
	public void finish() {
		out.print("{ \"status\": \"" + status + "\",  \"action\": \"" + action + "\"}");
	}

	/**
	 * Utility method to get file name from HTTP header content-disposition
	 *
	 * @param part
	 * @return
	 */
	public static String getFileName(Part part) throws FileNotFoundException {
		try {
			String contentDisp = part.getHeader("content-disposition");
			System.out.println("content-disposition header= " + contentDisp);
			String[] tokens = contentDisp.split(";");
			for (String token : tokens) {
				if (token.trim().startsWith("filename")) {
					return token.substring(token.indexOf("=") + 2, token.length() - 1);
				}
			}
			return "";
		} catch (Exception e) {
			throw new FileNotFoundException();
		}
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if FILEPATH servlet-specific error occurs
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
	 * @throws ServletException if FILEPATH servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns FILEPATH short description of the servlet.
	 *
	 * @return FILEPATH String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "SERVLET imports a CSV to labelingsystem repository";
	}// </editor-fold>

}
