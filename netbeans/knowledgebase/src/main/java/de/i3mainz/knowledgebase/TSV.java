package de.i3mainz.knowledgebase;

import de.i3mainz.tools.SesameConnect;
import java.io.File;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;
/*
 /**
 * CLASS (implements Runnable) to import a TSV file to the triplestore
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 26.01.2016
 */

public class TSV implements Runnable {

	@Override
	public void run() {
		String LOADLINK = "";
		String LOADFILE = "";
		try {
			InputTSV.action = "parse...";
			String[] tsvLines = InputTSV.UPLOADFILE.toString().split("\r\n");
			for (int i = 1; i < InputTSV.maxSteps; i++) {
				String[] elements = tsvLines[i].split("\t");
				String[] projektleitung = elements[4].split(";");
				String[] projektpartner = elements[6].split(";");
				String[] mitwirkende = elements[7].split(";");
				String[] foerderer = elements[8].split(";");
				UUID newUUID = UUID.randomUUID();
				String triples = Triples.setTriplesCERIFOntologyLight(newUUID.toString(), elements[0], elements[1], elements[2], elements[3], projektleitung, elements[5], projektpartner, mitwirkende, foerderer, Integer.parseInt(elements[9]), Integer.parseInt(elements[10]), Integer.parseInt(elements[11]), elements[12], elements[13], elements[14], Double.parseDouble(elements[15]), Double.parseDouble(elements[16]), elements[17], elements[18]);
				InputTSV.TRIPLE_LIST.add(triples);
			}
			InputTSV.status = 50.0;
			InputTSV.action = "send to triplestore...";
			String time = String.valueOf(System.currentTimeMillis());
			LOADFILE = InputTSV.FILEPATH + time + ".ttl";
			PrintWriter pw = new PrintWriter(LOADFILE, "UTF-8");
			for (String TRIPLE_LIST_ITEM : InputTSV.TRIPLE_LIST) {
				pw.println(TRIPLE_LIST_ITEM);
			}
			pw.close();
			LOADLINK = InputTSV.FILELINK + time + ".ttl";
			SesameConnect.SPARQLupdate(Config.TRIPLESTORE_REPOSITORY, "LOAD <" + LOADLINK + ">");
			File file = new File(LOADFILE);
			file.delete();
			InputTSV.action = "finished!";
			InputTSV.status = 100.0;
		} catch (Exception e) {
			File file = new File(LOADFILE);
			file.delete();
			Logger.getLogger(TSV.class.getName()).log(Level.SEVERE, null, e);
			InputTSV.action = e.toString();
			InputTSV.status = 100.0;
		}
	}

}
