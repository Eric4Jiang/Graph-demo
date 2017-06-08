import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Graph extends JPanel implements MouseListener {

    public ArrayList<Node> nodes;
    public ArrayList<Edge> edges;

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

    /**
     * Called every time a change is made to update graph
     * @param g - graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

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
