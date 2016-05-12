import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.json.JSONObject;
import java.util.Arrays;


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
		
		/*debug print statement - */System.out.println(url);
		
		
		/*
		 * Test example, takes about 2 minutes to run depending on
		 * internet connection, be patient!
		 * 
		 */
		JSONObject json = ApiReaderEdited.getJSON(url);
		// get the mapping of sponsors to all of their cosponsors
		HashMap<String, List<String>> test = ApiReaderEdited.getMap(json);
		// get the list of all the people in the graph
		List<String> people = ApiReaderEdited.getCongressmen();
		// create the weighted directed graph
		Graph g = new Graph(test, people);
		// get the list of nodes in the graph
		List<Node> n = g.getNodes();
		// start graph algorithms
		Algorithms a = new Algorithms(n);
		
		// find top ten congresspeople with in the highest indegree centrality (names not listed in order)
		HashMap<String, Double> in = a.getINDegreeCentrality();
		Double[] inArr = in.values().toArray(new Double[0]);
		Arrays.sort(inArr);
		String[] names = in.keySet().toArray(new String[0]);
		for (int i = 0; i < names.length; i++) {
			if (in.get(names[i]) >= inArr[inArr.length - 10]) {
				System.out.println("Congressperson " + names[i] + 
						" has an indegree centrality of " + in.get(names[i]));
			}
		}
		System.out.println("");
		// find top ten congresspeople with in the highest indegree nodestrength (names not listed in order)
		HashMap<String, Integer> inStrength = a.getInNodeStrength();
		Integer[] inStrArr = inStrength.values().toArray(new Integer[0]);
		Arrays.sort(inStrArr);
		String[] namesThree = inStrength.keySet().toArray(new String[0]);
		for (int i = 0; i < namesThree.length; i++) {
			if (inStrength.get(namesThree[i]) >= inStrArr[inStrArr.length - 10]) {
				System.out.println("Congressperson " + namesThree[i] + 
						" has an in nodestrength of " + inStrength.get(namesThree[i]));
			}
		}
		System.out.println("");
		// find the top ten people with the highest page ranks (names not listed in order 
		HashMap<Node, Tuple> ranks = a.pageRank();
		Double[] allRanks = new Double[n.size()];
		int counter = 0;
		for (Node curr : n) {
			allRanks[counter] = ranks.get(curr).currRank;
			counter++;
		}
		Arrays.sort(allRanks);
		for (Node curr : n) {
			if (ranks.get(curr).currRank >= allRanks[allRanks.length - 10]) {
				System.out.println("Congressperson " + curr.getName() + " has a PageRank of " + ranks.get(curr).currRank);
			}
		}
		
		
		/**
		 * Neighborhood Overlap and Clustering Coefficient Section
		 */
		
		//Graph graph = new Graph(test, people);
		Node gm = g.getNode("george miller");
		Node nj = g.getNode("nancy johnson");
		Node b = g.getNode("barton gordon");
		Node e = g.getNode("edolphus towns");
		Node p = g.getNode("patrick kennedy");
		Node jh = g.getNode("john boehner");
		Node jd = g.getNode("judy biggert");
		Node gg = g.getNode("george gekas");
		

		HashMap<Node, Integer> hm = nj.getOutgoingEdges();
		for(Node i : hm.keySet()) {
			System.out.println("outneighborhood: " + nj.getOutNeighborhoodOverlap(i));
			System.out.println("inneighborhood: " + nj.getOutNeighborhoodOverlap(i));
		}
	
		
		System.out.println();
		System.out.println("george miller: ");
		System.out.println();
		System.out.println("outneighborhood: g-n " + gm.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: g-n " + gm.getOutNeighborhoodOverlap(nj));
		System.out.println("outneighborhood: g-b " + gm.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: g-b " + gm.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: g-jd " + gm.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: g-jd " + gm.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: g-e " + gm.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: g-e " + gm.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + gm.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("nancy johnson: ");
		System.out.println();
		System.out.println("outneighborhood: n-g " + nj.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: n-g " + nj.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: n-b " + nj.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: n-b " + nj.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: n-jd " + nj.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: n-jd " + nj.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: n-e " + nj.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: n-e " + nj.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + nj.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("barton gordon: ");
		System.out.println();
		System.out.println("outneighborhood: b-g " + b.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: b-g " + b.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: b-n " + b.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: b-n " + b.getOutNeighborhoodOverlap(nj));
		System.out.println("outneighborhood: b-jd " + b.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: b-jd " + b.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: b-e " + b.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: b-e " + b.getOutNeighborhoodOverlap(e));
		System.out.println("clustering coefficient " + b.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("edolphus towns: ");
		System.out.println();
		System.out.println("outneighborhood: e-g " + e.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: e-g " + e.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: e-n " + e.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: e-n " + e.getOutNeighborhoodOverlap(nj));
		System.out.println("outneighborhood: e-jd " + e.getOutNeighborhoodOverlap(jd));
		System.out.println("inneighborhood: e-jd " + e.getOutNeighborhoodOverlap(jd));
		System.out.println("outneighborhood: e-b " + e.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: e-b " + e.getOutNeighborhoodOverlap(b));
		System.out.println("clustering coefficient " + e.getOutClusteringCoefficient());
		System.out.println();
		

		System.out.println();
		System.out.println("judy biggert: ");
		System.out.println();
		System.out.println("outneighborhood: jd-g " + jd.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: jd-g " + jd.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: jd-n " + jd.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: jd-n " + jd.getOutNeighborhoodOverlap(nj));
		System.out.println("clustering coefficient " + jd.getOutClusteringCoefficient());
		System.out.println("outneighborhood: jd-b " + jd.getOutNeighborhoodOverlap(b));
		System.out.println("inneighborhood: jd-b " + jd.getOutNeighborhoodOverlap(b));
		System.out.println("outneighborhood: jd-e " + jd.getOutNeighborhoodOverlap(e));
		System.out.println("inneighborhood: jd-e " + jd.getOutNeighborhoodOverlap(e));
		System.out.println();
		
		System.out.println();
		System.out.println("patrick kennedy: ");
		System.out.println();
		System.out.println("outneighborhood: p-g " + p.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: p-g " + p.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: p-n " + p.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: p-n " + p.getOutNeighborhoodOverlap(nj));
		System.out.println("clustering coefficient " + p.getOutClusteringCoefficient());
		System.out.println();
		
		System.out.println();
		System.out.println("john boehner: ");
		System.out.println();
		System.out.println("outneighborhood: jh-g " + jh.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: jh-g " + jh.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: jh-n " + jh.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: jh-n " + jh.getOutNeighborhoodOverlap(nj));
		System.out.println("clustering coefficient " + jh.getOutClusteringCoefficient());
		System.out.println();
		
		
		System.out.println();
		System.out.println("george gekas: ");
		System.out.println();
		System.out.println("outneighborhood: gg-g " + gg.getOutNeighborhoodOverlap(gm));
		System.out.println("inneighborhood: gg-g " + gg.getOutNeighborhoodOverlap(gm));
		System.out.println("outneighborhood: gg-n " + gg.getOutNeighborhoodOverlap(nj));
		System.out.println("inneighborhood: gg-n " + gg.getOutNeighborhoodOverlap(nj));
		System.out.println("clustering coefficient " + gg.getOutClusteringCoefficient());
		System.out.println();
		

		
		
		
	}
}