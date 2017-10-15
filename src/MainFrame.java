import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public JPanel buttonPanel;
    final public int BUTTON_H = 300, BUTTON_W = 50;

    public ArrayList<ArrayList<JButton>> layers;
    public ArrayList<JButton> layer1; // creation
    public ArrayList<JButton> layer2; // algorithm
    public ArrayList<JButton> layer3; // animation
    
    public Graph graph;

    public MainFrame(String title) {
        super(title);

        buttonPanel = new JPanel();

        layers = new ArrayList<>();
        layer1 = new ArrayList<>();
        layer2 = new ArrayList<>();
        layer3 = new ArrayList<>();
        layers.add(layer1);
        layers.add(layer2);
        layers.add(layer3);

        // add buttons to GUI
        createAddNodeButton();
        createAddEdgeButton();
        createSolveGraphButton();
        add(this.buttonPanel);

        // add graph to GUI
        graph = new Graph();
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
    public JButton makeButton(String name, JPanel panel, int h, int w) {
        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setPreferredSize(new Dimension(h, w));

        panel.add(button);
        refreshPanel(panel);

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
     * Associates buttons with a layer
     * 0 = creation layer, 1 = algorithm layer, 2 = animation layer
     *
     * @param b - Button we're adding
     * @param l - Which layer it's on
     */
    public void addToPanelLayer(JButton b, int l) {
        ArrayList<JButton> layer = layers.get(l);
        for (JButton button : layer) {
            if (button.getText().equals(b.getText()))
                return;
        }
        layer.add(b);
    }

    /**
     * Turns on add node functionality in graph
     */
    public void createAddNodeButton() {
        JButton addNodeButton = makeButton("Add Nodes", buttonPanel, BUTTON_H, BUTTON_W);
        addNodeButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            addNodeButton.setEnabled(false);
            graph.setGraphState(0);
        });
        addToPanelLayer(addNodeButton, 0);
    }

    /**
     * Turns on edge connecting functionality in graph
     */
    public void createAddEdgeButton() {
        JButton addEdgeButton = makeButton("Add Edges", buttonPanel, BUTTON_H, BUTTON_W);
        addEdgeButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            addEdgeButton.setEnabled(false);
            graph.setGraphState(1);
        });
        addToPanelLayer(addEdgeButton, 0);
    }

    /**
     * Remove addNode and addEdge buttons
     * Saves existing graph
     * Create Prim and Kruskal Buttons
     */
    public void createSolveGraphButton() {
        JButton addSolveButton = makeButton("Solve Graph", buttonPanel, BUTTON_H, BUTTON_W);
        addSolveButton.addActionListener(e -> {
            enableComponentsOnPanel(buttonPanel);
            clearPanel(buttonPanel);

            // create algorithm layer
            createKruskalButton();
            createPrimButton();
            createBackButton(0);

            graph.setGraphState(3);
        });
        addToPanelLayer(addSolveButton, 0);
    }

    /**
     * Find MST of graph using kruskal's algorithm
     */
    public void createKruskalButton() {
        JButton kruskalButton = makeButton("Kruskal", buttonPanel, BUTTON_H, BUTTON_W);
        kruskalButton.addActionListener(e -> {
            clearPanel(buttonPanel);
            graph.resetComponentColors();

            graph.setGraphState(10);
            graph.pauseMSTAnimation(false);

            ArrayList<Edge> MST = graph.animateMST("kruskal");

            createPauseResumeAnimationButton();
            createBackButton(1);
        });
        addToPanelLayer(kruskalButton, 1);
    }

    /**
     * Finds MST of graph using prim's algorithm
     */
    public void createPrimButton() {
        JButton primButton = makeButton("Prim", buttonPanel, BUTTON_H, BUTTON_W);
        primButton.addActionListener(e -> {
            clearPanel(buttonPanel);
            graph.resetComponentColors();

            graph.setGraphState(10);
            graph.pauseMSTAnimation(false);

            ArrayList<Edge> MST = graph.animateMST("prim");

            createPauseResumeAnimationButton();
            createBackButton(1);
        });
        addToPanelLayer(primButton, 1);
    }

    public void createBFSButton() {
        JButton BFSButton = makeButton("BFS", buttonPanel, BUTTON_H, BUTTON_W);
        BFSButton.addActionListener(e -> {
            clearPanel(buttonPanel);

            graph.resetComponentColors();

            createPauseResumeAnimationButton();
            createBackButton(1);
        });

        addToPanelLayer(BFSButton, 1);
    }

    /**
     * Takes user back a previous layer
     * @param l - layer to go back to
     */
    public void createBackButton(int l) {
        JButton backButton = makeButton("Back", buttonPanel, BUTTON_H, BUTTON_W);
        ArrayList<JButton> layer = layers.get(l);
        backButton.addActionListener(e -> {
            clearPanel(buttonPanel);

            graph.killAllAnimation();

            for (JButton b : layer) {
                buttonPanel.add(b);
            }
            // don't create back button for layer
            if (l != 0) {
                createBackButton(l - 1);
            }
        });
    }

    public void createPauseResumeAnimationButton() {
        JButton pauseButton = makeButton("Pause", buttonPanel, BUTTON_H, BUTTON_W);
        pauseButton.addActionListener(e -> {
            if (graph.isPaused()) {
                pauseButton.setText("Pause");
                graph.pauseMSTAnimation(false);
            } else {
                pauseButton.setText("Resume");
                graph.pauseMSTAnimation(true);
            }
        });
        addToPanelLayer(pauseButton, 2);
    }
}
