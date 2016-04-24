import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
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
		
		//debug print statement - System.out.println(url);
		
		
		/*
		 * Test example, takes about 2 minutes to run depending on
		 * internet connection, be patient!
		 * 
		 */
		JSONObject json = ApiReader.getJSON(url);
		HashMap<String, List<String>> test = ApiReader.getMap(json);
		System.out.println(test.get("george voinovich").toString());
		
		
		
	}

}
