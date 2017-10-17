import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;

public class Node extends JPanel implements MouseListener{

    public Point vertex;
    public String name;
    public Graph parentGraph;
    public Color color;

    public Ellipse2D.Double circle;

    // for search algorithms (BFS and DFS)
    public boolean visited = false;

    final public int NODE_SIZE = 26;

    public Node(int x, int y, Graph parentGraph) {
        this.vertex = new Point(x, y);
        this.name = Integer.toString(x) + Integer.toString(y);
        this.parentGraph = parentGraph;
        this.circle = new Ellipse2D.Double(0, 0, NODE_SIZE, NODE_SIZE);
        this.color = Graph.DEFAULT_C;

        // if not included, parentGraph will detect click instead
        addMouseListener(this);

        // set JPanel specs
        setBounds(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE*3, NODE_SIZE);
        setOpaque(false);
        setPreferredSize(new Dimension(NODE_SIZE, NODE_SIZE)); // calls paintcomponent

        System.out.println("Node = " + name);
    }

    public String getName() {
        return this.name;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean getVisited() {
        return this.visited;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public void refreshNode() {
        repaint();
        revalidate();
    }

    /**
     * Redraws node when changes are made
     * @param g - graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(color);

        // draw node and name it
        g.drawString(name, NODE_SIZE, NODE_SIZE / 2);
        g2d.fill(circle);
    }

    /**
     * Called when in add edge state and a node on the gui is clicked
     *
     * Handles edge forming. Select two existing nodes and an edge will appear between them.
     * Selecting the first node will highlight it. Click the node again to un-select.
     * Selecting the second node will form the edge, and the process is restarted.
     *
     * When clicked on it for BFS and DFS, first one clicked is starting node,
     * and second one clicked is the desire node
     *
     * @param e - where mouse was clicked on node
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // select first node for edge
        if (parentGraph.getGraphState() == 1) {
            color = Graph.HIGHLIGHT;
            parentGraph.edge_node1 = this;
            parentGraph.setGraphState(2);
        }

        // form edge if two unique nodes are selected
        else if (parentGraph.getGraphState() == 2) {
            if (color != Graph.HIGHLIGHT) {
                Edge existingEdge = parentGraph.findEdge(parentGraph.edge_node1.name, this.name);
                if (existingEdge == null) {
                    parentGraph.addEdge(new Edge(parentGraph.edge_node1, this));
                }
            }
            parentGraph.edge_node1.setColor(Graph.DEFAULT_C);
            parentGraph.setGraphState(1); // restart edge forming process
        }

        // select starting node for search algorithm
        else if (parentGraph.getGraphState() == 4) {
            color = Graph.FINAL_C;
            parentGraph.startNode = this;
            parentGraph.setGraphState(5);
        }

        // select desired node for search algorithm
        // Must be different from starting node
        else if (parentGraph.getGraphState() == 5) {
            if (parentGraph.startNode == this)
                return;

            color = Graph.FINAL_C;
            parentGraph.desiredNode = this;
            parentGraph.setGraphState(10);

            parentGraph.animateGraphAlgorithm();
        }

        refreshNode(); // update and color changes
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
