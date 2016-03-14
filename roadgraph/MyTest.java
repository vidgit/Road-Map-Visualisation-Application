package roadgraph;

import geography.GeographicPoint;

import java.util.Set;

import util.GraphLoader;

public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MapGraph graph = new MapGraph();
		GraphLoader.loadRoadMap("data/graders/mod2/map3.txt", graph);
		Set<GeographicPoint> vertices = graph.getVertices();
		for (GeographicPoint g : vertices) {
			graph.getNode(g).printEdges();

		}
	}

}
