package graph;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;

import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.net.URL;
import java.io.FileNotFoundException;

import java.util.LinkedList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShortestPathsTest {


    /* Returns the Graph loaded from the file with filename fn. */
    private Graph loadBasicGraph(String fn) {
        Graph result = null;
        try {
            result = ShortestPaths.parseGraph("basic", fn);
        } catch (FileNotFoundException e) {
            fail("Could not find graph " + fn);
        }
        return result;
    }

    /**
     * Dummy test case demonstrating syntax to create a graph from scratch.
     * Write your own tests below.
     */
    @Test
    public void test00Nothing() {
        Graph g = new Graph();
        Node a = g.getNode("A");
        Node b = g.getNode("B");
        g.addEdge(a, b, 1);

        // sample assertion statements:
        assertTrue(true);
        assertEquals(2 + 2, 4);
    }

    /**
     * Minimal test case to check the path from A to B in Simple0.txt
     */
    @Test
    public void test01Simple0() {
        Graph g = loadBasicGraph("Simple0.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);
        Node b = g.getNode("B");
        LinkedList<Node> abPath = sp.shortestPath(b);
        assertEquals(abPath.size(), 2);
        assertEquals(abPath.getFirst(), a);
        assertEquals(abPath.getLast(), b);
        assertEquals(sp.shortestPathLength(b), 1.0, 1e-6);
    }

    /**
     * Test case to check the path between multiple nodes in Simple1.txt
     */
    @Test
    public void test02Simple1() {
        Graph g = loadBasicGraph("Simple1.txt");
        g.report();
        ShortestPaths sp = new ShortestPaths();
        Node a = g.getNode("A");
        sp.compute(a);
        Node c = g.getNode("C");
        LinkedList<Node> acPath = sp.shortestPath(c);
        assertEquals(3, acPath.size()); // Assuming path A -> B -> C
        assertEquals(acPath.getFirst(), a);
        assertEquals(acPath.getLast(), c);
    }
@Test
	public void testSimple0() {
	    Graph g = loadBasicGraph("Simple0.txt");
	    g.report();
	    ShortestPaths sp = new ShortestPaths();
	    Node a = g.getNode("A");
	    sp.compute(a);
	    Node b = g.getNode("B");
	    LinkedList<Node> abPath = sp.shortestPath(b);
	    assertEquals(2, abPath.size());
	    assertEquals(a, abPath.getFirst());
	    assertEquals(b, abPath.getLast());
	    assertEquals(1.0, sp.shortestPathLength(b), 1e-6);
	}

	@Test
	public void testSimple1() {
	    Graph g = loadBasicGraph("Simple1.txt");
	    g.report();
	    ShortestPaths sp = new ShortestPaths();
	    Node s = g.getNode("S");
	    sp.compute(s);
	    Node a = g.getNode("A");
	    LinkedList<Node> saPath = sp.shortestPath(a);
	    assertEquals(2, saPath.size());
	    assertEquals(s, saPath.getFirst());
	    assertEquals(a, saPath.getLast());
	    assertEquals(10.0, sp.shortestPathLength(a), 1e-6);
	    Node c = g.getNode("C");
	    LinkedList<Node> scPath = sp.shortestPath(c);
	    assertEquals(2, scPath.size());
	    assertEquals(s, scPath.getFirst());
	    assertEquals(c, scPath.getLast());
	    assertEquals(5.0, sp.shortestPathLength(c), 1e-6);
	}

	@Test
	public void testSimple2() {
	    Graph g = loadBasicGraph("Simple2.txt");
	    g.report();
	    ShortestPaths sp = new ShortestPaths();
	    Node d = g.getNode("D");
	    sp.compute(d);
	    Node a = g.getNode("A");
	    LinkedList<Node> daPath = sp.shortestPath(a);
	    assertEquals(2, daPath.size());
	    assertEquals(d, daPath.getFirst());
	    assertEquals(a, daPath.getLast());
	    assertEquals(4.0, sp.shortestPathLength(a), 1e-6);
	    Node h = g.getNode("H");
	    LinkedList<Node> dhPath = sp.shortestPath(h);
	    assertEquals(2, dhPath.size());
	    assertEquals(d, dhPath.getFirst());
	    assertEquals(h, dhPath.getLast());
	    assertEquals(1.0, sp.shortestPathLength(h), 1e-6);
	}
	
	//   graph with one edge
    @Test
    public void testCompute_SimpleGraph() {
        Graph graph = new Graph();
        Node start = new Node("A");
        Node end = new Node("B");
        graph.addEdge(start, end, 1);
        ShortestPaths sp = new ShortestPaths();
        sp.compute(start);
        assertEquals(1, sp.shortestPathLength(end), 1e-6);
        List<Node> shortestPath = sp.shortestPath(end);
        assertEquals(2, shortestPath.size());
        assertEquals(start, shortestPath.get(0));
        assertEquals(end, shortestPath.get(1));
    }

 //  Graph with multiple edges
    @Test
    public void testCompute_MultipleEdges() {
        Graph graph = new Graph();
        Node start = new Node("A");
        Node end = new Node("C");
        graph.addEdge(start, new Node("B"), 1);
        graph.addEdge(new Node("B"), end, 2);
        graph.addEdge(start, end, 4); // longer path
        ShortestPaths sp = new ShortestPaths();
        sp.compute(start);
        assertEquals(3, sp.shortestPathLength(end), 1e-6);
        List<Node> shortestPath = sp.shortestPath(end);
        assertEquals(3, shortestPath.size());
        assertEquals(start, shortestPath.get(0));
        assertEquals(new Node("B"), shortestPath.get(1));
        assertEquals(end, shortestPath.get(2));
    }

 // Graph with negative weight edge
    @Test
    public void testCompute_NegativeWeightEdge() {
        Graph graph = new Graph();
        Node start = new Node("A");
        Node end = new Node("B");
        graph.addEdge(start, end, -1);
        ShortestPaths sp = new ShortestPaths();
        sp.compute(start);
        assertEquals(-1, sp.shortestPathLength(end), 1e-6);
        List<Node> shortestPath = sp.shortestPath(end);
        assertEquals(2, shortestPath.size());
        assertEquals(start, shortestPath.get(0));
        assertEquals(end, shortestPath.get(1));
    }

    // Graph with no path to destination
    @Test
    public void testCompute_NoPath() {
        Graph graph = new Graph();
        Node start = new Node("A");
        Node end = new Node("B");
        ShortestPaths sp = new ShortestPaths();
        sp.compute(start);
        assertEquals(Double.POSITIVE_INFINITY, sp.shortestPathLength(end), 1e-6);
        assertNull(sp.shortestPath(end));
    }

    // Graph with same node as start and end
    @Test
    public void testCompute_SameNode() {
        Graph graph = new Graph();
        Node node = new Node("A");
        ShortestPaths sp = new ShortestPaths();
        sp.compute(node);
        assertEquals(0, sp.shortestPathLength(node), 1e-6);
        List<Node> shortestPath = sp.shortestPath(node);
        assertEquals(1, shortestPath.size());
        assertEquals(node, shortestPath.get(0));
    }

    // Graph with multiple nodes and edges
    @Test
    public void testCompute_MultipleNodesEdges() {
        Graph graph = new Graph();
        Node start = new Node("A");
        Node end1 = new Node("B");
        Node end2 = new Node("C");
        graph.addEdge(start, end1, 1);
        graph.addEdge(start, end2, 2);
        graph.addEdge(end1, end2, 1);
        ShortestPaths sp = new ShortestPaths();
        sp.compute(start);
        assertEquals(1, sp.shortestPathLength(end1), 1e-6);
        assertEquals(2, sp.shortestPathLength(end2), 1e-6);
        List<Node> shortestPath1 = sp.shortestPath(end1);
        assertEquals(2, shortestPath1.size());
        assertEquals(start, shortestPath1.get(0));
        assertEquals(end1, shortestPath1.get(1));
        List<Node> shortestPath2 = sp.shortestPath(end2);
        assertEquals(2, shortestPath2.size());
        assertEquals(start, shortestPath2.get(0));
        assertEquals(end2, shortestPath2.get(1));
    }
    
 // Graph with edge to itself
    @Test
    public void testCompute_EdgeToItself() {
        Graph graph = new Graph();
        Node node = new Node("A");
        graph.addEdge(node, node, 1); // edge to itself
        ShortestPaths sp = new ShortestPaths();
        sp.compute(node);
        assertEquals(0, sp.shortestPathLength(node), 1e-6);
        List<Node> shortestPath = sp.shortestPath(node);
        assertEquals(1, shortestPath.size());
        assertEquals(node, shortestPath.get(0));
    }

    
}
