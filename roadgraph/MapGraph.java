/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which reprsents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
package roadgraph;


import geography.GeographicPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.function.Consumer;

import util.GraphLoader;

/**
 * @author UCSD MOOC development team and YOU
 * 
 * A class which represents a graph of geographic locations
 * Nodes in the graph are intersections between 
 *
 */
public class MapGraph {
	//TODO: Add your member variables here in WEEK 2
	private HashMap<GeographicPoint,MapNode> vertices;
	private int numEdges;
	private int numVertices;
	
	/** 
	 * Create a new empty MapGraph 
	 */
	public MapGraph()
	{
		vertices = new HashMap<GeographicPoint,MapNode>();
	}
	
	/**
	 * Get the number of vertices (road intersections) in the graph
	 * @return The number of vertices in the graph.
	 */
	public int getNumVertices()
	{
		//TODO: Implement this method in WEEK 2
		return numVertices;
	}
	
	/**
	 * Return the intersections, which are the vertices in this graph.
	 * @return The vertices in this graph as GeographicPoints
	 */
	public Set<GeographicPoint> getVertices()
	{
		//TODO: Implement this method in WEEK 2
		return vertices.keySet();
	}
	
	/**
	 * Get the number of road segments in the graph
	 * @return The number of edges in the graph.
	 */
	public int getNumEdges()
	{
		//TODO: Implement this method in WEEK 2
		return numEdges;
	}

	
	
	/** Add a node corresponding to an intersection at a Geographic Point
	 * If the location is already in the graph or null, this method does 
	 * not change the graph.
	 * @param location  The location of the intersection
	 * @return true if a node was added, false if it was not (the node
	 * was already in the graph, or the parameter is null).
	 */
	public boolean addVertex(GeographicPoint location)
	{
		// TODO: Implement this method in WEEK 2
		if(vertices.containsKey(location))
			return false;
		vertices.put(location,new MapNode(location));
		numVertices++;
		return true;
	}
	
