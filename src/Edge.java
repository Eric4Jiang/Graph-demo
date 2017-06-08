import java.awt.*;

public class Edge {

    public Node n1, n2;
    public double length, slope, weight;
    public Color color;

    public Edge (Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = lengthSquared();
    }

    public double lengthSquared() {
        return Math.pow(n1.getX() - n2.getX(), 2) + Math.pow(n1.getY() - n2.getY(), 2);
    }

}
