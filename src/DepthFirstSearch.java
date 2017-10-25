import javax.lang.model.type.NullType;
import javax.swing.*;
import java.util.*;

public class DepthFirstSearch extends SwingWorker<Boolean, NullType> {

    public Graph graph;
    public Stack<Node> S;

    public int SLEEP_TIME = 1000;

    public DepthFirstSearch(Graph graph) {
        this.graph = graph;
        this.S = new Stack<>();

        if (this.graph.nodes.size() > 10) {
            this.SLEEP_TIME /= 2;
        }
    }

    // performs DFS animation
    @Override
    protected Boolean doInBackground() throws Exception {
        for (Node n : graph.nodes) {
            n.setVisited(false);
        }

        S.push(graph.startNode);

        while (!S.isEmpty()) {
            // gui paused
            while(graph.isPaused()) {
                Thread.sleep(1);
            }

            Node u = S.peek();
            u.setColor(Graph.CURRENT_C);
            publish();
            Thread.sleep(SLEEP_TIME);

            // if (!u.visited) { // fix this
            u.visited = true;

            // FOUND DESIRED NODE
            if (u == graph.desiredNode) {
                u.setColor(Graph.CURRENT_C);
                Edge e = graph.findEdge(u, S.peek());
                e.setColor(Graph.FINAL_C);
                publish();
                Thread.sleep(SLEEP_TIME);
                return true;
            }

            // find an adjacent node to u and add to stack
            ArrayList<MyPair> adj = graph.adjacent(u);
            boolean backtrack = true;
            for (MyPair p : adj) {
                Node adjN = p.node;
                if (!adjN.visited) {
                    u.setColor(Graph.FINAL_C);
                    S.push(adjN);
                    Edge e = graph.findEdge(u, adjN);
                    e.setColor(Graph.FINAL_C);
                    backtrack = false;
                    break;
                }
            }
            if (backtrack) {
                u.setColor(Graph.FINAL_C);
                S.pop();
            }

            // } else {
//                u.setColor(Graph.FINAL_C);
//                S.pop();
//            }
        }

        return true;
    }

    /**
     * Called every time publish() is called.
     * Can safely update GUI here.
     * Just refreshes the graph. The edge color is set beforehand
     *
     * @param chunks - Edge that is being processed
     */
    @Override
    protected void process(List<NullType> chunks) {
        graph.refreshGraph();
    }

    @Override
    protected void done() {

    }
}
