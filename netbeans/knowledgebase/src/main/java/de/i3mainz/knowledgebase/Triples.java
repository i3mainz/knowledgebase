package de.i3mainz.knowledgebase;

/**
 * CLASS representing triples
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class Triples {
	
	public static String setTriplesDummyOntology(String ID, String req_projektname, String req_akronym, String req_projektbeschreibung, String req_projektwebsite, String[] req_projektleitung_split, String req_institut, String[] req_projektpartner_split, String []req_mitwirkende_split, String[] req_foerderer_split, int req_foerdersumme, int req_beginn, int req_ende, String req_epoche, String req_disziplin, String req_geografischerraum, double req_lat, double req_lon, String req_datentyp, String req_lizenz) {
		String triples = "";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/id" + "> ";
			triples += "\"" + ID + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/projektname" + "> ";
			triples += "\"" + req_projektname + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/akronym" + "> ";
			triples += "\"" + req_akronym + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/projektbeschreibung" + "> ";
			triples += "\"" + req_projektbeschreibung + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/projektwebsite" + "> ";
			triples += "<" + req_projektwebsite + ">" + " . ";
			for (String element : req_projektleitung_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://example.com/vocab/projektleitung" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/institut" + "> ";
			triples += "\"" + req_institut + "\"" + " . ";
			for (String element : req_projektpartner_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://example.com/vocab/projektpartner" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			for (String element : req_mitwirkende_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://example.com/vocab/mitwirkende" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			for (String element : req_foerderer_split) {	
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://example.com/vocab/foerderer" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/foerdersumme" + "> ";
			triples += "\"" + req_foerdersumme + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/beginn" + "> ";
			triples += "\"" + req_beginn + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/ende" + "> ";
			triples += "\"" + req_ende + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/epoche" + "> ";
			triples += "\"" + req_epoche + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/disziplin" + "> ";
			triples += "\"" + req_disziplin + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/geografischerraum" + "> ";
			triples += "\"" + req_geografischerraum + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/lat" + "> ";
			triples += "\"" + req_lat + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/lon" + "> ";
			triples += "\"" + req_lon + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/datentyp" + "> ";
			triples += "\"" + req_datentyp + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://example.com/vocab/lizenz" + "> ";
			triples += "\"" + req_lizenz + "\"" + " . ";
		return triples;
	}
	
	public static String setTriplesCERIFOntologyLight(String ID, String req_projektname, String req_akronym, String req_projektbeschreibung, String req_projektwebsite, String[] req_projektleitung_split, String req_institut, String[] req_projektpartner_split, String []req_mitwirkende_split, String[] req_foerderer_split, int req_foerdersumme, int req_beginn, int req_ende, String req_epoche, String req_disziplin, String req_geografischerraum, double req_lat, double req_lon, String req_datentyp, String req_lizenz) {
		String triples = "";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#internalIdentifier" + "> ";
			triples += "\"" + ID + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#name" + "> ";
			triples += "\"" + req_projektname + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#akronym" + "> ";
			triples += "\"" + req_akronym + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#abstract" + "> ";
			triples += "\"" + req_projektbeschreibung + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#uri" + "> ";
			triples += "<" + req_projektwebsite + ">" + " . ";
			for (String element : req_projektleitung_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://eurocris.org/ontologies/cerifX/1.3#head" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerifX/1.3#organisation" + "> ";
			triples += "\"" + req_institut + "\"" + " . ";
			for (String element : req_projektpartner_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://eurocris.org/ontologies/cerifX/1.3#partner" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			for (String element : req_mitwirkende_split) {
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://eurocris.org/ontologies/cerifX/1.3#staff" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			for (String element : req_foerderer_split) {	
				triples += "<" + Config.INSTANCE_HOST + ID + "> ";
				triples += "<" + "http://eurocris.org/ontologies/cerifX/1.3#sponsor" + "> ";
				triples += "\"" + element + "\"" + " . ";
			}
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#funding" + "> ";
			triples += "\"" + req_foerdersumme + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#startDate" + "> ";
			triples += "\"" + req_beginn + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://eurocris.org/ontologies/cerif/1.3#endDate" + "> ";
			triples += "\"" + req_ende + "\"^^<http://www.w3.org/2001/XMLSchema#integer>" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://purl.org/dc/elements/1.1/date" + "> ";
			triples += "\"" + req_epoche + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://purl.org/dc/elements/1.1/subject" + "> ";
			triples += "\"" + req_disziplin + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://purl.org/dc/elements/1.1/coverage" + "> ";
			triples += "\"" + req_geografischerraum + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://www.w3.org/2003/01/geo/wgs84_pos#lat" + "> ";
			triples += "\"" + req_lat + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://www.w3.org/2003/01/geo/wgs84_pos#lon" + "> ";
			triples += "\"" + req_lon + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://purl.org/dc/elements/1.1/format" + "> ";
			triples += "\"" + req_datentyp + "\"" + " . ";
			triples += "<" + Config.INSTANCE_HOST + ID + "> ";
			triples += "<" + "http://purl.org/dc/elements/1.1/rights" + "> ";
			triples += "\"" + req_lizenz + "\"" + " . ";
		return triples;
	}
	
}
