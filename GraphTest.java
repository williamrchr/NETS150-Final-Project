import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;
import org.junit.Test;

public class GraphTest {

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
	public void testOverlap1() {		
		ArrayList<String> people = new ArrayList<String>();
		HashMap<String, List<String>> connections = new HashMap<String, List<String>>();
		people.add("A");
		people.add("B");
		people.add("C");
		
		ArrayList<String> A = new ArrayList<String>();
		A.add("B");
		
		ArrayList<String> B = new ArrayList<String>();
		B.add("A");
	
		ArrayList<String> C = new ArrayList<String>();
		C.add("A");
		C.add("B");
		
		connections.put("A", A);
		connections.put("B", B);
		connections.put("C", C);
		Graph g = new Graph(connections, people);
		
		Node a = g.getNode("A");
		Node b = g.getNode("B");
		Node c = g.getNode("C");
		System.out.println("Test 1 In NOverlap " + a.getInNeighborhoodOverlap(c));
		System.out.println();
		System.out.println("Test 2 In NOverlap " + c.getInNeighborhoodOverlap(a));
		System.out.println();
	}
	
	@Test
	public void test1() {		
		ArrayList<String> people = new ArrayList<String>();
		HashMap<String, List<String>> connections = new HashMap<String, List<String>>();
		people.add("N");
		people.add("A");
		people.add("B");
		people.add("C");
		
		ArrayList<String> N = new ArrayList<String>();
		
		ArrayList<String> A = new ArrayList<String>();
		A.add("N");
		A.add("B");
			
		ArrayList<String> B = new ArrayList<String>();
		B.add("N");
		B.add("A");
	
		ArrayList<String> C = new ArrayList<String>();
		C.add("N");
		C.add("A");
		
		connections.put("N", N);
		connections.put("A", A);
		connections.put("B", B);
		connections.put("C", C);
		Graph g = new Graph(connections, people);
		
		Node n = g.getNode("N");
		System.out.println("Test 1 Out Clustering Coefficient: " + n.getOutClusteringCoefficient());
		System.out.println();
		Node b = g.getNode("B");
		System.out.println("Test 1 In Clustering Coefficient: " + b.getInClusteringCoefficient());
		System.out.println();
	}
	
	@Test
	public void test2() {		
		ArrayList<String> people = new ArrayList<String>();
		HashMap<String, List<String>> connections = new HashMap<String, List<String>>();
		people.add("N");
		people.add("A");
		people.add("B");
		people.add("C");
		
		ArrayList<String> N = new ArrayList<String>();
		
		ArrayList<String> A = new ArrayList<String>();
		A.add("B");
			
		ArrayList<String> B = new ArrayList<String>();
		B.add("A");
	
		ArrayList<String> C = new ArrayList<String>();
		C.add("A");
		
		connections.put("N", N);
		connections.put("A", A);
		connections.put("B", B);
		connections.put("C", C);
		Graph g = new Graph(connections, people);
		
		Node n = g.getNode("N");
		System.out.println("Test 2 Out Clustering Coefficient: " + n.getOutClusteringCoefficient());
		System.out.println();
		
		Node b = g.getNode("B");
		System.out.println("Test 2 In Clustering Coefficient: " + b.getInClusteringCoefficient());
		System.out.println();
	}
	
