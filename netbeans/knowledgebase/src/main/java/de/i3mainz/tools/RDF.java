package de.i3mainz.tools;

import com.github.jsonldjava.jena.JenaJSONLD;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

/**
 * CLASS for set up a RDF graph and export it
 *
 * @author Florian Thiery M.Sc.
 * @author i3mainz - Institute for Spatial Information and Surveying Technology
 * @version 25.01.2016
 */
public class RDF {

	private Model model = null;

	/**
	 * Constructor
	 */
	public RDF() {
		model = ModelFactory.createDefaultModel();
	}

	/**
	 * set triple with literal
	 *
	 * @param subject
	 * @param predicate
	 * @param object
	 * @throws java.sql.SQLException
	 */
	public void setModelLiteral(String subject, String predicate, String object) throws SQLException {
		try {
			Resource s = model.createResource(subject);
			Property p = model.createProperty(predicate);
			Literal o = model.createLiteral(object);
			model.add(s, p, o);
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * set triple with literal and language
	 *
	 * @param subject
	 * @param predicate
	 * @param object
	 * @param lang
	 * @throws java.sql.SQLException
	 */
	public void setModelLiteralLanguage(String subject, String predicate, String object, String lang) throws SQLException {
		try {
			Resource s = model.createResource(subject);
			Property p = model.createProperty(predicate);
			Literal o = model.createLiteral(object, lang);
			model.add(s, p, o);
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * set triple with uri
	 *
	 * @param subject
	 * @param predicate
	 * @param object
	 * @throws java.sql.SQLException
	 */
	public void setModelURI(String subject, String predicate, String object) throws SQLException {
		try {
			Resource s = model.createResource(subject);
			Property p = model.createProperty(predicate);
			Resource o = model.createResource(object);
			model.add(s, p, o);
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * set triple with blank node
	 *
	 * @param subject
	 * @param predicate
	 * @return
	 * @throws java.sql.SQLException
	 */
	public Resource setModelBlankNode(String subject, String predicate) throws SQLException {
		try {
			Resource s = model.createResource(subject);
			Property p = model.createProperty(predicate);
			Resource o = model.createResource();
			model.add(s, p, o);
			return o;
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * set triple and create model statement automaticly
	 * @param subject
	 * @param predicate
	 * @param object 
	 * @throws java.sql.SQLException 
	 */
	public void setModelTriple(String subject, String predicate, String object) throws SQLException {
		try {
			if (object.startsWith("http://") || object.contains("mailto")) {
				setModelURI(subject, predicate, object);
			} else {
				if (object.contains("@")) {
					String literalLanguage[] = object.split("@");
					setModelLiteralLanguage(subject, predicate, literalLanguage[0].replaceAll("\"", ""), literalLanguage[1]);
				} else {
					setModelLiteral(subject, predicate, object.replaceAll("\"", ""));
				}
			}
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * get RDF model as RDF/XML
	 *
	 * @return
	 * @throws java.sql.SQLException
	 */
	public String getModel() throws SQLException {
		try {
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			model.write(o, "RDF/XML");
			model.removeAll();
			return o.toString("UTF-8");
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

	/**
	 * get RDF model in several formats
	 * [Turtle,N-Triples,RDF/XML,RDF/JSON,TriG,NQuads]
	 *
	 * @param format
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws java.sql.SQLException
	 */
	public String getModel(String format) throws UnsupportedEncodingException, SQLException {
		// https://jena.apache.org/documentation/io/rdf-output.html#jena_model_write_formats
		try {
			JenaJSONLD.init();
			ByteArrayOutputStream o = new ByteArrayOutputStream();
			model.setNsPrefix("example", "http://example.com/vocab/");
			model.setNsPrefix("ec", "http://eurocris.org/ontologies/cerif/1.3#");
			model.setNsPrefix("ecext", "http://eurocris.org/ontologies/cerifX/1.3#");
			model.setNsPrefix("dc", "http://purl.org/dc/elements/1.1/");
			model.setNsPrefix("geo", "http://www.w3.org/2003/01/geo/wgs84_pos#");
			model.write(o, format);
			model.removeAll();
			return o.toString("UTF-8");
		} catch (Exception e) {
			throw new SQLException("[" + RDF.class.getName() + " | " + Thread.currentThread().getStackTrace()[1].getMethodName() + ": " + e + "]");
		}
	}

}
