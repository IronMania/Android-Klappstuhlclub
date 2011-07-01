package sem.android;

import java.io.InputStream;
import java.util.*;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.datatypes.xsd.*;
import com.hp.hpl.jena.query.* ;

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
		 getNextMeeting();
		 getNextMeeting();
		 
	}
	
	@SuppressWarnings("unused")
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
		String latitude     = "12.377937";
		String longitude     = "51.336939";
		createMeeting(longitude, latitude, Calendar.getInstance(),"Testmeeting");

		rdfGraph.write(System.out);
		meetingNumber ++;

		// some definitions
		latitude     = "12.377937";
		longitude     = "51.336939";
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
		 XSDDateTime xsdDate = new XSDDateTime(date);
		 Literal lDate = rdfGraph.createTypedLiteral(xsdDate);
		 Property pDate = rdfGraph.createProperty(URI + "startDate");
		 rdfGraph.add(meeting ,pDate, lDate);
		 
		 //adding the meeting Text
		 meeting.addProperty(rdfGraph.createProperty(URI + "MeetingText"), text);
		 
	}
	
	@SuppressWarnings("unused")
	public MessageMeeting getNextMeeting()
	{
		MessageMeeting msg = new MessageMeeting();
		  String queryString = "select distinct ?meeting ?startDate ?longitude ?latitude where {?meeting <http://klappstuhlclub.de/startDate> ?startDate . ?meeting <http://klappstuhlclub.de/longitude> ?longitude . ?meeting <http://klappstuhlclub.de/longitude> ?latitude} ORDER BY DESC(?startDate) LIMIT 1" ;
		  Query query = QueryFactory.create(queryString) ;
		  QueryExecution qexec = QueryExecutionFactory.create(query, rdfGraph) ;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("?meeting") ;       // Get a result variable by name.
		      Literal l = soln.getLiteral("?startDate") ;   // Get a result variable - must be a literal
//		      XSDDatatype test = new Baset
//		      XSDDateTime time = new XSDDateTime(l.toString(), XSDDateTime.FULL_MASK);
//		      TODO: msg.setDate(time.asCalendar());
		      msg.setLongitude(soln.getLiteral("?longitude").getFloat());
		      msg.setLatitude(soln.getLiteral("?latitude").getFloat());
		    }
		  } finally { qexec.close() ; }
		//TODO: return a Meeting and add the text to the query string
		  return msg;
	}
	
	public int getLastMeetingIndex()
	{
		  String queryString = "select distinct ?a ?b where {?a <http://klappstuhlclub.de/startDate> ?b} ORDER BY DESC(?b) LIMIT 1" ;
		  Query query = QueryFactory.create(queryString) ;
		  QueryExecution qexec = QueryExecutionFactory.create(query, rdfGraph) ;
		  RDFNode lastMeeting;
		  try {
		    ResultSet results = qexec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      RDFNode x = soln.get("?a") ;       // Get a result variable by name.
		      lastMeeting = x;
			  String a[] = lastMeeting.toString().split("/");
			  return Integer.parseInt( a[ a.length]);
//		      Resource r = soln.getResource("VarR") ; // Get a result variable - must be a resource
//		      Literal l = soln.getLiteral("?b") ;   // Get a result variable - must be a literal
		    }
		  } finally { qexec.close() ; }
//		  String a[] = lastMeeting.toString().split("/");
//		  return Integer.parseInt( a[ a.length]);
		return 0;
	}
}
