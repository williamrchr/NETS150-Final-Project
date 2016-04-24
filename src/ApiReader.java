import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class is used as a static method class to interface with the GovTrack
 * API in JSON format. Dependencies are Google's JSON Parser and Tokenizer
 * JAR.
 * 
 * @author	William Archer
 * 
 * 
 */
public class ApiReader {
	
	/**
	 * 
	 * read is a private helper function used to parse all the way through
	 * a specified reader. It is not meant to be used in main functions
	 * and only helps create the JSONObject used in getJSON.
	 * 
	 * @param	reader - the reader to read through
	 * @return	String - string of the entire JSON response
	 * @author William Archer
	 * 
	 */
	
	private static String read(Reader reader) {
		StringBuilder retVal = new StringBuilder();
		int character;
		try {
			//read returns char unless EOF, which is -1
			character = reader.read();
			while(character != -1) { //not done
				retVal.append((char) character);
				character = reader.read();
			}
			return retVal.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
		
	}
	
	/**
	 * This method produces a JSONObject from an HTTP call. It's genericized
	 * for any type of API, however it assumes the url is correctly formatted
	 * 
	 * @see	org.json
	 * @param url - url call to make
	 * @return	JSONObject from response by API URL
	 * @author William Archer
	 */
	public static JSONObject getJSON(String url) {
		
		try {
			//open up the url and get the bytes 
			InputStream is = new URL(url).openStream();
			InputStreamReader input = new InputStreamReader(is);
			String json = read(new BufferedReader(input));
			is.close(); //don't forget to close!
			return new JSONObject(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	/**
	 * getMap creates a HashMap of all bills in the json parameter of all
	 * sponsors and their various co-sponsors. It utilizes the govTrack.us
	 * API
	 * 
	 * 
	 * @param json - the json response previously gotten
	 * @return - HashMap of all sponsors and co-sponsors in all lowercase
	 * 			 we use a List because we want to see if a sponsor and
	 * 			 co-sponsor have worked together multiple times
	 * @author William Archer
	 */
	public static HashMap<String,List<String>> getMap(JSONObject json) {
		HashMap<String,List<String>> retVal = new HashMap<String, List<String>>();
		
		Set<String> ids = new HashSet<String>(); //capture all bill ids
		try {
			
			JSONArray test = json.getJSONArray("objects");
			//parse response to get bill ids
			for(int i=0; i < test.length(); i++) {
				JSONObject object = (JSONObject) test.get(i);
				ids.add(object.get("id").toString());
			}
			String baseUrl = "https://www.govtrack.us/api/v2/bill/";
			
			
			//API doesn't include co-sponsor information if querying so
			//you have to request each individual bill...
			for(String id : ids) { 
				JSONObject object = getJSON(baseUrl+id);
				
				JSONObject congress = object.getJSONObject("sponsor");
				
				//building congressperson's name
				String congressperson = (congress.getString("firstname") 
						+ " " + congress.getString("lastname")).toLowerCase();
				
				JSONArray cosponsors = object.getJSONArray("cosponsors");
				
				//co-sponsors are stored in a JSONArray which
				//we iterate through to get everything and store
				for(int i = 0; i < cosponsors.length(); i++) {
					JSONObject cosponsor = cosponsors.getJSONObject(i);
					String cosponsorName = (cosponsor.getString("firstname") 
							+ " " + cosponsor.getString("lastname")).toLowerCase();
					
					
					//if we haven't encountered the sponsor before
					//we need to create a new list
					if(retVal.get(congressperson) == null) {
						List<String> newSet = new LinkedList<String>();
						newSet.add(cosponsorName);
						retVal.put(congressperson,newSet);
					} else {
						retVal.get(congressperson).add(cosponsorName);
					}
					
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retVal;
	}
	
}
