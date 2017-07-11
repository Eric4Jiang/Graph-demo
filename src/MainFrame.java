import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public JPanel buttonPanel;
    public ArrayList<JButton> layer1;
    public ArrayList<JButton> layer2;
    final public int BUTTON_H = 300, BUTTON_W = 50;

    public Graph graph;

    public MainFrame(String title) {
        super(title);

        buttonPanel = new JPanel();
        layer1 = new ArrayList<>();
        layer2 = new ArrayList<>();

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
     * Turns on add node functionality in graph
     */
    public void createAddNodeButton() {
        JButton addNodeButton = makeButton("Add Nodes", buttonPanel, BUTTON_H, BUTTON_W);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponentsOnPanel(buttonPanel);
                addNodeButton.setEnabled(false);
                graph.setGraphState(0);
            }
        });
        layer1.add(addNodeButton);
    }

    /**
     * Turns on edge connecting functionality in graph
     */
    public void createAddEdgeButton() {
        JButton addEdgeButton = makeButton("Add Edges", buttonPanel, BUTTON_H, BUTTON_W);
        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponentsOnPanel(buttonPanel);
                addEdgeButton.setEnabled(false);
                graph.setGraphState(1);
            }
        });
        layer1.add(addEdgeButton);
    }

    /**
     * Remove addNode and addEdge buttons
     * Saves existing graph
     * Create Prim and Kruskal Buttons
     */
    public void createSolveGraphButton() {
        JButton addSolveButton = makeButton("Solve Graph", buttonPanel, BUTTON_H, BUTTON_W);
        addSolveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(buttonPanel);
                createKruskalButton();
                createPrimButton();
                createBackButton();
            }
        });
        layer1.add(addSolveButton);
    }

    /**
     * Find MST of graph using kruskal's algorithm
     */
    public void createKruskalButton() {
        JButton kruskalButton = makeButton("Kruskal", buttonPanel, BUTTON_H, BUTTON_W);
        kruskalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graph.resetComponentColors();

                ArrayList<Edge> MST = graph.demoMST("kruskal");

                System.out.println("Kruskal MST = " + MST);
            }
        });
        layer2.add(kruskalButton);
    }

    /**
     * Finds MST of graph using prim's algorithm
     */
    public void createPrimButton() {
        JButton primButton = makeButton("Prim", buttonPanel, BUTTON_H, BUTTON_W);
        primButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                clearButtonPanel();

//                for (Edge edge1 : graph.edges) {
//                    edge1.setColor(Graph.defaultC);
//                }
//
//                ArrayList<Edge> MST = graph.findMST("prim");
//                for (Edge edge : MST) {
//                    edge.setColor(Graph.highlight);
//                }
//                graph.refreshGraph();
//                System.out.println("Prim MST = " + MST);
            }
        });
        layer2.add(primButton);
    }

    /**
     * Takes user back to graph creation process
     */
    public void createBackButton() {
        JButton backButton = makeButton("Back", buttonPanel, BUTTON_H, BUTTON_W);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(buttonPanel);
                for (JButton b : layer1) {
                    buttonPanel.add(b);
                }
                graph.setGraphState(-1);
            }
        });
        layer2.add(backButton);
    }
}
