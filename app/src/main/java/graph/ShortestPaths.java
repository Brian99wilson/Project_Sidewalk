package graph;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
/** Provides an implementation of Dijkstra's single-source shortestpaths
 * algorithm
 * Sample usage:
 *   Graph g = // create your graph
 *   ShortestPaths sp = new ShortestPaths();
 *   Node a = g.getNode("A");
 *   sp.compute(a);
 *   Node b = g.getNode("B");
 *   LinkedList<Node> abPath = sp.getShortestPath(b);
 *   double abPathLength = sp.getShortestPathLength(b);
 *   */
public class ShortestPaths {
    // stores auxiliary data associated with each node for the shortest
    // path's computation:
    private HashMap<Node,PathData> paths;

    /** Compute the shortest path to all nodes from origin using Dijkstra's
     * algorithm. Fill in the paths field, which associates each Node with its
     * PathData record, storing total distance from the source, and the
     * back pointer to the previous node on the shortest path.
     * Precondition: origin is a node in the Graph.*/
    public void compute(Node origin) {
        paths = new HashMap<Node, PathData>();
        // TODO 1: implement Dijkstra's algorithm to fill paths with
        // shortest-path data for each Node reachable from origin.
        // Initialize distances and priority queue
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> {
            double distA = paths.get(a).distance;
            double distB = paths.get(b).distance;
            return Double.compare(distA, distB);
        });

        paths.put(origin, new PathData(0, null));
        pq.add(origin);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            double currentDistance = paths.get(current).distance;

            for (Map.Entry<Node, Double> entry : current.getNeighbors().entrySet()) {
                Node neighbor = entry.getKey();
                double weight = entry.getValue();
                double newDistance = currentDistance + weight;

                if (!paths.containsKey(neighbor) || newDistance < paths.get(neighbor).distance) {
                    paths.put(neighbor, new PathData(newDistance, current));
                    pq.add(neighbor);
                }
            }
        }
    }
    /** Returns the length of the shortest path from the origin to destination.
     * If no path exists, return Double.POSITIVE_INFINITY.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public double shortestPathLength(Node destination) {
        // TODO 2 - implement this method to fetch the shortest path length
        // from the paths data computed by Dijkstra's algorithm.

        // Ensure that the paths map is initialized
        // Additional check: throw an error if compute(origin) hasn't been called.
        if (paths == null) {
            throw new IllegalStateException("Paths data is not initialized. Call compute(origin) first.");
        }

        // Retrieve the PathData for the destination node from the paths map
        PathData pathData = paths.get(destination);

        // If no entry exists in the paths map for the destination node, it means that
        // the destination is unreachable from the origin.
        // Return infinity to indicate no path exists.
        if (pathData == null) {
            return Double.POSITIVE_INFINITY;
        }

        // If the PathData exists, return the stored shortest distance to the destination
        return pathData.distance;
    }

    /** Returns a LinkedList of the nodes along the shortest path from origin
     * to destination. This path includes the origin and destination. If origin
     * and destination are the same node, it is included only once.
     * If no path to it exists, return null.
     * Precondition: destination is a node in the graph, and compute(origin)
     * has been called. */
    public LinkedList<Node> shortestPath(Node destination) {
        // TODO 3 - implement this method to reconstruct sequence of Nodes
        // along the shortest path from the origin to destination using the
        // paths data computed by Dijkstra's algorithm.
        throw new UnsupportedOperationException();
    }


    /** Inner class representing data used by Dijkstra's algorithm in the
     * process of computing shortest paths from a given source node. */
    class PathData {
        double distance; // distance of the shortest path from source
        Node previous; // previous node in the path from the source

        /** constructor: initialize distance and previous node */
        public PathData(double dist, Node prev) {
            distance = dist;
            previous = prev;
        }
    }


    /** Static helper method to open and parse a file containing graph
     * information. Can parse either a basic file or a CSV file with
     * sidewalk data. See GraphParser, BasicParser, and DBParser for more.*/
    protected static Graph parseGraph(String fileType, String fileName) throws
        FileNotFoundException {
        // create an appropriate parser for the given file type
        GraphParser parser;
        if (fileType.equals("basic")) {
            parser = new BasicParser();
        } else if (fileType.equals("db")) {
            parser = new DBParser();
        } else {
            throw new IllegalArgumentException(
                    "Unsupported file type: " + fileType);
        }

        // open the given file
        parser.open(new File(fileName));

        // parse the file and return the graph
        return parser.parse();
    }

public static void main(String[] args) {
//     Validate required arguments
    if (args.length < 3) { // Check if fewer than 3 arguments are provided
        System.out.println("Error: Insufficient arguments provided. Expected at least 3 arguments:");
        System.out.println("Usage: <fileType> <fileName> <SidewalkOrigCode> [SidewalkDestCode]");
        return; // Exit the program
    }

    // Parse arguments
    String fileType = args[0];
    String fileName = args[1];
    String SidewalkOrigCode = args[2];

    // Handle optional destination code
    String SidewalkDestCode = "/Users/brian_wilson/Comp Year 2/DataStuctNAlgo2/Final Project/Program4-skeleton/app/Simple0.txt";
    if (args.length >= 4) { // Check if an optional 4th argument is provided
        SidewalkDestCode = args[3];
    } else {
        System.out.println("No destination code provided. Default behavior will be applied.");
    }

    // Parse a graph with the given type and filename
    Graph graph;
    try {
        graph = parseGraph(fileType, fileName);
    } catch (FileNotFoundException e) {
        System.out.println("Could not open file " + fileName);
        return;
    }

    // Report graph statistics
    graph.report();

    // Further processing for shortest paths
    // TODO 4:
    ShortestPaths sp = new ShortestPaths();
    Node originNode = graph.getNode(SidewalkOrigCode);

    // Compute shortestpaths from originNode
    sp.compute(originNode);

    if (SidewalkDestCode == null) {
        // TODO 5: Handle no destination code case
        // Example: Print reachable nodes and shortest path lengths
        for (Node node : graph.getNodes().values()) {
            double shortestPath = sp.shortestPathLength(node);
            System.out.println("Reachable Node: " + node + ", Shortest Path: " + shortestPath);
        }
    } else {
        // TODO 6: Handle when destination code is provided
        Node destinationNode = graph.getNode(SidewalkDestCode);
        LinkedList<Node> path = sp.shortestPath(destinationNode);
        if (path == null) {
            System.out.println("No path exists from " + SidewalkOrigCode + " to " + SidewalkDestCode);
        } else {
            System.out.println("Shortest Path: " + path);
            System.out.println("Total Path Length: " + sp.shortestPathLength(destinationNode));
        }
    }
}
}