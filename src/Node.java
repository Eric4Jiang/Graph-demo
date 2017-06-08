import java.awt.*;

public class Node {

    public Point vertex;
    public String name;
    public Graph parentGraph;
    public Color color;

    public Node(int x, int y, Graph parentGraph) {
        this.vertex = new Point(x, y);
        this.name = Integer.toString(x) + Integer.toString(y);
        this.parentGraph = parentGraph;

        System.out.println(name);
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }

    public double getX() {
        return this.vertex.getX();
    }

    public double getY() {
        return this.vertex.getY();
    }
}
