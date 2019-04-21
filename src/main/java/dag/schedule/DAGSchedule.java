package dag.schedule;

import org.jgrapht.Graph;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.Iterator;

/**
 * @Author: fansy
 * @Time: 2019/4/16 11:15
 * @Email: fansy1990@foxmail.com
 */
public class DAGSchedule {
    public static void main(String[] args) {
        AbstractBaseGraph<GraphNode , DefaultEdge> graph = generate();
        schedule(graph);
    }

    private static void schedule(AbstractBaseGraph<GraphNode,DefaultEdge> graph){

        AbstractBaseGraph<GraphNode,DefaultEdge> cloneGrapht =
                (AbstractBaseGraph<GraphNode,DefaultEdge>) graph.clone(); // to change status
        GraphNode start = graph.vertexSet().stream().filter(x -> graph.inDegreeOf(x) ==0 ).findAny().get();
        System.out.println("start : "+ start);

        Iterator<GraphNode> iterator  = new BreadthFirstIterator<>(graph,start);

        while (iterator.hasNext()) {
            GraphNode currNode = iterator.next();
            // change status
            changeStatus(cloneGrapht, currNode, NodeStatus.RUNNING);

//            if(canRun(cloneGrapht, currNode)){
//
//            }

        }
        System.out.println();
        Iterator<GraphNode> iterator2  = new BreadthFirstIterator<>(graph,start);

        while (iterator2.hasNext()) {
            GraphNode uri = iterator.next();
            System.out.print(uri+" , ");
        }



    }

    /**
     * change clone graph node status
     * @param graph the clone graph
     * @param node the specific node , only use id
     * @param status
     */
    private static void changeStatus(AbstractBaseGraph<GraphNode,DefaultEdge> graph, GraphNode node, NodeStatus status){
        GraphNode specificNode = graph.vertexSet().stream().filter(x -> x.getId().equals(node.getId())).findAny().get();
        specificNode.setStatus(status);
    }

    private static AbstractBaseGraph<GraphNode, DefaultEdge> generate(){
        AbstractBaseGraph<GraphNode,DefaultEdge> graph = new DirectedMultigraph<>(DefaultEdge.class);
        GraphNode A = new GraphNode("A");
        GraphNode B = new GraphNode("B");
        GraphNode C = new GraphNode("C");
        GraphNode D = new GraphNode("D");
        GraphNode E = new GraphNode("E");
        GraphNode F = new GraphNode("F");
        GraphNode G = new GraphNode("G");
        GraphNode H = new GraphNode("H");
        GraphNode I = new GraphNode("I");
        GraphNode J = new GraphNode("J");
        
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);
        graph.addVertex(F);
        graph.addVertex(G);
        graph.addVertex(H);
        graph.addVertex(I);
        graph.addVertex(J);

        graph.addEdge(A,B);
        graph.addEdge(A,C);
        graph.addEdge(C,E);
        graph.addEdge(C,D);
        graph.addEdge(C,F);
        graph.addEdge(B,F);
        
        graph.addEdge(B,G);
        graph.addEdge(D,H);
        graph.addEdge(H,I);
        graph.addEdge(H,J);


        return graph;
    }
}
