public class PointSET {
    private SET<Point2D> pSet;
    
    // construct an empty set of points
    public PointSET() {
        pSet = new SET<Point2D>();
    }
    
    // is the set empty?
    public boolean isEmpty() {
        return pSet.isEmpty();
    }
    
    // number of points in the set
    public int size() {
        return pSet.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pSet.add(p);
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return pSet.contains(p);
    }
    
    // draw all of the points to standard draw
    public void draw() {
        for (Point2D point:pSet)
            point.draw();
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> s = new Stack<Point2D>();
        for (Point2D point:pSet)
            if (rect.contains(point)) s.push(point);
        return s;
    }
    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
        double min, tmp;
        Point2D minP;
        
        if (isEmpty()) return null;
        
        minP = pSet.min();
        min = p.distanceTo(minP);
        
        for (Point2D point:pSet) {   
            tmp = p.distanceTo(point);
            if (tmp < min) {
                minP = point;
                min = tmp;  
            }
        }
        return minP; 
    }
}
