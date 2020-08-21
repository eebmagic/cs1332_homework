import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of various graph algorithms.
 *
 * @author Ethan Bolton
 * @version 1.0
 * @userid ebolton8
 * @GTID 903368012
 *
 * Collaborators: None
 *
 * Resources: All previous java homework
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("cannot do a search with a null argument");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start node does not appear in the adjacency list for this graph");
        }

        Queue<Vertex<T>> q = new LinkedList<>();
        Set<Vertex<T>> visited = new HashSet<>();

        q.add(start);
        visited.add(start);

        List<Vertex<T>> outOrder = new ArrayList<>();

        while (q.size() != 0) {
            outOrder.add(q.remove());
            for (VertexDistance<T> pair : graph.getAdjList().get(outOrder.get(outOrder.size() - 1))) {
                if (!(visited.contains(pair.getVertex()))) {
                    q.add(pair.getVertex());
                    visited.add(pair.getVertex());
                }
            }
        }

        return outOrder;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * NOTE: You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("cannot do a search with a null argument");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start node does not appear in the adjacency list for this graph");
        }

        Set<Vertex<T>> visited = new HashSet<>();
        List<Vertex<T>> output = new ArrayList<>();

        dfsHelp(start, graph, visited, output);
        return output;
    }

    /**
     * helper method that allows for dfs to operate recursively
     * @param <T> the generic type of the data
     * @param start the node to start the traversal from
     * @param graph the graph to traverse in
     * @param visited the set of nodes that have already been traversed over
     * @param output the order in which nodes have already been visited
     */
    private static <T> void dfsHelp(Vertex<T> start, Graph<T> graph, Set<Vertex<T>> visited, List<Vertex<T>> output) {
        output.add(start);
        visited.add(start);

        for (VertexDistance<T> dist: graph.getAdjList().get(start)) {
            if (!visited.contains(dist.getVertex())) {
                dfsHelp(dist.getVertex(), graph, visited, output);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty yet.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("cannot traverse with a null parameter");
        }
        if (!graph.getAdjList().containsKey(start)) {
            throw new IllegalArgumentException("start node is not in the graph");
        }

        Queue<VertexDistance<T>> q = new PriorityQueue<>();
        Map<Vertex<T>, Integer> output = new HashMap<>();

        // Build initial distances at positive infinity
        for (Vertex<T> vert: graph.getAdjList().keySet()) {
            if (vert.equals(start)) {
                output.put(vert, 0);
            } else {
                output.put(vert, Integer.MAX_VALUE);
            }
        }

        // iterate over queue
        q.add(new VertexDistance<>(start, 0));
        while (!q.isEmpty()) {
            VertexDistance<T> temp = q.remove();

            for (VertexDistance<T> dist: graph.getAdjList().get(temp.getVertex())) {
                int totalDistance = dist.getDistance() + temp.getDistance();

                if (output.get(dist.getVertex()) > totalDistance) {
                    output.put(dist.getVertex(), totalDistance);
                    q.add(new VertexDistance<>(dist.getVertex(), totalDistance));
                }
            }
        }

        return output;
    }
}