	/**
	 * Adds a directed edge to the graph from pt1 to pt2.  
	 * Precondition: Both GeographicPoints have already been added to the graph
	 * @param from The starting point of the edge
	 * @param to The ending point of the edge
	 * @param roadName The name of the road
	 * @param roadType The type of the road
	 * @param length The length of the road, in km
	 * @throws IllegalArgumentException If the points have not already been
	 *   added as nodes to the graph, if any of the arguments is null,
	 *   or if the length is less than 0.
	 */
	public void addEdge(GeographicPoint from, GeographicPoint to, String roadName,
			String roadType, double length) throws IllegalArgumentException {
		if(!(vertices.containsKey(from))||!(vertices.containsKey(from))||roadName==null||roadType==null||length<0){
			throw new IllegalArgumentException();
			}
		else{
			MapEdge edge=new MapEdge(from,to,roadName,roadType,length);
			vertices.get(from).addNeighbor(edge);
			vertices.get(to).addNeighbor(edge);
			numEdges++;
			}
		}
	

	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return bfs(start, goal, temp);
	}
	
	/** Find the path from start to goal using breadth first search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest (unweighted)
	 *   path from start to goal (including both start and goal).
	 */
	public List<GeographicPoint> bfs(GeographicPoint start, 
			 					     GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		Queue<MapNode> queue=new LinkedList<MapNode>();
		HashSet<MapNode> visited=new HashSet<MapNode>();
		HashMap<MapNode,MapNode> Parent=new HashMap<>();
		boolean found=false;
		queue.add(vertices.get(start));
		visited.add(vertices.get(start));
		MapNode curr=null;
		//Implementation of BFS starts
		while(!queue.isEmpty()){
			curr=queue.poll();
			nodeSearched.accept(curr.getLocation());
			if(curr.equals(vertices.get(goal)))
			{
				found = true;
				break;
			}
			else
			{
				List<MapEdge> neighbors=curr.getNeighbors();
				for(MapEdge n:neighbors)
				{
					MapNode m=vertices.get(n.getEnd());
					if(!visited.contains(m))
					{
						visited.add(m);
						Parent.put(m,curr);
						queue.add(m);
					}
				}
			}
		}
		if(found)
		{
			return TraceBack(Parent,curr);
		}
		return null;
	}
	
	/**
	 * Traces through the Parent HashMap to create
	 * a list of nodes (GeographicPoint) to traverse to get from
	 * start to goal.
	 * @param parent the Parent HashMap.
	 * @param curr The Final MapNode associated with the Goal.
	 * @return the Path from start to goal in the form of an ArrayList.
	 */
	private List<GeographicPoint> TraceBack(HashMap<MapNode, MapNode> parent,
			MapNode curr) 
			{
				List<GeographicPoint> path=new ArrayList<>();
				path.add(curr.getLocation());
				MapNode next=curr;
				while(parent.containsKey(next))
					{
						path.add(parent.get(next).getLocation());
						next=parent.get(next);
					}
				Collections.reverse(path);
				return path;
			}
	
	/**
	 * Helper method useful for debugging
	 * 
	 * @param g
	 * @return MapNode Corresponding to the location specified.
	 */
	public MapNode getNode(GeographicPoint g){
		return vertices.get(g);
	}
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
		// You do not need to change this method.
        Consumer<GeographicPoint> temp = (x) -> {};
        return dijkstra(start, goal, temp);
	}
	
	/** Find the path from start to goal using Dijkstra's algorithm
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> dijkstra(GeographicPoint start, 
										  GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		PriorityQueue<MapNode> queue=new PriorityQueue<MapNode>();
		HashSet<MapNode> visited=new HashSet<MapNode>();
		HashMap<MapNode,MapNode> Parent=new HashMap<>();
		boolean found=false;
		vertices.get(start).setStartDistance(0.0);
		MapNode curr=null;
		queue.add(vertices.get(start));
		int count1=0;
		int count2=0;
		while(!queue.isEmpty())
		{
			curr=queue.poll();
			count1++;
			nodeSearched.accept(curr.getLocation());
			if(!visited.contains(curr))
			{
				//System.out.println("ENter 1");
				visited.add(curr);
				if(curr.equals(vertices.get(goal)))
				{
					//System.out.println("Found!!");
					found=true;
					break;
				}
				else
				{
					List<MapEdge> neighbors=curr.getNeighbors();
					for(MapEdge n:neighbors)
					{
						MapNode m=vertices.get(n.getEnd());
						if(!visited.contains(m))
						{	
							m.setGoalDistance(0.0);
							//System.out.println(m.getStartDistance()+">"+(curr.getStartDistance()+n.getDistance()));
							if(m.getStartDistance()>(curr.getStartDistance()+n.getDistance())){
								m.setStartDistance(curr.getStartDistance()+n.getDistance());
								Parent.put(m, curr);
								queue.add(m);
								count2++;
								//System.out.println("Added!");
							}
						}
					}
				
				}
			}
		}
		if(found)
		{
			System.out.println("Djikstra:"+count1);
			System.out.println("Djikstra:"+count2);
			return TraceBack(Parent,curr);
		}
		
		System.out.println("Djikstra:"+count1);
		System.out.println("Djikstra:"+count2);
		//System.out.println("Not FOund!!");
		return null;
		
	}

	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, GeographicPoint goal) {
		// Dummy variable for calling the search algorithms
        Consumer<GeographicPoint> temp = (x) -> {};
        return aStarSearch(start, goal, temp);
	}
	
	/** Find the path from start to goal using A-Star search
	 * 
	 * @param start The starting location
	 * @param goal The goal location
	 * @param nodeSearched A hook for visualization.  See assignment instructions for how to use it.
	 * @return The list of intersections that form the shortest path from 
	 *   start to goal (including both start and goal).
	 */
	public List<GeographicPoint> aStarSearch(GeographicPoint start, 
											 GeographicPoint goal, Consumer<GeographicPoint> nodeSearched)
	{
		// TODO: Implement this method in WEEK 3
		PriorityQueue<MapNode> queue=new PriorityQueue<MapNode>();
		HashSet<MapNode> visited=new HashSet<MapNode>();
		HashMap<MapNode,MapNode> Parent=new HashMap<>();
		boolean found=false;
		vertices.get(start).setStartDistance(0.0);
		MapNode curr=null;
		queue.add(vertices.get(start));
		int count1=0;
		int count2=0;
		while(!queue.isEmpty())
		{
			curr=queue.poll();
			count1++;
			nodeSearched.accept(curr.getLocation());
			if(!visited.contains(curr))
			{
				//System.out.println("ENter 1");
				visited.add(curr);
				if(curr.equals(vertices.get(goal)))
				{
					//System.out.println("Found!!");
					found=true;
					break;
				}
				else
				{
					List<MapEdge> neighbors=curr.getNeighbors();
					for(MapEdge n:neighbors)
					{
						MapNode m=vertices.get(n.getEnd());
						if(!visited.contains(m))
						{	
							m.setGoalDistance(m.getLocation().distance(goal));
							//System.out.println(m.getStartDistance()+">"+(curr.getStartDistance()+n.getDistance()));
							if(m.getStartDistance()>(curr.getStartDistance()+n.getDistance())){
								m.setStartDistance(curr.getStartDistance()+n.getDistance());
								Parent.put(m, curr);
								queue.add(m);
								count2++;
								//System.out.println("Added!");
							}
						}
					}
				
				}
			}
		}
		if(found)
		{	
			System.out.println("A*:"+count1);
			System.out.println("A*:"+count2);
			return TraceBack(Parent,curr);
		}
		
		System.out.println("A*:"+count1);
		System.out.println("A*:"+count2);
		
		//System.out.println("Not FOund!!");
		return null;
	}

	
	
	public static void main(String[] args)
	{
		/**
		System.out.print("Making a new map...");
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/testdata/simpletest.map", theMap);
		System.out.println("DONE.");
		*/
		// You can use this method for testing.  
		
		//Use this code in Week 3 End of Week Quiz
		MapGraph theMap = new MapGraph();
		System.out.print("DONE. \nLoading the map...");
		GraphLoader.loadRoadMap("data/maps/utc.map", theMap);
		System.out.println("DONE.");

		GeographicPoint start = new GeographicPoint(32.8648772, -117.2254046);
		GeographicPoint end = new GeographicPoint(32.8660691, -117.217393);
		
		
		List<GeographicPoint> route = theMap.dijkstra(start,end);
		List<GeographicPoint> route2 = theMap.aStarSearch(start,end);

		
		
	}
	
}
