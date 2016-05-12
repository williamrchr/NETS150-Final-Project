import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;



public class Algorithms {
	
	private List<Node> nodes;
	public Algorithms(List<Node> nodes) {
		this.nodes = nodes;
	}
	
	/**
	 * Calculates the degree centrality of all the nodes in the graph, only taking into account the
	 * incoming edges
	 * @return hashmap of names to double values
	 */
	public HashMap<String, Double> getINDegreeCentrality() {
		HashMap<String, Double> centrality = new HashMap();
		double maxEdges = nodes.size() - 1;
		for (Node curr : nodes) {
			double degreeCentrality = curr.getInDegree() / maxEdges;
			centrality.put(curr.getName(), degreeCentrality);
			
		}
		return centrality;
	}

	
	/**
	 * Calculates the node strength for every node, only taking into account the incoming edges
	 * @return hashmap of names to integer values
	 */
	public HashMap<String, Integer> getInNodeStrength() {
		HashMap<String, Integer> nodeStrength = new HashMap();
		for (Node curr : nodes) {
			int strength = 0;
			HashMap<Node, Integer> neighbors = curr.getIncomingEdges();
			Collection<Integer> weights = neighbors.values();
			for (Integer w : weights) {
				strength = strength + w;
			}
			nodeStrength.put(curr.getName(), strength);
		}
		
		return nodeStrength;
	}
	
	
	/**
	 * This method performs a variation on the standard PageRank algorithm. This algorithm takes into 
	 * account the weights on the edges while assigning new pagerank values
	 * @return hashmap with node and pagerank values
	 */
	public HashMap<Node, Tuple> pageRank() {
		HashMap<Node, Tuple> ranks = new HashMap();
		// initially set PageRank to 1/n for each node
		for (Node curr : nodes) {
			Tuple t = new Tuple();
			t.currRank = 1.0 / nodes.size();
			ranks.put(curr, t);
		}
		while (true) {
			// update PageRank for all nodes
			for (Node curr : nodes) {
				HashMap<Node, Integer> neighbors = curr.getOutgoingEdges();
				int degree = neighbors.size();
				// If the node has no outgoing edges, I distribute its PageRank amongst all the nodes
				// in the graph to eliminate the issue of sinks.
				if (degree == 0) {
					double rank = ranks.get(curr).currRank;
					double distribute = rank / nodes.size();
					for (Node currTwo : nodes) {
						ranks.get(currTwo).nextRank += distribute;
					}
					continue;
				}
				double currRanking = ranks.get(curr).currRank;
				int totalWeight = findTotalWeight(curr);
				Node[] people = neighbors.keySet().toArray(new Node[0]);
				// distribute PageRank through all outgoing edges
				for (int i = 0; i < people.length; i++) {
					// distribute PageRank according to how much the edge weight is
					double outgoingRank = currRanking * (neighbors.get(people[i]) / (totalWeight * 1.0));
					Tuple t = ranks.get(people[i]);
					t.nextRank = t.nextRank + outgoingRank;
					
				}
			}
			// if the new PageRank is the same as the old PageRank, break
			if (rankHelper(ranks)) {
				break;
			}
			// set the current PageRank equal to the new PageRank, and set the new PageRank equal to 0
			for (Node curr : nodes) {				
				double newRank = ranks.get(curr).nextRank;
				ranks.get(curr).nextRank = 0;
				ranks.get(curr).currRank = newRank;	
			}
		}
		return ranks;		
	}
	/**
	 * Helper function for PageRank used to determine whether or not we have reached equilibrium
	 * @param ranks
	 * @return a boolean value to determine whether we reached equilibrium
	 */
	private boolean rankHelper(HashMap<Node, Tuple> ranks) {
		Node[] people = ranks.keySet().toArray(new Node[0]);
		for (int i = 0; i < people.length; i++) {
			if (Math.abs(ranks.get(people[i]).nextRank - ranks.get(people[i]).currRank) 
					> .0000000000000000001) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Helper function for PageRank that finds the total weight of all outgoing edges combined
	 * @param node
	 * @return the weight
	 */
	private int findTotalWeight(Node node) {
		int totalWeight = 0;
		HashMap<Node, Integer> neighbors = node.getOutgoingEdges();
		Collection<Integer> weights = neighbors.values();
		for (Integer weight : weights) {
			totalWeight = totalWeight + weight;
		}
		return totalWeight;
	}
	
}
