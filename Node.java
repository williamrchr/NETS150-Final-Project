import java.util.HashMap;


/**
 * This class creates a node for a weighted directed graph. Each node represents a congressperson.
 * The incoming edges and the outgoing edges are represented by two different HashMaps. The key is
 * a node, the value is an integer, representing the weight of an edge.  
 * @author kathdix
 *
 */
public class Node {
	
	private String name;
	private HashMap<Node, Integer> incomingEdges;
	private HashMap<Node, Integer> outgoingEdges;
	
	/**
	 * This constructor is used to create a node. 
	 * @param name- the name of the congressperson
	 */
	public Node(String name) {
		this.name = name;
		incomingEdges = new HashMap();
		outgoingEdges = new HashMap();
	}
	
	/**
	 * A getter for the name of the congressperson associated with the node
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * A getter for the incoming edges
	 * @return a hashmap representing the incoming edges
	 */
	public HashMap<Node, Integer> getIncomingEdges() {
		return incomingEdges;
	}
	
	/**
	 * A getter for the outgoing edges
	 * @return a hashmap represeting the outgoing edges
	 */
	public HashMap<Node, Integer> getOutgoingEdges() {
		return outgoingEdges;
	}
	
	/**
	 * A getter for the indegree of a node
	 * @return an integer representing the indegree
	 */
	public int getInDegree() {
		return incomingEdges.size();
	}
	
	/**
	 * A getter for the outdegree of a node
	 * @return an integer represeting the indegree
	 */
	public int getOutDegree() {
		return outgoingEdges.size();
	}
	
	/**
	 * This method adds a new incoming edge from another node. If this node is not already a key in the 
	 * incoming edges hashmap, the key is added to the hashmap with a value of 1. Otherwise, if the
	 * key is in the hashmap, the integer value is increased by 1 to increase the weight of the edge
	 * @param name
	 */
	public void addIncomingEdge(Node name) {
		if (incomingEdges.containsKey(name)) {
			int weight = incomingEdges.get(name);
			incomingEdges.put(name, weight + 1);
		} else {
			incomingEdges.put(name, 1);
		}
	}
	
	/**
	 * This method adds a new outgoing edge from another node. If this node is not already a key in the 
	 * incoming edges hashmap, the key is added to the hashmap with a value of 1. Otherwise, if the
	 * key is in the hashmap, the integer value is increased by 1 to increase the weight of the edge
	 * @param name
	 */
	public void addOutgoingEdge(Node name) {
		if (outgoingEdges.containsKey(name)) {
			int weight = outgoingEdges.get(name);
			outgoingEdges.put(name, weight + 1);
		} else {
			outgoingEdges.put(name, 1);
		}
	}
	
	/**
	 * Gets neighborhood overlap of in degree neighbors
	 * 
	 * @param name of other node
	 */
	public double getInNeighborhoodOverlap(Node name) {
		if(name == null || (!incomingEdges.keySet().contains(name) && !outgoingEdges.keySet().contains(name))) {
			return 0;
		}
		
		double count = 0;
		for(Node n : incomingEdges.keySet()) {
			if(n != null && name.getIncomingEdges().containsKey(n)) {
				count++;
			}
		}
		double both = getInDegree() + name.getInDegree();
		if(getIncomingEdges().containsKey(name)) {
			System.out.println("aaa");
			both--;
		}
		if(name.getIncomingEdges().containsKey(getName())) {
			System.out.println("bbb");
			both--;
		}
		
		both = both - count;
		
		System.out.println("count: " + count);
		System.out.println("both: " + both);
		return(count / both);
	}
	
	/**
	 * Gets neighborhood overlap of out degree neighbors
	 * 
	 * @param name of other node
	 */
	public double getOutNeighborhoodOverlap(Node name) {
		if(name == null || (!incomingEdges.keySet().contains(name) && !outgoingEdges.keySet().contains(name))) {
			return 0;
		}
		
		
		double count = 0;
		for(Node n : outgoingEdges.keySet()) {
			if(n != null && name.getOutgoingEdges().containsKey(n)) {
				count++;
			}
		}
		double both = getOutDegree() + name.getOutDegree();
		if(getOutgoingEdges().containsKey(name)) {
			both--;
		}
		if(name.getOutgoingEdges().containsKey(getName())) {
			both--;
		}
		
		both = both - count;
		return(count / both);
	}
	
	/**
	 * Get out degree clustering coefficient:
	 * the number of neighbors who are connected to each other / the number of neighbors' possible edges
	 * 	
	 */
	public double getOutClusteringCoefficient() {
		if(getOutDegree() < 2) {
			return 0;
		}
		double factorial = getOutDegree() * (getOutDegree() - 1);
		
		double count = 0;
		for(Node n : outgoingEdges.keySet()) {
			for(Node m : outgoingEdges.keySet()) {
				if(n.getOutgoingEdges().containsKey(m)) {
					count++;
				}
			}
		}
		
		System.out.println("count: " + count);
		System.out.println("factorial: " + factorial);
		return (count / factorial);
	}
	
	/**
	 * Get in degree clustering coefficient:
	 * the number of neighbors who are connected to each other / the number of neighbors' possible edges
	 * 	
	 */
	public double getInClusteringCoefficient() {
		if(getInDegree() < 2) {
			return 0;
		}
		double factorial = getInDegree() * (getInDegree() - 1);
		
		double count = 0;
		for(Node n : incomingEdges.keySet()) {
			for(Node m : incomingEdges.keySet()) {
				if(n.getIncomingEdges().containsKey(m)) {
					count++;
				}
			}
		}
		
		System.out.println("count: " + count);
		System.out.println("factorial: " + factorial);
		return (count / factorial);
	}


}
