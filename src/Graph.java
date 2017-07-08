import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Graph extends JPanel implements MouseListener {
    public static Color defaultC = Color.BLACK, highlight = Color.GREEN;

    public ArrayList<Node> nodes;

    public ArrayList<Edge> edges;
    public Node edge_node1;

    public int graph_state = -1; // -1 -> Do nothing
                                 // 0 -> Adding nodes
                                 // 1 -> Forming edges. 0 nodes selected
                                 // 2 -> Forming edges. 1 node selected;

    public int GRAPH_SIZE = 1000;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        this.addMouseListener(this);

        // init graph
        setLayout(null); // absolute layout
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(GRAPH_SIZE, GRAPH_SIZE)); // calls paintcomponent
    }

    public void setGraphState(int state) {
        System.out.println("state = " + state);
        if (state != 2 && edge_node1 != null) {
            edge_node1.setColor(defaultC);
            refreshGraph();
        }
        this.graph_state = state;
    }

    public int getGraphState() {
        return this.graph_state;
    }

    /**
     * Called every time a change is made to update graph
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

    /**
     * Handles node creation.
     * Omne "Add Node" has been selected, a click on anywhere on the graph
     * will spawn a node there. Can't place nodes too close to each other.
     *
     * @param e - where mouse was clicked on graph
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("graph-clicked");
        if (graph_state == 0) {
            // create a node where mouse was clicked
            Node n = new Node(e.getX(), e.getY(), this);
            this.nodes.add(n);
            add(n);
            refreshGraph();
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
}
