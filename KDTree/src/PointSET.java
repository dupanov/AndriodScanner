import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> pointsSET;
    private static Node node;
    public PointSET()                               // construct an empty set of points
    {
        pointsSET = new SET<>();
    }
    public boolean isEmpty()                      // is the set empty?
    {
        return size() == 0;
    }

    public int size()                         // number of points in the set
    {
        return this.pointsSET.size();
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        pointsSET.add(p);
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        return pointsSET.contains((Point2D) p);
    }
    public void draw()                         // draw all points to standard draw
    {
        for (Point2D point: pointsSET) {
            point.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
    {
        return new ArrayList<Point2D>();
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        return new Point2D(0, 0);
    }

    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    Node(Point2D p, RectHV rect, Node lb, Node rt)
    {
        Node.this.lb = lb;
        Node.this.p = p;
        Node.this.rect = rect;
        Node.this.rt = rt;
    }
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
    }
}