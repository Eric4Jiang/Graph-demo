import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Graph extends JPanel implements MouseListener {

    public ArrayList<Node> nodes;
    final public int NODE_SIZE = 26;

    public ArrayList<Edge> edges;

    public int GRAPH_SIZE = 1000;

    public String graph_state;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();

        this.addMouseListener(this);

        // init graph
        setLayout(null); // absolute layout
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(GRAPH_SIZE, GRAPH_SIZE)); // calls paintcomponent
    }

    public void setGraphState(String state) {
        this.graph_state = state;
    }

    public String getGraphState() {
        return this.graph_state;
    }

    /**
     * Called every time a change is made to update graph
     * @param g - graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Node n : this.nodes) {
            g.setColor(n.getColor());
            // draw node and name it
            int offset = NODE_SIZE / 2;
            g.drawString(n.getName(), (int)n.getX() - offset, (int)n.getY() - offset);
            g2d.fill(new Ellipse2D.Double(n.getX() - offset, n.getY() - offset, NODE_SIZE, NODE_SIZE));
        }

    }

    public void refreshGraph() {
        repaint();
        revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (graph_state.equals("node")) {
            // create a node where mouse was clicked
            Node n = new Node(e.getX(), e.getY(), this);
            this.nodes.add(n);
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
