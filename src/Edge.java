import java.awt.*;

public class Edge {

    public Node n1, n2;
    public double length, slope, weight;
    public Color color;

    public Edge (Node n1, Node n2) {
        this.n1 = n1;
        this.n2 = n2;
        this.weight = lengthSquared();
        color = Graph.defaultC;

        System.out.println("Edge name = " + n1.name + " " + n2.name);
    }

    public double slope() {
        double slope = (n1.getX() - n2.getX()) / (n1.getX() - n2.getX());
        return (n1.getX() - n2.getX() == 0 ? Math.abs(slope) : slope);
    }

    public Point midpoint() {
        return new Point((int)(n1.getX() + n2.getX())/2, (int)(n1.getX() + n2.getX())/2);
    }

    public double lengthSquared() {
        return Math.pow(n1.getX() - n2.getX(), 2)
                + Math.pow(n1.getY() - n2.getY(), 2);
    }

}
