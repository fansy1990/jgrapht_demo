package demo;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Iterator;

/**
 *
 * 应该选择 广度优先遍历 来实现 DGA的调度；
 *
 * @Author: fansy
 * @Time: 2019/4/16 10:31
 * @Email: fansy1990@foxmail.com
 */
public class DAGBreadthDemo {
    public static void main(String[] args) {
        Graph<String, DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);

        g(graph);

        traversal(graph);

    }

    public static void g(Graph graph){
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

        graph.addVertex("G");
        graph.addVertex("H");
        graph.addVertex("I");
        graph.addVertex("J");


        graph.addEdge("B","G");
        graph.addEdge("D","H");
        graph.addEdge("H","I");
        graph.addEdge("H","J");


//        graph.addEdge("F","G");
    }


    private static void traversal(Graph<String,DefaultEdge> graph){
        String start = graph.vertexSet().stream().filter(x -> graph.inDegreeOf(x) ==0 ).findAny().get();
        System.out.println("start : "+ start);
        Iterator<String> iterator = null;
        iterator = new BreadthFirstIterator<>(graph,start);

        while (iterator.hasNext()) {
            String uri = iterator.next();
            System.out.print(uri+" , ");
        }
    }
}
