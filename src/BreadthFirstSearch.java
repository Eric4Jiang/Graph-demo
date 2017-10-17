import javax.lang.model.type.NullType;
import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BreadthFirstSearch extends SwingWorker <Boolean, NullType>{
    public Graph graph;
    public Queue<Node> Q;

    public final int SLEEP_TIME = 1000;

    public BreadthFirstSearch(Graph graph) {
        this.graph = graph;
        this.Q = new LinkedList<>();
    }

    // performs BFS animation
    @Override
    protected Boolean doInBackground() throws Exception {
        Q.add(graph.startNode);
        graph.startNode.setVisited(true);

        while (!Q.isEmpty()) {
            // gui paused
            while(graph.isPaused()) {
                Thread.sleep(1);
            }

            Node currentNode = Q.poll(); // deque
            currentNode.setColor(Graph.CURRENT_C);
            publish();
            Thread.sleep(SLEEP_TIME);

            // find all un-visited adjacent nodes and add them to queue
            ArrayList<MyPair> adj = graph.adjacent(currentNode);
            for (MyPair pair : adj) {
                Node adjN = pair.node;
                Edge adjE = pair.edge;

                if (!adjN.visited) {
                    // FOUND DESIRED NODE
                    if (adjN == graph.desiredNode) {
                        currentNode.setColor(Graph.FINAL_C);
                        adjN.setColor(Graph.CURRENT_C);
                        adjE.setColor(Graph.FINAL_C);
                        publish();
                        return true;
                    }

                    adjN.setVisited(true);
                    Q.add(adjN);
                    adjN.setColor(Graph.HIGHLIGHT);
                    adjE.setColor(Graph.FINAL_C);
                }
            }
            publish();
            Thread.sleep(SLEEP_TIME);

            currentNode.setColor(Graph.FINAL_C);
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
