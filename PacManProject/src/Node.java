import java.util.PriorityQueue;
import java.util.Stack;
public class Node  {
    int x, y;
    double f, g, h;
    Node parent;

    public Node(int x, int y, double f, double g, Node parent) {
        this.x = x;
        this.y = y;
        this.f = f;
        this.g = g;
        this.h = 0.0;
        this.parent = parent;
    }

}
