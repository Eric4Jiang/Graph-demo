import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph extends JPanel implements MouseListener {
    public static Color defaultC = Color.BLACK, highlight = Color.GREEN,
                        MST_C = Color.RED ;

    public ArrayList<Node> nodes = new ArrayList<>();
    public Map nameToNode = new HashMap<String, Node>();

    public ArrayList<Edge> edges = new ArrayList<>();
    public Map<String, Edge> nameToEdge = new HashMap<>();
    public Node edge_node1;

    public int graph_state = -1; // -1 -> Do nothing
                                 // 0 -> Adding nodes
                                 // 1 -> Forming edges. 0 nodes selected
                                 // 2 -> Forming edges. 1 node selected;
                                 // 3 -> Doing animation demonstration

    public int GRAPH_SIZE = 1000;

    public SwingWorker kruskal;
    public SwingWorker prim;
    public boolean paused = false;

    public Graph() {
        this.addMouseListener(this);

        // init graph
        setLayout(null); // absolute layout
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(GRAPH_SIZE, GRAPH_SIZE)); // calls paintcomponent
    }

    public void setGraphState(int state) {
        System.out.println("state = " + state);
        if (state != 2 && edge_node1 != null) {
            resetComponentColors();
        }
        this.graph_state = state;
    }

    public int getGraphState() {
        return this.graph_state;
    }

    public boolean isPaused() {
        return this.paused;
    }

    /**
     * Called every time a change is made to update graph
     *
     * @param g - graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D) g;

        // draw edges
        ((Graphics2D) g).setStroke(new BasicStroke(10));
        for(Edge e : edges) {
            Node n1 = e.n1;
            Node n2 = e.n2;
            g.setColor(e.color);
            g.drawLine((int)n1.vertex.getX(), (int)n1.vertex.getY(), (int)n2.vertex.getX(), (int)n2.vertex.getY());
        }
    }

    public void refreshGraph() {
        repaint();
        revalidate();
    }

    public void resetComponentColors() {
        for (Node n : nodes) {
            n.setColor(defaultC);
        }
        for (Edge e : edges) {
            e.setColor(defaultC);
        }
        refreshGraph();
    }

    public void addNode(Node n) {
        nameToNode.put(n.getName(), n);
        this.nodes.add(n);
        add(n);
        refreshGraph();
    }

    public void addEdge(Edge e) {
        nameToEdge.put(e.name, e);
        this.edges.add(e);
        refreshGraph();
    }

    /**
     * Handles node creation.
     * Once "Add Node" has been selected, a click on anywhere on the graph
     * will spawn a node Jpanel there, and therefore prevent multiple nodes
     * from being at the same location.
     *
     * @param e - where mouse was clicked on graph
     */
    @Override
    public void mouseClicked(MouseEvent e) {
//        System.out.println("graph-clicked");
        if (graph_state == 0) {
            // create a node where mouse was clicked
            Node n = new Node(e.getX(), e.getY(), this);
            addNode(n);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // ============================ Solving Graph =================================

    /**
     * @param n1 - name of first node
     * @param n2 - name of second node
     * @return - Instance of edge that connects the first and second node.
     *           If no edge found, return null.
     */
    public Edge findEdge(String n1, String n2) {
        String name1 = n1 + n2;
        String name2 = n2 + n1;

        if (!nameToEdge.containsKey(name1)) {
            return nameToEdge.get(name2);
        }

        return nameToEdge.get(name1);
    }

    /**
     * finds all nodes connected to n by an edge
     *
     * @param n - node to find edges for
     * @return - List of MyPairs. MyPairs will contain edges that are
     *           connected to Node n.
     */
    public ArrayList<MyPair> adjacent(Node n) {
        ArrayList<MyPair> adj = new ArrayList<>();
        for(Edge e : edges) {
            if(e.n1.name.equals(n.name)) {
                adj.add(new MyPair(e.n2, e));
            } else if(e.n2.name.equals(n.name)) {
                adj.add(new MyPair(e.n1, e));
            }
        }
        for(MyPair p : adj) {
            System.out.println("Adjacent: " + p.edge.name);
        }

        return adj;
    }

    /**
     * Finds the graph's MST(Minimum Spanning Tree)
     *  or shortest tree that connects all nodes.
     *
     * @param method - which algorithm to find MST
     * @return - List of edges that represent MST.
     */
    public ArrayList<Edge> animateMST(String method) {
        if (edges.isEmpty())
            return null;

        if (method.equals("kruskal")) {
            kruskal = new Kruskal(this);
            kruskal.execute();
        } else if (method.equals("prim")) {
            Prim prim = new Prim();
            return prim.solveMST(this);
        }

        return null;
    }

    public void killAllAnimation() {
        kruskal.cancel(true);
        // prim.cancel(true);
    }

    public void pauseMSTAnimation(boolean value) {
        this.paused = value;
//        if (!kruskal.isCancelled()) {
//            kruskal.cancel(true);
//        }
    }
}
