import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public JPanel buttonPanel;
    final public int BUTTON_H = 250, BUTTON_W = 50;

    public ArrayList<ArrayList<JButton>> layers;
    public ArrayList<JButton> layer1; // creation
    public ArrayList<JButton> layer2; // algorithm
    public ArrayList<JButton> layer3; // animation

    public Graph graph;

    public MainFrame(String title) {
        super(title);

        buttonPanel = new JPanel();
        graph = new Graph();

        // init layers for buttonpanel
        layers = new ArrayList<>();
        layer1 = new ArrayList<>();
        layer2 = new ArrayList<>();
        layer3 = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);

        // init buttons
        JButton nodeB = createAddNodeButton();
        JButton edgeB = createAddEdgeButton();
        JButton solveB = createSolveGraphButton();
        layer1.add(nodeB);
        layer1.add(edgeB);
        layer1.add(solveB);

        JButton kruskalB = createKruskalButton();
        JButton primB = createPrimButton();
        JButton bfsB = createBFSButton();
        JButton dfsB = createDFSButton();
        JButton backToL1B = createBackButton(1);
        layer2.add(kruskalB);
        layer2.add(primB);
        layer2.add(bfsB);
        layer2.add(dfsB);
        layer2.add(backToL1B);

        JButton startB = createStartStopAnimationButton();
        JButton backToL2B = createBackButton(2);
        layer3.add(startB);
        layer3.add(backToL2B);

        // start program at creation layer
        switchToLayer(1);

        // add legend to graph
        JLabel label = new JLabel();
        ImageIcon icon = new ImageIcon("./legend.png");
        label.setIcon(icon);
        label.setBounds(graph.GRAPH_SIZE - 125, -20, 150, 150); // label default size is 0
        graph.add(label);

        // add components onto JFrame
        add(buttonPanel);
        add(graph);

        // init Jframe for GUI
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setResizable(true);
        setVisible(true);
    }

    /**
     * Need to refresh panel every time something is updated
     * @param panel - which panel to resfresh
     */
    public void refreshPanel(JPanel panel) {
        panel.repaint();
        panel.revalidate();
    }

    /**
     * Creates a button and adds it onto buttonPanel
     *
     * @param name: name of button
     * @param h,w : dimensions of button
     * @return button created
     */
    public JButton makeButton(String name, int h, int w) {
        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setPreferredSize(new Dimension(h, w));
        return button;
    }

    /**
     * Make all buttons clickable
     */
    public void enableComponentsOnPanel(JPanel panel) {
        Component[] components = panel.getComponents();
        for(Component c : components) {
            c.setEnabled(true);
        }
    }

    public void clearPanel(JPanel panel) {
        panel.removeAll();
        refreshPanel(panel);
    }

    /**
     * Clears current buttonPanel and creates desired one
     * Resets graph components
     *
     * @param l - which version of the button panel to create (1, 2, or 3)
     */
    public void switchToLayer(int l) {
        ArrayList<JButton> layer = layers.get(l - 1);
        clearPanel(buttonPanel);
        for (JButton b : layer) {
            // reset start and stop text on button
            if (b.getText() == "Stop") {
                b.setText("Start");
            }
            buttonPanel.add(b);
        }
        refreshPanel(buttonPanel);

        graph.resetNodes();
        graph.resetEdges();
        graph.refreshGraph();
    }

    /**
     * Turns on add node functionality in graph
     */
    public JButton createAddNodeButton() {
        JButton addNodeButton = makeButton("Add Nodes", BUTTON_H, BUTTON_W);
        addNodeButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            addNodeButton.setEnabled(false);
            graph.setGraphState(0);
        });
        return addNodeButton;
    }

    /**
     * Turns on edge connecting functionality in graph
     */
    public JButton createAddEdgeButton() {
        JButton addEdgeButton = makeButton("Add Edges", BUTTON_H, BUTTON_W);
        addEdgeButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            addEdgeButton.setEnabled(false);
            graph.setGraphState(1);
        });
        return addEdgeButton;
    }

    /**
     * Remove addNode and addEdge buttons
     * Saves existing graph
     * Create Prim and Kruskal Buttons
     */
    public JButton createSolveGraphButton() {
        JButton addSolveButton = makeButton("Solve Graph", BUTTON_H, BUTTON_W);
        addSolveButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            switchToLayer(2);
            graph.setGraphState(3);
        });
        return addSolveButton;
    }

    /**
     * Find MST of graph using kruskal's algorithm
     */
    public JButton createKruskalButton() {
        JButton kruskalButton = makeButton("Kruskal", BUTTON_H, BUTTON_W);
        kruskalButton.addActionListener(e -> {
            switchToLayer(3);

            graph.setGraphState(10);
            graph.setMethod("kruskal");
            graph.animateGraphAlgorithm();

        });
        return kruskalButton;
    }

    /**
     * Finds MST of graph using prim's algorithm
     */
    public JButton createPrimButton() {
        JButton primButton = makeButton("Prim", BUTTON_H, BUTTON_W);
        primButton.addActionListener(e -> {
            switchToLayer(3);

            graph.setGraphState(10);
            graph.setMethod("prim");
            graph.animateGraphAlgorithm();
        });
        return primButton;
    }

    public JButton createBFSButton() {
        JButton BFSButton = makeButton("BFS", BUTTON_H, BUTTON_W);
        BFSButton.addActionListener(e -> {
            switchToLayer(3);

            // must click on two nodes for animation to start. 4->5->10
            // at 10, the BFS animation will start (called from Node.java)
            graph.setGraphState(4);
            graph.setMethod("BFS");
        });
        return BFSButton;
    }

    public JButton createDFSButton() {
        JButton DFSButton = makeButton("DPS", BUTTON_H, BUTTON_W);
        DFSButton.addActionListener(e -> {
            switchToLayer(3);

            graph.setGraphState(4);
            graph.setMethod("DFS");
        });
        return DFSButton;
    }

    /**
     * Takes user back a previous layer
     * @param l - layer to go back to
     */
    public JButton createBackButton(int l) {
        JButton backButton = makeButton("Back", BUTTON_H, BUTTON_W);
        backButton.addActionListener(e -> {
            if (l == 2) { // going from layer 3 to 2
                graph.killAllAnimation();
                graph.stopAnimation();
                graph.setGraphState(3);
            } else if (l == 1) {
                graph.setGraphState(-1);
            }
            switchToLayer(l);
        });
        return backButton;
    }

    public JButton createStartStopAnimationButton() {
        JButton startButton = makeButton("Start", BUTTON_H, BUTTON_W);
        startButton.addActionListener(e -> {
            if (graph.getGraphState() != 10)
                return;
            if (graph.isPaused()) {
                startButton.setText("Stop");
                graph.startAnimation();
            } else {
                startButton.setText("Start");
                graph.stopAnimation();
            }
        });
        return startButton;
    }
}
