import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Kruskal {

    public Map<String, String> PARENT;
    public Map<String, Integer> RANK;

    public ArrayList<Edge> MST;

    Kruskal() {
        PARENT = new HashMap<>();
        RANK = new HashMap<>();

        MST = new ArrayList<>();
    }

    // finds MST by continuously appending the lowest weight edges onto forests
    // until there is one distinct forest that contains all the vertices with no cycles
    public ArrayList<Edge> solveMST(Graph graph) {
        ArrayList<Edge> Edges = graph.edges;
        ArrayList<Node> Nodes = graph.nodes;

        Collections.sort(Edges);

        for(Node n : Nodes) {
            PARENT.put(n.name, n.name);
            RANK.put(n.name, 0);
        }
        for(Edge e : Edges) {
            String root1 = Find(e.n1.name);
            String root2 = Find(e.n2.name);

            // found tree with different root connected by Edge e
            if(root1 != root2) {
//                graph.setPartOfMST(e.name);
                MST.add(e);
                Union(root1, root2);
            }
        }
        return MST;
    }

    // finds the root of a vertex by traversing through
    private String Find(String node) {
        if(PARENT.get(node).equals(node)) //found root
            return PARENT.get(node);
        else
            return Find(PARENT.get(node)); // traverse list to find parent of tree
    }

    /*
    combine the two distinct forests by setting assigning
    the larger tree's root as the smaller tree's new root
    */
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

}
