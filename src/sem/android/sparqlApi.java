package sem.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import android.content.Context;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.datatypes.xsd.*;

public class sparqlApi{
	
	final static String RDF_URI = "http://139.18.2.172:8893/sparql";
	private final String URI = "http://klappstuhlclub.de/"; 
	int meetingNumber = 1;
	private Model rdfGraph;
	
	public sparqlApi() {

		 // create an empty model
		 rdfGraph= ModelFactory.createDefaultModel();

		 //loadOnlineModel();
		 loadDummyModel();
		 
	}
	
	private void loadOnlineModel()
	{
		// use the FileManager to find the input file
				 InputStream in = FileManager.get().open( RDF_URI);
				 if (in == null) {
					    throw new IllegalArgumentException(
					                                 "File: " + RDF_URI + " not found");
					}

					// read the RDF/XML file
				 rdfGraph.read(in, null);

					// write it to standard out
				 rdfGraph.write(System.out);
	}
	private void loadDummyModel()
	{
		rdfGraph = ModelFactory.createDefaultModel();
		
		// some definitions
		String latitude     = "51.339647";
		String longitude     = "12.371285";
		createMeeting(longitude, latitude, Calendar.getInstance(),"Testmeeting");

		rdfGraph.write(System.out);
		meetingNumber ++;

		// some definitions
		latitude     = "51.336939";
		longitude     = "12.377937";
		createMeeting(longitude, latitude, Calendar.getInstance(),"Testmeeting2");

		rdfGraph.write(System.out);
		
	}
	
	public void createMeeting(String longitude, String latitude, Calendar date, String text)
	{
		//TODO: Generate the next Meeting Number
		
		
		// create the resource
		Resource meeting = rdfGraph.createResource(URI + meetingNumber);
		
				
		// add the property for Coordinates
		Property lon = rdfGraph.createProperty(URI + "longitude");
		Property lat = rdfGraph.createProperty(URI + "latitude");
		meeting.addProperty(lon, longitude);
		 meeting.addProperty(lat, latitude);
		 
		 
		 //adding the Date
		 Literal lDate = rdfGraph.createTypedLiteral(date);
		 Property pDate = rdfGraph.createProperty(URI + "MeetingDate");
		 rdfGraph.add(meeting ,pDate, lDate);
		 
		 //adding the meeting Text
		 meeting.addProperty(rdfGraph.createProperty(URI + "MeetingText"), text);
		 
	}
	
	public void getNextMeeting()
	{
		
	}
}
