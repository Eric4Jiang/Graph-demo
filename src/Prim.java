import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Prim {

    public Map<String, String> PARENT;
    public Map<String, Double> KEY;

    Prim() {
        PARENT = new HashMap<>();
        KEY = new HashMap<>();
    }

    public ArrayList<Edge> solveMST(Graph graph) {
        ArrayList<Edge> primMST = new ArrayList();
        ArrayList<Node> Nodes = graph.nodes;

        for(Node n : Nodes) {
            PARENT.put(n.name, null);
            KEY.put(n.name, Double.MAX_VALUE);
        }
        // set a starting point
        KEY.put(Nodes.get(0).name, 0.0);

        ArrayList<Node> Q = new ArrayList<>();
        Q.addAll(Nodes);

        // until all vertices are connected
        while(Q.size() != 0) {
            int nPos = posMinWeightV(Q);
            Node u = Q.get(nPos); // choose closest vertex to tree
            Q.remove(nPos);

            // add edge to MST
            if(PARENT.get(u.name) != null) {
                Edge e = graph.findEdge(u.name, PARENT.get(u.name));
                primMST.add(e);
                System.out.println("Added in Edge" + e.name);
                // graph.setPartOfMST(e.name);
            }

            // calculate distance from adjacent vertices
            ArrayList<MyPair> adj = graph.adjacent(u);
            for(MyPair pair : adj) {
                Node adjV = pair.node;
                Edge adjE = pair.edge;

                if(Q.contains(adjV)) {
                    // if vertex is closer to this vertex than other known all vertices
                    if(adjE.weight < KEY.get(adjV.name)) {
                        PARENT.put(adjV.name, u.name);
                        KEY.put(adjV.name, adjE.weight);
                    }
                }
            }
        }

        return primMST;
    }

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

}
