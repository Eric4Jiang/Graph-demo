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

    final public int NODE_SIZE = 26;

    public Node(int x, int y, Graph parentGraph) {
        this.vertex = new Point(x, y);
        this.name = Integer.toString(x) + Integer.toString(y);
        this.parentGraph = parentGraph;
        this.circle = new Ellipse2D.Double(0, 0, NODE_SIZE, NODE_SIZE);
        this.color = Graph.defaultC;

        // if not included, parentGraph will detect click instead
        addMouseListener(this);

        // set JPanel specs
        setBounds(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE*3, NODE_SIZE); // set center of jpanel on (x,y) on graph
        setOpaque(false);
        setPreferredSize(new Dimension(NODE_SIZE, NODE_SIZE)); // calls paintcomponent

        System.out.println("Node = " + name);
    }

    public String getName() {
        return this.name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
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
     * Handles edge forming. Select two existing nodes and an edge will appear between them.
     * Selecting the first node will highlight it. Click the node again to un-select.
     * Selecting the second node will form the edge, and the process is restarted.
     *
     * @param e - where mouse was clicked on node
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (parentGraph.getGraphState() == 1) {
            color = Graph.highlight;
            parentGraph.edge_node1 = this;
            parentGraph.setGraphState(2);
        } else if (parentGraph.getGraphState() == 2) {
            // make sure first and second node are different
            if (color != Graph.highlight) {
                parentGraph.edges.add(new Edge(parentGraph.edge_node1, this));
            }
            parentGraph.setGraphState(1); // restart edge forming process
        }
        parentGraph.refreshGraph();
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
