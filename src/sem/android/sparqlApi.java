package sem.android;


import com.hp.hpl.jena.query.* ;
import com.hp.hpl.jena.sparql.engine.http.HttpQuery;
import com.hp.hpl.jena.sparql.engine.http.QueryEngineHTTP;
import com.hp.hpl.jena.update.*;

public class sparqlApi{
	
	public final static String SPARQL_ENDPOINT = "http://139.18.2.172:8893/sparql";
	private final static String SPARQL_ENDPOINT_AUTH = "http://139.18.2.172:8893/sparql-auth";
	private final String URI = "http://klappstuhlclub.de/"; 
	private final String LOGIN_NAME = "dba";
	private char [] PW;
	
	public sparqlApi() {
		String bla = "vIGsEcRuGA32";
		PW = bla.toCharArray();
	}
	
	public void createMeeting(MessageMeeting meeting)
	{
		int meetingNumber= getLastMeetingIndex();
//		String executionQuery = "insert data into <http://klappstuhlclub.de/> {" +
//				"<firsttest> <test1> <test2> ." +
//				"}"; 		
//		
//		QueryEngineHTTP test = new QueryEngineHTTP(SPARQL_ENDPOINT_AUTH, executionQuery);
//		HttpQuery query = new HttpQuery(SPARQL_ENDPOINT_AUTH);
//		query.setBasicAuthentication(LOGIN_NAME, PW); 
//		
//				
//		test.setBasicAuthentication(LOGIN_NAME, PW );
//		test.execSelect();
//				
//		// add the property for Coordinates
//		Property lon = rdfGraph.createProperty(URI + "longitude");
//		Property lat = rdfGraph.createProperty(URI + "latitude");
//		meeting.addProperty(lon, longitude.toString());
//		 meeting.addProperty(lat, latitude.toString());
//		 
//		 
//		 //adding the Date
//		 XSDDateTime xsdDate = new XSDDateTime(date);
//		 Literal lDate = rdfGraph.createTypedLiteral(xsdDate);
//		 Property pDate = rdfGraph.createProperty(URI + "startDate");
//		 rdfGraph.add(meeting ,pDate, lDate);
//		 
//		 //adding the meeting Text
//		 meeting.addProperty(rdfGraph.createProperty(URI + "MeetingText"), text);
		 
	}
	
	public MessageMeeting getNextMeeting()
	{
		MessageMeeting msg = new MessageMeeting();
		String queryString = "select distinct ?label ?comment ?startdate ?latitude ?longitude from <http://klappstuhlclub.de/> where{" +
				"?a <http://www.w3.org/2002/12/cal#dtstart> ?startdate .	" +
				"?a <http://www.w3.org/2003/01/geo/wgs84_pos#lat> ?latitude ." +
				"?a <http://www.w3.org/2003/01/geo/wgs84_pos#long> ?longitude . " +
				"?a <http://www.w3.org/2000/01/rdf-schema#comment> ?comment . " +
				"?a <http://www.w3.org/2000/01/rdf-schema#label> ?label. " +
				"} ORDER BY DESC(?startdate) LIMIT 1";  
		QueryExecution queryExec = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT, queryString);
		  try {
		    ResultSet results = queryExec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
//		      TODO: parse Startdate and save it in the meeting
//		      Literal startdate = soln.getLiteral("?startdate");
		      msg.setMeetingComment(soln.getLiteral("?comment").toString());
		      msg.setMeetingLabel(soln.getLiteral("?label").toString());
		      msg.setLongitude(soln.getLiteral("?longitude").getDouble());
		      msg.setLatitude(soln.getLiteral("?latitude").getDouble());
		    }
		  } finally { queryExec.close() ; }
		  return msg;
	}
	
	public int getLastMeetingIndex()
	{
		  String queryString = "select distinct ?lastmeeting" +
		  		" from <http://klappstuhlclub.de/> " +
		  		"where {" +
		  		"?lastmeeting <http://www.w3.org/2002/12/cal#dtstart> ?startdate ." +
		  		"} ORDER BY DESC(?startdate) LIMIT 1" ;
		  
//		  QueryExecution queryExec = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT, queryString);
		  QueryEngineHTTP queryExec= new QueryEngineHTTP(SPARQL_ENDPOINT_AUTH, queryString);
		  queryExec.setBasicAuthentication(LOGIN_NAME, PW);
		   
		  
		  int meetingIndex = 0;
		  try {
		    ResultSet results = queryExec.execSelect() ;
		    for ( ; results.hasNext() ; )
		    {
		      QuerySolution soln = results.nextSolution() ;
		      String x = soln.get("?lastmeeting").toString() ;       // Get a result variable by name.
			  String a[] = x.split("/");
			  String b[] = a[a.length-1].split("-");
			  meetingIndex = Integer.parseInt(b[0]);
		    }
		  } finally { queryExec.close() ; } 
		return meetingIndex;
	}
}
