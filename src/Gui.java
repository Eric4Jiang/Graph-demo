import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {

    public JPanel buttonPanel;
    final public int BUTTON_H = 300, BUTTON_W = 50;

    public Graph graph;

    public Gui (String title) {
        super(title);

        buttonPanel = new JPanel();

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
    public JButton makeButton(String name, int h, int w) {
        JButton button = new JButton(name);
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setPreferredSize(new Dimension(h, w));

        buttonPanel.add(button);
        refreshPanel(buttonPanel);

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

    public void clearButtonPanel() {
        buttonPanel.removeAll();
        refreshPanel(buttonPanel);
    }

    /**
     * Turns on add node functionality in graph
     */
    public void createAddNodeButton() {
        JButton addNodeButton = makeButton("Add Nodes", BUTTON_H, BUTTON_W);
        addNodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponentsOnPanel(buttonPanel);
                addNodeButton.setEnabled(false);
                graph.setGraphState(0);
            }
        });
    }

    /**
     * Turns on edge connecting functionality in graph
     */
    public void createAddEdgeButton() {
        JButton addEdgeButton = makeButton("Add Edges", BUTTON_H, BUTTON_W);
        addEdgeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableComponentsOnPanel(buttonPanel);
                addEdgeButton.setEnabled(false);
                graph.setGraphState(1);
            }
        });
    }

    /**
     * Remove addNode and addEdge buttons
     * Saves existing graph
     * Create Prim and Kruskal Buttons
     */
    public void createSolveGraphButton() {
        JButton addSubmitButton = makeButton("Solve Graph", BUTTON_H, BUTTON_W);
        addSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearButtonPanel();
            }
        });
    }
}
