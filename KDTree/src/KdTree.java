import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;

/**
 * Created by admin on 07.03.2016.
 */
public class KdTree {

        private final Point2D point;

        public KdTree()                               // construct an empty set of points
        {
                this.point = new Point2D(0, 0);
        }
        public boolean isEmpty()                      // is the set empty?
        {
                return size() == 0;
        }

        public int size()                         // number of points in the set
        {
                return 0;
        }
        public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
        {
kdTree.add
        }
        public boolean contains(Point2D p)            // does the set contain point p?
        {
                return true;
        }
        public void draw()                         // draw all points to standard draw
        {

        }
        public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
        {
                return new ArrayList<Point2D>();
        }
        public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
        {
                return new Point2D(0, 0);
        }
        public static void main(String[] args)                  // unit testing of the methods (optional)
        {
        }
}
