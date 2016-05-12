import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

public class ApiReaderEditedTest {

	//URL Builder
	String baseUrl = "https://www.govtrack.us/api/v2/";
	String type = "bill"; //can be any of the major types as outlined in govtrack.us documentation
	String query = "student+debt"; //what we want to query
	
	//what we want to get, and how far back we want to go
	//date format is YYYY-MM-DD
	//Over 1100 bills in this range, more than API can return
	String fields= "sponsor,id&introduced_date__gte=2000-01-01";
	String size = "600"; //limit for govTrack API response
	String url = baseUrl+type+"?q="+query+"&fields="+fields+"&limit="+size;
	
	@Test
	public void testGet() {
		JSONObject json = ApiReaderEdited.getJSON(url);
		HashMap<String, List<String>> test = ApiReaderEdited.getMap(json);
		List<String> congresspeople = ApiReaderEdited.getCongressmen();
		Graph graph = new Graph(test, congresspeople);
		//List<Node> nodes = graph.getNodes();
		//Node n = graph.getNode("george miller");
		
		//System.out.println("Sponsors of george " + test.get("george miller").toString());
	}
}
