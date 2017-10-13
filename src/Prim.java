import javax.lang.model.type.NullType;
import javax.swing.*;
import java.util.*;

public class Prim extends SwingWorker<Boolean, NullType>{

    public Graph graph;
    public final int SLEEP_TIME = 500;

    public Map<String, String> PARENT;
    public Map<String, Double> KEY;

    public ArrayList<Edge> MST;

    Prim(Graph graph) {
        this.graph = graph;

        PARENT = new HashMap<>();
        KEY = new HashMap<>();
        MST = new ArrayList<>();
    }

    /**
     * Called when this class is executed
     *
     * @return - Minimum Spanning tree of graph
     */
    @Override
    protected Boolean doInBackground() throws Exception {
        ArrayList<Node> Nodes = graph.nodes;

        // set all points to default value (MAX)
        for(Node n : Nodes) {
            PARENT.put(n.name, null);
            KEY.put(n.name, Double.MAX_VALUE);
        }
        // set a starting point (value = 0)
        KEY.put(Nodes.get(0).name, 0.0);

        // Q holds the vertices that are not connected at any given time
        ArrayList<Node> Q = new ArrayList<>();
        Q.addAll(Nodes);

        // until all vertices are connected
        while(Q.size() != 0) {
            int nPos = posMinWeightV(Q);
            Node u = Q.get(nPos); // choose closest vertex to tree
            Q.remove(nPos);

            // clear node highlights
            for (Node n : Q) {
                n.setColor(Graph.defaultC);
            }
            u.setColor(Graph.MST_C);
            publish();
            Thread.sleep(SLEEP_TIME);

            // connect node to parent to form edge
            if(PARENT.get(u.name) != null) {
                Edge e = graph.findEdge(u.name, PARENT.get(u.name));
                MST.add(e);

                e.setColor(Graph.MST_C);
                publish();
                Thread.sleep(SLEEP_TIME);
            }

            // Update the weights of all vertices adjacent to u
            ArrayList<MyPair> adj = graph.adjacent(u);
            for(MyPair pair : adj) {
                Node adjV = pair.node;
                Edge adjE = pair.edge;

                // Don't update if:
                //      1) adjV is already in the tree (not in Q) or
                //      2) If adjV is closer to another vertex (not u)
                if(Q.contains(adjV)) {
                    if(adjE.weight < KEY.get(adjV.name)) {
                        PARENT.put(adjV.name, u.name);
                        KEY.put(adjV.name, adjE.weight);
                    }
                    adjV.setColor(Graph.highlight);
                }
            }
            publish();
            Thread.sleep(SLEEP_TIME);

            // gui paused
            while(graph.isPaused()) {
                Thread.sleep(1);
            }
        }

        return true;
    }

    /**
     * Called every time publish() is called
     * Can safely update GUI here.
     * Just refreshes the graph. The edge color is set beforehand
     *
     * @param chunks - Nothing
     */
    @Override
    protected void process(List<NullType> chunks) {
        graph.refreshGraph();
    }

    @Override
    protected void done() {

    }

    /**
     * Iterates through list for node with lowest weight.
     *
     * @param N - List to traverse through
     *
     * @return - index of node with lowest weight
     */
    public int posMinWeightV(ArrayList<Node> N) {
        int indexOfV = 0;
        double lowestWeight = Double.MAX_VALUE;

        for(int i = 0; i < N.size(); i++) {
            Node n = N.get(i);
            if(KEY.get(n.name) < lowestWeight) {
                indexOfV = i;
                lowestWeight = KEY.get(n.name);
            }
        }
        return indexOfV;
    }

    public ArrayList<Edge> getMST() {
        return this.MST;
    }

}
