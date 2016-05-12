import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * This class creates a weighted directed graph representing the connections between 
 * different people in congress.
 * @author kathdix
 *
 */
public class Graph {
	HashMap<String, List<String>> connections;
	List<Node> nodes;
	List<String> congresspeople;
	
	/**
	 * This constructor creates a new graph by calling the makeGraph method
	 * @param connections- a HashMap where the key is a String, representing a sponsor's name and the 
	 * value is a linkedlist of cosponsors who support the sponsor
	 * @param- congresspeople- a list of the names of all the sponsors and cosponsors who will be in
	 * the graph
	 */
	public Graph(HashMap<String, List<String>> connections, List<String> congresspeople) {
		nodes = new LinkedList();
		this.connections = connections;
		this.congresspeople = congresspeople;
	
		makeGraph();
	}
	
	/**
	 * This method creates the nodes and all the edges in the graph. First, it makes a node for
	 * each sponsor and cosponsor using the congresspeople List. Then, it creates the incoming and 
	 * outgoing edges using the information in the connections HashMap. 
	 * @param name
	 */
	private void makeGraph() {
		// Make the nodes
		for (String curr: congresspeople) {
			Node newNode = new Node(curr);
			nodes.add(newNode);
		}
		// Make an array of all sponsors
		String[] sponsors = connections.keySet().toArray(new String[0]);
<<<<<<< HEAD
=======
		
>>>>>>> origin/master
		// Add the incoming and outgoing edges to the graph
		for (int i = 0; i < sponsors.length; i++) {
			List<String> cosponsors = connections.get(sponsors[i]);
			// Get the node corresponding to the sponsor's name
			Node sponsor = getNode(sponsors[i]);
			for (String curr: cosponsors) {
<<<<<<< HEAD
				// Get the node corresponding to the cosponsor's name
=======
				// Get the node corressponding to the cosponsor's name
>>>>>>> origin/master
				Node cosponsor = getNode(curr);
				// add an incoming edge from the cosponsor to the sponsor
				sponsor.addIncomingEdge(cosponsor);
				// add an outgoing edge from the cosponsor to the sponsor 
<<<<<<< HEAD
				//System.out.println(cosponsor.getName() + "   " + sponsor.getName());
				cosponsor.addOutgoingEdge(sponsor);
			}
			
=======
				cosponsor.addOutgoingEdge(sponsor);
			}
			//System.out.println("The sponsor " + sponsor.getName() + " has " + sponsor.getIncomingEdges().size() + " cosponsors.");
>>>>>>> origin/master
		}
	}
	/**
	 * This helper method finds the node that corressponds to a name
	 * @param name
	 * @return the node that corresponds with the name 
	 */
<<<<<<< HEAD
	public Node getNode(String name) {
=======
	private Node getNode(String name) {
>>>>>>> origin/master
		for (Node curr: nodes) {
			if (curr.getName().equals(name)) {
				return curr;
			}
		}
		return null;
	}
	
	/**
	 * A getter for all the nodes in the graph
	 * @return a list of nodes
	 */
	public List<Node> getNodes() {		
		return nodes;
	}
}
