package roadgraph;

import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Vibhor Joshi
 *	MapNode describes a structure to hold the 
 *	GeographicLocation DataType and a List of Neighbors
 *	Using the MapEdge Class.
 */
public class MapNode implements Comparable<MapNode>{
	private GeographicPoint location;
	private List<MapEdge> neighbors;
	private double startDistance;
	private double goalDistance;

	//Constructor.
	public MapNode(GeographicPoint location) {
		this.location = location;
		neighbors = new ArrayList<MapEdge>();
		startDistance=Double.MAX_VALUE;
		goalDistance=Double.MAX_VALUE;
	}

	public double getStartDistance() {
		return startDistance;
	}

	public void setStartDistance(double startDistance) {
		this.startDistance = startDistance;
	}

	/**
	 * 
	 * @param neighbor
	 * @return true if the neighbor has been succesfully added
	 * false if the addition fails.
	 * 
	 */
	public boolean addNeighbor(MapEdge neighbor) {
		if (neighbor.getEnd().equals(this.getLocation()) || neighbor.getStart().equals(this.getLocation())) {
			neighbors.add(neighbor);
			return true; 
		}
		return false;
	}

	/**
	 * 
	 * @return  the Geographic Location associated with the Node.
	 */
	public GeographicPoint getLocation() {
		return location;
	}

	public void setLocation(GeographicPoint location) {
		this.location = location;
	}

	/**
	 * 
	 * @returns a new ArrayList containing neighbors,
	 * so that the original neighbor list is not modified.
	 */
	public List<MapEdge> getNeighbors() {
		return new ArrayList(neighbors);
	}

	public void setNeighbors(List<MapEdge> neighbors) {
		this.neighbors = neighbors;
	}
	
	
	/**
	 * A function to help in debugging
	 */
	public void printEdges() {
		List<MapEdge> edges=this.getNeighbors();
		System.out.println(edges.size());
		System.out.println("From \t\t\t To \t\t\t Name \t\t\t Street Type");
		for(MapEdge edge:edges){
			//System.out.println("Edge "+edge);
			System.out.println(edge.getStart()+" \t "+edge.getEnd()+" \t "+edge.getStreetname()+" \t "+edge.getRoadType());
		}
	}

	@Override
	public int compareTo(MapNode that) {
		if(this.getStartDistance()+this.getGoalDistance()>that.getStartDistance()+that.getGoalDistance())
		{
			return 1;
		}
		else if(this.getStartDistance()+this.getGoalDistance()<that.getStartDistance()+that.getGoalDistance())
		{
			return -1;
		}
		else
			return 0;
	}

	public double getGoalDistance() {
		return goalDistance;
	}

	public void setGoalDistance(double goalDistance) {
		this.goalDistance = goalDistance;
	}
}