	@Test
	public void test3() {
		ArrayList<String> people = new ArrayList<String>();
		HashMap<String, List<String>> connections = new HashMap<String, List<String>>();
		people.add("A");
		people.add("B");
		people.add("C");
		people.add("D");
		people.add("E");
		
		ArrayList<String> A = new ArrayList<String>();
		A.add("B");
			
		ArrayList<String> B = new ArrayList<String>();
		B.add("E");
	
		ArrayList<String> C = new ArrayList<String>();
		C.add("A");
		
		ArrayList<String> D = new ArrayList<String>();
		D.add("A");
		D.add("E");
		D.add("C");
		
		ArrayList<String> E = new ArrayList<String>();
		E.add("B");
		E.add("A");
		
		connections.put("A", A);
		connections.put("B", B);
		connections.put("C", C);
		connections.put("D", D);
		connections.put("E", E);
		
		Graph g = new Graph(connections, people);
		Node a = g.getNode("A");
		System.out.println("Test 3 Out Clustering Coefficient: " + a.getOutClusteringCoefficient());
		System.out.println();
		Node d = g.getNode("D");
		System.out.println("Test 3 InClustering Coefficient: " + d.getInClusteringCoefficient());
		System.out.println();
	}
	
	
	@Test
	public void testCongress() {
		JSONObject json = ApiReaderEdited.getJSON(url);
		HashMap<String, List<String>> test = ApiReaderEdited.getMap(json);
		List<String> congresspeople = ApiReaderEdited.getCongressmen();
		Graph graph = new Graph(test, congresspeople);
		Node g = graph.getNode("george miller");
		Node n = graph.getNode("nancy johnson");
		Node b = graph.getNode("barton gordon");
		Node e = graph.getNode("edolphus towns");
		Node p = graph.getNode("patrick kennedy");
		Node jh = graph.getNode("john boehner");
		Node jd = graph.getNode("judy biggert");
		Node gg = graph.getNode("george gekas");
		

		HashMap<Node, Integer> hm = n.getOutgoingEdges();
		for(Node i : hm.keySet()) {
			System.out.println("outneighborhood: " + n.getOutNeighborhoodOverlap(i));
			System.out.println("inneighborhood: " + n.getOutNeighborhoodOverlap(i));
		}
	
		
		System.out.println();
		System.out.println("george miller: ");
		System.out.println();
		System.out.println("outneighborhood: g-n " + g.getOutNeighborhoodOverlap(n));
		System.out.println("inneighborhood: g-n " + g.getOutNeighborhoodOverlap(n));
		System.out.println("outneighborhood: g-b " + g.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: g-b " + g.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: g-jd " + g.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: g-jd " + g.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: g-e " + g.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: g-e " + g.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + g.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("nancy johnson: ");
		System.out.println();
		System.out.println("outneighborhood: n-g " + n.getOutNeighborhoodOverlap(g));
		System.out.println("inneighborhood: n-g " + n.getOutNeighborhoodOverlap(g));
		System.out.println("outneighborhood: n-b " + n.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: n-b " + n.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: n-jd " + n.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: n-jd " + n.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: n-e " + n.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: n-e " + n.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + n.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("barton gordon: ");
		System.out.println();
		System.out.println("outneighborhood: b-g " + b.getOutNeighborhoodOverlap(g));
		System.out.println("inneighborhood: b-g " + b.getOutNeighborhoodOverlap(g));
		System.out.println("outneighborhood: b-n " + b.getOutNeighborhoodOverlap(n));
		System.out.println("inneighborhood: b-n " + b.getOutNeighborhoodOverlap(n));
		System.out.println("outneighborhood: b-jd " + b.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: b-jd " + b.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: b-e " + b.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: b-e " + b.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + b.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("edolphus towns: ");
		System.out.println();
		System.out.println("outneighborhood: e-g " + e.getOutNeighborhoodOverlap(g));
		System.out.println("inneighborhood: e-g " + e.getOutNeighborhoodOverlap(g));
		System.out.println("outneighborhood: e-n " + e.getOutNeighborhoodOverlap(n));
		System.out.println("inneighborhood: e-n " + e.getOutNeighborhoodOverlap(n));
		System.out.println("outneighborhood: e-jd " + e.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: e-jd " + e.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: e-b " + e.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: e-b " + e.getOutNeighborhoodOverlap(b));
		System.out.println("clustering coefficient " + e.getOutClusteringCoefficient());
		System.out.println();
		

		System.out.println();
		System.out.println("judy biggert: ");
		System.out.println();
		System.out.println("outneighborhood: jd-g " + jd.getOutNeighborhoodOverlap(g));
		System.out.println("inneighborhood: jd-g " + jd.getOutNeighborhoodOverlap(g));
		System.out.println("outneighborhood: jd-n " + jd.getOutNeighborhoodOverlap(n));
		System.out.println("inneighborhood: jd-n " + jd.getOutNeighborhoodOverlap(n));
		System.out.println("clustering coefficient " + jd.getOutClusteringCoefficient());
		System.out.println("outneighborhood: jd-b " + jd.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: jd-b " + jd.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: jd-e " + jd.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: jd-e " + jd.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + jd.getOutClusteringCoefficient());
		System.out.println();
		
	}

}
