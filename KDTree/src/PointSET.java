import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
    private SET<Point2D> pointsSET;

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
       ArrayList<Point2D> arr = new ArrayList<>();
       Iterator<Point2D> it = pointsSET.iterator();
        while (it.hasNext()) {
            Point2D point = it.next();
            if (point.x() <= rect.xmax() && point.x() >= rect.xmin() &&
                    point.y() <= rect.ymax() && point.y() <= rect.ymin()) {
                arr.add(point);
            }
        }
        if (arr.size() != 0) return arr;
        return null;
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (pointsSET.size() == 0) return null;

        ArrayList<Point2D> arr = new ArrayList<>();
        Iterator<Point2D> it = pointsSET.iterator();
        Point2D nearest = it.next();
        double nearDistance = Math.pow(nearest.x() - p.x(), 2) + Math.pow(nearest.y() - p.y(), 2);
        while (it.hasNext()) {
            Point2D point = it.next();
            double distance = Math.pow(point.x() - p.x(), 2) + Math.pow(point.y() - p.y(), 2);
            if (distance < nearDistance) nearest = point;
            }
        return nearest;
    }


    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
    }
}