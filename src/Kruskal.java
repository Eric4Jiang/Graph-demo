import javax.swing.*;
import java.util.*;

public class Kruskal extends SwingWorker<Boolean, Edge>{

    Graph graph;

    public final int SLEEP_TIME = 500;

    public Map<String, String> PARENT;
    public Map<String, Integer> RANK;

    public ArrayList<Edge> MST;

    Kruskal(Graph graph) {
        this.graph = graph;

        PARENT = new HashMap<>();
        RANK = new HashMap<>();
        MST = new ArrayList<>();
    }

    /**
     * Called when this class is executed
     *
     * finds MST by continuously appending the lowest weight edges onto forests
     * until there is one distinct forest that contains all the vertices with no cycles
     *
     * @return - Minimum Spanning tree of graph
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        ArrayList<Edge> Edges = graph.edges;
        ArrayList<Node> Nodes = graph.nodes;

        Collections.sort(Edges);

        for (Node n : Nodes) {
            PARENT.put(n.name, n.name);
            RANK.put(n.name, 0);
        }
        for (Edge e : Edges) {
            // pauses gui to highlight edge on gui
            e.setColor(Graph.highlight);
            publish(e);
            Thread.sleep(SLEEP_TIME);

            String root1 = Find(e.n1.name);
            String root2 = Find(e.n2.name);

            // found tree with different root connected by Edge e.
            if (root1 != root2) {
                MST.add(e);
                Union(root1, root2);

                // highlights edge as part of MST
                e.setColor(Graph.MST_C);
                publish(e);
            } else {
                // edge is not part of MST
                e.setColor(Graph.defaultC);
                publish(e);
            }
            Thread.sleep(SLEEP_TIME);

            // gui paused
            while(graph.isPaused()) {
                Thread.sleep(1);
            }
        }

        return true;
    }

    /**
     * Can safely update GUI here.
     * Just refreshes the graph. The edge color is set beforehand
     *
     * @param chunks - Edge that is being processed
     */
    @Override
    protected void process(List<Edge> chunks) {
        graph.refreshGraph();
    }

    @Override
    protected void done() {

    }

    // finds the root of a vertex by traversing through
    private String Find(String node) {
        if(PARENT.get(node).equals(node)) //found root
            return PARENT.get(node);
        else
            return Find(PARENT.get(node)); // traverse list to find parent of tree
    }

    // Combine the two distinct forests by setting assigning
    // the larger tree's root as the smaller tree's new root.
    private void Union(String root1, String root2) {
        if(RANK.get(root1) > RANK.get(root2)) {
            PARENT.put(root2, PARENT.get(root1));
        } else if(RANK.get(root1) < RANK.get(root2)) {
            PARENT.put(root1, PARENT.get(root2));
        } else {
            PARENT.put(root1, PARENT.get(root2));
            RANK.put(root1, RANK.get(root1) + 1);
        }
    }

    public ArrayList<Edge> getMST() {
        return this.MST;
    }

}
