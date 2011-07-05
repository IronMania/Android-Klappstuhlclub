package sem.android;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.auth.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.impl.client.DefaultHttpClient;

import android.R.bool;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.query.* ;

public class sparqlApi{

	public final static String SPARQL_ENDPOINT = "http://139.18.2.172:8893/sparql";
	private final static String SPARQL_ENDPOINT_AUTH = "http://139.18.2.172:8893/sparql-auth";
	private final String URI = "http://klappstuhlclub.de/"; 
	private final String LOGIN_NAME = "dba";
	private final String stringPW = "vIGsEcRuGA32";

	private final String propertyStartDate = "http://www.w3.org/2002/12/cal#dtstart";
	private final String propertyLat = 	"http://www.w3.org/2003/01/geo/wgs84_pos#lat";
	private final String propertyLon = 	"http://www.w3.org/2003/01/geo/wgs84_pos#long";
	private final String propertyComment ="http://www.w3.org/2000/01/rdf-schema#comment" ;
	private final String propertyLabel = "http://www.w3.org/2000/01/rdf-schema#label";
	private final String propertyTreffen = "http://www.w3.org/1999/02/22-rdf-syntax-ns#type";
	private final String conceptMeeting= "http://www.klappstuhlclub.de/wp/posts/";
	private final String conceptTreffen = "<http://klappstuhlclub.de/ontology/Treffen>";

	public sparqlApi() {
	}

	public boolean createMeeting(MessageMeeting meeting) 
	{
		int meetingNumber= getLastMeetingIndex();
		meetingNumber++;
		XSDDateTime date = new XSDDateTime(meeting.getDate());
		String dateString = date.toString();
		String lat = String.valueOf(meeting.getLatitude());
		String lon = String.valueOf(meeting.getLongitude());
		String request = SPARQL_ENDPOINT_AUTH +"?default-graph-uri=&query=insert+data+into+%3Chttp%3A%2F%2Fklappstuhlclub.de%2F%3E+%0A++%7B+" +
				// Concept +  "%3E+%3C" + property +  "%3E+%3C" + Range + "%3E+." +
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyTreffen, conceptTreffen) +
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyLabel, "\""+meetingNumber + ". Treffen Leipzig\" @de") +
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyComment,"\""+ meeting.getMeetingComment() + "\"") +
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyStartDate, "\"%5C\"" + dateString  + "%5C\"^^<http://www.w3.org/2001/XMLSchema#date>\"" ) +
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyLat, "\"" +lat +"\"") +	
				createTriple(conceptMeeting + meetingNumber +"-treffen-leipzig", propertyLon, "\"" +lon +"\"") +	
				"%0A++%7D%0A&debug=on&timeout=&format=text%2Fhtml&save=display&fname=";
		request = request.replaceAll("#", "%23");
		request = request.replaceAll("\\^", "%5E");
		request = request.replaceAll("<", "%3C");
		request = request.replaceAll(">", "%3E");
		request = request.replaceAll("\\s+", "+");
		request = request.replaceAll("\"", "%22");
//		request = request.replaceAll("\\", "%5C");
		
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(LOGIN_NAME, stringPW);

		DefaultHttpClient httpClient = new DefaultHttpClient();
		List <String> authpref = new ArrayList<String>();
		authpref.add(AuthPolicy.BASIC);
		httpClient.getParams().setParameter("http.auth.proxy-scheme-pref", authpref);
		httpClient.getCredentialsProvider().setCredentials(AuthScope.ANY, creds);

		HttpGet get = new HttpGet(request);

		try {
			httpClient.execute(get);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public MessageMeeting getNextMeeting()
	{
		MessageMeeting msg = new MessageMeeting();
		String queryString = "select distinct ?label ?comment ?startdate ?latitude ?longitude from <http://klappstuhlclub.de/> where{" +
				"?a <" + propertyStartDate + "> ?startdate .	" +
				"?a <" + propertyLat + "> ?latitude ." +
				"?a <" + propertyLon + "> ?longitude . " +
				"?a <" + propertyComment + "> ?comment . " +
				"?a <" + propertyLabel + "> ?label. " +
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
				" from <" + URI +
				"> where {" +
				"?lastmeeting <http://www.w3.org/2002/12/cal#dtstart> ?startdate ." +
				"} ORDER BY DESC (?startdate) LIMIT 1" ;

		QueryExecution queryExec = QueryExecutionFactory.sparqlService(SPARQL_ENDPOINT, queryString);

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
	private String createTriple(String concept, String property, String range)
	{
		return "%3C" + concept + "%3E+%3C" +property +"%3E+" +range + "+." ;
	}
}
