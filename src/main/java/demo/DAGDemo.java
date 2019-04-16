package demo;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.Comparator;
import java.util.Iterator;

/**
 *
 * 应该选择 广度优先遍历 来实现 DGA的调度；
 *
 * @Author: fansy
 * @Time: 2019/4/16 10:31
 * @Email: fansy1990@foxmail.com
 */
public class DAGDemo {
    public static void main(String[] args) {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

//        g1(graph);
//        g2(graph);
        g3(graph);
        String[] methods = {"depth-first","breadth-first","topological"};
        for(String method :methods) {
            traversal1(graph, method);
            System.out.println();
        }

//        graph.

    }

    public static void g1(Graph graph){
        graph.addVertex("A");
        graph.addVertex("B");
        graph.addVertex("C");
        graph.addVertex("D");
        graph.addVertex("E");
        graph.addVertex("F");

        graph.addEdge("A","B");
        graph.addEdge("A","C");
        graph.addEdge("C","E");
        graph.addEdge("C","D");
        graph.addEdge("C","F");
        graph.addEdge("B","F");
    }

    public static void g2(Graph graph){
        g1(graph);

        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");
        graph.addVertex("J");


        graph.addEdge("B","G");
        graph.addEdge("D","H");
        graph.addEdge("H","I");
        graph.addEdge("H","J");

    }

    public static void g3(Graph graph){
        g2(graph);

        graph.addEdge("F","G");
    }

    public static void traversal1(Graph<String,DefaultEdge> graph, String type){
        String start = graph.vertexSet().stream().filter(x -> graph.inDegreeOf(x) ==0 ).findAny().get();
        System.out.println("start : "+ start+ ", type :" + type);
        Iterator<String> iterator = null;
        switch (type){
            case "depth-first":
                iterator =new DepthFirstIterator<>(graph, start);
                break;
            case "breadth-first":
                iterator = new BreadthFirstIterator<>(graph,start);
                break;
            case "topological":
                iterator = new TopologicalOrderIterator(graph, Comparator.naturalOrder());
                break;
                default:
                    System.err.println("Wrong Type :"+ type);
        }

        while (iterator.hasNext()) {
            String uri = iterator.next();
            System.out.print(uri+" , ");
        }
    }
}
