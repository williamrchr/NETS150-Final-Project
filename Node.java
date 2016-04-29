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
	public int getOutDegreee() {
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
}
