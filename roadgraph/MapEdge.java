package roadgraph;

import geography.GeographicPoint;
/**
 * 
 * @author Vibhor Joshi
 *	MapEdge class describes a Type
 *	to represent the Edge structure in graph by
 *	holding the start, end, street name , distance and road type. 
 */
public class MapEdge {
	/*
	 * Member variables to hold
	 * start ,end and other values.
	 */
	private GeographicPoint start;
	private GeographicPoint end;
	private String streetName;
	private double distance;
	private String roadType;
	
	/**
	 * Constructor
	 * @param start
	 * @param end
	 * @param Streetname
	 * @param roadType
	 * @param distance
	 */
	public MapEdge(GeographicPoint start, GeographicPoint end,
			String Streetname, String roadType, double distance) {
		this.start = start;
		this.end = end;
		this.distance = distance;
		this.streetName = Streetname;
		this.roadType = roadType;
	}

	/**
	 * isStart
	 * @param m: MapNode to be used to check if the Edge starts from there.
	 * @return true if the parameter is the same as the start location of edge
	 * false otherwise
	 */
	public boolean isStart(MapNode m) {
		if (m.getLocation().equals(start))
			return true;
		return false;
	}

	/**
	 * @param m: MapNode to be used to check if the Edge ends there.
	 * @return true if the parameter is the same as the end location of edge
	 * false otherwise
	 */
	public boolean isEnd(MapNode m) {
		if (m.getLocation().equals(end))
			return true;
		return false;
	}
	
	//Method to get the other end of a MapNode
	//@param m map node which is part of Edge
	//@returns corresponding Geographic location
	//or null if edge does not contain node.
	
	/**
	 * Method to get the other end of an edge
	 * useful in debugging
	 * 
	 * @param m: MapNode to be used to return the corresponding other end.
	 * @return The other side of the Edge.
	 * null in case MapNode is neither start nor end of edge.
	 */
	public GeographicPoint otherEnd(MapNode m){
		if(isStart(m)){
			return this.end;
		}
		else if(isEnd(m)){
			return this.start;
		}
		else
			return null;
	}
	
	/**
	 * Getter and Setter Functions for the Data Members 
	 * 
	 */
	
	
	public String getRoadType() {
		return roadType;
	}

	public void setRoadType(String roadType) {
		this.roadType = roadType;
	}

	public GeographicPoint getStart() {
		return start;
	}

	public void setStart(GeographicPoint start) {
		this.start = start;
	}

	public GeographicPoint getEnd() {
		return end;
	}

	public void setEnd(GeographicPoint end) {
		this.end = end;
	}

	public String getStreetname() {
		return streetName;
	}

	public void setStreetname(String streetname) {
		streetName = streetname;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

}
