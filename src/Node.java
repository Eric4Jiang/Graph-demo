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

    // contains a circle
    public Ellipse2D.Double circle;

    final public int NODE_SIZE = 26;

    public Node(int x, int y, Graph parentGraph) {
        this.vertex = new Point(x, y);
        this.name = Integer.toString(x, y);
        this.parentGraph = parentGraph;
        this.circle = new Ellipse2D.Double(0, 0, NODE_SIZE, NODE_SIZE);

        // set JPanel specs
        setBounds(x - NODE_SIZE/2, y - NODE_SIZE/2, NODE_SIZE*3, NODE_SIZE); // set center of jpanel on (x,y) on graph
        setOpaque(false);
        setPreferredSize(new Dimension(NODE_SIZE, NODE_SIZE)); // calls paintcomponent
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

    @Override
    public void mouseClicked(MouseEvent e) {
        
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
