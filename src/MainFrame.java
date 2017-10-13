import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFrame extends JFrame {

    public JPanel buttonPanel;
    final public int BUTTON_H = 300, BUTTON_W = 50;

    public ArrayList<ArrayList<JButton>> layers;
    public ArrayList<JButton> layer1;
    public ArrayList<JButton> layer2;
    public ArrayList<JButton> layer3;

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
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponentsOnPanel(buttonPanel);
                addNodeButton.setEnabled(false);
                graph.setGraphState(0);
            }
        });
        addToPanelLayer(addNodeButton, 0);
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
        addToPanelLayer(addEdgeButton, 0);
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
                enableComponentsOnPanel(buttonPanel);
                clearPanel(buttonPanel);
                createKruskalButton();
                createPrimButton();
                createBackButton(0);

                graph.setGraphState(-1);
            }
        });
        addToPanelLayer(addSolveButton, 0);
    }

    /**
     * Find MST of graph using kruskal's algorithm
     */
    public void createKruskalButton() {
        JButton kruskalButton = makeButton("Kruskal", buttonPanel, BUTTON_H, BUTTON_W);
        kruskalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(buttonPanel);
                graph.resetComponentColors();

                graph.pauseMSTAnimation(false);

                ArrayList<Edge> MST = graph.animateMST("kruskal");

                createPauseResumeAnimationButton();
                createBackButton(1);
            }
        });
        addToPanelLayer(kruskalButton, 1);
    }

    /**
     * Finds MST of graph using prim's algorithm
     */
    public void createPrimButton() {
        JButton primButton = makeButton("Prim", buttonPanel, BUTTON_H, BUTTON_W);
        primButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(buttonPanel);
                graph.resetComponentColors();

                graph.pauseMSTAnimation(false);

                ArrayList<Edge> MST = graph.animateMST("prim");

                createPauseResumeAnimationButton();
                createBackButton(1);
            }
        });
        addToPanelLayer(primButton, 1);
    }

    /**
     * Takes user back to graph creation process
     */
    public void createBackButton(int l) {
        JButton backButton = makeButton("Back", buttonPanel, BUTTON_H, BUTTON_W);
        ArrayList<JButton> layer = layers.get(l);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearPanel(buttonPanel);

                graph.killAllAnimation();

                for (JButton b : layer) {
                    buttonPanel.add(b);
                }
                if (l != 0) {
                    createBackButton(l - 1);
                }
            }
        });
    }

    public void createPauseResumeAnimationButton() {
        JButton stopButton = makeButton("Pause", buttonPanel, BUTTON_H, BUTTON_W);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (graph.isPaused()) {
                    stopButton.setText("Pause");
                    graph.pauseMSTAnimation(false);
                } else {
                    stopButton.setText("Resume");
                    graph.pauseMSTAnimation(true);
                }
            }
        });
        addToPanelLayer(stopButton, 2);
    }
}
